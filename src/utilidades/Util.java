package utilidades;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import logica.Carrera;
import logica.Corredor;
import logica.Participante;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;

import src.BaseDatos;

public class Util {
	
	public static BaseDatos bd = new BaseDatos();
	
	
	
	/**
	 * Añade un corredor nuevo a la lista
	 * @param corredor
	 */
	public static void añadirCorredor(Corredor corredor) {
		
		String dni = corredor.getDni();
		Date fecha = corredor.getFechaNacimiento();
		String nombre = corredor.getNombre();
		String sexo;
		if(corredor.getGenero()) {sexo = "Masculino";}
		else {
			sexo ="Femenino";}
		
		try {
			
			//String categoria = corredor.getCategoria();
			bd.RegistrarCorredor(dni,nombre,sexo, "", fecha);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	/**
	 * Devuelve si un corredor esta en la base de datos
	 * @param DNI
	 * @return un booleano true si esta el corredor del DNI y false sino
	 */
	public static boolean existeCorredor(String DNI) {
		boolean b = false;
		try {
			b = bd.existeCorredor(DNI);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		return b;
	}

	
	/**
	 * Devuelve aquellas carreras donde el DNI seleccionado no participa
	 * 
	 * @param DNI
	 * @return lista de Carreras en las que no 
	 */
	public static Carrera[] carrerasPosibles(String DNI) {
		
		ArrayList<Carrera> lista1 = new ArrayList<Carrera>();
		
		try {
			lista1 = bd.carrerasPosibles(DNI);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Carrera[] lista2 = new Carrera[lista1.size()];
		for(int i =0;i<lista1.size();i++) {
			lista2[i]=lista1.get(i);
		}
		return lista2;
		
	}

	public static void registraParticipante(Carrera car,Participante par) {
		
		String nombre=car.getNombre();
		Date fecha2 = car.getFecha_celebracion();
		
//		Date fecha_celebracion =new java.sql.Date(fecha2.getTime());
		Date fecha_inscripcion = par.getFechaRegistro();
		

		try {
			BaseDatos.registrarParticipante(par.getCorredor().getDni(),nombre,fecha2,(java.sql.Date)fecha_inscripcion,par.getTiempo(),par.getEstado(),
					par.getIdentificador(),par.getDorsal(),par.getPosicion(),par.getClub().getNombreClub());
		} catch(SQLIntegrityConstraintViolationException e1) {
			
			e1.printStackTrace();
		}
		catch (ClassNotFoundException | SQLException e) {


			e.printStackTrace();
		}
		
	}
	/**
	 * Metodo que lee de un Excel y lo pasa a una Lista
	 * @param filename1
	 * @return
	 * @throws IOException
	 */
	public static List ExcelToList(String filename1) throws IOException {
		//
		// An excel file name. You can create a file name with a full
		// path information.
		//
		String filename = filename1;
		//
		// Create an ArrayList to store the data read from excel sheet.
		//
		List sheetData = new ArrayList();
		FileInputStream fis = null;
		try {
			//
			// Create a FileInputStream that will be use to read the
			// excel file.
			//
			fis = new FileInputStream(filename);
			//
			// Create an excel workbook from the file system.
			//
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			//
			// Get the first sheet on the workbook.
			//
			HSSFSheet sheet = workbook.getSheetAt(0);
			//
			// When we have a sheet object in hand we can iterator on
			// each sheet's rows and on each row's cells. We store the
			// data read on an ArrayList so that we can printed the
			// content of the excel to the console.
			//
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				
				Iterator cells = row.cellIterator();
				List data = new ArrayList();
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();
//					System.out.println("Añadiendo Celda: " + cell.toString());
					data.add(cell);
				}
				sheetData.add(data);
		
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
//		showExelData(sheetData);
		return sheetData;
	}


	/**
	 * Registra una carrera nueva en la base de datos
	 * @param car
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void registraCarrera(Carrera car) throws ClassNotFoundException, SQLException {
		
		String nombre = car.getNombre();
		Date fecha_cel = car.getFecha_celebracion();
		int devolucion = car.getDevolucion();
		String estado = car.getEstado();
		int numPart = car.getNumeroParticipantes();
		
		BaseDatos.registrarCarrera(nombre, fecha_cel,numPart,estado,devolucion);
		}
		

	/**
	 * Genera un justificante tras registrarse en una carrera
	 * @param par participante
	 * @param nom_carr nombe de las carreras
	 * @return String con el contenido del justificante
	 */
	public static String generaJustificante(Participante par,Carrera car) {
		
		String nombre = par.getCorredor().getNombre();
		String DNI = par.getCorredor().getDni();
		Date Date = par.getFechaRegistro();
		String sexo ="a";
		if(par.getCorredor().getGenero()) {sexo ="o";}
		String nom_carr=car.getNombre();
		String pago = "Paga la cantidad de ";
		double precio = car.getCoste(Date);
		
		
		
		 Calendar c = Calendar.getInstance();
		   String date=  c.getTime().toLocaleString();	
		   String[] fecha = date.toString().split("-");
		String linea =
				"------------JUSTIFICANTE DE REGISTRO------------\n" +
				nombre + " queda registrad"+sexo+" en la carrera "+nom_carr+"\n"+
				pago+" "+precio +" EUROS\n"+
				"A " + fecha[0] +" de " +fecha[1] +" del "+ (Date.getYear()+1900);
	
		  
		//   String[] fecha = date.toString().split("-");
		 //  System.out.println(fecha.toString());
	   	   String nombreFichero = "files/"+"Justificante-"+DNI+"-"+nom_carr+"-"+Date.toString()+".dat";
		    try {	
		    	 BufferedWriter fichero = new BufferedWriter(new FileWriter(nombreFichero));	
		    	
		         fichero.write(linea);
		         fichero.close();
		         }
		    catch (FileNotFoundException fnfe) {
		    	System.out.println("El archivo no se ha podido guardar");
		    	
		    }
		    catch (IOException ioe) {
		    	new RuntimeException("Error de entrada/salida.");
		    	
		    }
		    return linea;
		  }
	
	/**
	 * Devuelve un corredor en funcion de su DNI
	 * @param DNI
	 * @return
	 */
	public static Corredor devuelveCorredor(String DNI)  {
		Corredor c=null;
		try {
			c= BaseDatos.buscarCorredor(DNI);
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	public static Date convierteStringToDate(String fecha)throws IllegalArgumentException {
		String[] fin = fecha.split("-");
		
		Date date = new Date(Integer.parseInt(fin[0])-1900,Integer.parseInt(fin[1])-1,Integer.parseInt(fin[2]));
		
		return date;
	}
public static String generaJustificanteDevolucion(Participante par,Carrera car) {
		
		String nombre = par.getCorredor().getNombre();
		String DNI = par.getCorredor().getDni();
		Date Date = par.getFechaRegistro();
		String sexo ="a";
		if(par.getCorredor().getGenero()) {sexo ="o";}
		String nom_carr=car.getNombre();
		String pago = "Paga la tarifa de ";
		double precio = car.getCoste(Date);
		double dev = car.getDineroDevuelto(Date);
		
		
		 Calendar c = Calendar.getInstance();
		   String date=  c.getTime().toLocaleString();	
		   String[] fecha = date.toString().split("-");
		String linea =
				"------------JUSTIFICANTE DE DEVOLUCION------------\n" +
			"A "+nombre + " se le devuelve "+dev+" de los "+precio+" euros \n"+
				"Que pago para la carrera "+nom_carr + "\n"+
				"A " + fecha[0] +" de " +fecha[1] +" del "+ (Date.getYear()+1900);
	
		  
		//   String[] fecha = date.toString().split("-");
		 //  System.out.println(fecha.toString());
	   	   String nombreFichero = "files/"+"JustificanteDevolucion-"+DNI+"-"+nom_carr+"-"+Date.toString()+".dat";
		    try {	
		    	 BufferedWriter fichero = new BufferedWriter(new FileWriter(nombreFichero));	
		    	
		         fichero.write(linea);
		         fichero.close();
		         }
		    catch (FileNotFoundException fnfe) {
		    	System.out.println("El archivo no se ha podido guardar");
		    	
		    }
		    catch (IOException ioe) {
		    	new RuntimeException("Error de entrada/salida.");
		    	
		    }
		    return linea;
		  }
	

	
}