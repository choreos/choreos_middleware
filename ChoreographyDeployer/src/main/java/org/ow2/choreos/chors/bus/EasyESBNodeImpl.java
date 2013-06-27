/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ebmwebsourcing.esstar.management.UserManagementClientSOAP;

import esstar.petalslink.com.service.management._1_0.ManagementException;

/**
 * Access an EasyESB node.
 * 
 * @author leonardo
 * 
 */
public class EasyESBNodeImpl implements EasyESBNode {

    private Logger logger = Logger.getLogger(EasyESBNodeImpl.class);

    private final String adminEndpoint;
    private final String nodeIp;

    static {
        EasyAPILoader.loadEasyAPI();
    }

    public EasyESBNodeImpl(String adminEndpoint) {
        this.adminEndpoint = adminEndpoint;
        this.nodeIp = this.extractIpFromAdminEndpoint();
    }

    private String extractIpFromAdminEndpoint() {

        if (this.adminEndpoint.contains("localhost:8180")) {
            return "localhost";
        } else {
            Pattern pat = Pattern.compile("(\\d{1,3}.){3}\\d{1,3}");
            Matcher m = pat.matcher(this.adminEndpoint);
            m.find();
            String ip = m.group(0);
            return ip;
        }
    }

    @Override
    public String getAdminEndpoint() {
        return this.adminEndpoint;
    }

    @Override
    public String proxifyService(String serviceUrl, String serviceWsdl) throws ManagementException {

        logger.debug("-c " + this.adminEndpoint + " -pr " + serviceUrl + " " + serviceWsdl);
        UserManagementClientSOAP cli = new UserManagementClientSOAP(this.adminEndpoint);
        String response = cli.proxify(serviceUrl, serviceWsdl);
        String proxifiedUri = response.replace("localhost", this.nodeIp);
        return proxifiedUri;
    }

}
