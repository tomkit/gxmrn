/**
 * 
 */
package datastructures;


import static dart.Parameter.*;

import static java.awt.Color.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * @author tomkit07
 * Contains all the node specific information and some visualization methods
 */
public class Node extends Cell {
	private static final long serialVersionUID = 1L;
	private int slot = -1;
	private String type = null;
	private String kind = null;
	private String kindID = null;
	private String flags = null;
	private int parentSlot = -1;
	private Vector<Child> children = null;
	private boolean hasContinuation = false;
	private boolean isContinuation = false;
	private boolean hasExternalNode = false;
	private Rectangle2D rect = null;
	private Rectangle2D contOut = null;
	private Rectangle2D contIn = null;
	private Rectangle2D externalRect = null;
	private int level = -1;
	private String NID = null;
	private String NIDHex = null;
	private Node parent = null;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/** 
	 * Default Constructor
	 */
	public Node() {
	}
	
	/** 
	 * Overloaded Constructor
	 */
	Node(int slot, String type, String kind, String kindID, String flags) {
		super(slot);
		children = new Vector<Child>();
		this.slot = slot;
		this.type = type;
		this.kind = kind;
		this.kindID = kindID;
		this.flags = flags;
		this.rect = getRect();

		// if this node has a node pointing to it and points to another
		if(flags.length() == 4 && flags.charAt(2) == '6') {
			setContIn();
			setContOut();
		}
		// if this node has something pointing in
		else if(flags.length() == 4 && flags.charAt(2) == '2') {
			setContIn();
		}
		// if this node has something point out
		else if(flags.length() == 4 && flags.charAt(2) == '4') {
			setContOut();
		}
		setExternalRect();
	}
	
	/* 
	 * =========
	 * = Print =
	 * =========
	 */
	
	/** 
	 * Dumps data to the console
	 */
	public void printAll() {
		System.out.format("%10s %s", "Slot:", slot);	// testing formatted output
		System.out.println("(Level: " + level + ")");
		System.out.println("(Parent: " + parent + ")");
		System.out.println("NID: " + NID);
		System.out.println("NID Hex: " + NIDHex);
		System.out.println("Type: " + type);
		System.out.println("Kind: " + kind);
		System.out.println("Kind ID: " + kindID);
		System.out.println("Flags: " + flags);
	
		// print children that are external or inline or stringid's
		if(children.size() != 0) {
			
			if(DEBUGMODE) {
				System.out.println("- - - - - - -");
				System.out.println("number of children: " + children.size());
			}
			
			for(Node node : children) {
				if(node instanceof RootChild) ((RootChild)node).printAll();
				else if(node instanceof Child) ((Child)node).printAll();
			}
			
			if(DEBUGMODE) {
				System.out.println("- - - - - - -");
			}
			
		}
	}
	
	/**
	 * Returns a print of all information
	 */
	public String returnAll() {
		String returnString = "";
		
		returnString += String.format("%s %s", "Slot:", slot); 		// testing formatted output
		returnString += String.format("\n%s %s", "Type:", type); 	// testing formatted output
		if(NID != null) {
			returnString += 
			"\nNID: " + NID +
			"\nNID Hex: " + NIDHex;
		}
		returnString +=			
		"\nKind: " + kind + " (" + kindID + ")" +
		"\nFlags: 0x" + flags;
		
		return returnString;
	}
	
	/*
	 * =========================
	 * = Visualization Methods =
	 * =========================
	 */
	
	/** 
	 * The driver to draw this node and its components
	 */
	public void draw(Graphics g) {
		drawRect(g);					// the cell border
		drawContinuationButtons(g);		// continuation icons (in and out)
		drawExternalNode(g);			// external node icon
		drawText(g);					// cell text
		setClickable(true);				// sets this cell clickable
	}
	
	/**
	 * Highlights this node in a translucent color and its children
	 * @param g 
	 * @param color 
	 */
	public void highlight(Graphics g, Color color) {
		
		// set transparency
		Composite originalComposite = ((Graphics2D)g).getComposite();
		((Graphics2D)g).setComposite(makeComposite(ALPHA));
		
		// color the node
		g.setColor(color); 
		g.fillRect((int)rect.getX()+1, (int)rect.getY()+1, CELLWIDTH-1, CELLHEIGHT-1);
		
		// set original transparency
		((Graphics2D)g).setComposite(originalComposite);
		
		if(isClicked()) {
			g.setColor(YELLOW);
			g.drawRect((int)rect.getX(), (int)rect.getY(), CELLWIDTH, CELLHEIGHT);
		}
	}
	
//	/**
//	 * Highlights a rectangle within a node (continuation button)
//	 * !! not used anymore, this is used to highlight continuation/external nodes
//	 */
//	public void highlight(Graphics g, Rectangle2D rect) {
//		Composite originalComposite = ((Graphics2D)g).getComposite();
//		((Graphics2D)g).setComposite(makeComposite(ALPHA));
//		
//		g.setColor(GREEN); 
//		if(rect != null) {
//			g.fillRect((int)rect.getX()+1, (int)rect.getY()+1, (int)rect.getWidth()-1, (int)rect.getHeight()-1);
//			((Graphics2D)g).setComposite(originalComposite);
//			
//			if(isClicked()) {
//				g.setColor(YELLOW);
//				g.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth()-1, (int)rect.getHeight()-1);
//			}
//		}
//	}
	
