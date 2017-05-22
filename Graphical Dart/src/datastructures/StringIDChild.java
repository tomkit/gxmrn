package datastructures;

import java.util.*;

/**
 * @author tomkit07
 * String ID Child
 */
public class StringIDChild extends Child {
	private static final long serialVersionUID = 1L;
	private String hint = null;
	
	/**
	 * Constructor
	 */
	StringIDChild() {
		
	}
	
	/**
	 * Overloaded constructor
	 * @param slot 
	 * @param type 
	 * @param kind 
	 * @param flags 
	 * @param data 
	 */
	public StringIDChild(int slot, String type, String kind, String flags, String data) {
		super(slot, type, kind, flags, data);
		Scanner sc = new Scanner(data);
		hint = sc.next();
		
	}
	
	/*
	 * =========
	 * = Print =
	 * =========
	 */
	
	/**
	 * Returns to the console
	 */
	public void printAll() {
		super.printAll();
		System.out.println("Hint: " + hint);
	}
	
	/**
	 * Returns as a string
	 */
	public String returnAll() {
		String returnString = super.returnAll();
		returnString += "\nHint: " + hint;
		
		return returnString;
	}

}
