package dart;

import java.awt.*;
import javax.swing.*;

/**
 * @author tomkit07
 * This panel encompasses all the search tools on pages and nodes.
 * Currently all functionality is in GUI
 */
public class ToolBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */

	/**
	 * This is the default constructor
	 */
	public ToolBoard() {
		super();
		initialize();
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
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);
	}

}
