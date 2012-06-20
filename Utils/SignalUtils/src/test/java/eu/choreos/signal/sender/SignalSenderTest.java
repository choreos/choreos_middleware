package eu.choreos.signal.sender;


public class SignalSenderTest {
	
	private SignalSender s;

//	@Before
	public void setUp() {
		s = new SignalSender();
	}
	
//	@Test
	public void SendSignal() {
		int v = s.sendSignalByProcessName("gmond", 1);
//s		assertFalse(v == -1);	
	}

}
