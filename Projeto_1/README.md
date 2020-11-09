# Projeto Prático I - Sistema distribuído para quebra de senhas.

Este documento visa especificar a execução do projeto em um cenário contendo um processo Mestre (Servidor)
e três Trabalhadores (Clientes). É importante salientar que o desenvolvimento do sistema se deu pelo uso de
Sockets para realizar a comunicação entre os processos e por definição, uma comunicação via Sockets necessita conhecer previamente os endereços IPs e portas de serviço dos processos que se deseja realizar uma conexão.
Sendo assim, para o funcionamento deste projeto o usuário necessitará saber o endereço IP da máquina que executará
o processo Mestre e fornecer para todos os processos Trabalhadores. Desta forma para, executar os processos deve-se seguir os seguintes passos:

Para executar os programas é necessário realizar o download da pasta Projeto_Pratico_1 e, em um terminal de controle, o usuário terá que entrar no diretório onde se localiza os arquivos `.jar` referentes ao projeto e executá-los (caso o usuário queira realizar o download apenas dos arquivos `.jar`, os mesmos estão disponíveis neste repositório). O usuário **DEVE EXECUTAR PRIMEIRAMENTE** o processo Mestre e, já com ele em funcionamento deve-se executar os processos Trabalhadores. É importante salientar, que cada processo deve ser executado em terminais diferentes com usuários do sistema diferentes (caso o sistema for executado em uma única máquina). Antes de iniciar a execução dos processos, recomenda-se ler **COMPLETAMENTE** este documento.

 #### • Comando para executar o processo Mestre

```sh
 java -jar arquivoMestre.jar
```

#### • Comando para executar os processos Trabalhadores

Para a execução destes processos deve-se informar o nome do arquivo do Trabalhador que queira-se executar e passar como 
parâmetro o endereço IP da máquina que está rodando o processo Mestre.

```sh
 java -jar arquivoTrabalhador.jar ipServidor
```

O projeto implementa um sistema de comunicação bidirecional e para isto será necessário estabelecer
duas conexões por Trabalhadores, sendo assim, este mecanismo funciona da seguinte maneira: O processo Mestre disponibilizará 
seus serviços em uma porta padrão definida como 1234, cada processo Trabalhador irá se conectar primeiramente nesta porta e logo em seguida o Mestre enviará uma mensagem aos clientes para se conectar em uma porta livre escolhida pelo sistema automaticamente (é válido lembrar que todos esses processos de conexão via Sockets foram implementados utilizando as bibliotecas java.net.Socket e java.net.ServerSocket).

Assim, com todos os processos executando, as conexões já são estabelecidades sendo possível observá-las no processo Mestre, pois, ao estabelecer uma conexão é alertado ao usuário por meio do processo Mestre as portas utilizadas nestas conexões. Assim o usuário **DEVE** seguir os seguintes passos para o bom funcionamento do programa.

### Passo 1:

#### • Interface do usuário no processo Mestre.

No processo Mestre haverá uma interface de navegação para realizar as devidas ações com os Trabalhadores, as opções dessa interface são as seguintes:

```sh
 > Interface de controle
 [1] Verificar estado
 [2] Configurar trabalhos
 [3] Parar trabalho
 [4] Disparar trabalhos
 [5] Configurações trabalhadores
```

#### • Interface do usuário opção [1]

Escolhendo a opção [1] o usuário poderá ver quais trabalhadores estão ativos assim como os seus respectivos estados de serviço (ESPERA/TRABALHANDO).


### Passo 2:

#### • Interface do usuário opção [2].

Para iniciar o processo de distribuição de tarefas o usuário deve primeiro selecionar a opção [2] para configurar todo o ambiente. Selecionando esta opção, aparecerá outras três novas: 

```sh
 [1] Voltar para a interface
 [2] Selecionar trabalhador
 [3] Especificar arquivo
```
**• Subopção [1].**

Aqui o usuário poderá retornar à interface principal para verificar quais trabalhadores estão no estado de espera ou trabalhando.

**• Subopção [2].**

Selecionando essa opção o usuário deverá fornecer o trabalhador que se deseja configurar para realizar as tarefas, ou seja,
deve digitar 1 para selecionar o Trabalhador 1, digitar 2 para o Trabalhador 2 e 3 para o Trabalhador 3. Após este processo o usuário terá três novas opções:

```sh
 [1] Gerar senha com no máximo 5 caracteres
 [2] Gerar senha com 6 caracteres
 [3] Gerar senha com 7 caracteres
```
 Cada uma dessas opções definirá o tipo de tarefa que o Trabalhador selecionado irá executar.

**• Subopção [3].**

Aqui o usuário deverá fornecer o nome do arquivo que irá ser enviado para os Trabalhadores. Este arquivo **DEVE** estar na mesmo diretório que o arquivo `mestre.jar` e deverá ser especificado o seu nome e sua extensão, como por exemplo:

```sh
 arquivo.txt
```

### Passo 3:

#### • Interface do usuário opção [4].

Nesta opção o processo Mestre irá mandar os arquivos, tarefas e o comando para os Trabalhadores executarem seus serviços. Para isto, deve-se selecionar a subopção [2].

```sh
 [1] Voltar para a interface 
 [2] Disparar trabalhos
```

### Passo 4:

#### • Interface do usuário opção [3].

Retornando à interface principal e selecionando a opção [3], aparecerá as seguintes opções:

```sh
 [1] Voltar para a interface
 [2] Selecionar trabalhador 
 [3] Para todos trabalhadores 
```
**• Subopção [2].**

Selecionada esta opção o usuário deve indicar o Trabalhador que se deseja parar o processo, digitando 1 para o Trabalhador 1, 2 pra o Trabalhador 2 e assim por diante. O usuário deve saber previamente quais Trablhadores estão em estado de trabalho.

**• Subopção [3].**

Nesta opção o usuário poderá interromper todos os processos que estão trabalhando.

### Passo 5:

#### • Interface do usuário opção [5].

Selecionando esta opção o usuário pode visualizar as configurações realizadas nos Trabalhadores.

## Recebendo respostas dos Trabalhadores:

Quando um processo Trabalhador finaliza a execução do seu trabalho, o mesmo retorna ao Mestre um arquivo de texto contendo as saídas obtidas. Este arquivo ficará salvo no diretório do arquivo `mestre.jar` com o nome referenciando a porta de conexão do trabalhador no seguinte formato: `portaout.txt`. Ao realizar o envio de um segundo arquivo para os processos Trabalhadores, os mesmo irão retornar a mensagem de saída da tarefa e sobrescrever o arquivo de resposta anterior retornado ao Mestre. Desta forma, caso o usuário queira armazenar todos as respostas dos diferentes arquivos enviados aos Trabalhadores, ele deve acessar a pasta do processo Mestre e salvar o arquivo resposta em outro diretório de sua preferência.








