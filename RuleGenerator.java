package comprehensive;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Class for generating a random grammar to test RandomPhraseGenerator
 * 
 * @author Paul Nuffer, and Nils Streedain
 * @version April 27, 2021
 */
public class RuleGenerator {

	/**
	 * Runs the generateRandomGrammar with provided parameters
	 * 
	 * @param args - unused
	 */
	public static void main(String[] args) {
		generateRandomGrammar(10, 10, 5, .75);
	}

	/**
	 * Generates a random grammar used for testing
	 * 
	 * @param numNonTerminals    - Overall number of non-terminals to add
	 * @param numProdRules       - Number of productions rules in each non-terminal
	 * @param nonTerminalPerProd - Number of non-terminals in a production rules
	 * @param percentBaseRules   - Percent of production rules with no non-terminals
	 */
	public static void generateRandomGrammar(int numNonTerminals, int numProdRules, int nonTerminalPerProd,
			double percentBaseRules) {
		// Creates PrintWriter for writing to a file
		PrintWriter out = null;
		// Standard Try-Catch for PrintWriter
		try {
			out = new PrintWriter("src/comprehensive/random_grammar.g");
		} catch (IOException e) {
			System.out.println(e);
		}

		Random rng = new Random(1);

		// Code block for creating the <start> non-terminals definition
		out.println("{\n<start>");
		// Creates base case production rules for <start>
		for (int j = 0; j < numProdRules * percentBaseRules; j++)
			out.println("start " + j);
		// Creates production rules containing non-terminals for <start>
		for (int j = 0; j < numProdRules - (numProdRules * percentBaseRules); j++) {
			// Loops for each production rule to add to the <start> definition
			for (int k = 0; k < nonTerminalPerProd; k++)
				out.print("<" + rng.nextInt(numNonTerminals) + ">" + " a string ");
			out.println();
		}
		// Closes <start> definition to allow for others to be added
		out.println("}\n");

		// Code block for creating a number of non-terminal definitions
		for (int i = 0; i < numNonTerminals; i++) {
			out.println("{\n<" + i + ">");
			// Creates base case production rules
			for (int j = 0; j < numProdRules * percentBaseRules; j++)
				out.println(i + " " + j);
			// Creates production rules containing non-terminals
			for (int j = 0; j < numProdRules - (numProdRules * percentBaseRules); j++) {
				// Loops for each production rule to add to the non-terminal definition
				for (int k = 0; k < nonTerminalPerProd; k++)
					out.print("<" + (rng.nextInt(numNonTerminals - i) + i) + ">" + " a string ");
				out.println();
			}
			// Closes the definition to allow for others to be added
			out.println("}\n");
		}

		// Closes the file
		out.close();
	}
}