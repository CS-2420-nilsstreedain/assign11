package comprehensive;

public class RandomPhraseGenerator {
	public static void main(String[] args) {
//		try {
			String filename = args[0];
			int sentences = Integer.parseInt(args[1]);
//		} catch (ArrayIndexOutOfBoundsException e) {
//			System.out.println(e.getMessage());
//			System.exit(0);	
//		}
		
		PhraseGenerator phraseGenerator = new PhraseGenerator(filename);
		
		for (int i = 0; i < sentences; i++)
			System.out.println(phraseGenerator.finalPhrase("<start>"));
		
	}
}