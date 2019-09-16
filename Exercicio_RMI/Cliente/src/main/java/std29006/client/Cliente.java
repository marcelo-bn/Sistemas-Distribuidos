package std29006.client;
import std29006.InventarioDistribuido;

import javax.sound.midi.Soundbank;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    private static String nomeServidor;
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuInventario";

    public static void main(String args[]) {


        try {

            nomeServidor = args[0];

            System.out.println("> Conectando no servidor "+ nomeServidor);
            Registry registro = LocateRegistry.getRegistry(nomeServidor, porta);
            InventarioDistribuido stub = (InventarioDistribuido) registro.lookup(NOMEOBJDIST);

            if (args.length > 3) {
                if (args[1].equals("add")){
                    System.out.println("> Adicionando AP ao inventário");
                    stub.adicionaAP(args[2], args[3], args[4], args[5]);
                }
            }
            else if (args.length == 3){

                if (args[1].equals("list")){
                    if(args[2].equals("freq:5")){
                        ArrayList<String> f_names = stub.listaFrequencia5("freq:5");
                        System.out.println("> APs que operam em 5GHz");
                        for (int i = 0; i < f_names.size(); i++) {
                            System.out.println(f_names.get(i));
                        }
                    }
                }

                else if (args[1].equals("del")){
                    System.out.println("> Removendo AP do inventário");
                    stub.removeAP(args[2]);
                }

            }
            else if (args[1].equals("list")) {

                ArrayList<String> aux = stub.listaInventario();
                System.out.println("> APs cadastrados");
                if (aux.isEmpty()) System.out.println("> Nenhum AP está cadastrado");
                else {
                    for (int i = 0; i < aux.size(); i++) {
                        System.out.println(aux.get(i));
                    }
                }


            } else {
                System.out.println("Error!");
            }


        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}