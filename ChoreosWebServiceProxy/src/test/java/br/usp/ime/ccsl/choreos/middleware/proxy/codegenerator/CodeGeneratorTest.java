package br.usp.ime.ccsl.choreos.middleware.proxy.codegenerator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.ccsl.choreos.middleware.proxy.codegenerator.CodeGenerator;
import br.usp.ime.ccsl.choreos.middleware.proxy.codegenerator.CodeGeneratorHelper;

public class CodeGeneratorTest {

    CodeGenerator cg = new CodeGenerator();
    CodeGeneratorHelper cgh = mock(CodeGeneratorHelper.class);

    @BeforeClass
    public static void cleanPreviouslyGeneratedCode() throws IOException, InterruptedException {
	FileUtils.cleanDirectory(new File(CodeGeneratorHelper.SRC_GENERATED_SERVER_JAVA));
    }

    @Test
    public void generateServerCodeTest() throws Exception {
	cg.setCodeGeneratorHelper(cgh);
	URL url = new URL("http://bla.br");
	cg.generateServerCode(url);

	verify(cgh).generateJavaCode(url, CodeGeneratorHelper.SERVER);
    }

    @Test
    public void generateServerClassesTest() throws Exception {
	URL url = new URL("http://bla.br");

	when(cgh.generateJavaCode(url, CodeGeneratorHelper.SERVER)).thenReturn("generateServerCodeReturn");
	cg.setCodeGeneratorHelper(cgh);
	cg.generateServerClasses(url);

	verify(cgh).generateJavaCode(url, CodeGeneratorHelper.SERVER);
	verify(cgh).compileJavaFiles("generateServerCodeReturn", CodeGeneratorHelper.TARGET_GENERATED_SERVER_JAVA_CODE);
    }

    @Test
    public void simpleRun() throws Exception {
    }

    @Test
    public void testGenerateClientCode() throws Exception {
	cg.setCodeGeneratorHelper(cgh);

	URL url = new URL("http:/bla.br/");
	cg.generateClientCode(url);
	verify(cgh).generateJavaCode(url, CodeGeneratorHelper.CLIENT);
    }
}