package org.ow2.choreos.chef.impl;

import java.io.File;

import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeClient;
import org.ow2.choreos.chef.KnifeCookbook;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.KnifeNode;
import org.ow2.choreos.utils.CommandLine;

public class KnifeImpl implements Knife {

	private ChefScripts scripts;
	private KnifeNode knifeNode;
	private KnifeCookbook knifeCookbook;
	private KnifeClient knifeClient;
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 */
	public KnifeImpl(String knifeConfigFile) {
		
		File knifeConfig = new File(knifeConfigFile);
		if (!knifeConfig.exists()) {
			throw new IllegalArgumentException(knifeConfigFile + " does not exist!");
		}
		
		this.scripts = new ChefScripts(knifeConfigFile);
		this.knifeNode = new KnifeNodeImpl(knifeConfigFile);
		this.knifeCookbook = new KnifeCookbookImpl(knifeConfigFile);
		this.knifeClient = new KnifeClientImpl(knifeConfigFile);
	}
	
	@Override
	public KnifeNode node() {

		return this.knifeNode;
	}

	@Override
	public KnifeCookbook cookbook() {

		return this.knifeCookbook;
	}

	@Override
	public KnifeClient client() {

		return this.knifeClient;
	}

	@Override
	public String bootstrap(String pKeyFile, String ip, String user)
			throws KnifeException {

		String command = scripts.getKnifeBootstrap(pKeyFile, ip, user);
		return CommandLine.run(command);
	}


}
