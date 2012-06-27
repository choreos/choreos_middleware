package eu.choreos.gmond;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import eu.choreos.gmond.beans.Channel;

@WebService
public interface IGmondConf {

	@WebMethod
	public void setConfigFile(String fileName);

	@WebMethod
	public String getConfigFile();

	@WebMethod
	public String save();
	
	@WebMethod
	public void rollBack();

	@WebMethod
	public void addUdpSendChannel(String host, String port);

	@WebMethod
	public void updateUdpSendChannel(String currentHost, String newHost,
			String newPort);

	@WebMethod
	public void updateUdpSendChannelHost(String currentHost, String newHost);

	@WebMethod
	public void updateUdpSendChannelPort(String host, String port);

	@WebMethod
	public void removeUdpSendChannel(String host);
	
	@WebMethod
	public Channel[] listUdpSendChannel();

}