package org.ow2.choreos.chef.impl;

import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.KnifeNode;
import org.ow2.choreos.utils.CommandLine;

public class KnifeNodeImpl implements KnifeNode {

	private ChefScripts scripts;
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 */
	public KnifeNodeImpl(String knifeConfigFile) {
		
		scripts = new ChefScripts(knifeConfigFile);
	}
	
	@Override
	public String runListAdd(String nodeName, String cookbook, String recipe)
			throws KnifeException {

		String command = scripts.getKnifeRunListAdd(nodeName, cookbook, recipe);
		return CommandLine.run(command);
	}

	@Override
	public String list() throws KnifeException {

		String command = scripts.getKnifeNodeList();
		return CommandLine.run(command);
	}

	@Override
	public String show(String nodeName) throws KnifeException {

		String command = scripts.getKnifeNodeShow(nodeName);
		return CommandLine.run(command);
	}

	@Override
	public String delete(String nodeName) throws KnifeException {

		String command = scripts.getKnifeNodeDelete(nodeName);
		return CommandLine.run(command);
	}

	@Override
	public String runListRemove(String nodeName, String cookbook, String recipe)
			throws KnifeException {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public String create(String nodeName) throws KnifeException {

		String command = scripts.getKnifeNodeCreate(nodeName);
		return CommandLine.run(command);
	}

}
