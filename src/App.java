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
            public int compare(Candidato o1, Candidato o2) {

                //Compara os votos nominais
                if (o1.getVotos_nominais() != o2.getVotos_nominais()) {
                    return o2.getVotos_nominais() - o1.getVotos_nominais();
                }

                //Caso os votos sejam iguais, ordena por data de nascimento
                return o1.getData_nasc().compareTo(o2.getData_nasc());
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
                }
            }
        }

        //Imprime a quantidade de candidatos eleitos (1)
        System.out.println("Número de vagas: " + vagas + "\n");


        int pos = 1;
        //Imprime os vereadores eleitos, na ordem decrescente de votos (2)
        System.out.println("Vereadores eleitos:");
        for (Candidato c : candidatos_eleitos) {
            System.out.println(pos + " - " + c.getNome() + " / " + c.getNome_urna() + " (" + c.getPartido().getSigla_partido() + ", " + c.getVotos_nominais() + " votos)");
            pos++;
        }

        pos = 1;

        //Imprime os candidatos mais votados dentro do número de vagas (3)
        System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        for (int i = 0; i < vagas; i++) {
            System.out.println(pos + " - " + candidatos.get(i).getNome() + " / " + candidatos.get(i).getNome_urna() + " (" + candidatos.get(i).getPartido().getSigla_partido() + ", " + candidatos.get(i).getVotos_nominais() + " votos)");
            pos++;
        }

        //Imprime os candidatos que teriam sido eleitos, caso a votação fosse majoritária (4)
        System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");
        for (int i = 0; i < vagas; i++) {

            if (!Objects.equals(candidatos.get(i).getSituacao(), "Eleito"))
            System.out.println((i+1) + " - " + candidatos.get(i).getNome() + " / " + candidatos.get(i).getNome_urna() + " (" + candidatos.get(i).getPartido().getSigla_partido() + ", " + candidatos.get(i).getVotos_nominais() + " votos)");
        }

        //Imprime os candidatos que foram beneficiados pelo sistema proporcional (5)
        System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");
        for (int i = vagas; i < candidatos.size(); i++) {
            if (Objects.equals(candidatos.get(i).getSituacao(), "Eleito")) {
                System.out.println((i+1) + " - " + candidatos.get(i).getNome() + " / " + candidatos.get(i).getNome_urna() + " (" + candidatos.get(i).getPartido().getSigla_partido() + ", " + candidatos.get(i).getVotos_nominais() + " votos)");
            }
        }
    }
}

