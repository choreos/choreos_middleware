package org.ow2.choreos.chef.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chef.ChefNode;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.KnifeNode;
import org.ow2.choreos.utils.CommandLine;
import org.ow2.choreos.utils.CommandLineException;

public class KnifeNodeImpl implements KnifeNode {
	
	private static final String EXIT_STATUS_ERROR_MESSAGE = "Knife exit status > 0";

	private ChefScripts scripts;
	private boolean verbose;
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 */
	public KnifeNodeImpl(String knifeConfigFile) {
		
		this(knifeConfigFile, false);
	}
	
	/**
	 * 
	 * @param knifeConfigFile The path to the knife.rb file
	 * @param verbose
	 */
	public KnifeNodeImpl(String knifeConfigFile, boolean verbose) {
		
		this.scripts = new ChefScripts(knifeConfigFile);
		this.verbose = verbose;
	}
	
	@Override
	public String runListAdd(String nodeName, String recipeFullName)
			throws KnifeException {
		
		String command = scripts.getKnifeRunListAdd(nodeName, recipeFullName);
		synchronized(KnifeNodeImpl.class) { 
			try {
				return CommandLine.run(command, verbose);
			} catch (CommandLineException e) {
				throw new KnifeException(EXIT_STATUS_ERROR_MESSAGE, command);
			}
		}
	}
	
	@Override
	public List<String> list() throws KnifeException {

		String command = scripts.getKnifeNodeList();
		String result;
		try {
			result = CommandLine.run(command, verbose);
		} catch (CommandLineException e) {
			throw new KnifeException(EXIT_STATUS_ERROR_MESSAGE, command);
		}
		
		List<String> nodes = new ArrayList<String>();
		for (String node: result.split("\n"))
			nodes.add(node.trim());
		
		return nodes;
	}

	@Override
	public ChefNode show(String nodeName) throws KnifeException {

		String command = scripts.getKnifeNodeShow(nodeName);
		String output;
		try {
			output = CommandLine.run(command, verbose);
		} catch (CommandLineException e1) {
			throw new KnifeException(EXIT_STATUS_ERROR_MESSAGE, command);
		}
		ShowNodeParser parser = new ShowNodeParser();
		ChefNode node = null;
		try {
			node = parser.parse(output);
		} catch (IOException e) {
			throw new KnifeException("Could not parse output", command);
		}
		if (node == null) {
			throw new KnifeException("Could not parse output", command);
		}
		return node;
	}

	@Override
	public String delete(String nodeName) throws KnifeException {

		String command = scripts.getKnifeNodeDelete(nodeName);
		try {
			return CommandLine.run(command, verbose);
		} catch (CommandLineException e) {
			throw new KnifeException(EXIT_STATUS_ERROR_MESSAGE, command);
		}
	}

	@Override
	public String runListRemove(String nodeName, String recipeFullName)
			throws KnifeException {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public String create(String nodeName) throws KnifeException {

		String command = scripts.getKnifeNodeCreate(nodeName);
		try {
			return CommandLine.run(command, verbose);
		} catch (CommandLineException e) {
			throw new KnifeException(EXIT_STATUS_ERROR_MESSAGE, command);
		}
	}

}
