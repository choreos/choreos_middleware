package eu.choreos.platform.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellHandler {

	public String runLocalCommand(String command) throws CommandRuntimeException {
		return runLocalCommand(command, false);
	}

	public String runLocalCommand(String command, boolean verbose) throws CommandRuntimeException {
		return runLocalCommand(command, ".", verbose);
	}

	public String runLocalCommand(String command, String workingDirectory) throws CommandRuntimeException {
		return runLocalCommand(command, workingDirectory, false);
	}

	public String runLocalCommand(String command,
			String workingDirectory, boolean verbose) throws CommandRuntimeException {
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
			p.waitFor();
			if (p.exitValue()!=0) throw new CommandRuntimeException("Process exited with exit value = "+p.exitValue()) ;
		} catch (IOException e) {
			System.out.println("Error while executing " + command);
			e.printStackTrace();
		} catch (InterruptedException e) {
		}

		return commandReturn.replace("%20", " ");
	}

}