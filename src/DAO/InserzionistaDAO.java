package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import Entity.*;
import exception.*;


public class InserzionistaDAO {

    public static int checkPresenzaInserzionista(Connection conn, Long IDInserzionista, String Password) throws  DBConnectionException, DAOException{
        

        PreparedStatement preparedStatement = null;

        int num = 0;
        Connection connt = conn;


        try{
            if(conn == null)
                conn = DBManager.getConnection();

        try {

            final String QUERY_SQL = "SELECT COUNT (*) FROM inserzionista WHERE idInserzionista = ? AND password = ?;";
            
            preparedStatement = conn.prepareStatement(QUERY_SQL);

            preparedStatement.setLong(1, IDInserzionista);
            preparedStatement.setString(2, Password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                num=resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("ERRORE controllo inserzionista : checkPresenzaInserzionista");
        }finally{
            
            if (preparedStatement !=null){
                silentlyClose(preparedStatement);
            }

            if(connt == null)
                DBManager.closeConnection();

        }
    } catch (SQLException e){
        throw new DBConnectionException("Errore connessione al DB");

    }

        return num;

    }

    

    public static Inserzionista readInserzionista(Connection conn, Long idInserzionista) throws SQLException, DAOException, DBConnectionException{

        PreparedStatement preparedStatement = null;
        Inserzionista i = null;


        try{
             final String QUERY_SQL = "SELECT * FROM inserzionista WHERE idinserzionista = ?;";

             preparedStatement = conn.prepareStatement(QUERY_SQL);

             preparedStatement.setLong(1, idInserzionista);

             ResultSet result = preparedStatement.executeQuery();

             if (result.next()){
                Contatto c = ContattoDAO.readContatto(conn,result.getLong(1));
                i = new Inserzionista(result.getLong(1), result.getString(2), result.getString(3),c);
             }

        } catch (DAOException e){
            throw new DAOException("Errore nella lettura dell'inserzionista");
        } finally {
            if (preparedStatement != null)
                silentlyClose(preparedStatement);
        }

        return i;
    }

    private static void silentlyClose(Statement statement) {
    	try {
    		statement.close();
    	} catch (SQLException e) {
            e.printStackTrace();
    	}
    }    
    
}
