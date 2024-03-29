package comprehensive;

/**
 * Testing class for timing RandomPhraseGenerator
 * 
 * @author Paul Nuffer & Nils Streedain
 *
 */
public class RPGTimer {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		System.out.println("N\tnanoTime");

		int incr = 500;
		for (int probSize = 500; probSize <= 10000; probSize += incr) {

			int timesToLoop = 20;

			RuleGenerator.generateRandomGrammar(1000, probSize, 2, .75);
//			RandomPhraseGenerator rpg = new RandomPhraseGenerator("src/comprehensive/random_grammar.g");

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			long stopTime, midpointTime, startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {
			}

			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				RandomPhraseGenerator rpg = new RandomPhraseGenerator("src/comprehensive/random_grammar.g");
			}

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for (int i = 0; i < timesToLoop; i++) {

			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and searching.
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;

			System.out.println(probSize + "\t" + String.format("%.5f", averageTime));
		}
	}
}
