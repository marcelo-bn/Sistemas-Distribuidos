package std;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.FileOutputStream;
import java.io.File;

/*
    Classe responsável por tratar as mensagens vindas do Mestre.
 */
public class PrimeiroSocket implements Runnable {

    private Socket primeiraConexao;
    private String ipServidor;
    private String mensagem;
    private ListaFluxos listaSaida = new ListaFluxos();

    public PrimeiroSocket(Socket conexao, String ipServidor){
        this.primeiraConexao = conexao;
        this.ipServidor = ipServidor;
    }


    @Override
    public void run() {

        System.out.println("> Conectado! " + primeiraConexao);
        String nomeArquivo = Integer.toString(primeiraConexao.getLocalPort())+".txt";
        File f = new File(nomeArquivo);


        try {
            DataOutputStream fluxoSaida = new DataOutputStream(primeiraConexao.getOutputStream());
            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(primeiraConexao.getInputStream()));

            /* Objeto trabalhador*/
            ProcessoJohn trabalhador = new ProcessoJohn(listaSaida);


            label:
            while (true) {

                this.mensagem = fluxoEntrada.readUTF();
                String msg = mensagem;
                String[] msg_split = msg.split(" ");

                /* Tratamento das mensagens enviados pelo Servidor */
                switch (msg_split[0]) {
                    case "fim":
                        System.out.println("Conexão encerrada!");
                        break label;
                    case "port":

                        Thread segundoSocket = new Thread(new SegundoSocket(Integer.parseInt(msg_split[1]), trabalhador, listaSaida));
                        segundoSocket.start();

                        break;
                    case "estado":
                        System.out.println("> Mensagem enviada pelo servidor: " + msg);
                        fluxoSaida.writeUTF(trabalhador.getEstado());

                        break;
                    case "arquivo":
                        System.out.println("> Mensagem enviada pelo servidor: " + msg);

                        FileOutputStream fos = new FileOutputStream(f);

                        int c;
                        while ((c = fluxoEntrada.read()) != 255) {
                            fos.write(c);
                        }


                        break;
                    case "work":
                        System.out.println("> Mensagem enviada pelo servidor: " + msg);

                        trabalhador.iniciarTrabalho(f,nomeArquivo);
                        break;
                    case "stop":
                        System.out.println("> Mensagem enviada pelo servidor: " + msg);
                        trabalhador.pararTrabalho();
                        break;
                    case "tipo":
                        System.out.println("> Mensagem enviada pelo servidor: " + msg);
                        trabalhador.setTipoTarefa(msg_split[1]);

                        break;
                    default:
                        System.out.println(msg);
                        break;
                }

            }

            primeiraConexao.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
