package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DBManager {
    private final String url = "jdbc:mysql://localhost:3306/integrador_utn";

    public Connection iniciarConexion() throws  SQLException {
        return DriverManager.getConnection(url, "root", "MySQL420!");
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


}
