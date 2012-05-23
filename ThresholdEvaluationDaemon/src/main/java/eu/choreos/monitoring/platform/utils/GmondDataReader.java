package eu.choreos.monitoring.platform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.exception.GangliaException;

public class GmondDataReader {

	private String host;
	private int port;

	private List<Host> hosts;
	private Socket socket;

	public GmondDataReader(String host, int port) {
		this.host = host;
		this.port = port;
		
	}

	public void update() throws GangliaException {
		parseGangliaMetrics(getGangliaCurrentMetrics());
	}
		
	public String getMetricValue(String metric)  {
		return hosts.get(0).getMetricValue(metric);
	}

	private void parseGangliaMetrics(Document gangliaXML) {
		hosts = new ArrayList<Host>();
		gangliaXML.getDocumentElement().normalize();
		NodeList clusterNodeList = gangliaXML.getElementsByTagName("CLUSTER");
		Element clusterNode = (Element) clusterNodeList.item(0);

		NodeList hostNodeList = clusterNode.getElementsByTagName("HOST");
		for(int i = 0; i < hostNodeList.getLength(); i++) {
			Element element = (Element)hostNodeList.item(i);
			Host host = new Host(element);
			hosts.add(host);
		}
		
	}

	private Document getGangliaCurrentMetrics() throws GangliaException {
		try {
			if (socket == null)
				createSocket();
			else if (socket.isClosed())
				createSocket();
		} catch (UnknownHostException e) {
			throw new GangliaException(GangliaException.CONNECTION_ERROR_UNKNOWN_HOST);
		} catch (IOException e) {
			throw new GangliaException(
					GangliaException.CONNECTION_ERROR_COULD_NOT_READ_FROM_SOCKET);
		}
		Document dom = getGangliaMetricsFromSocket(socket);
		closeSocket(socket);
		return dom;

	}

	private Document getGangliaMetricsFromSocket(Socket socket)
			throws GangliaException {
		InputStream in = getStreamFromSocket(socket);
		Document dom = convertToDomDocument(in);
		closeInputStream(in);
		return dom;
	}

	private void closeSocket(Socket socket) throws GangliaException {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException ex) {
				throw new GangliaException(
						GangliaException.CONNECTION_ERROR_COULD_NOT_CLOSE_SOCKET);
			}
		}
	}

	private Document convertToDomDocument(InputStream in) throws GangliaException {
		Document dom = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			dom = db.parse(in);
		} catch (ParserConfigurationException e) {
			throw new GangliaException(
					GangliaException.CONNECTION_ERROR_COULD_NOT_PARSE_METRICS_INPUT);

		} catch (SAXException e) {
			throw new GangliaException(
					GangliaException.CONNECTION_ERROR_COULD_NOT_PARSE_METRICS_INPUT);
		} catch (IOException e) {
			throw new GangliaException(
					GangliaException.CONNECTION_ERROR_COULD_NOT_READ_FROM_SOCKET);
		}

		return dom;

	}

	private void closeInputStream(InputStream in) throws GangliaException {

		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
				throw new GangliaException(
						GangliaException.CONNECTION_ERROR_COULD_NOT_CLOSE_SOCKET);
			}
		}
	}

	private InputStream getStreamFromSocket(Socket socket)
			throws GangliaException {
		InputStream in = null;
		try {
			in = socket.getInputStream();
		} catch (IOException e) {
			throw new GangliaException(
					GangliaException.CONNECTION_ERROR_COULD_NOT_READ_FROM_SOCKET);
		}
		return in;
	}

	private void createSocket() throws UnknownHostException, IOException {
		socket = new Socket(InetAddress.getByName(host), port);
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public List<Host> getHosts() {
		// TODO Auto-generated method stub
		return hosts;
	}
}
