package br.usp.ime.ccsl.choreos.middleware.proxy;

import java.util.ArrayList;
import java.util.List;

import br.usp.ime.ccsl.choreos.middleware.exceptions.FrameworkException;
import br.usp.ime.ccsl.choreos.middleware.exceptions.InvalidOperationName;

public class Proxy {
    private List<WSClient> wsList;

    public Proxy() {
	wsList = new ArrayList<WSClient>();
    }

    public List<WSClient> getWebServiceList() {
	return wsList;
    }

    public void addService(WSClient ws) {
	wsList.add(ws);
    }

    public void request(String webMethod, String... params) throws InvalidOperationName, NoWebServiceException {
	try {
	    WSClient ws = wsList.get(0);
	    ws.request(webMethod, params);
	} catch (FrameworkException e) {
	    e.printStackTrace();
	} catch (IndexOutOfBoundsException e) {
	    throw new NoWebServiceException();
	}
    }
}