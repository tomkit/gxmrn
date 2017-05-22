/**
 * 
 */
package datastructures;

/**
 * @author tomkit07
 * This is a Child which encompasses NodeChild, RootChild, StringIDChild, and InlineChild
 */
public class Child extends Node {
	private static final long serialVersionUID = 1L;
	
	/* 
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	Child() {
		
	}
	
	/**
	 * Overloaded Constructor
	 * @param slot 
	 * @param type 
	 * @param kind 
	 * @param flags 
	 * @param data 
	 */
	public Child(int slot, String type, String kind, String flags, String data) {
		super(slot, type, kind, "", flags);
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Returns to the console
	 */
	public void printAll() {
		super.printAll();
	}
	
	/**
	 * Returns as a string
	 */
	public String returnAll() {
		String returnString = "Child " + super.returnAll();
		
		return returnString;
	}
	
	

}
