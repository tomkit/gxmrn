/**
 * 
 */
package datastructures;

import java.util.*;

/**
 * @author tomkit07
 * Inline Child
 */
public class InlineChild extends Child {
	private static final long serialVersionUID = 1L;
	private String coord = null;
	private String NID = null;
	private String NIDHex = null;
	private String hint = null;
	private String content = null;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	InlineChild() {
		
	}
	
	/**
	 * Overloaded constructor
	 * @param slot 
	 * @param type 
	 * @param kind 
	 * @param flags 
	 * @param data 
	 */
	public InlineChild(int slot, String type, String kind, String flags, String data) {
		super(slot, type, kind, flags, data);
		Scanner sc = new Scanner(data);
		coord = sc.next();		// (x, y) value // !! probably not called coordinate, but it looks like one
		NID = sc.next();		// pretty print
		NIDHex = sc.next(); 	// hex
		hint = sc.next();
		
		sc.useDelimiter("\n");
		content = sc.next(); 	// content of the inline child
		
	}
	
	/*
	 * =========
	 * = Print =
	 * =========
	 */
	
	/**
	 * Returns to console
	 */
	public void printAll() {
		super.printAll();
		System.out.println("Coord: " + coord);
		System.out.println("NID: " + NID);
		System.out.println("NID Hex: " + NIDHex);
		System.out.println("Hint: " + hint);
		System.out.println("Content: " + content);
	}
	
	/**
	 * Returns as a string
	 */
	public String returnAll() {
		String returnString = super.returnAll();
		returnString += "\nCoord: " + coord +
			"\nNID: " + NID +
			"\nNID Hex: " + NIDHex + 
			"\nHint: " + hint +
			"\nContent: " + content;
		
		return returnString;
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * @return String
	 */
	public String getCoord() {
		return coord;
	}
	
	/**
	 * @return String
	 */
	public String getNID() {
		return NID;
	}
	
	/**
	 * @return String
	 */
	public String getHint() {
		return hint;
	}
	
	/**
	 * @return String
	 */
	public String getContent() {
		return content;
	}
}
