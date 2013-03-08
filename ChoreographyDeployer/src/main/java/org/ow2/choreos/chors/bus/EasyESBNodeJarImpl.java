package org.ow2.choreos.chors.bus;

import java.io.File;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.utils.CommandLine;

import esstar.petalslink.com.service.management._1_0.ManagementException;

/**
 * Access EasyESB by using the command-line client as an external program.
 * 
 * @author leonardo
 *
 */
public class EasyESBNodeJarImpl implements EasyESBNode {

	private static final String EASY_ESB_CLIENT_FOLDER = "easyesb-choreos-cli-1.0";
	private static final String EASY_ESB_CLIENT = "client.jar"; 
	
	private Logger logger = Logger.getLogger(EasyESBNodeImpl.class);

	private final String clientPath;
	private String adminEndpoint;
	private String nodeIp;
	
	public EasyESBNodeJarImpl(String adminEndpoint) {
		this.adminEndpoint = adminEndpoint;
		this.clientPath = this.retrieveClientPath();
		this.nodeIp = this.extractIpFromAdminEndpoint();
	}
	
	private String extractIpFromAdminEndpoint() {

		if (this.adminEndpoint.contains("localhost:8180")) {
			return "localhost";
		} else {
			Pattern pat = Pattern.compile("(\\d{1,3}.){3}\\d{1,3}");
			Matcher m = pat.matcher(this.adminEndpoint);
			m.find();
			String ip = m.group(0);
			return ip;
		}
	}

	private String retrieveClientPath() {
		
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL eabsyEsb = loader.getResource(EASY_ESB_CLIENT_FOLDER);
		File client = FileUtils.getFile(eabsyEsb.getPath(), "bin", EASY_ESB_CLIENT);
		return client.getAbsolutePath();
	}
	
	@Override
	public String getAdminEndpoint() {
		return this.adminEndpoint;
	}

	@Override
	public String proxifyService(String serviceUrl, String serviceWsdl)
			throws ManagementException {

		final String exposeCommand = "java -jar $client -c $adminEndpoint -pr $url $wsdl";
		String command = exposeCommand.replace("$client", this.clientPath)
				.replace("$adminEndpoint", this.adminEndpoint)
				.replace("$url", serviceUrl)
				.replace("$wsdl", serviceWsdl);
		String output = CommandLine.run(command);
		logger.debug("Respose to the command " + command + ": " + output);
		
		if (!output.contains("Connection ok") || output.contains("Error while executing command pr")) {
			throw new ManagementException("pr output: " + output);
		}
		
		return getProfixiedUri();
	}
	
	private String getProfixiedUri() {
		
		String portTypeName = "";
		String proxifiedUri = "http://" + this.nodeIp + ":8180/services/" + portTypeName + "PortClientProxyEndpoint";
		return proxifiedUri;
	}
}
