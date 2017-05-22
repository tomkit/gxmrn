package dart;

import static dart.Parameter.*;
import parser.*;
import datastructures.*;

import java.awt.event.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * @author tomkit07
 * This is the main GUI for Graphical Dart
 */
public class GUI implements Runnable {
	
	/**
	 * Nested class working with the dynamic externalNodesMenu()
	 */
	private class ExternalNodesMenu implements java.awt.event.ActionListener {
		private Vector<ExternalNode> externalNodes = null;
		private int slot = -1;
		
		/**
		 * Constructor
		 * @param externalNodes
		 * @param slotNum
		 */
		ExternalNodesMenu(Vector<ExternalNode> externalNodes, int slotNum) {
			this.externalNodes = externalNodes;
			this.slot = slotNum;
		}

		public void actionPerformed(java.awt.event.ActionEvent e) {
			gotoExternalNodes(externalNodes, slot);
		}
	}
	
	/*
	 * ======================
	 * = GUI Related Fields =
	 * ======================
	 */
	
	private JFrame jFrame = null; 
	private JPanel drawingPanel = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JDialog aboutDialog = null;
	private JPanel aboutContentPane = null;
	private JTextArea aboutVersionTextArea = null;	
	private JScrollPane drawingScrollPane = null;
	private JMenuItem loadMenuItem = null;
	private JFileChooser fileChooser = null;
	private JMenuItem closeMenuItem = null;
	private JPanel jPanel = null;
	private JPanel detailsPanel = null;
	private JPanel toolBarPanel = null;
	private JTextPane textPane1 = null;
	private JTextPane textPane2 = null;
	private JTextPane textPane3 = null;
	private JTextPane textPane4 = null;
	private JPanel displayPanel = null;
	private JPanel pagesPanel = null;
	private JScrollPane pagesScrollPane = null;
	private JPanel toolPanel = null;
	private JLabel documentSearchLabel = null;
	private JTextField docIDTextField = null;
	private JButton docSearchGo = null;
	private JScrollPane detailsScrollPane1 = null;
	private JScrollPane detailsScrollPane2 = null;
	private JScrollPane detailsScrollPane3 = null;
	private JScrollPane detailsScrollPane4 = null;
	private JPopupMenu popupWindows = null;
	private JMenu popupWindowsMenu = null;
	private JPopupMenu popupExternalNodes = null;
	private JMenu popupExternalNodesMenu = null;
	private JMenuItem popupWindowMenuItem2 = null;
	private JMenuItem popupWindowMenuItem3 = null;
	private JMenuItem popupWindowMenuItem4 = null;
	private JLabel pageSearchLabel = null;
	private JTextField pageNOTextField = null;
	private JButton pageNOGo = null;
	private JPanel docSearchPanel = null;
	private JPanel pageSearchPanel = null;
	private JPanel docSearchSubPanel = null;
	private JPanel pageSearchSubPanel = null;
	private JTextField verIDTextField = null;
	private JPanel docSearchSubSubPanel = null;
	private JLabel jLabel = null;
	private JLabel verIDLabel = null;
	private JLabel pageNOLabel = null;
	private JPanel pageSearchSubSubPanel = null;
	private JTextField NIDTextField = null;
	private JLabel NIDLabel = null;
	private JLabel NIDHexLabel = null;
	private JTextField NIDHexTextField = null;
	private JButton resetDocumentSearch = null;
	private JButton resetPageSearch = null;
	/**
	 * This is the default constructor
	 */
	GUI() {
		
	}
	
	/*
	 * ===================
	 * = Public Methods =
	 * ===================
	 */
	
	/**
	 * Launches this application
	 */
	public void run() {
		String classPath = System.getProperty("java.class.path");
		String separator = System.getProperty("file.separator");
		
		// Splash window at program's startup (doesn't work with GDart.jar)
		// !! enable if you want to try to get this splashscreen to work with jar arhive too
		//SplashWindow sw = new SplashWindow("splashscreen.gif", 
		//		jFrame, 1500);
		
		this.getJFrame().setVisible(true);
		
		// Frame icon for Graphical Dart (doesn't work with GDart.jar)
		// !! enable if you want to try to get this icon to work with a jar archive too
		//jFrame.setIconImage(new ImageIcon("frameicon.gif").getImage());
	}
	
	/*
	 * ==========================
	 * = Private Helper Methods =
	 * ==========================
	 */
	
