package webservice.multiplication;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService( 
		serviceName="MultiplicationWebService", 
		targetNamespace="http://webservice.multiplication/", 
		portName="MultiplicationPort" )
		
public class Multiplication {

	
	@WebMethod( operationName="operationMultiply" )
	@WebResult( name="product" )
	
	public int multiplication(@WebParam(name="term1") int term1, @WebParam(name="term2") int term2) {
		Implementation ws = new Implementation(); 
		return ws.multiply(term1, term2);
	}
}
