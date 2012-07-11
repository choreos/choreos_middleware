package org.ow2.choreos.chef.impl;

import org.ow2.choreos.chef.KnifeClient;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.utils.CommandLine;

public class KnifeClientImpl implements KnifeClient {

	private ChefScripts scripts;
	private boolean verbose;
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 * @param verbose prints knife outputs if <code>verbose</code> is <code>true</code>
	 */
	public KnifeClientImpl(String knifeConfigFile, boolean verbose) {
		
		this.scripts = new ChefScripts(knifeConfigFile);
		this.verbose = verbose;
	}
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 * @param verbose prints knife outputs if <code>verbose</code> is <code>true</code>
	 */
	public KnifeClientImpl(String knifeConfigFile) {
		this(knifeConfigFile, false);
	}
	
	@Override
	public String delete(String clientName) throws KnifeException {

		String command = scripts.getKnifeClientDelete(clientName);
		return CommandLine.run(command, verbose);
	}

}
