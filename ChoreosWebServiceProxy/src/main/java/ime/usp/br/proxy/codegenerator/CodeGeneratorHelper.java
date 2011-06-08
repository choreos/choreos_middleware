package ime.usp.br.proxy.codeGenerator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.tools.wsdlto.WSDLToJava;

import com.ibm.wsdl.BindingImpl;
import com.sun.tools.javac.Main;

public class CodeGeneratorHelper {
    public static final String SRC_GENERATED_CLIENT_JAVA = "src/generated/client/java";
    public static final String SRC_GENERATED_SERVER_JAVA = "src/generated/server/java";
    public static final String TARGET_GENERATED_SERVER_JAVA_CODE = "target/classes/generated";

    public static final String CLASSPATH = "target/classes";

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

    public String includeProxyCodeIntoGeneratedJavaFiles(URL wsdlInterfaceDescriptor) {

	String namespace = getNamespace(wsdlInterfaceDescriptor);
	String destinationFolder = getDestinationFolder(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA, namespace);

	File fileDirectory = new File(destinationFolder);

	if (fileDirectory.exists() && fileDirectory.isDirectory()) {
	    File fileHandler = findServerFile(fileDirectory);

	    List<String> fileLines = getLines(fileHandler);
	    List<String> fileLinesCopy = new ArrayList<String>(fileLines);

	    for (String currentLine : fileLines) {
		addImportOf(fileLinesCopy, currentLine, "import java.lang.reflect.*;");
		addImportOf(fileLinesCopy, currentLine, "import ime.usp.br.proxy.generic.GenericImpl;");
		adaptImplementorLine(fileLinesCopy, currentLine);
	    }

	    fileHandler.delete();
	    createNewFile(fileHandler);
	    writeLines(fileHandler, fileLinesCopy);
	} else
	    System.out.println(fileDirectory.getPath() + " is not a directory!");
	return fileDirectory.getPath();
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

    private void addImportOf(List<String> fileLines, String currentLine, String packageName) {
	if (currentLine.matches("package .*;")) {
	    fileLines.add(fileLines.indexOf(currentLine) + 1, packageName);
	}
    }

    private void adaptImplementorLine(List<String> fileLines, String currentLine) {
	if (currentLine.matches(".*Object implementor = new .*")) {

	    int classnameStartIndex = currentLine.indexOf("Object implementor = new ")
		    + "Object implementor = new ".length();
	    int classNameEndIndex = currentLine.indexOf("Impl();");

	    String className = currentLine.substring(classnameStartIndex, classNameEndIndex);

	    String modifiedFileLine = "Object implementor = Proxy.newProxyInstance(" + className
		    + ".class.getClassLoader(), new Class[] { " + className + ".class }, new GenericImpl());";

	    fileLines.set(fileLines.indexOf(currentLine), modifiedFileLine);
	}
    }

    @SuppressWarnings("unchecked")
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
	Definition def = null;
	try {
	    WSDLFactory factory = WSDLFactory.newInstance();
	    WSDLReader reader = factory.newWSDLReader();
	    reader.setFeature("javax.wsdl.verbose", false);
	    reader.setFeature("javax.wsdl.importDocuments", true);
	    def = reader.readWSDL(null, wsdlInterfaceDescriptor.toExternalForm());
	} catch (WSDLException e) {
	    e.printStackTrace();
	}

	return def.getTargetNamespace();

    }

    @SuppressWarnings("unchecked")
    public String getPortName(URL wsdlInterfaceDescriptor) {
	Definition def = null;
	try {
	    WSDLFactory factory = WSDLFactory.newInstance();
	    WSDLReader reader = factory.newWSDLReader();
	    reader.setFeature("javax.wsdl.verbose", false);
	    reader.setFeature("javax.wsdl.importDocuments", true);
	    def = reader.readWSDL(null, wsdlInterfaceDescriptor.toExternalForm());
	} catch (WSDLException e) {
	    e.printStackTrace();
	}

	Collection bindingList = def.getBindings().values();
	for (Iterator bindingIterator = bindingList.iterator(); bindingIterator.hasNext();) {
	    BindingImpl bind = (BindingImpl) bindingIterator.next();
	    if (!bind.getPortType().isUndefined())
		return bind.getPortType().getQName().getLocalPart();
	}
	
	return "";

    }

    public String getDestinationFolder(String destinationPrefix, String namespace) {
	String destinationFolder = destinationPrefix;

	if (namespace.matches("http://.*")) {
	    String[] pieces = namespace.split("//")[1].split("\\.");
	    String invertedPieces = "";

	    for (int i = pieces.length - 1; i >= 0; i--) {
		invertedPieces = invertedPieces + "/" + pieces[i].replace("/", "");
	    }

	    return destinationFolder.concat(invertedPieces + "/");
	    // return
	    // destinationFolder.concat(namespace.split("//")[1].replace(".",
	    // "/"));

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

    @SuppressWarnings("unchecked")
    private List<String> listFiles(String sourcesDir) {
	List<String> files = new ArrayList<String>();

	if (new File(sourcesDir).isDirectory()) {
	    for (Iterator<File> iterator = FileUtils.listFiles(new File(sourcesDir), new String[] { "java" }, false)
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
