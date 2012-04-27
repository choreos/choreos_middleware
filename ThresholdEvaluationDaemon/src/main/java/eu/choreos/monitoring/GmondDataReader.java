package eu.choreos.monitoring;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import eu.choreos.monitoring.datatypes.Gmetric;

public class GmondDataReader {

	private String host;
	private int port;

	private Map<String, Gmetric> metrics;
	private Socket socket;

	public GmondDataReader(String host, int port) {
		this.host = host;
		this.port = port;
		metrics = new HashMap<String, Gmetric>();
	}

	public Map<String, Gmetric> getAllMetrics() {
		setMetrics(getGangliaCurrentMetrics());
		return metrics;
	}

	private void setMetrics(Document gangliaXML) {

		if (gangliaXML == null) {
			System.err.println("Error on parse ganglia XML file!");
			return;
		}

		gangliaXML.getDocumentElement().normalize();
		NodeList clusterNodeList = gangliaXML.getElementsByTagName("CLUSTER");
		Element clusterNode = (Element) clusterNodeList.item(0);

		// TODO: How to actually get the Host with the same name as it is not
		// necessarily the
		// first, and the host name and ip may not be the one used to access it

		NodeList hostNodeList = clusterNode.getElementsByTagName("HOST");
		Element hostNode = (Element) hostNodeList.item(0);

		NodeList metricNodeList = hostNode.getElementsByTagName("METRIC"); // list
																			// of
																			// metrics

		for (int i = 0; i < metricNodeList.getLength(); i++) {

			Element el = (Element) metricNodeList.item(i);

			metrics.put(el.getAttributeNode("NAME").getNodeValue(), // key
					new Gmetric(el.getAttributeNode("NAME").getNodeValue(), el
							.getAttributeNode("VAL").getNodeValue()));
		}
	}

	private Document getGangliaCurrentMetrics() {

		if (socket == null)
			createSocket();
		else if (socket.isClosed())
			createSocket();
		Document dom = getGangliaMetricsFromSocket(socket);
		closeSocket(socket);
		return dom;

	}

	private Document getGangliaMetricsFromSocket(Socket socket) {
		InputStream in = getStreamFromSocket(socket);
		Document dom = convertToDomDocument(in);
		closeInputStream(in);
		return dom;
	}

	private void closeSocket(Socket socket) {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public Document convertToDomDocument(InputStream in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(in);

			return dom;

		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	private void closeInputStream(InputStream in) {

		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private InputStream getStreamFromSocket(Socket socket) {
		InputStream in = null;
		try {
			in = socket.getInputStream();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return in;
	}

	private void createSocket() {
		try {
			socket = new Socket(InetAddress.getByName(host), port);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
