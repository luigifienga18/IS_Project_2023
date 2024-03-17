package test.BUtenteTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import DAO.*;
import Entity.Annuncio;
import Entity.Contatto;
import Entity.Tipologia;
import exception.*;


public class TestRicercaAnnunci {

    /*
     *     Inserire percorso assoluto del file gpaTesting.mv.db presente nel package test
     */

    //final String urlCompleto = "C:/Users/matte/Dropbox/Il mio PC (LAPTOP-EMJU3OME)/Desktop/università/Ingegneria del software/Progetto/Team11/src/test/gpaTesting";

    //final String urlCompleto = "C:/Users/Luigi Fienga/Desktop/ElaboratoIS/src/test/gpaTesting";

    final String urlCompleto = "C:/Users/admin/Desktop/ElaboratoIS/src/test/gpaTesting";

    @Before
    public void setDBRicerca(){

        Statement statement = null;
    

        Connection conn = null;

        try{

            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");

            try{

                final String QUERY_RESET3 = "DELETE FROM annuncio;";
                final String QUERY_RESET1 = "DELETE FROM inserzionista;";
                final String QUERY_RESET2 = "DELETE FROM contatto;";
                final String QUERY_RESET4 = "DELETE FROM utenteregistrato;";
                final String QUERY_RESET5 = "DROP SEQUENCE SEQUENZA_UTENTEREGISTRATO;";



                final String QUERY_SQL1 = "INSERT INTO inserzionista VALUES ('100001','paolo.limiti','caricatore11');";
                final String QUERY_SQL2 = "INSERT INTO contatto VALUES ('paolo.limiti@gmail.com','3392145620','100001',NULL);";
                
                final String QUERY_SQL3 = "INSERT INTO Annuncio VALUES ('400001',CURRENT_DATE,'Torre Annunziata','80058','125','7','Quadrilocale, Via Gino Alfani 45. Appartamento vista mare Ottavo piano con ascensore.','800','AFFITTO','RISTRUTTURATO','100001');";
                
                final String QUERY_SQL4 = "INSERT INTO utenteregistrato VALUES (NEXTVAL('SEQUENZA_UTENTEREGISTRATO'),'Luigi','Fienga',NULL);";
                final String QUERY_SQL5 = "INSERT INTO contatto VALUES ('luigi.fienga18@gmail.com','3298508761',NULL ,'200001');";
                final String QUERY_SQL6 = "CREATE SEQUENCE sequenza_utenteregistrato INCREMENT BY 1 START WITH 200001 MAXVALUE 299999 NOCYCLE NOCACHE;";


                statement = conn.createStatement();

                statement.executeUpdate(QUERY_RESET2);
                statement.executeUpdate(QUERY_RESET3);
                statement.executeUpdate(QUERY_RESET1);
                statement.executeUpdate(QUERY_RESET4);
                statement.executeUpdate(QUERY_RESET5);

                statement.executeUpdate(QUERY_SQL1);
                statement.executeUpdate(QUERY_SQL2);
                statement.executeUpdate(QUERY_SQL3);

                statement.executeUpdate(QUERY_SQL6);
                statement.executeUpdate(QUERY_SQL4);
                statement.executeUpdate(QUERY_SQL5);



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
    public void testRicercaAnnunciConEmail(){


        Annuncio annuncio = new Annuncio();
        Contatto contattour = null;
        Contatto contattoI = null;

        List<Annuncio> lA = new ArrayList<>();

        Connection conn = null;
        try{
        
            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");

            String tipologia = String.valueOf(Tipologia.AFFITTO);
            String citta = "Torre Annunziata";
            String cap = "80058";
            String numVani = String.valueOf(7);
        
            Long idIns = 100001L;

            annuncio.setCittà(citta);
            annuncio.setCap(cap);
            annuncio.setNumeroVani(7);
            annuncio.setTipologia(Tipologia.AFFITTO);


            try {
                lA = AnnuncioDAO.readAnnunciRicerca(conn,tipologia, citta, cap, numVani);
            } catch (DBConnectionException | DAOException e) {
                e.printStackTrace();
            }

            Tipologia expectedTipologia = annuncio.getTipologia();
            String expectedCap = annuncio.getCap();
            String expectedCitta = annuncio.getCittà();
            int expectedNumVani = annuncio.getNumeroVani();

            Tipologia actualTipologia = lA.get(0).getTipologia();
            String actualCap = lA.get(0).getCap();
            String actualCitta = lA.get(0).getCittà();
            int actualNumVani = lA.get(0).getNumeroVani();


            assertEquals("Tipologia diversa", expectedTipologia, actualTipologia);
            assertEquals("Citta diversa", expectedCitta, actualCitta);
            assertEquals("CAP diverso", expectedCap, actualCap);
            assertEquals("Numero di vani diverso", expectedNumVani, actualNumVani);

            Long idUR = 200001L;
        
            try {
                    contattour = ContattoDAO.readContatto(conn, idUR);
            } catch (DAOException e) {
                e.printStackTrace();
            }

            assertTrue("Utente non registrato", contattour!=null);

            try {
                    contattoI = ContattoDAO.readContatto(conn, idIns);
            } catch (DAOException  e) {
                e.printStackTrace();
            }

            assertTrue("Inserzionista non registrato", contattoI!=null);


            String excpectedEmail = "paolo.limiti@gmail.com";
            String expectedCellulare = "3392145620";


            String actualEmail = contattoI.getEmail();
            String actualCellulare = contattoI.getCellulare();

            assertEquals("Email differenti", excpectedEmail, actualEmail);
            assertEquals("Numeri di cellulare differenti", expectedCellulare, actualCellulare);


            conn.close();
        
    } catch(SQLException e){
        e.printStackTrace();
    }

    }


    @Test
    public void testRicercaAnnunciSenzaEmail(){
    
        Annuncio annuncio = new Annuncio();
        Contatto contatto = null;

        List<Annuncio> lA = new ArrayList<>();

        Connection conn = null;
        try{
        
            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");

            String tipologia = String.valueOf(Tipologia.AFFITTO);
            String citta = "Torre Annunziata";
            String cap = "80058";
            String numVani = String.valueOf(7);
        
            Long idIns = 100001L;

            annuncio.setCittà(citta);
            annuncio.setCap(cap);
            annuncio.setNumeroVani(7);
            annuncio.setTipologia(Tipologia.AFFITTO);


            try {
                lA = AnnuncioDAO.readAnnunciRicerca(conn,tipologia, citta, cap, numVani);
            } catch (DBConnectionException | DAOException e) {
                e.printStackTrace();
            }

            Tipologia expectedTipologia = annuncio.getTipologia();
            String expectedCap = annuncio.getCap();
            String expectedCitta = annuncio.getCittà();
            int expectedNumVani = annuncio.getNumeroVani();

            Tipologia actualTipologia = lA.get(0).getTipologia();
            String actualCap = lA.get(0).getCap();
            String actualCitta = lA.get(0).getCittà();
            int actualNumVani = lA.get(0).getNumeroVani();


            assertEquals("Tipologia diversa", expectedTipologia, actualTipologia);
            assertEquals("Citta diversa", expectedCitta, actualCitta);
            assertEquals("CAP diverso", expectedCap, actualCap);
            assertEquals("Numero di vani diverso", expectedNumVani, actualNumVani);

        
            try {
                    contatto = ContattoDAO.readContatto(conn, idIns);
            } catch (DAOException  e) {
                e.printStackTrace();
            }

            assertTrue("Inserzionista non registrato", contatto!=null);

            String excpectedEmail = "paolo.limiti@gmail.com";
            String expectedCellulare = "3392145620";


            String actualEmail = contatto.getEmail();
            String actualCellulare = contatto.getCellulare();

            assertEquals("Email differenti", excpectedEmail, actualEmail);
            assertEquals("Numeri di cellulare differenti", expectedCellulare, actualCellulare);


            conn.close();
        
    } catch(SQLException e){
        e.printStackTrace();
    }

}



@Test
public void testRicercaAnnunciSenzaAnnunci(){   

    Annuncio annuncio = new Annuncio();

    List<Annuncio> lA = new ArrayList<>();

    Connection conn = null;

    try{
        
        conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");

        String tipologia = String.valueOf(Tipologia.VENDITA);
        String citta = "aaaaa";
        String cap = "00000";
        String numVani = String.valueOf(2);

        annuncio.setCittà(citta);
        annuncio.setCap(cap);
        annuncio.setNumeroVani(2);
        annuncio.setTipologia(Tipologia.AFFITTO);


        try {
            lA = AnnuncioDAO.readAnnunciRicerca(conn,tipologia, citta, cap, numVani);
        } catch (DBConnectionException | DAOException e) {
            e.printStackTrace();
        }


        assertTrue("E' stato trovato un annuncio!", lA.size()==0);

        conn.close();

    }catch(SQLException e)
    {
        e.printStackTrace();
    }



}


}
