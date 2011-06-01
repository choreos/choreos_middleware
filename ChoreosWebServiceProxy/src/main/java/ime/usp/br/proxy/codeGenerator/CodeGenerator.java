package ime.usp.br.proxy.codeGenerator;

import java.io.BufferedReader;
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

    private CodeGeneratorHelper codeGeneratorHelper = new CodeGeneratorHelper();

    public void setCodeGeneratorHelper(CodeGeneratorHelper cgh) {
	codeGeneratorHelper = cgh;
    }
    
    public void generateServerCode(URL wsdlInterfaceDescriptor) {
	codeGeneratorHelper.generateJavaCode(wsdlInterfaceDescriptor);
	codeGeneratorHelper.includeProxyCodeIntoGeneratedJavaFiles(wsdlInterfaceDescriptor);
    }

    public static void generateClientCode(URL WsdlInterfaceDescriptor) {
	WSDLToJava.main(new String[] { "-client", "-d", "src/main/java", "-compile",
		WsdlInterfaceDescriptor.toExternalForm() });
    }

    public static void main(String[] args) {
	WSDLToJava.main(args);
    }
}
