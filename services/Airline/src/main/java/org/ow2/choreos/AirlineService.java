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
        int MAX = 40000000;
        int flightNumber = 0;
        Random r = new Random();
        for (int i = 0; i < MAX; ) {
            flightNumber = ++i + r.nextInt(1);
        }
        String result = "Flight number: " + flightNumber + "; (Thread ID: "
                + ManagementFactory.getRuntimeMXBean().getName() + ")";
        logger.info("Request to buy flight; response: " + result);
        return result;
    }

}
