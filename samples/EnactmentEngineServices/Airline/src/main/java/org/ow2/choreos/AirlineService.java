package org.ow2.choreos;

import java.lang.management.ManagementFactory;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.ow2.choreos.log.SimpleLogger;
import org.ow2.choreos.log.SimpleLoggerImpl;

@WebService
public class AirlineService implements Airline {

	private final SimpleLogger logger = new SimpleLoggerImpl("/tmp/airline.log");

	public AirlineService() {
		
		logger.info("Airline started at " + AirlineStarter.SERVICE_ADDRESS);
	}
	
	@WebMethod
	@Override
	public String buyFlight() {
		logger.info("Filling a 12500000 int array (50MB)");
        int [] vec = new int[12500000];
        Random r = new Random();
        for(int i=0; i < 12500000; i++) {
                vec[i] = r.nextInt();
        }
        try {
                Thread.sleep(1000);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        String result = "Flight number: " + vec[r.nextInt(12500000)] + "; (Thread ID: " + ManagementFactory.getRuntimeMXBean().getName() + ")";
        logger.info("Request to buy flight; response: " + result);
        return result;
	}

}
