package org.ow2.choreos.chef;

import org.ow2.choreos.utils.CommandLine;

public class KnifeImpl implements Knife {

	private ChefScripts scripts;
	
	/**
	 * 
	 * @param knifeConfigFile The knife.rb file
	 */
	public KnifeImpl(String knifeConfigFile) {
		
		scripts = new ChefScripts(knifeConfigFile);
	}
	
	@Override
	public String bootstrap(String pKeyFile, String ip, String user)
			throws KnifeException {

		String command = scripts.getKnifeBootstrap(pKeyFile, ip, user);
		return CommandLine.run(command);
	}

	@Override
	public String runListAdd(String nodeName, String cookbook, String recipe)
			throws KnifeException {

		String command = scripts.getKnifeRunListAdd(nodeName, cookbook, recipe);
		return CommandLine.run(command);
	}

	@Override
	public String nodeList() throws KnifeException {

		String command = scripts.getKnifeNodeList();
		return CommandLine.run(command);
	}

	@Override
	public String nodeShow(String nodeName) throws KnifeException {

		String command = scripts.getKnifeNodeShow(nodeName);
		return CommandLine.run(command);
	}

	@Override
	public String nodeDelete(String nodeName) throws KnifeException {

		String command = scripts.getKnifeNodeDelete(nodeName);
		return CommandLine.run(command);
	}

	@Override
	public String clientDelete(String clientName) throws KnifeException {

		String command = scripts.getKnifeClientDelete(clientName);
		return CommandLine.run(command);
	}

	@Override
	public String cookbookUpload(String cookbookName,
			String cookbookParentFolder) throws KnifeException {

		String command = scripts.getKnifeCookbookUpload(cookbookName, cookbookParentFolder);
		return CommandLine.run(command);
	}

	@Override
	public String cookbookDelete(String cookbookName) throws KnifeException {

		String command = scripts.getKnifeCookbookDelete(cookbookName);
		return CommandLine.run(command);
	}

	@Override
	public String cookbooksList() throws KnifeException {

		String command = scripts.getKnifeCookbooksList();
		return CommandLine.run(command);
	}

}
