package br.usp.ime.ccsl.choreos.middleware.proxy;

import java.util.ArrayList;
import java.util.List;

public class Proxy {
    private List<WSClient> wsList;

    public Proxy(String roleName) {
	wsList = new ArrayList<WSClient>();
    }

    public List<WSClient> getWebServiceList() {
	return wsList;
    }

    public void addService(WSClient ws) {
	wsList.add(ws);
    }
}