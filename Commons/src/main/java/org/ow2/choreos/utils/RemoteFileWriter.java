/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.jcraft.jsch.JSchException;

public class RemoteFileWriter {

    /**
     * Writes a file in a remote location.
     * 
     * Side effects: ' are replace by "
     * 
     * @param text
     * @param filePath
     * @param ssh
     * @throws SshCommandFailed
     */
    public void writeFile(String text, String filePath, SshUtil ssh) throws SshCommandFailed {

	text = text.replaceAll("'", "\""); // dirty hack

	Reader r = new StringReader(text);
	BufferedReader bf = new BufferedReader(r);
	String line;
	try {
	    line = bf.readLine();
	} catch (IOException e) {
	    throw new IllegalArgumentException("Invalid string: " + text);
	}
	while (line != null) {
	    String command = "echo '" + line + "' >> " + filePath;
	    try {
		ssh.runCommand(command);
	    } catch (JSchException e) {
		throw new SshCommandFailed(command);
	    }
	    try {
		line = bf.readLine();
	    } catch (IOException e) {
		throw new IllegalArgumentException("Invalid string: " + text);
	    }
	}

    }
}
