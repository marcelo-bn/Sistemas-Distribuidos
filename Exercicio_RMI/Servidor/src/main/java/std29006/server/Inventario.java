package std29006.server;
import std29006.InventarioDistribuido;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Classe que implementa a interface do objeto distribuido
 */
public class Inventario implements InventarioDistribuido {

    private ArrayList<String> inventario = new ArrayList<>();
    private String apCompleto;

    /*
       Adiciona um AP em um ArrayList
     */
    public void adicionaAP(String nome, String mac, String freq, String local) throws RemoteException {
        this.apCompleto = nome + " " + mac + " " + freq + " " + local;
        this.inventario.add(apCompleto);
    }

    /*
        Retorna uma lista com APs cadastrados
     */
    public ArrayList<String> listaInventario() throws RemoteException {
        return inventario;
    }

    /*
        Remove um AP da lista de cadastrados
     */
    public void removeAP(String ap) throws RemoteException {

        for (int i = 0; i < inventario.size(); i++){

            String aux = inventario.get(i);
            if(aux.contains(ap)) inventario.remove(i);

        }
    }

    /*
        Retorna uma lista com APs que possuem determinada frequência de operação
     */
    public ArrayList<String> listaFrequencia5(String s) {
        ArrayList<String> aux = new ArrayList<>();
        String z = "freq:2.4:5";

        for (String x : inventario) {
            if (x.contains(s)) {
                String[] parts = x.split(" ");
                String nome = parts[0];
                aux.add(nome);
            }
        }

        for (int i = 0; i < inventario.size(); i++){
            String freq2 = inventario.get(i);
            if(freq2.contains(z)){
                String[] parts = freq2.split(" ");
                String nome2 = parts[0];
                aux.add(nome2);
            }
        }

        return aux;
    }









}
