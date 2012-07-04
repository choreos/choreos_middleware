package eu.choreos.gmond.reloader;

import eu.choreos.platform.utils.CommandRuntimeException;
import eu.choreos.platform.utils.ShellHandler;

public class GmondReloader {

	private String commandRestartMonitor;

	private ShellHandler runtime;
	
	private String commandReloadMonitor;

	public GmondReloader() {
		commandRestartMonitor = "/etc/init.d/ganglia-monitor restart";
		commandReloadMonitor = "kill -1 $( cat /var/run/gmond.pid )";
		runtime = new ShellHandler();
	}

	public void setRuntime(ShellHandler runtime) {
		this.runtime = runtime;
	}

	
	public boolean reload() {
		try {
			if (gmondIsRunning())
				runtime.runLocalCommand(commandReloadMonitor);
			else
				runtime.runLocalCommand(commandRestartMonitor);
			
			return true;
		} catch (CommandRuntimeException e) {
			return false;
		}
	}

	private boolean gmondIsRunning() throws CommandRuntimeException {
		String stdout = runtime.runLocalCommand("ps -ef | grep gmond");
		return stdout.contains("/usr/sbin/gmond");
	}
}
