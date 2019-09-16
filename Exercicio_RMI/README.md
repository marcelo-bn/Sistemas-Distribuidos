# Exercício: Sistema de inventário de ativos de WiFi

Desenvolva uma aplicativo cliente / servidor baseado em RMI para fazer o inventário de pon- tos de acesso (Access Point – AP) WiFi. Ao cadastrar um AP é necessário informar um nome, MAC address, frequências que opera (2.4Ghz, 5Ghz ou ambas) e em qual sala do prédio esse AP está afixado (por exemplo: lab. redes II). O aplicativo servidor é o responsável por manter esse inventário em memória e o aplicativo cliente é responsável por fazer requisições ao servi- dor para cadastrar, remover ou listar pontos de acesso. Ao listar os pontos de acesso o cliente poderá indicar critérios de seleção. Exemplos:
  
   #### • Cadastrar um novo AP na sala lab SiDi e que opere em ambas as frequências

```sh
 java Cliente <endereço servidor> add AP1 mac:abcdef freq:2.4:5 Lab-SiDi
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
