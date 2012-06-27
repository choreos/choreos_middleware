package org.ow2.choreos.chef.impl;

import org.ow2.choreos.chef.KnifeCookbook;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.utils.CommandLine;

public class KnifeCookbookImpl implements KnifeCookbook {

	private ChefScripts scripts;
	
	/**
	 * 
	 * @param knifeConfigFile The pat to the knife.rb file
	 */
	public KnifeCookbookImpl(String knifeConfigFile) {
		
		scripts = new ChefScripts(knifeConfigFile);
	}
	
	@Override
	public String upload(String cookbookName,
			String cookbookParentFolder) throws KnifeException {

		String command = scripts.getKnifeCookbookUpload(cookbookName, cookbookParentFolder);
		return CommandLine.run(command);
	}

	@Override
	public String delete(String cookbookName) throws KnifeException {

		String command = scripts.getKnifeCookbookDelete(cookbookName);
		return CommandLine.run(command);
	}

	@Override
	public String list() throws KnifeException {

		String command = scripts.getKnifeCookbooksList();
		return CommandLine.run(command);
	}

}
