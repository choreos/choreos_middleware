package ime.usp.br.proxy.codeGenerator;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import ime.usp.br.proxy.support.webservice.HelloWorldService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class CodeGeneratorHelperTest {
    CodeGeneratorHelper cgh = new CodeGeneratorHelper();

    @BeforeClass
    public static void cleanPreviouslyGeneratedCode() throws IOException, InterruptedException {
	FileUtils.cleanDirectory(new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA));
	//FileUtils.cleanDirectory(new File(CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE));
    }

    @Test
    public void testGenerateServerJavaCode() throws MalformedURLException {

	URL wsdlInterfaceDescriptor = Object.class.getResource("/role.wsdl");

	cgh.generateJavaCode(wsdlInterfaceDescriptor, CodeGeneratorHelper.SERVER);

	String directory = cgh.getDestinationFolder(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA, wsdlInterfaceDescriptor);
	String pathname = directory + "HelloWorld8081_HelloWorld8081Port_Server.java";

	assertTrue(new File(pathname).exists());

    }
    
    @Test
    public void testGenerateClientJavaCode() throws MalformedURLException {

	URL wsdlInterfaceDescriptor = Object.class.getResource("/role.wsdl");

	cgh.generateJavaCode(wsdlInterfaceDescriptor, CodeGeneratorHelper.CLIENT);

	String directory = cgh.getDestinationFolder(CodeGeneratorHelper.SRC_GENERATED_CLIENT_JAVA, wsdlInterfaceDescriptor);
	String pathname = directory + "HelloWorld8081_HelloWorld8081Port_Client.java";

	assertTrue(new File(pathname).exists());
    }

    @Test
    public void testGetNamespaceFromURL() throws Exception {
	assertEquals("http://webservice.support.proxy.br.usp.ime/", cgh.getNamespace(Object.class.getResource("/role.wsdl")));
    }

    @Test
    public void testGetNamespaceFromFile() throws Exception {
	assertEquals("http://webservice.support.proxy.br.usp.ime/", cgh.getNamespace(Object.class
		.getResource("/hello.wsdl")));
    }

    @Test
    public void testGetDestinationFolder() throws Exception {
	String actualDestination = cgh.getDestinationFolder(CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE,
		Object.class.getResource("/role.wsdl"));

	String expectedDestination = CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE
		+ "/ime/usp/br/proxy/support/webservice/";

	assertEquals(expectedDestination, actualDestination);
    }

    @Test
    public void testCompileJavaFiles() throws Exception {
	cgh.compileJavaFiles(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA + "/ime/usp/br/proxy/support/webservice/",
		CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE);

	assertTrue(new File(CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE
		+ "/ime/usp/br/proxy/support/webservice/HelloWorld8081_HelloWorld8081Port_Server.class").exists());
    }
    
    @Test
    public void testGetPortNameFromURL() throws Exception {
	assertEquals("HelloWorld8081", cgh.getPortName(Object.class.getResource("/role.wsdl")));
    }

    @Test
    public void testGetPortNameFromFile() throws Exception {
	assertEquals("HelloWorld8081", cgh.getPortName(Object.class
		.getResource("/hello.wsdl")));
    }

    @Test
    public void shouldCreateAJavaImplementationSourceFile() throws Exception {
	cgh.generateJavaCode(Object.class.getResource("/role.wsdl"), CodeGeneratorHelper.SERVER);
	assertTrue(new File("src/generated/server/java/ime/usp/br/proxy/support/webservice/HelloWorld8081Impl.java").exists());
    }
}
