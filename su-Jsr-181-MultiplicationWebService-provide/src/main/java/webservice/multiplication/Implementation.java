package webservice.multiplication;

import calculatorserviceprovider.AdditionService;
import calculatorserviceprovider.CalculatorWebService;

public class Implementation {

	public int multiply(int term1, int term2) {
		int product = 0;
		
		for (int i=0; i<term2; i++)
			product = add(term1, product);
		
		return product;
	}
	
	private int add(int term1, int term2){
		return additionServiceClient().operationAdd(term1, term2);
	}
	
	private CalculatorWebService additionServiceClient(){
		// Get the WS handler
		AdditionService ws = new AdditionService();
		
		// Get the operation handler
		CalculatorWebService port = ws.getCalculatorWebServicePort();
		
		// Return it
		return port;
		
	}

}
