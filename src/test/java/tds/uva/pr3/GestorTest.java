package tds.uva.pr3;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * Implementacion de los test del gestor de correo
 * 
 * @author mariher
 * @author marllor
 */
class GestorTest{

	private Correo correo1;
	private Correo correo2;
	private Correo correo3;
	private Correo correo4;
	private Correo correo5;
	
	@BeforeEach
	void testAntesDeCadaTest() {
		correo1 = new Correo("pepe@gmail.com","juan97@gmail.com",LocalTime.of(10, 23),LocalDate.of(2020, 9, 19),
				"Asunto1","Contenido1",EnumCategoria.enviado);
		correo2 = new Correo("luisPerez@gmail.com","pablofdez@gmail.com",LocalTime.of(9, 33),LocalDate.of(2020, 5, 2),
				"Asunto2","Contenido2",EnumCategoria.enviado);
		correo3 = new Correo("luisPerez@gmail.com","pablofdez@gmail.com",LocalTime.of(17, 23),LocalDate.of(2020, 10, 7),
				"Asunto1","Contenido3",EnumCategoria.borrador);
		correo4 = new Correo("pepe@gmail.com","juan97@gmail.com",LocalTime.of(17, 23),LocalDate.of(2020, 9, 19),
				"Asunto4","Contenido4",EnumCategoria.recibido);
		correo5 = new Correo("p@gmail.com","mm@gmail.com",LocalTime.of(10, 23),LocalDate.of(2020, 9, 19),
				"Asunto1","Contenido5",EnumCategoria.enviado);
	}
	
	/*
	 * Test válido del constructor de gestor
	 */
	@Test
	@Tag("TDD")
	@Tag("CreacionGestor")
	void testGestor() {
		Gestor gestor = new Gestor();
		assertNotNull(gestor);
	}
	
