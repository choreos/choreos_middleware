package br.usp.ime.choreos.nodepoolmanager.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ScriptsProviderTest {

    @Test
    public void chefScript() throws Exception {
        String testKeyFile = ClassLoader.getSystemResource("chef/testkey.txt").getFile();
        String result = new ScriptsProvider().getChefBootstrapScript(testKeyFile);

        String scriptFile = ClassLoader.getSystemResource("chef/test_startup_script_result.txt").getFile();
        String expected = FileUtils.readFileToString(new File(scriptFile));

        assertEquals(expected, result);
    }

    @Test(expected = Exception.class)
    public void chefScriptInvalidKeyFile() throws Exception {
        new ScriptsProvider().getChefBootstrapScript("not_found");
    }
}
