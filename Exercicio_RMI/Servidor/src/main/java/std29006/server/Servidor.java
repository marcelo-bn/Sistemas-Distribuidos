package std29006.server;
import std29006.InventarioDistribuido;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    private static String nomeServidor = "127.0.0.1";
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuContador";
    public static void main(String args[]){
        try {
            // recebendo nome do servidor por argumento de linha de comando
            if (args.length>0) {
                if (args[0] != null){
                    nomeServidor = args[0];
                }
                if (args[1] != null){
                    porta = Integer.parseInt(args[1]);
                }
            }

            Inventario c = new Inventario();

            System.setProperty("java.rmi.std29006.server.hostname", nomeServidor);
            InventarioDistribuido stub = (InventarioDistribuido)
                    UnicastRemoteObject.exportObject(c, 0);

            Registry registro = LocateRegistry.createRegistry(porta);

            registro.bind(NOMEOBJDIST, stub);
            System.out.println("Servidor pronto!\n");
            System.out.println("Pressione CTRL + C para encerrar...");

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
