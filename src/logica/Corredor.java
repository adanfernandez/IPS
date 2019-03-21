package logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Corredor {

	
	SimpleDateFormat d = new SimpleDateFormat("dd-MM-yy");
	private String nombre;//
	private String dni;//
	private Date fechaNacimiento;//
	private Date fechaRegistro;
	//private categorias categoria;//
	private int edad;//
	private boolean genero;//

	public enum categorias
	{
	    SM, M35, M40, M45, M50, M55, M60, M65, M70, SF, F40, F45, F50, F55, F60, F70;
	}
	

	
	
	/**
	 * Constructor
	 * @param nombre
	 * @param DNI
	 * @param fecha, con el siguiente formato: dia/mes/año
	 * @param edad, edad del corredor
	 * @param genero, true: masculino, false: femenino
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	public Corredor(String nombre, String DNI, String fechaNacimiento, boolean genero) throws ParseException
	{
		
		String[] fechaNac = fechaNacimiento.split("-");
		
		
		this.nombre = nombre;
		this.dni = DNI;
		this.fechaNacimiento =  new Date(Integer.parseInt(fechaNac[0])-1900,Integer.parseInt(fechaNac[1])-1,Integer.parseInt(fechaNac[2]));
		//System.out.println(this.fechaNacimiento.toString());
		//		.fechaRegistro =   new Date(Integer.parseInt(fechaReg[2]),Integer.parseInt(fechaReg[1]),Integer.parseInt(fechaReg[0]));
		this.edad = comprobarEdad(this.fechaNacimiento);
		this.genero = genero;
		
		//asignarCategoria(edad, genero);
	}
	
	public Corredor(String string, boolean b) {
		this.nombre= string;
		this.genero = b;
	}

	@SuppressWarnings("deprecation")
	private static int comprobarEdad(Date fecha) {
		java.util.Date ahora = new java.util.Date();
		Date now = new Date(ahora.getYear(), ahora.getMonth(), ahora.getDate());
		if (fecha.getMonth() > now.getMonth()) {
			return 2017-fecha.getYear()-1900;
		}
		else if (fecha.getMonth()==now.getMonth()) {
			if(fecha.getDate() <=now.getDate()) {
				return 2017-fecha.getYear()-1900;
			}
		}
		return 2017-fecha.getYear()-1901;
		
	}
	/**
	 * 
	 * @return nombre, String
	 */
	public String getNombre()
	{
		return nombre;
	}
	
	/**
	 * 
	 * @return DNI, String
	 */
	public String getDni()
	{
		return dni;
	}
	
	/**
	 * Edad, int
	 * @return
	 */
	public int getEdad()
	{
		return edad;
	}
	
	/**
	 * 
	 * @return categoria, String
	 */
	/*public String getCategoria()
	{
		return categoria.name();
	}*/

	/**
	 * 
	 * @return género, boolean:
	 * 				1: masculino
	 * 				0: femenino
	 */
	public boolean getGenero()
	{
		return genero;
	}
	
	/**
	 * 
	 * @return fecha de nacimiento, String
	 */
	public Date getFechaNacimiento()
	{
		return fechaNacimiento;
	}
	
	public Date getFechaRegistro()
	{
		return fechaRegistro;
	}
	
	/**
	 * @param edad
	 * @param genero
	 */
	/**
	 * @param edad
	 * @param genero
	 */
	/*private void asignarCategoria(int edad, boolean genero) {
		if(genero)
		{
			if(edad >= 18 && edad < 35)
				this.categoria = categorias.SM;
			else if(edad >= 35 && edad < 40)
				this.categoria = categorias.M35;
			else if(edad >= 40 && edad < 45)
				this.categoria = categorias.M40;
			else if(edad >= 45 && edad < 50)
				this.categoria = categorias.M45;
			else if(edad >= 50 && edad < 55)
				this.categoria = categorias.M50;
			else if(edad >= 55 && edad < 60)
				this.categoria = categorias.M55;
			else if(edad >= 60 && edad < 65)
				this.categoria = categorias.M60;
			else if(edad >= 65 && edad < 70)
				this.categoria = categorias.M65;
			else //if(edad >= 70)
				this.categoria = categorias.M70;
		}
		else
		{

			if(edad >= 18 && edad < 39)
				this.categoria = categorias.SF;

			else if(edad >= 40 && edad < 44)
				this.categoria = categorias.F40;
			else if(edad >= 45 && edad < 50)
				this.categoria = categorias.F45;
			else if(edad >= 50 && edad < 55)
				this.categoria = categorias.F50;
			else if(edad >= 55 && edad < 60)
				this.categoria = categorias.F55;
			else if(edad >= 60 && edad < 70)
				this.categoria = categorias.F60;
			else //if(edad >= 70)
				this.categoria = categorias.F70;			
		}
	}*/
	
//	public String toString()
//	{
//		return "Nombre= " + getNombre() + ", DNI= " + getDni() + ", Edad= " + getEdad() 
//			+", Fecha de nacimiento= " + DateFormat.getDateInstance().format(getFechaNacimiento())
//			+ ", Fecha de Registro= " + DateFormat.getDateInstance().format(getFechaRegistro())
//			+ ", Pagado= " + getPagado();
//	}


		//SimpleDateFormat d = new SimpleDateFormat("dd-MM-yy");
		//Date date = d.parse("31-03-2016");
		//System.out.println(DateFormat.getDateInstance().format(date));
	
}
