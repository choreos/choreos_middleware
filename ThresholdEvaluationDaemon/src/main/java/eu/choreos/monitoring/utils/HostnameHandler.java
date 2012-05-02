package eu.choreos.monitoring.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;


public class HostnameHandler {

	public static String getScriptCommand() {

		String command;
		URL location = (new HostnameHandler()).getClass().getClassLoader()
				.getResource("hostname.sh");

		File tmpFile = new File("/tmp/hostname.sh");
		try {
			if (tmpFile.exists())
				FileUtils.forceDelete(tmpFile);
			FileUtils.copyURLToFile(location, tmpFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (tmpFile.exists()) {
			tmpFile.setExecutable(true);
			command = "/bin/bash "
					+ (tmpFile.getAbsolutePath()).replace("%20", " ");
		} else {
			command = "/bin/bash /tmp/hostname.sh";
		}

		return command;
	}

	public static String getHostName() {
		return ShellHandler.runLocalCommand(getScriptCommand()).replace("\n",
				"");
	}
}
