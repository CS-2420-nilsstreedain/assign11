package comprehensive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;


public class RandomPhraseGenerator {
	HashMap<String, String[]> nonTerminals;

	Random rng;
	BufferedReader reader;
	
	public static void main(String[] args) {
		RandomPhraseGenerator phraseGenerator = new RandomPhraseGenerator(args[0]);
		
		for (int i = 0; i < Integer.parseInt(args[1]); i++)
			System.out.println(phraseGenerator.getProductions());
	}
	
	public RandomPhraseGenerator(String filename) {
		nonTerminals = new HashMap<>();
		rng = new Random();
		
		try {
			File file = new File(filename);
			reader = new BufferedReader(new FileReader(filename));
		
			String curr;
			while ((curr = reader.readLine()) != null) {
		
				//skips over anything outside a curlybrace
				if (curr.isBlank() == false && curr.charAt(0) == '{') {
					String key = reader.readLine();
					reader.mark((int) file.length());
					int count = 0;
		
					curr = reader.readLine();
					while (curr.charAt(0) != '}') {
						count++;
						curr = reader.readLine();
					}
					
					String[] prodRules = new String[count];
					reader.reset();
					
					count = 0;
					
					curr = reader.readLine();
					while (curr.charAt(0) != '}') {
						prodRules[count++] = curr;
						curr = reader.readLine();
					}
					
					nonTerminals.put(key, prodRules);
					
				}	
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException f) {
				f.printStackTrace();
			}
		}
	}
	
	public StringBuilder getProductions() {
		StringBuilder nonTerminal = new StringBuilder("<start>");
		int startIndex, endIndex;
		
		//increment over the whole string
		for (int i = 0; i < nonTerminal.length(); i++) {
			//gets the index of the beginning of the first nonterminal
			if (nonTerminal.charAt(i) == '<') {
				startIndex = endIndex = i;
				
				//gets the end index for the current nonterminal
				while (nonTerminal.charAt(endIndex) != '>')
					endIndex++;
				
				//gets the possible productions to replace the nonterminal with
				String keyString = nonTerminal.subSequence(startIndex, endIndex + 1).toString();
				String[] possibleResults = nonTerminals.get(keyString);
				
				//replaces the nonterminal inside our string with a possible production
				nonTerminal.replace(startIndex, endIndex + 1, possibleResults[rng.nextInt(possibleResults.length)]);
				
				//decrements i so if another nonterminal was placed at the beginning of the one we just replaced,
				//we will check it and replace it
				i--;
			}
		}
		
		return nonTerminal;
	}
}