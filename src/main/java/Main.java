import clases.Configuracion;
import clases.Partido;
import clases.Pronostico;
import clases.Puntaje;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.DBManager;


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

        System.out.println("Iniciado programa...");

        // Seteamos los absolute paths en las variables.
        Path partidosPath = Paths.get(args[0]).toAbsolutePath();

        // Creamos variables de tipo File a partir de los paths.
        File resultadoFile = new File(partidosPath.toUri());

        // Obtenemos la lista de partidos a partir del archivo partidos.csv
        System.out.println("Obteniendo partidos...");
        List<Partido> listPartidos = Partido.buildListPartidosFromFile(resultadoFile);

        System.out.println("¡Partidos obtenidos!");
        List<Pronostico> listPronostico;

        // Instanciamos un puntaje de utilidad para configurar los puntos y obtener la lista de puntajes al final.
        Puntaje puntajeUtilidad = new Puntaje();

        // Validamos si el segundo argumento es un archivo .csv o es un archivo de configuración, en base a eso obtenemos la lista de pronósticos.
        System.out.println("Obteniendo pronósticos...");

        if(args[1].contains(".csv")) {
            // Obtenemos el .csv de pronosticos
            Path pronosticosPath =  Paths.get(args[1]).toAbsolutePath();
            File pronosticoFile = new File(pronosticosPath.toUri());

            // Obtenemos la lista de pronosticos a partir del .csv
	        listPronostico = Pronostico.buildListPronostico(pronosticoFile, listPartidos);
    	}else if(args[1].contains(".json")){
            // Obtenemos el archivo de configuración .json
            Path configuracionPath = Paths.get(args[1]).toAbsolutePath();
            File configuracionFile = new File(configuracionPath.toUri());

            // Instanciamos el objectMapper y leemos el json para mapearlo a la clase Configuración.
            ObjectMapper objectMapper = new ObjectMapper();
            Configuracion configuracion = objectMapper.readValue(configuracionFile, Configuracion.class);

            // Configuramos los datos de conexión a la DB y nombre de la DB.
            DBManager dbManager = DBManager.getInstance();
            dbManager.updateDataByConfiguration(configuracion);

            // Configuramos los puntos dinámicamente en base al json.
            puntajeUtilidad.updatePuntajesByConfiguration(configuracion);

            // Obtenemos la lista de pronósticos a partir de la DB.
    		listPronostico  = Pronostico.buildListPronosticoFromDB(listPartidos);
    	} else {
            throw new RuntimeException("No se encontró un archivo de pronosticos o configuración válido.");
        }
        System.out.println("¡Pronosticos obtenidos!");
    	
        /*
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
         */

        System.out.println("Calculando puntuación de los jugadores...");
        List<Puntaje> puntos = puntajeUtilidad.puntos(listPartidos, listPronostico);
        // Ordenamos la lista por puntajes de mayor a menor
        Collections.sort(puntos, (o1, o2) -> Integer.valueOf(o2.getPts()).compareTo(Integer.valueOf(o1.getPts())));
        System.out.println("¡Puntaje calculado con éxito!");
        
        
        System.out.println("============ LOS PUNTAJES SON: ============");
        int posicion = 1;
        for(Puntaje pts:puntos) {
        	System.out.println("PUESTO: #" + posicion+ " NOMBRE: "+pts.getNombre()+" PUNTOS: "+pts.getPts()+" CANTIDAD ACIERTOS: " + pts.getCantidadAciertos() + " RONDAS COMPLETAS: " + pts.getCantidadRondasAcertadas() + " FASES COMPLETAS: " + pts.getCantidadFasesAcertadas());
            posicion++;
        }
        System.out.println("===========================================");
    }
}
