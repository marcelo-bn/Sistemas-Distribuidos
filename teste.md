# Projeto Prático I - Sistema distribuído para quebra de senhas.

Este documento visa especificar a execução do projeto em um cenário contendo um processos Mestre (Servidor)
e três Trabalhadores (Clientes). É importante salientar que o desenvolvimento do sistema se deu pelo uso de
Sockets para realizar a comunicação entre processos, desta forma, é necessário realizar algumas configurações 
para a execução dos mesmos. Por definição, uma comunicação via Sockets necessita conhecer previamente os endereços
IPs e portas de serviço dos processos que se deseja realizar uma conexão.
Sendo assim, para o funcionamento deste projeto o usuário necessitará saber o endereço IP da máquina que executará
o processo Mestre e adicioná-lo em todos os processos Trabalhadores. Para isto, deve-se acessar o código presente 
na pasta dos Trabalhadores com o nome de Trabalhador01/Trabalhador02/Trabalhador03.java e realizar a troca da variável 
ipServidor para o endereço da máquina mestre. Abaixo está a linha que representa esta variável com o endereço usado
para testes locais do programa (endereço da máquina local).
  
   #### • Variável referente ao endereço IP do Mestre nos códigos Trabalhadores.

```sh
 ipServidor = "127.0.0.1";
```

Realizado este passo para a troca de endereço IP do processo Mestre, é necessário entender o processo de conexão
utilizando as portas. O projeto implementa um sistema de comunicação bidirecional e para isto será necessário estabelecer
duas conexões por Trabalhadores, sendo assim, este mecanismo funciona da seguinte maneira: O processo Mestre disponibilizará 
seus serviços em uma porta padrão definida como 1234, cada processo Trabalhador irá se conectar primeiramente nesta porta e logo em seguida o Mestre enviará uma mensagem aos clientes para se conectar em uma porta livre escolhida pelo sistema automaticamente (é válido lembrar que todos esses processos de conexão via Sockets foi implementado utilizando as bibliotecas java.net.Socket e java.net.ServerSocket). Caso o usuário queira trocar a porta padrão, basta realizar a mudança no parâmetro do método ServerSocket() localizado na pasta do processo Mestre no arquivo Mestre.java.

#### • Método utilizado realizar conexões na porta padrão escolhida.

```sh
 ServerSocket servidor = new ServerSocket(1234);
```
#### • Listar APs que operam na frequência de 5GHz

```sh
 java Cliente <endereço servidor> list freq:5 
```

#### • Remover um AP do inventário

```sh
 java Cliente <endereço servidor> del AP1
```