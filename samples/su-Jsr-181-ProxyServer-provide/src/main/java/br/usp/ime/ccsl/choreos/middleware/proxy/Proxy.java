package br.usp.ime.ccsl.choreos.middleware.proxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.usp.ime.choreos.vv.Item;
import br.usp.ime.choreos.vv.WSClient;
import br.usp.ime.choreos.vv.exceptions.FrameworkException;
import br.usp.ime.choreos.vv.exceptions.InvalidOperationNameException;

public class Proxy {
    private Logger logger;

    private List<WSClient> wsList;

    private final String roleName;

    public Proxy(String roleName, Logger logger) {
	this.roleName = roleName;
	this.logger = logger;

	wsList = new ArrayList<WSClient>();
    }

    public List<WSClient> getWebServiceList() {
	return wsList;
    }

    public void addService(WSClient ws) {
	wsList.add(ws);
    }

    public Item request(String webMethod, String... params) throws InvalidOperationNameException, NoWebServiceException {
	Item response = null;
	
	WSClient receiverClient = null;
	for (WSClient client : wsList) {
	    try {
		response = client.request(webMethod, params);
		receiverClient = client;
		break;
	    } catch (FrameworkException e) {
		// we are dealing with this by trying other services
	    }
	}
	if (receiverClient == null) {
	    throw new NoWebServiceException();
	}

	StringBuilder sb = new StringBuilder();
	sb.append(roleName).append(", ").append(receiverClient.getWsdl()).append(", ").append(webMethod).append(", (");
	if (params.length > 0) {
	    sb.append(StringUtils.join(params, "; "));
	}
	sb.append(")");
	logger.info(sb.toString());

	return response;
    }
}