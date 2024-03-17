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

public class TestCheckPresenzaContatto {
    
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

                final String QUERY_SQL5 = "INSERT INTO utenteregistrato VALUES (NEXTVAL('SEQUENZA_UTENTEREGISTRATO'),'Gabriele','Mangiacapre',NULL);";
                final String QUERY_SQL6 = "INSERT INTO contatto VALUES ('gabriele.mangiacapre11@gmail.com','3291163612',NULL ,'200001');";


                statement = conn.createStatement();
                statement.executeUpdate(QUERY_SQL1);
                statement.executeUpdate(QUERY_SQL2);
                statement.executeUpdate(QUERY_SQL3);
                statement.executeUpdate(QUERY_SQL4);
                statement.executeUpdate(QUERY_SQL5);
                statement.executeUpdate(QUERY_SQL6);



                

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
    public void testReadContattoUR(){

        Connection conn = null;
        try{

            conn = DriverManager.getConnection("jdbc:h2:"+urlCompleto, "sa", "");
        } catch(SQLException e){
            e.printStackTrace();
        }


        String email = "gabriele.mangiacapre11@gmail.com";
        String cellulare = "3291163612";

        int num = 0;

        try {
            num = ContattoDAO.checkPresenzaContatto(conn, email, cellulare);
        } catch (DAOException | DBConnectionException e) {
            e.printStackTrace();
        }

        int actual = num;

        int expected = 1;

        assertEquals("Contatto non presente nel database", expected, actual);


        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
