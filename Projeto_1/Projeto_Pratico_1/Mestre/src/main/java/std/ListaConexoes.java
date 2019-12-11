package std;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

/*
    Classe responsável pelo controle dos fluxos.
 */

public class ListaConexoes {

    private ArrayList<DataInputStream> listaFluxosEntradas = new ArrayList<>();
    private ArrayList<DataOutputStream> listaFluxosSaidas = new ArrayList<>();


    /*
        Adciona os fluxos de entrada.
     */
    public void addConexaoEntrada(DataInputStream entrada) {
       listaFluxosEntradas.add(entrada);
    }

    /*
        Adiciona os fluxos de saída.
     */
    public void addConexaoSaida(DataOutputStream saida) {
        listaFluxosSaidas.add(saida);
    }

    /*
        Lista os fluxos de saída.
     */
    public  ArrayList<DataOutputStream> listaConexoesSaida(){
        return listaFluxosSaidas;
    }

    /*
        Lista os fluxos de entrada.
     */
    public  ArrayList<DataInputStream> listaConexoesEntradas(){
        return listaFluxosEntradas;
    }


}
