package eu.choreos.gmond.reloader;

/*
 * Native interface to reload ganglia
 */
public class SignalSender {
	
	public native int sendSignalByPID(int pid, int sig);
	public native int sendSignalByProcessName(String name, int sig);

	/*
	 * loads the java native interface libSignalSender.so
	 * This shared library have the implementation
	 * of the both method above. The .so file must added 
	 * to LD_LIBRARY_PATH
	 */
	static {
		System.loadLibrary("SignalSender");
	}
}