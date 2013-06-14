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
		logger.info("Filling array");
        int [] vec = new int[Integer.MAX_VALUE];
        Random r = new Random();
        for(int i=0; i < Integer.MAX_VALUE; i++) {
                vec[i] = r.nextInt();
        }
        try {
                Thread.sleep(1000);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        String result = "Flight number: " + vec[r.nextInt(Integer.MAX_VALUE)] + "; (Thread ID: " + ManagementFactory.getRuntimeMXBean().getName() + ")";
        logger.info("Request to buy flight; response: " + result);
        return result;
	}

}
