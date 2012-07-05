package org.ow2.choreos.npm.utils;

import com.sun.tools.xjc.XJCFacade;

public class CodeGenerator {
    public static void main(String[] args) throws Throwable {
        XJCFacade.main(new String[] { "-d", "src/main/java", "src/main/resources/NodePoolManager.xsd" });
        System.out.println("Done!");
    }
}
