package ime.usp.br.proxy.codeGenerator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ime.usp.br.proxy.support.webservice.HelloWorld8081;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.ws.Endpoint;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CodeGeneratorTest {
    
    CodeGenerator cg = new CodeGenerator();
    CodeGeneratorHelper cgh = mock(CodeGeneratorHelper.class);
    
    @BeforeClass
    public static void cleanPreviouslyGeneratedCode() throws IOException, InterruptedException {
	HelloWorld8081 service = new HelloWorld8081("1");
	Endpoint endpoint = Endpoint.create(service);
	endpoint.publish("http://localhost:8085/hello");
	System.out.println("Serviço disponibilizado na porta 8085");

	FileUtils.cleanDirectory(new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA));
    }
    
    @Test
    public void generateServerCodeTest() throws Exception {
	cg.setCodeGeneratorHelper(cgh);
	URL url = new URL("http://bla.br");
	cg.generateServerCode(url);
	
	verify(cgh).generateJavaCode(url, true);
	verify(cgh).includeProxyCodeIntoGeneratedJavaFiles(url);
    }
    
    @Test
    public void generateServerClassesTest() throws Exception {
	URL url = new URL("http://bla.br");
	
	when(cgh.includeProxyCodeIntoGeneratedJavaFiles(url)).thenReturn("generateServerCodeReturn");
	
	cg.setCodeGeneratorHelper(cgh);
	cg.generateServerClasses(url);
	
	verify(cgh).generateJavaCode(url, true);
	verify(cgh).includeProxyCodeIntoGeneratedJavaFiles(url);
	verify(cgh).compileJavaFiles("generateServerCodeReturn", CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE);
    }
    
    @Test
    public void simpleRun() throws Exception {
    }
    
    @Test
    public void testGenerateClientCode() throws Exception {
	cg.setCodeGeneratorHelper(cgh);
	
	cg.generateClientCode(new URL("http:/bla.br/"));
	
    }
}
