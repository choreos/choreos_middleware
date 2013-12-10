package org.ow2.choreos.ee.bus;

import java.util.concurrent.Callable;

import org.ow2.choreos.services.datamodel.Proxification;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ProxificationTask implements Callable<String> {

    private String svcName;
    private String nativeUri;
    private Proxification proxification;
    private EasyESBNode easyEsbNode;

    public ProxificationTask(String svcName, String nativeUri, Proxification proxification, EasyESBNode easyEsbNode) {
        this.svcName = svcName;
        this.nativeUri = nativeUri;
        this.proxification = proxification;
        this.easyEsbNode = easyEsbNode;
    }

    public String getSvcName() {
        return svcName;
    }

    public String getNativeUri() {
        return nativeUri;
    }

    public Proxification getProxification() {
        return proxification;
    }

    public EasyESBNode getEasyEsbNode() {
        return easyEsbNode;
    }

    public String call() throws EasyESBException {
        String url = nativeUri.replaceAll("/$", "");
        String wsdl = url + "?wsdl";
        String proxifiedAddress = easyEsbNode.proxifyService(url, wsdl);
        proxification.setBusUri(ServiceType.SOAP, proxifiedAddress);
        proxification.setEasyEsbNodeAdminEndpoint(easyEsbNode.getAdminEndpoint());
        return proxifiedAddress;
    }

    @Override
    public String toString() {
        return "ProxificationTask [svcName=" + svcName + ", nativeUri=" + nativeUri + ", proxification="
                + proxification + ", easyEsbNode=" + easyEsbNode + "]";
    }

}
