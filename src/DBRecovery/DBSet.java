package DBRecovery;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Control.GestorePiattaformaAnnunci;
import DAO.DBManager;
import DAO.FotografiaDAO;
import exception.DAOException;
import exception.DBConnectionException;
import exception.RegistrationException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DBSet {
    
    public static void main(String [] argc)
    {
        boolean input = true;
        Scanner scan = new Scanner(System.in);
        String [] aS = new String[] {"QuadrilocaleTA", "TrilocaleP", "QuadrilocaleSV","TrilocaleMdN", "BilocaleNA"};
        Integer [] aI = new Integer [] {8,6,7,5,8};


        while(input)
        {
            System.out.println("Ciao! Cosa devi fare?");
            System.out.println("1) SET DB\n2) POPOLAMENTO DB \n3) ERASE DB \n4) EXIT");

            try{

                //C:/Users/admin/Desktop/ElaboratoIS/src/DBRecovery
                String sceltaUIString = scan.nextLine();
                String path = "./Input/";
                String sqlScriptListatoPath = "./src/DBRecovery/LISTATO.SQL";
                String sqlScriptInsertPath = "./src/DBRecovery/INSERT.SQL";
                String sqlScriptDroptPath = "./src/DBRecovery/DROP.SQL";

                
                switch(sceltaUIString)
                {
                    case "1":

                        try (Connection conn = DBManager.getConnection();
                            Statement statement = conn.createStatement();
                            BufferedReader reader = new BufferedReader(new FileReader(sqlScriptListatoPath))) {
       
                            StringBuilder scriptBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                scriptBuilder.append(line);
                                scriptBuilder.append("\n");
                            }
       
                            String sqlScript = scriptBuilder.toString();
                            statement.executeUpdate(sqlScript);
                            System.out.println("Script eseguito con successo.");
       
                        } catch (Exception e) {
                            System.err.println("Database già settato, esegui un'altra operazione.");
                        } finally{
                            try{
                                DBManager.closeConnection();
                            }catch(SQLException e){
                            System.out.println("ERRORE");
                        }
                    }

                    break;

                    case "2":
                        try (Connection conn = DBManager.getConnection();
                            Statement statement = conn.createStatement();
                            BufferedReader reader = new BufferedReader(new FileReader(sqlScriptInsertPath))) {
       
                            StringBuilder scriptBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                scriptBuilder.append(line);
                                scriptBuilder.append("\n");
                            }
       
                            String sqlScript = scriptBuilder.toString();
                            statement.executeUpdate(sqlScript);

                            for(int i=0;i<5;i++)
                            {
                                Long id = 400001L;
                                for(int s=0; s<aI[i];s++)
                                {
                                    Integer h = s+1;
                                    try
                                    {
                                        String pathdef = path + aS[i] + h.toString() + ".jpg";
                                        InputStream in = new FileInputStream(pathdef);
                                    
                                        FotografiaDAO.createFotografia(in, (id+i));
                                    
                                    }catch(FileNotFoundException e){
                                    System.out.println("ERRORE" + e.getMessage());
                                    }catch(DBConnectionException | DAOException e){
                                    System.out.println("ERRORE"+ e.getMessage());
                                    }
                                }
                        
                            }

                            try
                            {
                                GestorePiattaformaAnnunci gpa = GestorePiattaformaAnnunci.getInstance();

                                gpa.registrazioneUtente("Luigi","Fienga","3298508761","luigi.fienga18@gmail.com");
                                gpa.registrazioneUtente("Matteo","Davino","3298508751","mattavino@gmail.com");
                                gpa.registrazioneUtente("Camilla","Dandria","3298508741","camiandria@gmail.com");
                                gpa.registrazioneUtente("Gabriele","Mangiacapre","3298508731","gabrmang@gmail.com");
                        
                            }catch(RegistrationException e){
                            System.out.println("ERRORE");
                            }


                            System.out.println("Script eseguito con successo.\n");
       
                        } catch (Exception e) {
                            System.err.println("Database già popolato, esegui un' altra operazione.");
                        }finally{
                            try{
                                DBManager.closeConnection();
                            }catch(SQLException e){
                            System.out.println("ERRORE");
                            }
                        }

                    

                    break;
                    
                    case "3":
                        try (Connection conn = DBManager.getConnection();
                        Statement statement = conn.createStatement();
                        BufferedReader reader = new BufferedReader(new FileReader(sqlScriptDroptPath))) {
       
                            StringBuilder scriptBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                scriptBuilder.append(line);
                                scriptBuilder.append("\n");
                            }
       
                            String sqlScript = scriptBuilder.toString();
                            statement.executeUpdate(sqlScript);
                            System.out.println("Script eseguito con successo.\n");
       
                        } catch (Exception e) {
                            System.err.println("Database già vuoto, esegui un'altra operazione");
                        }

                    break;

                    case "4": input = false; scan.close(); break;

                    default: System.out.println("Valore inserito non valido, riprovare..."); 
                             break;
                }


            }catch(NoSuchElementException e){
                System.out.println("Exception: Valore inserito non valido, riprovare..."); 
                }

        }


    }
    
}
