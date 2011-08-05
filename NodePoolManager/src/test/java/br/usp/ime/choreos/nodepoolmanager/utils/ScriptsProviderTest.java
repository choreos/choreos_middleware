package br.usp.ime.choreos.nodepoolmanager.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ScriptsProviderTest {

    @Test
    public void chefScript() throws Exception {
        String result = new ScriptsProvider().chefStartupScript("chef/testkey.txt");

        File f = new File(ClassLoader.getSystemResource("chef/test_startup_script_result.txt").getFile());
        String expected = FileUtils.readFileToString(f);
        
        assertEquals(expected, result);
    }

    @Test(expected = Exception.class)
    public void chefScriptInvalidKeyFile() throws Exception {
        new ScriptsProvider().chefStartupScript("not_found");
    }
}
