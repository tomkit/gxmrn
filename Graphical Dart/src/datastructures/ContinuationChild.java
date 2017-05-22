/**
 * 
 */
package datastructures;

/**
 * @author tomkit07
 * Continuation Child (no information is dumped by dart so it will all be empty)
 */
public class ContinuationChild extends Child {
	private static final long serialVersionUID = 1L;
	private String NID = null;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	ContinuationChild() {
		
	}
	
	/**
	 * Overloaded constructor
	 * @param slot 
	 * @param type 
	 * @param kind 
	 * @param flags 
	 * @param data 
	 */
	public ContinuationChild(int slot, String type, String kind, String flags, String data) {
		super(slot, type, kind, flags, data);
		// uncomment to parser the incoming data
		// Scanner sc = new Scanner(data);
		// NID = sc.next();
		
		// !! no information is provided for continuation children in dart
		
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
		System.out.println("NID: " + NID);
	}
	
	/**
	 * Returns as a string
	 */
	public String returnAll() {
		String returnString = super.returnAll();
		returnString += "\nNID: " + NID;
		
		return returnString;
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Returns Node ID
	 * @return String
	 */
	public String getNID() {
		return NID;
	}
}
