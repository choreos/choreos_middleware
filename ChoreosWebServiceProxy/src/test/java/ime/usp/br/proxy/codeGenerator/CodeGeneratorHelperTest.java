package ime.usp.br.proxy.codeGenerator;

import static org.junit.Assert.*;

import ime.usp.br.proxy.support.webservice.HelloWorld8081;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.naming.ldap.SortResponseControl;
import javax.xml.ws.Endpoint;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sun.print.resources.serviceui;

import com.sun.xml.bind.v2.TODO;

public class CodeGeneratorHelperTest {

    @BeforeClass
    public static void cleanPreviouslyGeneratedCode() throws IOException, InterruptedException {

	HelloWorld8081 service = new HelloWorld8081("1");
	Endpoint endpoint = Endpoint.create(service);
	endpoint.publish("http://localhost:8085/hello");
	System.out.println("Serviço disponibilizado na porta 8085");

	FileUtils.cleanDirectory(new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA));
	FileUtils.cleanDirectory(new File(CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE));
    }

    @Test
    public void testGenerateJavaCode() throws MalformedURLException {
	CodeGeneratorHelper cg = new CodeGeneratorHelper();

	URL wsdlInterfaceDescriptor = new URL("http://localhost:8085/hello?wsdl");

	cg.generateJavaCode(wsdlInterfaceDescriptor, CodeGeneratorHelper.SERVER);

	String namespace = cg.getNamespace(wsdlInterfaceDescriptor);
	System.out.println("Namespace: " + namespace);
	String directory = cg.getDestinationFolder(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA, namespace);
	System.out.println("Directory: " + directory);
	String pathname = directory + "HelloWorld8081_HelloWorld8081Port_Server.java";
	System.out.println("Tested File Path: " + pathname);

	assertTrue(new File(pathname).exists());

    }

    @Test
    public void testIncludeProxyCodeIntoGeneratedJavaFiles() throws IOException {
	CodeGeneratorHelper cg = new CodeGeneratorHelper();

	cg.includeProxyCodeIntoGeneratedJavaFiles(new URL("http://localhost:8085/hello?wsdl"));

	File fileHandler = new File(cg.getDestinationFolder(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA, cg
		.getNamespace(new URL("http://localhost:8085/hello?wsdl")))
		+ "HelloWorld8081_HelloWorld8081Port_Server.java");

	List<String> fileLines = FileUtils.readLines(fileHandler);

	for (String currentLine : fileLines) {
	    if (currentLine.contains("Object implementor = new ") && currentLine.contains("Impl();")) {
		if (!(currentLine.contains("Object implementor = Proxy.newProxyInstance(") && currentLine
			.contains(".class }, new GenericImpl());"))) {
		    fail("File not Edited. Line found:" + currentLine);
		}
	    }

	}
    }

    @Test
    public void testGetNamespaceFromURL() throws Exception {
	CodeGeneratorHelper cg = new CodeGeneratorHelper();
	assertEquals("http://webservice.support.proxy.br.usp.ime/", cg.getNamespace(new URL(
		"http://localhost:8085/hello?wsdl")));

    }

    @Test
    public void testGetNamespaceFromFile() throws Exception {
	CodeGeneratorHelper cg = new CodeGeneratorHelper();
	assertEquals("http://webservice.support.proxy.br.usp.ime/", cg.getNamespace(Object.class.getResource("/hello.wsdl")));
    }

    @Test
    public void testGetDestinationFolder() throws Exception {
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();
	String dest = cgh.getDestinationFolder(CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE,
		"http://webservice.support.proxy.br.usp.ime/");

	assertEquals(CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE + "/ime/usp/br/proxy/support/webservice/",
		dest);
    }

    @Test
    public void testCompileJavaFiles() throws Exception {
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();

	cgh.compileJavaFiles(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA + "/ime/usp/br/proxy/support/webservice/",
		CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE);

	System.out.println(CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE
		+ "/ime/usp/br/proxy/support/webservice/HelloWorld8081_HelloWorld8081Port_Server.class");

	assertTrue(new File(CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE
		+ "/ime/usp/br/proxy/support/webservice/HelloWorld8081_HelloWorld8081Port_Server.class").exists());
    }
}
