package clases;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Configuracion {
    private String dbUrl;
    private String dbName;
    private String username;
    private String password;
    private int puntosAcierto;
    private int puntosRondaAcertada;
    private int puntosFaseAcertada;

    @JsonIgnore
    private static Configuracion instance = null;

    private Configuracion() {
        // Usamos patron Singleton
    }

    // Usamos patron Singleton
    public static Configuracion getInstance() {
        if(instance == null) {
            instance = new Configuracion();
        }

        return instance;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntosAcierto() {
        return puntosAcierto;
    }

    public void setPuntosAcierto(int puntosAcierto) {
        this.puntosAcierto = puntosAcierto;
    }

    public int getPuntosRondaAcertada() {
        return puntosRondaAcertada;
    }

    public void setPuntosRondaAcertada(int puntosRondaAcertada) {
        this.puntosRondaAcertada = puntosRondaAcertada;
    }

    public int getPuntosFaseAcertada() {
        return puntosFaseAcertada;
    }

    public void setPuntosFaseAcertada(int puntosFaseAcertada) {
        this.puntosFaseAcertada = puntosFaseAcertada;
    }
}
