package br.usp.ime.eeeval.unit;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.eeeval.Tracker;
import br.usp.ime.eeeval.TrackerImpl;

public class TrackerTest {

	private transient Tracker tracker;

	@Before
	public void setUp() {
		tracker = new TrackerImpl();
	}

	@Test
	public void testId() throws MalformedURLException {
		tracker.setId(42);
		assertEquals("42", tracker.getPathIds());
	}

	@Test
	public void shouldInferIdFromInvocationAddress()
			throws MalformedURLException {
		tracker.setInvocationAddress(null, "name1", new ArrayList<String>());
		assertEquals("0", tracker.getPathIds());

		tracker.setInvocationAddress(null, "name42", new ArrayList<String>());
		assertEquals("41", tracker.getPathIds());
	}

	@Test(expected = InvalidParameterException.class)
	public void shouldThrowExceptionIfDependencyIsNotNamedCorrectly() {
		tracker.setInvocationAddress(null, "noNumber", new ArrayList<String>());
	}
}
