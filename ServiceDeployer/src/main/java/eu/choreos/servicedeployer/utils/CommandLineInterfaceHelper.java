package eu.choreos.servicedeployer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineInterfaceHelper {


	public String runLocalCommand(String command){
		return runLocalCommand(command, ".");
	}
	
	public String runLocalCommand(String command, String workingDirectory) {
		String commandReturn = "";

		try {
			File wd = new File(workingDirectory);
			
			Process p = Runtime.getRuntime().exec(command,null,wd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				commandReturn = commandReturn + line + '\n';
			}
		} catch (IOException e) {
			System.out.println("[Storage Node] - Error while executing command"
					+ '\n' + command);
			e.printStackTrace();
		}

		return commandReturn;
	}


}
