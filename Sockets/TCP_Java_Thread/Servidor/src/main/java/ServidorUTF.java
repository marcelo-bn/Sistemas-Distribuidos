import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor que espera por uma mensagem do cliente (String em UTF) e depois
 * envia uma String de resposta, tambem em UTF
 *
 * 2014-08-24
 * @author Emerson Ribeiro de Mello
 */
public class ServidorUTF {

    public static void main(String[] args) throws IOException {

        /* Registra servico na porta 1234 e aguarda por conexoes */
        ServerSocket servidor = new ServerSocket(1234);

        while(true){
            System.out.println("> Aguardando por conexões...");

            Socket conexao = servidor.accept();
            System.out.println("> Cliente conectado: " + conexao);

            Thread cliente = new Thread( new ConexaoCliente(conexao));
            cliente.start();
            System.out.println("Fim");
        }


    }

}