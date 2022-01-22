import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

public class App {
    public static void main( String[] args ) throws Exception {

        ArrayList<Candidato> candidatos = new ArrayList<>();

        Scanner CandidatosCSV = new Scanner(new File(args[0]));
        CandidatosCSV.useDelimiter("\n");
        CandidatosCSV.next();

        StringTokenizer linhaCandidatos;

        while (CandidatosCSV.hasNext()) {
            linhaCandidatos = new StringTokenizer(CandidatosCSV.next());

            while (linhaCandidatos.hasMoreTokens()) {
                Candidato p = new Candidato();
                p.registraCandidato(linhaCandidatos);
                candidatos.add(p);
            }
        }

        CandidatosCSV.close();

        for(Candidato p : candidatos) {
            p.imprimeCandidato();
        }


        int eleitos = 0;
        for (Candidato p : candidatos) {
            if (Objects.equals(p.getSituacao(), "Eleito")) {
                eleitos++;
            }
        }

        System.out.println("Número de vagas:" + eleitos);

        ArrayList<Partido> partidos = new ArrayList<>();

        Scanner partidosCSV = new Scanner(new File(args[1]));
        partidosCSV.useDelimiter("\n");
        partidosCSV.next();

        StringTokenizer linhaPartidos;

        while (partidosCSV.hasNext()) {
            linhaPartidos = new StringTokenizer(partidosCSV.next());

            while (linhaPartidos.hasMoreTokens()) {
                Partido p = new Partido();
                p.registraPartido(linhaPartidos);
                partidos.add(p);
            }
        }

        for (Partido p : partidos) {
            p.imprimePartido();
        }
    }
}
