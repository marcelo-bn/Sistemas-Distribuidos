package std;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Mestre {

    public static void main(String[] args) throws IOException {

        ServerSocket servidor = new ServerSocket(1234);

        ListaConexoes listaEntrada = new ListaConexoes();
        ListaConexoes listaSaida = new ListaConexoes();

        Thread interfaceUsuario = new Thread(new InterfaceUsuario(listaEntrada,listaSaida));
        interfaceUsuario.start();


        while(true){
            Socket conexao = servidor.accept(); // conectou na porta 1234

            System.out.println(" > Cliente conectado: " + conexao.getLocalPort());

            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
            DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());

            listaEntrada.addConexaoEntrada(fluxoEntrada);
            listaSaida.addConexaoSaida(fluxoSaida);

            ServerSocket segundoSocket = new ServerSocket(0);
            Thread segundaConexao = new Thread(new SegundoSocket(segundoSocket, listaEntrada, listaSaida));
            segundaConexao.start();

            String mensagemSaida = "port " + segundoSocket.getLocalPort();
            fluxoSaida.writeUTF(mensagemSaida);
            fluxoSaida.flush();



        }

    }

}