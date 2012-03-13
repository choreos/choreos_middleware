package eu.choreos.monitoring;

public class HelloWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println((new HelloWorld()).getMessage());
	}
	
	public String getMessage(){
		return "Hello World";
	}

}
