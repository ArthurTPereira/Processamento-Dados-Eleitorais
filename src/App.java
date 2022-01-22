import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLOutput;
import java.util.*;

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

        //Define um vetor de candidatos eleitos
        ArrayList<Candidato> candidatos_eleitos = new ArrayList<>();

        int eleitos = 0;

        for (Candidato c : candidatos) {

            //Verifica a quantidade de candidatos eleitos
            if (Objects.equals(c.getSituacao(), "Eleito") && Objects.equals(c.getDestino_voto(), "Válido")) {
                eleitos++;
                candidatos_eleitos.add(c);
            }

            //Associa cada candidato ao seu partido
            for (Partido p : partidos) {
                if (c.getNumero_partido() == p.getNumero_partido()) {
                    c.setPartido(p);
                }
            }
        }

        //Organiza os candidatos eleitos na ordem decrescente de votos
        candidatos_eleitos.sort(Comparator.comparingInt(Candidato::getVotos_nominais).reversed());

        //Imprime a quantidade de candidatos eleitos
        System.out.println("Número de vagas: " + eleitos + "\n");


        //Imprime os vereadores eleitos, na ordem decrescente de votos
        int pos = 1;
        System.out.println("Vereadores eleitos:");
        for (Candidato c : candidatos_eleitos) {
            System.out.println(pos + " - " + c.getNome() + " / " + c.getNome_urna() + " (" + c.getPartido().getSigla_partido() + ", " + c.getVotos_nominais() + " votos)");
            pos++;
        }
    }
}

