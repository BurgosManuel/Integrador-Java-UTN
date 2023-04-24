package clases;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Puntaje {
	
	private String nombre;
	private int pts;
	private int cantidadAciertos;
	private int cantidadRondasAcertadas;
    private int cantidadFasesAcertadas;

    private int puntosAcierto = 1;
    private int puntosRondaAcertada = 1;
    private int puntosFaseAcertada = 2;
	
	public Puntaje(String nombre, int pts, int cantAciertos, int cantRondasAcertadas, int cantFasesAcertadas) {
		this.nombre=nombre;
		this.pts=pts;
		this.cantidadAciertos=cantAciertos;
		this.cantidadRondasAcertadas=cantRondasAcertadas;
        this.cantidadFasesAcertadas=cantFasesAcertadas;
	}

    public Puntaje(){};

	public List<Puntaje> puntos(List<Partido> listPartidos, List<Pronostico> listPronostico) {
		List<Puntaje> puntos = new ArrayList<>();
		
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

//        System.out.println("========= RONDAS =========");
//        for(Ronda ronda : listRondas) {
//            System.out.println(ronda.toString());
//        }

        
        for(String nombre : setNombres) {
            int puntaje = 0;
            for(Pronostico pronostico : listPronostico) {
                if(pronostico.getNombreJugador().equalsIgnoreCase(nombre)) {
                    try {
                        int puntosObtenidos = pronostico.puntos();
                        puntaje += puntosObtenidos;
                        if(puntosObtenidos > 0) {
                            for(Ronda ronda: listRondas) {
                                // Agregamos un acierto a la ronda cuando obtenemos un puntaje.
                                if(Integer.valueOf(ronda.getNro()).equals(pronostico.getPartido().getNroRonda())) {
                                    ronda.setAciertos(ronda.getAciertos() + 1);
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            // Revisamos la cantidad de rondas acertadas y multiplicamos el valor extra * cantidad de rondas.
            int cantidadAciertos = 0;
            int cantidadRondasAcertadas = 0;
            for(Ronda ronda : listRondas) {
                cantidadAciertos += ronda.getAciertos();
                if(ronda.getAciertos() == ronda.getPartidos().size()) {
                    cantidadRondasAcertadas++;
                }
            }

            if(cantidadRondasAcertadas > 0) {
                puntaje += this.puntosRondaAcertada * cantidadRondasAcertadas;// Se suma por cantidad de rondas acertadas
                if(cantidadRondasAcertadas == listRondas.size()) {
                    this.setCantidadFasesAcertadas(this.getCantidadFasesAcertadas() + 1);
                	puntaje += this.puntosFaseAcertada;// Puntos extras si acertamos en todas las rondas de esta Fase
                }
            }
            Puntaje aux = new Puntaje(nombre,puntaje,cantidadAciertos,cantidadRondasAcertadas, cantidadFasesAcertadas);

            // Reseteamos los valores
            listRondas.stream().forEach(r -> r.setAciertos(0));
            this.setCantidadFasesAcertadas(0);

            puntos.add(aux);
        }
		
		return puntos;
	}

    public void updatePuntajesByConfiguration(Configuracion configuracion) {
        if(Objects.nonNull(configuracion)) {
            this.puntosAcierto = configuracion.getPuntosAcierto();
            this.puntosRondaAcertada = configuracion.getPuntosRondaAcertada();
            this.puntosFaseAcertada = configuracion.getPuntosFaseAcertada();
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadAciertos() {
        return cantidadAciertos;
    }

    public void setCantidadAciertos(int cantidadAciertos) {
        this.cantidadAciertos = cantidadAciertos;
    }

    public int getCantidadRondasAcertadas() {
        return cantidadRondasAcertadas;
    }

    public void setCantidadRondasAcertadas(int cantidadRondasAcertadas) {
        this.cantidadRondasAcertadas = cantidadRondasAcertadas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
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

    public int getCantidadFasesAcertadas() {
        return cantidadFasesAcertadas;
    }

    public void setCantidadFasesAcertadas(int cantidadFasesAcertadas) {
        this.cantidadFasesAcertadas = cantidadFasesAcertadas;
    }
}
