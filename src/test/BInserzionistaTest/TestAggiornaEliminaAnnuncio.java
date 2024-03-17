package test.BInserzionistaTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import org.junit.Before;
import org.junit.Test;

import DAO.AnnuncioDAO;
import DAO.InserzionistaDAO;
import Entity.Annuncio;
import Entity.Stato;
import Entity.Tipologia;
import exception.DAOException;
import exception.DBConnectionException;

public class TestAggiornaEliminaAnnuncio {


    /*
     *     Inserire percorso assoluto del file gpaTesting.mv.db presente nel package test
     */

    //final String urlCompleto = "C:/Users/matte/Dropbox/Il mio PC (LAPTOP-EMJU3OME)/Desktop/università/Ingegneria del software/Progetto/Team11/src/test/gpaTesting";

    //final String urlCompleto = "C:/Users/Luigi Fienga/Desktop/ElaboratoIS/src/test/gpaTesting";

    final String urlCompleto = "C:/Users/admin/Desktop/ElaboratoIS/src/test/gpaTesting";



   /*
    * setDB() setta il database fittizio per effetuare i vari casi di test, è necessario cambiare ogni volta l'urlCompleto con
    * l'url assoluto del dispositivo
    */

    @Before
    public void setDB()
    {

        Statement statement = null;
    

        Connection conn = null;

        try{

            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");

            try{

                final String QUERY_RESET3 = "DELETE FROM annuncio;";
                final String QUERY_RESET1 = "DELETE FROM inserzionista;";
                final String QUERY_RESET2 = "DELETE FROM contatto;";

                final String QUERY_SQL1 = "INSERT INTO inserzionista VALUES ('100001','paolo.limiti','caricatore11');";
                final String QUERY_SQL2 = "INSERT INTO contatto VALUES ('paolo.limiti@gmail.com','3392145620','100001',NULL);";

                final String QUERY_SQL3 = "INSERT INTO Annuncio VALUES ('400001',CURRENT_DATE,'Somma Vesuviana','80049','126','7','Quadrilocale, Via Aldo Moro. Appartamento a primo Piano.','1000','AFFITTO','NUOVO','100001');";


                statement = conn.createStatement();

                statement.executeUpdate(QUERY_RESET2);
                statement.executeUpdate(QUERY_RESET3);
                statement.executeUpdate(QUERY_RESET1);

                statement.executeUpdate(QUERY_SQL1);
                statement.executeUpdate(QUERY_SQL2);
                statement.executeUpdate(QUERY_SQL3);

            } catch(SQLException e){
                e.printStackTrace();
            } finally{
                statement.close();
                conn.close();
            }

        } catch(SQLException e){
        e.printStackTrace();
    }
        
    }


