package eu.choreos.signal.sender;

import org.junit.*;
import static org.junit.Assert.*;

public class SignalSenderTest {
	
	private SignalSender s;

	@Before
	public void setUp() {
		s = new SignalSender();
	}
	
	@Test
	public void SendSignal() {
		int v = s.sendSignalByProcessName("gmond", 1);
		assertFalse(v == -1);	
	}

}
