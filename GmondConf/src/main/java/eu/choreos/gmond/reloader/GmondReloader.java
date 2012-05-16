package eu.choreos.gmond.reloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GmondReloader {

	private String commandRestartMonitor;

	private Runtime runtime;
	@SuppressWarnings("unused") //Used for testing purposes
	private Process proc;

	private String commandReloadMonitor;

	public GmondReloader() {
		commandRestartMonitor = "/etc/init.d/ganglia-monitor restart";
		commandReloadMonitor = "kill -1 $( cat /var/run/gmond.pid )";
		runtime = null;
	}

	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}

	public boolean reload() {
		Process procReturned;
		if(gmondIsRunning())
			procReturned = runCommand(commandReloadMonitor);
		else
			procReturned = runCommand(commandRestartMonitor);
		try {
			procReturned.waitFor();
		} catch (InterruptedException e) {
		}
		if (procReturned.exitValue() == 0)
			return true;
		return false;
	}

	private boolean gmondIsRunning() {
		
		Process process = runCommand("ps -ef | grep gmond");
		
		try {
			process.waitFor();
		} catch (InterruptedException e1) {
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		
		try {
			while ((line = in.readLine()) != null) {
				if(line.contains("/usr/sbin/gmond")) return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
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
