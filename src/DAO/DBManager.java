package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager {
	
	protected static Connection conn;

	protected final static String dbPath = "./gpa";
	protected final static String dbPathLF = "C:/Users/Luigi Fienga/Desktop/ElaboratoIS/gpa";
	protected final static String dbPathLF2 = "C:/Users/admin/Desktop/ElaboratoIS/gpa";
	protected final static String dbpathMD = "C:/Users/matte/Dropbox/Il mio PC (LAPTOP-EMJU3OME)/Desktop/università/Ingegneria del software/Progetto/Team11/gpa";
	protected final static String dbPathTEST = "./src/test/gpaTest";
	protected final static String url = "jdbc:h2:" + dbPath;
	protected final static String urlLF = "jdbc:h2:" + dbPathLF;
	protected final static String urlLF2 = "jdbc:h2:" + dbPathLF2;
	protected final static String urlMD = "jdbc:h2:" + dbpathMD;
	protected final static String urlTEST = "jdbc:h2:" + dbPathTEST;

	public static Connection getConnection() throws SQLException { /*questo throws si potrebbe togliere ma me lo richiede visto che si sta usando il metodo isClosed */
			
			
			if(conn == null || conn.isClosed() /*Come mai c'e' anche questo conn.isClosed? ho copiato e incollato quello del professore, chiedi a lui*/) {
				try{
					conn = DriverManager.getConnection(url, "sa", "");
				} catch(SQLException e){
					e.printStackTrace();
				}
			}
			
			return conn;
		
	}	
	
	public static void closeConnection() throws SQLException {
		
			if(conn != null) {
				conn.close();
				conn = null;
			}
	}

}
