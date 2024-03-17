package Boundary;

import Entity.*;
import exception.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Control.*;

public class BInserzionista {

    static Scanner scan = new Scanner(System.in);
    final static int SIZE_LISTA_FOTOGRAFIE = 8;
    final static int SIZE_MAX_STRING = 255;
    final static int CAP_LENGTH = 5;
    final static String FIRST_PART_PATH = "./Input/";
    final static Character APICES = '"';
    final static Character DAPICE = (char) 34; 
 
    final static Pattern caratteriSpeciali = Pattern.compile("[^a-zA-Z ]");
    final static Pattern caratteriSpeciali2 = Pattern.compile("[^a-zA-Z!0-9]");
    final static Pattern caratteriSpeciali3 = Pattern.compile("[^a-zA-Z_.0-9]");
    final static Pattern caratteriSpeciali4 = Pattern.compile("[^a-zA-Z!.0-9 ]");
    


    public static void aggiornaAnnuncio () {

  
        GestorePiattaformaAnnunci gestorePiattaformaAnnunci = GestorePiattaformaAnnunci.getInstance(); //gpa is singleton;   
        Annuncio annuncio;
        boolean inputvalido = false;
        Long idAnnuncio = 0L, idInserzionista = 0L;
        String password = null;
        Tipologia tipologia = null;
        String citta = null, cap = null, descrizione = null;
        Double numeroMetriQuadri = 0D, prezzo = 0D;
        Integer numeroVani = 0;
        Stato stato = null; 
        NomieIndiciFoto nomieIndiciFoto = null;

        Matcher match;
        boolean check;



        List<String> listanomi= null;
        List<Integer> listaindici= null;
     
        

        System.out.println("\n--------AGGIORNA ANNUNCIO--------\n");
        

            while(!inputvalido){

                System.out.println("\nInserisci l'ID dell'annuncio da modificare. (Formato 4XXXXX)");
                
                String idAnnuncioTemp = scan.nextLine();
                try{
                idAnnuncio = Long.parseLong(idAnnuncioTemp);
                if (idAnnuncioTemp.charAt(0)=='4' && idAnnuncioTemp.length() == 6 && idAnnuncioTemp.matches("\\d+")){
                    inputvalido = true;
                } else{
                    System.out.println("Formato id annuncio non corretto. Riprova...");
                    System.out.println();
                    }

                }catch (NumberFormatException nE){
                    System.out.println("E' stata inserita una stringa alfanumerica o non consentita, riprovare...");
                }
              
            }

            inputvalido = false;

            

            while (!inputvalido){
                System.out.println();
                System.out.println("Inserisci il tuo ID da inserzionista. (Formato 1xxxxx)");
                
                String idInserzionistaTemp = scan.nextLine();
                try{
                idInserzionista = Long.parseLong(idInserzionistaTemp);
                if (idInserzionistaTemp.charAt(0)=='1' && idInserzionistaTemp.length() ==6){
                    inputvalido = true;
                } else{
                    System.out.println("Formato id inserzionista non corretto. Riprova...");
                    System.out.println();
                    }
                }catch (NumberFormatException nE){
                    System.out.println("E' stata inserita una stringa alfanumerica o non consentita, riprovare...");
                }

            }

            inputvalido = false;

            while (!inputvalido){
            System.out.println();    
            System.out.println("Inserisci la tua password.");
            
            password = scan.nextLine();
            
            match = caratteriSpeciali2.matcher(password);
            check = match.find();
        
            if (password.length() > SIZE_MAX_STRING){
                System.out.println("\nLunghezza password non valida, riprovare...");
                } else if (check || password.equals("")){
                    System.out.println("\nHai inserito un carattere speciale, riprovare...");
            }   else
                    inputvalido=true;
        
        }

            
                
            try{

                annuncio = gestorePiattaformaAnnunci.controlloInserzionistaAnnuncio(idAnnuncio, idInserzionista, password);

                if (annuncio != null){
                    System.out.println("\nQuesto è l'annuncio da modificare:\n"); 
            
                    System.out.println(annuncio.toString());

                    System.out.println("Nella cartella Ouput \u00E8 possibile visualizzare le fotografie");
            
                    System.out.println("\nInserisci i valori che vuoi modificare dell'annuncio (Inserisci 'n' se non vuoi modificarlo)");

                    inputvalido = false;

                    while (!inputvalido){
                    try{
                        System.out.println("\nInserisci la tipologia (Affitto/Vendita): ");
                        String tipologiaTemp = scan.nextLine();

                        if (!tipologiaTemp.equals("n"))
                        tipologia = Tipologia.valueOf(tipologiaTemp.toUpperCase());
                    else
                        tipologia = null;

                    inputvalido=true;

                    }catch(IllegalArgumentException e){
                        System.out.println("\nStringa inserita non valida, riprovare...");
                    }
               
                }

                inputvalido = false;

                while(!inputvalido){

                    System.out.println("\nInserisci la città: ");
                    citta = scan.nextLine();

                    match = caratteriSpeciali.matcher(citta);
                    check = match.find();

                    if (citta.length() > SIZE_MAX_STRING){
                        System.out.println("\nHai inserito una stringa più grande di 255 caratteri, riprovare...");
                    } else if (check || citta.equals("")){
                        System.out.println("\nHai inserito un carattere speciale, riprovare...");
                        } else if (citta.equals("n")){

                            citta = null;
                            inputvalido = true;
                        }
                            else{
                                inputvalido = true;
                                citta = citta.toUpperCase();
                            }
                }

                inputvalido = false;
            
                while (!inputvalido){
                    System.out.println("\nInserisci il CAP (Formato XXXXX): ");
                    cap = scan.nextLine();

                    if (cap.length()==CAP_LENGTH && cap.matches("//d+")){
                        inputvalido = true;

                    } else if (cap.equals("n")){
                        cap = null;
                        inputvalido = true;

                        } else{
                            System.out.println("\nStringa inserita non valida, riprovare...");
                            }
                    }

                inputvalido=false;

                while(!inputvalido){
                    try{
                        System.out.println("\nInserisci il numero di metri quadri: ");
                        String numM2Temp = scan.nextLine();
                        if (!numM2Temp.equals("n"))
                            numeroMetriQuadri = Double.parseDouble(numM2Temp);
                        else
                            numeroMetriQuadri = null;

                        inputvalido = true;

                    }catch(NumberFormatException n){
                        System.out.println("\nNon è stato inserito un valore numerico, riprovare...");
                    }
                }

                inputvalido = false;

                while(!inputvalido){
                    try{
                        System.out.println("\nInserisci il numero di vani: ");
                        String numVaniTemp = scan.nextLine();
                        if (!numVaniTemp.equals("n"))
                            numeroVani = Integer.parseInt(numVaniTemp);
                        else
                            numeroVani = null;
                    
                        inputvalido = true;

                    }catch(NumberFormatException n){
                        System.out.println("\nNon è stato inserito un valore numerico, riprovare...");
                    }
                }

                inputvalido = false;

                while (!inputvalido){
                    try{
                        System.out.println("\nInserisci lo stato(Nuovo/Ristrutturato): ");
                        String statoTemp = scan.nextLine();
                        if (!statoTemp.equals("n"))
                            stato = Stato.valueOf(statoTemp.toUpperCase());
                        else    
                            stato = null;
                    
                        inputvalido = true;

                    } catch(IllegalArgumentException e){
                        System.out.println("\nStringa inserita non valida, riprovare...");
                    }
                
                }

                inputvalido = false;

                while(!inputvalido)
                {
                    System.out.println("\nInserisci la descrizione: ");
                    descrizione = scan.nextLine();

                    match = caratteriSpeciali4.matcher(descrizione);
                    check = match.find();

                    if (descrizione.length() > SIZE_MAX_STRING ){
                        System.out.println("\nHai inserito una stringa più grande di 255 caratteri, riprovare...");
                    } else if (descrizione.equals("n"))
                        {
                            descrizione = null;
                            inputvalido = true;
                        } else if (check || descrizione.equals("")){
                            System.out.println("\nHai inserito un carattere speciale, riprovare...");
                        }
                            else{
                                inputvalido = true;
                            }
                }
                
                inputvalido = false;

                while (!inputvalido){
                    try{

                        System.out.println("\nInserisci il prezzo: ");
                        String prezzoTemp = scan.nextLine();

                        if (!prezzoTemp.equals("n"))
                            prezzo = Double.parseDouble(prezzoTemp);
                        else
                            prezzo = null;
                
                        inputvalido = true;

                    } catch (NumberFormatException n){
                        System.out.println("\nNon è stato inserito un valore numerico, riprovare...");
                    }

            
                }


                //Gestione aggiunta, modifica ed eliminazione foto
                inputvalido = false;
            
                while (!inputvalido){

                    System.out.println("\nNell'annuncio ci sono "+ annuncio.getListaFotografie().size() +" fotografie. Cosa vuoi fare?");
                    System.out.println("1) AGGIUNGERE FOTOGRAFIE");
                    System.out.println("2) MODIFICARE FOTOGRAFIE ESISTENTI");
                    System.out.println("3) ELIMINARE FOTOGRAFIE ESISTENTI");
                    System.out.println("4) NESSUNA OPERAZIONE SULLE FOTOGRAFIE");
                    String operazioneFotografieTemp = scan.nextLine();

                    switch(operazioneFotografieTemp){

                        case "1": nomieIndiciFoto = aggiungiFoto(annuncio); inputvalido=true; break;

                        case "2": nomieIndiciFoto = aggiornaFotografie(annuncio); inputvalido=true; break;

                        case "3": nomieIndiciFoto = eliminaFoto(annuncio); inputvalido=true; break;

                        case "4": nomieIndiciFoto = new NomieIndiciFoto(null, null);inputvalido=true; break;

                        default: System.out.println("\nValore inserito non valido, riprovare..."); break;
                    }

            }

                listaindici = nomieIndiciFoto.getListaIndici();
                listanomi = nomieIndiciFoto.getListaNomi();

                gestorePiattaformaAnnunci.aggiornaAnnuncio(annuncio, tipologia, citta, cap, numeroMetriQuadri, numeroVani, stato, descrizione, prezzo, listaindici,listanomi);
    
                System.out.println("\nModifica avvenuta con successo!");
    
            } else{
                System.out.println("\nERRORE l'idannuncio non è associato all'inserzionista");
            }

        } catch (AuthenticationException aE){
            System.out.println("\nERRORE Autenticazione fallita");
        } catch (OperationException oE){
            System.out.println("\nERRORE nel lavorare con il DB");
        } catch (NoSuchElementException nE){
            System.out.println("\nValore inserito da tastiera non valido, riprovare...");
        } catch (Exception e){
            System.out.println("\nUnexpected exception, riprovare...");
            e.printStackTrace();
        }


    }



