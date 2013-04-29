package org.ow2.choreos.services.datamodel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URIInfoRetriever {
	
	private static final Pattern URI_REGEX = Pattern.compile("^(http://)?(.*?)(:\\d+)?/.*$");
	private static final Pattern IP_REGEX = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
	
	private String uri;
	
	public URIInfoRetriever(String uri) {
		this.uri = uri;
	}
	
	private String getHostnameOrIp() {
		
		Matcher matcher = URI_REGEX.matcher(this.uri);
		if (matcher.matches()) {
			return matcher.group(2);
		} else {
			return null;
		}
	}
	
	private boolean isIp(String ip) {
		
		Matcher matcher = IP_REGEX.matcher(ip);
		return matcher.matches();
	}
	
	public String getHostname() {
		
		String hostname = getHostnameOrIp();
		if (!isIp(hostname)) {
			return hostname;
		} else {
			return null;
		}
	}
	
	public String getIp() {

		String ip = getHostnameOrIp();
		if (isIp(ip)) {
			return ip;
		} else {
			return null;
		}
	}
	

}
