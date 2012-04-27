package eu.choreos.monitoring;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelloWorldTest {

	@Test
	public void shouldReturnHelloWorldMessage() {
		assertEquals((new HelloWorld()).getMessage(), "Hello World");
	}

}
