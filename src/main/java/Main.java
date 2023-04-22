import clases.Partido;
import clases.Pronostico;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Inicializamos un scanner para, más adelante pedir los directorios en la consola.
        Scanner scanner = new Scanner(System.in);

        //=== Lógica para pedir los datos por consola ===
        ///System.out.println("Ingresa la ubicación del archivo \"partidos.csv\"");
        //String resultadoPath = scanner.nextLine();
        //System.out.println("Ingresa la ubicación del archivo \"pronostico.csv\"");
        //String pronosticoPath = scanner.nextLine();


        // Seteamos los absolute paths en las variables.
        Path partidosPath = Paths.get("src\\main\\resources\\partidos.csv").toAbsolutePath();
        Path pronosticosPath =  Paths.get("src\\main\\resources\\pronostico.csv").toAbsolutePath();

        // Creamos variables de tipo File a partir de los paths.
        File resultadoFile = new File(partidosPath.toUri());
        File pronosticoFile = new File(pronosticosPath.toUri());

        // Obtenemos la lista de partidos a partir del archivo partidos.csv
        List<Partido> listPartidos = Partido.buildListPartidosFromFile(resultadoFile);

        List<Pronostico> listPronostico = Pronostico.buildListPronostico(pronosticoFile, listPartidos);

        // Imprimimos por pantalla el valor de las instancias del objeto Partido
        for(Partido p : listPartidos) {
            System.out.println(p.toString());
        }
    }
}
