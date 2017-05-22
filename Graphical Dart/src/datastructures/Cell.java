/**
 * 
 */
package datastructures;
import static dart.Parameter.*;
import structures.*;
import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

/**
 * @author tomkit07
 * This class encompasses most of the visual concerns for drawing cells although
 * some Node/Page specific things are in their respective classes
 */
public abstract class Cell extends JComponent implements Clickable, Drawable, Writable {
	private boolean clickable = false;
	private Rectangle2D rect = null;
	private boolean clickedOn = false;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	Cell() {
	}
	
	/**
	 * Overloaded constructor
	 */
	Cell(int slot) {
		this.rect = slotToRect(slot); // Uses the local slotToRect() method instead of the super
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Returns this cell's rectangle
	 * @return Rectangle2D
	 */
	public Rectangle2D getRect() {
		return rect;
	}
	
	/**
	 * Sets whether this cell was clicked on
	 * @param clickedOn 
	 */
	public void setClicked(boolean clickedOn) {
		this.clickedOn = clickedOn;
	}
	
	/**
	 * Returns if this cell was clicked on
	 * @return boolean
	 */
	public boolean isClicked() {
		return clickedOn;
	}
	
	/**
	 * This draws the cells in row major form
	 * @param g 
	 */
	public void drawRect(Graphics g) {
		g.drawRect((int)rect.getX(), (int)rect.getY(), CELLWIDTH, CELLHEIGHT);
	}
	
	/**
	 * For translucency
	 * @param alpha 
	 * @return AlphaComposite
	 */
	public AlphaComposite makeComposite(float alpha) {
	    int type = AlphaComposite.SRC_OVER;
	    return(AlphaComposite.getInstance(type, alpha));
	}
	
	/**
	 * Returns a string that fits into the width of this cell
	 * @param text 
	 * @param fontSize 
	 * @return String
	 */
	public String getFittableText(String text, int fontSize) {
		// calculate the pixels needed to display the text
		// to make sure that they are within the boundaries of
		// the cells
		// !! this doesn't work as well as intended because fontsize
		// doesn't equal the width of each character
		// e.g. l is shorter than m in pixels
		int allowableWidth = (CELLWIDTH-CELLBUFFER*2)*2/fontSize-2; 
		
		// return the whole string if it fits
		if(text.length() <= allowableWidth) {
			return text;
		}
		
		// otherwise return a substring
		return text.substring(0, allowableWidth) + "...";
	}
	
	
	/* 
	 * ===================
	 * = Private Methods =
	 * ===================
	 */
	
	/**
	 * Returns where this node's rectangle should be drawn given its slot number.
	 * This overwrites the super because it only draws in one row
	 */
	private Rectangle2D slotToRect(int slot) {
		
		// calculate where the slot should be drawn
		Rectangle2D rect = new Rectangle2D.Float(STARTX + (slot%NUMCOLS)*CELLWIDTH,
				STARTY + (slot/NUMCOLS)*CELLHEIGHT,
				CELLWIDTH, CELLHEIGHT);
		
		return rect;
	}
	
	/*
	 * ========================
	 * = Implements Clickable =
	 * ========================
	 */
	
	/**
	 * Sets whether this cell is clickable
	 */
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	
	/**
	 * Returns whether this cell is clickable
	 */
	public boolean isClickable() {
		return clickable;
	}
	
	/*
	 * =======================
	 * = Implements Drawable =
	 * =======================
	 */
	/**
	 * Empty, to be implemented by inheriter
	 */
	public void draw(Graphics g) { }
	
	/*
	 * =======================
	 * = Implements Writable =
	 * =======================
	 */
	/**
	 * To print out information to the console
	 */
	public void printAll() { }
	
	/**
	 * Returns information as a String
	 * @return String
	 */
	public String returnAll() { return null; }
	
}
