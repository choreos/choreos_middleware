package org.ow2.choreos.chef.impl;

import org.ow2.choreos.chef.KnifeCookbook;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.utils.CommandLine;

public class KnifeCookbookImpl implements KnifeCookbook {

	private ChefScripts scripts;
	private String chefRepo;
	private boolean verbose;
	
	/**
	 * 
	 * @param knifeConfigFile The pat to the knife.rb file
	 * @param chefRepo the path to the folder containing the cookbooks folders
	 * @param verbose prints knife outputs if <code>verbose</code> is <code>true</code>
	 */
	public KnifeCookbookImpl(String knifeConfigFile, String chefRepo, boolean verbose) {
		
		this.scripts = new ChefScripts(knifeConfigFile);
		this.chefRepo = chefRepo;
		this.verbose = verbose;
	}
	
	/**
	 * 
	 * @param knifeConfigFile The pat to the knife.rb file
	 */
	public KnifeCookbookImpl(String knifeConfigFile, String chefRepo) {
		this(knifeConfigFile, chefRepo, false);
	}
	
	@Override
	public String upload(String cookbookName,
			String cookbookParentFolder) throws KnifeException {

		String command = scripts.getKnifeCookbookUpload(cookbookName, cookbookParentFolder);
		return CommandLine.run(command, verbose);
	}
	
	@Override
	public String upload(String cookbookName) throws KnifeException {
		return this.upload(cookbookName, this.chefRepo);
	}

	@Override
	public String delete(String cookbookName) throws KnifeException {

		String command = scripts.getKnifeCookbookDelete(cookbookName);
		return CommandLine.run(command, verbose);
	}

	@Override
	public String list() throws KnifeException {

		String command = scripts.getKnifeCookbooksList();
		return CommandLine.run(command, verbose);
	}

}
