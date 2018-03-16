package spellcheck;

import java.util.HashMap;
import java.util.Hashtable;

public class StringHashCode implements IHashCode {

	@Override
	//our method to retrieve an hashcode from a string
	public int giveCode(Object o) {
		String toHash=o.toString();
		int hash=0;
		
		for (int i = 0; i < toHash.length(); i++) {
			int ascii=(int)toHash.charAt(i);
	
		    hash+= ascii*Math.pow(7, i);

		}
		
		return hash;
	}

}
