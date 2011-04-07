package org.ow2.petals.bc.loggedsoap.listeners;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.petals.component.framework.AbstractComponent;
import org.ow2.petals.component.framework.api.message.Exchange;
import org.ow2.petals.component.framework.su.AbstractServiceUnitManager;

public class JBIListenerTest {

	private static final class VoidComponent extends AbstractComponent {
		private static final Logger log = Logger.getLogger(JBIListener.class.getName());
		
		@Override
		public Logger getLogger() {
			return log;
		}

		@Override
		protected AbstractServiceUnitManager createServiceUnitManager() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	private static final String logName= "/home/felipepg/LabXP/choreos/log4j.log";
	private static final Logger log = Logger.getLogger(JBIListenerTest.class.getName());
	
	private static AbstractComponent component;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FileWriter fw = new FileWriter(logName);
		fw.close();
		
		component = new VoidComponent();
		component.getLogger();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOnJBIMessageExchange() throws IOException {
		JBIListener jbi = new JBIListener();
		jbi.init(new VoidComponent());
		
		jbi.onJBIMessage(null);
		
		String lastLine = lastLogLine();
		
		assertTrue("Log Generated Successfully", !("".equals(lastLine)));
	}

	private String lastLogLine() throws FileNotFoundException, IOException {
		String lastLine = null, nextLine = null;
		
		BufferedReader br = new BufferedReader(new FileReader(logName));
		
		while ((nextLine = br.readLine()) == null)
			lastLine = nextLine;
		
		br.close();
		return lastLine;
	}

}
