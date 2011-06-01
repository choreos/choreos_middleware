package ime.usp.br.proxy.codeGenerator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.tools.wsdlto.WSDLToJava;
import org.ow2.easywsdl.wsdl.impl.generic.WSDLReaderImpl;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.xml.xsom.parser.XMLParser;

public class CodeGeneratorHelper {
    public static final String SRC_GENERATED_CLIENT_JAVA = "src/generated/client/java";
    public static final String SRC_GENERATED_SERVER_JAVA = "src/generated/server/java";
    public static final boolean SERVER = true;
    public static final boolean CLIENT = false;

    public void generateJavaCode(URL wsdlInterfaceDescriptor, boolean server) {
	String[] parameters = null;
	if (server) {
	    parameters = new String[] { "-server", "-d", SRC_GENERATED_SERVER_JAVA,
		    wsdlInterfaceDescriptor.toExternalForm() };
	} else {
	    parameters = new String[] { "-client", "-d", SRC_GENERATED_CLIENT_JAVA, "-compile",
		    wsdlInterfaceDescriptor.toExternalForm() };
	}
	WSDLToJava.main(parameters);
    }

    public void includeProxyCodeIntoGeneratedJavaFiles(URL wsdlInterfaceDescriptor) {
	File fileDirectory = new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA + "/"
		+ getNamespace(wsdlInterfaceDescriptor));

	File fileHandler = findServerFile(fileDirectory);
	List<String> fileLines = getLines(fileHandler);
	List<String> fileLinesCopy = new ArrayList<String>(fileLines);

	for (String currentLine : fileLines) {
	    addImportOfReflections(fileLinesCopy, currentLine);
	    adaptImplementorLine(fileLinesCopy, currentLine);
	}

	fileHandler.delete();
	createNewFile(fileHandler);
	writeLines(fileHandler, fileLinesCopy);
    }

    private void writeLines(File fileHandler, List<String> fileLinesCopy) {
	try {
	    FileUtils.writeLines(fileHandler, fileLinesCopy);
	} catch (IOException e) {
	    System.out.println("Problem writing output to file");
	    e.printStackTrace();
	}
    }

    private void createNewFile(File fileHandler) {
	try {
	    fileHandler.createNewFile();
	} catch (IOException e) {
	    System.out.println("Problem Creating file: " + fileHandler.getName());
	    e.printStackTrace();
	}
    }

    private void addImportOfReflections(List<String> fileLines, String currentLine) {
	if (currentLine.matches("package .*;")) {
	    fileLines.add(fileLines.indexOf(currentLine) + 1, "import java.lang.reflect.*;");
	}
    }

    private void adaptImplementorLine(List<String> fileLines, String currentLine) {
	if (currentLine.matches(".*Object implementor = new .*")) {

	    int classnameStartIndex = currentLine.lastIndexOf("Object implementor = new ");
	    int classNameEndIndex = currentLine.indexOf("Impl();");

	    String className = currentLine.substring(classnameStartIndex, classNameEndIndex);

	    String modifiedFileLine = "Object implementor = Proxy.newProxyInstance(" + className
		    + ".class.getClassLoader(), new Class[] { " + className + ".class }, new GenericImpl());";

	    fileLines.set(fileLines.indexOf(currentLine), modifiedFileLine);
	}
    }

    private List<String> getLines(File fileHandler) {
	List<String> fileLines = null;
	try {
	    fileLines = FileUtils.readLines(fileHandler);
	} catch (IOException e) {
	    System.out.println("File not Found: " + fileHandler.getName());
	    e.printStackTrace();
	}
	return fileLines;
    }

    private File findServerFile(File fileDirectory) {
	File[] files = fileDirectory.listFiles(new FileFilter() {

	    public boolean accept(File pathname) {
		return pathname.getName().matches(".*Server.java");
	    }
	});
	return (files.length > 0) ? files[0] : null;
    }

    public String getNamespace(URL wsdlInterfaceDescriptor) {

	return "hello";
//	Pattern pattern = Pattern.compile(".*namespace=['\"].*?['\"].*");
//	
//	
//	try {
//	    File file = new File(wsdlInterfaceDescriptor.toURI());
//	    List<String> lines = getLines(file);
//	    
//	    
//	    
//	    for (String string : lines) {
//		string.
//		Matcher matcher = pattern.matcher(string);
//		if (matcher.matches())
//		    matcher.
//	    }
	    
	    
//	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//	    DocumentBuilder db = dbf.newDocumentBuilder();
//	    Document doc;
//	    doc = db.parse();
//	    System.out.println(wsdlInterfaceDescriptor.toURI());
//	    doc.getDocumentElement().normalize();
//	    System.out.println(doc.getNamespaceURI());
//	    return doc.getNamespaceURI();
//	} catch (SAXException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	} catch (IOException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	} catch (URISyntaxException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	} catch (ParserConfigurationException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
//
//	return null;
    }
}
