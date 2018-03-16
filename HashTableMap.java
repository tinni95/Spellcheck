package spellcheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HashTableMap implements IHashTableMonitor, IMap {
	String[] words = new String[7];
	IHashCode hcode;
	float maxLoadFactor;

	private int totalNumProbes = 0;
	private int SuccessfullProbes = 0;
	private int NumberOfElements = 0;

	/**
	 * constructor with no parameters Not allowed because user needs to input
	 * inputCode and maxLoadFactor
	 * 
	 * @throws MapException
	 */
	public HashTableMap() throws MapException {
		throw new MapException();
	}

	/**
	 * constructor with parameters This is the right constructor that should be
	 * called, we assign to the fields hCode and maxLoadFactor the parameter
	 * inputed
	 */
	public HashTableMap(IHashCode inputCode, float maxLoadFactor) {
		this.hcode = inputCode;
		this.maxLoadFactor = maxLoadFactor;
	}

	/**
	 * The insert method is used to insert a new key in the hashMap we first
	 * check the number of elements is still under the Maximum allowed by the
	 * user if it is over the limit,we call the rehash method because we need a
	 * bigger list if it is not, we call the method to find the index where to
	 * put the entry
	 * 
	 * @param key
	 */
	@Override
	public void insert(String key) throws MapException {
		if (!find(key)) {
			if (NumberOfElements >= this.getLoadFactor()) {
				this.rehash();
			}
			words[findIndex(key, words, null)] = key;
			NumberOfElements++;
		}

	}

	/**
	 * findIndex is used to find the index to insert,find,remove the keys the
	 * parameters passed are the key and the array used, and the action to do if
	 * null is passed as action, the first null place is passed if a string is
	 * passed as action, the index of that string in the map is returned this
	 * method is both used by the rehash() and insert() methods and that's why
	 * we need to use the parameter for the array
	 * 
	 * @param key
	 * @param words
	 * @return
	 */
	private int findIndex(String key, String[] words, String action) {
		int Shc = hcode.giveCode(key);
		int size = words.length;
		int index = Shc % size;
		int index2 = 3 - Shc % 3;
		for (int i = 0; i < size; i++) {
			totalNumProbes++;
			int ind = (index + index2 * i) % size;
			if (action != null) {
				if (words[ind] == null) {
					return -1;
				}

				else if (words[ind].toString().equals(action)) {
					SuccessfullProbes++;
					return ind;
				}
			} else {
				if (words[ind] == action) {
					SuccessfullProbes++;

					return ind;
				}

			}

		}
		return -1;
	}

	/**
	 * The method rehash is called when the numberOfElements overtakes the
	 * loadFactor we need to create another array,which size amounts to the
	 * first prime after double size of the current array then we insert all the
	 * values in the new array following the same double hashing procedure we
	 * then assign back the new array to be our field.
	 */
	private void rehash() {
		String[] words2 = new String[getNextPrime(words.length * 2)];
		for (int i = 0; i < words.length; i++) {
			if (words[i] != null && !words[i].equals("DEFUNCT")) {

				words2[findIndex(words[i], words2, null)] = words[i];

			}
		}
		words = Arrays.copyOf(words2, words2.length);
	}

	// return the next prime

	private int getNextPrime(int i) {
		if (isPrime(i)) {
			return i;
		} else {
			return getNextPrime(++i);
		}
	}

	// check if paramenter is prime
	private boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	/**
	 * check if the value to remove is in the array, using double hashing
	 * probing as usual if we find the value, we set it to null, otherwise we
	 * throm MapException
	 */
	@Override
	public void remove(String key) throws MapException {
		int is = findIndex(key, words, key);

		if (is == -1) {
			throw new MapException();
		} else if (words[is] != null && !words[is].equals("DEFUNCT")) {
			words[is] = "DEFUNCT";
			SuccessfullProbes++;
			NumberOfElements--;
			return;
		}

	}

	/**
	 * check if the value we are looking for is in the array, using double
	 * hashing probing as usual if we find the value, we return true, otherwise
	 * we return false
	 */
	@Override
	public boolean find(String key) {
		int is = findIndex(key, words, key);

		if (is != -1) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * returns an iterator over all of the elements, creating an arraylist and
	 * removing dummy value and null values
	 */
	public Iterator<String> elements() {
		List<String> iterable = new ArrayList<String>(Arrays.asList(words));
		iterable.removeAll(Collections.singleton(null));
		iterable.removeAll(Collections.singleton("DEFUNCT"));
		Iterator<String> it = iterable.iterator();

		return it;
	}

	@Override
	// return maxLoadFactor
	public float getMaxLoadFactor() {

		return maxLoadFactor;

	}

	// computes the loadFactor
	@Override
	public float getLoadFactor() {

		return words.length * this.getMaxLoadFactor();

	}

	@Override
	// calculates number of probes on average
	public float averNumProbes() {

		return totalNumProbes / SuccessfullProbes;
	}

	@Override
	// return field numberofelements
	public int numberOfElements() {
		return NumberOfElements;
	}
}
