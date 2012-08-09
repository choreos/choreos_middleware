package eu.choreos.monitoring.platform.glimpse.consumer;


/*
 * IListener interface to implement the Observer design pattern
 * 
 * */
public interface IListener {
	
	public void handleEvent(GlimpseMessageEvent ev) ;

}
