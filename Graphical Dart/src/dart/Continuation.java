package dart;

import static dart.Parameter.*;
import datastructures.*;

import java.util.*;
import javax.swing.*;

/**
 * @author tomkit07
 * This class deals with the algorithms to find continuation nodes and external nodes
 */
public class Continuation extends SharedData {
	
	/*
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	Continuation() {
		
	}
	
	/*
	 * ==================
	 * = Public Methods =
	 * ==================
	 */
	
	/**
	 * Finds the external node location for the given node
	 * @param node
	 * @return Vector<Pair>
	 */
	public static Vector<ExternalNode> findExternal(Node node) {
		String docID = "";
		String versionID = "";
		String rootNID = "";
		String nodeNID = "";
		Hashtable<Integer, String> NIDS = new Hashtable<Integer, String>();			
		Vector<String> targetNIDS = new Vector<String>();
		Vector<ExternalNode> externalNodes = new Vector<ExternalNode>();
		
		// obtain this node's relevant information for finding external nodes
		docID = getDocID(node);
		versionID = getVersionID(node);
		rootNID = getRootNID(node);
		
		// obtain the NIDs of the external nodes of this node
		NIDS = getExternalNIDS(node);
		
		if(DEBUGMODE) {
			Enumeration e = NIDS.elements();
			while(e.hasMoreElements()) {
			//for(String s : NIDS) {
				String s = (String)e.nextElement();
				System.out.println("external source NIDS:");
				System.out.println(s);
			}
		}
		
		// iterate through all pages
		Enumeration e = getPages().keys();
		while(e.hasMoreElements()) {
			int pageNo = (Integer)e.nextElement();
			
			if(DEBUGMODE) {
				System.out.println("page: " + pageNo);
			}
			
			// iterate through all nodes
			Hashtable<Integer, Node> page = getPage(pageNo);
			Enumeration en = page.keys();
			while(en.hasMoreElements()) {
				int slot = (Integer)en.nextElement();
				Node n = page.get(slot);
				
				if(n instanceof RootNode) {
					
					if(DEBUGMODE) {
						System.out.println("looking at docID: " + ((RootNode)n).getDocID());
						System.out.println("source docID: " + docID);
					}
					
					// iterate through all the external NIDs, format them into pretty print 
					// NIDs and use that to find the location of the external node					
					//for(String nid : NIDS) {
					Enumeration nidKeyEnum = NIDS.keys();
					while(nidKeyEnum.hasMoreElements()) {
						
						int nidKey = (Integer)nidKeyEnum.nextElement();
						String nid = NIDS.get(nidKey);
						String newTargetNID = "";
						
						// !! the value 5 is based on observation of NIDs when they include the 0.
						// and when they don't. this might not be fault proof
						
						// create the pretty print NID
						if(((RootNode)n).getNID().length() < 5) {
							newTargetNID = "0." + rootNID;
							newTargetNID += appendNID(node);
							newTargetNID += nid.substring(1);
						}
						else {
							newTargetNID = rootNID;
							newTargetNID += appendNID(node);
							newTargetNID += nid.substring(1);

							if(DEBUGMODE) {
								System.out.println("newTargetNID: " + newTargetNID);
							}
						}
						
						// when everything matches (document id, version id, nid)
						// we found out external node
						if(((RootNode)n).getDocID().equals(docID) &&
						   ((RootNode)n).getVersionID().equals(versionID) &&
						   ((RootNode)n).getNID().equals(newTargetNID)) {
							ExternalNode extNode = getStartOfContinuationNode((RootNode)n, pageNo);
							if(extNode != null) {
								extNode.setPage(pageNo);
								extNode.setNID(nid);
								extNode.setChildSlot(nidKey);
								//p.setChildSlot(n.getSlot());
								
								// add the match to structure to return
								externalNodes.add(extNode);
							}
						}
					}
				}
			}
		}
		
		// if we didn't find any external nodes, return null
		// so that it can be caught (instead of having a semantic
		// error
		if(externalNodes.size() == 0) {
			externalNodes = null;
		}
		
		return externalNodes;
	}
	
