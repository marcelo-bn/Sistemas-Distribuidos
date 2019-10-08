import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConexaoCliente implements Runnable {

    private Socket conexao;

    public ConexaoCliente(Socket conexao){
        this.conexao = conexao;
    }
    @Override
    public void run() {

        try{
            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(this.conexao.getInputStream()));
            DataOutputStream fluxoSaida = new DataOutputStream(this.conexao.getOutputStream());
            fluxoSaida.writeUTF("Seja bem-vindo cliente!");

            while(true){
                /* inicia a comunicacao */
                String mensagem = fluxoEntrada.readUTF();


                if(mensagem.equals("fim")){ /* Fecha fluxos e socket */
                    System.out.println("> Mensagem recebida: " + mensagem);
                    fluxoSaida.writeUTF("Encerrando conexão");
                    break;

                }
                else {
                    System.out.println("> Mensagem enviada pelo cliente: " + mensagem);
                    fluxoSaida.writeUTF("Recebi sua mensagem corretamente!");
                }
            } // Fim While

            fluxoSaida.close();
            fluxoEntrada.close();
            this.conexao.close();
            System.out.println("> Conexão encerrada, esperando novos clientes!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
