package clases;

import enums.ResultadoEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pronostico {
    private Partido partido;
    private Equipo equipo;
    private ResultadoEnum resultado;
    private String nombreJugador;

    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado, String nombreJugador) {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
        this.nombreJugador = nombreJugador;
    }

    public int puntos() throws IOException {
        int puntaje = 0;

        if(this.partido.resultado(equipo) == this.resultado) {
            puntaje++;
        };

        return puntaje;
    }

    public static List<Pronostico> buildListPronostico(File pronosticoFile, List<Partido> listPartidos) throws IOException {
        List<String> pronosticoLines = Files.readAllLines(pronosticoFile.toPath());

        // Armamos una list que contenga las X y los espacios Vacios-
        // Tambien armamos una lista para los nombres de los jugadores.
        List<String> listPosicionX = new ArrayList<>();
        List<String> listNombreJugador = new ArrayList<>();
        List<String> nombresDeEquipo = new ArrayList<>();

        // Obtenemos todos los nombres de equipo para diferenciar
        if(Objects.nonNull(listPartidos) && !listPartidos.isEmpty()) {
             nombresDeEquipo = listPartidos.stream()
                    .flatMap(partido -> Stream.of(partido.getEquipo1().getNombre(), partido.getEquipo2().getNombre()))
                    .collect(Collectors.toList());
        } else {
            throw new IOException("La lista de partidos esta vacio o es nula.");
        }


        for(String pronosticoLine : pronosticoLines) {
            // Separamos los datos con coma por cada linea.
            String[] resultadoSplit = pronosticoLine.split(";");
            // Obtenemos los datos a partir del array "spliteado"
            for(String splitDato : resultadoSplit) {
                if(splitDato.equalsIgnoreCase("X") || splitDato.isEmpty()) {
                    listPosicionX.add(splitDato);
                } else {
                    // Si el dato encontrado no es un nombre de equipo, se agrega a la lista de jugadores.
                    if(!nombresDeEquipo.contains(splitDato)) {
                        listNombreJugador.add(splitDato);
                    }
                }
            }

        }

        // Creamos la lista de pronosticos
        List<Pronostico> listPronosticos = new ArrayList<>();
        int i = 0;
            while(i < listPosicionX.size()) {
                // Obtenemos lo que haya en la lista, ya sea X o Espacio y evaluamos.
                String pronostico = listPosicionX.get(i);

                // Seteamos las 3 posibilidades en booleans.
                boolean gana1 = false;
                boolean empate = false;
                boolean gana2 = false;

                // Setamos un equipo y un resultado para este pronostico.
                Equipo equipoSeleccionado = null;
                ResultadoEnum resultado = null;

                // Si es un espacio vacio, pasamos a la siguiente posicion.
                if(!pronostico.equalsIgnoreCase("X")) {
                    i++;
                } else {
                    // Usamos la función obtenerPosXPorIndice para contemplar casos donde i > 3.
                    // Esto debido a que siempre necestamos un valor que sea 0, 1 o 2.
                    switch(Pronostico.obtenerPosXPorIndice(i)) {
                        case 0: gana1 = true;
                        i += 3;
                        break;
                        case 1: empate = true;
                        i += 2;
                        break;
                        case 2: gana2 = true;
                        i += 1;
                        break;
                    }

                    // Similar al caso de la posicionX, utilizamos obtenerIndicePartidoPorPosX para obtener el partido y nombre de jugador correcto.
                    int indicePartidos = Pronostico.obtenerIndicePartidoPorPosX(i);
                    int indiceNombre = Pronostico.obtenerIndicePartidoPorPosX(i);

                    // Regresamos al principio de la lista de partidos siempre que todavia haya jugadores disponibles.
                    if (indicePartidos >= listPartidos.size() && indicePartidos < listNombreJugador.size()) {
                        indicePartidos -= listPartidos.size();
                    }

                    Partido partido = listPartidos.get(indicePartidos);
                    String nombreJugador = listNombreJugador.get(indiceNombre);


                    if(!empate) {
                        if(gana1) {
                            equipoSeleccionado = partido.getEquipo1();
                        } else {
                            equipoSeleccionado = partido.getEquipo2();
                        }
                        resultado = ResultadoEnum.GANADOR;
                    } else {
                        equipoSeleccionado = partido.getEquipo1();
                        resultado = ResultadoEnum.EMPATE;
                    }

                    Pronostico pronosticoResult = new Pronostico(partido, equipoSeleccionado, resultado, nombreJugador);

                    if(Objects.nonNull(pronosticoResult)){
                        listPronosticos.add(pronosticoResult);
                    }

                }
            }

            if(listPronosticos.isEmpty()) {
                throw new IOException("No se pudo obtener la lista de pronosticos a partir del archivo utilizado.");
            }

        return  listPronosticos;
    }

    public static int obtenerIndicePartidoPorPosX(int posicionX) {
        // 3 posiciones de X == 1 partido. Vamos restando de a 3 posiciones para encontrar el lugar de cada partido.
        int indicePartido = 0;
        while (posicionX - 3 >= indicePartido) {
            posicionX -= 3;
            indicePartido++;
        }
        return indicePartido;
    }

    public static int obtenerPosXPorIndice(int indice) {
        // Restamos de a 3 valores para obtener siempre 0, 1 o 2.
        while(indice >= 3) {
            indice -= 3;
        }
        return indice;
    }


    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public ResultadoEnum getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoEnum resultado) {
        this.resultado = resultado;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    @Override
    public String toString() {
        return "Pronostico{" +
                "equipo=" + this.getEquipo().toString() +
                ", resultado=" + this.getResultado().getValue() +
                ", nombreJugador=" + this.getNombreJugador() +
                ", partido=" + this.getPartido().toString() +
                '}';
    }
}
