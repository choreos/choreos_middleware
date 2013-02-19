package org.ow2.choreos.deployment.services.bus;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.utils.CommandLine;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * Access EasyESB by using the command-line client as an external program.
 * 
 * TODO: use the EasyESB API instead the current approach. 
 * 
 * @author leonardo
 *
 */
public class EasyESBNodeImpl implements EasyESBNode {

	private static final String EASY_ESB_CLIENT_FOLDER = "easyesb-choreos-cli-1.0";
	private static final String EASY_ESB_CLIENT = "client.jar"; // I did not uploaded it to the repo
	
	private Logger logger = Logger.getLogger(EasyESBNodeImpl.class);

	private final String clientPath;
	private String adminEndpoint;
	
	public EasyESBNodeImpl(String adminEndpoint) {
		this.adminEndpoint = adminEndpoint;
		this.clientPath = this.retrieveClientPath();
	}
	
	private String retrieveClientPath() {
		
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL eabsyEsb = loader.getResource(EASY_ESB_CLIENT_FOLDER);
		File client = FileUtils.getFile(eabsyEsb.getPath(), "bin", EASY_ESB_CLIENT);
		return client.getAbsolutePath();
	}
	
	@Override
	public void bindService(String serviceUrl, String serviceWsdl) throws IOException {

		// "java -jar client command1 command2" is not working...
		// there should be some "[]" in some place,
		// but I think maybe it is better go directly to the EasyESB API usage
		final String bindCommand = "java -jar $client -c $adminEndpoint -b $url $wsdl";
		String command = bindCommand.replace("$client", this.clientPath)
				.replace("$adminEndpoint", this.adminEndpoint)
				.replace("$url", serviceUrl).replace("$wsdl", serviceWsdl);
		String output = CommandLine.run(command);
		logger.info("Respose to the command " + command + ": " + output);
	}

	@Override
	public void exposeService(String serviceNamespace, String serviceName,
			String endpointName) throws IOException {

		final String exposeCommand = "java -jar $client -c $adminEndpoint -e $namespace $localpart $endpoint";
		String command = exposeCommand.replace("$client", this.clientPath)
				.replace("$adminEndpoint", this.adminEndpoint)
				.replace("$namespace", serviceNamespace)
				.replace("$localpart", serviceName)
				.replace("$endpoint", endpointName);
		String output = CommandLine.run(command);
		logger.info("Respose to the command " + command + ": " + output);
	}

	@Override
	public String getAdminEndpoint() {
		return this.adminEndpoint;
	}

	
	public static void main(String[] args) throws IOException {
		
		LogConfigurator.configLog();
		EasyESBNodeImpl node = new EasyESBNodeImpl("http://192.168.56.101:8180/services/adminExternalEndpoint");
		node.bindService("http://192.168.56.101:1234/airline", "http://192.168.56.101:1234/airline?wsdl");
	}
}
