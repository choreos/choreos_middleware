

public class SignalSender {
	
	public native int sendSignalByPID(int pid, int sig);
	public native int sendSignalByProcessName(String name, int sig);

	static {
		System.load("SignalSender");
	}

	public static void main(String[] args) {

		SignalSender sigsender = new SignalSender();

		// sendsignal <signal> -n name
		// sendsignal 
	}
}
