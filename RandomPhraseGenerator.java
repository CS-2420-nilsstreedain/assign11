package comprehensive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains a method for generating random phrases based of a
 * formatted input grammar file.
 * 
 * @author Paul Nuffer, and Nils Streedain
 * @version April 27, 2021
 */
public class RandomPhraseGenerator {
	HashMap<String, ArrayList<String>> nonTerminals;
	Random rng;

	BufferedReader reader;

	/**
	 * Main method for creating a phrase generator and generating a number of
	 * phrases based of input arguments. This is for use when calling from terminal
	 * or using run configurations in Eclipse.
	 * 
	 * @param args - First argument is the input grammar file, second is the number
	 *             of phrases to generate
	 */
	public static void main(String[] args) {
		// Creates a new phrase generator, parsed from the input file
		RandomPhraseGenerator phraseGenerator = new RandomPhraseGenerator(args[0]);

		// A StringBuilder is used to allow for efficiently using only one print
		StringBuilder output = new StringBuilder();
		// Loops once for each phrase that needs to be generated and adds it to a new
		// line in the StringBuilder
		for (int i = 0; i < Integer.parseInt(args[1]); i++)
			output.append(phraseGenerator.generatePhrase() + "\n");
		// Single print statement to print the output
		System.out.println(output);
	}

	/**
	 * Constructor used for creating a new phrase generator object and then parsing
	 * an input file into that object.
	 * 
	 * @param filename - Input file to parse
	 */
	public RandomPhraseGenerator(String filename) {
		// Creates a HashMap instance to parse the data into
		nonTerminals = new HashMap<>();
		rng = new Random();

		// Standard try-catch for a FileReader nested in a BufferedReader
		try {
			// FileReader nested in a BufferedReader is used because it is more efficient
			// and is specifically meant for parsing only strings which fits this use case
			reader = new BufferedReader(new FileReader(filename));

			// curr is used to keep track of the current line being read in the file
			String curr;

			// Loops over each line in the input file
			while ((curr = reader.readLine()) != null) {

				// Skips over anything outside a curly bracket
				if (curr.equals("{")) {
					// Saves the first line after "{" to be used as they key (non-terminal name)
					String key = reader.readLine();
					// Creates an ArrayList of Strings (Production Rules) to be used as the value
					ArrayList<String> prodRules = new ArrayList<>();

					// Adds each line after the name to the production rules until "}" is reached
					while (!(curr = reader.readLine()).equals("}"))
						prodRules.add(curr);

					// Adds the given non-terminal definition to the HashMap
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

	/**
	 * Public method to generate a single phrase given a phraseGenerator object of a
	 * parsed input file.
	 * 
	 * @return - Generated Phrase
	 */
	public StringBuilder generatePhrase() {
		StringBuilder nonTerminal = new StringBuilder("<start>");

		// Increment over the whole string
		for (int start = 0; start < nonTerminal.length(); start++) {
			// Gets the index of the beginning of the first nonterminal
			if (nonTerminal.charAt(start) == '<') {
				int end = start + 1;

				// Gets the end index for the current nonterminal
				while (nonTerminal.charAt(end) != '>')
					end++;

				// Gets the list of possible productions to replace the nonterminal with
				ArrayList<String> possibleResults = nonTerminals.get(nonTerminal.substring(start, end + 1));

				// Replaces nonterminal substring with a random production rule from the list
				nonTerminal.replace(start, end + 1, possibleResults.get(rng.nextInt(possibleResults.size())));

				// Decrements i for when a replaced production rule starts with a non-terminal
				start--;
			}
		}

		return nonTerminal;
	}
}