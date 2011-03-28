package additionService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(
		name="CalculatorWebService",
		targetNamespace="http://CalculatorServiceProvider/",
		serviceName="AdditionService"
		)
public class WebServiceExposition {
	
	public static Implementation implementation = new Implementation();
	
	@WebMethod(operationName="operationAdd")
	@WebResult(name="total")
	public int operationAdd(@WebParam(name="term1") int term1, @WebParam(name="term2") int term2){
		return implementation.add(term1, term2);
	}
}
