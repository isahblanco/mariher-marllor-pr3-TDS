package tds.uva.pr3;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.time.LocalDate;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/*
 * Implementación de los test de Gestor con Mock Objects
 * @author mariher
 * @author marllor
 */
@ExtendWith(EasyMockExtension.class)
class GestorMockTest {
	
	@TestSubject
	Gestor gestor;

	@Mock
	Correo correo1;
	
	@Mock
	Correo correo2;
	
	@Mock
	Correo correo3;

	@Mock
	Correo correo4;
	
	@Test
	@Tag("Isolation")
	void testGestorConListaNull() {
		assertThrows(IllegalArgumentException.class,()->{Gestor g = new Gestor(null);;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorConListaVacia() {
		ArrayList<Correo> correos = new ArrayList<>();
		assertThrows(IllegalArgumentException.class,()->{Gestor g = new Gestor(correos);;});
	}

	@Test
	@Tag("Isolation")
	void testAddCorreoValido() {
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		assertTrue(gestor.inGestor(correo1));
		EasyMock.verify(correo1);
	}
	
	@Test
	@Tag("Isolation")
	void testAddCorreoNull() {
		assertThrows(IllegalArgumentException.class,()->{gestor.addCorreo(null);;});
	}
	
	@Test
	@Tag("Isolation")
	void testAddCorreoYaEnGestor() {
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		assertThrows(IllegalArgumentException.class,()->{gestor.addCorreo(correo1);;});
	}
	
	@Test
	@Tag("Isolation")
	void testAddCorreoIguales() {
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(true);
		EasyMock.replay(correo1);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo1);
		assertThrows(IllegalArgumentException.class,()->{gestor.addCorreo(correo2);;});
	}
	
	@Test
	@Tag("Isolation")
	void testGetNumCorreos() {
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		assertEquals(gestor.getNumCorreos(),2);
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
	}
	
	@Test
	@Tag("Isolation")
	void testGetCorreos() {
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		
		ArrayList<Correo> correos = new ArrayList<>();
		correos.add(correo1);
		correos.add(correo2);
		assertIterableEquals(gestor.getCorreos(), correos);
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
	}
	
	@Test
	@Tag("Isolation")
	void testgetFechaMasReciente() {
		EasyMock.expect(correo1.getFecha()).andReturn(LocalDate.of(2020, 9, 19)).times(1);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getFecha()).andReturn(LocalDate.of(2020, 11, 2)).times(2);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		EasyMock.expect(correo3.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo3.comparaIgual(correo2)).andReturn(false);
		EasyMock.expect(correo3.getFecha()).andReturn(LocalDate.of(2019, 1, 29)).times(1);
		EasyMock.replay(correo3);
		gestor.addCorreo(correo3);
		assertEquals(gestor.getFechaMasReciente(),LocalDate.of(2020, 11, 2));
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
		EasyMock.verify(correo3);
	}
	
	@Test
	@Tag("Isolation")
	void testgetFechaMasAntigua() {
		EasyMock.expect(correo1.getFecha()).andReturn(LocalDate.of(2020, 9, 19)).times(1);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getFecha()).andReturn(LocalDate.of(2020, 11, 2)).times(1);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		EasyMock.expect(correo3.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo3.comparaIgual(correo2)).andReturn(false);
		EasyMock.expect(correo3.getFecha()).andReturn(LocalDate.of(2019, 1, 29)).times(2);
		EasyMock.replay(correo3);
		gestor.addCorreo(correo3);
		assertEquals(gestor.getFechaMasAntigua(),LocalDate.of(2019, 1, 29));
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
		EasyMock.verify(correo3);
	}
	
