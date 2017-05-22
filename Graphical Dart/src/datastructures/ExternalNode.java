package datastructures;

/**
 * 
 * @author tomkit07
 * This class is used to represent an external node
 */
public class ExternalNode {
	private int page;
	private int slot;
	private int childSlot;
	private String NID;
	private String type;
	private String kind;
	private String flags;
	private String hint;
	
	/**
	 * Default Constructor
	 *
	 */
	public ExternalNode() {
		
	}
	
	/**
	 * 
	 * @param page
	 * @param slot
	 */
	public ExternalNode(int page, int slot) {
		this.page = page;
		this.slot = slot;
	}

	/**
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getChildSlot() {
		return childSlot;
	}
	
	/**
	 * 
	 * @param childSlot
	 */
	public void setChildSlot(int childSlot) {
		this.childSlot = childSlot;
	}
	
	/**
	 * 
	 * @param slot
	 */
	public void setNode(int slot) {
		this.slot = slot;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getNode() {
		return slot;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getNID() {
		return NID;
	}
	
	/**
	 * 
	 * @param NID
	 */
	public void setNID(String NID) { 
		this.NID = NID;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getKind() {
		return kind;
	}
	
	/**
	 * 
	 * @param kind
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getFlags() {
		return flags;
	}
	
	/**
	 * 
	 * @param flags
	 */
	public void setFlags(String flags) {
		this.flags = flags;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getHint() {
		return hint;
	}
	
	/**
	 * 
	 * @param hint
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}
}
