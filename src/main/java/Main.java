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
    	
        // Obtenemos la lista de partidos a partir del archivo partidos.csv
        List<Partido> listPartidos = Partido.buildListPartidosFromFile(resultadoFile);
        List<Pronostico> listPronostico;
    	
    	if(args[0].contains("cvs")) {
    		String pronosticoFile=args[0];
	        listPronostico = Pronostico.buildListPronostico(pronosticoFile, listPartidos);
    	}else {
    		listPronostico  = Pronostico.buildListPronostico(args[0], listPartidos);
    		
    		
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

        List<Puntajes> puntos = Puntajes.Puntos(listPartidos, listPronostico,Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
        
        
        System.out.println("============ LOS PUNTAJES SON: ============");
        for(Puntajes pts:puntos) {
        	System.out.println("Nombre:"+pts.getNombre()+"\tPuntos:"+pts.getPts()+"\tCANTIDAD ACIERTOS: " + pts.getCantidadAciertos() + "\tRONDAS COMPLETAS: " + pts.getCantidadRondasAcertadas());
        }
        
    }
}
