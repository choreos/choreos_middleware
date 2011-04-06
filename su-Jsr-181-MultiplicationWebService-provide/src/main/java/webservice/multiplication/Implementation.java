package webservice.multiplication;

import calculatorserviceprovider.CalculatorWebService;
import calculatorserviceprovider.AdditionService;

public class Implementation {

	AdditionService ws = new AdditionService();
	
	// Get the operation handler
	CalculatorWebService port = ws.getCalculatorWebServicePort();

	public int multiply(int term1, int term2) {
		int product = 0;
		
		for (int i=0; i<term2; i++)
			product = add(term1, product);
		
		return product;
	}
	
	private int add(int term1, int term2){
		return port.operationAdd(term1, term2);
	}
	

}