    public static void eliminaAnnuncio(){
        GestorePiattaformaAnnunci gestorePiattaformaAnnunci = GestorePiattaformaAnnunci.getInstance();
        Annuncio annuncio;
        boolean inputvalido = false;
        long idAnnuncio = 0L;
        long idInserzionista = 0L;
        String passwordTemp = null;

        Matcher match;
        boolean check;

        while (!inputvalido) {
        
            System.out.println("\nInserisci l'ID dell'annuncio da eliminare in formato 4#####:");
            String idAnnuncioString = scan.nextLine();
        
            try {
                idAnnuncio = Long.parseLong(idAnnuncioString);
        
            if (idAnnuncioString.charAt(0) == '4' && idAnnuncioString.length() == 6 && idAnnuncioString.matches("\\d+")) {
        
                inputvalido = true;
            } else {
        
                System.out.println("\nFormato ID Annuncio non corretto:");
            }
        
            } catch (NumberFormatException ex) {
                System.out.println("\nE' stata inserita una stringa alfanumerica o non consentita, riprovare...");
            }
        }
        
        inputvalido = false;
        
        while (!inputvalido) {
        
            System.out.println("\nInserisci l'ID dell'inserzionista in formato 1#####:");
            String idInserzionistaString = scan.nextLine();
        
            try {
                idInserzionista = Long.parseLong(idInserzionistaString);
        
                if (idInserzionistaString.charAt(0) == '1'  && idInserzionistaString.length() == 6 && idInserzionistaString.matches("^[0-9]*$")) {
        
                    inputvalido = true;
                } else {
        
                    System.out.println("\nFormato ID Inserzionista non corretto:");
                }
            } catch (NumberFormatException ex) {
                System.out.println("\nE' stata inserita una stringa alfanumerica o non consentita, riprovare...");
            }
        }     

        inputvalido = false;
        while (!inputvalido) {      
            System.out.println("\nInserisci la tua password.");

            passwordTemp = scan.nextLine();

            match = caratteriSpeciali2.matcher(passwordTemp);
            check = match.find();

            if (passwordTemp.length() > SIZE_MAX_STRING) {
                System.out.println("\nLunghezza password non valida, riprovare...");
            
            } else if (check || passwordTemp.equals("")) {//password vuota,se non si fa cosi non si esce piu) {
            
                System.out.println("\nHai inserito un carattere speciale, riprovare... ");
            } else{
                inputvalido = true;
            }


            }  
                
        
            try {
                annuncio=gestorePiattaformaAnnunci.controlloInserzionistaAnnuncio(idAnnuncio,idInserzionista,passwordTemp);
                
                if (annuncio != null) {

                    System.out.println("\n Questo è l'annuncio da eliminare:\n"); 
                
                    System.out.println(annuncio.toString());

                    System.out.println("\nNella cartella Ouput \u00E8 possibile visualizzare le fotografie");
                    
                System.out.println("\nSei sicuro di voler eliminare l'annuncio?(Digita 'S' o 's' per confermare, 'N' o 'n')");
                String conferma=scan.nextLine();

                boolean confermaEl = false;

                while (!confermaEl){

                    if(conferma.toUpperCase().equals("S")){
                        gestorePiattaformaAnnunci.eliminaAnnuncio(idAnnuncio);
                        System.out.println("\nEliminazione avvenuta con successo");
                        confermaEl = true;
                    }else if (conferma.toUpperCase().equals("N")){
                        System.out.println("\nEliminazione annullata");
                        confermaEl = true;
        
                    }else{
                        System.out.println("\nValore inserito non valido, riprovare...");
                        conferma=scan.nextLine();
                    }

                }
            
            } else {
                System.out.println("\nERRORE: l'idannuncio non è associato all'inserzionista");
            }
        
    }  catch (NoSuchElementException nE){
        System.out.println("\nValore inserito da tastiera non valido, riprovare...");
    }    catch (OperationException e){
        System.out.println("\nERRORE l'idannuncio non è associato all'inserzionista.");
    }    catch (AuthenticationException e){
        System.out.println("\nERRORE Autenticazione fallita.");
    }catch (Exception e){
        System.out.println("\nUnexpected exception, riprovare...");
    }
        
}



