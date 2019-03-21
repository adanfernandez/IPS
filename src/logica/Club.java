package logica;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Club {
	
	
	private List<Corredor> club;
	private int descuento;
	private String nombreClub;
	private String carreraEnLaQueParticipa;
	private Date FechaEnLaQueParticipa;
	
	
	public Club(String nombreCarrera,Date dateCarrera,String nombre ,List<Corredor> club,int descuento){
		this.club= club;
		this.nombreClub = nombre;
		carreraEnLaQueParticipa = nombreCarrera;
		FechaEnLaQueParticipa = dateCarrera;
		this.descuento=descuento;
	}
	public Club(String nombre){
		this.nombreClub = nombre;
	}


	public List<Corredor> getClub() {
		return club;
	}
	public void setFecha(Date fecha) {
		FechaEnLaQueParticipa=fecha;
	}
	

	public String getNombreClub() {
		return nombreClub;
	}


	public String getCarreraEnLaQueParticipa() {
		return carreraEnLaQueParticipa;
	}


	public Date getFechaEnLaQueParticipa() {
		return FechaEnLaQueParticipa;
	}
	public int getDescuento() {
		return descuento;
	}
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}
	
	

}
