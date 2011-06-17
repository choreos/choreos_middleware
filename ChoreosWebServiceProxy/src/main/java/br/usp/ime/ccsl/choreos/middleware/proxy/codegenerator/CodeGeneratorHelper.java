package br.usp.ime.ccsl.choreos.middleware.proxy.codegenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.tools.wsdlto.WSDLToJava;

import br.usp.ime.ccsl.choreos.wsdl.WsdlUtils;

import com.sun.tools.javac.Main;

public class CodeGeneratorHelper {
    public static final String SRC_GENERATED_CLIENT_JAVA = "src/generated/client/java";
    public static final String SRC_GENERATED_SERVER_JAVA = "src/generated/server/java";
    public static final String TARGET_GENERATED_SERVER_JAVA_CODE = "target/classes/generated";

    public static final String CLASSPATH = "target/classes";

    public static final boolean SERVER = true;
    public static final boolean CLIENT = false;

    private List<String> implementation;

    private void createImplementationClass(URL wsdlInterfaceDescriptor) {
	implementation = new ArrayList<String>();

	String packageName = getPackage(wsdlInterfaceDescriptor);

	implementation.add("package " + packageName.substring(0, packageName.length() - 1) + ";");
	implementation.add("");
	implementation.add("public class " + WsdlUtils.getPortName(wsdlInterfaceDescriptor) + "Impl implements "
		+ WsdlUtils.getPortName(wsdlInterfaceDescriptor) + "{");
	implementation.add("");

	String className = packageName + WsdlUtils.getPortName(wsdlInterfaceDescriptor);
	Class<?> implementedInterface = getInterfaceClass(className);

	for (int i = 0; i < implementedInterface.getMethods().length; i++) {
	    generateMethod(implementedInterface.getMethods()[i]);
	}

	implementation.add("}");

	String fileName = getDestinationFolder(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA, wsdlInterfaceDescriptor)
		+ WsdlUtils.getPortName(wsdlInterfaceDescriptor) + "Impl.java";
	File output = new File(fileName);

	writeLines(output, implementation);
    }

    private String getPackage(URL wsdlInterfaceDescriptor) {
	return getDestinationFolder("", wsdlInterfaceDescriptor).replace('/', '.').substring(1);
    }

    private void generateMethod(Method method) {
	String line = "public " + method.getReturnType().getSimpleName() + " " + method.getName() + "(";

	for (int i = 0; i < method.getParameterTypes().length; i++) {
	    line = line + method.getParameterTypes()[i].getSimpleName() + " param" + i;
	    line = (i == method.getParameterTypes().length) ? line + ", " : line;
	}
	line = line + "){";
	implementation.add(line);

	line = "Exception e = new Exception(\"There is no web service active for this role\");";
	implementation.add(line);
	implementation.add("return null;");
	implementation.add("}");

    }