    @Test
    public void testAggiornaAnnuncio()
    {    

        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");
            String actualidA = "400001";
            String actualidI = "100001";
            String actualpsw = "caricatore11";
            int num = 0;

            try
            {
                num = InserzionistaDAO.checkPresenzaInserzionista(conn,Long.parseLong(actualidI), actualpsw);
            }catch(DBConnectionException | DAOException e)
            {
                e.printStackTrace();
            }

            int actual = num;
            int expected = 1;

            assertEquals("Autenticazione fallita dell'inserzionista", expected, actual);

            Annuncio a = null;

            try
            {
                a = AnnuncioDAO.readAnnuncioPerIDs(conn, Long.parseLong(actualidA), Long.parseLong(actualidI));
            }catch(DBConnectionException | DAOException e)
            {
                e.printStackTrace();
            }

            Tipologia expectedTipologia = Tipologia.AFFITTO;
            String expectedcitta = "Somma Vesuviana";
            Double expectednr_m2 = 126D;
            Integer expectedNR_VANI = 7;
            Stato expectedStato = Stato.NUOVO;
            String expecteddescrizione = "Quadrilocale, Via Aldo Moro. Appartamento a primo Piano.";
            Double expectedprezzo = 1000D;
            String expectedCAP = "80049";
           



            Tipologia actualTipologia = a.getTipologia();
            String actualcitta = a.getCittà();
            Double actualnr_m2 = a.getNumeroMetriQuadri();
            Integer actualNR_VANI = a.getNumeroVani();
            Stato actualStato = a.getStato();
            String actualdescrizione = a.getDescrizione();
            Double actualprezzo = a.getPrezzo();
            String actualCAP = a.getCap();
            

            assertEquals("Tipologia differente", expectedTipologia, actualTipologia);
            assertEquals("Città differente", expectedcitta, actualcitta);
            assertEquals("NR_M2 differente", expectednr_m2, actualnr_m2);
            assertEquals("NR_VANI differente", expectedNR_VANI, actualNR_VANI);
            assertEquals("Stato differente", expectedStato, actualStato);
            assertEquals("Descrizione differente", expecteddescrizione, actualdescrizione);
            assertEquals("Prezzo differente", expectedprezzo, actualprezzo);
            assertEquals("CAP differente", expectedCAP, actualCAP);

           
           Tipologia nTipologia = Tipologia.VENDITA;
           Double nNR_M2 = 212.5D;
           Double nPrezzo = 400000.0D;


           try{
                AnnuncioDAO.updateAnnuncio(conn,Long.parseLong(actualidA), nTipologia, actualcitta, actualCAP, nNR_M2, actualNR_VANI, actualStato, actualdescrizione, nPrezzo);
           }catch(DAOException | DBConnectionException e)
           {
            e.printStackTrace();
           }

           Annuncio a2 = null;
            try{
                a2 = AnnuncioDAO.readAnnuncioPerIDs(conn, Long.parseLong(actualidA), Long.parseLong(actualidI));
            }catch(DBConnectionException | DAOException e)
            {
                e.printStackTrace();
            }

            Tipologia actualTipologia2 = a2.getTipologia();
            Double actualNR_M22 = a2.getNumeroMetriQuadri();
            Double actualPrezzo2 = a2.getPrezzo();

            assertEquals("Tipologia differente", actualTipologia2, nTipologia);
            assertEquals("Prezzo differente", actualPrezzo2, nPrezzo);
            assertEquals("NR_M2 differente", actualNR_M22, nNR_M2);


            conn.close();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }



    }


    @Test
    public void testEliminaAnnuncio(){

        Connection conn = null;

        try{

            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");

            String idA = "400001";
            String idI = "100001";
            String psw = "caricatore11";
            int num = 0;
            Annuncio annuncio = null;

            try
            {
                num = InserzionistaDAO.checkPresenzaInserzionista(conn,Long.parseLong(idI), psw);
            }catch(DBConnectionException | DAOException e)
            {
                e.printStackTrace();
            }

            int actual = num;
            int expected = 1;

            assertEquals("Autenticazione fallita dell'inserzionista", expected, actual);

            try {
                AnnuncioDAO.deleteAnnuncio(conn, Long.parseLong(idA));
            } catch (NumberFormatException | DBConnectionException | DAOException e) {
                e.printStackTrace();
            }


            try {
                AnnuncioDAO.readAnnuncioPerIDs(conn, Long.parseLong(idA), Long.parseLong(idI));
            } catch (NumberFormatException | DAOException | DBConnectionException e) {
                e.printStackTrace();
            }

            assertTrue("Annuncio ancora presente", annuncio==null);


            conn.close();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }



    }

    
    @Test
    public void testAutenticazioneInserzionista()
    {
        Connection conn = null;

        try{
            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");
            
            String actualidI = "100001";
            String actualpsw = "caricatore10";
            int num = 0;

            try
            {
                num = InserzionistaDAO.checkPresenzaInserzionista(conn,Long.parseLong(actualidI), actualpsw);
            }catch(DBConnectionException | DAOException e)
            {
                e.printStackTrace();
            }

            int actual = num;
            int expected = 0;

            assertEquals("Autenticazione riuscita dell'inserzionista", expected, actual);

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void testAnnuncioNonAssociatoInserzionista()
    {
        Connection conn = null;

        try{
            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");

            String actualidA = "400003";
            String actualidI = "100001";
            String actualpsw = "caricatore11";
            int num = 0;

            try
            {
                num = InserzionistaDAO.checkPresenzaInserzionista(conn,Long.parseLong(actualidI), actualpsw);
            }catch(DBConnectionException | DAOException e)
            {
                e.printStackTrace();
            }

            int actual = num;
            int expected = 1;

            assertEquals("Autenticazione fallita dell'inserzionista", expected, actual);

            Annuncio a = null;

            try
            {
                a = AnnuncioDAO.readAnnuncioPerIDs(conn, Long.parseLong(actualidA), Long.parseLong(actualidI));
            }catch(DBConnectionException | DAOException e)
            {
                e.printStackTrace();
            }            

            assertTrue("Annnucio associato all'inserzionista", a==null);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }    
}
