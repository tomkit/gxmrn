package datastructures;

/**
 * @author tomkit07
 * Processing Instruction Node
 */
public class ProcessingInstructionNode extends DocumentTypeNode {
	private static final long serialVersionUID = 1L;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */

	/**
	 * Default constructor
	 */
	ProcessingInstructionNode() {
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
	public ProcessingInstructionNode(int slot, String type, String kind, String kindID, String flags, String numDescendants, String internal, String attributes, 
			String ordered,	String parentSlotIndex, String simpleType, String annotatedTypeID, String indexTypeValue, 
			String localName, String namespaceURI, String namespacePrefix) {
		super(slot, type, kind, kindID, flags, numDescendants, internal, attributes, 
				ordered, parentSlotIndex, simpleType, annotatedTypeID, indexTypeValue,
				localName, namespaceURI, namespacePrefix);
	}
}
