package DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



import java.sql.PreparedStatement;


import java.io.ByteArrayInputStream;

import Entity.*;
import exception.DAOException;
import exception.DBConnectionException;

public class FotografiaDAO {

    public static java.util.List<Fotografia> readFotografia(Connection conn, Long idA) throws SQLException, DAOException
    {
        PreparedStatement preparedStatement = null;
        java.util.List<Fotografia> Lf = new ArrayList<>() ;

        try
        {
    		final String QUERY_SQL = "SELECT * FROM fotografia WHERE idannuncio = ?;";
            preparedStatement = conn.prepareStatement(QUERY_SQL);
    		preparedStatement.setLong(1,idA);
           
            ResultSet result = preparedStatement.executeQuery();
            while(result.next())
            {
                Integer i = (int) (result.getBlob(2).length());

                InputStream in = new ByteArrayInputStream(result.getBlob(2).getBytes(1L, i));

                Fotografia f = new Fotografia(result.getLong(1), result.getLong(3), in);

                Lf.add(f);
                
            }

        }catch(SQLException e){
            throw new DAOException("ERRORE nella lettura fotografie : readFotografia", e);
        } finally {
            if(preparedStatement != null){
                silentlyClose(preparedStatement);
            }
        }

        return Lf;

    } 

    public static void updateFotografia (Long idF,InputStream in) throws  DBConnectionException, DAOException
    {
        PreparedStatement preparedStatement = null;

        try{
            Connection conn = DBManager.getConnection();
        
        try{
    		final String QUERY_SQL = "UPDATE fotografia SET path = ? WHERE idfotografia = ?;";
            preparedStatement = conn.prepareStatement(QUERY_SQL);
    		preparedStatement.setBlob(1,in);
    		preparedStatement.setLong(2,idF);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            throw new DAOException ("ERRORE nell'aggiornament della fotografie : updateFotografia", e);
        }finally {
            if(preparedStatement != null){
                silentlyClose(preparedStatement);
            }
            DBManager.closeConnection();
        }

    }catch (SQLException e){
        throw new DBConnectionException("errore connessione al DB");
    }
    }


    

    public static void deleteFotografia (Long id) throws  DBConnectionException, DAOException
    {
        PreparedStatement preparedStatement = null;
        String firstDigit = String.valueOf(id).substring(0,1);

        try{
            Connection conn = DBManager.getConnection();
        
        try
        {
            String Q_SQL = null;
            
            if(firstDigit.equals("5"))
                Q_SQL = "DELETE FROM fotografia WHERE idfotografia = ?;"; 
            else if(firstDigit.equals("4"))
                Q_SQL = "DELETE FROM fotografia WHERE idannuncio = ?;";

                final String QUERY_SQL = Q_SQL;

            preparedStatement = conn.prepareStatement(QUERY_SQL);
    		preparedStatement.setLong(1,id);           
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DAOException("ERRORE nella cancellazione della fotografie : deleteFotografia", e);
        } finally {
            if(preparedStatement != null){
                silentlyClose(preparedStatement);
            }
            DBManager.closeConnection();
        }

    }catch(SQLException e){
        throw new DBConnectionException("errore connessione al DB");
    }
    }

    




    public static void createFotografia (InputStream in, Long idAnnuncio) throws DBConnectionException, DAOException
    {   
        PreparedStatement preparedStatement = null;

        try{

            Connection conn = DBManager.getConnection();

        try{
    		final String QUERY_SQL = "INSERT INTO Fotografia VALUES (NEXTVAL('SEQUENZA_FOTOGRAFIA'), ?, ?);";

    		preparedStatement=conn.prepareStatement(QUERY_SQL);
    		preparedStatement.setBlob(1, in);
    		preparedStatement.setLong(2, idAnnuncio);

            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            throw new DAOException("ERRORE nell'inserimento delle Fotografie nell base dati! : createFotografia", e);
        } finally {
            if(preparedStatement != null){
                silentlyClose(preparedStatement);
            }
            DBManager.closeConnection();
        }
    }catch (SQLException e){
        throw new DBConnectionException("errore connessione al DB");
    }
    }


    private static void silentlyClose(Statement statement) {
    	try {
    		statement.close();
    	} catch (SQLException e) {
            e.printStackTrace();
    	}
    }   

}
