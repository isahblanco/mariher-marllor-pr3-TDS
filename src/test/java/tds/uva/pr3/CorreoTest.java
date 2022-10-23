package tds.uva.pr3;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Implementacion de los test de correo
 * 
 * @author mariher
 * @author marllor
 */
class CorreoTest {
	/*
	 * Test de creacion de un objeto correo
	 */
	@Test
	@Tag("TDD")
	@Tag("CreacionCorreo")
	void createNewCorreoTest() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		LocalDate dia = LocalDate.of(2020, 10, 20);
		LocalTime hora = LocalTime.of(12, 12, 12);
		assertEquals("example@mail.com", correo1.getOrigen());
		assertEquals("example1@mail.com", correo1.getDestino());
		assertEquals(dia, correo1.getFecha());
		assertEquals(hora, correo1.getHora());
		assertEquals("Asunto", correo1.getAsunto());
		assertEquals("Contenido", correo1.getContenido());
		assertEquals(EnumCategoria.enviado, correo1.getCategoria());
	}

	/*
	 * Test de creación de un objeto correo con un email de origen inválido Devuelve
	 * IllegalArgumentException
	 */
	@Test
	@Tag("CreacionCorreo")
	void createNewCorreoTestFailOrigen() {
		assertThrows(IllegalArgumentException.class, () -> {
			Correo correo1 = new Correo(".abc@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
					LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		});
	}

	/*
	 * Test de creación de un objeto correo con un email de destino inválido
	 * Devuelve IllegalArgumentException
	 */
	@Test
	@Tag("CreacionCorreo")
	void createNewCorreoTestFailDestino() {
		assertThrows(IllegalArgumentException.class, () -> {
			Correo correo1 = new Correo("example@mail.com", "abc#def@mail.com", LocalTime.of(12, 12, 12),
					LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		});
	}

	/*
	 * Test de creación de un objeto correo con un objeto null como hora Devuelve
	 * IllegalArgumentException
	 */
	@Test
	@Tag("CreacionCorreo")
	void createNewCorreoTestFailHoraNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			Correo correo1 = new Correo("example@mail.com", "example1@mail.com", null, LocalDate.of(2020, 10, 20), "Asunto",
					"Contenido", EnumCategoria.enviado);
		});
	}

	/*
	 * Test de creación de un objeto correo con un objeto null como dia Devuelve
	 * IllegalArgumentException
	 */
	@Test
	@Tag("CreacionCorreo")
	void createNewCorreoTestFailDiaNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12), null, "Asunto",
					"Contenido", EnumCategoria.enviado);
		});
	}

	/*
	 * Test de creación de un objeto correo con un asunto vacío Devuelve
	 * IllegalArgumentException
	 */
	@Test
	@Tag("CreacionCorreo")
	void createNewCorreoTestFailAsuntoSinPalabras() {
		assertThrows(IllegalArgumentException.class, () -> {
			Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
					LocalDate.of(2020, 10, 20), "", "Contenido", EnumCategoria.enviado);
		});
	}

	/*
	 * Test de creación de un objeto correo con un asunto de más de diez palabras
	 * Devuelve IllegalArgumentException
	 */
	@Test
	@Tag("CreacionCorreo")
	void createNewCorreoTestFailAsuntoDemasiadasPalabras() {
		assertThrows(IllegalArgumentException.class, () -> {
			Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
					LocalDate.of(2020, 10, 20), "En un lugar de la mancha de cuyo nombre no quiero acordarme",
					"Contenido", EnumCategoria.enviado);
		});
	}

	/*
	 * Test de comparación de fechas de un correo, siendo el segundo posterior al
	 * primero
	 */
	@Test
	@Tag("TDD")
	@Tag("ComparaTiempo")
	void CorreoTestFechaMayor() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		Correo correo2 = new Correo("example2@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 12, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		assertEquals(correo1.comparaTiempo(correo2), "posterior");
	}

	/*
	 * Test de comparación de fechas de un correo, siendo el segundo anterior al
	 * primero
	 */
	@Test
	@Tag("ComparaTiempo")
	void CorreoTestFechaMenor() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		Correo correo2 = new Correo("example2@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 9, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		assertEquals(correo1.comparaTiempo(correo2), "anterior");
	}

	/*
	 * Test de comparación de fechas de un correo, siendo el segundo posterior al
	 * primero
	 */
	@Test
	@Tag("ComparaTiempo")
	void CorreoTestHoraMayor() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		Correo correo2 = new Correo("example2@mail.com", "example1@mail.com", LocalTime.of(13, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		assertEquals(correo1.comparaTiempo(correo2), "posterior");
	}

	/*
	 * Test de comparación de fechas de un correo, siendo el segundo anterior al
	 * primero
	 */
	@Test
	@Tag("ComparaTiempo")
	void CorreoTestHoraMenor() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		Correo correo2 = new Correo("example2@mail.com", "example1@mail.com", LocalTime.of(11, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		assertEquals(correo1.comparaTiempo(correo2), "anterior");
	}

	/*
	 * Test de comparación de fechas de un correo, siendo el segundo enviado al
	 * mismo tiempo al primero
	 */
	@Test
	@Tag("ComparaTiempo")
	void CorreoTestTiempoIgual() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		Correo correo2 = new Correo("example2@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		assertEquals(correo1.comparaTiempo(correo2), "igual");
	}

	/*
	 * Test de comparación de correos, siendo estos dos iguales según los criterios
	 * (fecha,hora,asunto y categoría)
	 */
	@Test
	@Tag("TDD")
	@Tag("ComparaCorreo")
	void CorreoTestIgual() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		Correo correo2 = new Correo("example2@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		assertTrue(correo1.comparaIgual(correo2));
	}

	/*
	 * Test de comparación de correos, siendo estos dos diferentes según los
	 * criterios (fecha,hora,asunto y categoría)
	 */
	@Test
	@Tag("ComparaCorreo")
	void CorreoTestNoIgual() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		Correo correo2 = new Correo("example2@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto diferente", "Contenido", EnumCategoria.enviado);
		assertFalse(correo1.comparaIgual(correo2));
	}
	/*
	 * Test de comparación de tiempo de correo con null
	 * Devuelve IllegalArgumentException
	 */
	@Test
	@Tag("ComparaTiempo")
	void CorreoTestFailCompararTiempoNull() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		assertThrows(IllegalArgumentException.class, () -> {
			correo1.comparaTiempo(null);
		});
	}
	/*
	 * Test de comparación de correo con null
	 * Devuelve IllegalArgumentException
	 */
	@Test
	@Tag("ComparaCorreo")
	void CorreoTestFailCompararCorreoNull() {
		Correo correo1 = new Correo("example@mail.com", "example1@mail.com", LocalTime.of(12, 12, 12),
				LocalDate.of(2020, 10, 20), "Asunto", "Contenido", EnumCategoria.enviado);
		assertThrows(IllegalArgumentException.class, () -> {
			correo1.comparaIgual(null);
		});
	}
}
