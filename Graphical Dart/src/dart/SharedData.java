package dart;

import java.util.Enumeration;
import java.util.Hashtable;

import datastructures.Node;
import datastructures.Page;

/**
 * @author tomkit07
 * This class contains static variables that are used throughout other classes
 */
public abstract class SharedData {
	//private NodeTransferHandler nodeHandler; 									// for drag and drop (in the future)
	private static Hashtable<Integer, Hashtable<Integer, Node>> pages = null;	// contains all pages of nodes
	private static Hashtable<Integer, Node> page = null;						// contains all nodes of a page
	private static Hashtable<Integer, Page> viewingPages = null; 				// contains a list of pages 
	private static int selectedNodeKey = -1;
	private static int currentPage = -1;
	
	// !! change these in the future to be wrapped in methods, i.e. don't allow public access
	static Enumeration docIDEnumerator = null;		// global enumeration for document search, enumerates through pages  
	static Enumeration docIDNodeEnumerator = null; 	// global enumeration for document search, enumerates through nodes
	static int docIDEnumeratorKey = -1;				// global key to resume in old spot
	static String oldSearchID = "";
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	SharedData() {
		
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Gets the selected node's key
	 * @return int
	 */
	public static int getSelectedNodeKey() {
		return selectedNodeKey;
	}
	
	/**
	 * Sets the selected node's key
	 * @param key 
	 */
	public static void setSelectedNodeKey(int key) {
		selectedNodeKey = key;
	}
	
	/**
	 * Returns the current page
	 * @return int
	 */
	public static int getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * Sets the current page
	 * @param page 
	 */
	public static void setCurrentPage(int page) {
		currentPage = page;
	}
	
	/**
	 * Sets the pages
	 * @param localPages 
	 */
	public static void setPages(Hashtable<Integer, Hashtable<Integer, Node>> localPages) {
		pages = localPages;
	}
	
	/**
	 * Gets the pages
	 * @return Hashtable<Integer, Hashtable<Integer, Node>>
	 */
	public static Hashtable<Integer, Hashtable<Integer, Node>> getPages() {
		return pages;
	}
	
	/**
	 * Sets the page
	 * @param key 
	 */
	public static void setPage(int key) {
		page = pages.get(key);
	}
	
	/**
	 * Sets Page to null
	 */
	public static void setPageNull() {
		page = null;
	}
	
	/**
	 * Sets pages to null
	 */
	public static void setPagesNull() {
		pages = null;
	}
	
	/**
	 * Sets viewingPages to null
	 */
	public static void setViewingPagesNull() {
		viewingPages = null;
	}
	
	/**
	 * Gets the current page
	 * @return Hashtable<Integer, Node>
	 */
	public static Hashtable<Integer, Node> getPage() {
		return page;
	}
	
	/**
	 * Gets a specific page
	 * @param key 
	 * @return Hashtable<Integer, Node>
	 */
	public static Hashtable<Integer, Node> getPage(int key) {
		if(pages != null) {
			return pages.get(key);
		}
		return null;
	}
	
	/**
	 * Sets the viewing pages
	 * @param localViewingPages 
	 */
	public static void setViewingPages(Hashtable<Integer, Page> localViewingPages) {
		viewingPages = localViewingPages;
	}
	
	/**
	 * Gets the viewing pages
	 * @return Hashtable<Integer, Page>
	 */
	public static Hashtable<Integer, Page> getViewingPages() {
		return viewingPages;
	}
	
	/**
	 * Gets a specific viewing page
	 * @param key 
	 * @return Page
	 */
	public static Page getViewingPage(int key) {
		if(viewingPages != null) {
			return viewingPages.get(key);
		}
		return null;
	}
	
	/**
	 * Get Node
	 * @param key 
	 * @return Node
	 */
	public static Node getNode(int key) {
		return pages.get(currentPage).get(key);

	}
	
	/*
	 * ===================
	 * = Private Methods =
	 * ===================
	 */
	
	// none
}
