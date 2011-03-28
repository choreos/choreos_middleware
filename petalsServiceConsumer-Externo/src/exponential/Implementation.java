package exponential;

import multiplication.webservice.Multiplication;
import multiplication.webservice.MultiplicationWebService;

public class Implementation {

	public int exponential(int base, int expoent) {
		
		int exp = 1;
		
		for (int j=0; j<expoent; j++)
			exp = multiplicationServiceClient().operationMultiply(base, exp);
		
		return exp;
	}

	private Multiplication multiplicationServiceClient(){
		// Get the WS handler
		MultiplicationWebService ws = new MultiplicationWebService();
		
		// Get the operation handler
		Multiplication port = ws.getMultiplicationPort();
		
		// Return it
		return port;
		
	}

}
