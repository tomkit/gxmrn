package dart;

import datastructures.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;
import java.util.*;

/**
 * @author tomkit07
 * Pages Board
 */
public class PagesBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private Page selectedPage = null;  

	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * This is the default constructor
	 */
	public PagesBoard() {
		super();
		initialize();
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Closes all data structures
	 */
	public void close() {
		if(selectedPage != null) {
			selectedPage.setClicked(false);
		}
		selectedPage = null;
		SharedData.setPagesNull();
		SharedData.setViewingPagesNull();
		repaint();
	}
	
	/**
	 * Resets the data structures, but doesn't close
	 */
	public void reset() {
		if(selectedPage != null) {
			selectedPage.setClicked(false);
		}
		selectedPage = null;
	}
	
	/**
	 * Prepares the pages to be drawn from pages in paged-ascending order
	 */
	public void setPages() {
		Hashtable<Integer, Page> viewingPages = new Hashtable<Integer, Page>();
		Set set = SharedData.getPages().keySet();
		TreeSet<Set> tset = new TreeSet<Set>(set);	// used to sort the pages
		Iterator i = tset.iterator();
		int slot = 0;
		
		// read all pages and place it in the viewingPages data structure
		while(i.hasNext()) {
			int key = (Integer)i.next();
			Page page = new Page(slot, key);
			viewingPages.put(key, page);
			slot++;
		}
		SharedData.setViewingPages(viewingPages);
		
		repaint();
	}
	
	/**
	 * Highlight interface
	 * @param slot 
	 */
	public void setHighlight(int slot) {
		
		// set current page
		SharedData.setCurrentPage(slot);
		
		// slide the panel to that region
		slidePagesPanel(slot);
		
		// highlight the page
		setHighlight(SharedData.getViewingPage(slot));
		
	}
	
	/**
	 * Paints this component
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// draw the pages first
		Draw.drawPages(SharedData.getViewingPages(), g);
		
		// then highlight
		if(selectedPage != null) {
			selectedPage.highlight(g);
		}
	}

	/*
	 * ===================
	 * = Private Methods =
	 * ===================
	 */
	
	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(16000, 80));
	}
	
	/**
	 * Automatically slides the pages panel to follow for a continuation node or search
	 */
	private void slidePagesPanel(int key) {
		
		// calculate the rectangle region to move by
		Rectangle2D r2d = SharedData.getViewingPages().get(key).getRect();
		int x = (int)r2d.getX();
		int y = (int)r2d.getY();
		
		this.scrollRectToVisible(new Rectangle(x-459, y, 1059, 1)); // !! fixed values. change it so it's relative
	}

	/**
	 * Prepares the pages to highlight, helper function for setHighlight()
	 */
	private void setHighlight(Page page) {
		
		// sets up the selectedPage value to be highlighted
		if(page != null) {
			reset();
			selectedPage = page;
			selectedPage.setClicked(true);
		}
		repaint();
	}
}
