/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.bus;

import org.apache.log4j.Logger;
import org.ow2.choreos.utils.URLUtils;

import com.ebmwebsourcing.easierbsm.admin.client.api.BSMAdminClient;
import com.ebmwebsourcing.easierbsm.admin.client.impl.BSMAdminClientImpl;
import com.ebmwebsourcing.easyesb.admin.client.impl.AdminClientImpl;
import com.ebmwebsourcing.easyesb.soa.api.ESBException;
import com.ebmwebsourcing.esstar.management.UserManagementClient;

import easierbsm.petalslink.com.service.bsmadmin._1_0.AdminExceptionMsg;
import easybox.easyesb.petalslink.com.soa.model.datatype._1.EJaxbBasicNodeInformationsType;
import easyesb.petalslink.com.data.admin._1.AddNeighBourNode;
import easyesb.petalslink.com.data.admin._1.GetNodeInformations;
import easyesb.petalslink.com.data.admin._1.GetNodeInformationsResponse;
import esstar.petalslink.com.service.management._1_0.ManagementException;

/**
 * Access an EasyESB node.
 * 
 * @author leonardo
 * 
 */
public class EasyESBNodeImpl implements EasyESBNode {

    private static final String IP_PATTERN = "(\\d{1,3}\\.){3}\\d{1,3}";

    private final String adminEndpoint;
    private final String nodeIp;
    private LinagoraFactory linagoraFactory = LinagoraFactory.getFactory();

    private Logger logger = Logger.getLogger(EasyESBNodeImpl.class);

    static {
	EasyAPILoader.loadEasyAPI();
    }

    public EasyESBNodeImpl(String adminEndpoint) {
	this.adminEndpoint = adminEndpoint;
	this.nodeIp = URLUtils.extractIpFromURL(adminEndpoint);
    }
    
    EasyESBNodeImpl(String adminEndpoint, LinagoraFactory linagoraFactory) {
        this.adminEndpoint = adminEndpoint;
        this.nodeIp = URLUtils.extractIpFromURL(adminEndpoint);
        this.linagoraFactory = linagoraFactory;
    }

    @Override
    public String getAdminEndpoint() {
	return this.adminEndpoint;
    }

    @Override
    public String proxifyService(String serviceUrl, String serviceWsdl) throws EasyESBException {
	UserManagementClient cli = linagoraFactory.getUserManagementClient(adminEndpoint);
	String response = null;
	try {
	    response = cli.proxify(serviceUrl, serviceWsdl);
	} catch (ManagementException e) {
	    fail("Proxifying " + serviceUrl + " in the bus " + adminEndpoint + " failed.");
	}
	String proxifiedUri = fixIP(response, nodeIp);
	return proxifiedUri;
    }
    
    private String fixIP(String proxifiedUri, String ip) {
        if (!proxifiedUri.contains(ip)) {
            // avoid returning private ip
            proxifiedUri = proxifiedUri.replaceAll(IP_PATTERN, ip);
            // avoid returning localhost
            proxifiedUri = proxifiedUri.replace("localhost", nodeIp);
        }
        return proxifiedUri;
    }

    @Override
    public void addNeighbour(EasyESBNode neighbour) throws EasyESBException {
	String neighbourAdminEndpoint = neighbour.getAdminEndpoint();
	EJaxbBasicNodeInformationsType neighbourNodeInfo = getNodeInfo(neighbourAdminEndpoint);
	AddNeighBourNode parameters = new AddNeighBourNode();
	parameters.setNeighbourNode(neighbourNodeInfo);
	AdminClientImpl cli = new AdminClientImpl(this.adminEndpoint);
	try {
	    cli.addNeighBourNode(parameters);
	} catch (ManagementException e1) {
	    fail("Adding " + neighbourAdminEndpoint + " as neighbour of " + this.adminEndpoint + " failed.");
	}
    }

    private EJaxbBasicNodeInformationsType getNodeInfo(String adminEndpoint) throws EasyESBException {
	AdminClientImpl client = new AdminClientImpl(adminEndpoint);
	GetNodeInformations nodein = new GetNodeInformations();
	GetNodeInformationsResponse nodeout = null;
	try {
	    nodeout = client.getNodeInformations(nodein);
	} catch (ManagementException e1) {
	    fail("Retrieving information about " + adminEndpoint + " failed");
	}
	EJaxbBasicNodeInformationsType nodeInfo = nodeout.getNode().getBasicNodeInformations();
	return nodeInfo;
    }

    @Override
    public void notifyEasierBSM(String bsmAdminEndpoint) throws EasyESBException {
	BSMAdminClient bsmClient;
	try {
	    bsmClient = new BSMAdminClientImpl(bsmAdminEndpoint);
	    bsmClient.connectToEsb(this.adminEndpoint, true);
	} catch (ESBException e) {
	    fail("Connecting to " + bsmAdminEndpoint + " failed.");
	} catch (AdminExceptionMsg e) {
	    fail("Notifying " + bsmAdminEndpoint + " about " + this.adminEndpoint + " failed.");
	}
    }

    private void fail(String msg) throws EasyESBException {
	logger.error(msg);
	throw new EasyESBException(msg);
    }

    public static void main2(String[] args) throws EasyESBException {
	String nodeAddress = "http://54.226.167.166:8180/services/adminExternalEndpoint";
	String neighbourAddress = "http://54.227.157.80:8180/services/adminExternalEndpoint";
	EasyESBNodeImpl node = new EasyESBNodeImpl(nodeAddress);
	EasyESBNodeImpl neighbour = new EasyESBNodeImpl(neighbourAddress);
	node.addNeighbour(neighbour);
    }

    public static void main(String[] args) throws EasyESBException {
	String nodeAddress = "http://54.227.177.74:8180/services/adminExternalEndpoint";
	String bsmAdminEndpoint = "http://avalon.isti.cnr.it:8184/services/bsmadminExternalEndpoint";
	EasyESBNodeImpl node = new EasyESBNodeImpl(nodeAddress);
	node.notifyEasierBSM(bsmAdminEndpoint);
    }

}
