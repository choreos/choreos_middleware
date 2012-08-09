package eu.choreos.monitoring.platform.analyzer;


import eu.choreos.monitoring.platform.glimpse.consumer.GlimpseConsumer;
import eu.choreos.monitoring.platform.glimpse.consumer.GlimpseMessageEvent;
import eu.choreos.monitoring.platform.glimpse.consumer.IListener;

public class AnalyzerDaemon implements IListener {
	
	@SuppressWarnings("unused")
	private GlimpseConsumer glimpseConsumer;
	
	public AnalyzerDaemon() {
		// Get a consumer for glimpse messages
		glimpseConsumer = GlimpseConsumer.getConsumer();
	}

	private int exec () {	
		return 0;
	}


	public void handleEvent(GlimpseMessageEvent ev) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		
		AnalyzerDaemon daemon = new AnalyzerDaemon();
		daemon.exec();
	}


}
