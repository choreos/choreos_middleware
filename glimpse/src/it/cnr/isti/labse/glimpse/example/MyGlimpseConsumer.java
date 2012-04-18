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

import it.cnr.isti.labse.glimpse.consumer.GlimpseAbstractConsumer;
import it.cnr.isti.labse.glimpse.utils.Manager;
import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;
import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;
import it.cnr.isti.labse.glimpse.xml.complexEventRule.ComplexEventRuleActionListDocument;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 * This class is an example of how to extend the {@link GlimpseAbstractConsumer} class, <br />
 * simply implementing the abstract method {@link MyGlimpseConsumer#messageReceived(Message)}.
 * <br /><br />
 * The behaviour of the class is defined in the {@link GlimpseAbstractConsumer} class and<br />
 * can be modified extending it or implementing the interface GlimpseConsumer.<br /><br />
 * 
 * @author Antonello Calabr&ograve;
 * @version 3.2
 * 
 */
public class MyGlimpseConsumer extends GlimpseAbstractConsumer {

	/**
	 * @param settings can be generated automatically using {@link Manager#createConsumerSettingsPropertiesObject(String, String, String, String, String, String, boolean, String)}
	 * @param plainTextRule a plain text rule is a String containing the Drools<br />
	 * (or other cep engine implemented) rule that can be generated<br />
	 * using the {@link ComplexEventRuleActionListDocument} classes.<br />
	 * For a rule example see the exampleRule.xml file. 
	 * 
	 */
	public MyGlimpseConsumer(Properties settings,
			String plainTextRule) {
		super(settings, plainTextRule);
	}

	@Override
	public void messageReceived(Message arg0) throws JMSException {
		try {
			ObjectMessage responseFromMonitoring = (ObjectMessage) arg0;	
			if (responseFromMonitoring.getObject() instanceof ComplexEventException) {
				ComplexEventException exceptionReceived = (ComplexEventException) responseFromMonitoring.getObject();
				System.out.println("Exception ClassName: " + exceptionReceived.getClassName() + "\n");
			}
			else {
				ComplexEventResponse resp = (ComplexEventResponse) responseFromMonitoring.getObject();
				System.out.println("Response value: " + resp.getResponseValue());
			}
		}
		catch(ClassCastException asd) {
		}
	}
}