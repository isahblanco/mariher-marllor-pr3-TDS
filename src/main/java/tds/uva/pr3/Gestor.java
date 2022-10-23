package tds.uva.pr3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implementacion de un gestor de correo
 * @author mariher
 * @author marllor
 */
public class Gestor {
	
	private ArrayList<Correo> correos;
	private ArrayList<Correo> correosPorFecha;
	private ArrayList<Correo> correosEntreFechas;
	private ArrayList<Correo> correosPorCat;
	private ArrayList<Correo> correosPorCatYFecha;
	private ArrayList<Correo> correosPorCatEntreFechas;
	
	/**
	 * Inicialización del gestor
	 */
	public Gestor() {
		correos =  new ArrayList<>();
	}
	
	/**
	 * Inicialización del gestor con una lista de correos
	 * @param listaCorreos Lista de correos del gestor
	 * @throws IllegalArgumentException si la lista es null
	 * @throws IllegalArgumentException si la lista está vacía
	 */
	public Gestor(ArrayList<Correo> listaCorreos) {
		if(listaCorreos == null) {
			throw new IllegalArgumentException("Lista de correos null");
		}
		if(listaCorreos.isEmpty()) {
			throw new IllegalArgumentException("Lista de correos vacía");
		}
		
		correos = listaCorreos;
	}

	/**
	 * Añade un nuevo correo al gestor
	 * @param c Correo que se añade
	 * @throws IllegalArgumentException si el correo es null
	 * @throws IllegalArgumentException si el correo ya está en el gestor
	 * @throws IllegalArgumentException si el correo está repetido, es igual otro
	 */
	public void addCorreo(Correo c) {
		if(c == null) {
			throw new IllegalArgumentException("Correo null");
		}
		if(correos.contains(c)) {
			throw new IllegalArgumentException("Correo ya en gestor");
		}
		for(int i = 0; i < correos.size(); i++) {
			if(c.comparaIgual(correos.get(i))) {
				throw new IllegalArgumentException("Correo igual a otro");
			}
		}
		correos.add(c); 
	}
	
	/**
	 * Comprueba si un correo está o no en el gestor
	 * @return true si está, false si no
	 * @throws IllegalArgumentException si el correo es null
	 */
	protected boolean inGestor(Correo c) {
		return  correos.contains(c);
	}
	
	/**
	 * Devuelve el numero de correos que hay en el gestor
	 * @return numero de correos
	 */
	public int getNumCorreos() {
		return correos.size();
	}
	
	/**
	 * Devuelve la lista de correos del gestor
	 * @return lista de correos del gestor
	 */
	public ArrayList<Correo> getCorreos() {
		return correos;
	}
	
	/**
	 * Devuelve la fecha del correo más reciente
	 * @return fecha del correo más reciente
	 */
	public LocalDate getFechaMasReciente() {
		LocalDate fecha = correos.get(0).getFecha();
		for(int i = 1; i < correos.size(); i++) {
			if(fecha.isBefore(correos.get(i).getFecha())) {
				fecha = correos.get(i).getFecha();
			}
		}
		return fecha;
	}
	
	/**
	 * Devuelve la fecha del correo más antiguo
	 * @return fecha del correo más antiguo
	 */
	public LocalDate getFechaMasAntigua() {
		LocalDate fecha = correos.get(0).getFecha();
		for(int i = 1; i < correos.size(); i++) {
			if(fecha.isAfter(correos.get(i).getFecha())) {
				fecha = correos.get(i).getFecha();
			}
		}
		return fecha;
	}
	
	/**
	 * Devuelve la lista de correos en orden cronológico,
	 * de anterior a posterior
	 * @return lista de correos en orden cronológico
	 */
	public ArrayList<Correo> getOrdenCornologico() {
		correos.sort(Comparator.comparing(Correo::getHora));
		correos.sort(Comparator.comparing(Correo::getFecha));
		return correos;
	}
	
	/**
	 * Devuelve la lista de correos por orden de categoría,
	 * en orden de enviado, recibido, borrador y papelera
	 * @return lista de correos por orden de categoría
	 */
	public ArrayList<Correo> getOrdenCategoria() {
		correos.sort(Comparator.comparing(Correo::getFecha));
		correos.sort(Comparator.comparing(Correo::getCategoria));
		return correos;
	}
	
	/**
	 * Devuelve un gestor con la lista de correos publicados en una fecha concreta
	 * @param fecha Fecha de los correos que habrá en la lista
	 * @return gestor con los correos que haya de esa fecha
	 * @throws IllegalArgumentException si la fecha es null
	 */
	public Gestor getGestorCorreosPorFecha(LocalDate fecha) {
		if (fecha == null) {
			throw new IllegalArgumentException("Fecha null");
		}
		correosPorFecha = new ArrayList<>();
		for (int i = 0; i < correos.size(); i++) {
			if (correos.get(i).getFecha().equals(fecha)) {
				correosPorFecha.add(correos.get(i));
			}
		}
		Gestor gestor = new Gestor(correosPorFecha);
		return gestor; 
	}
	
