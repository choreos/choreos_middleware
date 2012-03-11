package br.usp.ime.ccsl.choreos.hadoop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.cxf.jaxrs.ext.xml.XMLSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;

@Path("/")
public class HadoopWS {

	private static Logger log = Logger.getLogger(HadoopWS.class);
	
	private static class OurURLClassLoader {

		private static final Class[] parameters = new Class[] { URL.class };

		public static ClassLoader addFile(String s) throws IOException {
			File f = new File(s);
			return addFile(f);
		}

		public static ClassLoader addFile(File f) throws IOException {
			return addURL(f.toURI().toURL());
		}

		public static ClassLoader addURL(URL u) throws IOException {

			URLClassLoader sysloader = (URLClassLoader) ClassLoader
					.getSystemClassLoader();
			Class<URLClassLoader> sysclass = URLClassLoader.class;

			try {
				Method method = sysclass
						.getDeclaredMethod("addURL", parameters);
				method.setAccessible(true);
				method.invoke(sysloader, new Object[] { u });
			} catch (Throwable t) {
				t.printStackTrace();
				throw new IOException(
						"Error, could not add URL to system classloader");
			}
			return sysloader;

		}
	}

	@GET
	public Response getIntegradeApplication(@Context UriInfo uriInfo) {

		Response response = null;

		try {
			
			log.debug("Init");

			Property dfs = new Property();
			dfs.setName("fs.defaultFS");
			
			if (System.getProperty("hadoop.url") != null && !System.getProperty("hadoop.url").equals("")) {
				log.debug("defining config via hadoop.url property");
				dfs.setValue(System.getProperty("hadoop.url"));
			}
			else {
				log.debug("defining config via file property");
				File hdConfPath = new File(System.getProperty("hadoop.home",
						System.getProperty("user.home")) + File.separator + "hadoopws"
						+ File.separator + "conf");
				log.debug("file=" + hdConfPath.getAbsolutePath());
				OurURLClassLoader.addFile(hdConfPath);
	
				log.debug("loading config files");
				Configuration.addDefaultResource(hdConfPath.getAbsolutePath()
						+ File.separator + "core-site.xml");
				Configuration.addDefaultResource(hdConfPath.getAbsolutePath()
						+ File.separator + "hdfs-site.xml");
				Configuration.addDefaultResource(hdConfPath.getAbsolutePath()
						+ File.separator + "mapred-site.xml");
	
				log.debug("validating config files");
				Configuration conf = new Configuration(true);
	
				ByteArrayOutputStream baout = new ByteArrayOutputStream();
				conf.writeXml(baout);
				baout.close();
	
				ByteArrayInputStream bais = new ByteArrayInputStream(
						baout.toByteArray());
				
				log.debug("Reading XML to grab the correct property");
				XMLSource xml = new XMLSource(bais);
				xml.setBuffering(true);
	
				dfs = xml.getNode(
						"/configuration/property[name='fs.defaultFS']",
						Property.class);
			}

			log.debug("xml=" + dfs);
			
			response = Response.ok(dfs).build();

		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

	@XmlRootElement(name = "property")
	static class Property {

		private String name;
		private String value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Property [name=" + name + ", value=" + value + "]";
		}
		
		
	};
}