	/*
	 * Test válido del constructor de gestor a partir de una lista de correos
	 */
	@Test
	@Tag("TDD")
	@Tag("CreacionGestor")
	void testGestorConLista() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		ArrayList<Correo> listaCorreos = new ArrayList<>();
		listaCorreos.add(correoA);
		listaCorreos.add(correoB);
		Gestor gestor = new Gestor(listaCorreos);
		assertIterableEquals(gestor.getCorreos(),listaCorreos);
	}
	
	@Test
	@Tag("CreacionGestor")
	void testGestorConListaNull() {
		assertThrows(IllegalArgumentException.class,()->{Gestor gestor = new Gestor(null);});
	}
	
	@Test
	@Tag("CreacionGestor")
	void testGestorConListaVacia() {
		ArrayList<Correo> listaCorreos = new ArrayList<>();
		assertThrows(IllegalArgumentException.class,()->{Gestor gestor = new Gestor(listaCorreos);});
	}
	
	/*
	 * Test válido de addCorreo()
	 */
	@Test
	@Tag("TDD")
	@Tag("AnadirCorreo")
	void testAddCorreo() {
		Correo correo = correo1;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correo);
		assertTrue(gestor.inGestor(correo));
	}
	
	@Test
	@Tag("AnadirCorreo")
	void testAddCorreoNull() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.addCorreo(null);});
	}
	
	@Test
	@Tag("AnadirCorreo")
	void testAddCorreoYaEnGestor() {
		Correo correo = correo1;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correo);
		assertThrows(IllegalArgumentException.class,()->{gestor.addCorreo(correo);});
	}
	
	@Test
	@Tag("AnadirCorreo")
	void testAddCorreoRepetido() {
		Correo correoA = correo1;
		Correo correoB = correo5;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		assertThrows(IllegalArgumentException.class,()->{gestor.addCorreo(correoB);});
	}

	/*
	 * Test válido de getNumCorreos()
	 */
	@Test
	@Tag("TDD")
	void testGetNumCorreos() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		assertEquals(gestor.getNumCorreos(),2);
	}
	
	/*
	 * Test válido de getFechaMasReciente()
	 */
	@Test
	@Tag("TDD")
	void testGetFechaMasReciente() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		assertEquals(gestor.getFechaMasReciente(),correoC.getFecha());
	}
	
	/*
	 * Test válido de getFechaMasAntigua()
	 */
	@Test
	@Tag("TDD")
	void testGetFechaMasAntigua() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		assertEquals(gestor.getFechaMasAntigua(),correoB.getFecha());
	}
	
	/*
	 * Test válido de getOrdenCornologico()
	 */
	@Test
	@Tag("TDD")
	void testGetOrdenCornologico() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		
		
		ArrayList<Correo> listaOrdCronoligo = new ArrayList<>();
		
		listaOrdCronoligo.add(correoB);
		listaOrdCronoligo.add(correoA);
		listaOrdCronoligo.add(correoC);
		
		assertIterableEquals(gestor.getOrdenCornologico(), listaOrdCronoligo);
	}
	
	/*
	 * Test válido de getOrdenCategoria()
	 */
	@Test
	@Tag("TDD")
	void testGetOrdenCategoria() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		
		ArrayList<Correo> listaOrdCat = new ArrayList<>();
		listaOrdCat.add(correoB);
		listaOrdCat.add(correoA);
		listaOrdCat.add(correoC);
		
		assertIterableEquals(gestor.getOrdenCategoria(), listaOrdCat);
	}
	
	/*
	 * Test válido de getGestorCorreosPorFecha()
	 */
	@Test
	@Tag("TDD")
	void testGetGestorCorreosPorFecha() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Correo correoD = correo4;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		gestor.addCorreo(correoD);
		
		ArrayList<Correo> listaPorFecha = new ArrayList<>();
		listaPorFecha.add(correoA);
		listaPorFecha.add(correoD);
		
		Gestor gestor2 = new Gestor(listaPorFecha);
		
		assertIterableEquals(gestor.getGestorCorreosPorFecha(LocalDate.of(2020, 9, 19)).getCorreos(), gestor2.getCorreos());
	}

	@Test
	void testGetGestorCorreosPorFechaNull() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorFecha(null);});
	}
	

	/*
	 * Test válido de getGestorCorreosEntreFechas()
	 */
	@Test
	@Tag("TDD")
	@Tag("CorreosFechas")
	void testGetGestorCorreosEntreFechas() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Correo correoD = correo4;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		gestor.addCorreo(correoD);
				
		ArrayList<Correo> listaEntreFechas = new ArrayList<>();
		listaEntreFechas .add(correoA);
		listaEntreFechas .add(correoC);
		listaEntreFechas .add(correoD);
		
		Gestor gestor2 = new Gestor(listaEntreFechas);
		
		assertIterableEquals(gestor.getGestorCorreosEntreFechas(LocalDate.of(2020, 9, 15),
				LocalDate.of(2020, 10, 15)).getCorreos(), gestor2.getCorreos());
	}

	@Test
	@Tag("CorreosFechas")
	void tesGetGestorCorreosEntreFechasNull1() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosEntreFechas(null,
				LocalDate.of(2020, 10, 15));});
	}
	
	@Test
	@Tag("CorreosFechas")
	void tesGetGestorCorreosEntreFechasNull2() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosEntreFechas(LocalDate.of(2020, 9, 15),
				null);});
	}
	
	/*
	 * Test válido de getGestorCorreosPorCategoria()
	 */
	@Test
	@Tag("TDD")
	void testGetGestorCorreosPorCategoria() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Correo correoD = correo4;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		gestor.addCorreo(correoD);
		
		ArrayList<Correo> listaCatEnviados = new ArrayList<>();
		listaCatEnviados.add(correoA);
		listaCatEnviados.add(correoB);
		
		Gestor gestor2 = new Gestor(listaCatEnviados);
		
		assertIterableEquals(gestor.getGestorCorreosPorCategoria(EnumCategoria.enviado).getCorreos(), gestor2.getCorreos());
	}
	
	@Test
	void tesGetGestorCorreosPorCategoriaNull() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoria(null);});
	}
	
	/*
	 * Test válido de getGestorCorreosPorCategoriaYFecha()
	 */
	@Test
	@Tag("TDD")
	void testGetGestorCorreosPorCategoriaYFecha() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Correo correoD = correo4;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		gestor.addCorreo(correoD);
		
		ArrayList<Correo> listaCatyFecha = new ArrayList<>();
		listaCatyFecha.add(correoA);
		
		Gestor gestor2 = new Gestor(listaCatyFecha);
		
		assertIterableEquals(gestor.getGestorCorreosPorCategoriaYFecha(EnumCategoria.enviado,
				LocalDate.of(2020, 9, 19)).getCorreos(), gestor2.getCorreos());	
	}
	
	@Test
	void testGetGestorCorreosPorCategoriaNullYFecha() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaYFecha(null,
				LocalDate.of(2020, 9, 19));});
	}
	
	@Test
	void testGetGestorCorreosPorCategoriaYFechaNull() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaYFecha(EnumCategoria.enviado,
				null);});
	}
	
	/*
	 * Test válido de getGestorCorreosPorCategoriaEntreFechas()
	 */
	@Test
	@Tag("TDD")
	void testGetGestorCorreosPorCategoriaEntreFechas() {
		Correo correoA = correo1;
		Correo correoB = correo2;
		Correo correoC = correo3;
		Correo correoD = correo4;
		Gestor gestor = new Gestor();
		gestor.addCorreo(correoA);
		gestor.addCorreo(correoB);
		gestor.addCorreo(correoC);
		gestor.addCorreo(correoD);
		
		ArrayList<Correo> listaCatyEntreFechas = new ArrayList<>();
		listaCatyEntreFechas.add(correoA);
		listaCatyEntreFechas.add(correoB);
		
		Gestor gestor2 = new Gestor(listaCatyEntreFechas);
		
		assertIterableEquals(gestor.getGestorCorreosPorCategoriaEntreFechas(EnumCategoria.enviado,
				LocalDate.of(2020, 5, 1), LocalDate.of(2020, 10, 1)).getCorreos(), gestor2.getCorreos());
	}
	
	@Test
	void testGetGestorCorreosPorCategoriaNullYFechas() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaEntreFechas(null,
				LocalDate.of(2020, 5, 1), LocalDate.of(2020, 10, 1));});
	}
	
	@Test
	void testGetGestorCorreosPorCategoriaYFechasNull1() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaEntreFechas(EnumCategoria.enviado,
				null, LocalDate.of(2020, 10, 1));});
	}
	
	@Test
	void testGetGestorCorreosPorCategoriaYFechasNull2() {
		Gestor gestor = new Gestor();
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaEntreFechas(EnumCategoria.enviado,
				LocalDate.of(2020, 5, 1), null);});
	}
}