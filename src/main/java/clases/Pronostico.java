package clases;

import enums.ResultadoEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Pronostico {
    private Partido partido;
    private Equipo equipo;
    private ResultadoEnum resultado;

    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado) {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
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

        // Armamos una list que contenga las X y los espacios Vacios
        List<String> listPosicionX = new ArrayList<>();

        for(String pronosticoLine : pronosticoLines) {
            System.out.println(pronosticoLine);
            // Separamos los datos con coma por cada linea.
            String[] resultadoSplit = pronosticoLine.split(",");
            // Obtenemos los datos a partir del array "spliteado"
            for(String splitDato : resultadoSplit) {
                if(splitDato.equalsIgnoreCase("X") || splitDato.isEmpty()) {
                    listPosicionX.add(splitDato);
                }
            }

        }

        // Creamos la lista de pronosticos
        List<Pronostico> listPronosticos = new ArrayList<>();

            for(int i = 0; i < listPosicionX.size(); i = i) {
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

                    // Similar al caso de la posicionX, utilizamos obtenerIndicePartidoPorPosX para obtener el partido correcto.
                    Partido partido = listPartidos.get(Pronostico.obtenerIndicePartidoPorPosX(i));

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

                    Pronostico pronosticoResult = new Pronostico(partido, equipoSeleccionado, resultado);

                    listPronosticos.add(pronosticoResult);

                }
            }

        return  listPronosticos;
    }

    public static int obtenerIndicePartidoPorPosX(int posicionX) {
        // 3 posiciones de X == 1 partido. Vamos restando de a 3 posiciones para encontrar el lugar de cada partido.
        int indicePartido = 0;
        while (posicionX - 3 > indicePartido) {
            posicionX -= 3;
            indicePartido++;
        }
        return indicePartido;
    }

    public static int obtenerPosXPorIndice(int indice) {
        // Restamos de a 3 valores para obtener siempre 0, 1 o 2.
        while(indice > 3) {
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

    @Override
    public String toString() {
        return "Pronostico{" +
                "equipo=" + this.getEquipo().toString() +
                ", resultado=" + this.getResultado().getValue() +
                ", partido=" + this.getPartido().toString() +
                '}';
    }
}
