package monitoring.glimpse.consumer.daemon;

import it.cnr.isti.labse.glimpse.consumer.GlimpseAbstractConsumer;
import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;
import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;
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

public class MyGlimpseConsumer extends GlimpseAbstractConsumer {

    private NodePoolManager nodesClient;
    private ServicesManager servicesClient;

    private Logger logger = Logger.getLogger(MyGlimpseConsumer.class);

    public MyGlimpseConsumer(Properties settings, String plainTextRule) {
	super(settings, plainTextRule);
    }

    @Override
    public void messageReceived(Message arg0) throws JMSException {
	logger.info("Captured response message");
	try {
	    ObjectMessage responseFromMonitoring = (ObjectMessage) arg0;
	    if (responseFromMonitoring.getObject() instanceof ComplexEventException) {

		handleComplexEventException(responseFromMonitoring);

	    } else {

		handleComplexEventReceived(responseFromMonitoring);

	    }
	} catch (ClassCastException asd) {

	    asd.printStackTrace();

	}
    }

    private void handleComplexEventException(ObjectMessage responseFromMonitoring) throws JMSException {

	ComplexEventException exceptionReceived = (ComplexEventException) responseFromMonitoring.getObject();
	logger.warn("Exception ClassName: " + exceptionReceived.getClassName());

    }

    private void handleComplexEventReceived(ObjectMessage responseFromMonitoring) throws JMSException {
	ComplexEventResponse resp = (ComplexEventResponse) responseFromMonitoring.getObject();
	logger.debug("Response value: " + resp.getResponseKey() + " : " + resp.getResponseValue());

	// get deployable service on node resp.getResponseValue()
	String uri = "http://localhost:9100/deploymentmanager";
	nodesClient = new NodesClient(uri);
	servicesClient = new ServicesClient(uri);

	List<DeployableService> services = getServicesHostedOn(resp.getResponseValue());

	if (services.isEmpty()) {
	    logger.warn("Ooops! This node was created by enactment engine?");
	    throw new IllegalStateException();
	}

	// get service spec
	logger.debug("Getting specs of services hosted on " + resp.getResponseValue());
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
	    logger.debug("Going to update chor with spec: " + cSpec);
	    getChorClient().updateChoreography("1", cSpec);
	} catch (ChoreographyNotFoundException e) {
	    logger.fatal(e);
	} catch (EnactmentException e) {
	    logger.fatal(e);
	}

	try {
	    logger.debug("Enacting choreography");
	    getChorClient().enactChoreography("1");
	} catch (EnactmentException e) {
	    logger.fatal(e);
	} catch (ChoreographyNotFoundException e) {
	    logger.fatal(e);
	}
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

	ChorDeployerClient chorClient = getChorClient();

	Choreography chor = null;
	try {
	    chor = chorClient.getChoreography(chorId);
	} catch (ChoreographyNotFoundException e) {
	    e.printStackTrace();
	}
	return chor;
    }

    private ChorDeployerClient getChorClient() {
	String uri = "http://localhost:9102/choreographydeployer/";

	ChorDeployerClient chorClient = new ChorDeployerClient(uri);
	return chorClient;
    }
}