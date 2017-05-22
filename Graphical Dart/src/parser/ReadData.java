/**
 * 
 */
package parser;

import java.io.*;
import java.util.*;

/**
 * @author tomkit07
 * ReadData does the I/O work
 */
public class ReadData {
    private static BufferedReader inputStream = null;
    private File fileName = null;
    
    /*
     * ================
     * = Constructors =
     * ================
     */
    
    /**
     * Default constructor
     */
    ReadData() {
    	
    }
    
    /** 
     * Overloaded constructor
     */
    ReadData(File fileName) {
    	this.fileName = fileName;
    }
    
    /*
     * ==================
     * = Public Methods =
     * ==================
     */
    
    /**
     * Returns a vector of strings which each string representing one
     * line in the original Dart DXG dump
     * @return Vector<String>
     */
    public Vector<String> getData() {
    	Vector<String> strings = new Vector<String>();
    	String readInLine = "";
    	
    	try {
            inputStream = new BufferedReader(new FileReader(fileName));

            // !! in the future read in the first line to make sure it's a DXG file
            // '#DXG' (as opposed to checking after reading it all into
            // memory)
            while((readInLine = inputStream.readLine()) != null) {
                strings.add(readInLine);
            }
        } 
        catch(FileNotFoundException e) {
        	System.out.println("FileNotFoundException in copyLines()");
        }
        catch(IOException e) {
        	System.out.println("IOException in copyLines()");
        }
        finally {
        	try {
        		if (inputStream != null) {
                    inputStream.close();
                }
        	}
        	catch(IOException e) {
        		System.out.println("IOException in finally clause.");
        	}
        }
        
        return strings;
    } 
}