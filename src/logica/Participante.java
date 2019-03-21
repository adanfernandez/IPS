package logica;

import java.sql.Date;
import java.util.ArrayList;

public class Participante implements Comparable<Participante> {

	private Corredor corredor;
	private Date fechaInscripcion;
	private String estado;
	private double tiempo;
	private int identificador;
	private int dorsal;
	private boolean ispagado;
	private Categoria categoria;
	private Club club;
	private Carrera carrera;
	private int posicion;
	public Participante(Corredor corredor, Date fechaInscripcion,
			String estado, double tiempo, int identificador, int dorsal,
			int posicion,String club) {
		super();
		
		this.setPosicion(posicion);
		this.corredor = corredor;
		this.fechaInscripcion = fechaInscripcion;
		this.estado = estado;
		if (estado.equals("PAGADO")) {
			ispagado = true;
		}
		this.tiempo = tiempo;
		this.identificador = identificador;
		this.dorsal = dorsal;
		this.club=new Club(club);
	
//		asignarCategoria();
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public int getDorsal() {
		return dorsal;
	}

	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}

	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}

	/**
	 * Metodo que compara dos corredores por tiempo
	 */
	@Override
	public int compareTo(Participante arg0) {

		if (getTiempo() < arg0.getTiempo()) {
			return -1;
		} else if (getTiempo() == arg0.getTiempo()) {
			return 0;
		}
		return 1;
	}

	public Corredor getCorredor() {
		return corredor;
	}

	public Date getFechaRegistro() {
		return fechaInscripcion;
	}

	public double getTiempo() {
		return tiempo;
	}

	public int getIdentificador() {
		return identificador;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isPagado() {
		return ispagado;
	}

	public void setIspagado(boolean ispagado) {
		this.ispagado = ispagado;
	}

	public void setCategoria(Categoria categoria) {

		this.categoria = categoria;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

//	/**
//	 * Igual hay que pasarlo a carrera
//	 */
//	private void asignarCategoria() {
//		ArrayList<Categoria> categorias = carrera.getCategorias();
//		for (int i = 0; i < carrera.getCategorias().size(); i++) {
//			for (int j = 0; j < categorias.size(); j++) {
//				if (corredor.getEdad() >= categorias.get(j).getEdadi()
//						&& corredor.getEdad() <= categorias.get(j).getEdadf())
//
//					setCategoria(categorias.get(j));
//
//			}
//		}
//	}
}
