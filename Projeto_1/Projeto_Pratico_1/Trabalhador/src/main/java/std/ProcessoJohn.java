package std;

import java.io.*;
import java.util.ArrayList;

/*
    Classe responsável por realizar os processos John.
 */

public class ProcessoJohn {


    private String estado = "ESPERA";
    private Thread work;
    private String tipoTarefa;
    private File arquivo;
    private ListaFluxos listaSaida;
    private String linha;
    private String nomeArquivoServidor;
    Process process;

    /*
        Construtor
     */
    public ProcessoJohn(ListaFluxos l){

        this.listaSaida = l;

    }

    /*
        Método que realiza os processos
     */
    public void iniciarTrabalho(File f, String nA){
        this.estado = "TRABALHANDO";
        this.arquivo = f;
        this.nomeArquivoServidor = nA;
        String nomeArquivoSaida = Integer.toString(listaSaida.getSegundoPorta())+"out"+".txt";
        File fileOut = new File(nomeArquivoSaida);
        ArrayList<DataOutputStream> saida = listaSaida.listaConexoesSaida();

        // Linux
        ProcessBuilder processBuilder = new ProcessBuilder("john","-i="+tipoTarefa,nomeArquivoServidor);

        // Macos
        //ProcessBuilder processBuilder = new ProcessBuilder("john","-incremental="+tipoTarefa,nomeArquivoServidor);

        try{
            process = processBuilder.start();
        } catch (IOException e){
           e.printStackTrace();
        }

        // Linux
         ProcessBuilder processBuilder1 = new ProcessBuilder("john","--show",nomeArquivoServidor);

        // Macos
        //ProcessBuilder processBuilder1 = new ProcessBuilder("john","--show",nomeArquivoServidor);

        try {


            process = processBuilder1.start();

            saida.get(0).writeUTF("Processo concluído!");
            this.estado = "ESPERA";


            saida.get(0).writeUTF("done "+nomeArquivoSaida);


            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
               saida.get(0).writeUTF(line);
            }
            saida.get(0).writeUTF("endline");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
        Método que interrompe os processos.
     */
    public void pararTrabalho() throws IOException {

        process.destroy();
        this.estado = "ESPERA";
        ArrayList<DataOutputStream> saida = listaSaida.listaConexoesSaida();
        saida.get(0).writeUTF(" O execução do processo foi finalizada!");

    }

    /*
        Método que retorna o estado do Trabalhador.
     */
    public String getEstado() {
        return this.estado;
    }

    /*
        Método que altera o estado do Trabalhador.
     */
    public void setTipoTarefa(String tipo){
        this.tipoTarefa = tipo;
    }



}
