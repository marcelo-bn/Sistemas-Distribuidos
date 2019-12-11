package std;

import java.io.IOException;
import java.net.Socket;


public class Trabalhador {

    public static void main(String[] args) throws IOException{
        int porta = 1234;
        String ipServidor = args[0];

        Socket conexao = new Socket(ipServidor,porta);

        Thread primeiraConcexao = new Thread(new PrimeiroSocket(conexao,ipServidor));
        primeiraConcexao.start();

    }

}