package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RandomPhraseGenerator {
	
	

	public static void main(String[] args) {
		HashMap<String, String[]> nonTerminals = new HashMap<String, String[]>();
//		HashMap<String, ArrayList<String>> nonTerminals = new HashMap<>();
		Scanner scan = null;
		try {
			String filename = args[0];
			int sentences = Integer.parseInt(args[1]);
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		while (scan.hasNext()) {
			//continues until open curlybrace, discards that line
			if (scan.nextLine().charAt(0) == '{') {
				String key = scan.nextLine();
				StringBuilder prodRulesBuilder = new StringBuilder();
				
				//keeps grabbing lines until a closed curlybrace
				while (true) {
					String line = scan.nextLine();
					if (line.charAt(0) == '{')
						break;
					prodRulesBuilder.append(line);
				}
				
				nonTerminals.put(key, prodRulesBuilder.toString().split("\n"));
			}
			
			
	//ARRAYLIST VERISON		
//			if (scan.nextLine().charAt(0) == '{') {
//				String key = scan.nextLine();
//				ArrayList<String> prodRules = new ArrayList<>();
//				
//				while (true) {
//					String line = scan.nextLine();
//					if (line.charAt(0) == '{')
//						break;
//					prodRules.add(line);
//				}
//				
//				nonTerminals.put(key, prodRules);
//				
//			}
				
		}

	}

}