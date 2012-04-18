package eu.choreos.enactment;

import java.util.ArrayList;
import java.util.List;

public class EnactmentStarter implements Enacter {
	
	private List<Enacter> enacters;

	public EnactmentStarter() {
		
		enacters = new ArrayList<Enacter>();
		enacters.add(new POWSEnacter());
		enacters.add(new SAEnacter());
	}
	
	public void enact() {
		
		System.out.println("Starting airport enactment...");
		for (Enacter enacter: enacters) {
			enacter.enact(); // can you read these two lines? =P
		}
	}
	
	
	public static void main(String[] args) {

		EnactmentStarter enacter = new EnactmentStarter();
		enacter.enact();
	}

}
