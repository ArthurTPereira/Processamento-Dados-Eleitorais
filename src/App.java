import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

public class App {
    public static void main( String[] args ) throws Exception {

        //Define um vetor do tipo Candidato
        ArrayList<Candidato> candidatos = new ArrayList<>();

        //Configura a leitura do arquivo CSV de candidatos e ignora a primeira linha
        Scanner CandidatosCSV = new Scanner(new File(args[0]));
        CandidatosCSV.useDelimiter("\n");
        CandidatosCSV.next();

        StringTokenizer linhaCandidatos;

        //Varre cada linha do arquivo CSV de candidatos
        while (CandidatosCSV.hasNext()) {
            linhaCandidatos = new StringTokenizer(CandidatosCSV.next());

            //Varre cada coluna da linha atual do arquivo CSV de cadidatos
            while (linhaCandidatos.hasMoreTokens()) {

                //Cria um candidato e o registra no vetor de candidatos
                Candidato p = new Candidato();
                p.registraCandidato(linhaCandidatos);
                candidatos.add(p);
            }
        }

        //Fecha o método utilizado para ler o arquivo CSV de candidatos
        CandidatosCSV.close();

        //Define um vetor do tipo Partido
        ArrayList<Partido> partidos = new ArrayList<>();

        //Configura a leitura do arquivo CSV de partidos e ignora a primeira linha
        Scanner partidosCSV = new Scanner(new File(args[1]));
        partidosCSV.useDelimiter("\n");
        partidosCSV.next();

        StringTokenizer linhaPartidos;

        //Varre cada linha do arquivo CSV de partidos
        while (partidosCSV.hasNext()) {
            linhaPartidos = new StringTokenizer(partidosCSV.next());

            //Varre cada coluna da linha atual do arquivo CSV de partidos
            while (linhaPartidos.hasMoreTokens()) {

                //Cria um partido e o registra no vetor de partidos
                Partido p = new Partido();
                p.registraPartido(linhaPartidos);
                partidos.add(p);
            }
        }

        //Fecha o método utilizado para ler o arquivo CSV de partidos
        partidosCSV.close();
    }
}
