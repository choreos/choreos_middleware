package ime.usp.br.proxy.codeGenerator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.naming.ldap.SortResponseControl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.xml.bind.v2.TODO;

public class CodeGeneratorHelperTest {

    @BeforeClass
    public static void cleanPreviouslyGeneratedCode() throws IOException {
	FileUtils.cleanDirectory(new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA));
	//FileUtils.cleanDirectory(new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA_CODE));
    }

    @Test
    public void testGenerateJavaCode() {
	CodeGeneratorHelper cg = new CodeGeneratorHelper();

	URL wsdlInterfaceDescriptor = Object.class.getResource("/hello.wsdl");

	// TODO: colocar true = SERVER e false = CLIENT na classe de constantes
	cg.generateJavaCode(wsdlInterfaceDescriptor, CodeGeneratorHelper.SERVER);

	// TODO: Create a constants class in which we describe the default path
	// for generated files
	String pathname = CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA
		+ "/hello/HelloWorld8081_HelloWorld8081Port_Server.java";

	System.out.println("File Path: " + pathname);
	assertTrue(new File(pathname).exists());

    }

    @Test
    public void testIncludeProxyCodeIntoGeneratedJavaFiles() throws IOException {
	CodeGeneratorHelper cg = new CodeGeneratorHelper();
	cg.includeProxyCodeIntoGeneratedJavaFiles(Object.class.getResource("/hello.wsdl"));

	File fileHandler = new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA
		+ "/hello/HelloWorld8081_HelloWorld8081Port_Server.java");

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
    public void testGetNamespace() throws Exception {
	CodeGeneratorHelper cg = new CodeGeneratorHelper();
	assertEquals("oi", cg.getNamespace(Object.class.getResource("/oi.wsdl")));
	assertEquals("hello", cg.getNamespace(Object.class.getResource("/hello.wsdl")));

    }

    @Test
    public void testCompileJavaFiles() throws Exception {
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();

	cgh.compileJavaFiles(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA + "/hello/",
		CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA_CODE + "/hello/");

	assertTrue(new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA_CODE
		+ "/hello/HelloWorld8081_HelloWorld8081Port_Server.class").exists());
    }
}
