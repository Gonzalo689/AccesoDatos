import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Cipher {

	private int[] swaps;
	
	public Cipher(int password)
	{
		swaps = new int[256];
		for(int i=0; i<swaps.length; i++)
		{
			swaps[i]=-1;
		}
		shuffle(password);
	}

	public void encrypt(File src, File dst)
	{
		if(src.exists() && src.isFile()){

		}
	}
	
	public void decrypt(File src, File dst)
	{
		if(src.exists() && src.isFile()){
			
		}
	}
	
	private void shuffle(int password)
	{
		//Mete valores desde 0 a 255 en el array de forma aleatoria y 
		//sin repetidos <-(usar el método find para esto)
		//Código de ayuda
		Random r = new Random(password);
		int max = swaps.length;
		int random = r.nextInt(max);
	}
	
	private boolean find(int value)
	{
		//Busca el valor en el array 'swaps' y devuelve si lo encuentra o no
		return true;
	}
	
	public static void close(Closeable c)
	{
	     if (c == null) return; 
	     try 
	     {
	        c.close();
	     } 
	     catch (IOException e) 
	     {
	        //log the exception
	     }
	}
	
	public static void main(String[] args) 
	{
		if(args.length==4){
			Cipher cipher = new Cipher(Integer.parseInt(args[1]));
			switch(args[0]){
				case "-c":
					System.out.println("Cifrando fichero '"+args[2]+"' con password: "+args[1]);
					cipher.encrypt(new File(args[2]), new File(args[3]));
					System.out.println("Fichero cifrado: "+args[3]);
				break;
				case "-d":
					System.out.println("Descifrando fichero '"+args[2]+"' con password: "+args[1]);
					cipher.decrypt(new File(args[2]), new File(args[3]));
					System.out.println("Fichero descifrado: "+args[3]);
				break;
				default:
					System.out.println("Operación no implementada");
				break;
			}
		}
		else{
			System.err.println("El programa sólo admite 4 argumentos");
			System.out.println("Para cifrar: -c password srcFile secretFile");
			System.out.println("Para descifrar: -d password secretFile dstFile");
		}
		
	}
}
