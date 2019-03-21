package persistencia;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import utilidades.Util;
import logica.Carrera;
import logica.Categoria;
import logica.Club;
import logica.Corredor;
import logica.Corredor.categorias;
import logica.Participante;
import src.BaseDatos;

public class Generador {
	private static ArrayList<String> nombresMasculinos;
	private static ArrayList<String> nombresFemeninos;
	private static categorias categoria;
	
	public static void main(String[] args) {
		añadirNombres();
		generarCorredor(50);
		generarCarreras(50);
		llenarCarreras();
		generarBaseCreible();
	}
	
	@SuppressWarnings("deprecation")
	private static void generarCarreras(int n) {
		for (int i=0;i<n;i++) {
			int año = new Random().nextInt(3)+2018;
			int mes = new Random().nextInt(12);
			int dia;
			if (mes==2) 
				dia = new Random().nextInt(28)+1;
			else if (mes==1 || mes==3 || mes==5 || mes == 7 || mes==8 || mes==10 || mes == 12) 
				dia = new Random().nextInt(31)+1;
			else 
				dia = new Random().nextInt(30)+1;
			Date fecha_Celebracion = new Date(año-1900, mes, dia);
			int Dias = (10 * 24 * 60 * 60 * 1000);
			Date fecha_Fin_2Couta= new Date(fecha_Celebracion.getTime()-Dias);
			Date fecha_Fin_1Couta=new Date(fecha_Fin_2Couta.getTime()-Dias);
			Date fecha_Registro=new Date(fecha_Fin_1Couta.getTime()-Dias);
					
			int participantes = new Random().nextInt(200)+30;
			String nombre = "car" + String.valueOf(fecha_Celebracion.hashCode());
		//	Carrera car = new Carrera(fecha_Celebracion,nombre,participantes,"ABIERTA",fecha_Registro,fecha_Fin_1Couta,fecha_Fin_2Couta);
		//	Util.registraCarrera(car);
			//System.out.printf("INSERT INTO CARRERA VALUES ('%s', %s);\n", nombre, fecha);
		}
	}

	private static void añadirNombres() {
		nombresMasculinos = new ArrayList<String>();
		nombresFemeninos = new ArrayList<String>();
		nombresMasculinos.add("Alejandro");
		nombresMasculinos.add("Daniel");
		nombresMasculinos.add("David");
		nombresMasculinos.add("Pablo");
		nombresMasculinos.add("Adrián");
		nombresMasculinos.add("Javier");
		nombresMasculinos.add("Álvaro");
		nombresMasculinos.add("Sergio");
		nombresMasculinos.add("Carlos");
		nombresMasculinos.add("Jorge");
		nombresFemeninos.add("María");
		nombresFemeninos.add("Lucía");
		nombresFemeninos.add("Paula");
		nombresFemeninos.add("Laura");
		nombresFemeninos.add("Andrea");
		nombresFemeninos.add("Marta");
		nombresFemeninos.add("Alba");
		nombresFemeninos.add("Sara");
		nombresFemeninos.add("Ana");
		nombresFemeninos.add("Nerea");
		
	}

