package inter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

	
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("H_VenderpName.txt");
		Scanner sc = new Scanner(file);
		
		
		for (int i = 0; i < 3; i++) {
			System.out.println("-------------------");
			sc.hasNext();
			System.out.println(sc.findInLine("닭"));
			sc.nextLine();
			System.out.println(sc.findInLine("매운양념"));
			sc.nextLine();
			System.out.println(sc.findInLine("간장양념"));
			sc.nextLine();
			System.out.println(sc.findInLine("매운양념"));
			sc =  new Scanner(file);
			
		}
		
	
	}

}

//닭-5000
//매운양념-4000
//간장양념-4000
//콜라-1700

