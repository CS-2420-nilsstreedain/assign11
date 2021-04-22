package comprehensive;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class RuleGenerator {
	
	public static void main(String[] args) {
		generateRandomGrammar(10, 10, 2, .75);
	}
	
	public static void generateRandomGrammar(int numNonTerminals, int numProdRules, int nonTerminalPerProd, double percentOfBaseRules) {
	    PrintWriter out = null;
	    try {
	      out = new PrintWriter("src/comprehensive/random_grammar.g");
	    } 
	    catch (IOException e) {
	      System.out.println(e);
	    }

	    Random rng = new Random();
	    
	    out.println("{\n<start>");
    	for (int j = 0; j < numProdRules * percentOfBaseRules; j++)
			out.println("start " + j);
    	for (int j = 0; j < numProdRules - (numProdRules * percentOfBaseRules); j++) {
    		for (int k = 0; k < nonTerminalPerProd; k++) 
				out.print("<" + rng.nextInt(numNonTerminals) + ">" + " a string ");
    		out.println();
    	}
		out.println("}\n");
	    
	    for (int i = 0; i < numNonTerminals; i++) {
	    	out.println("{\n<" + i + ">");
	    	for (int j = 0; j < numProdRules * percentOfBaseRules; j++)
				out.println(i + " " + j);
	    	for (int j = 0; j < numProdRules - (numProdRules * percentOfBaseRules); j++) {
	    		for (int k = 0; k < nonTerminalPerProd; k++)
	    			out.print("<" + rng.nextInt(numNonTerminals) + ">" + " a string ");
				out.println();
    		}
    		out.println("}\n");
	    }
	    
	    out.close();
	}
}