    private static NomieIndiciFoto aggiungiFoto(Annuncio annuncio){

        List<String> listanomi = new ArrayList<>(SIZE_LISTA_FOTOGRAFIE);
        List<Integer> listaindici = null;
        boolean inputnumfoto = false;
        String numFotoString = null;

        Matcher match;
        boolean check;

        
        while (!inputnumfoto){

            System.out.println("\nQuante altre fotografie vuoi aggiungere? max("+(SIZE_LISTA_FOTOGRAFIE- annuncio.getListaFotografie().size())+")");
        
            try{
                
                numFotoString = scan.nextLine();

                    Integer numFoto = Integer.parseInt(numFotoString);
            
                    if ((!(numFoto > (SIZE_LISTA_FOTOGRAFIE - annuncio.getListaFotografie().size()))) && numFoto >= 0){

                        System.out.println("\nAttenzione! Inserire esclusivamente il nome della foto presente nella cartella di Input; senza l'estensione (si presuppone che siano tutti .jpg).");
                        for (int i=0; i<numFoto; i++){

                            System.out.println("\nInserisci il nome della "+(i+1)+"° foto che vuoi inserire: ");
                            String nomeFoto = scan.nextLine();
                            
                            match = caratteriSpeciali3.matcher(nomeFoto);
                            check = match.find();

                        while(check || nomeFoto.equals(""))
                        {
                            System.out.println("\nHai inserito un carattere speciale... riprovare: ");
                            nomeFoto = scan.nextLine();

                            match = caratteriSpeciali3.matcher(nomeFoto);
                            check = match.find();
                        }
                        
                            listanomi.add(FIRST_PART_PATH+nomeFoto+".jpg");
                        }

                        inputnumfoto = true;
                    } else{
                        System.out.println("\nIl numero delle fotografie inserito è troppo grande o minore di 0. Riprovare...");
                    }

            
            
            }catch(InputMismatchException iE){
                System.out.println("\nNon è stato inserito un valore numerico, riprovare...");//Questo catch serve per catturare le eccezioni che posso essere sollevate da scan.nextInt nel caso in cui a terminale non venga inserito un numero
            }catch (NoSuchElementException nE){
                System.out.println("\nNon è stato inserito nulla, riprovare...");
            }catch(IllegalArgumentException iaE)
            {
                System.out.println("\nNon è stato inserito nulla, riprovare...");                
            }
        }

        NomieIndiciFoto nomieIndiciFoto = new NomieIndiciFoto(listanomi, listaindici);
        return nomieIndiciFoto;


    }



