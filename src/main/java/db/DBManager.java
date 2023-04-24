package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DBManager {
	// Usamos DB en la nube por defecto.
	private String DB_USERNAME="ua4ttdzklhqkwylu";
	private String DB_PASSWORD="iuPpq9MjL4czoe6UnwH0";
	private String DB_URL="jdbc:mysql://bzfr6hutfpue9340vlj8-mysql.services.clever-cloud.com:3306/bzfr6hutfpue9340vlj8";
	
	private static DBManager instance = null;
	
	
	private DBManager() { // Es privada para que solo se pueda hacer una instancia de DBManager--- El punto es no poder hacer mas de una conexion en el programa
	}
	
	public static DBManager getInstance() {
		if(instance==null) {
			instance=new DBManager();//si la variable es nula devuelve una nueva instancia // si no, devuelve la instancia anterior
		}
		
		return instance;
	}

	public Connection conexion() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	public void finalizarConexion(Connection conexion) {
		if(Objects.nonNull(conexion)) {
			try{
				conexion.close();
				conexion = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Generamos unicamente los setters para evitar acceder a los valores de los datos.

	public void setDB_USERNAME(String DB_USERNAME) {
		this.DB_USERNAME = DB_USERNAME;
	}

	public void setDB_PASSWORD(String DB_PASSWORD) {
		this.DB_PASSWORD = DB_PASSWORD;
	}

	public void setDB_URL(String DB_URL) {
		this.DB_URL = DB_URL;
	}
}
