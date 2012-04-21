package eu.choreos.enactment;

import eu.choreos.enactment.spec.CDSpecBuilder;
import eu.choreos.enactment.spec.POWSSpecBuilder;
import eu.choreos.enactment.spec.SAConsumeSpecBuilder;
import eu.choreos.enactment.spec.SAProvideSpecBuilder;
import eu.choreos.enactment.spec.SpecBuilder;

public class EnacterFactory {

	public static Enacter getPOWSEnacter() {
		
		SpecBuilder[] specBuilders = new SpecBuilder[]{new POWSSpecBuilder()};
		return new Enacter("POWS", specBuilders);
	}
	
	public static Enacter getCDEnacter() {
		
		SpecBuilder[] specBuilders = new SpecBuilder[]{new CDSpecBuilder()};
		return new Enacter("SA CD", specBuilders);
	}
	
	public static Enacter getSoapConsumeEnacter() {
		
		SpecBuilder[] specBuilders = { new SAConsumeSpecBuilder() };
		return new Enacter("SA SOAP Consume", specBuilders);
	}
	
	public static Enacter getSoapProvideEnacter() {
		
		SpecBuilder[] specBuilders = { new SAProvideSpecBuilder() };
		return new Enacter("SA SOAP Provide", specBuilders);
	}
}