    private Class<?> getInterfaceClass(String portName) {
	Class<?> clazz = null;
	try {
	    clazz = Class.forName(portName);
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	if (clazz == null)
	    System.out.println("Class " + portName + " was not found.");
	return clazz;
    }

    public String generateJavaCode(URL wsdlInterfaceDescriptor, boolean server) {
	String[] parameters = null;
	if (server) {
	    parameters = new String[] { "-server", "-d", SRC_GENERATED_SERVER_JAVA,
		    wsdlInterfaceDescriptor.toExternalForm() };
	    WSDLToJava.main(parameters);
	    createImplementationClass(wsdlInterfaceDescriptor);
	    return getDestinationFolder(SRC_GENERATED_SERVER_JAVA, wsdlInterfaceDescriptor);
	} else {
	    parameters = new String[] { "-client", "-d", SRC_GENERATED_CLIENT_JAVA,
		    wsdlInterfaceDescriptor.toExternalForm() };
	    WSDLToJava.main(parameters);
	    return getDestinationFolder(SRC_GENERATED_CLIENT_JAVA, wsdlInterfaceDescriptor);
	}

    }

    private void writeLines(File fileHandler, List<String> fileLinesCopy) {
	try {
	    FileUtils.writeLines(fileHandler, fileLinesCopy);
	} catch (IOException e) {
	    System.out.println("Problem writing output to file");
	    e.printStackTrace();
	}
    }

    public String getDestinationFolder(String destinationPrefix, URL wsdlInterfaceDescriptor) {
	String namespace = WsdlUtils.getNamespace(wsdlInterfaceDescriptor);
	String destinationFolder = destinationPrefix;

	if (namespace.matches("http://.*")) {
	    String[] pieces = namespace.split("//")[1].split("\\.");
	    String invertedPieces = "";

	    for (int i = pieces.length - 1; i >= 0; i--) {
		invertedPieces = invertedPieces + "/" + pieces[i].replace("/", "");
	    }

	    return destinationFolder.concat(invertedPieces + "/");
	} else
	    return namespace;

    }

    public void compileJavaFiles(String sourcesDir, String classesDestinationDir) {
	/*
	 * Usage: javac <options> <source files> where possible options include:
	 * -g Generate all debugging info -g:none Generate no debugging info
	 * -g:{lines,vars,source} Generate only some debugging info -nowarn
	 * Generate no warnings -verbose Output messages about what the compiler
	 * is doing -deprecation Output source locations where deprecated APIs
	 * are used -classpath <path> Specify where to find user class files and
	 * annotation processors -cp <path> Specify where to find user class
	 * files and annotation processors -sourcepath <path> Specify where to
	 * find input source files -bootclasspath <path> Override location of
	 * bootstrap class files -extdirs <dirs> Override location of installed
	 * extensions -endorseddirs <dirs> Override location of endorsed
	 * standards path -proc:{none,only} Control whether annotation
	 * processing and/or compilation is done. -processor
	 * <class1>[,<class2>,<class3>...]Names of the annotation processors to
	 * run; bypasses default discovery process -processorpath <path> Specify
	 * where to find annotation processors -d <directory> Specify where to
	 * place generated class files -s <directory> Specify where to place
	 * generated source files -implicit:{none,class} Specify whether or not
	 * to generate class files for implicitly referenced files -encoding
	 * <encoding> Specify character encoding used by source files -source
	 * <release> Provide source compatibility with specified release -target
	 * <release> Generate class files for specific VM version -version
	 * Version information -help Print a synopsis of standard options
	 * -Akey[=value] Options to pass to annotation processors -X Print a
	 * synopsis of nonstandard options -J<flag> Pass <flag> directly to the
	 * runtime system
	 */
	createDirectory(classesDestinationDir);

	List<String> argsList = listFiles(sourcesDir);

	// argsList.add(0, "-nowarn");
	argsList.add(0, "-d");
	argsList.add(1, classesDestinationDir);

	String[] args = convertStringListToStringArray(argsList);

	Main.compile(args);
    }

    private String[] convertStringListToStringArray(List<String> list) {
	String[] array = new String[list.size()];
	int i = 0;

	for (String file : list) {
	    array[i++] = file;
	}
	return array;
    }

    private List<String> listFiles(String sourcesDir) {
	List<String> files = new ArrayList<String>();

	if (new File(sourcesDir).isDirectory()) {
	    for (Iterator<?> iterator = FileUtils.listFiles(new File(sourcesDir), new String[] { "java" }, false)
		    .iterator(); iterator.hasNext();) {
		File file = (File) iterator.next();
		files.add(file.getPath());
	    }
	} else {
	    System.out.println("Argument " + sourcesDir + " is not a directory");
	    return null;
	}

	return files;
    }

    private void createDirectory(String directory) {
	try {
	    File destination = new File(directory);

	    if (destination.exists()) {
		if (destination.isDirectory()) {
		    FileUtils.deleteDirectory(destination);
		}
	    } else {
		System.out.println("Directory " + directory + " does not exist.");
	    }

	    Process pr = Runtime.getRuntime().exec("mkdir -p ./" + directory);
	    pr.waitFor();

	} catch (IOException e) {
	    System.out.println("Couldn't create " + directory + " directory.");
	    e.printStackTrace();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}
