package br.usp.ime.choreos.nodepoolmanager.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class ScriptsProvider {

    public String getChefStartupScript(String keyFileName) throws IOException {
        URL scriptFile = ClassLoader.getSystemResource("chef/startup_script.sh");
        String command = FileUtils.readFileToString(new File(scriptFile.getFile()));

        String pkey = FileUtils.readFileToString(new File(keyFileName));

        return command.replace("$pkey", pkey);
    }

}
