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
package it.cnr.isti.labse.glimpse.example;

import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventImpl;
import it.cnr.isti.labse.glimpse.probe.GlimpseAbstractProbe;
import it.cnr.isti.labse.glimpse.probe.GlimpseProbe;
import it.cnr.isti.labse.glimpse.utils.DebugMessages;
import it.cnr.isti.labse.glimpse.utils.Manager;


/**
 * This class provides an example of how to send messages (events) to the Glimpse Monitoring Bus,
 * <br />there are three methods for sending event on which payload is <br /><br />
 * a String: {@link MyGlimpseProbe#generateAndSendExample_GlimpseBaseEvents_StringPayload(int)},<br />
 * an Object: {@link MyGlimpseProbe#generateAndSendExample_GlimpseBaseEvents_ObjectPayload(int)},<br />
 * an Exception: {@link MyGlimpseProbe#generateAndSendExample_GlimpseBaseEvents_ExceptionPayload(Exception)}<br /><br />
 * the sendEvent action behavior may be modified simply implementing the abstract method <br />
 * {@link GlimpseAbstractProbe#sendMessage(GlimpseBaseEvent, boolean)}.<br /<br />
 * In this class we used the classic sendEventMessage method provided by the abstract class {@link GlimpseAbstractProbe}<br />
 * <br />
 * You can directly implement your probe extending {@link GlimpseAbstractProbe} or directly implementing {@link GlimpseProbe}<br /><br />
 * @author Antonello Calabr&ograve;
 * @version 3.3.1
 *
 */

public class MyGlimpseProbe extends GlimpseAbstractProbe {

	public MyGlimpseProbe(Properties settings) {
		super(settings);
	}


	private void generateAndSendExample_GlimpseBaseEvents_StringPayload(int howMany) {
		for (int i = 0; i < howMany; i++) {
			//	creation of an event transporting a String
			GlimpseBaseEvent<String> message = new GlimpseBaseEventImpl<String>("asd", "connector1","connInstance", "conninstexec",123,122,System.currentTimeMillis(),"NS1", false);
			message.setData("This is the payload as a String of the message for the event #"+i);
			//	sending
			try {
				this.sendEventMessage(message, true);
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}	

	private void generateAndSendExample_GlimpseBaseEvents_ObjectPayload(int howMany) {
		for (int i = 0; i < howMany; i++) {
			//	creation of an event transporting an unspecialized Object
			GlimpseBaseEvent<Object> message = new GlimpseBaseEventImpl<Object>("asd", "connector1","connInstance", "conninstexec",123,122,System.currentTimeMillis(),"NS1", false);
			message.setData("This is the payload as an Object of the message for the event #"+i);

			//	sending
			try {
				this.sendEventMessage(message, false);
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}	
	
	private void generateAndSendExample_GlimpseBaseEvents_ExceptionPayload(Exception e) {
		//	creation of an event transporting an Exception
		GlimpseBaseEvent<Exception> exceptionEvent = new GlimpseBaseEventImpl<Exception>("e", "connector1","connInstance", "conninstexec",123,122,System.currentTimeMillis(), "NS1" , true);
		exceptionEvent.setIsException(true);
		exceptionEvent.setData(e);
		
		//	sending
		try {
			this.sendEventMessage(exceptionEvent, false);
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/*
	 * To override the sendMessage, you can use the following code
	 * 
	 * @Override
	 * public void sendMessage(GlimpseBaseEvent<?> event, boolean debug) {
	 * 	//YOUR SENDING IMPLEMENTATION
	 * }
	 * 
	 * */
	
	@Override
	public void sendMessage(GlimpseBaseEvent<?> event, boolean debug) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		
		MyGlimpseProbe aGenericProbe = new
				MyGlimpseProbe(Manager.createProbeSettingsPropertiesObject(
								"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
								"tcp://localhost:61616",
								"system",
								"manager",
								"TopicCF",
								"jms.probeTopic",
								false,
								"probeName",
								"probeTopic")
								);
		try {
			DebugMessages.println(MyGlimpseProbe.class.getSimpleName(), "Executing generateAndSendExample_GlimpseBaseEvents_StringPayload method");
			aGenericProbe.generateAndSendExample_GlimpseBaseEvents_StringPayload(9);
			
			DebugMessages.println(MyGlimpseProbe.class.getSimpleName(), "Executing generateAndSendExample_GlimpseBaseEvents_ObjectPayload method");
			aGenericProbe.generateAndSendExample_GlimpseBaseEvents_ObjectPayload(3);
			
			//Simulating throw of an IndexOutOfBounds Exception
			String[] arr = new String[2];
			System.out.println(arr[4]);	
		}
		catch(IndexOutOfBoundsException asdG) {
			//HERE the catch so the simulation of a event Exception
			DebugMessages.println(MyGlimpseProbe.class.getSimpleName(), "Executing generateAndSendExample_GlimpseBaseEvents_ExceptionPayload method");
			aGenericProbe.generateAndSendExample_GlimpseBaseEvents_ExceptionPayload(asdG);
		}			
	}
}
