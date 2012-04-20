package eu.choreos.enactment;

import java.util.Set;

import eu.choreos.enactment.context.ChorContextCaster;
import eu.choreos.servicedeployer.datamodel.Service;

public class EnactmentStarter {
	
	private Enacter powsEnacter = EnacterFactory.getPOWSEnacter();
	private Enacter cdEnacter = EnacterFactory.getCDEnacter();
	private Enacter consumeEnacter = EnacterFactory.getSoapConsumeEnacter();
	private Enacter provideEnacter = EnacterFactory.getSoapProvideEnacter();
	
	public void enact() {
		
		System.out.println("Starting airport enactment...");
		Set<Service> powss = powsEnacter.enact();
		Set<Service> provides = provideEnacter.enact();
		Set<Service> consumes = consumeEnacter.enact();
		cdEnacter.enact();

		System.out.println("Passing context");
		ChorContextCaster caster = new ChorContextCaster();
		caster.cast(powss, provides, consumes);
	}
	
	
	public static void main(String[] args) {

		EnactmentStarter enacter = new EnactmentStarter();
		enacter.enact();
	}

}
