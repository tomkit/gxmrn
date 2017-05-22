/**
 * 
 */
package datastructures;

/**
 * @author tomkit07
 * This class encompasses DocumentNode, AttributeNode, CommentNode, ElementNode, ProcessingInstructionNode, and TextNode
 * which all have these fields in common
 */
public abstract class DocumentTypeNode extends Node{
	private String numDescendants = null;
	private String internal = null;
	private String attributes = null;
	private String ordered = null;
	private String parentSlotIndex = null;
	private String simpleType = null;
	private String annotatedTypeID = null;
	private String indexTypeValue = null;
	private String localName = null;
	private String namespaceURI = null;
	private String namespacePrefix = null;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	DocumentTypeNode() {
		super();
	}
	
	/**
	 * Overloaded constructor
	 * @param slot 
	 * @param type 
	 * @param kind 
	 * @param kindID 
	 * @param flags 
	 * @param numDescendants 
	 * @param internal 
	 * @param attributes 
	 * @param ordered 
	 * @param parentSlotIndex 
	 * @param simpleType 
	 * @param annotatedTypeID 
	 * @param indexTypeValue 
	 * @param localName 
	 * @param namespaceURI 
	 * @param namespacePrefix 
	 */
	public DocumentTypeNode(int slot, String type, String kind, String kindID, String flags, String numDescendants, String internal, String attributes, 
			String ordered,	String parentSlotIndex, String simpleType, String annotatedTypeID, String indexTypeValue, 
			String localName, String namespaceURI, String namespacePrefix) {
		super(slot, type, kind, kindID, flags);
		this.numDescendants = numDescendants;
		this.internal = internal;
		this.attributes = attributes;
		this.ordered = ordered;
		this.parentSlotIndex = parentSlotIndex;
		this.simpleType = simpleType;
		this.annotatedTypeID = annotatedTypeID;
		this.indexTypeValue = indexTypeValue;
		this.localName = localName;
		this.namespaceURI = namespaceURI;
		this.namespacePrefix = namespacePrefix;
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
		System.out.println("Document Node");
		super.printAll();
		System.out.println("Number Descendants: " + numDescendants);
		System.out.println("Internal: " + internal);
		System.out.println("Attributes: " + attributes);
		System.out.println("Ordered: " + ordered);
		System.out.println("Parent Slot Index: " + parentSlotIndex);
		System.out.println("Simple Type: 0x" + simpleType);
		System.out.println("Annotated Type ID: 0x" + annotatedTypeID);
		System.out.println("Index Type Value: 0x" + indexTypeValue);
		System.out.println("Local Name: " + localName);
		System.out.println("Name Space URI: " + namespaceURI);
		System.out.println("Name Space Prefix: " + namespacePrefix);
		((Node)this).printChildren();
	}
	
	/**
	 * Returns as a string
	 */
	public String returnAll() {
		String returnString = super.returnAll();
		returnString += "\nNumber Descendants: " + numDescendants +
			"\n   Internal: " + internal +
			"\n   Attributes: " + attributes +
			"\n   Ordered: " + ordered +
			"\nParent Slot Index: " + parentSlotIndex +
			"\nSimple Type: 0x" + simpleType +
			"\nAnnotated Type ID: 0x" + annotatedTypeID +
			"\nIndex Type Value: 0x" + indexTypeValue +
			"\nLocal Name: " + localName +
			"\nName Space URI: " + namespaceURI +
			"\nName Space Prefix: " + namespacePrefix;
		returnString += "\n" + ((Node)this).returnChildren();
		
		return returnString;
	}
}