	/**
	 * Devuelve un gestor con la lista de correos publicados dentro de un intervalo de fechas
	 * @param fecha1 Fecha mínima del intervalo
	 * @param fecha2 Fecha máxima del intervalo
	 * @return gestor con los correos publicados dentro del intervalo de fechas
	 * @throws IllegalArgumentException si alguna de las fechas es null
	 */
	public Gestor getGestorCorreosEntreFechas(LocalDate fecha1, LocalDate fecha2) {
		if (fecha1 == null) {
			throw new IllegalArgumentException("Primera fecha null");
		}
		if (fecha2 == null) {
			throw new IllegalArgumentException("Segunda fecha null");
		}
		correosEntreFechas = new ArrayList<>(); 
		for (int i = 0; i < correos.size(); i++) {
			if (correos.get(i).getFecha().isAfter(fecha1) && correos.get(i).getFecha().isBefore(fecha2)) {
				correosEntreFechas.add(correos.get(i));
			}
		}
		Gestor gestor = new Gestor(correosEntreFechas);
		return gestor;
	}
	
	/**
	 * Devuelve un gestor con la lista de correos publicados de una categoria dada
	 * @param categoria Categoria de los correos de la lista
	 * @return gestor con los correos publicados de la categoria especificada
	 * @throws IllegalArgumentException si la categoria es null
	 */
	public Gestor getGestorCorreosPorCategoria(EnumCategoria categoria) {
		if (categoria == null) {
			throw new IllegalArgumentException("Categoria null");
		}
		correosPorCat = new ArrayList<>();
		for (int i = 0; i < correos.size(); i++) {
			if (correos.get(i).getCategoria().equals(categoria) ) {
				correosPorCat.add(correos.get(i));
			}
		}
		Gestor gestor = new Gestor(correosPorCat);
		return gestor;
	}
	
	/**
	 * Devuelve un gestor con la lista de correos publicados de una categoría y fecha dada
	 * @param categoria Categoría de los correos a estar en la lista
	 * @param fecha Fecha de los correos a estar en la lista
	 * @return gestor con los correos publicados de esa categoría y fecha
	 * @throws IllegalArgumentException si la categoria es null
	 * @throws IllegalArgumentException si la fecha es null
	 */
	public Gestor getGestorCorreosPorCategoriaYFecha(EnumCategoria categoria, LocalDate fecha) {
		if (categoria == null) {
			throw new IllegalArgumentException("Categoria null");
		}
		if (fecha == null) {
			throw new IllegalArgumentException("Fecha null");
		}
		
		correosPorCatYFecha = new ArrayList<>();
		for (int i = 0; i < correos.size(); i++) {
			if (correos.get(i).getCategoria().equals(categoria) && correos.get(i).getFecha().equals(fecha)) {
				correosPorCatYFecha.add(correos.get(i));
			}
		}
		Gestor gestor = new Gestor(correosPorCatYFecha);
		return gestor;
	}
	
	/**
	 * Devuelve un gestor con la lista de correos publicados de una categoría e intervalo de fechas dados
	 * @param categoria Categoría de los correos a estar en la lista
	 * @param fecha1 Fecha mínima del intervalo
	 * @param fecha2 Fecha máxima del intervalo
	 * @return gestor con los correos publicados de esa categoría y dentro del intervalo de fechas dado
	 * @throws IllegalArgumentException si la categoria es null
	 * @throws IllegalArgumentException si alguna de las fechas es null
	 */
	public Gestor getGestorCorreosPorCategoriaEntreFechas(EnumCategoria categoria, LocalDate fecha1, LocalDate fecha2) {
		if (categoria == null) {
			throw new IllegalArgumentException("Categoria null");
		}
		if (fecha1 == null) {
			throw new IllegalArgumentException("Primera fecha null");
		}
		if (fecha2 == null) {
			throw new IllegalArgumentException("Segunda fecha null");
		}
		correosPorCatEntreFechas = new ArrayList<>();
		for (int i = 0; i < correos.size(); i++) {
			if (correos.get(i).getCategoria().equals(categoria) && (correos.get(i).getFecha().isAfter(fecha1) && 
					correos.get(i).getFecha().isBefore(fecha2))) {
				correosPorCatEntreFechas.add(correos.get(i));
			}
		}
		Gestor gestor = new Gestor(correosPorCatEntreFechas);
		return gestor;
	}
}