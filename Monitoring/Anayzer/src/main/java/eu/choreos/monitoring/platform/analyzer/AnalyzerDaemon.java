package eu.choreos.monitoring.platform.analyzer;


import eu.choreos.monitoring.platform.glimpse.consumer.GlimpseConsumer;
import eu.choreos.monitoring.platform.glimpse.consumer.GlimpseMessageEvent;
import eu.choreos.monitoring.platform.glimpse.consumer.IListener;

public class AnalyzerDaemon implements IListener {
	
	@SuppressWarnings("unused")
	private GlimpseConsumer glimpseConsumer;

	private void exec () {	
		glimpseConsumer = GlimpseConsumer.getConsumer();		
	}

	public void handleEvent(GlimpseMessageEvent ev) {
		
	}
	
	public static void main(String[] args) {		
		AnalyzerDaemon daemon = new AnalyzerDaemon();
		daemon.exec();
	}


}
