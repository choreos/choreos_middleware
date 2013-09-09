package org.ow2.choreos.chors.reconfiguration;

import it.cnr.isti.labse.glimpse.consumer.GlimpseAbstractConsumer;
import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;
import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.client.ServicesClient;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class EnactmentEngineGlimpseConsumer extends GlimpseAbstractConsumer {

    private NodePoolManager nodesClient;
    private ServicesManager servicesClient;

    private List<HandlingEvent> handlingEvents = new ArrayList<HandlingEvent>();

    private SimpleLogger logger = new SimpleLoggerImpl("consumer.log");

    public EnactmentEngineGlimpseConsumer(Properties settings, String plainTextRule) {
	super(settings, plainTextRule);
    }

    @Override
    public void messageReceived(Message arg0) throws JMSException {
	logger.info("Captured response message");
	try {
	    ObjectMessage responseFromMonitoring = (ObjectMessage) arg0;

	    if (responseFromMonitoring.getObject() instanceof ComplexEventException) {

		logger.debug("Handling expection " + responseFromMonitoring);
		handleComplexEventException(responseFromMonitoring);

	    } else {
		// logger.debug("Handling event " + responseFromMonitoring);
		// System.out.println(">>>>> " +
		// responseFromMonitoring.getObject().toString());
		handleComplexEventReceived(responseFromMonitoring);

	    }
	} catch (ClassCastException asd) {

	    logger.error(asd.getMessage());

	}
    }

    private void handleComplexEventException(ObjectMessage responseFromMonitoring) throws JMSException {

	ComplexEventException exceptionReceived = (ComplexEventException) responseFromMonitoring.getObject();
	logger.warn("Exception ClassName: " + exceptionReceived.getClassName());

    }

    private boolean eventIsBeingHandling(HandlingEvent ev) {
	for (HandlingEvent e : handlingEvents) {
	    if (ev.getNode().equals(e.getNode()))
		return true;
	}
	return false;
    }

    private void handleComplexEventReceived(ObjectMessage responseFromMonitoring) throws JMSException {

	ComplexEventResponse resp = (ComplexEventResponse) responseFromMonitoring.getObject();

	HandlingEvent ev = new HandlingEvent(resp.getResponseKey(), resp.getResponseValue());

	if (eventIsBeingHandling(ev))
	    return;
	else
	    handlingEvents.add(ev);

	logger.debug("Response value: " + resp.getResponseKey() + " : " + resp.getResponseValue());

	// get deployable service on node resp.getResponseValue()
	String uri = "http://localhost:9100/deploymentmanager";
	nodesClient = new NodesClient(uri);
	servicesClient = new ServicesClient(uri);

	Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

	Matcher m = ipPattern.matcher(resp.getResponseValue());

	if (!m.find())
	    logger.error("Deu merda!");

	String hostt = m.group(0);

	logger.info(">>>>>>>>>>" + hostt);

	List<DeployableService> services = getServicesHostedOn(hostt);

	if (services.isEmpty()) {
	    logger.warn("Ooops! This node was created by enactment engine?");
	    throw new IllegalStateException();
	}

	// get service spec
	logger.debug("Getting specs of services hosted on " + hostt);
	List<DeployableServiceSpec> serviceSpecs = getServiceSpecsForServices(services);

	// inscrease number of instances (for now, only testing, there
	// is one service, and it is an airline instance)
	logger.debug("Selecting first service spec");
	DeployableServiceSpec spec = serviceSpecs.get(0);

	// get chor that service beyonds to
	Choreography c = getChor();
	ChoreographySpec cSpec = c.getChoreographySpec();
	for (DeployableServiceSpec s : cSpec.getDeployableServiceSpecs()) {
	    if (s.getName().equals(spec.getName())) {
		logger.debug("Found service spec. Going to increase number of instances");
		s.setNumberOfInstances(s.getNumberOfInstances() + 1);
		break;
	    }
	}

	// call choreography deployer to update chor
	try {
	    logger.info("Going to update chor with spec: " + cSpec);
	    getChorClient().updateChoreography("1", cSpec);
	} catch (ChoreographyNotFoundException e) {
	    logger.error(e.getMessage());
	} catch (EnactmentException e) {
	    logger.error(e.getMessage());
	}

	try {
	    logger.info("Enacting choreography");
	    getChorClient().enactChoreography("1");
	} catch (EnactmentException e) {
	    logger.error(e.getMessage());
	} catch (ChoreographyNotFoundException e) {
	    logger.error(e.getMessage());
	}

	scheduleRemoveEvent(ev);
	logger.info("Finished update");
    }

    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

    private void scheduleRemoveEvent(final HandlingEvent ev) {

	Runnable task = new Runnable() {

	    @Override
	    public void run() {
		logger.info("Removing handled event");
		handlingEvents.remove(ev);
	    }
	};
	worker.schedule(task, 1, TimeUnit.MINUTES);

    }

    private List<DeployableServiceSpec> getServiceSpecsForServices(List<DeployableService> services) {

	List<DeployableServiceSpec> specs = new ArrayList<DeployableServiceSpec>();
	for (DeployableService service : services) {
	    specs.add(service.getSpec());
	}

	return specs;
    }

    private List<DeployableService> getServicesHostedOn(String responseValue) {

	Choreography chor = getChor();

	List<DeployableService> result = new ArrayList<DeployableService>();
	for (DeployableService service : chor.getDeployableServices()) {
	    for (CloudNode node : service.getSelectedNodes())
		if (node.getIp().equals(responseValue)) {
		    result.add(service);
		    break;
		}
	}
	return result;
    }

    private Choreography getChor() {

	String chorId = "1"; // i know that

	ChoreographyDeployer chorClient = getChorClient();

	Choreography chor = null;
	try {
	    chor = chorClient.getChoreography(chorId);
	} catch (ChoreographyNotFoundException e) {
	    e.printStackTrace();
	}
	return chor;
    }

    private ChoreographyDeployer getChorClient() {
	String uri = "http://localhost:9102/choreographydeployer/";
	ChorDeployerClient chorClient = new ChorDeployerClient(uri);
	return chorClient;
    }
}