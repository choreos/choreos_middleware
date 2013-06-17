/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chef.impl;

import org.ow2.choreos.chef.KnifeClient;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.utils.CommandLine;
import org.ow2.choreos.utils.CommandLineException;

public class KnifeClientImpl implements KnifeClient {

    private static final String EXIT_STATUS_ERROR_MESSAGE = "Knife exit status > 0";

    private ChefScripts scripts;
    private boolean verbose;

    /**
     * 
     * @param knifeConfigFile
     *            The path to the knife.rb file
     * @param verbose
     *            prints knife outputs if <code>verbose</code> is
     *            <code>true</code>
     */
    public KnifeClientImpl(String knifeConfigFile, boolean verbose) {

	this.scripts = new ChefScripts(knifeConfigFile);
	this.verbose = verbose;
    }

    /**
     * 
     * @param knifeConfigFile
     *            The path to the knife.rb file
     * @param verbose
     *            prints knife outputs if <code>verbose</code> is
     *            <code>true</code>
     */
    public KnifeClientImpl(String knifeConfigFile) {
	this(knifeConfigFile, false);
    }

    @Override
    public String delete(String clientName) throws KnifeException {

	String command = scripts.getKnifeClientDelete(clientName);
	try {
	    return CommandLine.run(command, verbose);
	} catch (CommandLineException e) {
	    throw new KnifeException(EXIT_STATUS_ERROR_MESSAGE, command);
	}
    }

}
