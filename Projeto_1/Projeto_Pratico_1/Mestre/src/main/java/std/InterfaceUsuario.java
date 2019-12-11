package std;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
    Classe responsável pela Interface com o usuário.

 */

public class InterfaceUsuario implements Runnable{

    private ListaConexoes listaEntrada;
    private ListaConexoes listaSaida;
    private Scanner teclado = new Scanner(System.in);
    private String nomeArquivo;
    private  int [] estadosTrabalhadores = new int[3]; // 0 - Espera, 1 - trabalhando.
    private  String [] tarefas = new String[3];

    public InterfaceUsuario(ListaConexoes listaEntrada, ListaConexoes listaSaida){
        this.listaEntrada = listaEntrada;
        this.listaSaida = listaSaida;
    }

    @Override
    public void run() {

        while (true){
            ArrayList<DataOutputStream> saida = listaSaida.listaConexoesSaida();
            ArrayList<DataInputStream> entrada = listaEntrada.listaConexoesEntradas();

            String rsp;

            int op = 0, esc = 0, trab = 0;

            System.out.println(" > Interface de Controle");
            System.out.println(" [1] Verificar estado \n [2] Configurar trabalhos \n [3] Parar Trabalho" +
                               " \n [4] Disparar Trabalhos \n [5] Configurações trabalhadores");
            op = teclado.nextInt();

            switch (op) {

                case 1:
                    try {
                        System.out.println(" > Estado dos trabalhadores");

                        if(saida.isEmpty()){
                            System.out.println(" > Ainda não foi estabelecida nenhuma conexão!\n");
                        }
                        else {
                            for (int i = 0; i < saida.size(); i++) {
                                saida.get(i).writeUTF("estado");
                                rsp = entrada.get(i).readUTF();

                                if (rsp.equals("TRABALHANDO")) {
                                    estadosTrabalhadores[i] = 1;
                                } else {
                                    estadosTrabalhadores[i] = 0;
                                }

                                System.out.println(" Trabalhador " + (i + 1) + ": " + rsp);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    while(true) {
                        System.out.println("> Determine o trabalhador que você deseja configurar suas tarefas," +
                                " \n  para isso verifique quais os que estão ativos escolhendo a opção [1] " +
                                " \n  na interface do usuário. Especifique qual arquivo você irá encaminhar" +
                                " \n  aos trabalhadores. Selecione uma das opcões abaixo:  ");

                        System.out.println(" [1] Voltar para a interface " +
                                " \n [2] Selecionar trabalhador " +
                                " \n [3] Especificar arquivo");


                        esc = teclado.nextInt();

                        if (esc == 2) {
                            System.out.println("> Digite 1 para o escolher trabalhador 1, 2 para o trabalhador 2 e assim por diante:");
                            trab = teclado.nextInt();

                            if (trab <= 0 || trab > saida.size()) {
                                System.out.println("> Este trabalhador não está conectado!");
                            } else {

                                System.out.println("> Defina a tarefa que este trabalhador irá executar:" +
                                        " \n [1] Gerar senha com no máximo 5 caracteres" +
                                        " \n [2] Gerar senha com 6 caracteres" +
                                        " \n [3] Gerar senha com 7 caracteres");

                                int task = teclado.nextInt();

                                if (task == 1) {
                                    tarefas[trab-1] = "tipo All5";
                                } else if (task == 2) {
                                    tarefas[trab-1] = "tipo All6";
                                } else if (task == 3) {
                                    tarefas[trab-1] = "tipo All7";
                                }
                                else{
                                    System.out.println("> Esta opção não está definida! ");
                                }

                            }

                        } else if (esc == 3) {
                            System.out.println("> Informe o nome do arquivo que será distribuído aos trabalhadores no " +
                                    "\n no formato 'arquivo.extensao' :");

                            teclado.nextLine();
                            this.nomeArquivo = teclado.nextLine();
                            System.out.println(nomeArquivo);


                        }
                        else if (esc == 1){break;}
                        else {
                            System.out.println("> Esta opção não está definida! ");
                        }
                    }

                    break;

                case 3:
                    System.out.println("> Determine o trabalhador que você deseja que pare o processo, para isso" +
                                       " \n  verifique quais estão em estado de 'trabalho' escolhendo a opção [1] " +
                                       " \n  na interface do usuário. Você também pode parar todos os trabalhadores que" +
                                       " \n  estão trabalhando. Selecione uma das opções abaixo:  ");
                    System.out.println(" [1] Voltar para a interface " +
                                       " \n [2] Selecionar trabalhador \n [3] Para todos trabalhadores ");

                    esc = teclado.nextInt();

                    if(esc == 2){

                        System.out.println("> Digite 1 para o escolher trabalhador 1, 2 para o trabalhador 2 e assim por diante:");
                        trab = teclado.nextInt();


                        if (estadosTrabalhadores[trab - 1] == 0) {
                            System.out.println("> Este trabalhador já está em estado de espera!");
                        }
                        else {
                            try {
                                saida.get(trab - 1).writeUTF("stop");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }


                    }
                    else if(esc == 3){
                        for (int i = 0; i < estadosTrabalhadores.length; i++) {
                            if(estadosTrabalhadores[i] == 1){
                                try {
                                    saida.get(i).writeUTF("stop");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    else if (esc<1 || esc>3){
                        System.out.println("> Esta opção não está definida!");
                    }

                    break;

                case 4:
                    System.out.println("> Para verificar as predefinições de trabalho dos trabalhadores" +
                                       " \n  volte para a interface do usuário e selecione a opção [5]" +
                                       " \n  Desta forma, selecione uma das opções abaixo:");
                    System.out.println(" [1] Voltar para a interface \n [2] Disparar trabalhos \n");

                    esc = teclado.nextInt();

                    if(esc == 2){

                        if (saida.size()<=0){
                            System.out.println("> Nenhum trabalhador está conectado!\n");
                        }
                        else {
                            try {
                                /* Enviando tipo de tarefa */
                                for (int i = 0; i < saida.size(); i++) {
                                    saida.get(i).writeUTF(tarefas[i]);
                                }

                                /* Enviando arquivo */
                                for (int i = 0; i < saida.size(); i++) {
                                    saida.get(i).writeUTF("arquivo");
                                }


                                File myFile = new File(nomeArquivo);


                                FileInputStream f = new FileInputStream(myFile);
                                FileInputStream f2 = new FileInputStream(myFile);
                                FileInputStream f3 = new FileInputStream(myFile);

                                FileInputStream [] vetorFile = new FileInputStream[3];
                                vetorFile[0] = f;
                                vetorFile[1] = f2;
                                vetorFile[2] = f3;

                                for (int i = 0; i < saida.size(); i++) {

                                    if (estadosTrabalhadores[i]==1){
                                        System.out.println("> O trabalhador já está trabalhando!\n");
                                    }
                                    else{
                                        int c;
                                        while ((c = vetorFile[i].read()) != -1) {
                                            saida.get(i).write(c);
                                        }
                                        saida.get(i).write(255);
                                    }

                                }

                                /* Enviando mensagem de trabalho */
                                for (int i = 0; i < saida.size(); i++) {
                                    saida.get(i).writeUTF("work");
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if (esc<1 || esc>2){
                        System.out.println("> Esta opção não está definida!");
                    }


                    break;

                case 5:

                    if (saida.isEmpty()) {
                        System.out.println("> Nenhum trabalhador está conectado!\n");
                    }
                    else {
                        for (int i = 0; i < saida.size(); i++) {
                            System.out.println("Trabalhador " + (i + 1) + " -> Tarefa: " + tarefas[i]);
                        }
                    }

                    break;

                default:
                    System.out.println(" > Esta opção não está definida!\n");
                    break;

            }

        }

    }

}
