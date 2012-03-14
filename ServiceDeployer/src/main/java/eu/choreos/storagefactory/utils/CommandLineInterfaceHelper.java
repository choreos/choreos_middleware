package eu.choreos.storagefactory.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineInterfaceHelper {

	public String runLocalCommand(String command) {
		String commandReturn = "";

		try {
			Process p = Runtime.getRuntime().exec(command);
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
