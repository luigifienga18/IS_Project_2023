package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import Entity.*;
import exception.DAOException;
import exception.DBConnectionException;



public class AnnuncioDAO {

    public static Annuncio readAnnuncioPerIDs (Connection conn, Long idANNUNCIO, Long idINSERZIONISTA) throws DAOException, DBConnectionException{

        PreparedStatement preparedStatement = null;
        java.util.List<Fotografia> lF = new ArrayList<>(); 
        Annuncio annuncio = null;

        Connection connt = conn;
    
        try{
            if (conn==null)
                conn = DBManager.getConnection();

        try{

            final String QUERY_SQL = "SELECT * FROM annuncio where idAnnuncio = ? AND idinserzionista = ?;";

            preparedStatement = conn.prepareStatement(QUERY_SQL);
            preparedStatement.setLong(1, idANNUNCIO);
            preparedStatement.setLong(2, idINSERZIONISTA);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next())
            {
                lF = FotografiaDAO.readFotografia(conn, idANNUNCIO);
                
                Tipologia tipologia = Tipologia.valueOf(result.getString(9));
                
                Stato stato = Stato.valueOf(result.getString(10));
                
                Inserzionista inserzionista = InserzionistaDAO.readInserzionista(conn, idINSERZIONISTA);
                annuncio = new Annuncio(idANNUNCIO, result.getDate(2), result.getString(3), result.getString(4), result.getDouble(5), result.getInt(6), result.getString(7), result.getDouble(8), tipologia, stato, lF, inserzionista);
            }
        } catch (SQLException e){
            throw new DAOException("errore lettura annuncio",e);
        } finally{
            if (preparedStatement != null)
                {
                    silentlyClose(preparedStatement);
                }

                if(connt==null)
                    DBManager.closeConnection();
        }

    } catch (SQLException e){
        throw new DBConnectionException("errore connessione DB");
    }

        return annuncio;

    }



    public static java.util.List<Annuncio> readAnnunciRicerca (Connection conn, String tipologia, String città, String CAP,  String numVani) throws DBConnectionException, DAOException{
   
            PreparedStatement preparedStatement = null;
            java.util.List<Annuncio> listaAnnunci = new ArrayList<> ();
            java.util.List<Fotografia> listaFotografie = null;

            Connection connt = conn;

            try{
                if(conn == null)
                 conn = DBManager.getConnection();

            try{
                final String QUERY_SQL = "SELECT * FROM annuncio WHERE (tipologia = ? OR ? IS NULL) AND (citta = ? OR ? IS NULL) AND (cap = ? OR ? IS NULL) AND (numerovani = ? OR ? IS NULL);";
                preparedStatement = conn.prepareStatement(QUERY_SQL);
                
                preparedStatement.setString(1, tipologia);
                preparedStatement.setString(2, tipologia);
                preparedStatement.setString(3, città);
                preparedStatement.setString(4, città);
                preparedStatement.setString(5, CAP);
                preparedStatement.setString(6, CAP);
                preparedStatement.setString(7, numVani);
                preparedStatement.setString(8, numVani);

                ResultSet result = preparedStatement.executeQuery();

                while (result.next()){

                    Tipologia tipologiaA = Tipologia.valueOf(result.getString(9));
                    Stato statoA = Stato.valueOf(result.getString(10));
                    
                    Inserzionista inserzionista = InserzionistaDAO.readInserzionista(conn, result.getLong(11));
                    listaFotografie = FotografiaDAO.readFotografia(conn, result.getLong(1));
                    Annuncio a = new Annuncio (result.getLong(1), result.getDate(2), result.getString(3), result.getString(4), result.getDouble(5), result.getInt(6), result.getString(7), result.getDouble(8), tipologiaA, statoA, null, inserzionista);
                    a.setListaFotografie(listaFotografie);
                    listaAnnunci.add(a);
                }

            } catch (SQLException e){
                throw new DAOException("errore lettura annunci : readannunciricerca",e);
            } finally{
                if (preparedStatement != null)
                    {
                        silentlyClose(preparedStatement);
                    }
                    
                    if(connt == null)
                    {
                        DBManager.closeConnection();
                    }
            }

        }catch(SQLException e){
            throw new DBConnectionException("errore connessione al DB");
        }

            return listaAnnunci;

    }



    public static void updateAnnuncio (Connection conn, Long IDAnnuncio, Tipologia tipologia, String citta, String CAP, Double nr_m2, int nr_vani, Stato stato, String descrizione, Double prezzo) throws DBConnectionException, DAOException{
        PreparedStatement preparedStatement = null;
        String tipo = tipologia.toString(); //Questi due potrebbero anche essere fatti all'interno della boundary o del gpa
        String stato_a = stato.toString();

        Connection connt= conn;

        try{
            if(conn == null)
                conn = DBManager.getConnection();
        
        try{
    		final String QUERY_SQL = "UPDATE annuncio SET tipologia = ?, citta = ?, cap = ?, numerometriquadri = ?, numerovani = ?, descrizione = ?, prezzo = ?, stato = ? WHERE idannuncio = ?;";
           
            preparedStatement = conn.prepareStatement(QUERY_SQL);
    		preparedStatement.setString(1,tipo);
    		preparedStatement.setString(2, citta);
            preparedStatement.setString(3, CAP);
            preparedStatement.setDouble(4, nr_m2);
            preparedStatement.setInt(5, nr_vani);
            preparedStatement.setString(6, descrizione);
            preparedStatement.setDouble(7, prezzo);
            preparedStatement.setString(8, stato_a);
            preparedStatement.setLong(9,IDAnnuncio);
            
            preparedStatement.executeUpdate();
   
        }
        catch(SQLException e)
        {
            throw new DAOException ("ERRORE nell'aggiornament della fotografie : updateFotografia", e);
        } 
        finally {
            if(preparedStatement != null){
                silentlyClose(preparedStatement);
            }
            if(connt == null)
                DBManager.closeConnection();

        }

    }catch(SQLException e){
        throw new DBConnectionException("errore connessione al DB");
    }
    }




    public static void deleteAnnuncio(Connection conn, Long idAnnuncio) throws DBConnectionException, DAOException
    {
        PreparedStatement preparedStatement = null;
        Connection contr = conn;

        try{

            if (conn == null)
                conn = DBManager.getConnection();
        
        try
        {
    		final String QUERY_SQL = "DELETE FROM annuncio WHERE idannuncio = ?;";
            preparedStatement = conn.prepareStatement(QUERY_SQL);
    		preparedStatement.setLong(1,idAnnuncio);           
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DAOException("ERRORE nella cancellazione dell'annuncio' : deleteFotografia", e);
        } finally {
            if(preparedStatement != null){
                silentlyClose(preparedStatement);
            }

            if (contr == null)
                DBManager.closeConnection();
        }

        }catch(SQLException e){
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
