import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    public static void main( String[] args ) throws Exception {

        //Verifica se os argumentos estão corretos
        if (args[0].isEmpty() || args[1].isEmpty()) {
            System.out.println("Erro: Argumentos insuficientes/incorretos.");
            System.exit(1);
        }

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
                LocalDate d1 = LocalDate.parse(c1.getData_nasc(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate d2 = LocalDate.parse(c2.getData_nasc(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return d1.compareTo(d2);
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
                    if (Objects.equals(c.getDestino_voto(), "Válido")) {
                        p.getCandidatos().add(c);
                        p.incrementaVotos(c.getVotos_nominais());
                    }

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
            if (p.getVotos_nominais() > 1) {
                System.out.println(pos + "" + p);
            }
            else {
                System.out.printf("%d - %s - %d, %d voto (%d nominal e %d de legenda), %d candidato eleito\n",
                        pos,p.getSigla_partido(),p.getNumero_partido(),p.getVotos_totais(),p.getVotos_nominais(),
                        p.getVotos_legenda(),p.getCandidatos_eleitos());
            }
            pos++;
        }

        //Ordena os partidos na ordem decrescente de votos de legenda, priorizando a ordem de votos nominais e em seguina, número do partido
        partidos.sort(new Comparator<Partido>() {
            @Override
            public int compare(Partido p1, Partido p2) {

                //Compara os votos de legenda
                if (p1.getVotos_legenda() != p2.getVotos_legenda()) {
                    return p2.getVotos_legenda() - p1.getVotos_legenda();
                }

                //Compara os votos nominais
                else if (p1.getVotos_nominais() != p2.getVotos_nominais()) {
                    return p2.getVotos_nominais() - p1.getVotos_nominais();
                }

                //Se os votos acima forem iguais, ordena pelo menor número do partido
                return p1.getNumero_partido() - p2.getNumero_partido();
            }
        });

        //Imprime os votos de legenda com a porcentagem em relação aos votos totais (7)
        System.out.println("\nVotação dos partidos (apenas votos de legenda):");
        pos = 1;
        for (Partido p : partidos) {
            if (p.getVotos_totais() != 0) {
                if (p.getVotos_totais() > 1) {
                    System.out.printf(Locale.FRANCE,"%d - %s - %d, %d votos de legenda (%,.2f%% do total do partido)\n",
                            pos,p.getSigla_partido(),p.getNumero_partido(),p.getVotos_legenda(),
                            100.0 * p.getVotos_legenda() / p.getVotos_totais());
                }
                else {
                    System.out.printf(Locale.FRANCE,"%d - %s - %d, %d voto de legenda (%,.2f%% do total do partido)\n",
                            pos,p.getSigla_partido(),p.getNumero_partido(),p.getVotos_legenda(),
                            100.0 * p.getVotos_legenda() / p.getVotos_totais());
                }
            }

            //Caso os votos totais sejam zero
            else {
                System.out.printf("%d - %s - %d, %d voto de legenda (proporção não calculada, 0 voto no partido)\n",
                        pos,p.getSigla_partido(),p.getNumero_partido(),p.getVotos_legenda());
            }
            pos++;
        }

        //Ordena os candidatos dos partidos na ordem decrescente de votos nominais, priorizando a idade do mais velho.
        for (Partido p : partidos) {
            //Verifica se a lista de candidatos está vazia
            if (!p.getCandidatos().isEmpty()) {

                //Faz a ordenação
                p.getCandidatos().sort(new Comparator<Candidato>() {
                    @Override
                    public int compare(Candidato c1, Candidato c2) {

                        //Compara os votos nominais dos candidatos
                        if (c1.getVotos_nominais() != c2.getVotos_nominais()) {
                            return c2.getVotos_nominais() - c1.getVotos_nominais();
                        }

                        //Caso os votos nominais sejam iguais, compara a idade
                        LocalDate d1 = LocalDate.parse(c1.getData_nasc(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        LocalDate d2 = LocalDate.parse(c2.getData_nasc(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        return d1.compareTo(d2);
                    }
                })
            ;}
        }

        //Ordena os partidos por ordem decrescente de votos nominais do candidato mais votado de cada partido,
        // priorizando o número do partido
        partidos.sort(new Comparator<Partido>() {
            @Override
            public int compare(Partido p1, Partido p2) {

                //Verifica se a lista de candidatos está vazia
                if (p1.getCandidatos().isEmpty() || p2.getCandidatos().isEmpty()) {
                    return 0;
                }

                //Compara os votos nominais dos candidatos mais votados do partido
                else if (p1.getCandidatos().get(0).getVotos_nominais() != p2.getCandidatos().get(0).getVotos_nominais()) {
                    return p2.getCandidatos().get(0).getVotos_nominais() - p1.getCandidatos().get(0).getVotos_nominais();
                }

                //Caso os votos nominais sejam iguais, compara o número do partido
                return p1.getNumero_partido() - p2.getNumero_partido();
            }
        });

        //Imprime os primeiros e último colocados de cada partido (8)
        System.out.println("Primeiro e último colocados de cada partido:");
        pos = 1;
        for (Partido p : partidos) {

            //Verifica se a lista de candidatos está vazia
            if (!p.getCandidatos().isEmpty()) {
                 if (p.getCandidatos().get(p.getCandidatos().size()-1).getVotos_nominais() > 1) {

                    //Imprime no formato especificado
                    System.out.printf("%d - %s - %d, %s (%d, %s votos) / %s (%d, %d votos)\n",
                            pos,p.getSigla_partido(),p.getNumero_partido(), p.getCandidatos().get(0).getNome_urna(),
                            p.getCandidatos().get(0).getNumero(), p.getCandidatos().get(0).getVotos_nominais(),
                            p.getCandidatos().get(p.getCandidatos().size() - 1).getNome_urna(),
                            p.getCandidatos().get(p.getCandidatos().size() - 1).getNumero(),
                            p.getCandidatos().get(p.getCandidatos().size() - 1).getVotos_nominais());
                }
                else {

                    //Imprime no formato especificado
                     System.out.printf("%d - %s - %d, %s (%d, %s votos) / %s (%d, %d voto)\n",
                             pos,p.getSigla_partido(),p.getNumero_partido(), p.getCandidatos().get(0).getNome_urna(),
                             p.getCandidatos().get(0).getNumero(), p.getCandidatos().get(0).getVotos_nominais(),
                             p.getCandidatos().get(p.getCandidatos().size() - 1).getNome_urna(),
                             p.getCandidatos().get(p.getCandidatos().size() - 1).getNumero(),
                             p.getCandidatos().get(p.getCandidatos().size() - 1).getVotos_nominais());
                }
                pos++;
            }
        }

        //Configura a data da eleição
        LocalDate data_eleicao = LocalDate.parse(args[2], DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        //Variaveis para imprimir os itens 9 e 10
        int[] intervalos = {0,0,0,0,0}; // Cada posição indica um dos intervalos determinados, em ordem
        int masculinos = 0;
        int femininos = 0;
        LocalDate data_nascimento;

        //Varre os candidatos eleitos e determina o intervalo de idade e o sexo
        for (Candidato c : candidatos_eleitos) {

            //Compara as datas
            data_nascimento = LocalDate.parse(c.getData_nasc(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            //Verifica se é menor que 30
            if (Period.between(data_nascimento,data_eleicao).getYears() < 30) {
                intervalos[0]++;
            } //Verifica se está entre 30 e 40
            else if (Period.between(data_nascimento,data_eleicao).getYears() >= 30 && Period.between(data_nascimento,data_eleicao).getYears() < 40) {
                intervalos[1]++;
            } //Verifica se está entre 40 e 50
            else if (Period.between(data_nascimento,data_eleicao).getYears() >=40 && Period.between(data_nascimento,data_eleicao).getYears() < 50 ) {
                intervalos[2]++;
            } //Verifica se está entre 50 e 60
            else if (Period.between(data_nascimento,data_eleicao).getYears() >= 50 && Period.between(data_nascimento,data_eleicao).getYears() < 60) {
                intervalos[3]++;
            } //Se não entrar em nenhum if, possui mais que 60
            else {
                intervalos[4]++;
            }

            //Verifica se é masculino ou feminino
            if (Objects.equals(c.getSexo(),"M")) {
                masculinos++;
            } else {
                femininos++;
            }
        }

        //Imprime a distribuição de eleitos por faixa etária, considerando a idade do candidato no dia da eleição (9)
        System.out.println("Eleitos, por faixa etária (na data da eleição):");
        System.out.printf(Locale.FRANCE,"      Idade < 30: %d (%,.2f%%)\n",intervalos[0],100.0 * intervalos[0] / vagas);
        System.out.printf(Locale.FRANCE,"30 <= Idade < 40: %d (%,.2f%%)\n",intervalos[1],100.0 * intervalos[1] / vagas);
        System.out.printf(Locale.FRANCE,"40 <= Idade < 50: %d (%,.2f%%)\n",intervalos[2],100.0 * intervalos[2] / vagas);
        System.out.printf(Locale.FRANCE,"50 <= Idade < 60: %d (%,.2f%%)\n",intervalos[3],100.0 * intervalos[3] / vagas);
        System.out.printf(Locale.FRANCE,"60 <= Idade     : %d (%,.2f%%)\n",intervalos[4],100.0 * intervalos[4] / vagas);


        //Imprime a distribuição de eleitos por sexo (10)
        System.out.println("Eleitos, por sexo:");
        System.out.printf(Locale.FRANCE,"Feminino: %d (%,.2f%%)\n",femininos, 100.0 * femininos / vagas);
        System.out.printf(Locale.FRANCE,"Masculino: %d (%,.2f%%)\n",masculinos, 100.0 * masculinos / vagas);


        int votos_validos = 0;
        int votos_nominais = 0;
        int votos_legenda = 0;

        //Calcula os votos totais, nominais e de legenda
        for (Partido p : partidos) {
            votos_validos += p.getVotos_totais();
            votos_nominais += p.getVotos_nominais();
            votos_legenda += p.getVotos_legenda();
        }

        //Imprime o total de votos, os nominais e os de legenda (11)
        System.out.printf("Total de votos válidos:    %d\n",votos_validos);
        System.out.printf(Locale.FRANCE,"Total de votos nominais:   %d (%,.2f%%)\n",votos_nominais, 100.0 * votos_nominais/votos_validos);
        System.out.printf(Locale.FRANCE,"Total de votos de legenda: %d (%,.2f%%)\n",votos_legenda, 100.0 * votos_legenda/votos_validos);
    }
}