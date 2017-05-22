/**
 * 
 */
package dart;

/**
 * @author tomkit07
 * This class contains all the parameters used as constants in the GUI and visualization
 */
public class Parameter {

	public final static int STARTX = 10;						// starting x position of a cell
	public final static int STARTY = 10;						// starting y position of a cell
	public final static int NUMROWS = 80;						// the max. number of rows in a page
	public final static int NUMCOLS = 10;						// the max number of columns in a page
	public static int RESWIDTH = 1280;							// default window resolution width
	public static int RESHEIGHT = 1024;							// default window resolution height
	public static int PAGEWIDTH = 600;							// pages pane initial width
	public static int PAGEHEIGHT = 100;							// pages pane initial height
	public static int DRAWWIDTH = 600;							// drawing pane initial width
	public static int DRAWHEIGHT = 400;							// drawing pane initial height
	public static int CELLWIDTH = 90;							// the width of a cell
	public static int CELLHEIGHT = 50;							// the height of a cell
	public static int ENDX = STARTX + NUMCOLS * CELLWIDTH;		// the ending x position of a page
	public static int ENDY = STARTY + NUMROWS * CELLHEIGHT;		// the ending y position of a page
	public static int CELLBUFFER = 2;							// a buffer length to buffer cell borders and text in the cell
	public static float ALPHA = 0.5f;							// alpha transparency setting for highlighting cells
	public final static int INVALID = -1;						// a constant used for represnting invalid
	public final static int MAXNUMCHILDREN = 255;				// the maximum number of children of a node
	public static boolean DEBUGMODE = false;					// enabled - prints out debug information. disabled - no printing to console	
	public static enum DIRECTION {FORWARD, BACKWARD, EXTERNAL}	// continuation node direction
	
	/**
	 * Asserts that the given parameters are all valid
	 * @throws Exception
	 */
	public static void assertParams() throws Exception {
		// make sure there are enough cells
		if(NUMROWS * NUMCOLS != 800) { throw new Exception("Assertion failed: Number of rows * number of columns != 800"); }
		
		// make sure the resolution isn't too small
		if(RESWIDTH < 640) { throw new Exception("Assertion failed: Resolution width < 640"); }
		if(RESHEIGHT < 480) { throw new Exception("Assertion faileD: Resolution height < 480"); }
	}
}
