package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import Entity.*;
import exception.DAOException;
import exception.DBConnectionException;

public class ContattoDAO {

    public static int checkPresenzaContatto (Connection conn, String email, String cellulare) throws DAOException, DBConnectionException{
        int num = 0;
        PreparedStatement preparedStatement = null;
        Connection contr = conn;
        try{
            if (conn == null)
                conn = DBManager.getConnection();
        
        try{
    		final String QUERY_SQL = "SELECT COUNT(*) FROM Contatto WHERE cellulare = ? OR email = ?;" ;
            preparedStatement = conn.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1, cellulare);
    		preparedStatement.setString(2, email);
            ResultSet result = preparedStatement.executeQuery();

            if(result.next()) {
                num = result.getInt(1);
            }
            
        }catch(SQLException e){
            throw new DAOException("ERRORE nella ricerca dei Contatti : checkPresenzaContatto"+ e.toString());
        }finally{
            if(preparedStatement != null){
                silentlyClose(preparedStatement);
            }         
            
            if (contr == null)
                DBManager.closeConnection();
        }

    }catch(SQLException e){
        throw new DBConnectionException("errore connessione al DB");
    }

        return num;
    }

    
   

    public static Contatto readContatto (Connection conn, Long ID) throws DAOException{

        

        PreparedStatement preparedStatement = null;
        Contatto contatto = null;
        String firstDigit = String.valueOf(ID).substring(0,1);

        try{
            
            String Q_SQL = null;
            if(firstDigit.equals("1"))
                Q_SQL = "SELECT * FROM CONTATTO WHERE idinserzionista = ?;"; 
            else if(firstDigit.equals("2"))
                Q_SQL = "SELECT * FROM CONTATTO WHERE idutenteregistrato = ?;";
            
            final String QUERY_SQL = Q_SQL;
            preparedStatement = conn.prepareStatement(QUERY_SQL);

            preparedStatement.setLong(1, ID);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()){
                contatto = new Contatto (result.getString(1), result.getString(2), result.getLong(4), result.getLong(3));

            }

            } catch (SQLException e){
                throw new DAOException("ERRORE ricerca contatto : readContatto");
            } finally{
                if (preparedStatement != null){
                    silentlyClose(preparedStatement);
                }
            }
        

        return contatto;


    }

    public static Contatto readContatto (Long ID) throws DAOException{

 
        PreparedStatement preparedStatement = null;
        Contatto contatto = null;
        String firstDigit = String.valueOf(ID).substring(0,1);

        try{

            Connection conn = DBManager.getConnection();

            try{
            
            String Q_SQL = null;
            if(firstDigit.equals("1"))
                Q_SQL = "SELECT * FROM CONTATTO WHERE idinserzionista = ?;"; 
            else if(firstDigit.equals("2"))
                Q_SQL = "SELECT * FROM CONTATTO WHERE idutenteregistrato = ?;";
            
            final String QUERY_SQL = Q_SQL;
            preparedStatement = conn.prepareStatement(QUERY_SQL);

            preparedStatement.setLong(1, ID);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()){
                contatto = new Contatto (result.getString(1), result.getString(2), result.getLong(4), result.getLong(3));

            }

            } catch (SQLException e){
                throw new DAOException("ERRORE ricerca contatto : readContatto");
            } finally{
                if (preparedStatement != null){
                    silentlyClose(preparedStatement);
                }
            }

        } catch(SQLException dbE){
            System.out.println("errore connessione al DB");

        }
        

        return contatto;


    }

    

 



    public static void createContattoUR (Connection conn, String email, String cellulare, Long IDUR) throws DAOException, DBConnectionException{
        PreparedStatement preparedStatement = null; 
        Connection contr = conn;
    
        try{
            if (conn == null)
                conn = DBManager.getConnection();
    
            try{
                final String QUERY_SQL = "INSERT INTO Contatto (email, cellulare, idutenteregistrato) VALUES (?, ?, ?);" ;
                preparedStatement = conn.prepareStatement(QUERY_SQL);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, cellulare);
                preparedStatement.setLong(3, IDUR);

                preparedStatement.executeUpdate();
            }catch(SQLException e){
                throw new DAOException("ERRORE nell'inserimento del Contatto nell base dati! : createContattoUR", e);
            } finally {
                if(preparedStatement != null){
                    silentlyClose(preparedStatement);
                }
                if (contr == null)
                    DBManager.closeConnection();

            }
        } catch(SQLException e){
            throw new DBConnectionException("errore connessione al DB");
        }
    }




    public static Contatto readContattoUR (Connection conn, String email) throws DAOException, DBConnectionException {
        PreparedStatement preparedStatement = null;   
        Entity.Contatto contatto= null;
        Connection contr = conn;

        try{
            if (conn == null)
                conn = DBManager.getConnection();

            try{
                final String QUERY_SQL = "SELECT * FROM Contatto WHERE email = ? AND idinserzionista IS NULL;" ;
                preparedStatement = conn.prepareStatement(QUERY_SQL);

                preparedStatement.setString(1, email);

                ResultSet result = preparedStatement.executeQuery();

                if(result.next()) {
                    contatto = new Contatto(result.getString(1),result.getString(2),result.getLong(4),result.getLong(3));
            }
            }catch(SQLException e){
                throw new DAOException("ERRORE ricerca contatto : readContatto ", e);
            }finally{
                if(preparedStatement != null){
                    silentlyClose(preparedStatement);
                }

            if (contr == null)
                DBManager.closeConnection();        
            }

        }catch(SQLException e){
            throw new DBConnectionException("errore connessione al DB");
        }
    
    return contatto;
    
}

 

    private static void silentlyClose(Statement statement) {
    	try {
    		statement.close();
    	} catch (SQLException e) {
            e.printStackTrace();
    	}
    }   


}
