package std;

import java.io.DataOutputStream;
import java.util.ArrayList;

/*
    Classe responsável pelo controle dos fluxos.
 */
public class ListaFluxos {

    private ArrayList<DataOutputStream> listaFluxosSaidas = new ArrayList<>();
    private int segundoPorta;

    /*
        Adiciona fluxos de saída.
     */
    public void addConexaoSaida(DataOutputStream saida) {
        this.listaFluxosSaidas.add(saida);
    }

    /*
        Lista fluxos de saídas.
     */
    public  ArrayList<DataOutputStream> listaConexoesSaida(){
        return this.listaFluxosSaidas;
    }

    /*
        Adiciona porta da segunda conexão.
     */
    public void addSegundaPorta(int p) { this.segundoPorta = p;}

    /*
        Retorna porta da segunda conexão.
     */
    public int getSegundoPorta(){ return segundoPorta;}


}
