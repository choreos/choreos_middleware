package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.util.concurrent.Callable;

import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;

public class Enacter {

    private static transient int chorSizeArg;
    private static transient String warFileArg;
    private static final String CHOR_DEPLOYER = "http://localhost:9102/choreographydeployer/";

    private transient final int enacterId;
    private transient int chorSize;
    private transient Choreography choreography;

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

        verifyChoreographySize(chorSizeArg);
    }

    private static void verifyChoreographySize(final int chorSize) {
        if (chorSize % 5 > 0) {
            throw new IllegalArgumentException(
                    "Please, follow the rhyme:\nthe composition size\nmust be multiple of 5.");
        }
    }

    public Enacter(final int enacterId) {
        this.enacterId = enacterId;
    }

    public int getId() {
        return this.enacterId;
    }

    public Choreography getChoreography() {
        return choreography;
    }

    public int getChorSize() {
        return chorSize;
    }

    public void enact(final String warFile, final int chorSize) throws EnactmentException,
            ChoreographyNotFoundException, MalformedURLException {
        verifyChoreographySize(chorSize);
        ChorSpecCreator.setWarFile(warFile);
        this.chorSize = chorSize;
        this.choreography = createChoreography();
        setLastServiceId();
    }

    private Choreography createChoreography() throws EnactmentException, ChoreographyNotFoundException {
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

    /*
     * Last Tracker cannot deduce its id from its dependency because it has not
     * any dependency.
     */
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
        final ChorProperties trackerInfo = new ChorProperties();
        trackerInfo.setChoreography(choreography);
        return trackerInfo.getWsdl(trackerNumber);
    }

    public boolean verifyAnswer() throws MalformedURLException {
        final VerifyTask task = new VerifyTask();
        final int timeout = 100;
        final Invoker<Boolean> invoker = new InvokerBuilder<Boolean>("VerifyTask", task, timeout).trials(3)
                .pauseBetweenTrials(40).build();
        boolean answerIsCorrect;
        try {
            answerIsCorrect = invoker.invoke();
        } catch (InvokerException e) {
            answerIsCorrect = false;
        }

        return answerIsCorrect;
    }

    private class VerifyTask implements Callable<Boolean> {
        @Override
        public Boolean call() throws MalformedURLException {
            final Tracker firstTracker = getTracker(0);
            final String answer = firstTracker.getPathIds();
            final ChorProperties chorProps = new ChorProperties();
            chorProps.setChoreography(choreography);
            boolean ok = chorProps.isAnswerCorrect(answer);
            return ok;
        }
    }
}
