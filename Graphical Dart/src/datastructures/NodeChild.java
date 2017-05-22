/**
 * 
 */
package datastructures;
import java.util.*;

/**
 * @author tomkit07
 * Node Child
 */
public class NodeChild extends Child {
	private static final long serialVersionUID = 1L;
	private int parentSlot = -1;
	private String NID = null;
	private String NIDHex = null;
	private String hint = null;
	private boolean isExternal = false;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	NodeChild() {
		
	}
	
	/**
	 * Overloaded Constructor
	 * @param slot 
	 * @param type 
	 * @param kind 
	 * @param flags 
	 * @param data 
	 */
	public NodeChild(int slot, String type, String kind, String flags, String data) {
		super(slot, type, kind, flags, data);
		Scanner sc = new Scanner(data);
		String possibleSlot = sc.next();

		// !! this is a hack to parse correctly because in this
		// specific situation we do not what value to expect. 
		// since we are not writing header information to tell 
		// what the parser to expect, this is the only work-around.
		// the next word we parse is either its parent slot, or
		// if this doesn't exist, it's its NID in pretty print
		try {
			// if this works and doesn't throw an exception then
			// this word contains parent slot information
			parentSlot = Integer.valueOf(possibleSlot);
		}
		// if we go in here then this word is its NID in pretty print
		// and therefore an external node
		catch(NumberFormatException e) {
			parentSlot = -1;
			isExternal = true;
			NID = possibleSlot;
			NIDHex = sc.next();
			hint = sc.next();
			
			// avoid continuing below
			return; 
		}
		
		// this is not an external node
		NID = sc.next();
		NIDHex = sc.next();
		hint = sc.next();
		
	}
	
	/*
	 * =========
	 * = Print =
	 * =========
	 */
	
	/**
	 * Outputs to console
	 */
	public void printAll() {
		super.printAll();
		System.out.println("Slot: " + parentSlot);
		System.out.println("NID: " + NID);
		System.out.println("NIDHex: " + NIDHex);
		System.out.println("Hint: " + hint);
	}
	
	/**
	 * Returns as a string
	 */
	public String returnAll() {
		String superReturnString = super.returnAll();
		String returnString = "";
		
		// mark that this child node is an external node
		if(isExternal()) {
			returnString += "<EXTERNAL NODE>\n";
		}
		
		returnString += superReturnString;
		returnString += "\nSlot: " + parentSlot +
			"\nNID: " + NID +
			"\nNIDHex: " + NIDHex + 
			"\nHint: " + hint;
		
		return returnString;
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Whether this child is an external node
	 * @return boolean
	 */
	public boolean isExternal() {
		return isExternal;
	}
	
	/**
	 * Returns slot of its parent
	 */
	public int getSlot() {
		return parentSlot;
	}
	
	/**
	 * Returns Node ID
	 * @return String
	 */
	public String getNID() {
		return NID;
	}
	
	/**
	 * Returns NID in hex
	 * @return String
	 */
	public String getNIDHex() {
		return NIDHex;
	}
	
	/**
	 * Returns hint
	 * @return String
	 */
	public String getHint() {
		return hint;
	}
}
