/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.utils;

import com.sun.tools.xjc.XJCFacade;

public class CodeGenerator {
    public static void main(String[] args) throws Throwable {
	XJCFacade.main(new String[] { "-d", "src/main/java", "src/main/resources/NodePoolManager.xsd" });
	System.out.println("Done!");
    }
}
