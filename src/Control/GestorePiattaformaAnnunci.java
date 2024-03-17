package Control;

import Entity.*;
import Utility.DatiInserzionistaAnnuncio;
import exception.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.apache.commons.io.FileUtils;

import DAO.*;

import java.util.ArrayList;
import java.util.List;


public class GestorePiattaformaAnnunci {

    private static GestorePiattaformaAnnunci gpa = null;
    private Annuncio annuncio;



    public static GestorePiattaformaAnnunci getInstance() 
	{ 
		if (gpa == null) 
			gpa = new GestorePiattaformaAnnunci(); 

		return gpa; 
	}




    public GestorePiattaformaAnnunci(){
        annuncio = null;
    }

  
    

    public Annuncio controlloInserzionistaAnnuncio(Long idAnnuncio, Long idInserzionista, String password) throws AuthenticationException, OperationException{

        boolean valid;
        Annuncio annuncio = null;
       
        
        try{

            int num = InserzionistaDAO.checkPresenzaInserzionista(null,idInserzionista, password);
            if (num == 0)
                valid = false;
            else
                valid = true;
            
            if (valid == false)
                throw new AuthenticationException("Autenticazione fallita");

            FileUtils.cleanDirectory(new File("./Output"));

            annuncio = trovaAnnuncioPerID(idAnnuncio, idInserzionista);

            if (annuncio != null)
                conversioneobjFotografie(annuncio.getListaFotografie(),"1"); //STAMPA FOTOGRAFIA CARTELLA OUTPUT
            
        } catch(OperationException o){
            throw new OperationException("ERRORE connessione DB da trovaAnnuncioPerID : controlloInserzionistaAnnuncio");
        } catch (IOException ioE){
            throw new OperationException("ERRORE nella clean o nella stampa delle foto nella cartella di Outuput : controlloInserzionistaAnnuncio");
        } catch (DBConnectionException | DAOException dbE){
            throw new OperationException("ERRORE legato al database : controlloInserzionistaAnnuncio");
        }

        return annuncio;


    }




    public void aggiornaAnnuncio (Annuncio annuncio, Tipologia tipologia, String città, String cap, Double numeroMetriQuadri, Integer numeroVani, Stato stato, String descrizione, Double prezzo, java.util.List<Integer> listaindici, java.util.List<String> listanomi) throws OperationException{
        

        try{

            if (tipologia==null){
                tipologia = annuncio.getTipologia();
            }

            if (città == null){
                città = annuncio.getCittà();
            }

            if (cap == null){
                cap = annuncio.getCap();
            }

            if (numeroMetriQuadri == null){
                numeroMetriQuadri = annuncio.getNumeroMetriQuadri();
            }

            if (numeroVani == null){
                numeroVani = annuncio.getNumeroVani();
            }

            if (stato == null){
                stato = annuncio.getStato();
            }

            if (descrizione == null){
                descrizione = annuncio.getDescrizione();
            }

            if (prezzo == null){
                prezzo = annuncio.getPrezzo();
            }


            AnnuncioDAO.updateAnnuncio(null,annuncio.getIdAnnuncio(), tipologia, città, cap, numeroMetriQuadri, numeroVani, stato, descrizione, prezzo);

            if(listaindici != null && listanomi !=null)
            {
                for(int i=0; i<listaindici.size(); i++)
                {
                    InputStream in = new FileInputStream(listanomi.get(i));
                    FotografiaDAO.updateFotografia(annuncio.getListaFotografie().get((listaindici.get(i) - 1)).getIdFotografia(), in);
                }
            } 
            else if(listaindici != null && listanomi == null)
            {
                for(int i=0; i<listaindici.size(); i++)
                {
                    FotografiaDAO.deleteFotografia(annuncio.getListaFotografie().get((listaindici.get(i) - 1)).getIdFotografia());
                }                
            }
            else if(listaindici == null && listanomi != null)
            {
                for(int i=0; i<listanomi.size(); i++)
                {
                    InputStream in = new FileInputStream(listanomi.get(i));
                    FotografiaDAO.createFotografia(in, annuncio.getIdAnnuncio());
                }                 
            }


        }catch(DBConnectionException e){
            throw new OperationException("Problema riscontrato all'interno dell'applicazione : modificaDatiAnnuncio");
        }catch(DAOException dE){
            throw new OperationException("Mi dispiace, qualcos è andato storto : modificaDatiAnnuncio");
        }catch (FileNotFoundException e){
            throw new OperationException("ERRORE nella creazione dell'InputStream : modificaDatiAnnuncio");
        }

    }