	/**
	 * The driver for finding a continuation node
	 * @param node 
	 * @param direction 
	 * @return int[]
	 */
	public static ExternalNode findContinuation(Node node, DIRECTION direction) {
		String NID = null;								
		String targetNID = null;							
		String docID = "";
		String versionID = "";
		String rootNID = "";
		
		// get the source document id, version id, and nid
		// to find a target root node that matches
		docID = getDocID(node);
		versionID = getVersionID(node);
		rootNID = getRootNID(node);
		
		if(DEBUGMODE) {
			System.out.println("source docID: " + docID);
		}
		
		// we need to look at the first or last nid digit of this node's children
		// based on the direction we're going. if we're going backwards in the
		// continuation chain, then we have to look at the first valid child's NID
		// and vice versa if we're going forwards 
		if(direction == Parameter.DIRECTION.FORWARD) {
			NID = getLastNID(node);
		}
		else if(direction == Parameter.DIRECTION.BACKWARD) {
			NID = getFirstNID(node);
		}
		
		// iterate through each page
		Enumeration e = getPages().keys();
		while(e.hasMoreElements()) {
			int pagesKey = (Integer)e.nextElement();
			
			if(DEBUGMODE) {
				System.out.println("page: " + pagesKey);
			}
			
			// iterate through each node
			Hashtable<Integer, Node> page = getPages().get(pagesKey);
			Enumeration en = page.keys();
			while(en.hasMoreElements()) {
				int pageKey = (Integer)en.nextElement();
				Node n = page.get(pageKey);
				
				// look at only root nodes
				if(n instanceof RootNode) {
					
					if(DEBUGMODE) {
						System.out.println("looking at docID: " + ((RootNode)n).getDocID());
						System.out.println("source docID: " + docID);
					}
					
					// when everything matches (document id, version id, nid)
					if(((RootNode)n).getDocID().equals(docID) &&
					   ((RootNode)n).getVersionID().equals(versionID) &&
					   ((RootNode)n).getNID().equals(rootNID)) {
						
						if(DEBUGMODE) {
							System.out.println("found a matching docID");
						}
						
						// obtain the target nid from the target node
						targetNID = getTargetNID(page, n, direction, NID);
					
						// if we're going forward, we need the source nid incremented and
						// the target nid to match
						if(direction == Parameter.DIRECTION.FORWARD) {
							
							if(DEBUGMODE) {
								System.out.println("looking for NID: " + nextNID(NID));
							}
							
							if(nextNID(NID).equals(targetNID)) {
								
								if(DEBUGMODE) {
									System.out.println("found key: " + pagesKey);
								}
								
								return new ExternalNode(pagesKey, pageKey);
							}
						}
						
						// if we're going backwards, weneed the source nid decremented
						// and the target nid to match
						else {
							
							if(DEBUGMODE) {
								System.out.println("looking for NID: " + previousNID(NID));
							}
							
							if(previousNID(NID).equals(targetNID)) {
								
								if(DEBUGMODE) {
									System.out.println("found key: " + pagesKey);
								}
								
								return new ExternalNode(pagesKey, pageKey);
							}
						}
					}
				}
			}
		}
		
		if(DEBUGMODE) {
			System.out.println("not found");
		}
		
		// when a continuation node is not found, it is probably not in the data dumped.
		// this can happen when only a select number of pages are dumped in dart (in the future)
		JOptionPane.showMessageDialog(null, "The continuation node is not in this DXG dump file.");
		return null;
	}
	
	/*
	 * ===================
	 * = Private Methods =
	 * ===================
	 */
	
	/**
	 * Returns a NID obtained by appending its parents NID and successively
	 * appending its children's NIDs
	 */
	private static String appendNID(Node node) {
		String NID = "";
		
		// go up the tree as long as there is a parent and it's not a root node (which
		// has no NID)
		if(node.getParentNode() != null && !(node.getParentNode() instanceof RootNode)) {
			NID += appendNID(node.getParentNode());
		}
		
		// as long as a NID exists, append them together
		if(node.getNID() != null) {
			
			if(DEBUGMODE) {
				System.out.println("recursing through NID: " + node.getNID());
			}
						
			NID += node.getNID().substring(1);
		}
		
		return NID;
	}
	
	/**
	 * Returns a document ID in the region's root node of a given node
	 */
	private static String getDocID(Node node) {
		
		// go up the tree until we hit the root node
		while(node.getParentSlot() != -1) {
			node = getNode(node.getParentSlot());
			if(node instanceof RootNode) {
				break;
			}
		}
		
		if(DEBUGMODE) {
			System.out.println("document ID: " + ((RootNode)node).getDocID());
		}
		
		return ((RootNode)node).getDocID();
	}
	
	/**
	 * Returns a version ID in the region's root node from a given node
	 */
	private static String getVersionID(Node node) {
		
		// go up the tree until we hit the root node
		while(node.getParentSlot() != -1) {
			node = getNode(node.getParentSlot());
			if(node instanceof RootNode) {
				break;
			}
		}
		
		if(DEBUGMODE) {
			System.out.println("version ID: " + ((RootNode)node).getVersionID());
		}
		
		return ((RootNode)node).getVersionID();
	}
	
	/**
	 * Returns a NID in the region's root from a given node
	 */
	private static String getRootNID(Node node) {
		
		// go up the tree until we hit the root node
		while(node.getParentSlot() != -1) {
			node = getNode(node.getParentSlot());
			if(node instanceof RootNode) {
				break;
			}
		}
		
		if(DEBUGMODE) {
			System.out.println("root NID: " + ((RootNode)node).getNID());
		}
		
		return ((RootNode)node).getNID();
	}
	
