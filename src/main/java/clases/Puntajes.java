package clases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Puntajes {
	
	private String nombre;
	private int pts;
	private int cantidadAciertos;
	private int cantidadRondasAcertadas;
	
	public Puntajes(String nombre,int pts,int cantAciertos,int cantRondasAcertadas) {
		this.nombre=nombre;
		this.pts=pts;
		this.cantidadAciertos=cantAciertos;
		this.cantidadRondasAcertadas=cantRondasAcertadas;
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

	public static List<Puntajes> Puntos(List<Partido> listPartidos,List<Pronostico> listPronostico,int ptsPorAcierto,int ptsExtraRonda,int ptsExtraFase) {
		List<Puntajes> puntos = new ArrayList<>();
		
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
                                    ronda.setAciertos(ronda.getAciertos() +ptsPorAcierto);
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
                puntaje += ptsExtraRonda * cantidadRondasAcertadas;//se suma x por ronda acertada
                if(cantidadRondasAcertadas > 1) {
                	puntaje += ptsExtraFase;//se suma x si completa una fase acertada
                }
            }
            listRondas.stream().forEach(r -> r.setAciertos(0));
            
            Puntajes aux = new Puntajes(nombre,puntaje,cantidadAciertos,cantidadRondasAcertadas);            
            puntos.add(aux);
            //System.out.println(nombre + ": " + "PUNTOS: " + puntaje + " CANTIDAD ACIERTOS: " + cantidadAciertos + " RONDAS COMPLETAS: " + cantidadRondasAcertadas); // Imprimimos el nombre y puntaje por consola.
        }
		
		return puntos;
	}

}
