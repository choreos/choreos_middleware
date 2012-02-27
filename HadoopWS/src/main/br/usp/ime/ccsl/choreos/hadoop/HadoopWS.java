package br.usp.ime.ccsl.choreos.hadoop;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.util.ByteArray;

@Path("/hadoop/")
public class HadoopWS {

	private static class OurURLClassLoader {
		
		private static final Class[] parameters = new Class[]{URL.class};

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
			Class sysclass = URLClassLoader.class;

			try {
				Method method = sysclass.getDeclaredMethod("addURL", parameters);
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

			File hdConfPath = new File(System.getProperty("hadoop.home",
					System.getProperty("user.home"))
					+ File.separator + "conf");
			OurURLClassLoader.addFile(hdConfPath);
			
			Configuration.addDefaultResource(hdConfPath.getAbsolutePath() + File.separator + "core-site.xml");
			Configuration.addDefaultResource(hdConfPath.getAbsolutePath() + File.separator + "hdfs-site.xml");
			Configuration.addDefaultResource(hdConfPath.getAbsolutePath() + File.separator + "mapred-site.xml");
			
			Configuration conf = new Configuration(true);
			
			ByteArrayOutputStream baout = new ByteArrayOutputStream();
			conf.writeXml(baout);
			baout.close();

			response = Response.ok(baout.toString()).type("text/xml").build();

		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

}