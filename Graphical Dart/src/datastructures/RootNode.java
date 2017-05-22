/**
 * 
 */
package datastructures;

/**
 * @author tomkit07
 * Root Node
 */
public class RootNode extends Node {
	private static final long serialVersionUID = 1L;
	private String numChildren = null;
	private String pathID = null;
	private String documentID = null;
	private String versionID = null;
	private String nodeIDLength = null;
	private String nodeID = null;
	private String NIDHex = null;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/** 
	 * Default constructor
	 */
	RootNode() {
		super();
	}
	
	/**
	 * Overloaded constructor
	 * @param slot 
	 * @param type 
	 * @param kind 
	 * @param kindID 
	 * @param flags 
	 * @param numChildren 
	 * @param pathID 
	 * @param documentID 
	 * @param versionID 
	 * @param nodeIDLength 
	 * @param nodeID 
	 * @param NIDHex 
	 */
	public RootNode(int slot, String type, String kind, String kindID, String flags, String numChildren, String pathID, String documentID, 
			String versionID, String nodeIDLength, String nodeID, String NIDHex) {
		super(slot, type, kind, kindID, flags);
		this.numChildren = numChildren;
		this.pathID = pathID;
		this.documentID = documentID;
		this.versionID = versionID;
		this.nodeIDLength = nodeIDLength;
		this.nodeID = nodeID;
		this.NIDHex = NIDHex;
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Returns document id
	 * @return String
	 */
	public String getDocID() {
		return documentID;
	}
	
	/**
	 * Returns version id
	 * @return String
	 */
	public String getVersionID() {
		return versionID;
	}
	
	/**
	 * Returns nid
	 * @return String
	 */
	public String getNID() {
		return nodeID;
	}
	
	/**
	 * Returns nid in hex
	 * @return String
	 */
	public String getNIDHex() {
		return NIDHex;
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
		System.out.println("Root Node");
		super.printAll();
		System.out.println("Number of Children: " + numChildren);
		System.out.println("Path ID: " + pathID);
		System.out.println("Document ID: " + documentID);
		System.out.println("Version ID: " + versionID);
		System.out.println("Node ID Length: " + nodeIDLength);
		System.out.println("Node ID: " + nodeID);
		System.out.println("NID Hex: " + NIDHex);
	}
	
	/**
	 * Returns as a string
	 */
	public String returnAll() {
		String returnString = super.returnAll();
		returnString += "\nNumber of Children: " + numChildren +
			"\nPath ID: " + pathID +
			"\nDocument ID: 0x" + documentID +
			"\nVersion ID: 0x" + versionID +
			"\nNode ID Length: " + nodeIDLength +
			"\nNode ID: " + nodeID +
			"\nNID Hex: " + NIDHex;
			
		return returnString;
	}
}
