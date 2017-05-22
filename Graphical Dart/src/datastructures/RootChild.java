/**
 * 
 */
package datastructures;

/**
 * @author tomkit07
 * Root Child
 */
public class RootChild extends Child {
	private static final long serialVersionUID = 1L;
	private int childSlot;
	private String flags = null;
	private int parentSlot;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	RootChild() {
		
	}
	
	/**
	 * Overloaded constructor
	 * @param childSlot 
	 * @param type 
	 * @param flags 
	 * @param parentSlot 
	 */
	public RootChild(int childSlot, String type, String flags, int parentSlot) {
		super(childSlot, type, "", flags, "");
		this.childSlot = childSlot;
		this.flags = flags;
		this.parentSlot = parentSlot;
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
		System.out.println("Child Slot: " + childSlot);
		System.out.println("Flags: " + flags);
		System.out.println("Parent Slot: " + parentSlot);
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Returns slot
	 */
	public int getSlot() {
		return parentSlot;
	}
}
