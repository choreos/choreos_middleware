
//package eu.choreos.signal.sender;

public class SignalSender {
	
	public native int sendSignalByPID(int pid, int sig);
	public native int sendSignalByProcessName(String name, int sig);

	static {
		System.loadLibrary("MiddlewareSignalSender");
	}
	//public static void main(String[] args) {
	//	System.out.println(new SignalSender().sendSignalByProcessName("ls",1));		
	//}
}
