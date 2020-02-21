//tema 2 POO Duta Viorel-Ionut
import java.io.*;

public class Tema2 {
	
	public static void main(String[] args) {
		File inFile = new File(args[0]);
		Read r = new Read();
		r.functie(inFile);//apelez functia care citeste si face toate operatiile
	}
	
}
