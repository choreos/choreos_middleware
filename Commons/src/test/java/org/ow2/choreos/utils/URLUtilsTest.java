package org.ow2.choreos.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class URLUtilsTest {

    @Test
    public void test() {
	String URL = URLUtils.extractIpFromURL("http://127.0.0.1:8180/admin/esbendpoint");
	String URLWithLocalhost = URLUtils.extractIpFromURL("http://localhost:8180/admin/esbendpoint");

	assertTrue(URLWithLocalhost.equals("localhost") || URLWithLocalhost.equals("127.0.0.1"));
	assertEquals("127.0.0.1", URL);
	assertEquals("127.0.0.1", URLUtils.extractIpFromURL("http://localhost/admin/esbendpoint"));
	assertEquals("127.0.0.1", URLUtils.extractIpFromURL("http://127.0.0.1/admin/esbendpoint"));
    }
}
