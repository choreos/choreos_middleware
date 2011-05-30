package ime.usp.br.proxy.codeGenerator;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.apache.cxf.tools.wsdlto.WSDLToJava;

/*
 * WSDLToJava - From Apache CXF
 * 
 * For more detailed information checkout 
 * http://cxf.apache.org/docs/wsdl-to-java.html
 * 
 *  wsdl2java -fe <frontend name>
 * -db <data binding name>
 * -wv <[wsdl version]> 
 * -p <[wsdl namespace =]Package Name> 
 * -sn <service-name> 
 * -b <binding-name>  
 * -catalog <catalog-file-name> 
 * -d <output-directory> 
 * -compile 
 * -classdir <compile-classes-directory> 
 * -impl 
 * -server 
 * -client 
 * -all  
 * -autoNameResolution 
 * -defaultValues<=class name for DefaultValueProvider> 
 * -ant  
 * -nexclude <schema namespace [= java packagename]>
 * -exsh <(true, false)> 
 * -dns <(true, false)> 
 * -dex <(true, false)> 
 * -validate 
 * -keep  
 * -wsdlLocation <wsdlLocation attribute> 
 * -xjc<xjc arguments> 
 * -noAddressBinding 
 * -h 
 * -v -verbose -quiet 
 * -useFQCNForFaultSerialVersionUID <wsdlurl>
 * */
public class CodeGenerator {

    public static void generateServerCode(URL WsdlInterfaceDescriptor) {
	WSDLToJava.main(new String[] { "-server", "-d", "src/main/java", "-compile",
		WsdlInterfaceDescriptor.toExternalForm() });

	String command = "solveServerInconsistency.sh  src/main/java/hello/*Server.java";

	runProcessAndPrintOutput(command);

    }

    private static void runProcessAndPrintOutput(String command) {
	Runtime run = Runtime.getRuntime();

	Process pr;
	try {
	    pr = run.exec(command);
	    pr.waitFor();
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return;
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return;
	}
	BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));

	String line;
	try {
	    while ((line = buf.readLine()) != null) {
		System.out.println(line);
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void generateClientCode(URL WsdlInterfaceDescriptor) {
	WSDLToJava.main(new String[] { "-client", "-d", "src/main/java", "-compile",
		WsdlInterfaceDescriptor.toExternalForm() });
    }

    public static void main(String[] args) {
	WSDLToJava.main(args);
    }
}
