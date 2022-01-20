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
                p.setNumero(Integer.parseInt(linha.nextToken(",")));
                p.setVotos_nominais(Integer.parseInt(linha.nextToken(",")));
                p.setSituacao(linha.nextToken(","));
                p.setNome(linha.nextToken(","));
                p.setNome_urna(linha.nextToken(","));
                p.setSexo(linha.nextToken(","));
                p.setData_nasc(linha.nextToken(","));
                p.setDestino_voto(linha.nextToken(","));
                p.setNumero_partido(Integer.parseInt(linha.nextToken(",\r")));
                candidatos.add(p);
            }
        }

        CandidatosCSV.close();

        for(Candidato p : candidatos) {
            System.out.println(p.getNumero());
            System.out.println(p.getVotos_nominais());
            System.out.println(p.getSituacao());
            System.out.println(p.getNome());
            System.out.println(p.getNome_urna());
            System.out.println(p.getSexo());
            System.out.println(p.getData_nasc());
            System.out.println(p.getDestino_voto());
            System.out.println(p.getNumero_partido());
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
