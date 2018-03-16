package spellcheck;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Compare {
	static StringHashCode hCode = new StringHashCode();
	static IMap M;
	static IMap S;

	public static void main(String[] args) throws java.io.IOException, MapException {

		if (args.length != 2) {
			System.out.println("Usage: SpellCheck dictionaryFile.txt inputFile.txt ");
			System.exit(0);
		}
		HashTableMap M = new HashTableMap(hCode, 0.80f);
		HashTableMap S = new HashTableMap(hCode, 0.80f);
		LinkedListMap LM = new LinkedListMap();
		LinkedListMap LS = new LinkedListMap();
		SpellCheck HashMap = new SpellCheck(M, S);

		try {
			double HashStartTime = System.currentTimeMillis();
			double LinkStartTime = System.currentTimeMillis();

			BufferedInputStream dict;
			BufferedInputStream file;
			dict = new BufferedInputStream(new FileInputStream(args[0]));
			FileWordRead readWords = new FileWordRead(dict);
			BufferedInputStream dict2 = new BufferedInputStream(new FileInputStream(args[0]));
			FileWordRead readWords3 = new FileWordRead(dict2);
			file = new BufferedInputStream(new FileInputStream(args[1]));
			FileWordRead readWords2 = new FileWordRead(file);
			BufferedInputStream file2 = new BufferedInputStream(new FileInputStream(args[1]));
			FileWordRead readWords4 = new FileWordRead(file2);
			
			HashMap.popMap(readWords);
			dict.close();

			HashMap.checkHashMap(readWords2);
			file.close();
			double HashfinTime = System.currentTimeMillis();

	
			SpellCheck LinkedList = new SpellCheck(LM, LS);
			LinkedList.popMap(readWords3);

			LinkedList.checkHashMap(readWords4);

			double LinkfinTime = System.currentTimeMillis();

			System.out.println("HashMap Took: " + (HashfinTime - HashStartTime)/1000);
			System.out.println("linkedList Took: " + (LinkfinTime - LinkStartTime)/1000 );
			System.out.println(
					"HashMap Took " + (((LinkfinTime - LinkStartTime)-(HashfinTime - HashStartTime))/1000 ) + " seconds less");

		} catch (IOException e) { // catch exceptions caused by file
									// input/output errors
			System.out.println("Check your file name");
			System.exit(0);
		}

		// HashMap.printS();
		// LinkedList.printS();
	}
}
