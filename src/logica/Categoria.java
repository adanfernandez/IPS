package logica;

public class Categoria {
	
	private String nombre;
	private int edadi;
	private int edadf;
	
	String genero;
	
	public Categoria(String nombre, int edad0, int edadf, String g)
	{
		this.edadi=edad0;
		this.edadf=edadf;
		this.nombre=nombre;
		this.genero=g;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdadi() {
		return edadi;
	}

	public void setEdadi(int edadi) {
		this.edadi = edadi;
	}

	public int getEdadf() {
		return edadf;
	}

	public void setEdadf(int edadf) {
		this.edadf = edadf;
	}

	public String getGenero() {
		return genero;
	}

	public void setMasculino(String genero) {
		this.genero = genero;
	}

	public boolean perteneceAcategoria(Participante p) {
		boolean genero=false;
		if(this.genero.equals("Mixto")) {
			genero = true;
		}
		else	if(p.getCorredor().getGenero()) {
		 genero = this.genero.equals("Masculino");
		}
		else {genero = this.genero.equals("Femenino");}
		boolean edad =(	p.getCorredor().getEdad()<=edadf &&
				p.getCorredor().getEdad()>=edadi);
		return genero&&edad;
	}

}
