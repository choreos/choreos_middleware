/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes.datamodel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Node {

    private String id;
    private Integer cpus;
    private Integer ram;
    private Integer storage;
    private String so;
    private String zone;
    private String ip;
    private String hostname;
    private String user;
    private String privateKeyFile;
    private String image;
    private Integer state;

    public Node() {
	
    }

    public Node(NodeRestRepresentation rest) {
	cpus = rest.cpus;
	hostname = rest.hostname;
	id = rest.id;
	image = rest.image;
	ip = rest.ip;
	ram = rest.ram;
	storage = rest.storage;
	so = rest.so;
	zone = rest.zone;
	state = rest.state;
    }

    public NodeRestRepresentation getRestRepresentation() {
	NodeRestRepresentation rest = new NodeRestRepresentation();

	rest.cpus = cpus;
	rest.hostname = hostname;
	rest.id = id;
	rest.image = image;
	rest.ip = ip;
	rest.ram = ram;
	rest.storage = storage;
	rest.so = so;
	rest.zone = zone;
	rest.state = state;

	return rest;
    }

    static Pattern IP_PATTERN = Pattern.compile("(\\d{1,4}\\.){3}\\d{1,4}");

    public boolean hasIp() {
	if (ip == null || ip.isEmpty())
	    return false;
	Matcher matcher = IP_PATTERN.matcher(ip);
	return matcher.matches();
    }

    public void setPrivateKeyFile(String privateKeyFile) {
	this.privateKeyFile = privateKeyFile;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public Integer getCpus() {
	return cpus;
    }

    public void setCpus(Integer cpus) {
	this.cpus = cpus;
    }

    public Integer getRam() {
	return ram;
    }

    public void setRam(Integer ram) {
	this.ram = ram;
    }

    public Integer getStorage() {
	return storage;
    }

    public void setStorage(Integer storage) {
	this.storage = storage;
    }

    public String getSo() {
	return so;
    }

    public void setSo(String so) {
	this.so = so;
    }

    public String getZone() {
	return zone;
    }

    public void setZone(String zone) {
	this.zone = zone;
    }

    public String getIp() {
	return ip;
    }

    public void setIp(String ip) {
	this.ip = ip;
    }

    public String getHostname() {
	return hostname;
    }

    public void setHostname(String hostname) {
	this.hostname = hostname;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getPrivateKeyFile() {
	return privateKeyFile;
    }

    public void setPrivateKey(String privateKeyFile) {
	this.privateKeyFile = privateKeyFile;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public Integer getState() {
	return state;
    }

    public void setState(Integer state) {
	this.state = state;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((ip == null) ? 0 : ip.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Node other = (Node) obj;
	if (hostname == null) {
	    if (other.hostname != null)
		return false;
	} else if (!hostname.equals(other.hostname))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (ip == null) {
	    if (other.ip != null)
		return false;
	} else if (!ip.equals(other.ip))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Node [id=" + id + ", ip=" + ip + ", hostname=" + hostname + "]";
    }

}
