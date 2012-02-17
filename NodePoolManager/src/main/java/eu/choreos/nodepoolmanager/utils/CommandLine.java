package eu.choreos.nodepoolmanager.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLine {

	public static String runLocalCommand(String command) {
		String commandReturn = "";

		try {
			Process p = Runtime.getRuntime().exec(command);
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
