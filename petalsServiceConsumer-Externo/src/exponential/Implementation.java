package exponential;

import multiplication.webservice.Multiplication;
import multiplication.webservice.MultiplicationWebService;

public class Implementation {

	public int exponential(int base, int expoent) {
		MultiplicationWebService ws = new MultiplicationWebService();
		Multiplication multiplicationServiceClient = ws.getMultiplicationPort();

		int exp = 1;
		
		for (int j=0; j<expoent; j++){
			exp = multiplicationServiceClient.operationMultiply(base, exp);
		}
		return exp;
	}


}
