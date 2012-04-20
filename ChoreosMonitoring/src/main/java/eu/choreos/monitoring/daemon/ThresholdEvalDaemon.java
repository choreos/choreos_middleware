package eu.choreos.monitoring.daemon;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.monitoring.GmondDataReader;

public class ThresholdEvalDaemon {

	private static String host;
	private static int port;
	private AnomalyNotifier notifier;

	public ThresholdEvalDaemon(){
		
	}
	
	public ThresholdEvalDaemon(String host,int port){
		this.setDataReader(new GmondDataReader(host, port));
	}
	
	public void setDataReader(GmondDataReader dataReader) {
		this.notifier = new AnomalyNotifier(dataReader);
	}

	public static void main(String[] args) throws InterruptedException {
		parseArgs(args);
		ThresholdEvalDaemon daemon = new ThresholdEvalDaemon(host,port);
	
		List<Threshold> thresholds = new ArrayList<Threshold>();
		thresholds.add(new Threshold("load_one", Threshold.MIN, 3));
		
		daemon.setThresholdList(thresholds);
		

		while (true) {
			daemon.evaluateThresholds();
			Thread.sleep(6000);
		}
	}
	
	public void setThresholdList(List<Threshold> thresholds) {
	    notifier.setThresholds(thresholds);
	}

	public List<Threshold> evaluateThresholds() {
	    
	    List<Threshold> evaluateAllThresholds = notifier.getAllSurpassedThresholds();
		for(Threshold threshold: evaluateAllThresholds) {
		System.out.println(threshold);
	    }
	    return evaluateAllThresholds;
	}

	private static void parseArgs(String[] args) {
	    host = "localhost";
	    port = 8649;
		
	    switch (args.length) {
		case 0:
			break;
		case 2:
		    	port = Integer.parseInt(args[1]);
		case 1:
			host = args[0];
			
			break;
		default:
			System.out
					.println("USAGE: ThresholdEvalDaemon [hostLocation] [port]");
			System.out
					.println("Default values: hostLocation = 'http://localhost/'");
			System.out.println("                port = 8649");host = args[0];
			
			System.out
					.println("Note: to set a port, the hostLocation must also be present");
			break;
		}
	}

}
