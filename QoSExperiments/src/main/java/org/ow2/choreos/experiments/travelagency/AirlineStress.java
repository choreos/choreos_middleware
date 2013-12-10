package org.ow2.choreos.experiments.travelagency;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.experiments.travelagency.client.TravelAgencyService;
import org.ow2.choreos.experiments.travelagency.client.TravelAgencyServiceService;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.services.datamodel.qos.DesiredQoS;
import org.ow2.choreos.services.datamodel.qos.ResponseTimeMetric;
import org.ow2.choreos.tests.ModelsForTest;

public class AirlineStress implements Runnable {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private static final int N_TRDS = 10;
    private static ChoreographyDeployer enactmentEngine;

    private ChoreographySpec chorSpec;
    private ModelsForTest models;
    private Choreography chor;

    private AtomicInteger counter = new AtomicInteger(0);
    private List<TravelAgencyService> clients = new ArrayList<TravelAgencyService>();

    private final ExecutorService pool;

    static {
	enactmentEngine = new ChorDeployerClient("http://localhost:9100/enactmentengine/");
	ModelsForTest.AIRLINE_WAR = "http://www.ime.usp.br/~tfurtado/downloads/airline.war";
	ModelsForTest.TRAVEL_AGENCY_WAR = "http://www.ime.usp.br/~tfurtado/downloads/travelagency.war";
    }

    public AirlineStress() {
	pool = Executors.newFixedThreadPool(N_TRDS);

	models = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT);
	chorSpec = models.getChorSpec();
	DesiredQoS desiredQoS = new DesiredQoS();
	ResponseTimeMetric responseTime = new ResponseTimeMetric();
	responseTime.setAcceptablePercentage(0.05f);
	responseTime.setMaxDesiredResponseTime(120f);
	desiredQoS.setResponseTimeMetric(responseTime);
	((DeployableServiceSpec) chorSpec.getServiceSpecByName("airline")).setDesiredQoS(desiredQoS);

    }

    private void runExperiment() throws InterruptedException, IOException, EnactmentException,
	    ChoreographyNotFoundException {

	String chorId = enactmentEngine.createChoreography(chorSpec);
	chor = enactmentEngine.enactChoreography(chorId);

	String travelAgencyURI = ((DeployableService) chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY))
		.getInstances().get(0).getNativeUri();

	long TIME_UNIT_IN_MS = 12 * 1000; // 60 * 1000 = 1 minute; 12 * 1000 =
					  // 12
					  // minutes

	long rateVector[][] = { { 1800, 30 }, { 1500, 10 }, { 1300, 10 }, { 1100, 10 }, { 900, 15 }, { 700, 15 },
		{ 400, 30 }, { 800, 15 }, { 1000, 15 }, { 1500, 15 }, { 1700, 30 }, { 2000, 15 }, { 2800, 30 },
		{ 3000, 30 } };

	logger.info("Setting up travel agency clients. Total: " + N_TRDS);

	String travelAgencyWsdlLocation = travelAgencyURI + "?wsdl";
	for (int i = 0; i < N_TRDS; i++) {
	    clients.set(i, getNewTravelAgencyClient(travelAgencyWsdlLocation));
	}

	System.out.println("Press ENTER to start experiment:");
	System.in.read();
	logger.info("Experiment started");

	for (int j = 0; j < 14; j++) {

	    long rate = rateVector[j][0];
	    long time = rateVector[j][1];

	    long timeCounter = 0;

	    logger.info("Starting loop; rate = " + rate + "ms; time = " + time);
	    while (timeCounter < time * TIME_UNIT_IN_MS) {
		timeCounter += rate;
		pool.submit(new BuyTripHandler(getClient()));
		logger.info("Request sent; sleeping for " + rate);
		Thread.sleep(rate);
	    }
	    logger.info("Finished loop; rate = " + rate + "ms; time = " + time);
	}

	logger.info("Experiment finished");
    }

    private TravelAgencyService getClient() {
	return clients.get(counter.getAndIncrement());
    }

    private TravelAgencyService getNewTravelAgencyClient(String travelAgencyWsdlLocation) throws MalformedURLException {
	String namespace = "http://choreos.ow2.org/";
	String local = "TravelAgencyServiceService";

	QName travelAgencyNamespace = new QName(namespace, local);
	TravelAgencyServiceService travel = new TravelAgencyServiceService(new URL(travelAgencyWsdlLocation),
		travelAgencyNamespace);

	return travel.getTravelAgencyServicePort();
    }

    @Override
    public void run() {
	try {
	    runExperiment();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (EnactmentException e) {
	    e.printStackTrace();
	} catch (ChoreographyNotFoundException e) {
	    e.printStackTrace();
	}
    }

    class BuyTripHandler implements Callable<String> {

	private TravelAgencyService client;

	public BuyTripHandler(TravelAgencyService client) {
	    this.client = client;
	}

	@Override
	public String call() throws Exception {
	    long t = System.currentTimeMillis();
	    String result = client.buyTrip();
	    long tf = System.currentTimeMillis() - t;
	    return result + "; " + (tf);
	}

    }

    public static void main(String[] args) {
	new Thread(new AirlineStress()).start();
    }
}
