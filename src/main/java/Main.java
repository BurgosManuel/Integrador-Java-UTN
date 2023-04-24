import clases.Partido;
import clases.Pronostico;
import clases.Puntajes;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        // Inicializamos un scanner para, más adelante pedir los directorios en la consola.
        //Scanner scanner = new Scanner(System.in);

        //=== Lógica para pedir los datos por consola ===
        ///System.out.println("Ingresa la ubicación del archivo \"partidos.csv\"");
        //String resultadoPath = scanner.nextLine();
        //System.out.println("Ingresa la ubicación del archivo \"pronostico.csv\"");
        //String pronosticoPath = scanner.nextLine();


        // Seteamos los absolute paths en las variables.
        Path partidosPath = Paths.get(args[0]).toAbsolutePath();

        // Creamos variables de tipo File a partir de los paths.
        File resultadoFile = new File(partidosPath.toUri());

        // Obtenemos la lista de partidos a partir del archivo partidos.csv
        List<Partido> listPartidos = Partido.buildListPartidosFromFile(resultadoFile);
        List<Pronostico> listPronostico;

        // Validamos si el segundo argumento es un archivo .csv o es un archivo de configuración, en base a eso obtenemos la lista de pronósticos.
    	if(args[1].contains(".csv")) {
            Path pronosticosPath =  Paths.get(args[1]).toAbsolutePath();
            File pronosticoFile = new File(pronosticosPath.toUri());
	        listPronostico = Pronostico.buildListPronostico(pronosticoFile, listPartidos);
    	}else {
            //TODO: Configurar conexion a DB y puntaje.
    		listPronostico  = Pronostico.buildListPronosticoFromDB(listPartidos);
    	}
    	
        // Imprimimos por pantalla el valor de las instancias del objeto Partido.
        System.out.println("========== PARTIDOS =========");
        for(Partido p : listPartidos) {
            System.out.println(p.toString());
        }

        System.out.println("============ PRONOSTICOS ==========");
        // Imprimimos por pantalla el valor de las instancias del objeto Partido.
        for(Pronostico pro : listPronostico) {
            System.out.println(pro.toString());
        }

        List<Puntajes> puntos = Puntajes.Puntos(listPartidos, listPronostico,1,1,2);
        
        
        System.out.println("============ LOS PUNTAJES SON: ============");
        for(Puntajes pts:puntos) {
        	System.out.println("Nombre:"+pts.getNombre()+"\tPuntos:"+pts.getPts()+"\tCANTIDAD ACIERTOS: " + pts.getCantidadAciertos() + "\tRONDAS COMPLETAS: " + pts.getCantidadRondasAcertadas());
        }
        
    }
}
