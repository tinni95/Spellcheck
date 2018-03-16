package spellcheck;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListMap extends LinkedList<Object> implements IMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LinkedListMap(){
		super();
	}

	public void insert(String key){
		if(!find(key))
	this.addFirst(key);
	}
	public boolean find(String key){
	if (this.contains(key))
		return true;
	else 
		return false;
	}
	public void remove(String key) throws MapException{
		this.remove(key);
	}
	public int numberOfElements(){
		return this.size();
	}
	public Iterator elements(){
		return this.iterator();
	}
	
}