    private static NomieIndiciFoto aggiornaFotografie (Annuncio annuncio){
  

        int numFotoAnnuncio = annuncio.getListaFotografie().size();
        boolean inputvalido = false;
        List<String> listanomi = new ArrayList<>(SIZE_LISTA_FOTOGRAFIE);
        List<Integer> listaindici = new ArrayList<>(SIZE_LISTA_FOTOGRAFIE);
        boolean inputFoto = false;

        Matcher match;
        boolean check;

        while (!inputvalido){

            try{
                System.out.println("\nQuante fotografie vuoi modificare? (max " + numFotoAnnuncio + ")");
                String numFotoString = scan.nextLine();


                int numFoto = Integer.parseInt(numFotoString);

                if (numFoto == numFotoAnnuncio){

                    System.out.println("\nAttenzione! Inserire esclusivamente il nome della foto presente nella cartella di Input; senza l'estensione (si presuppone che siano tutti .jpg).");            
                    for (int i=0; i<numFotoAnnuncio; i++){
                        System.out.println("\nInserisci il nome della "+(i+1)+"° foto da modificare: ");
                        String nomeFoto = scan.nextLine();

                        match = caratteriSpeciali3.matcher(nomeFoto);
                        check = match.find();

                        while(check || nomeFoto.equals(""))
                        {
                            System.out.println("\nHai inserito un carattere speciale... riprovare: ");
                            nomeFoto = scan.nextLine();

                            match = caratteriSpeciali3.matcher(nomeFoto);
                            check = match.find();
                        }

                        listanomi.add(FIRST_PART_PATH+nomeFoto+".jpg");
                        listaindici.add(i+1);
                        
                    
                    }
                    
                    inputvalido = true;

                } else  if(numFoto < numFotoAnnuncio && numFoto > 0){
                    System.out.println("\nIn base alle foto restituite nella cartella di output, inserisci l'ultima cifra del nome della foto da modificare");
                    System.out.println("Ad esempio, se vuoi modificare la foto 'risultato1.3', inserisci 3");
                

                    for (int i=0; i<numFoto; i++){

                        inputFoto = false;
                        Integer indiceFoto = null;


                        while (!inputFoto){

                            boolean cFoto = false;

                            while(!cFoto){

                                try{
                                    System.out.println("\n"+(i+1) +"° Indice: ");
                                    String indiceFotoString = scan.nextLine();
                                    indiceFoto = Integer.parseInt(indiceFotoString);
                                    cFoto=true;
                                } catch (NumberFormatException nE){
                                    System.out.println("\nValore non valido, riprovare...");
                                }
                            }


                            if (indiceFoto <= numFotoAnnuncio && !(listaindici.contains(indiceFoto)) && indiceFoto > 0){
                                System.out.println("\nAttenzione! Inserire esclusivamente il nome della foto presente nella cartella di Input; senza l'estensione (si presuppone che siano tutti .jpg).");
                                System.out.println("\nInserisci il nome della foto che vuoi inserire: ");
                                String nomeFoto = scan.nextLine();

                                match = caratteriSpeciali3.matcher(nomeFoto);
                                check = match.find();

                            while(check || nomeFoto.equals(""))
                            {
                                System.out.println("\nHai inserito un carattere speciale... riprovare: ");
                                nomeFoto = scan.nextLine();

                                match = caratteriSpeciali3.matcher(nomeFoto);
                                check = match.find();
                            }

                            listaindici.add(indiceFoto);
                            listanomi.add(FIRST_PART_PATH+nomeFoto+".jpg");
                            inputFoto=true;

                            } else if(indiceFoto > numFotoAnnuncio || indiceFoto <= 0){
                                System.out.println("\nIndice foto non valido, riprovare...");
                
                            } else {
                                System.out.println("\nIndice foto già inserito o indice foto sbagliato, riprovare...");
                            }

                        }
                 
                    }

                    inputvalido = true;
                } else{
                    System.out.println("\nValore inserito non valido, riprovare...");
                }

            } catch (NumberFormatException nE){
            System.out.println("\nValore non valido, riprovare...");
            }catch(IllegalArgumentException iaE)
            {
                System.out.println("\nNon è stato inserito nulla, riprovare...");                
            }

        }

        NomieIndiciFoto nomieIndiciFoto = new NomieIndiciFoto(listanomi, listaindici);
        return nomieIndiciFoto;
            
    }



