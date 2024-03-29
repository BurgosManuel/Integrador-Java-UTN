import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import clases.Partido;
import clases.Pronostico;
import clases.Puntaje;

public class PuntajeTest {
	
	private List<Partido> resulPartidos,resulPartidos2;
	private List<Pronostico> resulPronosticos,resulPronosticos2;
	private List<Puntaje> pts, pts2;
	private Puntaje puntajeUtilidad = new Puntaje();

	@BeforeEach
	public void setup() throws IOException {
		Path arch= Paths.get(new File ("src\\test\\resources\\PartidosTest.csv").getAbsolutePath()); 
		Path arch2=Paths.get(new File ("src\\test\\resources\\PronosticoTest.csv").getAbsolutePath());
		
		
		resulPartidos = Partido.buildListPartidosFromFile(new File (arch.toUri()));
		resulPronosticos = Pronostico.buildListPronostico(new File (arch2.toUri()), resulPartidos);
	}
	
	
	@DisplayName ("Puntaje de una persona en 2 rondas consecutivas")
	@Test
	public void Test1() throws IOException {
		int esperado=7;
		pts= this.puntajeUtilidad.puntos(resulPartidos, resulPronosticos);

		for(Puntaje puntos: pts) {
			Assertions.assertEquals(esperado,puntos.getPts());
		}
	}

	@DisplayName ("Nombre de persona correcto")
	@Test
	public void test2()throws IOException{
		String esperado="MARIANA";
		pts= this.puntajeUtilidad.puntos(resulPartidos, resulPronosticos);

		for(Puntaje puntos: pts) {
			Assertions.assertEquals(esperado,puntos.getNombre());
		}
	}
	
	@DisplayName ("Probando la suma del extra por fase")
	@Test
	public void test3()throws IOException{
		int esperado=14;
		
		Path arch3= Paths.get(new File ("src\\test\\resources\\PartidosTest2.csv").getAbsolutePath());
		Path arch4=Paths.get(new File ("src\\test\\resources\\PronosticoTest2.csv").getAbsolutePath());
		resulPartidos2 = Partido.buildListPartidosFromFile(new File (arch3.toUri()));
		resulPronosticos2 = Pronostico.buildListPronostico(new File (arch4.toUri()), resulPartidos2);
		
		pts2= this.puntajeUtilidad.puntos(resulPartidos2, resulPronosticos2);
		
		for(Puntaje puntos: pts2) {
			Assertions.assertEquals(esperado,puntos.getPts());
		}
	}
}
