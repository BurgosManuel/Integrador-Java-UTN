import clases.Partido;
import clases.Pronostico;
import clases.Ronda;
import enums.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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

        // Armamos un set para no tener repetidos los nombres.
        Set<String> setNombres = new HashSet<>(listPronostico.stream()
                .map(pronostico -> pronostico.getNombreJugador())
                .collect(Collectors.toList()));

        // Armamos un set de Rondas.
        Set<Integer> setNroRondas = new HashSet<>(listPartidos.stream()
                .map(partido -> partido.getNroRonda())
                .collect(Collectors.toList()));

        // Creamos la lista de Rondas y Partidos
        List<Ronda> listRondas = new ArrayList<>();
        setNroRondas.stream().forEach(nroRonda -> {
            List<Partido> listPartidosFiltered = listPartidos.stream()
                    .filter(partido -> partido.getNroRonda() == nroRonda)
                    .collect(Collectors.toList());

            listRondas.add(new Ronda(nroRonda.toString(), listPartidosFiltered));
        });

        System.out.println("========= RONDAS =========");
        for(Ronda ronda : listRondas) {
            System.out.println(ronda.toString());
        }

        System.out.println("============ LOS PUNTAJES SON: ============");
        for(String nombre : setNombres) {
            int puntajeJugador = listPronostico.stream()
                    .filter(pronostico -> pronostico.getNombreJugador().equals(nombre)) // Obtenemos los pronosticos que correspondan al nombre del jugador
                    .map(pro -> { // Obtneemos el puntaje del jugador
                        int puntaje = 0;
                        try {
                            int puntosObtenidos = pro.puntos();
                            puntaje += puntosObtenidos;
                            if(puntosObtenidos > 0) {
                                for(Ronda ronda: listRondas) {
                                    // Agregamos un acierto a la ronda cuando obtenemos un puntaje.
                                    if(Integer.valueOf(ronda.getNro()).equals(pro.getPartido().getNroRonda())) {
                                        ronda.setAciertos(ronda.getAciertos() +1);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        return puntaje;
                    }).reduce(0, (x,y) -> x+y); // Usamos reduce para sumar el puntaje total
            // Revisamos la cantidad de rondas acertadas y multiplicamos el valor extra * cantidad de rondas.
            int cantidadRondasAcertadas= listRondas.stream().filter(r -> r.getAciertos() == r.getPartidos().size()).collect(Collectors.toList()).size();
            if(cantidadRondasAcertadas > 0) {
                puntajeJugador += Constants.VALOR_EXTRA_RONDAS * cantidadRondasAcertadas;
            }
            listRondas.stream().forEach(r -> r.setAciertos(0));

            System.out.println(nombre + ": " + puntajeJugador); // Imprimimos el nombre y puntaje por consola.
        }
    }
}
