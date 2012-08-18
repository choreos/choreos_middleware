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
		/*try {
			if (gmondIsRunning())
				runtime.runLocalCommand(commandReloadMonitor);
			else
				runtime.runLocalCommand(commandRestartMonitor);
			
			return true;
		} catch (CommandRuntimeException e) {
			return false;
		}*/
		
		SignalSender s = new SignalSender();
		String ss = "gmond";
		return (s.sendSignalByProcessName(ss, 1) == 0);
	}

	@SuppressWarnings("unused")
	private boolean gmondIsRunning() throws CommandRuntimeException {
		
		// TODO: verify if gmond is running with JNI interface too
		
		String stdout = runtime.runLocalCommand("ps -ef | grep gmond");
		return stdout.contains("/usr/sbin/gmond");
	}
}
