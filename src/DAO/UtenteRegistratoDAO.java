package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import Entity.UtenteRegistrato;
import exception.DAOException;
import exception.DBConnectionException;



public class UtenteRegistratoDAO {


    public static Long createUtenteRegistrato (Connection conn, String nome, String cognome) throws DAOException, DBConnectionException{

        PreparedStatement preparedStatement = null;
        Long id=null;
        Connection contr = conn;

         try{
            if (conn == null)
                conn = DBManager.getConnection();
        try{
    		final String QUERY_SQL = "INSERT INTO UtenteRegistrato (idUtenteRegistrato, nome, cognome) VALUES (NEXTVAL('SEQUENZA_UTENTEREGISTRATO'), ?, ?);" ;
    		preparedStatement = conn.prepareStatement(QUERY_SQL,Statement.RETURN_GENERATED_KEYS);
    		preparedStatement.setString(1, nome);
    		preparedStatement.setString(2, cognome);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next() == true)
            {
                id = resultSet.getLong(1);
            }
        }
        catch(SQLException e){
            throw new DAOException("ERRORE nell'inserimento dell'Utente nell base dati! : createUtenteRegistrato"/*, e*/);
        } finally {
            if(preparedStatement != null){
                silentlyClose(preparedStatement);
            }
            if (contr == null)
                DBManager.closeConnection();
        }

    } catch (SQLException e){
        throw new DBConnectionException("errore connessione al DB");
    }
        return id;

    }


    public static UtenteRegistrato readUtenteRegistrato(Connection conn, Long id) throws DBConnectionException,DAOException
    {
        UtenteRegistrato ur = null;
        PreparedStatement preparedStatement = null;
        Connection contr = conn;

        try
        {
            if (conn == null)
                conn = DBManager.getConnection();

            try
            {
                final String QUERY_SQL = "SELECT * FROM UTENTEREGISTRATO WHERE IDUTENTEREGISTRATO = ? ;" ;
                preparedStatement = conn.prepareStatement(QUERY_SQL,Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setLong(1, id);

                ResultSet resultSet =  preparedStatement.executeQuery();

                if(resultSet.next() == true)
                {
                    ur = new UtenteRegistrato(resultSet.getLong("IDUTENTEREGISTRATO"),resultSet.getString("nome"), resultSet.getString("cognome"),ContattoDAO.readContatto(conn, resultSet.getLong("IDUTENTEREGISTRATO")));
                }

            }catch(SQLException e){
                throw new DAOException("ERRORE nell'inserimento dell'Utente nell base dati! : readUtenteRegistrato"/*, e*/);
                } finally {
                    if(preparedStatement != null){
                        silentlyClose(preparedStatement);
                    }

                    if (contr == null)
                        DBManager.closeConnection();
                }
        


        }catch(SQLException e)
        {
            throw new DBConnectionException("errore connessione al DB");
        }



        return ur;
    }
    
    

    private static void silentlyClose(Statement statement) {
    	try {
    		statement.close();
    	} catch (SQLException e) {
            e.printStackTrace();
    	}
    }

    
}
