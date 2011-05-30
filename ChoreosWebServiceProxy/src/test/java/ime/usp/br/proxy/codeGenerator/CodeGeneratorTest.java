package ime.usp.br.proxy.codeGenerator;

import static org.mockito.Mockito.*;

import java.net.URL;

import org.junit.Test;


public class CodeGeneratorTest {

    @Test
    public void generateServerCodeTest() throws Exception {
	CodeGeneratorHelper cgh = mock(CodeGeneratorHelper.class);
	CodeGenerator.setCodeGeneratorHelper(cgh);
	URL url = new URL("http://bla.br");
	CodeGenerator.generateServerCode(url);
	
	verify(cgh).generateJavaCode(url);
	verify(cgh).includeProxyCodeIntoGeneratedJavaFiles(url);
    }
}
