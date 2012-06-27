package org.ow2.choreos.chef.impl;

import org.ow2.choreos.chef.KnifeClient;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.utils.CommandLine;

public class KnifeClientImpl implements KnifeClient {

	private ChefScripts scripts;
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 */
	public KnifeClientImpl(String knifeConfigFile) {
		
		scripts = new ChefScripts(knifeConfigFile);
	}
	
	@Override
	public String delete(String clientName) throws KnifeException {

		String command = scripts.getKnifeClientDelete(clientName);
		return CommandLine.run(command);
	}

}
