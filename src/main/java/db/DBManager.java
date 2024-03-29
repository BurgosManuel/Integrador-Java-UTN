package db;

import clases.Configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DBManager {
	// Usamos datos de DB en Clever Cloud por defecto.
	private String DB_NAME;
	private String DB_USERNAME;
	private String DB_PASSWORD;
	private String DB_URL;

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

	public void updateDataByConfiguration(Configuracion config) {
		if(Objects.nonNull(config)) {
			this.DB_NAME = config.getDbName();
			this.DB_URL = config.getDbUrl() + config.getDbName();
			this.DB_USERNAME = config.getUsername();
			this.DB_PASSWORD = config.getPassword();
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

	public String getDB_NAME() {
		return DB_NAME;
	}

	public void setDB_NAME(String DB_NAME) {
		this.DB_NAME = DB_NAME;
	}
}
