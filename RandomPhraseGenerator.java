package comprehensive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class RandomPhraseGenerator {
	HashMap<String, ArrayList<String>> nonTerminals;

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
			reader = new BufferedReader(new FileReader(filename));

			String curr;
			while ((curr = reader.readLine()) != null) {

				// skips over anything outside a curlybrace
//				if (!curr.isBlank() && curr.charAt(0) == '{') {
				if (curr.equals("{")) {
					String key = reader.readLine();
					ArrayList<String> prodRules = new ArrayList<>();

//					curr = reader.readLine();
//					while (curr.charAt(0) != '}') {
//						prodRules.add(curr);
//						curr = reader.readLine();
//					}

					while (!(curr = reader.readLine()).equals("}"))
						prodRules.add(curr);

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

		// increment over the whole string
		for (int start = 0; start < nonTerminal.length(); start++) {
			// gets the index of the beginning of the first nonterminal
			if (nonTerminal.charAt(start) == '<') {
				int end = start + 1;

				// gets the end index for the current nonterminal
				while (nonTerminal.charAt(end) != '>')
					end++;

				// gets the possible productions to replace the nonterminal with
				ArrayList<String> possibleResults = nonTerminals.get(nonTerminal.substring(start, end + 1));

				// replaces the nonterminal inside our string with a possible production
				nonTerminal.replace(start, end + 1, possibleResults.get(rng.nextInt(possibleResults.size())));

				// decrements i so if another nonterminal was placed at the beginning of the one
				// we just replaced,
				// we will check it and replace it
				start--;
			}
		}

		return nonTerminal;
	}
}