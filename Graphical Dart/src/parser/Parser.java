/**
 * 
 */
package parser;

import static dart.Parameter.*;
import datastructures.*;

import java.io.*;
import java.util.*;

/**
 * @author tomkit07
 * This class reads into memory the dart dump
 */
public class Parser {
	private static Hashtable<Integer, Hashtable<Integer, Node>> pages = null;
	
	/* 
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * This is the default constructor
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchElementException
	 */
	public Parser(File fileName) throws UnsupportedEncodingException, NoSuchElementException {
		pages = new Hashtable<Integer, Hashtable<Integer, Node>>();
		ReadData rd = new ReadData(fileName);	// read from file and put into memory
		driver(rd.getData());					// parse from memory
		setHierarchy();							// set up hierarchy of nodes into a tree
		
		if(DEBUGMODE) {
			testPrint();
		}
		
		if(DEBUGMODE) {
			System.out.println("successfully completed!");
		}
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Returns the data structure holding all the pages
	 * @return Hashtable<Integer, Hashtable<Integer, Node>>
	 */
	public Hashtable<Integer, Hashtable<Integer, Node>> getPages() {
		return pages;
	}
	
	/* 
	 * ===================
	 * = Private Methods =
	 * ===================
	 */
	
	/**
	 * After setting up the pages data structure, traverse through
	 * each region root node and set each node's level from the root
	 * with the root being level 0
	 */
	private void setHierarchy() {
		
		// for each page
		Enumeration pagesEnum = pages.elements();
		while(pagesEnum.hasMoreElements()) {
			Hashtable page = (Hashtable)pagesEnum.nextElement();

			// for each node
			Enumeration pageEnum = page.elements();
			while(pageEnum.hasMoreElements()) {
				Node node = (Node)pageEnum.nextElement();

				// only follow root nodes
				if(node instanceof RootNode) {
					
					// set up the level
					setLevel(page, node, 0);
				}
			}
		}
	}
	
	/**
	 * Helper function to setLevel() that recursively assigns
	 * levels to each node
	 */
	private void setLevel(Hashtable page, Node parent, int level) {

		// set the level
		parent.setLevel(level);
		
		// iterate through the tree
		if(parent.hasChildren()) {
			for(Child child : parent.getChildren()) {
				if(!(child instanceof InlineChild) && !(child instanceof ContinuationChild) && !(child instanceof StringIDChild)) {
					if(child.getSlot() != -1) {
										
						// set the parent slot information too
						Node nodePointedToByChild = ((Node)page.get(child.getSlot()));
						nodePointedToByChild.setParentSlot(parent.getSlot());
						nodePointedToByChild.setParentNode(parent);
						nodePointedToByChild.setNID(child.getNID());
						nodePointedToByChild.setNIDHex(child.getNIDHex());
						
						// recursively go through all nodes to set the level
						setLevel(page, (Node)page.get(child.getSlot()), ++level);
						level--;
					}
				}
			}
		}
	}
	
	/**
	 * Returns a merged page if there is already an existing one
	 */
	private Hashtable<Integer, Node> merge(int pageNum, Hashtable<Integer, Node> page) {
		Hashtable<Integer, Node> existingPage = pages.get(pageNum);
		// page = newly created page
		
		Enumeration e = existingPage.keys();
		while(e.hasMoreElements()) {
			int key = (Integer)e.nextElement();
			page.put(key, existingPage.get(key));
		}
		
		return page;
	}
	
	/** 
	 * Converts the Vector of hexadecimals to unicode
	 * and then parses the data and inserts it into a 
	 * tree structure in memory used for traversing
	 * nodes and visualizing pages
	 */
	private void driver(Vector<String> allData) throws UnsupportedEncodingException, NoSuchElementException {
		String unicodeString = "";										
		String hexString = "";											
		Iterator<String> i = allData.iterator();
		Hashtable<Integer, Node> page = new Hashtable<Integer, Node>();
		int presentParent = INVALID;
		int pageNum = -1;
		
		// check for dxg header
		if(i.hasNext()) {
			hexString = i.next();
			if(!isDXG(hexString)) {
				
				if(DEBUGMODE) {
					System.out.println("Not a DXG file.");
				}
				
				throw new UnsupportedEncodingException();
			}
		}
		
		// read in lines
		while(i.hasNext()) {
			
			if(DEBUGMODE) {
				System.out.println("-------------------");
			}

			// grab the string
			hexString = (String)i.next();
			
			// get slot and type
			String header = getHeader(hexString);
			int slot = getSlot(header);
			
			// set the page number
			if(getType(header).equals("P")) {
				
				// whenever there's a new regional root node,
				// create a new page for it. flush the current page into 
				// pages except for the very first one
				if(pageNum != -1) {
					
					if(DEBUGMODE) {
						System.out.println("-------------------");
						System.out.println("Inserted page: " + pageNum);
						System.out.println("-------------------");
					}
					
					// merge into an existing page if a page with the same
					// number is already present (this happens in the case
					// when there are multiple regional root nodes on the
					// same page
					if(pages.containsKey(pageNum)) {
						pages.put(pageNum, merge(pageNum, page));
					}
					
					// put into a new page
					else {
						pages.put(pageNum, page);
					}
					page = new Hashtable<Integer, Node>();
				}
				
				// set current slot as parent
				pageNum = slot;
				
				if(DEBUGMODE) {
					System.out.println("current page number: " + pageNum);
				}
			}

			// parse the line(node) that is read in
			else 
			{
				// if this line is a parent, set it
				if(isParent(hexString)) {
					presentParent = slot;
				}

				// convert hex to string 
				unicodeString = hexToString(hexString);
				
				// convert string to node 
				Node node = null;
				try {
					node = getNode(page, unicodeString, presentParent);
				}
				catch(NoSuchElementException e) {
					System.out.println("Not in proper DXG format.");
					return;
				}
				
				// put node in current page 
				page.put(presentParent, node);
			}
		}		
		
		if(DEBUGMODE) {
			System.out.println("-------------------");
			System.out.println("Inserted page: " + pageNum);
			System.out.println("-------------------");
		}
		
		// flush the very last page out, either new or merge
		if(pages.containsKey(pageNum)) {
			pages.put(pageNum, merge(pageNum, page));
		}
		else {
			pages.put(pageNum, page);
		}
	}
	
	/**
	 * Checks if the node is a parent by looking at its first byte
	 */
	private boolean isParent(String hexString) {
		
		// if there is a space in the line read, it is not a parent
		// obviously, this is dependent on the output from DXG dart
		if(hexString.charAt(0) == ' ') { 
			if(DEBUGMODE) {
				System.out.println("not parent"); 
			}
			return false; 
		}
		
		// otherwise it is a parent
		else { 
			if(DEBUGMODE) {
				System.out.println("is parent"); 
			}
			return true; 
		}
	}
	
	/**
	 * Outputs information for debugging purposes
	 */
	private void testPrint() {
		Hashtable page = new Hashtable();
		
		Enumeration pagesEnum = pages.elements();

		while(pagesEnum.hasMoreElements()) {
			page = (Hashtable)pagesEnum.nextElement();
			System.out.println("********************************************");
			System.out.println("Page Printout");
			System.out.println("Page size: " + page.size());
			Enumeration e = page.keys();
			while(e.hasMoreElements()) {
				int key = (Integer)e.nextElement();
				Node node = (Node)page.get(key);
				if(node instanceof DocumentNode) {
					System.out.println("----------------------");
					System.out.println("instance of document node");
					((DocumentNode)node).printAll();
				}
				else if(node instanceof ElementNode) {
					System.out.println("----------------------");
					System.out.println("instance of element node");
					((ElementNode)node).printAll();
				}
				else if(node instanceof RootNode) {
					System.out.println("----------------------");
					System.out.println("instance of root node");
					((RootNode)node).printAll();
				}
				else if(node instanceof TextNode) {
					System.out.println("----------------------");
					System.out.println("instance of text node");
					((TextNode)node).printAll();
				}
				else if(node instanceof AttributeNode) {
					System.out.println("----------------------");
					System.out.println("instance of attribute node");
					((AttributeNode)node).printAll();
				}
				else if(node instanceof CommentNode) {
					System.out.println("----------------------");
					System.out.println("instance of comment node");
					((CommentNode)node).printAll();
				}
				else if(node instanceof ProcessingInstructionNode) {
					System.out.println("----------------------");
					System.out.println("instance of processing instruction node");
					((ProcessingInstructionNode)node).printAll();
				}
				else {
					System.out.println("----------------------");
					System.out.println("instance of non-captured node");
				}
			}
			System.out.println("********************************************");
		}
	}
	
	/**
	 * Returns the slot number given a header string (which consists of both
	 * a header and a tpye)
	 */
	private int getSlot(String header) {
		int endSlotIndex = 0;
		
		for(endSlotIndex = 0; endSlotIndex < header.length(); endSlotIndex++) {
			try {
				Integer.parseInt(header.substring(endSlotIndex, endSlotIndex+1));
			}
			catch(NumberFormatException e) {
				break;
			}
		}
		
		return Integer.parseInt(header.substring(0, endSlotIndex));	
	}
	
	/** 
	 * Returns the type given a header string (which consists of both a
	 * header and a type)
	 */
	private String getType(String header) {
		int endSlotIndex = 0;
		
		for(endSlotIndex = 0; endSlotIndex < header.length(); endSlotIndex++) {
			try {
				Integer.parseInt(header.substring(endSlotIndex, endSlotIndex+1));
			}
			catch(NumberFormatException e) {
				break;
			}
		}
		
		return header.substring(endSlotIndex, header.length());
	}
	
	/**
	 * Converts the one line of test into the
	 * record data structure
	 */
	private Node getNode(Hashtable page, String unicodeString, int presentParent) throws NoSuchElementException {
		Scanner sc = new Scanner(unicodeString);
		String header = sc.next();
		
		if(DEBUGMODE) {
			System.out.println("------------------");
			System.out.println("header: " + header);
		}
		
		int slot = getSlot(header);
		String type = getType(header);
		
		if(DEBUGMODE) {
			System.out.println("slot: " + slot);
			System.out.println("type: " + type);
		}
		
		// ROOT NODE: root
		if(type.equals("R")) {
			
			if(DEBUGMODE) {
				System.out.println("new root node created");
			}
			
			// set the attributes
			String kind = sc.next();
			String kindID = sc.next();
			String flags = sc.next();
			String numChildren = sc.next();
			String pathID = sc.next();
			String documentID = sc.next();
			String versionID = sc.next();
			String nodeIDLength = sc.next();
			String nodeID = sc.next();
			
			sc.useDelimiter("\n");
			String NIDHex = sc.next().trim();	// !! trim last value (because dart dxg has an extra space somewhere)
			
			return new RootNode(slot, type, kind, kindID, flags, numChildren, pathID, documentID, versionID,
					nodeIDLength, nodeID, NIDHex);
			
		}
		
		// NODE: element node, document node, text node 
		else if(type.equals("E") || type.equals("D") || type.equals("T") || 
				type.equals("CM") || type.equals("PI") || type.equals("A")) {
			
			if(DEBUGMODE) {
				if(type.equals("E")) System.out.println("new element node created");
				else if(type.equals("D")) System.out.println("new document node created");
				else if(type.equals("T")) System.out.println("new text node created");
				else if(type.equals("CM")) System.out.println("comment node created");
				else if(type.equals("PI")) System.out.println("processing instruction node created");
				else if(type.equals("A")) System.out.println("attribute node created");
			}
			
			// set the attributes
			String kind = sc.next();
			String kindID = sc.next();
			String flags = sc.next();
			String numDescendants = sc.next();
			String internal = sc.next();
			String attributes = sc.next();
			String ordered = sc.next();
			String parentSlotIndex = sc.next();
			String simpleType = sc.next();
			String annotatedTypeID = sc.next();
			String indexTypeValue = sc.next();
			String localName = sc.next();
			String namespaceURI = sc.next();
			String namespacePrefix = sc.next();	
			
			// return the new node depending on the type
			if(type.equals("E")) {
				return new ElementNode(slot, type, kind, kindID, flags, numDescendants, internal, attributes,
						ordered, parentSlotIndex, simpleType, annotatedTypeID, indexTypeValue, localName,
						namespaceURI, namespacePrefix);
			}
			else if(type.equals("D")) {
				return new DocumentNode(slot, type, kind, kindID, flags, numDescendants, internal, attributes,
						ordered, parentSlotIndex, simpleType, annotatedTypeID, indexTypeValue, localName,
						namespaceURI, namespacePrefix);
			}
			else if(type.equals("T")) {	
				return new TextNode(slot, type, kind, kindID, flags, numDescendants, internal, attributes,
						ordered, parentSlotIndex, simpleType, annotatedTypeID, indexTypeValue, localName,
						namespaceURI, namespacePrefix);
			}
			else if(type.equals("CM")) {
				return new CommentNode(slot, type, kind, kindID, flags, numDescendants, internal, attributes,
						ordered, parentSlotIndex, simpleType, annotatedTypeID, indexTypeValue, localName,
						namespaceURI, namespacePrefix);
			}
			else if(type.equals("PI")) {
				return new ProcessingInstructionNode(slot, type, kind, kindID, flags, numDescendants, internal, attributes,
						ordered, parentSlotIndex, simpleType, annotatedTypeID, indexTypeValue, localName,
						namespaceURI, namespacePrefix);
			}
			else if(type.equals("A")) {
				return new AttributeNode(slot, type, kind, kindID, flags, numDescendants, internal, attributes,
						ordered, parentSlotIndex, simpleType, annotatedTypeID, indexTypeValue, localName,
						namespaceURI, namespacePrefix);
			}
			
		}

		// CHILD NODE: node child, inline cihld, continuation child, stringid child
		else if(type.equals("NO") || type.equals("IN") || type.equals("CO") || type.equals("SI")) {
			
			if(DEBUGMODE) {
				System.out.println("new child created");
			}
			
			String kind = sc.next();
			String flags = sc.next();
			
			sc.useDelimiter("\n");
			String data = sc.next();	// the rest of the child node	
			
			Child child = null;
			if(type.equals("NO")) {
				child = new NodeChild(slot, type, kind, flags, data);
			}
			else if(type.equals("IN")) {
				child = new InlineChild(slot, type, kind, flags, data);
			}
			else if(type.equals("CO")) {
				child = new ContinuationChild(slot, type, kind, flags, data);
			}
			else if(type.equals("SI")) {
				child = new StringIDChild(slot, type, kind, flags, data);
			}
			
			// it is always the case these nodes are children 
			Node node = (Node)page.get(presentParent);
			node.addChild(child);
				
			return node;
		}

		// ROOT's CHILD: (no type "")
		else if (type.equals("")) {
			
			if(DEBUGMODE) {
				System.out.println("root child created");
			}
			
			String flags = sc.next();
			int parentSlot = Integer.valueOf(sc.next());
			RootChild rootChild = new RootChild(slot, type, flags, parentSlot);
			
			if(DEBUGMODE) {
				rootChild.printAll();
			}
			
			Node node = (Node)page.get(presentParent);
			node.addChild(rootChild);
			
			return node;
		}

		else {

		}
		
		// shouldn't go here
		if(DEBUGMODE) {
			System.out.println("SHOULDN'T GO HERE, IF IT GOT HERE THEN IT DIDN'T CATCH A NODE TYPE");
		}
		
		return new Node();
	}
	
	/**
	 * Checks to see the read file is a DXG file
	 */
	// !! This could be pushed to ReadData to prevent memory waste
	// for large files
	private boolean isDXG(String hexString) {
		
		if(DEBUGMODE) {
			System.out.println("------------");
		}
		
		// the first line in a DXG file should be "#DXG" 
		if(hexString.equals("#DXG")) {
			if(DEBUGMODE) {
				System.out.println("DXG file.");
			}
			return true;
		}
		
		System.out.println("Not DXG file.");
		return false;
	}
	
	/** 
	 * Converts a hexadecimal string into a unicode string
	 */
	private String hexToString(String hexString) {
		String returnString = "";
		
		if(DEBUGMODE) {
			System.out.println("----------------------");
			System.out.println("original: " + hexString);
		}
		
		// split header and content from hexString 
		String header = getHeader(hexString);
		returnString += header + " ";
		String content = getContent(hexString);
		
		if(DEBUGMODE) {
			System.out.print("unicode: ");
		}
	
		// convert hex to unicode 
		for(int i = 0; i < content.length(); i+=2) {
			
			try {
				String subString = content.substring(i, i+2);
				char c = (char)Integer.parseInt(subString, 16);
				
				if(DEBUGMODE) {
					System.out.print(c);
				}
				
				returnString += c;
			}
			catch(NumberFormatException e) {
				System.out.println("Error: can't convert hex string. Turn debug on.");
				e.printStackTrace();
			}
			catch(StringIndexOutOfBoundsException e) {
				System.out.println("Error: can't convert hex string. Turn debug on.");
				e.printStackTrace();
			}
		}

		if(DEBUGMODE) {
			System.out.println();
		}

		return returnString;
	}
	
	
	/**
	 * Returns the first part of a hexString delimited by a space
	 */
	private String getHeader(String hexString) {
		Scanner sc = new Scanner(hexString);
		
		if(sc.hasNext()) { return sc.next(); }	
		
		return null;
	}
	
	/** 
	 * Returns the second part of a hexString delimited by a space
	 * 
	 */
	private String getContent(String hexString) {
		Scanner sc = new Scanner(hexString);
		
		if(sc.hasNext()) { sc.next(); }
		if(sc.hasNext()) { return sc.next(); }
		
		return null;
		
	}
	
}
