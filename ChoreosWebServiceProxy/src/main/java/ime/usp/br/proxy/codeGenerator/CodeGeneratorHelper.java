package ime.usp.br.proxy.codeGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class CodeGeneratorHelper {
    public void generateJavaCode(URL wsdlInterfaceDescriptor) {
	WSDLToJava.main(new String[] { "-server", "-d", "src/generated/java", "-compile",
		wsdlInterfaceDescriptor.toExternalForm() });
    }

    public void includeProxyCodeIntoGeneratedJavaFiles(URL wsdlInterfaceDescriptor) {
	//TODO: Improve bash script to allow the compilation of the generated .java files
	String command = "solveServerInconsistency.sh  src/main/java/" + getNamespace(wsdlInterfaceDescriptor)
		+ "/*Server.java";

	runProcessAndPrintOutput(command);
    }
    

    private String getNamespace(URL wsdlInterfaceDescriptor) {
	// TODO Auto-generated method stub
	return null;
    }


    private void runProcessAndPrintOutput(String command) {
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


}
