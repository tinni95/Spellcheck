package spellcheck;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/** Main class for the Spell-Checker program */
public class SpellCheck {
	static StringHashCode hCode = new StringHashCode();
	static IMap M;
	static IMap S;

	public SpellCheck(IMap Dictionary, IMap Corrections) {
		SpellCheck.M = Dictionary;
		SpellCheck.S = Corrections;
	}

	public static void main(String[] args) throws java.io.IOException, MapException {
		long StartTime = System.currentTimeMillis();

		if (args.length != 2) {
			System.out.println("Usage: SpellCheck dictionaryFile.txt inputFile.txt ");
			System.exit(0);
		}
		HashTableMap M = new HashTableMap(hCode, 0.80f);
		HashTableMap S = new HashTableMap(hCode, 0.80f);
		// LinkedListMap M=new LinkedListMap();
		// LinkedListMap S=new LinkedListMap();
		SpellCheck P = new SpellCheck(M, S);
		try {
			BufferedInputStream dict;
			BufferedInputStream file;
			dict = new BufferedInputStream(new FileInputStream(args[0]));
			FileWordRead readWords = new FileWordRead(dict);

			P.popMap(readWords);

			dict.close();

			file = new BufferedInputStream(new FileInputStream(args[1]));
			FileWordRead readWords2 = new FileWordRead(file);

			P.checkHashMap(readWords2);
			file.close();

		} catch (IOException e) { // catch exceptions caused by file
									// input/output errors
			System.out.println("Check your file name");
			System.exit(0);
		}

		
		P.printS();
	}
	//uploading the words from the dictionary to the IMap
		protected void popMap(FileWordRead dict) throws MapException, IOException {

			while (dict.hasNextWord()) {
				String word = dict.nextWord();

				M.insert(word);
			}

		}
//checking for the second file and applying corrections to mispelled words
	protected void checkHashMap(FileWordRead f) throws MapException, IOException {

		while (f.hasNextWord()) {
			String word = f.nextWord();
			if (!M.find(word)) {
				trySubstitution(word);
				tryOmission(word);
				tryInsertion(word);
				trySwap(word);
			}

		}

	}
//prints Corrections IMap Values
	protected void printS() {
		Iterator<String> it = S.elements();
		while (it.hasNext()) {
			String s = (String) it.next();
				System.out.println(s);
	}
	}
	// let's try to swap every 2 letter with each other
	private void trySwap(String word) throws MapException {
		for (int x = 0; x < word.length() - 1; x++) {
			if (M.find(swap(word, x, x + 1))) {
				String foundWord = word + "=>" + (swap(word, x, x + 1));
				S.insert(foundWord);
				

			}
		}

	}

	// now we try to add a letter in all the places possible in a word
	private void tryInsertion(String word) throws MapException {
		char[] alphabet = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
				'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		for (int x = 0; x <= word.length(); x++) {

			for (char p : alphabet) {
				StringBuilder Word = new StringBuilder(word);
				Word.insert(x, p);
				if (M.find(Word.toString())) {
					String foundWord = (word + "=>" + Word);
					S.insert(foundWord);
					
				}
			}

		}

	}

	// here we try, by turn, to omit all the letters in the word

	private void tryOmission(String word) throws MapException {
		for (int x = 0; x < word.length(); x++) {
			StringBuilder Word = new StringBuilder(word);
			Word.deleteCharAt(x);

			if (M.find(Word.toString())) {
				String foundWord = (word + "=>" + Word);
				S.insert(foundWord);

				
			}
		}

	}
	// let's try to change, one by one each character of the world with all the
	// letters from the alphabet, and try to find the corrected word in the
	// dictionary

	private void trySubstitution(String word) throws MapException {
		char[] alphabet = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
				'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		for (int x = 0; x < word.length(); x++) {
			for (char p : alphabet) {
				StringBuilder Word = new StringBuilder(word);
				Word.setCharAt(x, p);
				if (M.find(Word.toString())) {
					String foundWord = (word + "=>" + Word);
					S.insert(foundWord);

				
				}
			}
		}

	}

	static private String swap(String s, int i0, int i1) {
		String s1 = s.substring(0, i0);
		String s2 = s.substring(i0 + 1, i1);
		String s3 = s.substring(i1 + 1);
		return s1 + s.charAt(i1) + s2 + s.charAt(i0) + s3;
	}
}
