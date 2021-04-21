package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomPhraseGenerator {
	HashMap<String, ArrayList<String>> nonTerminals;

	Random rng;
	Scanner scan;
	
	public static void main(String[] args) {
//		try {
			String filename = args[0];
			int sentences = Integer.parseInt(args[1]);
//		} catch (ArrayIndexOutOfBoundsException e) {
//			System.out.println(e.getMessage());
//			System.exit(0);	
//		}
		
		RandomPhraseGenerator phraseGenerator = new RandomPhraseGenerator(filename);
		
		for (int i = 0; i < sentences; i++)
			System.out.println(phraseGenerator.finalPhrase("<start>"));
		
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
		
		while (scan.hasNext()) {
			if (scan.nextLine().contains("{")) { // Look at contains
				String key = scan.nextLine();
				ArrayList<String> prodRules = new ArrayList<>();
				
				while (true) {
					String line = scan.nextLine();
					if (line.contains("}")) // Look at contains
						break;
					prodRules.add(line);
				}
				
				nonTerminals.put(key, prodRules);
			}	
		}
	}
	
	public String finalPhrase(String nonTerminal) {
		ArrayList<String> possibleResults;
		Matcher m = Pattern.compile("(\\<.*?\\>)").matcher(nonTerminal);

		while (m.find()) {
			possibleResults = nonTerminals.get(m.group(1));
			nonTerminal = nonTerminal.replaceFirst(m.group(1), possibleResults.get(rng.nextInt(possibleResults.size()))); // Look at arraylist
		}
		
		if (nonTerminal.contains("<"))
			nonTerminal = finalPhrase(nonTerminal);
		
		return nonTerminal;
	}
	
	public void getProductions(StringBuilder nonTerminalStringBuilder) {
		
		int startIndex, endIndex;
		endIndex = startIndex = nonTerminalStringBuilder.indexOf("<");
		while (endIndex != -1) {
			while (nonTerminalStringBuilder.charAt(endIndex) != '>')
				endIndex++;
			nonTerminalStringBuilder.replace(startIndex, endIndex + 1, nonTerminalStringBuilder.subSequence(startIndex, endIndex + 1).toString());
		}
		
		
	}
}
