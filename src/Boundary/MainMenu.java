package Boundary;


import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;


public class MainMenu {


    public static void main(String [] argc) {

        boolean input = true;
        Scanner scan = new Scanner(System.in);
        boolean inputUtente, inputInserzionista;
        String op = null;
        String path = "./Output";

        File cartella = new File(path);

        if (!cartella.exists()) {
            cartella.mkdirs();
        }
        
        while (input){

            System.out.println("\n-----Benvenuto in Immobiliare T11-----");
            System.out.println("\nChi sei? (Digita '1', '2' o '3')");
            System.out.println("1) UTENTE\n2) INSERZIONISTA\n3) ESCI");

       
            try{
                String sceltaUIString = scan.nextLine();
                
                switch(sceltaUIString){
                   

                    case "1":

                            inputUtente = false;
                        
                            while (!inputUtente){
                
                                try{
                
                                    System.out.println("\nQuale operazione vuoi eseguire? (Digita il numero alla sinistra dell'operazione oppure '0' per tornare indietro)");
                                    System.out.println("Per le ultime due funzionalità c'è bisogno che tu sia registrato\n");
                                    System.out.println("1) REGISTRAZIONE" + "\n"+
                                                       "2) RICERCA ANNUNCI"+ "\n"+
                                                       "3) ISCRIZIONE NEWSLETTER"+"\n"+
                                                       "4) DISISCRIZIONE NEWSLETTER");
                                
                                    op = scan.nextLine();  
                                    switch(op){
                                        case "0": 
                                            inputUtente = true;
                                            break;
                
                                        case "1": 
                                            BUtente.registrazioneUtente();
                                            inputUtente = true;
                                            break;
                
                                        case "2":
                                            BUtente.ricercaAnnunci();
                                            inputUtente = true;
                                            break;

                                        case "3": System.out.println("\nSiamo desolati, al momento questa funzionalità non è disponibile"); break;

                                        case "4": System.out.println("\nSiamo desolati, al momento questa funzionalità non è disponibile"); break;
                
                                        default: System.out.println("\nValore inserito non valido, riprova...");
                
                                }
                
                                } catch(NoSuchElementException e){
                                    System.out.println("Exception: Valore inserito non valido, riprovare...");
                                }
                
                        }
                    
            
                        break;

                    case "2":

                        inputInserzionista = false;

                        while (!inputInserzionista){

                            try{
            
                            System.out.println("\nQuale operazione vuoi eseguire? (Digita il numero alla sinistra dell'operazione oppure '0' per tornare indietro)\n");
                            System.out.println("1) REGISTRAZIONE" + "\n"+
                                               "2) PUBBLICA ANNUNCIO" + "\n"+
                                               "3) AGGIORNA ANNUNCIO"+ "\n"+
                                               "4) ELIMINA ANNUNCIO"+ "\n"+
                                               "5) VISUALIZZA ANNUNCI");
                            
                            op = scan.nextLine();  
                            switch(op){
                                case "0": 
                                    inputInserzionista = true;
                                    break;

                                case "1": System.out.println("\nSiamo desolati, al momento questa funzionalità non è disponibile"); break;
                                
                                case "2": System.out.println("\nSiamo desolati, al momento questa funzionalità non è disponibile"); break;

                                case "3": 
                                    BInserzionista.aggiornaAnnuncio();
                                    inputInserzionista = true;
                                    break;
            
                                case "4":
                                    BInserzionista.eliminaAnnuncio();
                                    inputInserzionista = true;
                                    break;
                                
                                case "5" : System.out.println("\nSiamo desolati, al momento questa funzionalità non è disponibile"); break;
            
                                default: System.out.println("\nValore inserito non valido, riprova...");
            
                            }
            
                        } catch(NoSuchElementException e){
                            System.out.println("Exception: Valore inserito non valido, riprovare...");
                        }
            
            
                    }
                        break;

                    case "3": input = false; scan.close(); break;

                    default: System.out.println("\nValore inserito non valido, riprovare..."); 
                             break;
                    

                }


            } catch(NoSuchElementException e){
                System.out.println("Exception: Valore inserito non valido, riprovare...");
            }

        }


    }



}
    