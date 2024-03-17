package Boundary;

import Entity.*;
import Utility.DatiInserzionistaAnnuncio;

import exception.OperationException;
import exception.RegistrationException;
import Control.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;





public class BUtente {

    static Scanner scan = new Scanner(System.in);
    final static int CAP_LENGTH = 5;
    final static int ID_LENGTH = 6;
    final static int SIZE_LISTA_FOTOGRAFIE = 8;
    final static String FIRST_PART_PATH = "./Input/";
    final static Character APICES = '"';
    final static Character DAPICE = (char) 34;  



    public static void ricercaAnnunci()
{
    GestorePiattaformaAnnunci gestorePiattaformaAnnunci = GestorePiattaformaAnnunci.getInstance(); //gpa is singleton;   

    boolean inputvalido = false;

    Tipologia tipologia = null;
    String citta = null, cap = null, email = null;
    Integer numeroVani = 0;

    Pattern caratteriSpeciali = Pattern.compile("[^a-zA-Z ]");
    Matcher match;
    boolean check;


    System.out.println("\n--------RICERCA ANNUNCI--------\n");
    System.out.println("Inserisci i parametri di ricerca (Inserisci 'n' se non vuoi specificare un parametro)");
        
    while(!inputvalido)
    {
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

    while(!inputvalido)
    {
        System.out.println("\nInserisci la città: ");
        citta = scan.nextLine();

        match = caratteriSpeciali.matcher(citta);
        check = match.find();

        if (citta.equals("n"))
        {
            citta = null;
            inputvalido = true;
        }
        else if (check || citta.equals(""))
        {
            System.out.println("\nHai inserito un carattere speciale, riprovare... ");
            inputvalido = false;
        }
        else if(citta.length()>255)
        {
            System.out.println("\nHai inserito una stringa più grande di 255 caratteri, riprovare...");
            inputvalido = false;
        }
        else
        {
            citta = citta.toUpperCase();
            inputvalido = true;
        }
    }

    inputvalido = false;

    while (!inputvalido){
        System.out.println("\nInserisci il CAP (Formato XXXXX): ");
        cap = scan.nextLine();

        if (cap.length()==CAP_LENGTH && cap.matches("\\d+"))
        {
            inputvalido = true;
        } 
        else if (cap.equals("n"))
        {
            cap = null;
            inputvalido = true;
        } 
        else
        {
            System.out.println("\nStringa inserita non valida, riprovare...");
        }
    }

    inputvalido = false;

    while (!inputvalido){

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

    while (!inputvalido) {
        System.out.println("\nInserisci la email (Digita 'n' se non vuoi specificarla): ");

        email = scan.nextLine();

        if (email.contains("@") && email.contains(".")) 
        {
            inputvalido = true;
        }
        else if(email.equals("n"))
        {
            email = null;            
            inputvalido = true;
        }
        else 
        {
            System.out.println("\nEmail non valida, controllare se sono state inserite la @ ed il '.', riprovare....");
        }
    }

    DatiInserzionistaAnnuncio risultato = null;
 
    try 
    {
        risultato = gestorePiattaformaAnnunci.ricercaAnnunci(tipologia, citta, cap, numeroVani, email);        
    } 
    catch (OperationException e) 
    {
        System.out.println("\nQualcosa e' andato storto...");
    }
    
    if(risultato.getListaAnnunci()!=null)
    {
        Boolean p =true; 
            System.out.println(risultato.toString());

            for(Integer i = 0; i<risultato.getListaAnnunci().size();i++){
                
                Integer k = i + 1;
                
                try{
                    gestorePiattaformaAnnunci.conversioneobjFotografie(risultato.getListaAnnunci().get(i).getListaFotografie(), k.toString());
                } catch(IOException e) {
                        p=false;
                        System.out.println("ERRORE nell'operazione di conversione delle foto");
                   }
                
            }
                
            if(p==true){
                System.out.println("Nella cartella Ouput è possibile visualizzare le fotografie");
            }
   
    }
    else{
        System.out.println("\nNon e' stato trovato nessun annuncio con i parametri forniti");
    }
} 
    

public static void registrazioneUtente(){
        GestorePiattaformaAnnunci gestorePiattaformaAnnunci = GestorePiattaformaAnnunci.getInstance();

        String nome = null;
        String cognome = null;
        String cellulare = null;
        String email = null;
        boolean valid = false;
        
        

        Pattern caratteriSpeciali = Pattern.compile("[^a-zA-Z ]");
        Matcher match;
        boolean check;
        
        
        System.out.println("\n--------REGISTRAZIONE UTENTE--------\n");

            while(!valid){

                System.out.println("Nome: ");
                nome = scan.nextLine();
                match = caratteriSpeciali.matcher(nome);
                check = match.find();

                if(nome.equals("n")){
                   
                    valid = true;

                }else if(check || nome.equals("")){
                        System.out.println(("\nHai inserito un carattere speciale, riprovare..."));
                        valid = false;

                } else if(nome.length()>255){
                        System.out.println("\nHai inserito una stringa più grande di 255 caratteri, riprovare...");
                        valid = false;

                }else{
                        valid = true;
                }
                
                }
            

            valid = false;
            while(!valid){

                System.out.println("\nCognome: ");
                cognome = scan.nextLine();
                match = caratteriSpeciali.matcher(cognome);
                check = match.find();

                if(cognome.equals("n")){
                    valid = true;

                }else if(check || cognome.equals("")){
                        System.out.println(("\nHai inserito un carattere speciale, riprovare... "));
                        valid = false;
                    } else if(cognome.length()>255){
                        System.out.println("\nHai inserito una stringa più grande di 255 caratteri, riprovare...");
                        valid = false;
                    }else{
                        valid = true;
                    }
                 }
                

            valid = false;
            while (!valid) {
                    
                System.out.println("\nEmail: ");

                email = scan.nextLine();
            
                if (email.contains("@") && email.contains(".")) 
                {
                    valid = true;

                } else if(email.equals("n")){

                    email = null;            
                    valid = true;

                    } else {
                        System.out.println("\nEmail non valida, controllare se sono state inserite la @ ed il '.', riprovare....");
                    }   
            }

            valid = false;

            while (!valid){
                    
                    System.out.println("\nCellulare: ");

                    cellulare = scan.nextLine();

                    boolean isInteger = true;
                    for (char c : cellulare.toCharArray()) {
                        if (!Character.isDigit(c)) {
                        isInteger = false;
                        break;
                        }
                    }

                    int lunghezza = cellulare.length();

                    if(isInteger && lunghezza==10)
                    { 
                        valid = true;
                    }
                    else
                    {
                        System.out.println("\nNumero di cellulare non valido");
                    } 
                }


                boolean valid_registrazione = false;
                try{    
                    valid_registrazione = gestorePiattaformaAnnunci.registrazioneUtente(nome, cognome, cellulare, email);
                } catch(RegistrationException e){
                    System.out.println("\nRegistrazione Fallita...");
                    }
                    
                    
                if(valid_registrazione == true){
                    System.out.println();
                    System.out.println("\nRegistrazione effettuata con successo!");
                } else{
                    System.out.println();
                    System.out.println("\nIl seguente utente è già registrato!");
                    }

            }
                
    

}
    