	@Test
	@Tag("Isolation")
	void testgetOrdenCronologico() {
		EasyMock.expect(correo1.getHora()).andReturn(LocalTime.of(17, 23)).times(1);
		EasyMock.expect(correo1.getFecha()).andReturn(LocalDate.of(2020, 12, 19)).times(1);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getHora()).andReturn(LocalTime.of(10, 23)).times(1);
		EasyMock.expect(correo2.getFecha()).andReturn(LocalDate.of(2020, 12, 19)).times(1);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		
		ArrayList<Correo> correos = new ArrayList<>();
		correos.add(correo2);
		correos.add(correo1);
		assertIterableEquals(gestor.getOrdenCornologico(), correos);
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
	}
	
	@Test
	@Tag("Isolation")
	void testgetOrdenCategoria() {
		EasyMock.expect(correo1.getFecha()).andReturn(LocalDate.of(2020, 11, 19)).times(1);
		EasyMock.expect(correo1.getCategoria()).andReturn(EnumCategoria.recibido).times(1);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getFecha()).andReturn(LocalDate.of(2020, 12, 19)).times(1);
		EasyMock.expect(correo2.getCategoria()).andReturn(EnumCategoria.enviado).times(1);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		
		ArrayList<Correo> correos = new ArrayList<>();
		correos.add(correo2);
		correos.add(correo1);
		assertIterableEquals(gestor.getOrdenCategoria(), correos);
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorFecha() {
		EasyMock.expect(correo1.getFecha()).andReturn(LocalDate.of(2020, 11, 19)).times(1);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getFecha()).andReturn(LocalDate.of(2020, 11, 19)).times(1);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		EasyMock.expect(correo3.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo3.comparaIgual(correo2)).andReturn(false);
		EasyMock.expect(correo3.getFecha()).andReturn(LocalDate.of(2019, 1, 20)).times(1);
		EasyMock.replay(correo3);
		gestor.addCorreo(correo3);
		
		ArrayList<Correo> correosPorFecha = new ArrayList<>();
		correosPorFecha.add(correo1);
		correosPorFecha.add(correo2);
		Gestor g = new Gestor(correosPorFecha);
		assertIterableEquals(gestor.getGestorCorreosPorFecha(LocalDate.of(2020, 11, 19)).getCorreos(), g.getCorreos());
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
		EasyMock.verify(correo3);
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorFechaNull() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorFecha(null);;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosEntreFechas() {
		EasyMock.expect(correo1.getFecha()).andReturn(LocalDate.of(2020, 11, 19)).times(2);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getFecha()).andReturn(LocalDate.of(2020, 12, 31)).times(2);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		EasyMock.expect(correo3.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo3.comparaIgual(correo2)).andReturn(false);
		EasyMock.expect(correo3.getFecha()).andReturn(LocalDate.of(2019, 1, 20)).times(1);
		EasyMock.replay(correo3);
		gestor.addCorreo(correo3);
		
		ArrayList<Correo> correosEntreFechas = new ArrayList<>();
		correosEntreFechas.add(correo1); 
		Gestor g = new Gestor(correosEntreFechas);
		assertIterableEquals(gestor.getGestorCorreosEntreFechas(LocalDate.of(2020, 6, 1),
				LocalDate.of(2020, 12, 1)).getCorreos(), g.getCorreos());
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
		EasyMock.verify(correo3);
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorFecha1Null() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosEntreFechas(null, 
				LocalDate.of(2020, 12, 1));;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorFecha2Null() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosEntreFechas(LocalDate.of(2020, 6, 1), 
				null);;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoria() {
		EasyMock.expect(correo1.getCategoria()).andReturn(EnumCategoria.recibido).times(1);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getCategoria()).andReturn(EnumCategoria.enviado).times(1);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		EasyMock.expect(correo3.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo3.comparaIgual(correo2)).andReturn(false);
		EasyMock.expect(correo3.getCategoria()).andReturn(EnumCategoria.recibido).times(1);
		EasyMock.replay(correo3);
		gestor.addCorreo(correo3);
		
		ArrayList<Correo> correosPorCat = new ArrayList<>();
		correosPorCat.add(correo1);
		correosPorCat.add(correo3);
		Gestor g = new Gestor(correosPorCat);
		assertIterableEquals(gestor.getGestorCorreosPorCategoria(EnumCategoria.recibido).getCorreos(), g.getCorreos());
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
		EasyMock.verify(correo3);
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoriaNull() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoria(null);;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoriaYFecha() {
		EasyMock.expect(correo1.getCategoria()).andReturn(EnumCategoria.recibido).times(1);
		EasyMock.expect(correo1.getFecha()).andReturn(LocalDate.of(2020, 11, 19)).times(1);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getCategoria()).andReturn(EnumCategoria.enviado).times(1);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		EasyMock.expect(correo3.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo3.comparaIgual(correo2)).andReturn(false);
		EasyMock.expect(correo3.getCategoria()).andReturn(EnumCategoria.recibido).times(1);
		EasyMock.expect(correo3.getFecha()).andReturn(LocalDate.of(2020, 7, 3)).times(1);
		EasyMock.replay(correo3);
		gestor.addCorreo(correo3);
		
		ArrayList<Correo> correosPorCatYFecha = new ArrayList<>();
		correosPorCatYFecha.add(correo1);
		Gestor g = new Gestor(correosPorCatYFecha);
		assertIterableEquals(gestor.getGestorCorreosPorCategoriaYFecha(EnumCategoria.recibido, 
				LocalDate.of(2020, 11, 19)).getCorreos(), g.getCorreos());
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
		EasyMock.verify(correo3);
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoriaNullYFecha() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaYFecha(null,
				LocalDate.of(2020, 11, 19));;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoriaYFechaNull() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaYFecha(EnumCategoria.recibido,
				null);;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoriaEntreFechas() {
		EasyMock.expect(correo1.getCategoria()).andReturn(EnumCategoria.recibido).times(1);
		EasyMock.expect(correo1.getFecha()).andReturn(LocalDate.of(2020, 12, 19)).times(2);
		EasyMock.replay(correo1);
		gestor.addCorreo(correo1);
		EasyMock.expect(correo2.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo2.getCategoria()).andReturn(EnumCategoria.enviado).times(1);
		EasyMock.replay(correo2);
		gestor.addCorreo(correo2);
		EasyMock.expect(correo3.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo3.comparaIgual(correo2)).andReturn(false);
		EasyMock.expect(correo3.getCategoria()).andReturn(EnumCategoria.recibido).times(1);
		EasyMock.expect(correo3.getFecha()).andReturn(LocalDate.of(2020, 7, 3)).times(2);
		EasyMock.replay(correo3);
		gestor.addCorreo(correo3);
		EasyMock.expect(correo4.comparaIgual(correo1)).andReturn(false);
		EasyMock.expect(correo4.comparaIgual(correo2)).andReturn(false);
		EasyMock.expect(correo4.comparaIgual(correo3)).andReturn(false);
		EasyMock.expect(correo4.getCategoria()).andReturn(EnumCategoria.recibido).times(1);
		EasyMock.expect(correo4.getFecha()).andReturn(LocalDate.of(2020, 1, 3)).times(1);
		EasyMock.replay(correo4);
		gestor.addCorreo(correo4);
		
		ArrayList<Correo> correosPorCatEntreFechas = new ArrayList<>();
		correosPorCatEntreFechas.add(correo3);
		Gestor g = new Gestor(correosPorCatEntreFechas);
		assertIterableEquals(gestor.getGestorCorreosPorCategoriaEntreFechas(EnumCategoria.recibido,
				LocalDate.of(2020, 2, 1),LocalDate.of(2020, 12, 1)).getCorreos(), g.getCorreos());
		EasyMock.verify(correo1);
		EasyMock.verify(correo2);
		EasyMock.verify(correo3);
		EasyMock.verify(correo4);
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoriaNullEntreFechas() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaEntreFechas(null,
				LocalDate.of(2020, 1, 1),LocalDate.of(2020, 12, 1));;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoriaEntreFechasNull1() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaEntreFechas(EnumCategoria.recibido,
				null,LocalDate.of(2020, 12, 1));;});
	}
	
	@Test
	@Tag("Isolation")
	void testGestorCorreosPorCategoriaEntreFechasNull2() {
		assertThrows(IllegalArgumentException.class,()->{gestor.getGestorCorreosPorCategoriaEntreFechas(EnumCategoria.recibido,
				LocalDate.of(2020, 1, 1),null);;});
	}
}
