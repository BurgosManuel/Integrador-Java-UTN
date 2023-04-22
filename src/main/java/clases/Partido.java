package clases;

import enums.Constants;
import enums.ResultadoEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Partido {
    private int nroRonda;
    private Equipo equipo1;
    private int golesEquipo1;
    private Equipo equipo2;
    private int golesEquipo2;
    private final int CONST_CANTIDAD_DATOS_PARTIDO = 4;

    public Partido(Equipo equipo1, int golesEquipo1, Equipo equipo2, int golesEquipo2, int nroRonda) {
        this.equipo1 = equipo1;
        this.golesEquipo1 = golesEquipo1;

        this.equipo2 = equipo2;
        this.golesEquipo2 = golesEquipo2;

        this.nroRonda = nroRonda;
    }

    public Partido() {

    }

    public static List<Partido> buildListPartidosFromFile(File resultadosFile) throws IOException {
        List<String> resultadosLines = Files.readAllLines(resultadosFile.toPath());

        List<Integer> listRondas = new ArrayList<>();
        List<Integer> listGolesEquipos = new ArrayList<>();
        List<String> listaResultadosEquipos = new ArrayList<>();


        for(String resultadoLine : resultadosLines) {
            // Separamos los datos de equipo y goles con coma por cada linea.
            String[] resultadoSplit = resultadoLine.split(";");

            // Usamos cantidadRondas para setear el primer int a la lista de Rondas;
            int cantidadRondas = 0;

            // Obtenemos los datos a partir del array "spliteado"
            for(String splitDato : resultadoSplit) {
                int splitInt;
                String splitEquipo;
                // Aprovechamos la Excepción del parseInt para diferenciar Goles de Equipos.
                try{
                    splitInt = Integer.parseInt(splitDato);
                    // Si obtenemos un int y no hay rondas, lo agregamos a listRondas.
                    if(cantidadRondas == 0) {
                        listRondas.add(Integer.valueOf(splitInt));
                        cantidadRondas++;
                    } else {
                        listGolesEquipos.add(Integer.valueOf(splitInt));
                    }

                } catch (NumberFormatException e) {
                    splitEquipo = splitDato;
                    listaResultadosEquipos.add(splitEquipo);
                }
            }
        }

        List<Partido> listPartidos = new ArrayList<>();

        for (int i = 0; i < listaResultadosEquipos.size(); i += Constants.CONST_AUMENTO_INDICE_PARTIDOS) {
            Equipo equipo1 = new Equipo(listaResultadosEquipos.get(i));
            int golesEquipo1 = listGolesEquipos.get(i);

            Equipo equipo2= new Equipo(listaResultadosEquipos.get(i+1));
            int golesEquipo2 = listGolesEquipos.get(i+1);

            // Cada partido tiene 2 equipos y 2 goles, pero 1 ronda, por lo que siempre dividimos en 2 el indice para obtener la ronda correcta.
            int nroRonda = listRondas.get(i > 0 ? i / 2 : i);

            Partido partidoResultado = new Partido(equipo1, golesEquipo1, equipo2, golesEquipo2, nroRonda);

            if(Objects.nonNull(partidoResultado)) {
                listPartidos.add(partidoResultado);
            }
        }

        if(listPartidos.isEmpty()) {
            throw new IOException("No se pudieron obtener partidos/resultados a partir del archivo utilizado.");
        }

        return  listPartidos;
    }


    public ResultadoEnum resultado(Equipo equipo) throws IOException {
        // Validamos que el equipo usado como parámetro corresponda a este partido
        if(equipo != this.equipo1 && equipo != this.equipo2) {
            throw new IOException("El equipo ingresado no corresponde a este partido");
        }

        // Inicializamos un equipoGanador
        Equipo equipoGanador = null;

        // Definimos el equipoGanador basandonos en la cantidad de goles
        if(this.golesEquipo1 > this.golesEquipo2) {
            equipoGanador = this.equipo1;
        } else if(this.golesEquipo1 < this.golesEquipo2)  {
            equipoGanador = this.equipo2;
        }

        // Si equipoGanador fue seteado significa que tenemos un ganador, caso contrario, es un empate.
        if(equipoGanador != null) {
            return equipoGanador == equipo ? ResultadoEnum.GANADOR : ResultadoEnum.PERDEDOR;
        } else {
            return ResultadoEnum.EMPATE;
        }
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public int getGolesEquipo1() {
        return golesEquipo1;
    }

    public void setGolesEquipo1(int golesEquipo1) {
        this.golesEquipo1 = golesEquipo1;
    }

    public int getGolesEquipo2() {
        return golesEquipo2;
    }

    public void setGolesEquipo2(int golesEquipo2) {
        this.golesEquipo2 = golesEquipo2;
    }

    public int getNroRonda() {
        return nroRonda;
    }

    public void setNroRonda(int nroRonda) {
        this.nroRonda = nroRonda;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "nroRonda=" + this.getNroRonda() +
                ", equipo1=" + equipo1.toString() +
                ", equipo2=" + equipo2.toString() +
                ", golesEquipo1=" + golesEquipo1 +
                ", golesEquipo2=" + golesEquipo2 +
                '}';
    }
}
