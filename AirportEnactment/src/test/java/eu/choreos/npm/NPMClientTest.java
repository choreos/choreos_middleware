package eu.choreos.npm;

import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class NPMClientTest {

	@Test
	public void shouldReturnIp() {

		NodePoolManager npm = new NodePoolManagerClient();
		String ip = npm.createNode();
		System.out.println(ip);
		assertTrue(isIp(ip));
	}

	private boolean isIp(String ip) {
		Pattern ipPattern = Pattern.compile("([0-9]{1,3}\\.){1,3}[0-9]{1,3}");
		Matcher matcher = ipPattern.matcher(ip);
		
		return matcher.matches();
	}

}
