/**
 * 
 */
package datastructures;

import static dart.Parameter.*;

import java.awt.*;
import java.awt.geom.*;

/**
 * @author tomkit07
 * Page
 */
public class Page extends Cell {
	private static final long serialVersionUID = 1L;
	private int number = -1;
	private Rectangle2D rect = null;
	
	/* 
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	Page() {
		
	}
	
	/**
	 * Overloaded constructor
	 * @param slot 
	 * @param number 
	 */
	public Page(int slot, int number) {
		this.number = number;
		//this.slot = slot;
		this.rect = slotToRect(slot);
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Paints this 
	 */
	public void draw(Graphics g) {
		drawRect(g);
		drawText(g);
		setClickable(true);
	}
	
	/** 
	 * Highlights this node in a translucent color
	 * @param g 
	 */
	public void highlight(Graphics g) {
		/*
		 * transparency
		 */
		Composite originalComposite = ((Graphics2D)g).getComposite();
		((Graphics2D)g).setComposite(makeComposite(ALPHA));
		
		/*
		 * highlight
		 */
		g.setColor(Color.RED); 
		g.fillRect((int)rect.getX()+1, (int)rect.getY()+1, CELLWIDTH-1, CELLHEIGHT-1);
		
		/*
		 * set old transparency
		 */
		((Graphics2D)g).setComposite(originalComposite);
		
		/*
		 * highlight border
		 */
		if(isClicked()) {
			g.setColor(Color.YELLOW);
			g.drawRect((int)rect.getX(), (int)rect.getY(), CELLWIDTH, CELLHEIGHT);
		}
	}
	
	/**
	 * Returns this rect
	 */
	public Rectangle2D getRect() {
		return rect;
	}
	
	/*
	 * ===================
	 * = Private Methods =
	 * ===================
	 */
	
	/**
	 * Returns where this node's rectangle should be drawn given its slot number
	 */
	private Rectangle2D slotToRect(int slot) {
		Rectangle2D rect = new Rectangle2D.Float(STARTX + slot*CELLWIDTH,
				STARTY ,
				CELLWIDTH, CELLHEIGHT);
		
		return rect;
	}
	
	/**
	 * This draws the cells in row major form
	 */
	public void drawRect(Graphics g) {
		g.drawRect((int)rect.getX(), (int)rect.getY(), CELLWIDTH, CELLHEIGHT);
	}
	
	/**
	 * This draws the labels for cells. The most important data are give first priority.
	 * If a certain field is too long to display it will be truncated and appended with "..."
	 * Displays slot number
	 */
	private void drawText(Graphics g) {
		int fontSize = g.getFont().getSize();
		int row = 0;
		int startX = (int)rect.getX()+CELLBUFFER;
		String numberString = "Page# " + Integer.toString(number);
		
		row = 0;
		g.drawString(getFittableText(numberString, fontSize), startX, (int)rect.getY()+((row+1)*fontSize)+CELLBUFFER);
		row = 1;
		// something
		row = 2;
		// something
		
	}
}
