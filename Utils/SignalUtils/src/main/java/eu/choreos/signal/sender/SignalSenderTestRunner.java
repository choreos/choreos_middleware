package eu.choreos.signal.sender;

public class SignalSenderTestRunner {

	public static void main(String[] args) {
		System.out.println(new SignalSender().sendSignalByProcessName("ls",1));		
	}
	
}
