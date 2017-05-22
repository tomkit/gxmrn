/**
 * 
 */
package dart;

import datastructures.*;

import static dart.Parameter.*;
import static java.awt.Color.*;
import java.awt.*;
import java.util.*;

/**
 * @author tomkit07
 * This class does all the common drawing used in Pages and Nodes
 */
public class Draw {
	
	/* 
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	Draw() {
		
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */

	/** 
	 * Highlights the selected node and all its children and parents
	 * @param selectedNode 
	 * @param pageData 
	 * @param children 
	 * @param parents 
	 * @param g 
	 */
	public static void highlight(Node selectedNode, Hashtable<Integer, Node> pageData, Vector<Node> children, Vector<Node> parents, Graphics g) {
		
		// highlight selected node
		if(selectedNode != null) {

			if(DEBUGMODE) {
				System.out.println("------------");
				System.out.println("highlighted");
			}
			
			selectedNode.highlight(g, RED.darker().darker());	// normal highlight color is set darker so parent can be brighter
		}
		
		// highlight children that are one level below
		if(children != null && pageData != null && selectedNode != null) {
			for(Node i : children){
				int slot = i.getSlot();
				
				if(slot != -1) {
					Node node = pageData.get(slot);
					if(node != null) {  
						if(selectedNode.getLevel()+1 == node.getLevel()) {
							node.highlight(g, RED.darker().darker().darker().darker().darker().darker());
						}
					}
				}
			}
		}
		
		// highlight parents that are one level above
		if(parents != null && pageData != null && selectedNode != null) {
			for(Node i : parents) {
				int slot = i.getSlot();
				
				if(slot != -1) { 
					Node node = pageData.get(slot);
					if(node != null) {
						if(selectedNode.getLevel()-1 == node.getLevel()) {
							node.highlight(g, RED);	// can't get any brighter than this so red is reserved for the parent
						}
					}
				}
			}
		}
	}
	
	/**
	 * Enumerates through all nodes on this page and draws them in the DrawingBoard
	 * @param pageData 
	 * @param g 
	 */
	public static void setCells(Hashtable<Integer, Node> pageData, Graphics g) {
		
		// iterate through all nodes 
		if(pageData != null) {
			Enumeration e = pageData.keys();
			while(e.hasMoreElements()) {
				int key = (Integer)e.nextElement();
				Node node = pageData.get(key);
				
				// draw
				node.draw(g);
			}
		}
	}
	
	/**
	 * Iterates through all the viewingpages in this Vector and displays them in the PagesBoard
	 * @param drawingPage 
	 * @param g 
	 */
	public static void drawPages(Hashtable<Integer, Page> drawingPage, Graphics g) {
		
		// iterates through all drawingPages
		if(drawingPage != null) {
			Enumeration e = drawingPage.keys();
			while(e.hasMoreElements()) {
				int key = (Integer)e.nextElement();
				Page page = (Page)drawingPage.get(key);
				
				// draw
				page.draw(g);
			}
		}
	}
}
