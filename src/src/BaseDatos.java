package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.hsqldb.types.Types;

import logica.Carrera;
import logica.Categoria;
import logica.Club;
import logica.Corredor;
import logica.Participante;
import logica.Cuota;

@SuppressWarnings("unused")
public class BaseDatos {
	
	private final static String url = "jdbc:hsqldb:hsql://localhost/labdb";
	private final static String usuario = "SA";
	private final static String contraseña = "";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {		
		Class.forName("org.hsqldb.jdbcDriver");
		try {
			getConnection();
			System.out.println("Conexión establecida");
			
			
		} catch (SQLException e) {
			System.out.println("No se ha podido acceder a la base de datos");
		}

		 
		 		 
		 Date date1 = new Date(117,9,30);		 
		 Date date2 = new Date(117,9,30);

		
	//	registrarCarrera("Carrera popular",date1);
	//	registrarCarrera("Carrera popular",date2);
		
	}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(url, usuario, contraseña);
		return conn;
	}
	
	/**
	 * Devuelve un array con todos los participantes de una carrera determinada
	 * @param String carrera seleccionada
	 * @param Date carrera seleccionada
	 * @return array de Participantes
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static ArrayList<Participante>devuelveParticipantes(String nombre, Date fechaCelebracion) throws ClassNotFoundException, SQLException, ParseException{
	
		Connection con = getConnection();
		ArrayList<Participante> lista = new ArrayList<Participante> ();
		StringBuilder query = new StringBuilder();
		query.append("SELECT DNI,NOMBRE,FECHA_NACIMIENTO,SEXO,FECHA_CELEBRACION,FECHA_INSCRIPCION,TIEMPO,"
		+ "DORSAL,ESTADO,ID_SENSOR,POSICION,NOMBRE_CLUB FROM PARTICIPA PA,CORREDOR CO WHERE PA.DNI = CO.DNI AND NOMBRE_CARRERA = ? AND FECHA_CELEBRACION = ? ");//todas las carreras		
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, nombre);
		pst.setDate(2, fechaCelebracion);
		ResultSet rs = pst.executeQuery();
		//Corredor corredor, Date fechaInscripcion, boolean pagado, double tiempo, int identificador,	int dorsal)			
		while(rs.next()) {
			String DNI = rs.getString("DNI");
			String nom = rs.getString("NOMBRE");
			Date fecha_nac = rs.getDate("FECHA_NACIMIENTO");
			boolean sexo = rs.getString("SEXO").equals("Masculino");
			Date fecha_inscripcion = rs.getDate("FECHA_INSCRIPCION");
			Date fecha_celebracion = rs.getDate("FECHA_CELEBRACION");
			String estado = rs.getString("ESTADO");
			int dorsal = rs.getInt("DORSAL");
			double tiempo = rs.getDouble("TIEMPO");
			int sensor = rs.getInt("ID_SENSOR");
			int posicion = rs.getInt("POSICION");
			String club = rs.getString("NOMBRE_CLUB");
			 Corredor c = new Corredor(nom,DNI,fecha_nac.toString(),sexo);
			 Participante p = new Participante(c,fecha_inscripcion,estado,tiempo,sensor,dorsal,posicion,club);
			
				 lista.add(p);
		}
		
		
		rs.close();
		pst.close();
		con.close();
		return lista;
		
	}
		
	

	/**
	 * Devuelve TODOS los corredores de la BBDD
	 * @return Array de Corredores
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static ArrayList<Corredor> devuelveCorredores() throws ClassNotFoundException, SQLException, ParseException{
		Connection con = getConnection();
		ArrayList<Corredor> lista = new ArrayList<Corredor> ();
		StringBuilder query = new StringBuilder();
		query.append("SELECT DNI,NOMBRE,FECHA_NACIMIENTO,SEXO FROM CORREDOR ");//todas las carreras
	//	query.append(" SELECT NOMBRE_CARRERA,FECHA_CELEBRACION FROM PARTICIPA WHERE DNI = ?");//Menos en las que participa	
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query.toString());
		
		while(rs.next()) {
			String DNI = rs.getString("DNI");
			String nombre = rs.getString("NOMBRE");
			Date fecha = rs.getDate("FECHA_NACIMIENTO");
			//int numeroParticipantes = rs.getInt("PARTICIPANTES");
			boolean sexo = rs.getString("SEXO").equals("Masculino");
			Corredor c;
			
				c = new Corredor(nombre,DNI,fecha.toString(),sexo);
			
			lista.add(c);
		}
		
		
		rs.close();
		st.close();
		con.close();
		return lista;
		
	}
	
	
	/**
	 * Registra un Corredor en la BBDD
	 * 
	 * @param DNI
	 * @param nombre
	 * @param sexo
	 * @param categoria
	 * @param fecha
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void RegistrarCorredor(String DNI,String nombre,String sexo,String categoria,Date fecha) throws SQLException, ClassNotFoundException {		
		
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO corredor (dni,nombre,sexo,FECHA_NACIMIENTO) VALUES (?,?,?,?)");		
		PreparedStatement pst = con.prepareStatement(query.toString());		
		pst.setString(1,DNI);
		pst.setString(2, nombre );
		pst.setString(3, sexo);		
		pst.setDate(4, fecha);		
		
		if(pst.executeUpdate() == 1)
			System.out.println("Data successfully introduced!!");
		else
			System.out.println("Some error has ocurred, data not introduced!!");			
		pst.close();
		con.close();	
	}

	/**
	 * Comprueba que un corredor esta en la BDD
	 * 
	 * @param DNI
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean existeCorredor(String DNI) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("SELECT count(DNI) FROM CORREDOR WHERE DNI = ?");		
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, DNI);
		ResultSet rs = pst.executeQuery();
		rs.next();
		int res = rs.getInt(1);
		
		rs.close();
		pst.close();
		con.close();
		
		if(res==0) {return false;}
		else
			return true;
		
	}
	
	/**
	 * Devuelve las carreras en las que aun no se ha registrado un corredor 
	 * 
	 * @param DNI
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<Carrera> carrerasPosibles(String DNI) throws ClassNotFoundException, SQLException{
		
		Connection con = getConnection();
		ArrayList<Carrera> lista = new ArrayList<Carrera> ();
		StringBuilder query = new StringBuilder();
		//FECHA_INICIO_REGISTRO DATE,FECHA_FIN_1CUOTA DATE,FECHA_FIN_2CUOTA DATE
		query.append("SELECT NOMBRE_CARRERA,FECHA_CELEBRACION,PARTICIPANTES,DEVOLUCION FROM CARRERA WHERE ESTADO = ? MINUS ");//todas las carreras
		query.append("SELECT NOMBRE_CARRERA,FECHA_CELEBRACION,PARTICIPANTES,DEVOLUCION FROM PARTICIPA PA,CARRERA CA WHERE PA.FECHA_CELEBRACION =CA.FECHA_CELEBRACION "
				+ "AND PA.NOMBRE_CARRERA =CA.NOMBRE_CARRERA AND DNI = ? ");//Menos en las que participa	
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, "ABIERTA");
		pst.setString(2, DNI);
		
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			String nombre = rs.getString("NOMBRE_CARRERA");
			Date fecha = rs.getDate("FECHA_CELEBRACION");
			int participantes = rs.getInt("PARTICIPANTES");
		
			int devolucion = rs.getInt("DEVOLUCION");
			Carrera car = new Carrera(fecha,nombre,participantes, "ABIERTA",null, devolucion);
			
			if(devolverCategorias(car)==null) {
				car.categoriasPorDefecto();}
			else
				car.setCategorias(devolverCategorias(car));
				car.setCuotas(devolverCuotas(car));
			lista.add(car);
		}
		rs.close();
		pst.close();
		con.close();
		return lista;
		
	}

	/**
	 * Registra a un participante en una carrera
	 * @param DNI
	 * @param nombre
	 * @param fecha_celebracion
	 * @param fecha_registro
	 * @param tiempo
	 * @param estado
	 * @param id
	 * @param dorsal
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void registrarParticipante(String DNI,String nombre,Date fecha_celebracion,Date fecha_registro,double tiempo,String estado,int id,int dorsal,int posicion,String club) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("INSERT into PARTICIPA(DNI,NOMBRE_CARRERA,FECHA_CELEBRACION,FECHA_INSCRIPCION,DORSAL,ESTADO,TIEMPO,ID_SENSOR,POSICION,NOMBRE_CLUB) VALUES(?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement pst = con.prepareStatement(query.toString());		
		pst.setString(1,DNI);
		pst.setString(2, nombre );
		pst.setDate(3, fecha_celebracion);		
		pst.setDate(4, fecha_registro);
		pst.setInt(5, dorsal);
		pst.setString(6, estado);	
		pst.setDouble(7, tiempo);
		pst.setInt(8, id);
		pst.setInt(9, posicion);
		pst.setString(10, club);
		
		if(pst.executeUpdate() == 1)
			System.out.println("Data successfully introduced!!");
		else
			System.out.println("Some error has ocurred, data not introduced!!");			
		pst.close();
		con.close();	
	}
   
	/**
	 * Registra una nueva Carrera en la Base de Datos
	 * @param nombre
	 * @param fecha_cel
	 * @param fecha_Reg
	 * @param participantes
	 * @param estado
	 * @param precio
	 * @param devolucion 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void registrarCarrera(String nombre,Date fecha_cel,int participantes,String estado, int devolucion) throws SQLException, ClassNotFoundException{
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO CARRERA(NOMBRE_CARRERA,FECHA_CELEBRACION,PARTICIPANTES,ESTADO,DEVOLUCION) VALUES(?,?,?,?,?) ");
		PreparedStatement pst = con.prepareStatement(query.toString());		
		pst.setString(1,nombre);
		pst.setDate(2,fecha_cel );
		pst.setInt(3, participantes);
		pst.setString(4, estado);		
		pst.setInt(5, devolucion);
		
		if(pst.executeUpdate() == 1)
			System.out.println("Data successfully introduced!!");
		else
			System.out.println("Some error has ocurred, data not introduced!!");			
		pst.close();
		con.close();	
		
	}
	
	
	/**
	 * Añade Una nueva Cuota asociada a una carrera 
	 * @param car
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void AñadirCuotas(Carrera car,Cuota cuo) throws SQLException, ClassNotFoundException {
		
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO CUOTAS(NOMBRE_CARRERA,FECHA_CELEBRACION,FECHA_INICIO_CUOTA,FECHA_FIN_CUOTA,DESCUENTO) VALUES(?,?,?,?,?) ");

	
		PreparedStatement pst = con.prepareStatement(query.toString());		
		pst.setString(1,car.getNombre());
		pst.setDate(2,car.getFecha_celebracion() );
		pst.setDate(3,cuo.getFecha_inicio());
		pst.setDate(4,cuo.getFecha_fin() );
		pst.setDouble(5,cuo.getDescuento() );

		if(pst.executeUpdate() == 1)
			System.out.println("Data successfully introduced!!");
		else
			System.out.println("Some error has ocurred, data not introduced!!");			
		pst.close();
		
		con.close();	
	}
	
	
	/**
	 * Cambiar estado a un Participante
	 * @param participante
	 * @param fecha_carrera
	 * @param nombre_carrera
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void cambiarEstadoParticipante(Participante participante,Date fecha_carrera,String nombre_carrera) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE participa SET ESTADO = 'PAGADO-SIN-RECOGER' WHERE DNI = ? AND FECHA_CELEBRACION = ? AND NOMBRE_CARRERA = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, participante.getCorredor().getDni());
		pst.setDate(2, fecha_carrera);
		pst.setString(3, nombre_carrera);
		pst.execute();
		pst.close();
		con.close();
	}
	
	public static void participanteRecogeDorsal(Participante participante,Date fecha_carrera,String nombre_carrera) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE participa SET ESTADO = 'PAGADO' WHERE DNI = ? AND FECHA_CELEBRACION = ? AND NOMBRE_CARRERA = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, participante.getCorredor().getDni());
		pst.setDate(2, fecha_carrera);
		pst.setString(3, nombre_carrera);
		pst.execute();
		pst.close();
		con.close();
	}
	
	public static void cambiarEstadoParticipanteCanceladoPagar(Participante participante, Date fecha, String nombre) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE participa SET ESTADO = 'CANCELADO-DEVUELTO' WHERE DNI = ? AND FECHA_CELEBRACION = ? AND NOMBRE_CARRERA = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, participante.getCorredor().getDni());
		pst.setDate(2, fecha);
		pst.setString(3, nombre);
		pst.execute();
		pst.close();
		con.close();
	}
	
	public static void cambiarEstadoParticipanteCancelado(Participante participante, Date fecha, String nombre) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		if (participante.getEstado().equals("CANCELADO"))
			query.append("UPDATE participa SET ESTADO = 'CANCELADO' WHERE DNI = ? AND FECHA_CELEBRACION = ? AND NOMBRE_CARRERA = ?");
		else
			query.append("UPDATE participa SET ESTADO = 'CANCELADO-PENDIENTE' WHERE DNI = ? AND FECHA_CELEBRACION = ? AND NOMBRE_CARRERA = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, participante.getCorredor().getDni());
		pst.setDate(2, fecha);
		pst.setString(3, nombre);
		pst.execute();
		pst.close();
		con.close();
		
	}
	
	/**
	 * Devuelve un corredor introduciendo su DNI
	 * @param DNI
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public static Corredor buscarCorredor(String DNI) throws SQLException, ParseException, ClassNotFoundException {
		
		Connection con = getConnection();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT dni,nombre,sexo,fecha_nacimiento "
					+ "FROM CORREDOR WHERE DNI = ? ");//todas las carreras
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, DNI);
		ResultSet rs = pst.executeQuery();
		rs.next();
		String dni = rs.getString(1);
		String nombre = rs.getString(2);
		String sexo = rs.getString(3);
		String fecha = rs.getDate(4).toString();

		boolean sex = sexo.equals("Masculino");
		Corredor c = new Corredor(nombre,dni,fecha,sex);
		
		rs.close();
		pst.close();
		con.close();
		
		return c;
	}

	
	/**
	 * Devuelve todas las carreras en un array
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<Carrera> carrerasTotal() throws ClassNotFoundException, SQLException{
		
		Connection con = getConnection();
		ArrayList<Carrera> lista = new ArrayList<Carrera> ();
		StringBuilder query = new StringBuilder();
		query.append("SELECT NOMBRE_CARRERA,FECHA_CELEBRACION,PARTICIPANTES,ESTADO,DEVOLUCION FROM CARRERA ");//todas las carreras
	//	query.append(" SELECT NOMBRE_CARRERA,FECHA_CELEBRACION FROM PARTICIPA WHERE DNI = ?");//Menos en las que participa	
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query.toString());
		
		while(rs.next()) {
			String nombre = rs.getString("NOMBRE_CARRERA");
			Date fecha = rs.getDate("FECHA_CELEBRACION");
			int participantes = rs.getInt("PARTICIPANTES");
			String estado = rs.getString("ESTADO");
			
			int devolucion = rs.getInt("DEVOLUCION");
			Carrera car = new Carrera(fecha,nombre,participantes, estado,null, devolucion);
			if(devolverCategorias(car)==null) {
				car.categoriasPorDefecto();}
			else{car.setCategorias( devolverCategorias(car));}
			lista.add(car);
			car.setCuotas(devolverCuotas(car));
			//lista.add(new Carrera(fecha,nombre,participantes,estado,precio,null));
		
		}
		
		
		rs.close();
		st.close();
		con.close();
		return lista;
		
	}

	
	/**
	 * Carreras en funcion de su estado 
	 * @param estados
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	/*public ArrayList<Carrera> carrerasPorEstados(String estados) throws ClassNotFoundException, SQLException{
		Connection con = getConnection();
		ArrayList<Carrera> lista = new ArrayList<Carrera> ();
		StringBuilder query = new StringBuilder();
	//	query.append("SELECT NOMBRE_CARRERA,FECHA_CELEBRACION,PARTICIPANTES,ESTADO FROM CARRERA MINUS ");//todas las carreras
		query.append(" SELECT NOMBRE_CARRERA,FECHA_CELEBRACION,PARTICIPANTES,ESTADO FROM CARRERA WHERE ESTADO = ?");//Menos en las que participa	
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, estados);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			String nombre = rs.getString("NOMBRE_CARRERA");
			Date fecha = rs.getDate("FECHA_CELEBRACION");
		
			String estado = rs.getString("ESTADO");
			Carrera car = new Carrera(fecha,nombre,estado);
			if(devolverCategorias(car)==null) {
				car.setCategorias(car.categoriasPorDefecto());}
			else{car.setCategorias( devolverCategorias(car));}
			lista.add(car);
		}
		rs.close();
		pst.close();
		con.close();
		return lista;
		
	}
	*/
	
	/**
	 * 
	 * Devuelve el numero de participantes inscritos en una carrera determinada
	 * @param c
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int numeroCorredores (Carrera c) throws ClassNotFoundException, SQLException {
		String nombre = c.getNombre();
		Date fechaCelebracion = c.getFecha_celebracion();
		Connection con = getConnection();
		ArrayList<Carrera> lista = new ArrayList<Carrera> ();
		StringBuilder query = new StringBuilder();
		query.append(" SELECT count(DNI) FROM PARTICIPA WHERE NOMBRE_CARRERA = ? AND FECHA_CELEBRACION=?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1,nombre);
		pst.setDate(2,fechaCelebracion);
		ResultSet rs = pst.executeQuery(); 
		rs.next();
		int ret = rs.getInt(1);
		
		rs.close();
		pst.close();
		con.close();
		return ret;
	}
	
	
    
   public static ArrayList<Participante> obtenerListaCorredorPreinscrito(String nombreCarrera, Date fechaCarrera) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Participante> participantesTotales = devuelveParticipantes(nombreCarrera,fechaCarrera);
		ArrayList<Participante> participantesSinDorsal = new ArrayList<Participante>() ;
		for(Participante p:participantesTotales) {			
			if(p.getDorsal()==0 && p.getEstado().equals("PRE_INSCRITO")) {
				participantesSinDorsal.add(p);
			}
		}
		return participantesSinDorsal;
	}
	
	
	
	
	
	/**
	 * Cambiar Dorsales de un participante
	 * @param participante
	 * @param nombre
	 * @param fecha
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void cambiarDorsalesCorredor(Participante participante, String nombre, Date fecha) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE participa SET DORSAL = ");
		query.append(participante.getDorsal());
		query.append(" WHERE DNI = ?  AND NOMBRE_CARRERA = ? AND FECHA_CELEBRACION = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, participante.getCorredor().getDni());
		pst.setString(2, nombre);
		pst.setDate(3, fecha);
		pst.executeUpdate();
		pst.close();
		con.close();
	}
	
	/**
	 * Cambiar el tiempo para un participante concreto
	 * @param par
	 * @param nombre
	 * @param fecha
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void añadirTiempo(Participante par,String nombre,Date fecha) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE participa SET TIEMPO = ");
		query.append(par.getTiempo());
		query.append(" WHERE DNI = ?  AND NOMBRE_CARRERA = ? AND FECHA_CELEBRACION = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, par.getCorredor().getDni());
		pst.setString(2, nombre);
		pst.setDate(3, fecha);
		pst.executeUpdate();
		pst.close();
		con.close();
	}
	
	public static void añadirTiempoAtleta(Map<Integer, Double> datos,String nombre,Date fecha) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		
			datos.forEach((k,v) -> HazLoQueTengasQueHacer(nombre,fecha,k,v));
		
		
		
		//con.close();
	}
	
	private static void  HazLoQueTengasQueHacer(String nombre, Date fecha,Integer identificador, Double tiempo) {
		Connection con=null;
		try {
			con = getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder query = new StringBuilder();
		query.append("UPDATE participa SET TIEMPO = ");
		query.append(tiempo);
		query.append(" WHERE ID_SENSOR = ? AND NOMBRE_CARRERA = ? AND FECHA_CELEBRACION = ?");
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(query.toString());
			pst.setInt(1, identificador);
			pst.setString(2, nombre);
			pst.setDate(3, fecha);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	/**
	 * Devuelve todos los participantes sin Dorsales
	 * @param nombre
	 * @param fechaCar
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static List<Participante> corredoresSinDorsales(String nombre, Date fechaCar) throws ClassNotFoundException, SQLException, ParseException {
		List<Participante> participantesTotales = devuelveParticipantes(nombre,fechaCar);
		List<Participante> participantesSinDorsal = new ArrayList<Participante>() ;
		for(Participante p:participantesTotales) {			
			if(p.getEstado().equals("PAGADO-SIN-RECOGER")) {
				participantesSinDorsal.add(p);
			}
		}
		return participantesSinDorsal;
	}
	
	/**
	 * Devuelve los participantes no cancelados
	 * @param nombre
	 * @param fecha
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static List<Participante> devuelveParticipantesSinCancelar(String nombre, Date fecha) throws ClassNotFoundException, SQLException, ParseException {
		List<Participante> participantesTotales = devuelveParticipantes(nombre,fecha);
		List<Participante> participantesSinCancelar = new ArrayList<Participante>() ;
		for(Participante p:participantesTotales) {			
			if(p.getEstado().equals("PAGADO-SIN-RECOGER") || p.getEstado().equals("PRE_INSCRITO")) {
				participantesSinCancelar.add(p);
			}
		}
		return participantesSinCancelar;
	}
	
	/**
	 * Devuelve todos los participantes con Dorsales
	 * @param nombre
	 * @param fechaCar
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static List<Participante> corredoresConDorsales(String nombre, Date fechaCar) throws ClassNotFoundException, SQLException, ParseException {
		List<Participante> participantesTotales = devuelveParticipantes(nombre,fechaCar);
		List<Participante> participantesSinDorsal = new ArrayList<Participante>() ;
		for(Participante p:participantesTotales) {			
			if(p.getDorsal()>0 && p.getEstado().equals("PAGADO-SIN-RECOGER")) {
				participantesSinDorsal.add(p);
			}
		}
		return participantesSinDorsal;
	}
	
	public static List<Participante> corredoresConDorsalesFin(String nombre, Date fechaCar) throws ClassNotFoundException, SQLException, ParseException {
		List<Participante> participantesTotales = devuelveParticipantes(nombre,fechaCar);
		List<Participante> participantesSinDorsal = new ArrayList<Participante>() ;
		for(Participante p:participantesTotales) {			
			if(p.getDorsal()>0 && p.getEstado().equals("PAGADO")) {
				participantesSinDorsal.add(p);
			}
		}
		return participantesSinDorsal;
	}
	
	/**
	 * Devuelve todos los participantes cancelados
	 * @param nombre
	 * @param fechaCar
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static List<Participante> corredoresCancelados (String nombre, Date fecharCar) throws ClassNotFoundException, SQLException, ParseException {
		List<Participante> participantesTotales = devuelveParticipantes(nombre,fecharCar);
		List<Participante> participantesCancelados = new ArrayList<Participante>() ;
		for(Participante p:participantesTotales) {
			if(p.getEstado().equals("CANCELADO") || p.getEstado().equals("CANCELADO-PENDIENTE")) {
				participantesCancelados.add(p);
			}
		}
		return participantesCancelados;
	}
	
	/**
	 * Devuelve todos los participantes cancelados PendientesPago
	 * @param nombre
	 * @param fechaCar
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static List<Participante> corredoresCanceladosPendientesPago (String nombre, Date fecharCar) throws ClassNotFoundException, SQLException, ParseException {
		List<Participante> participantesTotales = devuelveParticipantes(nombre,fecharCar);
		List<Participante> participantesCancelados = new ArrayList<Participante>() ;
		for(Participante p:participantesTotales) {			
			if(p.getEstado().equals("CANCELADO-PENDIENTE")) {
				participantesCancelados.add(p);
			}
		}
		return participantesCancelados;
	}


	/**
	 * Cambia el estado de una determinada carrera
	 * @param carrera carrera a cambiar
	 * @param estado nuevo estado
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void cambiarEstadoCarrera(Carrera carrera, String estado) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE carrera SET ESTADO = ?");
		//query.append(estado);
		query.append(" WHERE NOMBRE_CARRERA = ? AND FECHA_CELEBRACION = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, estado);
		pst.setString(2, carrera.getNombre());
		pst.setDate(3, carrera.getFecha_celebracion());
		pst.executeUpdate();
		pst.close();
		con.close();
		
		
	}
	

	/**
	 * introduce una nueva categoria
	 * @param carrera carrera a cambiar
	 * @param estado nuevo estado
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void NuevaCategoria(Categoria cat) throws ClassNotFoundException, SQLException {
			
			Connection con = getConnection();
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO CATEGORIA(NOMBRE,SEXO,EDAD_MIN,EDAD_MAX) VALUES (?,?,?,?)");		
			PreparedStatement pst = con.prepareStatement(query.toString());		
			pst.setString(1,cat.getNombre());
			pst.setString(2, cat.getGenero());		
			pst.setInt(3, cat.getEdadi());
			pst.setInt(4, cat.getEdadf());
			
			if(pst.executeUpdate() == 1)
				System.out.println("Data successfully introduced!!");
			else
				System.out.println("Some error has ocurred, data not introduced!!");			
			pst.close();
			con.close();	
		}
	
	/**
	 * vincula una categoria a una carrera
	 * @param carrera carrera a cambiar
	 * @param estado nuevo estado
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */	
	public static void VinculaCategoriaCarrera(Carrera car,Categoria cat) throws ClassNotFoundException, SQLException {
		
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO RELACION_CATEGORIA_CARRERA(NOMBRE,NOMBRE_CARRERA,FECHA_CELEBRACION) VALUES (?,?,?)");		
		PreparedStatement pst = con.prepareStatement(query.toString());		
		pst.setString(1,cat.getNombre());
		pst.setString(2, car.getNombre() );		
		pst.setDate(3, car.getFecha_celebracion());	

		
		if(pst.executeUpdate() == 1)
			System.out.println("Data successfully introduced!!");
		else
			System.out.println("Some error has ocurred, data not introduced!!");			
		pst.close();
		con.close();	
	}
	

	/**
	 * Comprueba si existe una categoria
	 * @param nombre nombre de la categoria
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean existeCategoria(String nombre) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("SELECT count(NOMBRE) FROM CATEGORIA WHERE NOMBRE = ?");		
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		rs.next();
		int res = rs.getInt(1);
		
		rs.close();
		pst.close();
		con.close();
		
		if(res==0) {return false;}
		else
			return true;
		
	}
	
	/**
	 * devuelve las categorias de una carrera
	 * @param carrera carrera a cambiar
	 * @param estado nuevo estado
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<Categoria> devolverCategorias(Carrera car) throws ClassNotFoundException, SQLException {
		ArrayList<Categoria> categorias =new ArrayList<Categoria>();
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT CAT.EDAD_MIN, CAT.EDAD_MAX, CAT.NOMBRE, CAT.SEXO FROM CATEGORIA CAT,"
				+ " RELACION_CATEGORIA_CARRERA REL WHERE REL.NOMBRE_CARRERA = ? AND REL.FECHA_CELEBRACION = ? "
				+ " AND REL.NOMBRE = CAT.NOMBRE");		
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, car.getNombre());
		pst.setDate(2, car.getFecha_celebracion());
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {		
			int edadMin =rs.getInt("EDAD_MIN");
			int edadMax = rs.getInt("EDAD_MAX");
			String nombre =rs.getString("NOMBRE");
			String sexo = rs.getString("SEXO");
			Categoria cat = new Categoria(nombre,edadMin,edadMax,sexo);
			categorias.add(cat);
		}return categorias;
	}
	
	/**
	 * borra una carrera
	 * @param carrera carrera a cambiar
	 * @param estado nuevo estado
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void borrarCarrera(Carrera carreraAntigua) throws ClassNotFoundException, SQLException {
		
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		
		query.append("DELETE FROM RELACION_CATEGORIA_CARRERA WHERE NOMBRE_CARRERA = '"+carreraAntigua.getNombre()+
				"' AND FECHA_CELEBRACION = '"+carreraAntigua.getFecha_celebracion().toString()+"'");
		Statement st = con.createStatement();
		
		st.executeUpdate(query.toString());
		
			st.close();
	
	query = new StringBuilder();
	query.append("DELETE FROM CUOTAS WHERE NOMBRE_CARRERA = '"+carreraAntigua.getNombre()+
			"' AND FECHA_CELEBRACION = '"+carreraAntigua.getFecha_celebracion().toString()+"'");
	 st = con.createStatement();
	
	st.executeUpdate(query.toString());
	
	
		st.close();
	query = new StringBuilder();
	query.append("DELETE FROM CARRERA WHERE NOMBRE_CARRERA = '"+carreraAntigua.getNombre()+
		"' AND FECHA_CELEBRACION = '"+carreraAntigua.getFecha_celebracion().toString()+"'");
	 st = con.createStatement();
	 st.executeUpdate(query.toString());
	 query = new StringBuilder();
		query.append("DELETE FROM  RELACION_CLUB_CARRERA WHERE NOMBRE_CARRERA = '"+carreraAntigua.getNombre()+
			"' AND FECHA_CELEBRACION = '"+carreraAntigua.getFecha_celebracion().toString()+"'");
		 st = con.createStatement();
st.executeUpdate(query.toString());


	st.close();}

	
public static void borrarParticipante(Carrera carreraAntigua,Participante p) throws ClassNotFoundException, SQLException {
		
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		
		query.append("DELETE FROM PARTICIPA WHERE NOMBRE_CARRERA = '"+carreraAntigua.getNombre()+
				"' AND FECHA_CELEBRACION = '"+carreraAntigua.getFecha_celebracion().toString()+"'"
				+ " AND DNI = '"+p.getCorredor().getDni()+"'");
		Statement st = con.createStatement();
		
		st.executeUpdate(query.toString());
		
			st.close();
	
	

	st.close();}
	
	/**
	 * devuelve las cuotas asociadas a una carrera
	 * @param carrera carrera a cambiar
	 * @param estado nuevo estado
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */		
	public static ArrayList<Cuota> devolverCuotas(Carrera car) throws ClassNotFoundException, SQLException {
		ArrayList<Cuota> cuotas =new ArrayList<Cuota>();
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT FECHA_FIN_CUOTA ,FECHA_INICIO_CUOTA ,DESCUENTO FROM CUOTAS WHERE NOMBRE_CARRERA = ? AND FECHA_CELEBRACION = ? ");		
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, car.getNombre());
		pst.setDate(2, car.getFecha_celebracion());
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {		
			Date fechaFin =rs.getDate("FECHA_FIN_CUOTA");
			Date fechaInicio = rs.getDate("FECHA_INICIO_CUOTA");
			int descuento =rs.getInt("DESCUENTO");
			
			Cuota cu = new Cuota(fechaInicio,fechaFin,descuento);
			cuotas.add(cu);
		}
		return cuotas;
	}

	public static void añadirClub(Club club) throws ClassNotFoundException, SQLException {
		
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO CLUB(NOMBRE_CLUB) VALUES (?)");		
		PreparedStatement pst = con.prepareStatement(query.toString());		
		pst.setString(1,club.getNombreClub());
		
		
		if(pst.executeUpdate() == 1)
			System.out.println("Data successfully introduced!!");
		else
			System.out.println("Some error has ocurred, data not introduced!!");			
		pst.close();
		con.close();	
	}
	
	public static void VincularClubCarrera(Club club) throws ClassNotFoundException, SQLException {
		
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO RELACION_CLUB_CARRERA(NOMBRE_CLUB,NOMBRE_CARRERA,FECHA_CELEBRACION,DESCUENTO) VALUES (?,?,?,?)");		
		PreparedStatement pst = con.prepareStatement(query.toString());		
		pst.setString(1,club.getNombreClub());
		pst.setString(2,club.getCarreraEnLaQueParticipa() );
		pst.setDate(3,club.getFechaEnLaQueParticipa());	
		pst.setInt(4, club.getDescuento());
	
		
		if(pst.executeUpdate() == 1)
			System.out.println("Data successfully introduced!!");
		else
			System.out.println("Some error has ocurred, data not introduced!!");			
		pst.close();
		con.close();	
		
	}
	public static List<Club> clubsEnCarrera(Carrera carrera) throws ClassNotFoundException, SQLException{
		Connection con = getConnection();
		ArrayList<Club> Clubs =new ArrayList<Club>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT NOMBRE_CLUB , DESCUENTO FROM RELACION_CLUB_CARRERA WHERE NOMBRE_CARRERA =? AND FECHA_CELEBRACION = ?");		
		PreparedStatement pst = con.prepareStatement(query.toString());		
		pst.setString(1,carrera.getNombre());
		pst.setDate(2,carrera.getFecha_celebracion());		
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			
		int DTO = rs.getInt("DESCUENTO");
		String nombre = rs.getString("NOMBRE_CLUB");
		Club club = new Club(carrera.getNombre(),carrera.getFecha_celebracion(),nombre,null,DTO);
		Clubs.add(club);
		
		}
		
		pst.close();
		con.close();	
		
		return Clubs;
		
		
		
	}
	public static boolean existeClub(String nombre) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("SELECT count(NOMBRE_CLUB) FROM CLUB WHERE NOMBRE_CLUB = ?");		
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, nombre);
		ResultSet rs = pst.executeQuery();
		rs.next();
		int res = rs.getInt(1);
		
		rs.close();
		pst.close();
		con.close();
		
		if(res==0) {return false;}
		else
			return true;
		
	}

	public static int descuentoClub(String nombreClub, String nombreCarrera, Date fechaCarrera) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("SELECT DESCUENTO FROM RELACION_CLUB_CARRERA WHERE NOMBRE_CLUB = ? AND FECHA_CELEBRACION = ? AND NOMBRE_CARRERA = ?");		
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setString(1, nombreClub);
		pst.setDate(2, fechaCarrera);
		pst.setString(3, nombreCarrera);
		ResultSet rs = pst.executeQuery();
		rs.next();
		int res = rs.getInt(1);
		
		rs.close();
		pst.close();
		con.close();
		return res;
		
		
	}
	public static void cambiarPosicionParticipante(Participante participante,Date fecha_carrera,String nombre_carrera) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE participa SET POSICION = ? WHERE DNI = ? AND FECHA_CELEBRACION = ? AND NOMBRE_CARRERA = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setInt(1, participante.getPosicion());
		pst.setString(2, participante.getCorredor().getDni());
		pst.setDate(3, fecha_carrera);
		pst.setString(4, nombre_carrera);
		pst.execute();
		pst.close();
		con.close();
	}

	public static void cambiarDevolucion(Carrera carrera, int i) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		StringBuilder query = new StringBuilder();
		query.append("UPDATE carrera SET DEVOLUCION = ? WHERE  FECHA_CELEBRACION = ? AND NOMBRE_CARRERA = ?");
		PreparedStatement pst = con.prepareStatement(query.toString());
		pst.setInt(1, i);
		pst.setDate(2, carrera.getFecha_celebracion());
		pst.setString(3, carrera.getNombre());
		pst.execute();
		pst.close();
		con.close();
	}

	
}


	
	
