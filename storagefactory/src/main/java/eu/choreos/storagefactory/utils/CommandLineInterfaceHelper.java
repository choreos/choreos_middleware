package eu.choreos.storagefactory.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineInterfaceHelper {

	public static String runLocalCommand(String command) {
		String commandReturn = "";

		try {
			Process process = Runtime.getRuntime().exec(command);

			String line;
			BufferedReader bri = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			while ((line = bri.readLine()) != null) {
				commandReturn.concat(line);
			}
		} catch (IOException e) {
			System.out.println("[Storage Node] - Error while executing command"
					+ '\n' + command);
			e.printStackTrace();
		}

		return commandReturn;
	}

}
