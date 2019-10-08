import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Cliente que envia uma String em UTF por um socket TCP e espera
 * por uma resposta do servidor
 *
 * 2014-08-24
 * @author Emerson Ribeiro de Mello
 */
public class ClienteUTF {

    public static void main(String[] args) throws IOException{
        int porta = 1234;
        String ip = "127.0.0.1";
        String mensagem;
        Scanner teclado = new Scanner(System.in);

        /* Estabelece conexao com o servidor */
        Socket conexao = new Socket(ip,porta);
        System.out.println("> Conectado! " + conexao);

        /* Estabelece fluxos de entrada e saida */
        DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
        DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));

        /* Recebendo mensagem de boas-vindas */
        //String msg = fluxoEntrada.readUTF();
        //System.out.println("> Mensagem recebida: " + msg);

        /* Inicia comunicacao */
        while (true) {
            System.out.println("> Enviar mensagem: ");
            mensagem = teclado.nextLine();
            //String dadoEnvio = mensagem + "\n";
            fluxoSaida.writeUTF(mensagem); // Enviando mensagem
            fluxoSaida.flush();

            if(mensagem.equals("fim")){
                System.out.println("Conexão encerrada!");
                break;
            }
            else {
                String msg = fluxoEntrada.readUTF();
                System.out.println("> Mensagem enviada pelo servidor: " + msg);
            }
        } // Fim while

        conexao.close();

    }

}