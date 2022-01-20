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

        StringTokenizer linha;
        int n = 0;

        while (CandidatosCSV.hasNext()) {
            linha = new StringTokenizer(CandidatosCSV.next());

            while (linha.hasMoreTokens()) {
                Candidato p = new Candidato();
                p.registraCandidato(linha);
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

    }
}
