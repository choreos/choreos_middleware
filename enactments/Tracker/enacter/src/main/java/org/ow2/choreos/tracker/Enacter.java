package org.ow2.choreos.tracker;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;

public class Enacter {

    private static transient int chorSizeArg;
    private static transient String warFileArg;
    private static final String CHOR_DEPLOYER = "http://localhost:9102/choreographydeployer/";

    private int enacterId;
    private transient int chorSize;
    private transient Choreography choreography;

    private Logger logger = Logger.getLogger(Enacter.class);

    public static void main(final String[] args) throws EnactmentException, ChoreographyNotFoundException,
	    IllegalArgumentException, MalformedURLException {
	readArgs(args);
	final Enacter enacter = new Enacter(1);
	enacter.enact(warFileArg, chorSizeArg);
	enacter.verifyAnswer();
    }

    private static void readArgs(final String[] args) throws IllegalArgumentException {
	if (args.length < 2) {
	    throw new IllegalArgumentException("2 args expected: war file, number of services.");
	}

	warFileArg = args[0];
	chorSizeArg = Integer.parseInt(args[1]);
    }

    public Enacter(int id) {
	this.enacterId = id;
    }

    public int getId() {
	return this.enacterId;
    }

    public void enact(final String warFile, final int chorSize) throws EnactmentException,
	    ChoreographyNotFoundException, MalformedURLException {
	ChorSpecCreator.setWarFile(warFile);
	this.chorSize = chorSize;

	this.choreography = getChoreography();
	setLastServiceId();
    }

    private Choreography getChoreography() throws EnactmentException, ChoreographyNotFoundException {
	final ChorSpecCreator chorSpecCreator = new ChorSpecCreator();
	final ChoreographySpec chorSpec = chorSpecCreator.create(chorSize);
	return deployChoreography(chorSpec);
    }

    private Choreography deployChoreography(final ChoreographySpec chorSpec) throws EnactmentException,
	    ChoreographyNotFoundException {
	final ChorDeployerClient deployer = new ChorDeployerClient(CHOR_DEPLOYER);
	final String chorId = deployer.createChoreography(chorSpec);
	return deployer.enactChoreography(chorId);
    }

    private void setLastServiceId() throws MalformedURLException {
	final int trackerNumber = chorSize - 1;
	final Tracker tracker = getTracker(trackerNumber);
	tracker.setId(trackerNumber);
    }

    private Tracker getTracker(final int trackerNumber) throws MalformedURLException {
	final String wsdl = getTrackerWsdl(trackerNumber);
	return CxfProxyCreator.getTracker(wsdl);
    }

    private String getTrackerWsdl(final int trackerNumber) {
	final TrackerInfo trackerInfo = new TrackerInfo();
	trackerInfo.setChoreography(choreography);
	return trackerInfo.getWsdl(trackerNumber);
    }

    public boolean verifyAnswer() throws MalformedURLException {
	final Tracker firstTracker = getTracker(0);
	final String actual = firstTracker.getPathIds();
	final String expected = getExpectedPathIds();

	boolean ok = expected.equals(actual);
	if (!ok) {
	    logger.error(String.format("Expected '%s' but got '%s' at Enacter#'%d'.", expected, actual, this.enacterId));
	}
	return ok;
    }

    private String getExpectedPathIds() {
	final TrackerInfo trackerInfo = new TrackerInfo();
	return trackerInfo.getExpectedPathIds(chorSize);
    }
}
