package br.usp.ime.ccsl.choreos.middleware.proxy.interceptor;

import javax.jws.WebMethod;

public class InterceptorTestWS {
    private int instancePort;
    private static final String addressPrefix = "http://localhost:";
    private static final String addressSuffix = "/hello";

    public InterceptorTestWS(int port) {
	this.instancePort = port;
    }

    @WebMethod
    public int getPort() {
	return instancePort;
    }

    public String getAddress() {
	return InterceptorTestWS.getAddress(instancePort);
    }

    public static String getAddress(int port) {
	return addressPrefix + port + addressSuffix;
    }
}