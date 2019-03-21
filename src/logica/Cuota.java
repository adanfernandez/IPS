package logica;

import java.sql.Date;

public class Cuota {
	private int precio;
	private Date fecha_fin;
	private Date fecha_inicio;
	
	public Cuota( Date fecha_inicio, Date fecha_fin,int descuento) {
		super();
		this.setDescuento(descuento);
		this.fecha_fin = fecha_fin;
		this.fecha_inicio = fecha_inicio;
	}

	public boolean entraEnTarifa(Date inscripcion) {
		return((inscripcion.after(fecha_inicio)&&inscripcion.before(fecha_fin))
				||(inscripcion.equals(fecha_fin)||(inscripcion.equals(fecha_inicio))));
	}

	public int getDescuento() {
		return precio;
	}

	public void setDescuento(int precio) {
		this.precio = precio;
	}public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public boolean incluyeLaCuota(Date date1, Date date2) {
		if(date1.before(fecha_inicio)&&date2.after(fecha_fin)) {
			return true;
		}return false;
	}
}