	public static void generarCorredor(int n) {
		for (int i=0;i<n;i++) {
		String dni = generarDni();
		boolean genero = generarGenero();
		String nombre = generarNombre(genero);
		Date fecha = generarFecha();
		
	//	asignarCategoria(comprobarEdad(fecha),genero);
		
		String gen = (genero?"Masculino":"Femenino");
		try {
			Util.añadirCorredor(new Corredor(nombre,dni,fecha.toString(),genero));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
   		}
		System.out.println("INSERT INTO CORREDOR VALUES ('" + dni + "','" + nombre + "','" + gen + "'," + fecha + ",'"  + String.valueOf(categoria) + "';");
		//System.out.println(fecha.toString());
		}
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

	@SuppressWarnings("deprecation")
	private static Date generarFecha() {
		int año = 2017 - (new Random().nextInt(63)+18);
		int mes = new Random().nextInt(12);
		int dia;
		if (mes==2) 
			dia = new Random().nextInt(28)+1;
		else if (mes==1 || mes==3 || mes==5 || mes == 7 || mes==8 || mes==10 || mes == 12) 
			dia = new Random().nextInt(31)+1;
		else 
			dia = new Random().nextInt(30)+1;
		Date fecha = new Date(año-1900, mes, dia);
		return fecha;
	}

	private static String generarNombre(boolean genero) {
		return (genero?nombresMasculinos.get(new Random().nextInt(nombresMasculinos.size())):nombresFemeninos.get(new Random().nextInt(nombresFemeninos.size())));
	}

	private static boolean generarGenero() {
		return (new Random().nextInt(2)==1? true: false);
	}

	private static String generarDni() {
		String dni;
			dni = String.valueOf(new Random().nextInt(100000000));
			if (dni.length()<8){
				String cero = "";
				int ceros = 8 - dni.length();
				for (int j=0; j<ceros;j++) {
					cero = cero + "0";
				}
				dni = cero + dni;
			}
			dni = dni + calcularLetraDni(Integer.valueOf(dni));
			return dni;
	}
	
	private static String calcularLetraDni(int n)
	{	
		String[] aLetras = new String[23];
		aLetras[0] = "T" ;aLetras[1] = "R" ;aLetras[2] = "W" ;
		aLetras[3] = "A" ;aLetras[4] = "G" ;aLetras[5] = "M" ;
		aLetras[6] = "Y" ;aLetras[7] = "F" ;aLetras[8] = "P" ;
		aLetras[9] = "D" ;aLetras[10] = "X" ;aLetras[11] = "B" ;
		aLetras[12] = "N" ;aLetras[13] = "J" ;aLetras[14] = "Z" ;
		aLetras[15] = "S" ;aLetras[16] = "Q" ;aLetras[17] = "V" ;
		aLetras[18] = "H" ;aLetras[19] = "L" ;aLetras[20] = "C" ;
		aLetras[21] = "K" ;aLetras[22] = "E" ;
		
		return aLetras[n%23];
	}

	@SuppressWarnings("deprecation")
	public static Date fechaAleatoria() {
	 		
		java.util.Date fecha = new java.util.Date();
	 	int dia = new Random().nextInt(fecha.getDate())+1;	
	 	Date f = new Date(fecha.getYear(), fecha.getMonth(), dia);
	 	return f;
	}

	private static void asignarCategoria(int edad, boolean genero) {
		if(genero)
		{
			if(edad >= 18 && edad < 35)
				categoria = categorias.SM;
			else if(edad >= 35 && edad < 40)
				categoria = categorias.M35;
			else if(edad >= 40 && edad < 45)
				categoria = categorias.M40;
			else if(edad >= 45 && edad < 50)
				categoria = categorias.M45;
			else if(edad >= 50 && edad < 55)
				categoria = categorias.M50;
			else if(edad >= 55 && edad < 60)
				categoria = categorias.M55;
			else if(edad >= 60 && edad < 65)
				categoria = categorias.M60;
			else if(edad >= 65 && edad < 70)
				categoria = categorias.M65;
			else if(edad >= 70)
				categoria = categorias.M70;
		}
		else
		{

			if(edad >= 18 && edad < 39)
				categoria = categorias.SF;

			else if(edad >= 40 && edad < 44)
				categoria = categorias.F40;
			else if(edad >= 45 && edad < 50)
				categoria = categorias.F45;
			else if(edad >= 50 && edad < 55)
				categoria = categorias.F50;
			else if(edad >= 55 && edad < 60)
				categoria = categorias.F55;
			else if(edad >= 60 && edad < 70)
				categoria = categorias.F60;
			else if(edad >= 70)
				categoria = categorias.F70;			
		}
	}

	private static void llenarCarreras() {
		ArrayList<Carrera> carreras;
		ArrayList<Corredor> corredores;
		try {
			carreras = BaseDatos.carrerasTotal();
			corredores = BaseDatos.devuelveCorredores();
			for(Carrera c :carreras) {
				for(int i=0;i<50;i++) {
				String DNI = corredores.get(i).getDni();	
				//Util.registraParticipante(DNI, c, new Date(new java.util.Date().getTime()));
				}
			}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static void generarBaseCreible() {
		
		//Constructor Carreras
	//	public Carrera(Date fecha_celebracion, String nombre,int numeroParticipantes, String estado,
	//	Date fechaInicioInscripcion,Date fechaFinPrimeraCouta,Date fechaFinSegundaCouta) {
		ArrayList<Carrera> carreras = new ArrayList<Carrera> ();
		ArrayList<Corredor> corredores = new ArrayList<Corredor> ();
		ArrayList<Categoria> categorias = Carrera.categoriasPorDefecto();
		try {
			corredores = BaseDatos.devuelveCorredores();
			Club c2 = new Club("");
			BaseDatos.añadirClub(c2);
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//Carreras ya Finalizadas	
		carreras.add(new Carrera(new Date(2016-1900,12-1,31),"San Silvestre",100,"FINALIZADA",categorias,20));
		carreras.add(new Carrera(new Date(2016-1900,8-1,15),"Carrera Popular Gijon",150,"FINALIZADA",categorias,20));
		carreras.add(new Carrera(new Date(2016-1900,9-1,21),"Carrera Naranco",70,"FINALIZADA",categorias,20));
		carreras.add( new Carrera(new Date(2016-1900,3-1,15),"Caleyando Ribadedeva",20,"FINALIZADA",categorias,20));
		//Registro todas las carreras
				for(Carrera car :carreras) {
					try {
						Util.registraCarrera(car);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
		for(Carrera c :carreras) {
			for(Categoria cat :c.getCategorias()) {
				try {
					if(!BaseDatos.existeCategoria(cat.getNombre()))
						BaseDatos.NuevaCategoria(cat);
					BaseDatos.VinculaCategoriaCarrera(c, cat);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}}
			
		for(Carrera c :carreras) {
			for(int j=0;j<10;j++)	{
				//Corredor corredor, Date fechaInscripcion, boolean PAGADO-SIN-RECOGER, double tiempo,
				//int identificador,int dorsal)
				Participante par = new Participante(corredores.get(j),new Date(2016-1900,10-1,j+1),"PAGADO-SIN-RECOGER",(30+j)*0.1,0,j+11,0,"");
				Util.registraParticipante(c, par);}//Les meto a las finalizadas 10 corredores
			}
		
		//Le meto corredores que no han finalizado la carrera
		for(Carrera c :carreras) {
			for(int j=0;j<5;j++) {	
				Participante par = new Participante(corredores.get(j+20),new Date(2016-1900,10-1,j+1),"PAGADO-SIN-RECOGER",0,0,j+30,0,"");
			Util.registraParticipante(c, par);}
		}
		
		//Carreras abiertas (En primera couta)
		Carrera car1 = new Carrera(new Date(2017-1900,12-1,31),"San Silvistre",100,"ABIERTA",categorias,20);
		//La clave primaria es conjuntamente la fecha  el nombre deberia de valer
		Carrera car2 = new Carrera(new Date(2018-1900,1-1,20),"Carrera Invierno",50,"ABIERTA",categorias,20);
		
		
		//Carreras abiertas (En segunda couta)
		Carrera car3 =new Carrera(new Date(2017-1900,11-1,20),"Carrera de otoño",40,"ABIERTA",categorias,20);
		Carrera car4 =new Carrera(new Date(2017-1900,12-1,8),"Carrera de Adviento",50,"ABIERTA",categorias,20);
		
		//Carreras abiertas (En couta normal)
		Carrera car5 =new Carrera(new Date(2017-1900,10-1,31),"Carrera de Halloween",40,"ABIERTA",categorias,20);
		Carrera car6 = new Carrera(new Date(2017-1900,10-1,20),"Carrera popular",100,"ABIERTA",categorias,20);
		
		//Carreras cerradas (Ya celebradas pero sin tiempos )
		Carrera car7 =new Carrera(new Date(2017-1900,10-1,17),"Carrera de la Libertad",40,"CERRADA",categorias,20);
		Carrera car8 =new Carrera(new Date(2017-1900,10-1,20),"Carrera OcktoberFest",20,"CERRADA",categorias,20);
		
		
		Carrera[] carreras2 = new Carrera[]{car1,car2,car3,car4,car5,car6,car7,car8};
		
	  
		
		
		try {
		Util.registraCarrera(car1);
		Util.registraCarrera(car2);
		Util.registraCarrera(car3);
		Util.registraCarrera(car4);
		Util.registraCarrera(car5);
		Util.registraCarrera(car6);
		Util.registraCarrera(car7);
		Util.registraCarrera(car8);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Carrera c:carreras2) {
			for(Categoria cat :c.getCategorias()) {
				try {
					if(!BaseDatos.existeCategoria(cat.getNombre()))
						BaseDatos.NuevaCategoria(cat);
					BaseDatos.VinculaCategoriaCarrera(c, cat);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}}
		for(int j=0;j<15;j++) {	
			Participante par = new Participante(corredores.get(j+25),new Date(2016-1900,10-1,j+1),"PAGADO-SIN-RECOGER",0,0,j+11,0,"");
		Util.registraParticipante(car7, par);
		Util.registraParticipante(car8, par);
		}
	//LLENO LAS CARRERAS CERRADAS
		for(int j=0;j<30;j++)	{
			//Corredor corredor, Date fechaInscripcion, boolean PAGADO-SIN-RECOGER, double tiempo,
			//int identificador,int dorsal) cua
			String estado = "PRE_INSCRITO";
			if(j%2==0) {estado = "PAGADO-SIN-RECOGER";}
			Participante par = new Participante(corredores.get(j),new Date(2017-1900,10-1,j+1),estado,0,0,j+11,0,"");
			Util.registraParticipante(car1, par);
			Util.registraParticipante(car2, par);
		}//Les meto a dos de las abiertas 30 corredores
		
	
	for(int j=0;j<40;j++)	{
		//Corredor corredor, Date fechaInscripcion, String estado, double tiempo,
		//int identificador,int dorsal)
		String estado = "PRE_INSCRITO";
		if(j%3==0) {estado = "PAGADO-SIN-RECOGER";}
		Participante par = new Participante(corredores.get(j),new Date(2016-1900,10-1,j+1),estado,0,0,0,0,"");
		Util.registraParticipante(car3, par);
		Util.registraParticipante(car5, par);
	}//Lleno dos de las carreras(Car 3 y 5)
	
	//Dejo dos carreras parcialmente vacias
	Participante par1 = new Participante(corredores.get(4),new Date(2017-1900,9-1,20),"PRE_INSCRITO",0,0,0,0,"");
	Participante par2 = new Participante(corredores.get(2),new Date(2016-1900,10-1,15),"PRE_INSCRITO",0,0,0,0,"");
	Participante par3 = new Participante(corredores.get(6),new Date(2016-1900,10-1,3),"PRE_INSCRITO",0,0,0,0,"");
	Participante par4 = new Participante(corredores.get(0),new Date(2016-1900,10-1,9),"PAGADO-SIN-RECOGER",0,0,0,0,"");
	
	
	Util.registraParticipante(car4, par1);
	Util.registraParticipante(car4, par2);
	Util.registraParticipante(car4, par3);
	Util.registraParticipante(car4, par4);
	
	Util.registraParticipante(car6, par1);
	Util.registraParticipante(car6, par2);
	Util.registraParticipante(car6, par3);
	Util.registraParticipante(car6, par4);
	
	try {
		Club c1 = new Club(car6.getNombre(),car6.getFecha_celebracion(),"EquipoA",null,30);
		Club c2 = new Club(car4.getNombre(),car4.getFecha_celebracion(),"EquipoA",null,30);

		BaseDatos.añadirClub(c1);
		
		BaseDatos.VincularClubCarrera(c1);
		BaseDatos.VincularClubCarrera(c2);

	;
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	//METO varios participantes de un equipo
	Participante par5 = new Participante(corredores.get(30),new Date(2017-1900,9-1,20),"PRE_INSCRITO",0,0,0,0,"EquipoA");
	Participante par6 = new Participante(corredores.get(31),new Date(2016-1900,10-1,15),"PRE_INSCRITO",0,0,0,0,"EquipoA");
	Participante par7 = new Participante(corredores.get(32),new Date(2016-1900,10-1,3),"PRE_INSCRITO",0,0,0,0,"EquipoA");
	Participante par8 = new Participante(corredores.get(33),new Date(2016-1900,10-1,9),"PRE_INSCRITO",0,0,0,0,"EquipoA");
	
	Util.registraParticipante(car4, par5);
	Util.registraParticipante(car4, par6);
	Util.registraParticipante(car4, par7);
	Util.registraParticipante(car4, par8);
	
	Util.registraParticipante(car6, par5);
	Util.registraParticipante(car6, par6);
	Util.registraParticipante(car6, par7);
	Util.registraParticipante(car6, par8);
	}

}