	/**
	 * Gets the last valid child NID from a node
	 */
	private static String getLastNID(Node node) {
		String NID = null;
		
		// as long as the node has valid children keep assigning
		// it to the return NID
		if(node.hasChildren()) {
			for(Child child : node.getChildren()) {	
				
				// valid child
				if(child instanceof InlineChild) {
					NID = ((InlineChild)child).getNID();
					
				}
				
				// valid child as long as the nodechild is not an 
				// external or continuation node (which won't have
				// a NID
				else if(child instanceof NodeChild) {
					if(!((NodeChild)child).getNID().equals("NULL")) {
						NID = ((NodeChild)child).getNID();
					}
				}
			}
		}
		
		if(DEBUGMODE) {
			System.out.println("source NID: " + NID);
		}
		
		return NID;
	}
	
	/**
	 * Gets the first valid NID in a node
	 */
	private static String getFirstNID(Node node) {
		String NID = null;
		
		// obtain the very first valid child node's NID
		if(node.hasChildren()) {
			for(Child child : node.getChildren()) {
				
				// !! i think this is removable
				if(child instanceof InlineChild) {
					NID = ((InlineChild)child).getNID();
				}
				
				// if it is a node child with an existing
				// NID, then this is our child
				else if(child instanceof NodeChild) {
					NID = ((NodeChild)child).getNID();
					if(!NID.equals("NULL")) {
						break;
					}
				}
			}
		}
		
		if(DEBUGMODE) {
			System.out.println("looking at NID: " + NID);
		}
		
		return NID;
	}
	
	/**
	 * Returns a vector of NIDs that are external nodes
	 */
	private static Hashtable<Integer, String> getExternalNIDS(Node node) {
		Hashtable<Integer, String> NIDS = new Hashtable<Integer, String>();
		int childNum = 0;
		
		// iterate through all the children nodes
		if(node.hasChildren()) {
			for(Child child : node.getChildren()) {
				childNum++;
				// if it is a node child and it is external
				// then add it to the vector
				if(child instanceof NodeChild) {
					if(((NodeChild)child).isExternal()) {
						NIDS.put(childNum, ((NodeChild)child).getNID());						
					}
				}
			}
		}
		
		return NIDS;
	}
	
	/**
	 * Returns the node that is the start of a continuation chain
	 */
	private static ExternalNode getStartOfContinuationNode(RootNode node, int pageNo) {
		String NID = null;
		Hashtable<Integer, Node> page = SharedData.getPage(pageNo);
		int slot = -1;
		
		// get the first child's slot
		if(node.hasChildren()) {
			for(Child child : node.getChildren()) {
				slot = ((RootChild)child).getSlot();
				break;
			}
			
			Node n = page.get(slot);
			
			// only check nodes who are in the middle of continuation nodes or non continuation nodes
			if((n.getFlags().length() == 4 && n.getFlags().charAt(2) == '4') || n.getFlags().equals(("0"))) {
				return new ExternalNode(-1, n.getSlot());
			}
		}
		
		if(DEBUGMODE) {
			System.out.println("looking at NID: " + NID);
		}
		
		return null;
	}
	
	/**
	 * Gets the target NID from a given root node
	 */
	private static String getTargetNID(Hashtable<Integer, Node> page, Node node, DIRECTION direction, String sourceNID) {
		String NID = null;
		
		if(node instanceof TextNode || node instanceof ElementNode) {
			if(direction == Parameter.DIRECTION.FORWARD) {
				return getFirstNID(node);
			}
			else if(direction == Parameter.DIRECTION.BACKWARD){
				return getLastNID(node);
			}
		}
		else if(node.hasChildren()) {
			for(Node n : node.getChildren()) {
				if(n.getType().equals("NO") || n.getType().equals("")) {
					NID = getTargetNID(page, page.get(n.getSlot()), direction, sourceNID);
				}
			}
		}
		
		return NID;
	}
	
	/**
	 * Returns the next NID given a specific NID (not in hex; in pretty print)
	 */
	private static String nextNID(String NID) {
		String nextNID = "";
		int intValue = -1;
		String stringValue = "";
		
		// grab the value from after the decimal in the nid.
		// make it an integer and increment it to find the 
		// target nid
		stringValue = NID.substring(2);
		intValue = Integer.parseInt(stringValue);
		intValue++;
		
		// prepend the "0."
		nextNID = "0." + intValue;
		
		return nextNID;
	}
	
	/**
	 * Returns the next NID given a specific NID (not in hex; in pretty print)
	 */
	private static String previousNID(String NID) {
		String previousNID = "";
		int intValue = -1;
		String stringValue = "";
		
		// grab the value from after the decimal in the nid.
		// make it an integer and decrement it to find the 
		// target nid
		stringValue = NID.substring(2);
		intValue = Integer.parseInt(stringValue);
		intValue--;
		
		// prepend the "0."
		previousNID = "0." + intValue;
		
		return previousNID;
	}
}
