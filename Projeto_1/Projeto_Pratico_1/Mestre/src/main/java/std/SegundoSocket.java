package std;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
    Classe para o recebimento de mensagens dos clientes
 */
public class SegundoSocket implements Runnable {

    private ServerSocket novoSocket;
    private String mensagemEntrada;
    private ListaConexoes listaEntrada;
    private ListaConexoes listaSaida;



    public SegundoSocket(ServerSocket novoSocket, ListaConexoes listaEntrada, ListaConexoes listaSaida){
            this.novoSocket = novoSocket;
            this.listaEntrada = listaEntrada;
            this.listaSaida = listaSaida;
    }


    @Override
    public void run() {

       try {


           Socket conexao = novoSocket.accept();

           DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
           DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());


           System.out.println(" > Cliente conectado: " + conexao.getLocalPort());


           while(true){
               this.mensagemEntrada = fluxoEntrada.readUTF();
               String [] msg_split = this.mensagemEntrada.split(" ");

               if(this.mensagemEntrada.equals("fim")){
                   System.out.println("> Mensagem recebida: " + this.mensagemEntrada + " Porta: " + conexao.getLocalPort());
                   break;

               }
               if (msg_split[0].equals("done")) {


                   File f = new File(msg_split[1]);
                   BufferedWriter buff = new BufferedWriter(new FileWriter(f));

                   while(true){
                       mensagemEntrada = fluxoEntrada.readUTF();
                       if(!mensagemEntrada.equals("endline")){
                           buff.append(mensagemEntrada+"\n");
                       }
                       else{
                           break;
                       }
                   }

                   buff.close();

               }

               else {
                   System.out.println("> Mensagem enviada pelo cliente: " + this.mensagemEntrada + " Porta: " + conexao.getLocalPort());
               }
           }

           fluxoSaida.close();
           fluxoEntrada.close();
           this.novoSocket.close();
           System.out.println("> Conexão encerrada, esperando novos clientes!");

       }catch (IOException e){
           e.printStackTrace();
       }

    }
}