	/**
	 * Sets up fields when a user clicks on an external node
	 */
	private void gotoExternalNodes(Vector<ExternalNode> externalNodes, int slotNum) {
		int page = externalNodes.get(slotNum).getPage(); 
		int slot = externalNodes.get(slotNum).getNode(); 
		
		chooseNode(page, slot);
	}
	
	/**
	 * Sets up all the visuals when a user clicks on a continuation icon
	 */
	private void followContinuation(ExternalNode contPage) {
		int page = contPage.getPage();
		int slot = contPage.getNode();
		
		chooseNode(page, slot);
	}
	
	/**
	 * Sets up all the visuals when a user clicks on a node
	 */
	private void chooseNode(int page, int slot) {
		((PagesBoard)pagesPanel).setHighlight(page);
		((DrawingBoard)drawingPanel).setHighlight(page, slot);
		((TextBoard)textPane1).setNode(page, slot);
	}
	
	/**
	 * Sets up the visuals when a user clicks on a viewingPage
	 */
	private void chooseViewingPage(int key) {
		((PagesBoard)pagesPanel).setHighlight(key);
		((DrawingBoard)drawingPanel).setHighlight(key, -1);
	}
	
	/**
	 * Resets the entire toolbar
	 */
	private void resetToolBar() {
		resetDocumentSearch();
		resetPageSearch();
	}
	
	/**
	 *  Resets the fields for a document search
	 */
	private void resetDocumentSearch() {
		
		// Clear the inputted text
		docIDTextField.setText("");
		verIDTextField.setText("");
		NIDTextField.setText("");
		NIDHexTextField.setText("");
		
		// Clear their stored values
		SharedData.docIDEnumerator = null;		
		SharedData.docIDNodeEnumerator = null; 	
		SharedData.docIDEnumeratorKey = -1;			
		SharedData.oldSearchID = "";
	}
	
	/**
	 * Resets the field for a page search
	 */
	private void resetPageSearch() {
		
		// Clears the input (which is also used to obtain the value)
		pageNOTextField.setText("");
	}
	
