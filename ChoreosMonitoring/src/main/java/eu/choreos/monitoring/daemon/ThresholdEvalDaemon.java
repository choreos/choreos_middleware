package eu.choreos.monitoring.daemon;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.monitoring.GmondDataReader;

public class ThresholdEvalDaemon {

	private static String host;
	private static int port;
	private AnomalyNotifier notifier;

	public void setDataReader(GmondDataReader dataReader) {
		this.notifier = new AnomalyNotifier(dataReader);
	}

	public static void main(String[] args) throws InterruptedException {
		parseArgs(args);

		ThresholdEvalDaemon daemon = new ThresholdEvalDaemon();

		daemon.setDataReader(new GmondDataReader(host, port));
		
		List<Threshold> thresholds = new ArrayList<Threshold>();
		thresholds.add(new Threshold("load_one", Threshold.MAX, 3));
		
		daemon.setThresholdList(thresholds);
		

		while (true) {
			daemon.evaluateThresholds();
			Thread.sleep(600000);
		}
	}
	
	public void setThresholdList(List<Threshold> thresholds) {
	    notifier.setThresholds(thresholds);
	}

	private void evaluateThresholds() {
	    
	    for(Threshold threshold: notifier.evaluateAllThresholds()) {
		System.out.println("Threshold triggered for " + threshold);
	    }
	    
	}

	private static void parseArgs(String[] args) {
		switch (args.length) {
		case 0:
			host = "http://localhost/";
			port = 8649;
			break;
		case 1:
			host = args[0];
			port = 8649;
			break;
		case 2:
			host = args[0];
			port = Integer.parseInt(args[1]);
			break;
		default:
			System.out
					.println("USAGE: ThresholdEvalDaemon [hostLocation] [port]");
			System.out
					.println("Default values: hostLocation = 'http://localhost/'");
			System.out.println("                port = 8649");
			System.out
					.println("Note: to set a port, the hostLocation must also be present");
			break;
		}
	}

}
