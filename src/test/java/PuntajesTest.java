import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import clases.Partido;
import clases.Pronostico;
import clases.Puntajes;

public class PuntajesTest {
	
	@DisplayName ("Puntaje de una persona en 2 rondas consecutivas")
	@Test
	public void Test1() throws IOException {
		Path arch= Paths.get(new File ("src\\test\\resources\\PartidosTest.csv").getAbsolutePath()); 
		Path arch2=Paths.get(new File ("src\\test\\resources\\PronosticoTest.csv").getAbsolutePath());
		int esperado=7;
		List<Partido> resulPartidos = Partido.buildListPartidosFromFile(new File (arch.toUri()));
		
		List<Pronostico> resulPronosticos = Pronostico.buildListPronostico(new File (arch2.toUri()), resulPartidos);
		
		List<Puntajes> pts=Puntajes.Puntos(resulPartidos, resulPronosticos, 1, 1, 2);
		
		
		for(Puntajes puntos: pts) {
			Assertions.assertEquals(esperado,puntos.getPts());
		}
		}
}
