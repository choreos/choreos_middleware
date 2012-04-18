package it.cnr.isti.labse.glimpse.utils;

import it.cnr.isti.labse.glimpse.consumer.GlimpseAbstractConsumer;
import it.cnr.isti.labse.glimpse.probe.GlimpseAbstractProbe;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class should be used only for debug purpose<br />
 * because uses deprecated methods.
 * Helps to read text from files and to generate<br />
 * Properties object
 *  
 * @author Antonello Calabr&ograve;
 * @version 3.2
 * 
 */
public class Manager
{
	/**
	 * @param fileName the absolute path of the file to parse
	 * @return a {@link Properties} object
	 */
	@SuppressWarnings("deprecation")
	public static Properties Read(String fileName)
	{
		Properties readedProps = new Properties();
		
		File file = new File(fileName);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		
		try	{
			fis = new FileInputStream(file);
		
			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			
			// dis.available() returns 0 if the file does not have more lines.
			String property = "";
			String key = "";
			String value = "";
		 
			while (dis.available() != 0) {
			  // this statement reads the line from the file and print it to
				 // the console.
	    	property = dis.readLine().trim();
	    	if (property.length() > 0)
	    	{
		    	key = property.substring(0,property.indexOf("="));
		    	value = property.substring(property.indexOf("=")+1,property.length());
				
		    	readedProps.put(key.trim(), value.trim());
	    	}
	    	}

			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readedProps;
	}
	 
	/**
	 * It reads text from file and provides it on string
	 * 	 * 
	 * @param filePath the file to read path
	 * @return a String containing all the file text
	 */
	@SuppressWarnings("deprecation")
	public static String ReadTextFromFile(String filePath)
	{
		File file = new File(filePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		StringBuilder strB = new StringBuilder();
				
		try	{
			fis = new FileInputStream(file);
		
			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
				 
			while (dis.available() != 0) {
			  // this statement reads the line from the file and print it to
				 // the console.
	    	strB.append(dis.readLine());
	    	
	    	}
			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strB.toString();
	}
	
	/**
	 * This method generate a {@link Properties} object file.
	 * 
	 * @param javaNamingFactoryInitial 
	 * @param javaNamingProviderUrl
	 * @param javaNamingSecurityPrincipal
	 * @param javaNamingSecurityCredential
	 * @param connectionFactoryNames 
	 * @param topicServiceTopic the channel where to connect to send the monitoring request
	 * @param debug
	 * @param consumerName the name of the consumer that is sending the request
	 * @return a {@link Properties} object
	 */
	public static Properties createConsumerSettingsPropertiesObject(
				String javaNamingFactoryInitial, String javaNamingProviderUrl,
				String javaNamingSecurityPrincipal,
				String javaNamingSecurityCredential, String connectionFactoryNames,
				String topicServiceTopic, boolean debug, String consumerName) {
			if (debug)
				DebugMessages.print(GlimpseAbstractConsumer.class.getSimpleName(),
				"Creating Properties object ");
			Properties settings = new Properties();
			settings.setProperty("java.naming.factory.initial",javaNamingFactoryInitial);
			settings.setProperty("java.naming.provider.url", javaNamingProviderUrl);
			settings.setProperty("java.naming.security.principal", javaNamingSecurityPrincipal);
			settings.setProperty("java.naming.security.credential", javaNamingSecurityCredential);
			settings.setProperty("connectionFactoryNames", connectionFactoryNames);
			settings.setProperty("topic.serviceTopic", topicServiceTopic);
			settings.setProperty("consumerName", consumerName);
			if (debug) {
				DebugMessages.ok(); 
				DebugMessages.line(); }
			return settings;
		}
	
	/**
	 * This method generate a {@link Properties} object file that can be used to<br />
	 * setup a {@link GlimpseAbstractProbe}.
	 * 
	 * @param javaNamingFactoryInitial 
	 * @param javaNamingProviderUrl
	 * @param javaNamingSecurityPrincipal
	 * @param javaNamingSecurityCredential
	 * @param connectionFactoryNames 
	 * @param topicProbeTopic the channel where to connect to send the events
	 * @param debug
	 * @param probeName the name of the probe that is sending the events
	 * @param probeChannel the channel where to send events
	 * @return a {@link Properties} object
	 */
	public static Properties createProbeSettingsPropertiesObject(
			String javaNamingFactoryInitial, String javaNamingProviderUrl,
			String javaNamingSecurityPrincipal,
			String javaNamingSecurityCredential, String connectionFactoryNames,
			String topicProbeTopic, boolean debug,
			String probeName, String probeChannel) {
		if (debug)
			DebugMessages.print(GlimpseAbstractProbe.class.getSimpleName(),
			"Creating Properties object ");
		Properties settings = new Properties();
		settings.setProperty("java.naming.factory.initial",javaNamingFactoryInitial);
		settings.setProperty("java.naming.provider.url", javaNamingProviderUrl);
		settings.setProperty("java.naming.security.principal", javaNamingSecurityPrincipal);
		settings.setProperty("java.naming.security.credential", javaNamingSecurityCredential);
		settings.setProperty("connectionFactoryNames", connectionFactoryNames);
		settings.setProperty("topic.probeTopic", topicProbeTopic);
		settings.setProperty("probeName", probeName);
		settings.setProperty("probeChannel", probeChannel);
		if (debug) {
			DebugMessages.ok(); 
			DebugMessages.line(); }
		return settings;
	}
}