    private static NomieIndiciFoto eliminaFoto (Annuncio annuncio){

       
        boolean inputindice;
        List<String> listanomi = null;
        List<Integer> listaindici = new ArrayList<>(SIZE_LISTA_FOTOGRAFIE);
        boolean inputFoto = false;

        Integer numFotoAnnuncio = annuncio.getListaFotografie().size();
        

        while(!inputFoto){

            try{

                System.out.println("\nQuante fotografie vuoi eliminare? (max " + (numFotoAnnuncio-1) + ")");
                String numFotoDaEliminare = scan.nextLine(); 
                int numFoto = Integer.parseInt(numFotoDaEliminare);
                
                if (numFoto <= numFotoAnnuncio-1 && numFoto > 0){

                    System.out.println("\nIn base alle foto restituite nella cartella di output, inserisci l'ultima cifra del nome della foto da eliminare.");
                    System.out.println("Ad esempio, se vuoi eliminare la foto 'risultato1.3', inserisci 3");


                    for (int i=0; i<numFoto; i++){

                        inputindice = false;
                        Integer indiceFoto = null;

                        while (!inputindice){
                            
                            boolean cFoto = false;

                        while(!cFoto)
                        {
                            try{
                                System.out.println("\n"+(i+1) +"° Indice: ");
                                String indiceFotoString = scan.nextLine();
                                indiceFoto = Integer.parseInt(indiceFotoString);
                                cFoto=true;
                            } catch (NumberFormatException nE){
                                System.out.println("\nValore non valido, riprovare...");
                            }

                        }

                     
                        if (indiceFoto <= numFotoAnnuncio && !(listaindici.contains(indiceFoto)) && indiceFoto > 0){

                            listaindici.add(indiceFoto);
                            inputindice = true;

                        }else if(indiceFoto > numFotoAnnuncio || indiceFoto <= 0){

                            System.out.println("\nIndice foto non valido, riprovare...");
                
                            } else{

                                System.out.println("\nIndice foto già inserito, riprovare...");
                            } 

                        }                   
                    }

                    inputFoto = true;

                } else{
                    System.out.println("\nNumero di foto da eliminare inserito non valido, riprovare...");
                }
            }catch(NumberFormatException iE){
                System.out.println("\nNon è stato inserito un valore numerico, riprovare...");
            }catch(IllegalArgumentException iaE)
            {
                System.out.println("\nNon è stato inserito nulla, riprovare...");                
            }

        }

        NomieIndiciFoto nomieIndiciFoto = new NomieIndiciFoto(listanomi, listaindici);
        return nomieIndiciFoto;
    }


    
}


//QUESTA CLASSE E' UTILE PER SETTARE IN MANIERA CORRETTA I VALORI DELLA DUE LISTE CREATE DAGLI INSERIMENTI DELL'INSERZIONISTA DA INVIARE AL METODO MODIFICADATIANNUNCIO
class NomieIndiciFoto{
    private List<String> listaNomi;
    private List<Integer> listaIndici;


    public NomieIndiciFoto(List<String> listaNomi, List<Integer> listaIndici) {
        this.listaNomi = listaNomi;
        this.listaIndici = listaIndici;
    }



    public List<String> getListaNomi() {
        return this.listaNomi;
    }

    public void setListaNomi(List<String> listaNomi) {
        this.listaNomi = listaNomi;
    }

    public List<Integer> getListaIndici() {
        return this.listaIndici;
    }

    public void setListaIndici(List<Integer> listaIndici) {
        this.listaIndici = listaIndici;
    }

}
            

            




            