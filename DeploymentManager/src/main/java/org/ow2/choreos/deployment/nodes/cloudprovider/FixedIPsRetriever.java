package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ow2.choreos.deployment.Configuration;

class FixedIPsRetriever {
	
	private static final String IP_REGEX = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
	private static final String IPS_REGEX = "(" + IP_REGEX + "; )*" + IP_REGEX;
	
	public List<String> retrieveIPs() throws IllegalStateException {
		
		String ipsConfig = Configuration.get("FIXED_VM_IPS");
		if (!validate(ipsConfig)) {
			throw new IllegalStateException();
		}
		
		Pattern pat = Pattern.compile(IP_REGEX);
		Matcher matcher = pat.matcher(ipsConfig);
		List<String> ips = new ArrayList<String>();
		while (matcher.find()) {
		    String ip = matcher.group(0);
		    ips.add(ip);
		}
		
		return ips;
	}

	private boolean validate(String ipsConfig) {
		
		if (ipsConfig == null) {
			return false;
		}
		
		Pattern pat = Pattern.compile(IPS_REGEX);
		Matcher matcher = pat.matcher(ipsConfig);
		
		return matcher.matches();
	}

}
