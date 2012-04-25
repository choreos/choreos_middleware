package eu.choreos.monitoring.glimpse.probe;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.probe.GlimpseAbstractProbe;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import eu.choreos.monitoring.daemon.Threshold;
import eu.choreos.monitoring.daemon.ThresholdEvalDaemon;

public class GangliaProbe extends GlimpseAbstractProbe {

	private ThresholdEvalDaemon daemon;


	public GangliaProbe(Properties settings) {
		super(settings);
	}
	
	public void notifyMessages(GlimpseBaseEvent<String> message,String host,int port) {
			thresholdEval(host,port,message);
	}
	
	private void thresholdEval(String host,int port, GlimpseBaseEvent<String> message){
		daemon = new ThresholdEvalDaemon(host,port);
		List<Threshold> thresholds = new ArrayList<Threshold>();
		thresholds.add(new Threshold("load_one", Threshold.MAX, 1));
		daemon.setThresholdList(thresholds);
		
		while (true) {
			sendStringMsg(message);
		}
	}
	
	private void sendStringMsg(GlimpseBaseEvent<String> event) {
		List<Threshold> triggeredThresholds = daemon.evaluateThresholds();
		if(!triggeredThresholds.isEmpty())
			event.setNetworkedSystemSource(triggeredThresholds.get(0).getHostName());
		for(Threshold threshold:triggeredThresholds){
			try {
				event.setData(threshold.toString());
				this.sendEventMessage(event,false);
				Thread.sleep(6000);
			} catch (JMSException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void sendMessage(GlimpseBaseEvent<?> event, boolean debug) {
		//TODO
	}

}
