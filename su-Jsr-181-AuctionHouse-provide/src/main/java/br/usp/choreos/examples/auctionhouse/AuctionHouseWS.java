package br.usp.choreos.examples.auctionhouse;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Here is a sample JAX-WS implementation.
 * <p>
 * For more information about JAX-WS, please visit
 * <b>https://jax-ws.dev.java.net/jax-ws-ea3/docs/annotations.html</b>.
 * </p>
 * 
 * @author rafaelj
 */
@WebService(serviceName = "AuctionHouseWS", targetNamespace = "http://auctionhouse.examples.choreos.usp.br", portName = "AuctionHouseWSPort")
public class AuctionHouseWS {

    @WebMethod(operationName = "helloWorld")
    @WebResult(name = "returnMessage")
    public String helloWorld() {
	return "Hello World!";
    }

    @WebMethod(operationName = "listenToTheWorld")
    @Oneway
    public void listenToTheWorld(@WebParam(name = "message") String message) {
	// TODO: We here illustrate a method that do not return anything.
    }
}
