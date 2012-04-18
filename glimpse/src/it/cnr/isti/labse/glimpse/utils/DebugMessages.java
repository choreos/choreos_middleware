package it.cnr.isti.labse.glimpse.utils;

/**
 * This class provides print function for debug
 * 
 * @author Antonello Calabr&ograve;
 * @version 3.2 
 *
 */
public class DebugMessages {

	public static int lastMessageLength = 0;
	
	/**
	 * Print the string "className : message " without break line.
	 * Can be used with method {@link #ok()}
	 * 
	 * @param callerClass the name of the class that is calling method
	 * @param messageToPrint the message to print
	 */
	public static void print(String callerClass, String messageToPrint)
	{
		String message = callerClass + ": " + messageToPrint;
		System.out.print(message);
		lastMessageLength = message.length();
	}
	/**
	 * Print the string "className : message " with break line.
	 * Can be used with method {@link #ok()}
	 * 
	 * @param callerClass the name of the class that is calling method
	 * @param messageToPrint the message to print
	 */
	public static void println(String callerClass, String messageToPrint)
	{
		String message = callerClass + ": " + messageToPrint;
		System.out.println(message);
	}
	/**
	 * Print the OK text
	 */
	public static void ok()
	{
		int tab = 10 - (lastMessageLength / 8);
		String add="";
		for(int i = 0; i< tab;i++) {
			add +="\t"; 
		}
		System.out.println(add + "[ OK ]");
	}
	/**
	 * 
	 * Print a line <br />
	 */
	public static void line() {
		System.out.println("--------------------------------------------------------------------------------------");	
	}
	
	/**
	 * Print asterisks
	 */
	public static void asterisks() {
		System.out.println("**************************************************************************************");	
	}
}
