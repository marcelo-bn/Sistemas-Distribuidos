# Projeto Prático 2 - API REST para a exemplificação do Protocolo de Commit por Duas Fases (2PC)
> Engenharia de Telecomunicações - Sistemas Distribuídos (STD29006)
>
> Instituto Federal de Santa Catarina - campus São José
>
> Marcelo Bittencourt do Nascimento Filho

## Introdução

Este projeto visa implementar uma **API REST** para exemplificar o funcionamento do algoritmo de decisão por duas fases. O sistema será constituído por três aplicações (também denominadas de processos ao longo deste documento) onde uma terá o papel de coordenadora e as outras de réplicas, que farão a simulação de transações bancárias 
com o objetivo de armazenar os dados dessas operações em todos os processos de forma sincronizada utilizando requisições **HTTP**.

Com isso, o funcionamento do projeto se dará resumidamente da seguinte maneira: O processo coordenador ao receber uma requisição de alteração de dados das contas já pré-armazenadas nas aplicações, se comunicará seguindo as regras do protocolo 2PC com os processos réplicas e dependendo das respostas dos processos réplicas, o mesmo encaminhará uma mensagem ao cliente se foi possível ou não realizar a operação desejada.

O requisito principal para que aconteça a operação enviada pelo cliente ao coordenador é que ambas as repostas dos processos réplicas sejam 'yes', seguindo o princípio do protocolo por duas fases. Para mais informações a respeito do protocolo estudado e sobre os requisitos gerais das aplicações confira a descrição do projeto [aqui](http://docente.ifsc.edu.br/mello/std/std-projeto-pratico-2pc.pdf).

## Subindo as aplicações

Para subir as aplicações e deixá-las em funcionamento, deve-se seguir o seguinte processo:

Realizar o download dos arquivos `app.py` e `requirements.txt` presentes neste repositório. Com isso, em um diretório contendo os tais arquivos, recomenda-se criar um ambiente virtual python e realizar as devidas configurações para a executação de uma aplicação desenvolvida nesta linguagem e, assim rodar as aplicações. Após este processo, o usuário **DEVE** inserir os seguintes comandos no seu terminal de controle para executar a aplicação:

```
python app.py 'portaAplicação'
```
**Caso o usuário não queira realizar o processo de criação de um ambiente virtual em sua máquina, o mesmo pode fazer o download da pasta `projeto_venv` situada neste repositório e seguir a sequência de passos abaixo:**

1) Entrar no diretório referente a este repositório;
2) Entrar no diretório `projeto_venv`;
3) Entrar no diretório `app`;
4) Entrar no ambiente virtual digitando `. venv/bin/activate`;
5) Executar a aplicação com `python app.py 'portaAplicação'`.

O usuário deverá realizar a execução de três aplicações para o funcionamento completo do projeto. Desta forma, ele poderá ter as três aplicações rodando na mesma máquina, para isto deve-se fornecer no argumento de linha de comando a porta onde determinada aplicação fornecerá seu serviço através do campo `'portaAplicação'`. Cada aplicação deve rodar em portas diferentes. Com todos os processos já no ar, o usuário já pode realizar a simulação.

## Realizando a simulação

