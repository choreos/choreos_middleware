 /*
  * GLIMPSE: A generic and flexible monitoring infrastructure.
  * For further information: http://labsewiki.isti.cnr.it/labse/tools/glimpse/public/main
  * 
  * Copyright (C) 2011  Software Engineering Laboratory - ISTI CNR - Pisa - Italy
  * 
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * 
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  * 
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  * 
*/

package it.cnr.isti.labse.glimpse.probe;

import java.util.Properties;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.probe.GlimpseProbe;
import it.cnr.isti.labse.glimpse.utils.DebugMessages;
import it.cnr.isti.labse.glimpse.utils.Manager;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * This class represent a generic implementation of the interface {@link GlimpseProbe}.<br />
 * It provides the abstract method: {@link #sendMessage(GlimpseBaseEvent, boolean)}<br />
 * that can be extended if needed.<br />
 * 
 * @author Antonello Calabr&ograve;
 * @version 3.2
 *
 */
public abstract class GlimpseAbstractProbe implements GlimpseProbe {
	
	protected static InitialContext initContext;
	protected static TopicSession publishSession;
	protected static TopicPublisher tPub;
	protected static TopicConnection connection;
	protected static Topic connectionTopic;
	
	/**
	 * This constructor allow to create a GlimpseAbstractProbe object<br />
	 * providing the {@link Properties} settings object
	 * @param settings can be generated automatically
	 * using {@link Manager#createConsumerSettingsPropertiesObject(String, String, String, String, String, String, boolean, String)}.
	 * 
	 */	
	public GlimpseAbstractProbe(Properties settings) {
		
		try {	
			initContext = this.initConnection(settings, true);
			this.createConnection(initContext,settings.getProperty("probeChannel"), settings, true);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * This method setup a {@link TopicConnection} object.
	 * 
	 * @param initConn the InitialContext object generated using the method {@link #initConnection(Properties, boolean)}.
	 * @param settings can be generated automatically using {@link Manager#createProbeSettingsPropertiesObject(String, String, String, String, String, String, boolean, String, String)}
	 * @param probeChannel
	 * @param settings
	 * @param debug
	 * @return a {@link TopicPublisher} object
	 * @throws NamingException
	 * @throws JMSException
	 */
	protected TopicPublisher createConnection(InitialContext initConn, String probeChannel, Properties settings, boolean debug) throws NamingException, JMSException
	{
		if (debug)
			DebugMessages.print(this.getClass().getSimpleName(),
					"Creating ConnectionFactory with settings ");
		TopicConnectionFactory connFact = (TopicConnectionFactory)initConn.lookup(settings.getProperty("connectionFactoryNames"));
		if (debug) {
			DebugMessages.ok();  
			DebugMessages.print(this.getClass().getSimpleName(),
						"Creating TopicConnection "); }
			connection = connFact.createTopicConnection();
			if (debug) {
			DebugMessages.ok();
			DebugMessages.line(); }
			if (debug) {
				DebugMessages.print(this.getClass().getSimpleName(),
						"Creating Session "); }
			publishSession = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
			if (debug) {
				DebugMessages.ok();
				DebugMessages.print(this.getClass().getSimpleName(),
						"Looking up for channel ");}
			connectionTopic = (Topic) initContext.lookup(probeChannel);
			if (debug) {
				DebugMessages.ok();
				DebugMessages.print(this.getClass().getSimpleName(),
						"Creating Publisher "); }
			return tPub = publishSession.createPublisher(connectionTopic);
	}

	/**
	 * This method setup the connection parameters using the {@link Properties} object {@link #settings}
	 * 
	 * @param settings the parameter to setup the connection to the Enterprise Service Bus<br /> to send messages
	 * @param debug
	 * @return it provides an {@link InitialContext} object that will be used<br />during the Consumer <-> Monitoring interaction.
	 * @throws NamingException
	 */
	protected InitialContext initConnection(Properties settings, boolean debug) throws NamingException {
		if (debug)
		DebugMessages.print(this.getClass().getSimpleName(),
				"Creating InitialContext with settings ");
		InitialContext initConn = new InitialContext(settings);
		if (debug) {
			DebugMessages.ok(); 
			DebugMessages.line(); }
		return initConn;
	}	
	
	public abstract void sendMessage(GlimpseBaseEvent<?> event, boolean debug);
	
	/**
	 * This method send a {@link GlimpseBaseEvent} message on the ESB<br />
	 * specifically on the channel specified in the {@link #settings} object.
	 * 
	 * @param event the event to send
	 * @param debug
	 * @throws JMSException
	 * @throws NamingException
	 */
	protected void sendEventMessage(GlimpseBaseEvent<?> event, boolean debug) throws JMSException,
			NamingException {
		if (debug) {
			DebugMessages.print(this.getClass().getSimpleName(),
					"Creating Message "); }
		try 
		{
			ObjectMessage messageToSend = publishSession.createObjectMessage();			
			messageToSend.setObject(event);		
		if (debug) {
			DebugMessages.ok();
			DebugMessages.print(this.getClass().getSimpleName(),
					"Publishing message  "); }
		tPub.publish(messageToSend);	
		if (debug) {
			DebugMessages.ok(); 
			DebugMessages.line(); }
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
