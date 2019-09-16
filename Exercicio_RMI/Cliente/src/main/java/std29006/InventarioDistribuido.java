package std29006;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Interface que deve ser compartilhada por servidor e clientes
 */
public interface InventarioDistribuido extends Remote{

    public void adicionaAP(String nome, String mac, String freq, String local) throws RemoteException;
    public ArrayList<String> listaInventario() throws RemoteException;
    public void removeAP(String ap) throws RemoteException;
    public ArrayList<String> listaFrequencia5(String s) throws RemoteException;

}