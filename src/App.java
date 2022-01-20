import java.io.File;
import java.util.Scanner;

public class App {
    public static void main( String[] args ) throws Exception {
        Scanner CandidatosCSV = new Scanner(new File("candidatos.csv"));
        CandidatosCSV.useDelimiter(",");
        while (CandidatosCSV.hasNext()) {
            System.out.println(CandidatosCSV.next());
        }

        CandidatosCSV.close();
    }
}
