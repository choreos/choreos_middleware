package eu.choreos.enactment.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLine {

	public static String runLocalCommand(String command) {
		return runLocalCommand(command, false);
	}
	
	public static String runLocalCommand(String command, boolean verbose) {
		return runLocalCommand(command, ".", verbose);
	}
	
	public static String runLocalCommand(String command, String workingDirectory) {
		return runLocalCommand(command, workingDirectory, false);
	}
	
	public static String runLocalCommand(String command, String workingDirectory, boolean verbose) {
		String commandReturn = "";
		File wd = new File(workingDirectory);

		try {
			Process p = Runtime.getRuntime().exec(command, null, wd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				commandReturn = commandReturn + line + '\n';
			}
		} catch (IOException e) {
			System.out.println("Error while executing " + command);
			e.printStackTrace();
		}

		return commandReturn;
	}

}
