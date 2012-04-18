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

import it.cnr.isti.labse.glimpse.utils.Manager;

/**
 * This is a test class that execute the extended class {@link MyGlimpseConsumer}<br />
 * {@link MyGlimpseConsumer} simply override the {@link MyGlimpseConsumer#messageReceived(javax.jms.Message)} method 
 * 
 * @author Antonello Calabr&ograve;
 * @version 3.2
 *
 */
public class MyGlimpseConsumerLauncher {

	public static void main(String[] args) {
		
		new MyGlimpseConsumer(
				Manager.createConsumerSettingsPropertiesObject(
						"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
						"tcp://localhost:61616",
						"system",
						"manager",
						"TopicCF",
						"jms.serviceTopic",
						true,
						"consumerTest"),
						Manager.ReadTextFromFile(
								System.getProperty("user.dir") + "/bin/it/cnr/isti/labse/glimpse/example/exampleRule.xml")
								);
	}
}
