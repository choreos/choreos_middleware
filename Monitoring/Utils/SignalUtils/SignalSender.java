
public class SignalSender {
	
	public native int sendSignalByPID(int pid, int sig);
	public native int sendSignalByProcessName(String name, int sig);

	static {
		System.loadLibrary("SignalSender");
	}
}
