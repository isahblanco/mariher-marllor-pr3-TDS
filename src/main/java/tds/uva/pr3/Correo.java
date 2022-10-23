package tds.uva.pr3;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.StringTokenizer;  
/**
 * Implementacion de un correo
 * 
 * @author mariher
 * @author marllor
 */

public class Correo {
	private String Origen;
	private String Destino;
	private LocalTime hora;
	private LocalDate dia;
	private String Asunto;
	private String Contenido;
	private EnumCategoria Categoria;

	/**
	 * Constructor objeto correo
	 * 
	 * @param origen    email de origen
	 * @param destino   email de destino
	 * @param hora      hora de envío
	 * @param dia       día de envío
	 * @param Asunto    Asunto del correo
	 * @param Contenido Contenido del correo
	 * @param categoria categoría del mensaje
	 * @throws IllegalArgumentException En caso de correo erróneo ; de un asunto
	 *                                  vacío o con más de 10 palabras; o en caso de
	 *                                  que la hora o el día sean null
	 */
	public Correo(String origen, String destino, LocalTime hora, LocalDate dia, String Asunto, String Contenido,
			EnumCategoria categoria) {
		if (origen == null || destino == null || hora == null || dia == null || Asunto == null || Contenido == null
				|| categoria == null) {
			throw new IllegalArgumentException("Argumento nulo");
		}
		if (!EmailValido(origen)) {
			throw new IllegalArgumentException("Dirección de origen inválida");
		}
		if (!EmailValido(destino)) {
			throw new IllegalArgumentException("Dirección de destino inválida");
		}
		if (!AsuntoValido(Asunto)) {
			throw new IllegalArgumentException("Formato de asunto inválido");
		}
		this.Origen = origen;
		this.Destino = destino;
		this.hora = hora;
		this.dia = dia;
		this.Asunto = Asunto;
		this.Contenido = Contenido;
		this.Categoria = categoria;
	}

	/**
	 * Devuelve el email de origen del correo
	 * 
	 * @return email de origen
	 */
	public String getOrigen() {
		return Origen;
	}

	/**
	 * Devuelve el email de destino del correo
	 * 
	 * @return email de destino
	 */
	public String getDestino() {
		return Destino;
	}

	/**
	 * Devuelve el asunto del correo
	 * 
	 * @return asunto del correo
	 */
	public String getAsunto() {
		return Asunto;
	}

	/**
	 * Devuelve la hora del correo
	 * 
	 * @return hora del correo
	 */
	public LocalTime getHora() {
		return hora;
	}

	/**
	 * Devueve la fecha del correo
	 * 
	 * @return fecha del correo
	 */
	public LocalDate getFecha() {
		return dia;
	}

	/**
	 * Devuelve el contenido del correo
	 * 
	 * @return contenido del correo
	 */
	public String getContenido() {
		return Contenido;
	}

	/**
	 * Devuelve la categoria del correo
	 * 
	 * @return categoria del correo
	 */
	public EnumCategoria getCategoria() {
		return Categoria;
	}

	/**
	 * Compara dos correos y define si el comparado es posterior, anterior, o igual
	 * al objeto
	 * 
	 * @param correo2 correo con el que comparar
	 * @return posterior si se envió mas tarde, anterior si se envió antes,igual si
	 *         se envió en el mismo momento
	 * @throws IllegalArgumentException si el correo a comparar es null
	 */
	public String comparaTiempo(Correo correo2) {
		if(correo2==null) {
			throw new IllegalArgumentException("Argumento nulo");
		}
		if (correo2.getFecha().isAfter(this.dia)) {
			return "posterior";
		} else if (correo2.getFecha().isBefore(this.dia)) {
			return "anterior";
		} else if (correo2.getFecha().isEqual(this.dia)) {
			if (correo2.getHora().isAfter(this.hora)) {
				return "posterior";
			} else if (correo2.getHora().isBefore(this.hora)) {
				return "anterior";
			} else {
				return "igual";
			}
		}
		return "igual";
	}

	/**
	 * Compara dos correos y define si estos son iguales en base a unos criterios
	 * (fecha,hora,asunto y categoría)
	 * 
	 * @param correo2 correo con el que comparar
	 * @return true si son iguales, false si no
	 * @throws IllegalArgumentException si el correo a comparar es null
	 */
	public Boolean comparaIgual(Correo correo2) {
		if(correo2==null) {
			throw new IllegalArgumentException("Argumento nulo");
		}
		if(correo2.getFecha().equals(this.dia)&&correo2.getHora().equals(this.hora)&&correo2.getAsunto().equals(this.Asunto)&&correo2.getCategoria().equals(this.Categoria)) {
			return true;
		}
		return false;
	}

	private boolean EmailValido(String email) {
		String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		return email.matches(regex);
	}

	private boolean AsuntoValido(String Asunto) {
		if(Asunto.isBlank()) {
			return false;
		}
		StringTokenizer AsuntoToken=new StringTokenizer(Asunto);
		if(AsuntoToken.countTokens()>10) {
			return false;
		}
		return true;
	}
}