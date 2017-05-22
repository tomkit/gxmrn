/**
 * 
 */
package dart;
import javax.swing.*;
import javax.swing.text.*;

import datastructures.*;

/**
 * @author tomkit07
 * This class displays details of nodes
 */
public class TextBoard extends JTextPane {
	private static final long serialVersionUID = 1L;
	private Node node = null;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	TextBoard() {
		
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Displays this node in the textboard
	 * @param key 
	 * @param currentPage 
	 */
	public void setNode(int currentPage, int key) {
		
		// if nothing selected
		if(key == 0 && currentPage == 0) {
			setNull();
		}
		
		// otherwise set up the text content of the node selected
		else {
			SharedData.setCurrentPage(currentPage); // !! redundant?
			SharedData.setSelectedNodeKey(key); 
			
			// set text
			node = SharedData.getNode(key);
			setText("Page: " + currentPage + "\n" + node.returnAll());
						
			// make the textpane scroll back to its original position after setting text
			try {
				this.setCaretPosition(0);
			}
			catch(Exception e) {}
		}
	}
	
	/**
	 * Sets this textboard to blank
	 */
	public void setNull() {
		setText("");
	}
}
