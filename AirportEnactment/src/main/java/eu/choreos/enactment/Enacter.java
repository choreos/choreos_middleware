package eu.choreos.enactment;

import eu.choreos.enactment.context.ChoreographyContext;

public interface Enacter {
	
	/**
	 * 
	 * @param context the <code>enact</code> method must 
	 * insert the deployed services into the context
	 */
	public void enact(ChoreographyContext context);

}
