1) CodeGenerator (Client-side)
 O projeto HelloClient possui o código fonte para a geração dos fontes e compilados do cliente

2) CodeGenerator (Server side)
 O projeto ServerCodeGenerator possui o código fonte para a geração dos fontes do servidor.

 Após a geração, o arquivo <WSName>Port_Server possuirá um erro pois o arquivo <WSName>Impl não está implementado.

 Para contornar este problema, criamos o script solveServerInconsistency.sh que utiliza ferramentas de bash Unix para resolver essa inconsistência e incluir um proxy Java (da classe GenericImpl) que receberá as requisições a métodos e lidará da maneira adequada com cada uma delas. Provavelmente invocando os métodos que as implementam.

3) HelloProxyProvider
Este projeto, movido para a pasta samples, cria um interceptador de mensagesn SOAP e desvia a requisição do endpoint requisitado para algum outro, necessariamente distinto do original desde que existam pelo menos 2 servidores ativos.
