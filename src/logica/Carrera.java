package logica;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Carrera {

	private List<Participante> v = new ArrayList<Participante>();
	private List<Cuota> cuotas;
	private List<Participante> participantes;
	private Date fecha_celebracion;
	private String nombre;
	private int numeroParticipantes;
	private String estado;
	private int devolucion;
	private ArrayList<Categoria> categorias = new ArrayList<Categoria>();
	private Cuota cuotaActual;
	private boolean alrevesPago = false;
	private boolean alrevesFecha = false;



	public Carrera(Date fecha_celebracion, String nombre,
			int numeroParticipantes, String estado, 
			ArrayList<Categoria> categorias, int devolucion) {
		this.fecha_celebracion = fecha_celebracion;
		this.nombre = nombre;
		this.numeroParticipantes = numeroParticipantes;
		this.estado = estado;
		this.devolucion = devolucion;
		this.categorias = categorias;
	}

	public double getDineroDevuelto(Date fechaInscripcion) {
		double pago =  getCoste(fechaInscripcion);
		double dev = pago * (devolucion / 100.0);

		if (devolucion >= 100) {
			return pago;
		}
		if (devolucion <= 0) {
			return 0;
		}
		if (pago == 0) {
			return 0;
		}
		return dev;
	}

	public int getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(int devolucion) {
		this.devolucion = devolucion;
	}

	public Carrera(Date fecha, String nombre, String estado) {
		this.fecha_celebracion = fecha;
		this.nombre = nombre;
		this.estado = estado;
		participantes = new ArrayList<Participante>();
	}

	public double getCoste(Date fechaInscripcion) {
		//int precio = getPrecio();
		if (cuotas == null) {
			return 20;
		}
		for (Cuota c : cuotas) {
			if (c.entraEnTarifa(fechaInscripcion)) {
				setCuotaActual(c);
				return c.getDescuento();
			}}
		return 20;
	}
		
	public  List<Cuota> getCuotas(){
		return cuotas;
	}

	public List<Participante> getparticipantes() {
		return participantes;
	}

	public void setparticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}

	public Date getFecha_celebracion() {
		return fecha_celebracion;
	}

	public void setFecha_celebracion(Date fecha_celebracion) {
		this.fecha_celebracion = fecha_celebracion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Participante> clasificacionAbsoluta() {
		ArrayList<Participante> absoluta = new ArrayList<Participante>();
		ArrayList<Participante> aux = new ArrayList<Participante>();
		for (Participante c : participantes) {
			if (c.getTiempo() == 0.0) {
				aux.add(c);
			} else
				add(c, absoluta);
		}
		for (int i = 0; i < aux.size(); i++)
			absoluta.add(aux.get(i));
		return absoluta;
	}

	public ArrayList<Participante> clasificacionMasculina() {
		ArrayList<Participante> absoluta = new ArrayList<Participante>();
		ArrayList<Participante> aux = new ArrayList<Participante>();

		for (Participante c : participantes) {
			if (c.getCorredor().getGenero()) {
				if (c.getTiempo() == 0.0) {
					aux.add(c);
				} else
					add(c, absoluta);
			}

		}
		for (int i = 0; i < aux.size(); i++)
			absoluta.add(aux.get(i));

		return absoluta;
	}

	public ArrayList<Participante> clasificacionFemenina() {
		ArrayList<Participante> absoluta = new ArrayList<Participante>();
		ArrayList<Participante> aux = new ArrayList<Participante>();

		for (Participante c : participantes) {
			if (!c.getCorredor().getGenero()) {
				if (c.getTiempo() == 0.0) {
					aux.add(c);
				} else
					add(c, absoluta);
			}

		}
		for (int i = 0; i < aux.size(); i++)
			absoluta.add(aux.get(i));

		return absoluta;
	}

	public void add(Participante c) {
		participantes.add(c);
	}

	/**
	 * OJO Metodo Peligroso zabrozongo Metodo que aï¿½ade un Participante a la
	 * lista
	 * 
	 */

	private ArrayList<Participante> add(Participante Participante,
			ArrayList<Participante> l) {
		int position = 0;
		position = findPositionByComparable(Participante, l);
		l.add(position, Participante);
		return l;
	}

	/**
	 * compara los participantes de la lista para posteriormente ordenarlos
	 * 
	 * @param
	 * @return
	 */
	private int findPositionByComparable(Participante ParticipanteToCompare,
			ArrayList<Participante> l) {

		for (int i = 0; i < l.size(); i++) {

			Participante ParticipanteInList = (Participante) l.get(i);

			if ((ParticipanteInList).compareTo(ParticipanteToCompare) > 0) {
				return i;
			}
		}

		return l.size();

	}

	public String toString() {

		return "" + getNombre() + "  " + getFecha_celebracion().toString();
	}

	public List<Participante> getVectorOrdenado() {
		return v;
	}

	/**
	 * Ordenaciï¿½n por selecciï¿½n
	 * 
	 * @param a
	 *            array de enteros, despuï¿½s de la llamada quedarï¿½ ordenado
	 */
	public void ordenarFechaRegistro() {

		if (!alrevesFecha) {
			List<Participante> a = participantes;

			int n = a.size();
			int posmin;
			for (int i = 0; i < n - 1; i++) {
				// Buscar la posicion del mas pequeï¿½o de los que quedan
				posmin = i;
				for (int j = i + 1; j < n; j++)
					if (a.get(j).getFechaRegistro()
							.compareTo(a.get(posmin).getFechaRegistro()) == -1)
						posmin = j;

				// Intercambia el que toca con el mï¿½s pequeï¿½o
				Intercambiar(a, i, posmin);

			}

			v = a;
			alrevesFecha = true;
			alrevesPago = false;
		} else {
			List<Participante> a = participantes;

			int n = a.size();
			int posmin;
			for (int i = 0; i < n - 1; i++) {
				// Buscar la posicion del mas pequeï¿½o de los que quedan
				posmin = i;
				for (int j = i + 1; j < n; j++)
					if (a.get(j).getFechaRegistro()
							.compareTo(a.get(posmin).getFechaRegistro()) == -1)
						posmin = j;

				// Intercambia el que toca con el mï¿½s pequeï¿½o
				Intercambiar(a, i, posmin);
			}
			Collections.reverse(a);
			v=a;
			alrevesFecha=false;
		}

	}

	/**
	 * Ordenaciï¿½n por selecciï¿½n
	 * 
	 * @param a
	 *            array de enteros, despuï¿½s de la llamada quedarï¿½ ordenado
	 */
	public void ordenarPago() {
		if (!alrevesPago) {
			List<Participante> a = participantes;
			Collections.sort(a, new Comparator<Participante>() {
				public int compare(Participante f1, Participante f2) {
					return f1.getEstado().compareTo(f2.getEstado());
				}
			});
			v = a;
			alrevesPago = true;
			alrevesFecha = false;
		} else {
			List<Participante> a = participantes;
			Collections.sort(a, new Comparator<Participante>() {
				public int compare(Participante f1, Participante f2) {
					return f1.getEstado().compareTo(f2.getEstado());
				}
			});
			Collections.reverse(a);
			v = a;
			alrevesPago = false;
		}
	}

	/**
	 * Intercambiar los elementos de las posiciones i, j en el array v
	 * 
	 */
	private void Intercambiar(List<Participante> v, int i, int j) {
		Participante t;
		t = v.get(i);
		v.set(i, v.get(j));
		v.set(j, t);
	}

	public int getNumeroParticipantes() {
		return numeroParticipantes;
	}

	public void setNumeroParticipantes(int numeroParticipantes) {
		this.numeroParticipantes = numeroParticipantes;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	

	public List<Cuota> getTarifas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public ArrayList<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(ArrayList<Categoria> categorias) {
		this.categorias = categorias;
	}

	public void cargarCategoriasFerni() {

		for (Participante par : participantes) {
			for (Categoria cat : categorias) {
				if (cat.perteneceAcategoria(par)) {
					par.setCategoria(cat);
				}
			}
		}
	}

	/**
	 * Asigna una categoría a cada participante
	 */
	public void cargarCategorias() {
		categorias = new ArrayList<Categoria>();
		for (int i = 0; i < categorias.size(); i++) {
			for (int j = 0; j < participantes.size(); j++) {
				if (categorias.get(i).genero.equals("Masculino")) {
					if (participantes.get(j).getCorredor().getGenero()) {
						if (participantes.get(j).getCorredor().getEdad() <= categorias
								.get(i).getEdadf()
								&& participantes.get(j).getCorredor().getEdad() >= categorias
										.get(i).getEdadi()) {
							participantes.get(j)
									.setCategoria(categorias.get(i));
						}
					}
				} else if (categorias.get(i).genero.equals("Femenino")) {
					if (!participantes.get(j).getCorredor().getGenero()) {
						if (participantes.get(j).getCorredor().getEdad() <= categorias
								.get(i).getEdadf()
								&& participantes.get(j).getCorredor().getEdad() >= categorias
										.get(i).getEdadi()) {
							participantes.get(j)
									.setCategoria(categorias.get(i));
						}
					}
				} else if (categorias.get(i).genero.equals("Mixto")) {

					if (participantes.get(j).getCorredor().getEdad() <= categorias
							.get(i).getEdadf()
							&& participantes.get(j).getCorredor().getEdad() >= categorias
									.get(i).getEdadi()) {
						participantes.get(j).setCategoria(categorias.get(i));
					}

				}
			}
		}
	}

	/**
	 * Método para ver si un corredor puede inscribirse en una carrera
	 */
	public boolean puedeInscribirse(Corredor corredor) {
		for (int i = 0; i < categorias.size(); i++) {
			if (categorias.get(i).genero.equals("Mixto")) {
				if (corredor.getEdad() >= categorias.get(i).getEdadi()
						&& corredor.getEdad() <= categorias.get(i).getEdadf())
					return true;
			}
			if (categorias.get(i).genero.equals("Masculino")) {
				if (corredor.getGenero()
						&& corredor.getEdad() >= categorias.get(i).getEdadi()
						&& corredor.getEdad() <= categorias.get(i).getEdadf())
					return true;
			}
			if (categorias.get(i).genero.equals("Femenino")) {
				if (corredor.getGenero() == false
						&& corredor.getEdad() >= categorias.get(i).getEdadi()
						&& corredor.getEdad() <= categorias.get(i).getEdadf())
					return true;
			}
		}
		return false;
	}
	
	public boolean puedeInscribirseFecha()
	{
		Date hoy = new Date(new java.util.Date().getTime());
		for(Cuota cuota:cuotas)
		{
			if(cuota.entraEnTarifa(hoy))
				return true;
		}
		return false;
	}

	public static ArrayList<Categoria> categoriasPorDefecto() {
		Categoria categoria1m = new Categoria("SM", 0, 34, "Masculino");
		Categoria categoria2m = new Categoria("M-35", 35, 39, "Masculino");
		Categoria categoria3m = new Categoria("M-40", 39, 44, "Masculino");
		Categoria categoria4m = new Categoria("M-45", 45, 49, "Masculino");
		Categoria categoria5m = new Categoria("M-50", 50, 54, "Masculino");
		Categoria categoria6m = new Categoria("M-55", 55, 59, "Masculino");
		Categoria categoria7m = new Categoria("M-60", 60, 65, "Masculino");
		Categoria categoria8m = new Categoria("M-65", 66, 70, "Masculino");
		Categoria categoria9m = new Categoria("M-70", 71, Integer.MAX_VALUE,
				"Masculino");

		Categoria categoria1f = new Categoria("SF", 0, 39, "Femenino");
		Categoria categoria2f = new Categoria("F-40", 40, 44, "Femenino");
		Categoria categoria3f = new Categoria("F-45", 45, 49, "Femenino");
		Categoria categoria4f = new Categoria("F-50", 50, 54, "Femenino");
		Categoria categoria5f = new Categoria("F-55", 55, 59, "Femenino");
		Categoria categoria6f = new Categoria("F-60", 60, 70, "Femenino");
		Categoria categoria7f = new Categoria("F-70", 71, Integer.MAX_VALUE,
				"Femenino");

		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		categorias.add(categoria1m);
		categorias.add(categoria2m);
		categorias.add(categoria3m);
		categorias.add(categoria4m);
		categorias.add(categoria5m);
		categorias.add(categoria6m);
		categorias.add(categoria7m);
		categorias.add(categoria8m);
		categorias.add(categoria9m);
		categorias.add(categoria1f);
		categorias.add(categoria2f);
		categorias.add(categoria3f);
		categorias.add(categoria4f);
		categorias.add(categoria5f);
		categorias.add(categoria6f);
		categorias.add(categoria7f);

		return categorias;

	}

	public Cuota getCuotaActual() {
		return cuotaActual;
	}

	public void setCuotaActual(Cuota cuotaActual) {
		this.cuotaActual = cuotaActual;
	}

	
	
}