	/**
	 * Sets the rectangle to represent an external node
	 */
	private void setExternalRect() {
		externalRect = new Rectangle2D.Float((int)rect.getX()+CELLBUFFER*5,
				(int)rect.getY()+CELLHEIGHT-CELLBUFFER*5, 
				CELLWIDTH-CELLBUFFER*10+1, 
				CELLBUFFER*3);
	}
	
	/**
	 * Check if this node has an external node in its children by scanning it
	 */
	private boolean containsExternal() {
		for(Child child : children) {
			if(child instanceof NodeChild) {
				if(((NodeChild)child).isExternal()) {
					hasExternalNode = true;
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Draws the continuation buttons
	 */
	private void drawContinuationButtons(Graphics g) {
		int x[] = new int[3];
		int y[] = new int[3];
		
		// pointed into this node
		if(isContinuation && contIn != null) {	
			x[0] = (int)contIn.getX() + (int)contIn.getWidth();
			y[0] = (int)contIn.getY();
			x[1] = (int)contIn.getX();
			y[1] = (int)contIn.getY() + (int)contIn.getHeight()/2;
			x[2] = (int)contIn.getX() + (int)contIn.getWidth();
			y[2] = (int)contIn.getY() + (int)contIn.getHeight();
			
			g.fillPolygon(x, y, 3);
		}
		
		// pointing out of this node
		if(hasContinuation && contOut != null) {
			x[0] = (int)contOut.getX();
			y[0] = (int)contOut.getY();
			x[1] = (int)contOut.getX() + (int)contOut.getWidth();
			y[1] = (int)contOut.getY() + (int)contOut.getHeight()/2;
			x[2] = (int)contOut.getX();
			y[2] = (int)contOut.getY() + (int)contOut.getHeight();

			g.fillPolygon(x, y, 3);
		}
	}
	
	/**
	 * Sets the rectangle that represents an external icon
	 */
	private void drawExternalNode(Graphics g) {
		if(hasExternalNode || containsExternal()) {
			g.fillRect((int)externalRect.getX(), (int)externalRect.getY(), (int)externalRect.getWidth(), (int)externalRect.getHeight());
		}
	}
	
	/**
	 * Sets the rectangle that represents an incoming continuation icon
	 */
	private void setContIn() {
		isContinuation = true;
		contIn = new Rectangle2D.Float((int)rect.getX()+CELLBUFFER,
				(int)rect.getY()+CELLBUFFER, 
				CELLBUFFER*3, 
				CELLHEIGHT-CELLBUFFER*2);
	}
	
	/**
	 * Sets the rectangle that represents an incoming continuation icon
	 */
	private void setContOut() {
		hasContinuation = true;
		contOut = new Rectangle2D.Float((int)rect.getX()+CELLWIDTH-CELLBUFFER*4+1,
				(int)rect.getY()+CELLBUFFER+3, 
				CELLBUFFER*3-1, 
				CELLHEIGHT-CELLBUFFER*2-6);
	}
	
	/**
	 * This draws the labels for cells. The most important data are give first priority.
	 * If a certain field is too long to display it will be truncated and appended with "..."
	 * Displays slot, then kind, then flags
	 */
	private void drawText(Graphics g) {
		int fontSize = g.getFont().getSize();
		int row = 0;
		int startX = (int)rect.getX()+CELLBUFFER*5;
		String slotString = "Slot: " + Integer.toString(slot);
		
		row = 0;
		g.drawString(getFittableText(slotString, fontSize), startX, (int)rect.getY()+((row+1)*fontSize)+CELLBUFFER);
		row = 1;
		g.drawString(getFittableText("Kind: " + kind, fontSize), startX, (int)rect.getY()+((row+1)*fontSize)+CELLBUFFER);
		row = 2;
		g.drawString(getFittableText("Flags: " + flags, fontSize), startX, (int)rect.getY()+((row+1)*fontSize)+CELLBUFFER);
	}
	
	/*
	 * ====================
	 * = Node Information =
	 * ====================
	 */
	
	/**
	 * Prints all children
	 */
	public void printChildren() {
		if(getChildren() != null) {
			Vector<Child> children = getChildren();

			for(Child child : children) {
				if(child instanceof InlineChild) {
					((InlineChild)child).printAll();
				}
				else if(child instanceof NodeChild && ((NodeChild)child).getSlot() == -1) {
					((NodeChild)child).printAll();
				}
				else if(child instanceof StringIDChild) {
					((StringIDChild)child).printAll();
				}
				
			}
		}
	}
	
	/**
	 * Returns all children as a string
	 * @return String
	 */
	public String returnChildren() {
		String returnString = "";
		
		if(getChildren() != null) {
			Vector<Child> children = getChildren();
			
			for(Child child : children) {				
				if(child instanceof InlineChild) {					
					returnString += "\n\n======================\n\n";
					returnString += ((InlineChild)child).returnAll();
				}
				else if(child instanceof NodeChild) {
					returnString += "\n\n======================\n\n";
					returnString += ((NodeChild)child).returnAll();
				}
				else if(child instanceof StringIDChild) {
					returnString += "\n\n======================\n\n";
					returnString += ((StringIDChild)child).returnAll();
				}
			}
		}
		return returnString;
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
	 * @param NIDHex
	 */
	public void setNIDHex(String NIDHex) {
		this.NIDHex = NIDHex;
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
	 * @return String
	 */
	public String getNIDHex() {
		return NIDHex;
	}
	
	/**
	 * 
	 * @param parent
	 */
	public void setParentNode(Node parent) {
		this.parent = parent;
	}
	
	/**
	 * 
	 * @return Node
	 */
	public Node getParentNode() {
		return parent;
	}
	
	/**
	 * Sets whether this node has a pointer out of it
	 * @param isContinuation 
	 */
	public void setIsContinuation(boolean isContinuation) {
		this.isContinuation = isContinuation;
	}
	
	/**
	 * Sets whether this node has a pointer into it
	 * @return boolean
	 */
	public boolean isContinuation() {
		return isContinuation;
	}
	
	/**
	 * Returns the rectangle continuation rectangle
	 * @return Rectangle2D
	 */
	public Rectangle2D getContinuationRect() {
		return contOut;
	}
	
	/**
	 * Sets whether this node has a pointer into it
	 * @param hasContinuation 
	 */
	public void setHasContinuation(boolean hasContinuation) {
		this.hasContinuation = hasContinuation;
	}
	
	/**
	 * Whether this node has an external node
	 * @return boolean
	 */
	public boolean hasExternalNode() {
		return hasExternalNode;
	}
	
	/**
	 * Returns this node's external rectangle
	 * @return Rectangle2D
	 */
	public Rectangle2D getExternalRect() {
		return externalRect;
	}
	
	/**
	 * Whether this node has a continuation
	 * @return boolean
	 */
	public boolean hasContinuation() {
		return hasContinuation;
	}
	
	/**
	 * Returns the continued rectangle
	 * @return Rectangle2D
	 */
	public Rectangle2D getContinuedRect() {
		return contIn;
	}
	
	/**
	 * Sets parent slot
	 * @param parentSlot
	 */
	public void setParentSlot(int parentSlot) {
		this.parentSlot = parentSlot;
	}
	
	/**
	 * Returns the parent slot
	 * @return int
	 */
	public int getParentSlot() {
		return parentSlot;
	}
	
	/**
	 * Sets the node's level from the node the user clicked on
	 * @param level 
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * Returns the level that this node is viewed on
	 * @return int
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Gets the type information for this node
	 * @return String
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns whether this node has children
	 * @return boolean
	 */
	public boolean hasChildren() {
		if(children.size() == 0) { return false; }
		else { return true; }
	}
	
	/**
	 * Returns the children
	 * @return Vector<Child>
	 */
	public Vector<Child> getChildren() {
		return children;
	}
	
	/**
	 * Adds a child
	 * @param node 
	 */
	public void addChild(Child node) {
		children.add(node);
	}
	
	/**
	 * Returns this node's slot
	 * @return int
	 */
	public int getSlot() { 
		return slot;
	}
	
	/**
	 * Return this node's kind
	 * @return String
	 */
	public String getKind() {
		return kind;
	}
	
	/**
	 * Returns this node's flags
	 * @return String
	 */
	public String getFlags() {
		return flags;
	}
	
	
}
