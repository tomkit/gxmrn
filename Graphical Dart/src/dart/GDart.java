package dart;

//
// The is the main() class executable. There will be notes presented here that won't appear elsewhere.
// The typical style of coding is the following:
// 
// Packages;
// 
// Libraries;
// 
// /**
//  * Class Description
//  */
// ClassName() {
// 	  Global Fields;       // comments for fields appear on the same line and in lowercase
//    
//    /*
//     * ================
//     * = Constructors =
//     * ================
//     */
//
//    /**
//     * Method Description
//     * JavaDoc Notes
//     */
//    Constructors() {    
//       Initializations;  // comments for fields appear on the same line and in lowercase
//    }
//    
//    /*
//     * ==================
//     * = Public Methods =
//     * ==================
//     */
//
//    /**
//     * Method Description
//     * JavaDoc Notes
//     */
//    Public Methods() {
//       Local Fields;
//       
//       // code comments start in lowercase and before a chunk of code
//       Chunk of Code;
//
//       // !! some comments start with two "!!" which means that
//       // there is something done in the following chunk of code that could
//       // potentially lead to problems
//       Chunk of Code;
//    } 
//    
//    /*
//     * ===================
//     * = Private Methods =
//     * ===================
//     */
//
//    /**
//     * Method Description
//     * JavaDoc Notes
//     */
//    Private Methods() {
//       Local Fields;
//
//       // code comments start in lowercase and before a chunk of code
//       Chunk of code;
//    }
// } 
//
//



import static dart.Parameter.*;	// this is a static import that allows you to use static members of the class
								// without prefixing the member with a "Class.something", i.e. you can use
								// something directly

/**
 * @author tomkit07
 * This is the program's main driver. This class can potentially include command arguments
 */
public class GDart {
	
	/* 
	 * ================
	 * = Constructors =
	 * ================
	 */
	
	/**
	 * Default constructor
	 */
	GDart() {
		
	}
	
	/* 
	 * ========
	 * = Main =
	 * ========
	 */

	/**
	 * Main
	 * @param args 
	 */
	public static void main(String[] args) {
		int argsIndex = 0;
		
		// parameter handling
		// parameter /DEBUG	-	[on/off (default)]
		// parameter /RES	-   [width] [height]
		// parameter /HELP	-	[]
		
		while(argsIndex < args.length) {	
			// "/help" case
			if(args[argsIndex].equalsIgnoreCase("/help")) {
				helpParam();
			}
			// "/res" case
			else if(args[argsIndex].equalsIgnoreCase("/res")) {		
				try {
					resParam(args[++argsIndex], args[++argsIndex]);
				}
				catch(IndexOutOfBoundsException e) {
					System.out.println("Missing resolution parameters: /res [width & height]");
					System.exit(1);
				}
			}
			// "/debug" case
			else if(args[argsIndex].equalsIgnoreCase("/debug")) {
				try {
					debugParam(args[++argsIndex]);
				}
				catch(IndexOutOfBoundsException e) {
					System.out.println("Missing debug parameter: /debug [on | off]");
					System.exit(1);
				}
			}	
			// incorrect parameters
			else {		
				System.out.println("Usage: java dart.GDart [/debug [on|off] | /res [width&height] | /help]");
				System.exit(1);				
			}
			argsIndex++;
		}
		
		// check parameters for valid values, e.g. resolution shouldn't be too small
		try {
			Parameter.assertParams();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		// create a new instance of the gui and run it
		GUI gui = new GUI();
		gui.run();
	}
	
	/*
	 * ===========
	 * = Private =
	 * ===========
	 */
	
	/**
	 * Debug parameters
	 */
	private static void debugParam(String debugOption) {
		if(debugOption.equalsIgnoreCase("on")) {
			DEBUGMODE = true;
		}
		else if(debugOption.equalsIgnoreCase("off")) {
			// default already off: do nothing
		}
		else {
			System.out.println("Debug parameter invalid: [on | off]");
			System.exit(1);
		}
	}
	
	/**
	 * Resolution Parameters
	 */
	private static void resParam(String width, String height) {
		int resWidth = 0;
		int resHeight = 0;
		
		// check to make sure the inputted parameter values are numbers
		try {
			resWidth = Integer.parseInt(width);
			resHeight = Integer.parseInt(height);
		}
		catch(NumberFormatException e) {
			System.out.println("Resolution parameters are not numbers");
			System.exit(1);
		}
		
		// set the global parameters
		RESWIDTH = resWidth;
		RESHEIGHT = resHeight;
	}
	
	/**
	 * Help Parameters
	 */
	private static void helpParam() {
		System.out.println("Usage: java dart.GDart [/debug [on | off] | /res [width & height] | /help]");
		System.exit(1);
	}
}
