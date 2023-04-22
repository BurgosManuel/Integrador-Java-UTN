package clases;

import java.util.List;

public class Ronda {
    private String nro;
    private List<Partido> partidos;
    private int aciertos;

    public Ronda(String nro, List<Partido> partidos) {
        this.nro = nro;
        this.partidos = partidos;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    @Override
    public String toString() {
        return "Ronda{" +
                "nroRonda=" + this.getNro() +
                ", partidos=" + this.getPartidos().toString() +
                '}';
    }
}
