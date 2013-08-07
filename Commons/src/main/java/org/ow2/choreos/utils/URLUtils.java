package org.ow2.choreos.utils;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class URLUtils {
    public static String extractIpFromURL(String URL) {
	InetAddress address;
	try {
	    address = InetAddress.getByName(new URL(URL).getHost());
	} catch (UnknownHostException e) {
	    throw new IllegalArgumentException();
	} catch (MalformedURLException e) {
	    throw new IllegalArgumentException();
	}
	return address.getHostAddress();
    }
}
