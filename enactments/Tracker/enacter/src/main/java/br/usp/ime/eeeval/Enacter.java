package br.usp.ime.eeeval;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.xmlbeans.XmlException;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;

import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.WSDLException;

public class Enacter {

	private static final int DEFAULT_CHORSIZE = 2;
	private transient int chorSize;
	private transient Choreography choreography;

	public static void main(final String[] args) throws EnactmentException,
			ChoreographyNotFoundException, WSDLException, XmlException,
			IOException, FrameworkException {
		final Enacter enacter = new Enacter();
		enacter.setArgs(args);
		enacter.enactAndVerify();
	}

	private void setArgs(final String[] args) {
		if (args.length > 0) {
			chorSize = Integer.parseInt(args[0]);
		} else {
			chorSize = DEFAULT_CHORSIZE;
		}
	}

	private void enactAndVerify() throws EnactmentException,
			ChoreographyNotFoundException, MalformedURLException {
		this.choreography = getChoreography();
		setLastServiceId();
		verifyAnswer();
	}

	private Choreography getChoreography() throws EnactmentException,
			ChoreographyNotFoundException {
		final ChorSpecCreator chorSpecCreator = new ChorSpecCreator();
		final ChoreographySpec chorSpec = chorSpecCreator.create(chorSize);
		return deployChoreography(chorSpec);
	}

	private Choreography deployChoreography(final ChoreographySpec chorSpec)
			throws EnactmentException, ChoreographyNotFoundException {
		final ChoreographyDeployer deployer = new ChoreographyDeployerImpl();
		final String chorId = deployer.createChoreography(chorSpec);
		return deployer.enactChoreography(chorId);
	}

	private void setLastServiceId() throws MalformedURLException {
		final int trackerNumber = chorSize - 1;
		final Tracker tracker = getTracker(trackerNumber);
		tracker.setId(trackerNumber);
	}

	private Tracker getTracker(final int trackerNumber)
			throws MalformedURLException {
		final String wsdl = getTrackerWsdl(trackerNumber);
		final ProxyCreator proxyCreator = new ProxyCreator();
		return proxyCreator.getProxy(wsdl);
	}

	private String getTrackerWsdl(final int trackerNumber) {
		final TrackerInfo trackerInfo = new TrackerInfo();
		trackerInfo.setChoreography(choreography);
		return trackerInfo.getWsdl(trackerNumber);
	}

	private void verifyAnswer() throws MalformedURLException,
			EnactmentException {
		final Tracker firstTracker = getTracker(0);
		final String actual = firstTracker.getPathIds();
		final String expected = getExpectedPathIds();

		if (!expected.equals(actual)) {
			throw new EnactmentException(String.format(
					"Expected '%s' but got '%s'.", expected, actual));
		}
	}

	private String getExpectedPathIds() {
		final TrackerInfo trackerInfo = new TrackerInfo();
		return trackerInfo.getExpectedPathIds(chorSize);
	}
}