    public DatiInserzionistaAnnuncio ricercaAnnunci(Tipologia tipologia, String citta, String cap, Integer numvani, String email) throws OperationException
    {
        DatiInserzionistaAnnuncio risultato = new DatiInserzionistaAnnuncio(null, null);


        List<Annuncio> lA = new ArrayList<>();
        List<String> lC = new ArrayList<>(); 
        String tipologiaString = null;
        String numvaniString = null;

        if(tipologia != null)
            tipologiaString = String.valueOf(tipologia);


        if(numvani != null)
            numvaniString = String.valueOf(numvani);

        try{
            lA=AnnuncioDAO.readAnnunciRicerca(null,tipologiaString, citta, cap, numvaniString);
        } catch (DBConnectionException e){
            throw new OperationException("Problema riscontrato nella connessione al DB : ricercaAnnunci");       
        } catch (DAOException e){
        throw new OperationException("Problema riscontrato nel lavorare col DB : ricercaAnnunci");       
        }
               

        if(lA.size()!=0)
        {
            Contatto cUR = null;

            try{
                cUR = ContattoDAO.readContattoUR(null, email);               
            }catch(DBConnectionException e){
                throw new OperationException("Problema riscontrato nella connessione al DB : ricercaAnnunci");       
            }catch(DAOException e){
                throw new OperationException("Problema riscontrato nel lavorare col DB : ricercaAnnunci");       
            }

            if(cUR!=null)
            {
                
                for(int i = 0; i<lA.size() ; i++)
                {
                    String contattoString = prelevaDatiInserzionista (lA.get(i).getInserzionista().getContatto());
                    lC.add(contattoString);
                }


                risultato.setListaAnnunci(lA);
                risultato.setListaContattiInserzionisti(lC);
            }
            else {
                lC = null;
            }

            risultato.setListaAnnunci(lA);
            risultato.setListaContattiInserzionisti(lC);

        }

        try{
        FileUtils.cleanDirectory(new File("./Output"));
        }catch(IOException e){
            throw new OperationException("Problema riscontrato nel pulire la cartella di output: ricercaAnnunci");
        }

        return risultato;
    }




    public void eliminaAnnuncio (Long IDAnnuncio){

        try{
            FotografiaDAO.deleteFotografia(IDAnnuncio);
        } catch(DBConnectionException | DAOException e){
            System.out.println("Errore nella cancellazione delle fotografie dell'annuncio");
            e.printStackTrace();
        }

        try{
            AnnuncioDAO.deleteAnnuncio(null,IDAnnuncio);
        } catch(DAOException | DBConnectionException e){
            System.out.println("Errore nella cancellazione dell'annuncio");
            e.printStackTrace();
        }


    }
    



    public boolean registrazioneUtente(String nome, String cognome, String cellulare, String email) throws RegistrationException{
        boolean valid = true;
        Integer num =null;
        
        try{
            num = ContattoDAO.checkPresenzaContatto(null, email, cellulare);
        }catch(DAOException e){
            throw new RegistrationException("ERRORE nel controllo dell'utente : registrazioneUtente" + e.toString());
        }catch(DBConnectionException e){
            throw new RegistrationException("ERRORE nella connessione al DB : registrazioneUtente");
        }

        if(num==1)
        {
            valid=false;
        }

        if(valid == true)
        {
            long idUr = 0;

            try{
                idUr = UtenteRegistratoDAO.createUtenteRegistrato(null, nome, cognome);
            }catch(DAOException e){
                throw new RegistrationException("ERRORE nella registrazione dell'utente : registrazioneUtente");
            }catch(DBConnectionException e){
                throw new RegistrationException("ERRORE nella connessione al DB : registrazioneUtente");
            }


            try{ 
                ContattoDAO.createContattoUR(null, email, cellulare, idUr);
            }catch(DAOException e){
                throw new RegistrationException("ERRORE nella creazione del contatto ");
            }catch(DBConnectionException e){
                throw new RegistrationException("ERRORE nella connessione al DB ");
            }            

        }

        
        return valid;
}




    public void conversioneobjFotografie(java.util.List<Fotografia> lF,String a) throws IOException
    {
            
        if(lF.size()!=0){
        String outputFile = "./Output/risultato";
        String end = ".jpg";
        int n = lF.size();

        for(int i=0; i<n;i++)
        {
            String output = outputFile + a + "." + (i+1) + end;
            try{

                Files.copy(lF.get(i).getPath(), Paths.get(output));
            
                lF.get(i).getPath().close();

            }
            catch(IOException e)
            {
                throw new IOException("ERRORE nel download delle fotografie nella cartella di Output : conversioneobjFotografie",e);
            }

        }
    }
}




    private Annuncio trovaAnnuncioPerID(Long idAnnuncio, Long idInserzionista) throws OperationException{

        try{

        annuncio = AnnuncioDAO.readAnnuncioPerIDs(null,idAnnuncio, idInserzionista);

        } catch (DBConnectionException a){
            throw new OperationException("ERRORE nell'accesso alla base dati : trovaAnnuncioPerID"); 
        }catch (DAOException e){
            throw new OperationException("ERRORE nell'interazione con la base dati : trovaAnnuncioperID");
        }

        return annuncio;

    }
 
    


    private String prelevaDatiInserzionista (Contatto contatto){

        String conttattoString = "Contatto inserzionista: \nEmail: "+ contatto.getEmail()+"\nCellulare: "+contatto.getCellulare() + "\n";
        return conttattoString;
    }
  
 

    
}
