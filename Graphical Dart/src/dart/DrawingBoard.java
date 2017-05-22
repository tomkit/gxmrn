/**
 * 
 */
package dart;

import static dart.Parameter.*;
import datastructures.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * @author tomkit07
 * This panel is used in the Display panel to display the nodes
 */
public class DrawingBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Node selectedNode = null;		
	private Vector<Node> children = null;			
	private Vector<Node> parents = null;			
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * this is the default constructor
	 */
	public DrawingBoard() {
		super();
		initialize();
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * resets all the local fields and refreshes the screen
	 */
	public void close() {
		dehighlight();
		children = null;
		selectedNode = null;
		parents = null;
		SharedData.setPageNull();
		repaint();
	}
	
	/**
	 * resets to nothing highlighted but doesn't close data structures
	 */
	public void reset() {
		dehighlight();
		children = null;
		selectedNode = null;
		parents = null;
	}
	
	/**
	 * called from SharedData. initialize the pages data structure
	 * and initialize clickable regions on pages 
	 */
	public void setPages() {
		repaint();
	}
	
	/**
	 * sets up the page to draw
	 * @param page
	 * @param slot
	 */
	public void setHighlight(int page, int slot) {
		if(SharedData.getPages() != null) {
			
			// selected a node
			if(SharedData.getPage(page) != null) {
				
				// remove previous properties
				close();
				
				// set up selected page
				SharedData.setPage(page);
				if(slot != -1) {
					SharedData.setSelectedNodeKey(slot);
					setHighlight(slot);
				}
			}
		}
		repaint();
	}
	
	/**
	 * this method paints
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// first draw the cells
		Draw.setCells(SharedData.getPage(), g);
		
		// then highlight
		Draw.highlight(selectedNode, SharedData.getPage(), children, parents, g);
		
		// we don't highlight the continuation icons anymore. uncomment if you want to try
		//if(selectedNode != null) {
		//	selectedNode.highlight(g, clickedDirection);
		//}
	}
	
	/**
	 * called from SharedData to highlight a node. this calls the helper, overloaded setHighlight()
	 * @param slot 
	 */
	public void setHighlight(int slot) {
		if(SharedData.getSelectedNodeKey() != -1) {
			setHighlight(SharedData.getNode(slot));
		}
	}
	
	/**
	 * !! to draw the array of external nodes instead of displaying it as text in details panel
	 */
	public void drawExternalNodes() {
		
	}
	
// we don't highlight continuation icons anymore. uncomment if you want to try
//	/**
//	 * Highlights the continuation rectangle
//	 */
//	public void setHighlightContinuation(int slot) {
//		if(clickedDirection == null) {
//			clickedDirection = SharedData.getNode(slot).getContinuationRect();
//		}
//		else {
//			clickedDirection = null;
//		}
//		repaint();
//	}
//	/**
//	 * Highlights the continued rectangle
//	 */
//	public void setHighlightContinued(int slot) {
//		if(clickedDirection == null) {
//			clickedDirection = SharedData.getNode(slot).getContinuedRect();
//		}
//		else {
//			clickedDirection = null;
//		}
//		repaint();
//	}
	
	/*
	 * ===================
	 * = Private Methods =
	 * ===================
	 */
	
	/**
	 * removes highlighting of a selected node
	 */
	private void dehighlight() {
		if(selectedNode != null && selectedNode.isClicked()) {
			selectedNode.setClicked(false);
		}
	}
	
	/**
	 * sets the value of the Rectangle2D selected and repaints.
	 * if null, everything is cleared
	 */
	private void setHighlight(Node node) {
		if(node != null) {

			// resets previous properties first
			reset();
			
			// sets up the selected node to be highlighted
			selectedNode = node;
			selectedNode.setClicked(true);
			
			if(DEBUGMODE) {
				selectedNode.printAll();
			}
			
			// highlight children and parents
			try {
				children = new Vector<Node>();
				parents = new Vector<Node>();
				
				setHighlightChildren(selectedNode);
				setHighlightParents(selectedNode);
				
				if(DEBUGMODE) {
					System.out.println("-------------------------");
					System.out.println("selected!");
					node.printAll();
				}
				
			}
			catch(NullPointerException e) {
				System.out.println("Nothing to highlight.");
			}
		}
		repaint();
	}
	
	/**
	 * prepares the children nodes to be highlighted
	 */
	private void setHighlightChildren(Node node) {
		
		// iterate through all children nodes
		if(node.hasChildren()) {
			for(Child n : node.getChildren()) {
				if(n.getType().equals("NO") || n.getType().equals("")) {
					
					// add the child to the children vector to be highlighted
					children.add(n);
					
					if(DEBUGMODE) {
						System.out.println("Added a child (type: " + n.getType() + ") to highlight in slot: " + n.getSlot());
					}
					
					// enable this to highlight all children
					//setHighlightChildren(SharedData.getPage().get(n.getSlot())); 
				}
			}
		}
	}
	
	/**
	 * Prepares the parent nodes to be highlighted
	 */
	private void setHighlightParents(Node node) {
		
		// as long as there is a parent
		if(node.getParentSlot() != -1) {
			
			// add the parent to be highlighted
			parents.add(SharedData.getNode((node.getParentSlot())));
			
			// enable this to highlight all parents
			//setHighlightParents(SharedData.getPage().get(node.getParentSlot()));
		}
	}
	
	/**
	 * This method initializes this JPanel
	 */
	private void initialize() {
		this.setLayout(new GridBagLayout());
	}
} 
