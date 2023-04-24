package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
	
	private String DB_USERNAME="root";
	private String DB_PASSWORD="31928264As";
	
	private static DBManager instance = null;
	
	
	private DBManager() { // Es privada para que solo se pueda hacer una instancia de DBManager--- El punto es no poder hacer mas de una conexion en el programa
	}
	
	public static DBManager getInstance() {
		if(instance==null) {
			instance=new DBManager();//si la variable es nula devuelve una nueva instancia // si no, devuelve la instancia anterior
		}
		
		return instance;
	}
	
	public Connection conexion(String DB_URL) {
		Connection connection=null;
		
		try {
			connection= DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);//conecta la BD
			connection.setAutoCommit(false);//Apaga la actualizacion automatica			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return connection;
	}

}
