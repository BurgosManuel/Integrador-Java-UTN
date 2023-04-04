import clases.Partido;

import java.io.File;
import java.io.IOException;
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
        String partidosPath =  "E:\\Coding\\Cursos\\Argentina Programa 4.0 - UTN Java\\Trabajo Integrador\\Proyecto\\Proyecto Integrador - Java UTN\\src\\main\\resources\\partidos.csv";
        String pronosticosPath =  "E:\\Coding\\Cursos\\Argentina Programa 4.0 - UTN Java\\Trabajo Integrador\\Proyecto\\Proyecto Integrador - Java UTN\\src\\main\\resources\\pronostico.csv";

        // Creamos variables de tipo File a partir de los paths.
        File resultadoFile = new File(partidosPath);
        File pronosticoFile = new File(pronosticosPath);

        // Obtenemos la lista de partidos a partir del archivo partidos.csv
        List<Partido> listPartidos = Partido.buildListPartidosFromFile(resultadoFile);

        // Imprimimos por pantalla el valor de las instancias del objeto Partido
        for(Partido p : listPartidos) {
            System.out.println(p.toString());
        }
    }
}
