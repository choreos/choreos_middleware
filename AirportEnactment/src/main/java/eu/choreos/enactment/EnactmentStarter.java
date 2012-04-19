package eu.choreos.enactment;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.enactment.context.ChoreographyContext;
import eu.choreos.enactment.context.ContextCaster;

public class EnactmentStarter implements Enacter {
	
	private List<Enacter> enacters;
	
	public EnactmentStarter() {
		
		enacters = new ArrayList<Enacter>();
		enacters.add(new POWSEnacter());
		enacters.add(new SAEnacter());
	}
	
	public void enact(ChoreographyContext context) {
		
		System.out.println("Starting airport enactment...");
		for (Enacter enacter: enacters) {
			enacter.enact(context); // can you read these two lines? =P
		}
		
		ContextCaster contextCaster = new ContextCaster(context);
		contextCaster.cast();
	}
	
	
	public static void main(String[] args) {

		EnactmentStarter enacter = new EnactmentStarter();
		enacter.enact(new ChoreographyContext());
	}

}
