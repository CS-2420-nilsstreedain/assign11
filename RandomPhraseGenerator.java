package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class RandomPhraseGenerator {
	HashMap<String, ArrayList<String>> nonTerminals;

	Random rng;
	Scanner scan;
	
	public static void main(String[] args) {
		RandomPhraseGenerator phraseGenerator = new RandomPhraseGenerator(args[0]);
		
		for (int i = 0; i < Integer.parseInt(args[1]); i++)
			System.out.println(phraseGenerator.getProductions());
	}
	
	public RandomPhraseGenerator(String filename) {
		nonTerminals = new HashMap<>();
		rng = new Random();
		
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		while (scan.hasNextLine()) {
			String nextLine = scan.nextLine();

			if (nextLine.isBlank() == false && nextLine.charAt(0) == '{') {
				String key = scan.nextLine();
				ArrayList<String> prodRules = new ArrayList<>();

				nextLine = scan.nextLine();
				while (nextLine.charAt(0) != '}') {
					prodRules.add(nextLine);
					nextLine = scan.nextLine();
				}
				
				nonTerminals.put(key, prodRules);
			}	
		}
	}
	
	public StringBuilder getProductions() {
		StringBuilder nonTerminal = new StringBuilder("<start>");
		int startIndex, endIndex;
		
		for (int i = 0; i < nonTerminal.length(); i++) {
			if (nonTerminal.charAt(i) == '<') {
				startIndex = endIndex = i;
				
				while (nonTerminal.charAt(endIndex) != '>')
					endIndex++;
				
				String keyString = nonTerminal.subSequence(startIndex, endIndex + 1).toString();
				ArrayList<String> possibleResults = nonTerminals.get(keyString);
				
				nonTerminal.replace(startIndex, endIndex + 1, possibleResults.get(rng.nextInt(possibleResults.size())));
				
				i--;
			}
		}
		
		return nonTerminal;
	}
}