Para o usuário visualizar a implementação completa do projeto, recomenda-se o uso do software [Postman](https://www.getpostman.com) para realizar as requisições **HTTP**. Os passos a seguir dão sequência ao funcionamento do projeto tomando como premissa que os processos rodarão na **mesma máquina** (as aplicações não precisam necessariamente rodar na mesma máquina).

### Passo 1 - Definindo o processo coordenador.

Para definir um processo como coordenador do sistema, o cliente deverá realizar uma requisição do tipo **POST** para o processo de sua escolha utilizando a rota `/contas/replicas` e fornecer uma arquivo em formato `JSON`contendo o **endpoint** e o **id** dos processos que serão réplicas. Abaixo está um exemplo da URI à que se deve realizar a requisição juntamente com o formato da lista de réplicas que deve estar no corpo da requisição.

```
http://localhost:5000/contas/replicas

```

```json
{
    "replicas" : [
		{
		"id" : "replica 1",
		"endpoint" : "http://localhost:12345/contas"
		},
		{
		"id" : "replica 2",
		"endpoint" : "http://localhost:12354/contas"
		}
   ] 
}

```
No exemplo acima foi realizada uma requisição POST para a aplicação com endereço IP `localhost` e que disponibilizou seu serviço na porta `5000` e enviou em seu corpo de dados uma lista identificando o **endpoint** e o **id** das aplicações réplicas. É importante observar que as réplicas estão fornecendo seus serviços em portas diferentes, conforme especificado na seção `Subindo as aplicações`.

### Passo 2 - Carregando semente para número pseudo aleatório

Neste item o cliente deve realizar uma requisição POST para todas as réplicas e enviar em formato `JSON` um arquivo contendo um número que será utilizado como semente para gerar um número aleatório que definiará a resposta que os processos réplicas irão enviar ao coordenador durante o processo de votação do protocolo 2PC. A semente deve ser a mesma para todas as aplicações, abaixo está exemplificado a URI de um processo que vai receber a semente juntamente com o formato do arquivo que será enviado. Vale lembrar que todos os processos devem receber este arquivo, desta forma deve-se executar a requisição POST para cada uma das operações.

```
http://localhost:5000/contas/semente

```

```json
{"seed":"123"}

```

### Passo 3 - Realizando requisições

Com todos os passos anteriores já realizados, o usuário pode seguir a sequência abaixo para visualizar o funcionamento (a sequência é somente um exemplo, o usuário tem a liberdade de alterar os dados e realizar testes com a aplicação):

**1 ) Enviando operação:** Para enviar uma operação que deve ser realizada, o cliente fará uma requisição POST ao processo coordenador enviando um documento `JSON` contendo todas as informações necessárias para a transação. Abaixo está descrita uma URI exemplo de um processo coordenador que receberá a requisicão juntamente com os dados formatados. 

```
http://localhost:5000/contas/acao
```
```
{"operacao":"debito","valor":"5,00","conta":"4345","id":"22F6146B-67D7-4EC0-9D34-2BA840B307F0"}

```

É importante ressaltar que cada operação deve possuir um `id` único, para isso o usuário pode digitar o comando em seu terminal de controle correspondente a `uuidgen` e gerar essa sequência. Caso a operação seja realizada, o cliente receberá a respostas `success` (HTTP 201 Created), caso não seja realizada receberá `fail` (HTTP 403 Forbidden) e se não houver saldo suficiente receberá `Sem saldo` (HTTP 403 Forbidden).

**2) Conferindo contas:** Para visualizar se as aplicações realizaram ou não a operação desejada e verificar se as mesmas estão sincronizadas em relação aos dados armazenados, o cliente deve realizar um requisição GET para a aplicação que deseja verificar. O exemplo abaixo demonstra a URI para uma delas, porém, para ter a certeza do funcionamento do sistema deve-se requerir para todas.

```
http://localhost:5000/contas
```

**3) Conferindo ações realizadas:** Agora, para verificar todas as operações que foram requeridas, basta realizar um GET para a aplicação coordenadora para receber uma lista de ações com seus respectivos `id's`. O exemplo abaixo realiza esta requisição assim como uma lista com algumas operações realizadas.

```
http://localhost:5000/acoes
```

```json
{
  "acoes": [
    {
      "id": "C45AC2EC-0EF7-48A2-B572-7AF28B316068",
      "status": "fail"
    },
    {
      "id": "C45AC2EC-0EF7-48A2-B572-7AF28B316069",
      "status": "success"
    }
  ]
}
```

**4) Outras requisições:** Para utilizar ou ter conhecimento sobre outras funcionalidades desenvolvidas na API, o usuário pode acessar a sua documentação criada de acordo com a especificação API [Blueprint](https://apiblueprint.org) situada neste repositório com o nome de `apiary.apib`.

## Objetivos alcançados

A versão final deste projeto está apta a realizar plenamente todas as funcionalidades propostas pelo [documento](http://docente.ifsc.edu.br/mello/std/std-projeto-pratico-2pc.pdf) fornecido em aula pelo professor.




