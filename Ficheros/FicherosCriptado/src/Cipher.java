import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
			try {
				BufferedReader bfr = new BufferedReader(new InputStreamReader(
						new FileInputStream(src), "UTF-8"));
				BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(dst), "UTF-8"));
				
				String linea = "";
				bfw.write("");
				
				while ((linea = bfr.readLine()) != null) {
					for (int i = 0; i < linea.length(); i++) {
						int asciiAleatorio = swaps[linea.charAt(i)];
						bfw.write((char) asciiAleatorio);
					}
					bfw.newLine();
				}

				close(bfw);
				close(bfr);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
		}
	}
	
	public void decrypt(File src, File dst)
	{
		if(src.exists() && src.isFile()){
			try {
				BufferedReader bfr = new BufferedReader(new InputStreamReader(
						new FileInputStream(src), "UTF-8"));
				BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(dst), "UTF-8"));
				
				int caracter;
				String linea = "";
				bfw.write("");

				while ((linea = bfr.readLine()) != null) {
					for (int i = 0; i < linea.length(); i++) {
						caracter = linea.charAt(i);
						for (int j = 32; j <= 126 ; j++) 
							if (swaps[j] == caracter) {
								bfw.write((char)j);
								break;
							}
					}
					bfw.newLine();
				}

				close(bfw);
				close(bfr);

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	private void shuffle(int password)
	{
		Random r = new Random(password);
		int longitud = swaps.length;
		int max = 126;
		int min = 32;
		int numerosAleatorios;
		
		for (int i = 0; i < longitud; i++) {
            if(i < min || i > max)
              swaps[i] = r.nextInt(longitud); 
            else{
                numerosAleatorios = r.nextInt(max - min + 1) + min;
                if(!find(numerosAleatorios))
					swaps[i] = numerosAleatorios;
				else
					i--;
            }
        }

	}
	
	private boolean find(int value)
	{
		for (int i = 32; i < 126; i++) {
			if(swaps[i] == value)
				return true;
			
		}
		return false;
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
	         e.printStackTrace();
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
