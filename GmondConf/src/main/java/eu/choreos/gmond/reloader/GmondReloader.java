package eu.choreos.gmond.reloader;

public class GmondReloader {

	private String commandRestartMonitor;

	private Runtime runtime;
	private Process proc;

	public GmondReloader() {
		commandRestartMonitor = "/etc/init.d/ganglia-monitor restart";
		runtime = null;
	}

	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}

	public boolean reload() {
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

	private Process runCommand(String cmd) {
		Runtime runtime = null;
		if (this.runtime == null)
			runtime = Runtime.getRuntime();
		else
			runtime = this.runtime;

		Process proc = null;
		try {
			proc = runtime.exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proc;
	}

	public void setProc(Process proc) {
		this.proc = proc;
	}
}
