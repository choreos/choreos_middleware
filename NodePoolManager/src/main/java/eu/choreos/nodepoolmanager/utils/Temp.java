package eu.choreos.nodepoolmanager.utils;

import java.io.IOException;

public class Temp {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		String ip = "ec2-174-129-57-53.compute-1.amazonaws.com";
		String user = "ubuntu";
		String key = "/home/leonardo/.ssh/leofl.pem";
		SshUtil ssh = new SshUtil(ip, user, key);
		String command = ScriptsProvider.getChefName();
		String chefClientName = ssh.runCommand(command);
		System.out.println(chefClientName);
	}

}
