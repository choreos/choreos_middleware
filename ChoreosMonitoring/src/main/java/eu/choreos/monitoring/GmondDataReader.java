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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

	public float getLastMinuteLoadAverage() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("load_one").getValue())).floatValue();
	}

	public float getLastFiveMinuteLoadAverage() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("load_five").getValue())).floatValue();
	}

	public String getOSName() {
		setMetrics(getGangliaCurrentMetrics());
		return (new String(metrics.get("os_name").getValue()));
	}

	public String getOSRelease() {
		setMetrics(getGangliaCurrentMetrics());
		return (new String(metrics.get("os_release").getValue()));
	}

	public float getLastFifteenMinuteLoadAverage() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("load_fifteen").getValue())).floatValue();
	}

	public String getSystemArchitecture() {
		setMetrics(getGangliaCurrentMetrics());
		return (new String(metrics.get("machine_type").getValue()));
	}

	public int getCpuCount() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Integer(metrics.get("cpu_num").getValue())).intValue();
	}

	public int getCpuSpeed() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Integer(metrics.get("cpu_speed").getValue())).intValue();
	}

	public float getCpuNice() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("cpu_nice").getValue())).floatValue();
	}

	public float getCpuIdleTime() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("cpu_idle").getValue())).floatValue();
	}

	public float getCpuIdleTimeDueToIO() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("cpu_wio").getValue())).floatValue();
	}

	public float getCpuIdleTimePercentageSinceBoot() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("cpu_aidle").getValue())).floatValue();
	}

	public float getCpuUserTime() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("cpu_user").getValue())).floatValue();
	}

	public float getCpuSystemTimePercentage() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("cpu_system").getValue())).floatValue();
	}

	public float getBiggestPartitionUsage() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("part_max_used").getValue()))
				.floatValue();
	}

	public double getTotalAvailableStorageSpace() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Double(metrics.get("disk_total").getValue())).doubleValue();
	}

	public float getBufferedMemory() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("mem_buffers").getValue())).floatValue();
	}

	public float getSharedMemory() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("mem_shared").getValue())).floatValue();
	}

	public float getTotalMemory() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("mem_total").getValue())).floatValue();
	}

	public float getFreeMemory() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("mem_free").getValue())).floatValue();
	}

	public float getCachedMemory() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("mem_cached").getValue())).floatValue();
	}

	public float getFreeSwap() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("swap_free").getValue())).floatValue();
	}

	public int getProcessCount() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Integer(metrics.get("proc_total").getValue())).intValue();
	}

	public int getRunningProcessCount() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Integer(metrics.get("proc_run").getValue())).intValue();
	}

	public boolean isGexecAvailable() {
		setMetrics(getGangliaCurrentMetrics());
		return metrics.get("gexec").getValue().equals("ON");
	}

	public double getAvailableDiskSpace() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Double(metrics.get("disk_free").getValue())).doubleValue();
	}

	public float getIncomingPacketRate() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("pkts_in").getValue())).floatValue();
	}

	public float getOutgoingPacketRate() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("pkts_out").getValue())).floatValue();
	}

	public float getIncomingByteRate() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("bytes_in").getValue())).floatValue();
	}

	public float getOutgoingByteRate() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("bytes_out").getValue())).floatValue();
	}

	public float getTotalSwap() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Float(metrics.get("bytes_in").getValue())).floatValue();
	}

	public float getBootTime() {
		setMetrics(getGangliaCurrentMetrics());
		return (new Integer(metrics.get("boottime").getValue())).intValue();
	}

	public Gmetric getMetricByName(String name) {
		setMetrics(getGangliaCurrentMetrics());
		return metrics.get(name);
	}

	public Map<String, Gmetric> getAllMetrics() {
		setMetrics(getGangliaCurrentMetrics());
		return metrics;
	}

	public void setMetrics(Document gangliaXML) {

		if (gangliaXML == null) {
			System.err.println("Error on parse ganglia XML file!");
			return;
		}

		gangliaXML.getDocumentElement().normalize();
		NodeList clusterNodeList = gangliaXML.getElementsByTagName("CLUSTER");
		Element clusterNode = (Element) clusterNodeList.item(0);

		//TODO: How to actually get the Host with the same name as it is not necessarily the 
		// 		first, and the host name and ip may not be the one used to access it
		
		NodeList hostNodeList = clusterNode.getElementsByTagName("HOST");
		Element hostNode = (Element) hostNodeList.item(0);
		
		NodeList metricNodeList = hostNode.getElementsByTagName("METRIC"); // list of metrics

		for (int i = 0; i < metricNodeList.getLength(); i++) {

			Element el = (Element) metricNodeList.item(i);

			metrics.put(el.getAttributeNode("NAME").getNodeValue(), // key
					new Gmetric(el.getAttributeNode("NAME").getNodeValue(), el
							.getAttributeNode("VAL").getNodeValue()));
		}
	}

	private Document getGangliaCurrentMetrics() {

		if(socket==null) createSocket();
		Document dom = getGangliaMetricsFromSocket(socket);
		closeSocket(socket);
		return dom;

	}

	public Document getGangliaMetricsFromSocket(Socket socket) {
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
				// TODO Auto-generated catch block
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

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
