package br.usp.ime.ccsl.choreos.middleware.proxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.usp.ime.ccsl.choreos.middleware.exceptions.FrameworkException;
import br.usp.ime.ccsl.choreos.middleware.exceptions.InvalidOperationName;

public class Proxy {
    private Logger logger;
    
    private List<WSClient> wsList;

    public Proxy(Logger logger) {
	this.logger = logger;
	
	wsList = new ArrayList<WSClient>();
    }

    public List<WSClient> getWebServiceList() {
	return wsList;
    }

    public void addService(WSClient ws) {
	wsList.add(ws);
    }

    public String request(String webMethod, String... params) throws InvalidOperationName, NoWebServiceException {
	String response = null;
	
	StringBuilder sb = new StringBuilder();
	sb.append("Request received: " + webMethod + "; ");
	
	if (params.length > 0) {
	    sb.append("parameters: " + StringUtils.join(params, ", "));
	    sb.append(".");
	} else {
	    sb.append("no parameters.");
	}
	logger.info(sb.toString());
	
	try {
	    WSClient ws = wsList.get(0);
	    response = ws.request(webMethod, params);
	} catch (FrameworkException e) {
	    e.printStackTrace();
	} catch (IndexOutOfBoundsException e) {
	    throw new NoWebServiceException();
	}
	return response;
    }
}