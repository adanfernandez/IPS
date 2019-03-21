package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JOptionPane;

import logica.Corredor;
public class FileSystem {
	
	private String dataCarrera[];

	public String[] getDataCarrera() {
		return dataCarrera;
	}

	/**
	 * carga los datos de un fichero de texto y los guarda en lista
	 * 
	 * @throws IOException
	 */
	public List<Corredor> loadTextFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		return load(reader,fileName);
	}

	public List<Corredor> loadZipFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new GZIPInputStream(new FileInputStream(fileName))));
		return load(reader,fileName);
	}

	private List<Corredor> load(BufferedReader reader,String fileName) throws IOException {
		List<Corredor> list = new LinkedList<Corredor>();
		try {
			parser(reader, list,fileName);
		} finally {
			reader.close();
		}
		return list;

	}
	private void parser(BufferedReader reader, List<Corredor> list,String fileName) throws IOException {
		String line;
		Corredor corredor;
		int lineNumber = 0;
		while (reader.ready()) {
			line = reader.readLine();
			lineNumber++;
			try {
				 if(lineNumber>1){ 
					corredor = getCorredor(line,lineNumber);
					list.add(corredor);}
				 else{
//					throw new IOException("compruebe el nombre del fichero .dat");
					 dataCarrera = line.split(",");
					 
					}
					
					
			} catch (IOException ife) {
				System.err.println("error de formato en linea: " + lineNumber + "\n" + ife.getMessage());
			}
		}
	}


	// private void handleIllegalFormatException(IFormatException ife, int
	// lineNumber)
	// {
	// System.out.println("error de formato en linea:
	// "+lineNumber+"\n"+ife.getMessage());
	// Log.log("error de formato en linea: "+lineNumber+"\n"+ife.getMessage());
	// }


	private Corredor getCorredor(String line, int lineNumber) throws IOException,ArrayIndexOutOfBoundsException {
		try{
				String[] dataCorredor = line.split(" ");// parte la linea	
				String nombre = dataCorredor[0];
	//			System.out.println(nombre);
				String dni = dataCorredor[1];
				String fechaNacimiento = dataCorredor[2];
				String genero = dataCorredor[3];
				boolean generoBool;
				if(genero.equals("Masculino"))
				{
					generoBool=true;
				}else{generoBool=false;}
				
				try {
					return new Corredor(nombre,dni,fechaNacimiento,generoBool);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"error de formato en linea: " + lineNumber);
			}
			return null;
	}
	

	
	public void safeToTextFile1(String fileName, List<Object> list) {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
			safe(writer, list);
		} catch (IOException e) {
			System.err.println("No se puede salvar el fileName");
		}

	}
	public void safeToTextFile2(String fileName, List<Object> list) {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			safe(writer, list);
		} catch (IOException e) {
			System.err.println("No se puede salvar el fileName");
		}

	}
	public void safeToZipFile(String fileName, List<Object> list) {

		try {
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(fileName))));
			safe(writer, list);
		} catch (IOException e) {
			System.err.println("No se puede salvar el fileName");
		}

	}

	private void safe(BufferedWriter writer, List<Object> list) throws IOException {
		try {
			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i).toString() + "\n");
				// writer.newLine();
			}
		} finally {
			writer.close();
		}

	}


	public ArrayList<String> getLineFromFile(String fileName) throws IOException {
		ArrayList<String> lista = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		try {
			while (reader.ready()) {
				lista.add(reader.readLine());

			}
		} finally {
			reader.close();
		}
		return lista;

	}

}