	/*
	 * =======================
	 * = Private GUI Methods =
	 * =======================
	 */

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//jFrame.setBounds(new Rectangle(0, 0, RESWIDTH, RESHEIGHT));
			jFrame.setBounds(new Rectangle(0,0,1280,1024));
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setContentPane(getJPanel());
			jFrame.setTitle("Graphical Dart");
		}
		return jFrame;
	}

	/**
	 * This method initializes drawingPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getDrawingPanel() {
		if (drawingPanel == null) {
			drawingPanel = new DrawingBoard();
			drawingPanel.setLayout(new BorderLayout());
			drawingPanel.setBounds(0, 0, DRAWWIDTH, DRAWHEIGHT);
			drawingPanel.setPreferredSize(new Dimension(DRAWWIDTH, DRAWHEIGHT));
			drawingPanel.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					// left click
					if(e.getButton() == MouseEvent.BUTTON1) {		
						
						// iterate through all the nodes to see if it was selected. break early
						// if one is selected
						if(SharedData.getPage() != null) {
							Enumeration en = SharedData.getPage().keys();
							while(en.hasMoreElements()) {
								int key = (Integer)en.nextElement();
								Node node = SharedData.getNode(key);
								
								// user clicked on a region within a node. we check the continued, continuation, and external
								// regions before checking if the user clicked on the other regions of the node
								if(node.isContinuation() || node.hasContinuation() || node.hasExternalNode() || node.isClickable()) {
									ExternalNode contPage = null;
									Vector<ExternalNode> externalNodes = null;	
							
									// continuation region
									if(node.isContinuation() && node.getContinuedRect().contains(e.getX(), e.getY())) {
										contPage = Continuation.findContinuation(node, Parameter.DIRECTION.BACKWARD);
									}
									
									// continued elsewhere region
									else if(node.hasContinuation() && node.getContinuationRect().contains(e.getX(), e.getY())) {
										contPage = Continuation.findContinuation(node, Parameter.DIRECTION.FORWARD);
									}
									
									// external region
									else if(node.hasExternalNode() && node.getExternalRect().contains(e.getX(), e.getY())) {
										externalNodes = Continuation.findExternal(node);
									}
									
									// clicked elsewhere on the node
									else if(node.isClickable() && node.getRect().contains(e.getX(), e.getY())) {
										chooseNode(SharedData.getCurrentPage(), key);
										break;
									}
									
									// check if a continuation/continued region or external region was clicked on
									// if so, follow it
									if(contPage != null) {
										followContinuation(contPage);
										break;
									}
									else if(externalNodes != null) {
										getExternalNodesPopup(externalNodes).show(drawingPanel, e.getX(), e.getY());
										break;
									}
									
								}
								
								// otherwise the user clicked on a region in the node that is not a continuation/continued
								// or external region
								
							}
						}
					}
					
					// middle click
					else if(e.getButton() == MouseEvent.BUTTON2) {
						
						// we are doing nothing here yet
					}
					
					// right click
					else {
						
						// iterate through all the nodes to see if it was selected
						if(SharedData.getPage() != null) {
							Enumeration en = SharedData.getPage().keys();
							while(en.hasMoreElements()) {
								int key = (Integer)en.nextElement();
								Node node = SharedData.getPage().get(key);		
															
								// this node was selected
								if(node.isClickable() && node.getRect().contains(e.getX(), e.getY())) {
									
									// setup textboard node
									SharedData.setSelectedNodeKey(key);	
									
									if(DEBUGMODE) {
										System.out.println("selected key: " + SharedData.getSelectedNodeKey());
									}
									
									// create the popup for user to select which window to push the content
									// of the node to
									getWindowPopup().show(drawingPanel, e.getX(), e.getY());
									
									break;
								}
								
								// if none were selected, reset the value field
								else {
									SharedData.setSelectedNodeKey(-1);
								}
							}
						}
						
					}
				}
			});
			
			// this allows the user to drag the mouse and move the screen
			drawingPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseDragged(java.awt.event.MouseEvent e) {
					Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
			        ((JPanel)e.getSource()).scrollRectToVisible(r);
				}
			});
		}
		return drawingPanel;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getLoadMenuItem());
			fileMenu.add(getCloseMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");			
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.NORTH);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JTextArea getAboutVersionLabel() {
		if (aboutVersionTextArea == null) {
			aboutVersionTextArea = new JTextArea();
			aboutVersionTextArea.setText("Graphical Dart Version 0.9\n" +
										 "By Thomas Chen (tomkit@gmail.com)\n" +
										 "Thanks to the XSI Team\n" +
										 "Special thanks to Uttam Jain");			
			aboutVersionTextArea.setBackground(Color.lightGray);
		}
		return aboutVersionTextArea;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setEnabled(false);
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setEnabled(false);
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setEnabled(false);
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setEnabled(false);
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
		}
		return saveMenuItem;
	}	

	/**
	 * This method initializes drawingScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDrawingScrollPane() {
		if (drawingScrollPane == null) {
			drawingScrollPane = new JScrollPane();
			drawingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			drawingScrollPane.setBorder(BorderFactory.createTitledBorder(null, "Display", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 70, 213)));
			drawingScrollPane.setViewportView(getDrawingPanel());
			drawingScrollPane.setPreferredSize(new Dimension(100, 100));
		}
		return drawingScrollPane;
	}
	
	/**
	 * Returns a java library file chooser
	 */
	private JFileChooser getFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
		}
		
		return fileChooser;
	}

	/**
	 * This method initializes loadMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getLoadMenuItem() {
		if (loadMenuItem == null) {
			loadMenuItem = new JMenuItem();
			loadMenuItem.setText("Load");
			loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
					Event.CTRL_MASK, true));
			loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					// bring up the library filechooser
					getFileChooser();
					int returnVal = fileChooser.showOpenDialog(jFrame);
					
					// if a valid option is chosen
					if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fileChooser.getSelectedFile();
			            
			            if(DEBUGMODE) {
			            	System.out.println("trying to open: " + file.getAbsoluteFile());
			            }
			            
			            try {
			            	// close all the previously loaded data values first
			            	((DrawingBoard)drawingPanel).close();
			            	((PagesBoard)pagesPanel).close();
			            	
			            	// then load into memory
			            	Parser p = new Parser(file.getAbsoluteFile());
			            	
			            	// set up the shared data
			            	if(p != null) {
			            		SharedData.setPages(p.getPages());
			            	}
			            	
			            	// calculate the new panel size
			            	PAGEWIDTH = SharedData.getPages().size()*CELLWIDTH+2*CELLBUFFER+STARTX*2;
			            	PAGEHEIGHT = CELLHEIGHT+STARTY*2;
			            	DRAWWIDTH = NUMCOLS*CELLWIDTH+2*CELLBUFFER+STARTX*2;
			            	DRAWHEIGHT = NUMROWS*CELLHEIGHT+2*CELLBUFFER+STARTY*2;
			            	
			            	// update pages panel
			            	pagesScrollPane.remove(pagesPanel);		
			            	pagesPanel = null;
			            	pagesPanel = getPagesPanel();
			            	pagesScrollPane.setViewportView(pagesPanel);
			            	
			            	// update drawing panel
			            	drawingScrollPane.remove(drawingPanel);
			            	drawingPanel = null;
			            	drawingPanel = getDrawingPanel();
			            	drawingScrollPane.setViewportView(drawingPanel);
			            	
			            	
			            	// have each panel load data from the shared data
			            	((DrawingBoard)drawingPanel).setPages();
			            	((PagesBoard)pagesPanel).setPages();
			            }
			            catch(UnsupportedEncodingException err) {
			            	JOptionPane.showMessageDialog(null, file.getName() + " is not a DXG file.");
			            }
			            catch(NoSuchElementException err) {
			            	JOptionPane.showMessageDialog(null, file.getName() + " is not a DXG file.");
			            }
			        } 
					
					// otherwise load nothing
					else {
			            System.out.println("Open command cancelled by user.");
			        }
				}
			});
		}
		return loadMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCloseMenuItem() {
		if (closeMenuItem == null) {
			closeMenuItem = new JMenuItem();
			closeMenuItem.setText("Close");
			closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
			closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					// clearn everything
					((DrawingBoard)drawingPanel).close();
					((PagesBoard)pagesPanel).close();
					resetToolBar();
					SharedData.setPagesNull();
					SharedData.setPageNull();
					textPane1.setText("");
					textPane2.setText("");
					textPane3.setText("");
					textPane4.setText("");
				}
			});
		}
		return closeMenuItem;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getDetailsPanel(), BorderLayout.SOUTH);
			jPanel.add(getToolBarPanel(), BorderLayout.WEST);
			jPanel.add(getDisplayPanel(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes detailsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDetailsPanel() {
		if (detailsPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			gridLayout.setHgap(2);
			gridLayout.setVgap(0);
			detailsPanel = new JPanel();
			detailsPanel.setBorder(BorderFactory.createTitledBorder(null, "Details", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 70, 213)));
			detailsPanel.setLayout(gridLayout);
			detailsPanel.setPreferredSize(new Dimension(100, 300));
			detailsPanel.add(getDetailsScrollPane1(), null);
			detailsPanel.add(getDetailsScrollPane2(), null);
			detailsPanel.add(getDetailsScrollPane3(), null);
			detailsPanel.add(getDetailsScrollPane4(), null);
		}
		return detailsPanel;
	}

	/**
	 * This method initializes toolBarPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getToolBarPanel() {
		if (toolBarPanel == null) {
			toolBarPanel = new ToolBoard();
			toolBarPanel.setLayout(new BoxLayout(getToolBarPanel(), BoxLayout.Y_AXIS));
			toolBarPanel.setPreferredSize(new Dimension(230, 100));
			toolBarPanel.setBorder(BorderFactory.createTitledBorder(null, "Tool Bar", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 70, 213)));
			toolBarPanel.add(getToolPanel(), null);
		}
		return toolBarPanel;
	}

	/**
	 * This method initializes textPane1	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getTextPane1() {
		if (textPane1 == null) {
			textPane1 = new TextBoard();
			textPane1.setPreferredSize(new Dimension(96, 300));
			textPane1.setEditable(false);
			textPane1.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					// clear everything
					((TextBoard)textPane1).setNull();
				}
			});
		}
		return textPane1;
	}

	/**
	 * This method initializes textPane2	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getTextPane2() {
		if (textPane2 == null) {
			textPane2 = new TextBoard();
			textPane2.setPreferredSize(new Dimension(96, 300));
			textPane2.setEditable(false);
			textPane2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					// clear everything
					((TextBoard)textPane2).setNull();
				}
			});
		}
		return textPane2;
	}

	/**
	 * This method initializes textPane3	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getTextPane3() {
		if (textPane3 == null) {
			textPane3 = new TextBoard();
			textPane3.setPreferredSize(new Dimension(96, 300));
			textPane3.setEditable(false);
			textPane3.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					// clear everything
					((TextBoard)textPane3).setNull();
				}
			});
		}
		return textPane3;
	}

	/**
	 * This method initializes textPane4	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getTextPane4() {
		if (textPane4 == null) {
			textPane4 = new TextBoard();
			textPane4.setPreferredSize(new Dimension(96, 300));
			textPane4.setEditable(false);
			textPane4.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					// clear everything
					((TextBoard)textPane4).setNull();
				}
			});
		}
		return textPane4;
	}

	/**
	 * This method initializes displayPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDisplayPanel() {
		if (displayPanel == null) {
			displayPanel = new JPanel();
			displayPanel.setLayout(new BorderLayout());
			displayPanel.add(getDrawingScrollPane(), BorderLayout.CENTER);
			displayPanel.add(getPagesScrollPane(), BorderLayout.NORTH);
		}
		return displayPanel;
	}

	/**
	 * This method initializes pagesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPagesPanel() {
		if (pagesPanel == null) {
			pagesPanel = new PagesBoard();
			pagesPanel.setLayout(new GridBagLayout());
			pagesPanel.setBounds(new Rectangle(0, 0, PAGEWIDTH, PAGEHEIGHT));
			pagesPanel.setPreferredSize(new Dimension(PAGEWIDTH, PAGEHEIGHT));
			pagesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					// check every viewingpage to see it if was selected
					if(SharedData.getViewingPages() != null) {
						Enumeration en = SharedData.getViewingPages().keys();
						while(en.hasMoreElements()) {
							int key = (Integer)en.nextElement();
							if(SharedData.getViewingPage(key).isClickable() && SharedData.getViewingPage(key).getRect().contains(e.getX(), e.getY())) {
								chooseViewingPage(key);
								break;
							}
						}
					}
				}
			});
			
			// moves the panel when users drag the mouse
			pagesPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				public void mouseDragged(java.awt.event.MouseEvent e) {
					Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
			        ((JPanel)e.getSource()).scrollRectToVisible(r);
				}
			});
		}
		return pagesPanel;
	}

	/**
	 * This method initializes pagesScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getPagesScrollPane() {
		if (pagesScrollPane == null) {
			pagesScrollPane = new JScrollPane();
			pagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			pagesScrollPane.setBorder(BorderFactory.createTitledBorder(null, "Pages", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 70, 213)));
			pagesScrollPane.setPreferredSize(new Dimension(16016, 130));
			pagesScrollPane.setViewportView(getPagesPanel());
		}
		return pagesScrollPane;
	}

	/**
	 * This method initializes toolPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getToolPanel() {
		if (toolPanel == null) {
			pageSearchLabel = new JLabel();
			pageSearchLabel.setText("Page Search");
			pageSearchLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			documentSearchLabel = new JLabel();
			documentSearchLabel.setText("Document Search");
			documentSearchLabel.setPreferredSize(new Dimension(44, 14));
			toolPanel = new JPanel();
			toolPanel.setLayout(new FlowLayout());
			toolPanel.setPreferredSize(new Dimension(230, 30));
			toolPanel.add(getDocSearchPanel(), null);
			toolPanel.add(getPageSearchPanel(), null);
		}
		return toolPanel;
	}

	/**
	 * This method initializes docIDTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getDocIDTextField() {
		if (docIDTextField == null) {
			docIDTextField = new JTextField();
			docIDTextField.setColumns(9);
			docIDTextField.setPreferredSize(new Dimension(70, 19));
		}
		return docIDTextField;
	}

	/**
	 * This method initializes docSearchGo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDocSearchGo() {
		if (docSearchGo == null) {
			docSearchGo = new JButton();
			docSearchGo.setText("Go");
			docSearchGo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					// cut out the end white spaces from the grabbed values
					String docID = docIDTextField.getText().trim();
					String verID = verIDTextField.getText().trim();
					String NID = NIDTextField.getText().trim();
					String NIDHex = NIDHexTextField.getText().trim();
					
					// reset the search if a new id is entered
					if(!SharedData.oldSearchID.equals(docID)) {
						SharedData.docIDEnumerator = null;
						SharedData.docIDNodeEnumerator = null;
					}
					
					// perform search if it's a continuation search or a new search, respectively 
					if(SharedData.oldSearchID.equals(docID) || (SharedData.docIDEnumerator == null && SharedData.docIDNodeEnumerator == null)) {
						SharedData.oldSearchID = docID;
						boolean found = false;
						
						// if we're doing a new search, grab the first page
						if(SharedData.getPages() != null) {
							if(SharedData.docIDEnumerator == null) {
								SharedData.docIDEnumerator = SharedData.getPages().keys();
							}
							
							// iterate through every page
							while(SharedData.docIDEnumerator.hasMoreElements()) {
								if(SharedData.docIDNodeEnumerator == null) {
									SharedData.docIDEnumeratorKey = (Integer)SharedData.docIDEnumerator.nextElement();
									SharedData.docIDNodeEnumerator = SharedData.getPage(SharedData.docIDEnumeratorKey).elements();
								}
								
								// iterate through every node
								while(SharedData.docIDNodeEnumerator.hasMoreElements()) {
									Node node = (Node)SharedData.docIDNodeEnumerator.nextElement();
									int key = node.getSlot();
									
									// if all inputted fields match the node we're looking at or if they're empty
									// we ignore that field then we found a match (assuming not all the fields 
									// are empty)
									if(node instanceof RootNode) {
										if(docID.equals("") || ((RootNode)node).getDocID().equals(docID)) {
											if(verID.equals("") || ((RootNode)node).getVersionID().equals(verID)) {
												if(NID.equals("") || ((RootNode)node).getNID().equals(NID)) {
													if(NIDHex.equals("") || ((RootNode)node).getNIDHex().equals(NIDHex)) {
														if(!(docID.equals("") && verID.equals("") && NID.equals("") && NIDHex.equals(""))) {
															chooseNode(SharedData.docIDEnumeratorKey, key);
															
															found = true;
															break; 
														}
													}
												}
											}
										}
									}
								}
								
								// if we are done with this page, set the node iterator to null
								// so that we can catch it at the start of the search and do
								// a continuation search
								if(!SharedData.docIDNodeEnumerator.hasMoreElements()){
									SharedData.docIDNodeEnumerator = null;
								}
								
								// this break is to allow the user to see a match within the same page. 
								// if we don't break when there are multiple matches in the same page
								// we will skip over all but the last match in that page
								if(found) {
									break;
								}
							}
							
							// if we are done with all the pages, we set the page iterator to null so
							// we can do a new search
							if(!SharedData.docIDEnumerator.hasMoreElements()) {
								SharedData.docIDEnumerator = null;
								JOptionPane.showMessageDialog(null, "No more Documents of same ID.");
							}
						}
					}
					
				}
			});
		}
		return docSearchGo;
	}

	/**
	 * This method initializes detailsScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDetailsScrollPane1() {
		if (detailsScrollPane1 == null) {
			detailsScrollPane1 = new JScrollPane();
			detailsScrollPane1.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
			detailsScrollPane1.setViewportView(getTextPane1());
		}
		return detailsScrollPane1;
	}

	/**
	 * This method initializes detailsScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDetailsScrollPane2() {
		if (detailsScrollPane2 == null) {
			detailsScrollPane2 = new JScrollPane();
			detailsScrollPane2.setViewportView(getTextPane2());
		}
		return detailsScrollPane2;
	}

	/**
	 * This method initializes detailsScrollPane3	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDetailsScrollPane3() {
		if (detailsScrollPane3 == null) {
			detailsScrollPane3 = new JScrollPane();
			detailsScrollPane3.setViewportView(getTextPane3());
		}
		return detailsScrollPane3;
	}

	/**
	 * This method initializes detailsScrollPane4	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDetailsScrollPane4() {
		if (detailsScrollPane4 == null) {
			detailsScrollPane4 = new JScrollPane();
			detailsScrollPane4.setViewportView(getTextPane4());
		}
		return detailsScrollPane4;
	}
	
	/**
	 * 
	 * @return JPopupMenu
	 */
	private JPopupMenu getExternalNodesPopup(Vector<ExternalNode> externalNodes) {
			popupExternalNodes = new JPopupMenu();
			popupExternalNodesMenu = new JMenu("External Nodes");
			JMenuItem externalNodesMenuItem = null;
			int count = 0;
			
			// this creates a new menu item for each external node region clicked on
			// and utilizes the nested class ExternalNodesMenu
			for(ExternalNode pair : externalNodes) {
				externalNodesMenuItem = new JMenuItem("ChildSlot: " + pair.getChildSlot() + "  NID: " + pair.getNID() + "  Page: " + pair.getPage() + "  Slot: " + pair.getNode());
				externalNodesMenuItem.addActionListener(new ExternalNodesMenu(externalNodes, count));

				// add all the new menus
				popupExternalNodesMenu.add(externalNodesMenuItem);
				count++;
			}
			
			popupExternalNodes.add(popupExternalNodesMenu);
		
		return popupExternalNodes;
		
	}
	
	/**
	 * Returns a popup menu for the user to select which window to place the node information into
	 * @return JPopupMenu
	 */
	private JPopupMenu getWindowPopup() {
		if (popupWindows == null) {
			popupWindows = new JPopupMenu();
			popupWindowsMenu = new JMenu("Window");
			popupWindowMenuItem2 = new JMenuItem("2");
			popupWindowMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				
				// set the node in text panel 2
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(SharedData.getSelectedNodeKey() != -1) {
						((TextBoard)textPane2).setNode(SharedData.getCurrentPage(), SharedData.getSelectedNodeKey());
					}
				}
			});
			popupWindowMenuItem3 = new JMenuItem("3");
			popupWindowMenuItem3.addActionListener(new java.awt.event.ActionListener() {
				
				// set node in text panel 3
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(SharedData.getSelectedNodeKey() != -1) {
						((TextBoard)textPane3).setNode(SharedData.getCurrentPage(), SharedData.getSelectedNodeKey());
					}
				}
			});
			popupWindowMenuItem4 = new JMenuItem("4");
			popupWindowMenuItem4.addActionListener(new java.awt.event.ActionListener() {
				
				// set node in text panel 4
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(SharedData.getSelectedNodeKey() != -1) {
						((TextBoard)textPane4).setNode(SharedData.getCurrentPage(), SharedData.getSelectedNodeKey());
					}
				}
			});
			
			// add everything to the menu
			popupWindowsMenu.add(popupWindowMenuItem2);
			popupWindowsMenu.add(popupWindowMenuItem3);
			popupWindowsMenu.add(popupWindowMenuItem4);
			popupWindows.add(popupWindowsMenu);
		}
		
		return popupWindows;
	}

	/**
	 * This method initializes pageNOTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPageNOTextField() {
		if (pageNOTextField == null) {
			pageNOTextField = new JTextField();
			pageNOTextField.setColumns(9);
			pageNOTextField.setPreferredSize(new Dimension(70, 19));
		}
		return pageNOTextField;
	}

	/**
	 * This method initializes pageNOGo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPageNOGo() {
		if (pageNOGo == null) {
			pageNOGo = new JButton();
			pageNOGo.setText("Go");
			pageNOGo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					// grab the value
					String pageNO = pageNOTextField.getText();
					int pageNOInt = -1;
					
					// make sure the input is an integer
					try {
						pageNOInt = Integer.parseInt(pageNO);
					}
					catch(NumberFormatException err) {
						System.out.println("Not a number.");
					}
					
					// if it exists, go to it
					if(SharedData.getPages() != null) {
						if(SharedData.getPages().containsKey(pageNOInt)) {
							
							if(DEBUGMODE) {
								System.out.println("settings page: " + pageNOInt);
							}
							
							chooseNode(pageNOInt, 0);
						}
						else {
							JOptionPane.showMessageDialog(null, "This page number does not exist.");
						}
					}
				}
			});
		}
		return pageNOGo;
	}

	/**
	 * This method initializes docSearchPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDocSearchPanel() {
		if (docSearchPanel == null) {
			docSearchPanel = new JPanel();
			docSearchPanel.setLayout(new BorderLayout());
			docSearchPanel.setPreferredSize(new Dimension(210, 170));
			docSearchPanel.setComponentOrientation(ComponentOrientation.UNKNOWN);
			docSearchPanel.add(documentSearchLabel, BorderLayout.NORTH);
			docSearchPanel.add(getDocSearchSubPanel(), BorderLayout.CENTER);
		}
		return docSearchPanel;
	}

	/**
	 * This method initializes pageSearchPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPageSearchPanel() {
		if (pageSearchPanel == null) {
			pageSearchPanel = new JPanel();
			pageSearchPanel.setLayout(new BorderLayout());
			pageSearchPanel.setPreferredSize(new Dimension(210, 100));
			pageSearchPanel.add(pageSearchLabel, BorderLayout.NORTH);
			pageSearchPanel.add(getPageSearchSubPanel(), BorderLayout.CENTER);
		}
		return pageSearchPanel;
	}

	/**
	 * This method initializes docSearchSubPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDocSearchSubPanel() {
		if (docSearchSubPanel == null) {
			docSearchSubPanel = new JPanel();
			docSearchSubPanel.setLayout(new FlowLayout());
			docSearchSubPanel.setPreferredSize(new Dimension(100, 50));
			docSearchSubPanel.add(getDocSearchSubSubPanel(), null);
			docSearchSubPanel.add(getResetDocumentSearch(), null);
			docSearchSubPanel.add(getDocSearchGo(), null);
		}
		return docSearchSubPanel;
	}

	/**
	 * This method initializes pageSearchSubPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPageSearchSubPanel() {
		if (pageSearchSubPanel == null) {
			pageNOLabel = new JLabel();
			pageNOLabel.setText("PageNo:");
			pageSearchSubPanel = new JPanel();
			pageSearchSubPanel.setLayout(new FlowLayout());
			pageSearchSubPanel.setPreferredSize(new Dimension(100, 25));
			pageSearchSubPanel.add(getPageSearchSubSubPanel(), null);
			pageSearchSubPanel.add(getResetPageSearch(), null);
			pageSearchSubPanel.add(getPageNOGo(), null);
		}
		return pageSearchSubPanel;
	}

	/**
	 * This method initializes verIDTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getVerIDTextField() {
		if (verIDTextField == null) {
			verIDTextField = new JTextField();
			verIDTextField.setPreferredSize(new Dimension(70, 19));
			verIDTextField.setColumns(9);
		}
		return verIDTextField;
	}

	/**
	 * This method initializes docSearchSubSubPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDocSearchSubSubPanel() {
		if (docSearchSubSubPanel == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(4);
			gridLayout1.setColumns(2);
			NIDHexLabel = new JLabel();
			NIDHexLabel.setText("NID: 0x");
			NIDLabel = new JLabel();
			NIDLabel.setText("NID:");
			verIDLabel = new JLabel();
			verIDLabel.setText("VerID: 0x");
			jLabel = new JLabel();
			jLabel.setText("DocID: 0x");
			docSearchSubSubPanel = new JPanel();
			docSearchSubSubPanel.setLayout(gridLayout1);
			docSearchSubSubPanel.setPreferredSize(new Dimension(210, 80));
			docSearchSubSubPanel.add(jLabel, null);
			docSearchSubSubPanel.add(getDocIDTextField(), null);
			docSearchSubSubPanel.add(verIDLabel, null);
			docSearchSubSubPanel.add(getVerIDTextField(), null);
			docSearchSubSubPanel.add(NIDLabel, null);
			docSearchSubSubPanel.add(getNIDTextField(), null);
			docSearchSubSubPanel.add(NIDHexLabel, null);
			docSearchSubSubPanel.add(getNIDHexTextField(), null);
		}
		return docSearchSubSubPanel;
	}

	/**
	 * This method initializes pageSearchSubSubPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPageSearchSubSubPanel() {
		if (pageSearchSubSubPanel == null) {
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(1);
			pageSearchSubSubPanel = new JPanel();
			pageSearchSubSubPanel.setLayout(gridLayout2);
			pageSearchSubSubPanel.setPreferredSize(new Dimension(210, 19));
			pageSearchSubSubPanel.add(pageNOLabel, null);
			pageSearchSubSubPanel.add(getPageNOTextField(), null);
		}
		return pageSearchSubSubPanel;
	}

	/**
	 * This method initializes NIDTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNIDTextField() {
		if (NIDTextField == null) {
			NIDTextField = new JTextField();
			NIDTextField.setPreferredSize(new Dimension(70, 19));
			NIDTextField.setColumns(9);
		}
		return NIDTextField;
	}

	/**
	 * This method initializes NIDHexTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNIDHexTextField() {
		if (NIDHexTextField == null) {
			NIDHexTextField = new JTextField();
			NIDHexTextField.setPreferredSize(new Dimension(70, 19));
			NIDHexTextField.setColumns(9);
		}
		return NIDHexTextField;
	}

	/**
	 * This method initializes resetDocumentSearch	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getResetDocumentSearch() {
		if (resetDocumentSearch == null) {
			resetDocumentSearch = new JButton();
			resetDocumentSearch.setText("Reset");
			resetDocumentSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					resetDocumentSearch();
				}
			});
		}
		return resetDocumentSearch;
	}

	/**
	 * This method initializes resetPageSearch	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getResetPageSearch() {
		if (resetPageSearch == null) {
			resetPageSearch = new JButton();
			resetPageSearch.setText("Reset");
			resetPageSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					resetPageSearch();
				}
			});
		}
		return resetPageSearch;
	}
}
