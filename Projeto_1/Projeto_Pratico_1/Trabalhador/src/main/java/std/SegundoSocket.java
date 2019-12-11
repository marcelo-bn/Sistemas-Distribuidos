package std;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/*
    Classe responsável por enviar mensagens ao Mestre.
 */
public class SegundoSocket implements Runnable {

    private int segundaPorta;
    private String ip = "127.0.0.1";
    private String mensagemSaida;
    private String mensagemEntrada;
    private ProcessoJohn trabalhador;
    private ListaFluxos listaSaida;
    Scanner teclado = new Scanner(System.in);


    public SegundoSocket(int segundaPorta, ProcessoJohn t, ListaFluxos l){

        this.segundaPorta = segundaPorta;
        this.trabalhador = t;
        this.listaSaida = l;
    }


    @Override
    public void run() {

        try {
            Socket conexao = new Socket(this.ip, this.segundaPorta);
            System.out.println("> Conectado! " + conexao);

            DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());

            // Adicinando na lista de fluxos
            listaSaida.addConexaoSaida(fluxoSaida);
            // Adicionando valor da segunda porta
            listaSaida.addSegundaPorta(segundaPorta);


            while (true) {
                System.out.println("> Enviar mensagem: ");
                this.mensagemSaida = teclado.nextLine();


                if(this.mensagemSaida.equals("fim")){
                    break;
                }
                else{
                    fluxoSaida.writeUTF(mensagemSaida);
                    fluxoSaida.flush();
                }
            }

            conexao.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
