import java.io.File;
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

        //Organiza os candidatos na ordem decrescente de votos, priorizando o mais velho
        candidatos.sort(new Comparator<Candidato>() {
            @Override
            public int compare(Candidato c1, Candidato c2) {

                //Compara os votos nominais
                if (c1.getVotos_nominais() != c2.getVotos_nominais()) {
                    return c2.getVotos_nominais() - c1.getVotos_nominais();
                }

                //Caso os votos sejam iguais, ordena por data de nascimento
                return c1.getData_nasc().compareTo(c2.getData_nasc());
            }
        });

        //Define um vetor de candidatos eleitos
        ArrayList<Candidato> candidatos_eleitos = new ArrayList<>();

        int vagas = 0;

        for (Candidato c : candidatos) {

            //Verifica a quantidade de candidatos eleitos e os adiciona ao novo vetor
            if (Objects.equals(c.getSituacao(), "Eleito") && Objects.equals(c.getDestino_voto(), "Válido")) {
                vagas++;
                candidatos_eleitos.add(c);
            }

            //Associa cada candidato ao seu partido
            for (Partido p : partidos) {
                if (c.getNumero_partido() == p.getNumero_partido()) {
                    c.setPartido(p);

                    //Incrementa os votos do candidato atual ao número de votos do seu partido
                    p.incrementaVotos(c.getVotos_nominais());

                    //Se o candidato atual for eleito, incrementa o número de candidatos eleitos do partido
                    if (Objects.equals(c.getSituacao(), "Eleito") && Objects.equals(c.getDestino_voto(), "Válido")) {
                        p.incrementaCandidatosEleitos();
                    }
                }
            }
        }

        //Imprime a quantidade de candidatos eleitos (1)
        System.out.println("Número de vagas: " + vagas + "\n");


        int pos = 1;
        //Imprime os vereadores eleitos, na ordem decrescente de votos (2)
        System.out.println("Vereadores eleitos:");
        for (Candidato c : candidatos_eleitos) {
            System.out.println(pos + "" + c);
            pos++;
        }

        pos = 1;

        //Imprime os candidatos mais votados dentro do número de vagas (3)
        System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        for (int i = 0; i < vagas; i++) {
            System.out.println(pos + "" + candidatos.get(i));
            pos++;
        }

        //Imprime os candidatos que teriam sido eleitos, caso a votação fosse majoritária (4)
        System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");
        for (int i = 0; i < vagas; i++) {

            if (!Objects.equals(candidatos.get(i).getSituacao(), "Eleito"))
            System.out.println((i+1) + "" + candidatos.get(i));
        }

        //Imprime os candidatos que foram beneficiados pelo sistema proporcional (5)
        System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");
        for (int i = vagas; i < candidatos.size(); i++) {
            if (Objects.equals(candidatos.get(i).getSituacao(), "Eleito")) {
                System.out.println((i+1) + "" + candidatos.get(i));
            }
        }

        //Ordena o vetor de partidos em ordem decrescente de votos totais, priorizando o partido com menor número
        partidos.sort(new Comparator<Partido>() {
            @Override
            public int compare(Partido p1, Partido p2) {

                //Compara os votos totais
                if (p1.getVotos_totais() != p2.getVotos_totais()) {
                    return p2.getVotos_totais() - p1.getVotos_totais();
                }

                //Se os votos totais forem iguais, ordena pelo menor número do partido
                return p1.getNumero_partido() - p2.getNumero_partido();
            }
        });

        //Imprime os votos totalizados por partido e o número de candidatos eleitos (6)
        System.out.println("\nVotação dos partidos e número de candidatos eleitos:");
        pos = 1;
        for (Partido p : partidos) {
            System.out.println(pos + "" + p);
            pos++;
        }
    }
}