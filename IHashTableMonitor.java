package spellcheck;



/** Interface your hash table has to implement */
public interface IHashTableMonitor {

	/** Returns the maximum authorised load factor */
	public float getMaxLoadFactor();

	/** Returns the current load factor */
	public float getLoadFactor();

	/** Returns the average number of probes */
	public float averNumProbes();

}

