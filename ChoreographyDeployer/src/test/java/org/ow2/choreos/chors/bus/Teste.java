package org.ow2.choreos.chors.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.utils.Concurrency;

import esstar.petalslink.com.service.management._1_0.ManagementException;

public class Teste {
	
	private Logger logger = Logger.getLogger(Teste.class);

	public static void main(String[] args) {

		Teste test = new Teste();
		test.start();
	}

	public void start() {
	
		List<String> endpoints = new ArrayList<String>();
		endpoints.add("http://192.168.56.101:1234/airline");
		endpoints.add("http://192.168.56.101:1235/travelagency");
		
		ExecutorService executor = Executors.newFixedThreadPool(2);

		logger.info("Requesting services proxification");
		for (String endpoint: endpoints) {
			MyRunnable proxifier = new MyRunnable(endpoint);
			executor.submit(proxifier);
		}
		
		Concurrency.waitExecutor(executor, 5, this.logger);
		
		logger.info("Proxification phase finished");

	}
	
	public void startSequencial() {
		
		List<String> endpoints = new ArrayList<String>();
		endpoints.add("http://192.168.56.101:1234/airline");
		endpoints.add("http://192.168.56.101:1235/travelagency");
		
		logger.info("Requesting services proxification");
		for (String endpoint: endpoints) {
			EasyESBNode esbNode = new EasyESBNodeImpl("http://192.168.56.101:8180/services/adminExternalEndpoint");
			String wsdl = endpoint.replaceAll("/$", "").concat("?wsdl");
			try {
				System.out.println("proxifying " + endpoint);
				String proxified = esbNode.proxifyService(endpoint, wsdl);
				System.out.println(endpoint + " proxified as " + proxified);
			} catch (ManagementException e) {
				System.out.println("Exception " + e.getClass() + ": " + e.getMessage());
			}
		}
		
		logger.info("Proxification phase finished");

	}
	
	private class MyRunnable implements Runnable {

		String endpoint;
		
		private MyRunnable(String endpoint) {
			this.endpoint = endpoint;
		}
		
		@Override
		public void run() {
			EasyESBNode esbNode = new EasyESBNodeImpl("http://192.168.56.101:8180/services/adminExternalEndpoint");
			String wsdl = endpoint.replaceAll("/$", "").concat("?wsdl");
			try {
				System.out.println("proxifying " + endpoint);
				String proxified = esbNode.proxifyService(endpoint, wsdl);
				System.out.println(endpoint + " proxified as " + proxified);
			} catch (ManagementException e) {
				System.out.println("Exception " + e.getClass() + ": " + e.getMessage());
			}
		}
		
	}
}
