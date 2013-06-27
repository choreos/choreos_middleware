/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class CommandLine {

    private static Logger logger = Logger.getLogger(CommandLine.class);

    public static String run(String command) throws CommandLineException {
        return run(command, false);
    }

    public static String run(String command, boolean verbose) throws CommandLineException {
        return run(command, ".", verbose);
    }

    public static String run(String command, String workingDirectory) throws CommandLineException {
        return run(command, workingDirectory, false);
    }

    public static String run(String command, String workingDirectory, boolean verbose) throws CommandLineException {
        String commandReturn = "";
        File wd = new File(workingDirectory);

        try {
            if (verbose) {
                logger.info(command);
            }
            Process p = Runtime.getRuntime().exec(command, null, wd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                commandReturn = commandReturn + line + '\n';
                if (verbose) {
                    logger.info(commandReturn);
                }
            }

            try {
                int status = p.waitFor();
                if (status > 0) {
                    throw new CommandLineException("Command failed: " + command);
                }
            } catch (InterruptedException e) {
                throw new CommandLineException("Command failed: " + command);
            }

        } catch (IOException e) {
            logger.error("Error while executing " + command);
            throw new CommandLineException("Command failed: " + command);
        }

        return commandReturn;
    }

}
