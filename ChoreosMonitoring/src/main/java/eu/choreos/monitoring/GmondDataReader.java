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

public class GmondDataReader {

	private String host;
	private int port;
	
	private Map<String, Gmetric> metrics;

	public GmondDataReader(String host, int port) {
		this.host = host;
		this.port = port;
		metrics = new HashMap<String, Gmetric>();
	}

	public float getOneMinuteLoadAverage() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("load_one").getValue())).floatValue();
	}
	
	public float getFiveMinuteLoadAverage() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("load_five").getValue())).floatValue();
	}
	
	public float getFifteenMinuteLoadAverage() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("load_fifteen").getValue())).floatValue();
	}
	
	public float getCpuIdle() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("cpu_idle").getValue())).floatValue();
	}
	
	public float getCpuUser() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("cpu_user").getValue())).floatValue();
	}
	
	public float getCpuNice() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("cpu_nice").getValue())).floatValue();
	}
	
	public float getCpuAidle() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("cpu_aidle").getValue())).floatValue();
	}
	
	public float getMemBuffers() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("mem_buffers").getValue())).floatValue();
	}
	
	public float getCpuSystem() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("cpu_system").getValue())).floatValue();
	}
	
	public float getPartMaxUsed() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("part_max_used").getValue())).floatValue();
	}
	
	public double getDiskTotal() {
		setMetrics(gangliaXML());
		return (new Double(metrics.get("disk_total").getValue())).doubleValue();
	}
	
	public float getMemShared() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("mem_shared").getValue())).floatValue();
	}
	
	public float getCpuWIO() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("cpu_wio").getValue())).floatValue();
	}
	
	public String getMachineType() {
		setMetrics(gangliaXML());
		return (new String(metrics.get("machine_type").getValue()));
	}
	
	public int getProcTotal() {
		setMetrics(gangliaXML());
		return (new Integer(metrics.get("proc_total").getValue())).intValue();
	}
	
	public int getCpuNum() {
		setMetrics(gangliaXML());
		return (new Integer(metrics.get("cpu_num").getValue())).intValue();
	}
	
	public int getCpuSpeed() {
		setMetrics(gangliaXML());
		return (new Integer(metrics.get("cpu_speed").getValue())).intValue();
	}
	
	public float getPktsOut() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("pkts_out").getValue())).floatValue();
	}
	
	public float getSwapFree() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("swap_free").getValue())).floatValue();
	}
	
	public float getMemTotal() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("mem_total").getValue())).floatValue();
	}
	
	public String getOSRelease() {
		setMetrics(gangliaXML());
		return (new String(metrics.get("os_release").getValue()));
	}
	
	public int getProcRun() {
		setMetrics(gangliaXML());
		return (new Integer(metrics.get("proc_run").getValue())).intValue();
	}
	
	public String getGexec() {
		setMetrics(gangliaXML());
		return (new String(metrics.get("gexec").getValue()));
	}
	
	public double getDiskFree() {
		setMetrics(gangliaXML());
		return (new Double(metrics.get("disk_free").getValue())).doubleValue();
	}
	
	public float getMemCached() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("mem_cached").getValue())).floatValue();
	}
	
	public float getPktsIn() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("pkts_in").getValue())).floatValue();
	}
	
	public float getBytesIn() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("bytes_in").getValue())).floatValue();
	}
	
	public float getBytesOut() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("bytes_out").getValue())).floatValue();
	}
	
	public float getSwapTotal() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("bytes_in").getValue())).floatValue();
	}
	
	public float getMemFree() {
		setMetrics(gangliaXML());
		return (new Float(metrics.get("mem_free").getValue())).floatValue();
	}
	
	public String getOSName() {
		setMetrics(gangliaXML());
		return (new String(metrics.get("os_name").getValue()));
	}
	
	public float getBootTime() {
		setMetrics(gangliaXML());
		return (new Integer(metrics.get("boottime").getValue())).intValue();
	}
	
	public Gmetric getMetricByName(String name) {
		setMetrics(gangliaXML());
		return metrics.get(name);
	}
	
	public Map<String, Gmetric> getAllMetrics() {
		setMetrics(gangliaXML());
		return metrics;
	}

	private void setMetrics(Document gangliaXML) {
		
		if(gangliaXML == null) {
			System.err.println("Error on parse ganglia XML file!");
			return;
		}
		
		gangliaXML.getDocumentElement().normalize();
		NodeList clusterNodeList = gangliaXML.getElementsByTagName("CLUSTER");
		Element clusterNode = (Element) clusterNodeList.item(0);
		
		NodeList hostNodeList = clusterNode.getElementsByTagName("HOST");
		Element hostNode = (Element) hostNodeList.item(0);
		
		NodeList metricNodeList = hostNode.getElementsByTagName("METRIC"); // list of metrics
		
		for(int i = 0; i < metricNodeList.getLength(); i++) {
			
			Element el = (Element) metricNodeList.item(i);
						
			metrics.put(
					el.getAttributeNode("NAME").getNodeValue(), // key
					new Gmetric(el.getAttributeNode("NAME").getNodeValue(), el.getAttributeNode("VAL").getNodeValue()));
		}
	}
	
	public Document gangliaXML() {

		Socket socket = createSocket();
		InputStream in = getStreamFromSocket(socket);
		return convertToDomDocument(socket, in);
	
	}

	public Document convertToDomDocument(Socket socket, InputStream in) {
		try {			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(in);
 
    	    socket.close();
    	    in.close();
    	    
    	    return dom;
    	    
    	} catch ( Throwable e ) {
			e.printStackTrace();
			try {
				if(socket != null)
					socket.close();
				if(in != null)
					in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return null;
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

	private Socket createSocket() {
		Socket socket = null;
		try {
			socket = new Socket(InetAddress.getByName(host), port);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return socket;
	}
}
