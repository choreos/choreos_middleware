package com.ttdev;

import org.apache.cxf.tools.wsdlto.*;
import ime.usp.br.proxy.generic.*;

public class CodeGenerator {
	public static void main(String[] args) {
		WSDLToJava.main(new String[] { "-server", "-d", "src/main/java",
				"-compile", "http://localhost:8081/hello?wsdl" });
		System.out.println("Done!");
	}
}
