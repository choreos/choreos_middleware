package src;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class EasyProxy {

    public static void main(String[] args) throws Exception {

	// Endereço, local onde encontra-se o Web Service
	String local = "http://localhost:8081/hello";

	// Criando e configurando o serviço
	Call call = (Call) new Service().createCall();

	// Configurando o endereço.
	call.setTargetEndpointAddress(local);

	// Marcando o método a ser chamado.
	call.setOperationName("sayHello");

	// Parâmetros da função soma.
	Object[] param = new Object[] { "Felps"};

	// Retorno da Função
	String ret = (String) call.invoke(param);

	// Imprime o resultado: ret = 2 + 4.
	System.out.println("Resultado da Soma: " + ret);

    }
}
