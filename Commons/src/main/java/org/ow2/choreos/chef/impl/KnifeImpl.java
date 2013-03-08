package org.ow2.choreos.chef.impl;

import java.io.File;
import java.util.List;

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
	
	private String chefRepo;
	private boolean verbose;
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 * @param chefRepo the path to the folder containing the cookbooks folders
	 * @param verbose prints knife outputs if <code>verbose</code> is <code>true</code>
	 */
	public KnifeImpl(String knifeConfigFile, String chefRepo, boolean verbose) {
		
		File knifeConfig = new File(knifeConfigFile);
		if (!knifeConfig.exists()) {
			throw new IllegalArgumentException(knifeConfigFile + " does not exist!");
		}
		
		this.scripts = new ChefScripts(knifeConfigFile);
		this.knifeNode = new KnifeNodeImpl(knifeConfigFile, verbose);
		this.knifeCookbook = new KnifeCookbookImpl(knifeConfigFile, chefRepo, verbose);
		this.knifeClient = new KnifeClientImpl(knifeConfigFile, verbose);
		this.chefRepo = chefRepo;
		this.verbose = verbose;
	}
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 * @param chefRepo the path to the folder containing the cookbooks folders
	 */
	public KnifeImpl(String knifeConfigFile, String chefRepo) {
		this(knifeConfigFile, chefRepo, false);
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
	public String bootstrap(String ip, String user, String pKeyFile, List<String> recipes)
			throws KnifeException {

		String command = scripts.getKnifeBootstrap(pKeyFile, ip, user);
		return CommandLine.run(command, chefRepo, verbose);
	}


}
