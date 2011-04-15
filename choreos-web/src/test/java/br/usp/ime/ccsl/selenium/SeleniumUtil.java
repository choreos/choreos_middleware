package br.usp.ime.ccsl.selenium;

import static org.junit.Assert.assertTrue;

import java.net.BindException;

import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumUtil {

    public static final String TIME_OUT = "30000";

    private static SeleniumServer server;
    private static DefaultSelenium client;

    // Should be run in a @Before, @BeforeClass or initialized in an attribute
    // declaration
    public static DefaultSelenium startSelenium() {
	if (client != null)
	    return client;

	RemoteControlConfiguration conf = new RemoteControlConfiguration();
	conf.setSingleWindow(true);
	try {
	    server = new SeleniumServer(conf);
	    server.start();
	} catch (BindException e) {
	    System.out.println("Couldn't bind to :4444. If selenium server is already running, ignore this warning.");
	} catch (Exception e) {
	    e.printStackTrace();
	}

	client = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/");
	client.start();
	return client;
    }

    public static void setFormValue(String id, String value) {
	client.getEval("window.document.getElementById('" + id + "').value = '" + value + "';");
    }

    public static void assertTextPresent(String text) {
	assertTrue("Text \"" + text + "\" not present", client.isTextPresent(text));
    }

    @Override
    public void finalize() {
	client.stop();
	server.stop();
	try {
	    super.finalize();
	} catch (Throwable e) {
	    e.printStackTrace();
	}
    }
}