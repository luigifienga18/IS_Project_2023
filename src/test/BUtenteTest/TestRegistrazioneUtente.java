package test.BUtenteTest;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import DAO.*;
import exception.*;

public class TestRegistrazioneUtente {

    /*
     *     Inserire percorso assoluto del file gpaTesting.mv.db presente nel package test
     */


    //final String urlCompleto = "C:/Users/matte/Dropbox/Il mio PC (LAPTOP-EMJU3OME)/Desktop/università/Ingegneria del software/Progetto/Team11/src/test/gpaTesting";
    //final String urlCompleto = "C:/Users/Luigi Fienga/Desktop/ElaboratoIS/src/test/gpaTesting";
    final String urlCompleto = "C:/Users/admin/Desktop/ElaboratoIS/src/test/gpaTesting";

    @Before
    public void setDBUR (){

        Statement statement = null;
             

        Connection conn = null;
        try{
            
            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");


            try{

                final String QUERY_SQL1 = "DELETE FROM contatto WHERE idinserzionista IS NULL;";
                final String QUERY_SQL2 = "DELETE FROM utenteregistrato;";
                final String QUERY_SQL3 = "DROP SEQUENCE SEQUENZA_UTENTEREGISTRATO;";
                final String QUERY_SQL4 = "CREATE SEQUENCE sequenza_utenteregistrato INCREMENT BY 1 START WITH 200001 MAXVALUE 299999 NOCYCLE NOCACHE;";

                statement = conn.createStatement();

                statement.executeUpdate(QUERY_SQL1);
                statement.executeUpdate(QUERY_SQL2);
                statement.executeUpdate(QUERY_SQL3);
                statement.executeUpdate(QUERY_SQL4);

        } catch (SQLException e) {
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
    public void testRegistrazioneUtente(){

        Connection conn = null;
        try{
        
            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");

            String nome = "Gabriele";
            String cognome = "Mangiacapre";
            String email = "gabriele.mangiacapre11@gmail.com";
            String cellulare = "3291163612";

            Long idUR = 0L;

        try {
            idUR = UtenteRegistratoDAO.createUtenteRegistrato(conn, nome, cognome);

        } catch (DBConnectionException | DAOException e) {

            e.printStackTrace();
        }

        long expected = idUR;


        long actual = 0L;
        try {
            actual = UtenteRegistratoDAO.readUtenteRegistrato(conn, idUR).getIdUtenteRegistrato();
        } catch (DBConnectionException | DAOException e) {

            e.printStackTrace();
        }


        assertEquals("IDUR non corretto", expected, actual);


        try{
            ContattoDAO.createContattoUR(conn, email, cellulare, idUR);
        } catch(DAOException | DBConnectionException e){
            e.printStackTrace();
        }

        long attuale = 0L;

        try {
            attuale = ContattoDAO.readContattoUR(conn, email).getIDutenteregistrato();
        } catch (DAOException | DBConnectionException e) {
            e.printStackTrace();
        }

        assertEquals("Contatto non creato correttamente", expected, attuale);

        conn.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}









}
