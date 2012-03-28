package eu.choreos.gmond.reloader;



public class GmondReloader {
	
	private String commandRestartMonitor;

	public GmondReloader(){
		commandRestartMonitor = "/etc/init.d/ganglia-monitor restart";
	}
	
	public boolean reload(){
		Process procReturned = runCommand(commandRestartMonitor);
		try {
			procReturned.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (procReturned.exitValue() == 0)
			return true;
		return false;
	}

	private static Process runCommand(String cmd) {
		Runtime runtime = Runtime.getRuntime();
		Process proc = null;
		try {
			proc = runtime.exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proc;
	}
}
