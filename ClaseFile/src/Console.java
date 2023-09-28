import java.io.File;
public class Console {
	/**
	 * Método que sirve para enseñar todos los directorios y ficheros donde se encuentra o de la ruta elegida
	 * @param args parámetros escritos en la terminal [0] comando ls [1] ruta (opcional)
	 */
	public void ls(String[] args){
        String ruta = "";
		if(args.length == 1 || args.length == 2) {
			try {
				if (args.length == 1) {
					ruta = System.getProperty("user.dir");
				} else if (args.length == 2) {
					ruta = args[1];
				}else
					System.out.println("No se encontro la ruta");

				File rutaFile = new File(ruta);
				if(rutaFile.isDirectory()){
					String[] arraysFiles = rutaFile.list();
					for (String file : arraysFiles) 
						System.out.println(file + " ");
				}else
					System.out.println("Ruta no encontrada");		
			} catch (Exception e) {
				e.printStackTrace();
        	}
		}else
			System.out.println("Parametros no validos");
	} 
	/**
	 * Método que sirve para copiar un fichero
	 * @param args parámetros escritos en la terminal [0] comando cp [1] ruta donde se desea copiar el fichero
	 *  [2] ruta nueva 
	 */

	public void cp(String[] args){
		if(args.length == 3){
			String ruta1 = args[1];
			String ruta2= args[2];
			File fichero = new File(ruta1);
			File directorio = new File(ruta2);
			try {
				if(fichero.isFile())
					if(directorio.exists()){
						String nombre = fichero.getName();
						File ficheroCopiado = new File(directorio + "\\" + nombre);
						ficheroCopiado.createNewFile();
						System.out.println("Archivo copiado correctamente");
					}else
						System.out.println("La ruta no existe");
				else
					System.out.println("El fichero que desea copiar no existe");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else
			System.out.println("Parametros no validos");
	} 
	/**
	 * Método para mover ficheros 
	 * @param args parámetros escritos en la terminal [0] comando mv [1] ruta donde se movera el fichero 
	 *  [2] ruta nueva con su nombre nuevo nombre(nombre opcional) 
	 */
	public void mv(String[] args){
		if(args.length == 3){
			String ruta1 = args[1];
			String ruta2= args[2];
			File fichero = new File(ruta1);
			File directorio = new File(ruta2);
			try {
				File ruta3 = directorio.getParentFile();
				if(fichero.isFile())
					if(directorio.isDirectory()){
						String nombre = fichero.getName();
						fichero.delete();
						File ficheroCopiado = new File(directorio + "\\" + nombre);
						ficheroCopiado.createNewFile();
						System.out.println("Archivo movido correctamente");
					}else if(ruta3.isDirectory()){ 
						String nombre = directorio.getName();
						fichero.delete();
						File ficheroCopiado = new File(ruta3 + "\\" + nombre);
						ficheroCopiado.createNewFile();
						System.out.println("Archivo movido correctamente");
					}else
						System.out.println("El archivo que desea copiar no existe");
				else
					System.out.println("Fichero no encontrado");
			
			} catch (Exception e) {
				System.out.println("as");
			}
			
		}else
			System.out.println("Parametros no validos");
	} 
	/**
	 * Método para borrar ficheros y directorios, para borrar directorios se pondra el comando -p (se borrara todo el contenido del directorio)
	 * @param args parámetros escritos en la terminal [0] comando rm [1] ruta + nombre o 
	 * 	comando -p para crear varios directorios
	 *  [3] en caso de que se quiera borrar todo el contenido del directorio
	 */
	
	public void rm(String[] args) {
		String ruta;
		File fichero;
		if(args.length == 2){
			ruta = args[1];
			fichero = new File(ruta);
			if (fichero.isFile()) {
				fichero.delete();
				System.out.println("Borrado con éxito");
			} else if(fichero.isDirectory())
				System.out.println("Para borrar directorios hay que usar el -r");
			else
				System.out.println("Fichero no encontrado");

		}else if(args.length == 3 && args[1].equals("-r")){
			ruta = args[2];
			fichero = new File(ruta);
			if(fichero.exists()){
				borrarFile(fichero);
				System.out.println("Borrado con éxito");
			}else 
				System.out.println("Fichero o directorio no encontrado");
		}else
			System.out.println("Parametros no validos");
	}
	/**
	 * se le pasa el directorio que se desea borrar y hace un borrado recursivo
	 * @param fichero directorio principal que se desea borrar
	 */
    public void borrarFile(File fichero) {
        if(fichero.isDirectory()){
            File[] ficheros = fichero.listFiles();         
            for(File archivo : ficheros) 
                borrarFile(archivo);
        }
        fichero.delete();
    }
	/**
	 * Método para crear directorio para crear mas de un directorio se pondra el comando -p
	 * @param args parámetros escritos en la terminal [0] comando mkdir [1] ruta + nombre directorio(nuevo) o 
	 * 	comando -p para crear varios directorios
	 *  [3] en caso de que se quiera crear directorio/s el nombre del mismo 
	 */

	public void mkdir(String[] args){
		String ruta1;
		File directorio;
		if(args.length == 2){
			ruta1 = args[1];
			directorio = new File(ruta1);
			File ruta2 = directorio.getParentFile();

			if(ruta2.isDirectory()){
				String nombre = directorio.getName();
				File nuevoDirectorio = new File(ruta2 + "\\" + nombre);
				nuevoDirectorio.mkdir();
				System.out.println("Directorio creado");
			}else
				System.out.println("Ruta no encontrada");
		}else if(args.length == 3 && args[1].equals("-p")){
			ruta1 = args[2];
			directorio = new File(ruta1);
			if(!directorio.exists()){
			crearDirectorios(directorio);
			System.out.println("Creaccion con exito");
			}else
				System.out.println("El directorio ya existe");
		}else
			System.out.println("Parametros no validos");
	}
	/**
	 * Método que para crear recursivamente los directorios eseados
	 * @param directorio directorio o conjunto de directorios que se quieren crear divididos por "\"
	 */
	public void crearDirectorios(File directorio){
		if(!directorio.exists())
            crearDirectorios( directorio.getParentFile());
		directorio.mkdir();
	}
	/**
	 * Método que sirve para crear un fichero en una ruta si no hay ruta lo crea desde donde se ha ejecutado el programa
	 * @param args parámetros escritos en la terminal [0] comando touch [1] nombre fichero [2]ruta (opcional) 
	 */
	public void touch(String[] args){
		String nombreFichero = args[1];
		String ruta = "";
		File ficheroNuevo;
		if(args.length == 2 || args.length ==3){
			if(args.length == 2)
				ruta = System.getProperty("user.dir");
			else if(args.length == 3)
				ruta = args[2];
				
			File directorio = new File(ruta);
				if(directorio.isDirectory()){
					ficheroNuevo = new File(ruta + "\\" + nombreFichero);
					try {
						ficheroNuevo.createNewFile();
						System.out.println("Archivo Creado");
					} catch (Exception e) {
						System.out.println("Error al cear el fichero");
					}
				}
		}else
			System.out.println("Parametros no validos");
	} 
	/**
	 *  Busca un nombre o un fichero en una ruta en concreto si no se especifica ruta lo crea desde donde se ha ejecutado el programa
	 * @param args parámetros escritos en la terminal. [0] comando grep [1] lo que se busca [2]ruta (opcional) 
	 */
	public void grep(String[] args){
		String patron = args[1];
		String ruta = "";
		int cont = 0;
		if(args.length == 2){
			if(args.length==2)
				ruta = System.getProperty("user.dir");	
			else if(args.length==3)
				ruta = args[2];

			File rutaFile = new File(ruta);
			if(rutaFile.isDirectory()){
				File[] fileAll = rutaFile.listFiles();
				for (File file : fileAll) {
					String nombre = file.getName();
					String[] nuevoNombre = nombre.split("\\.");
					if(nombre.equals(patron)){
						System.out.println(nombre);
						cont++;
					}else if(nuevoNombre[0].equals(patron)){
						System.out.println(nombre);
						cont++;
					}
				}
				if(cont == 0)
					System.out.println("no se encontro ningun fichero");
			}else
				System.out.println("Error en la ruta");
		}else
			System.out.println("Parametros no validos");	
	}
	/**
	 * Programa principal donde se ejecutara principalmente desde la terminal donde se activara el comando correspondiente
	 * @param args parámetros escritos desde la terminal
	 */
	public static void main(String[] args){
		Console console = new Console();
		if(args.length>0){
			switch (args[0]){
			case "ls":
				console.ls(args);
				break;
			case "cp":
				console.cp(args);
				break;
			case "mv":
				console.mv(args);
				break;
			case "rm":
				console.rm(args);
				break;
			case "mkdir":
				console.mkdir(args);
				break;
			case "touch":
				console.touch(args);
				break;
			case "grep":
				console.grep(args);
				break;
			case "exit":
				console.grep(args);
				break;
			default:
				System.out.println("Comado desconocido");
				break;	
			}
		}
	}
}
