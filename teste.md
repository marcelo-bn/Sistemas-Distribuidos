# Projeto Prático I - Sistema distribuído para quebra de senhas.

Este documento visa especificar a execução do projeto em um cenário contendo um processos Mestre (Servidor)
e três Trabalhadores (Clientes). É importante salientar que o desenvolvimento do sistema se deu pelo uso de
Sockets para realizar a comunicação entre processos, desta forma, é necessário realizar algumas configurações 
para a execução dos mesmos. Por definição, uma comunicação via Sockets necessita conhecer previamente os endereços
IPs e portas de serviço dos processos que se deseja realizar uma conexão.
Sendo assim, para o funcionamento deste projeto o usuário necessitará saber o endereço IP da máquina que executará
o processo Mestre e adicioná-lo em todos os processos Trabalhadores. Para isto, deve-se acessar o código presente 
na pasta dos Trabalhadores com o nome de Trabalhador01/Trabalhador02/Trabalhador/03.java e realizar a troca da variável 
ipServidor para o endereço da máquina mestre. Abaixo está a linha que representa esta variável com o endereço usado
para testes locais do programa (endereço da máquina local).
  
   #### • Variável referente ao endereço IP do Mestre no códigos Trabalhadores.

```sh
 ipServidor = "127.0.0.1";
```

#### • Listar APs cadastrados

```sh
 java Cliente <endereço servidor> list
```
#### • Listar APs que operam na frequência de 5GHz

```sh
 java Cliente <endereço servidor> list freq:5 
```

#### • Remover um AP do inventário

```sh
 java Cliente <endereço servidor> del AP1
```
