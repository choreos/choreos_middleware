package eu.choreos.enactment.context;


public class ContextCaster {

	private ChoreographyContext context;
	
	public ContextCaster(ChoreographyContext context) {
	
		this.context = context;
	}
	
	public void cast() {
		
		for (String service: context.getContext().keySet()) {
			
			System.out.println("Setting context on " + service);
			
			for (String s: context.getContext().keySet()) {
				
				if (!s.equals(service)) {
					
					System.out.println("Call " + service
							+ ".setChoreographyContext(" + s + ", "
							+ context.getContext().get(s) + ")");
				}
			}
		}
	}
}
