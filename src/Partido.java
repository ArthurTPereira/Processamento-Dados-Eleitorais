import java.util.StringTokenizer;

public class Partido {

    private int numero_partido;
    private int votos_legenda;
    private String nome_partido;
    private String sigla_partido;


    public Partido() {

    }

    public int getNumero_partido() {
        return numero_partido;
    }

    public void setNumero_partido(int numero_partido) {
        this.numero_partido = numero_partido;
    }

    public int getVotos_legenda() {
        return votos_legenda;
    }

    public void setVotos_legenda(int votos_legenda) {
        this.votos_legenda = votos_legenda;
    }

    public String getNome_partido() {
        return nome_partido;
    }

    public void setNome_partido(String nome_partido) {
        this.nome_partido = nome_partido;
    }

    public String getSigla_partido() {
        return sigla_partido;
    }

    public void setSigla_partido(String sigla_partido) {
        this.sigla_partido = sigla_partido;
    }

    public void registraPartido(StringTokenizer linha) {
        this.setNumero_partido(Integer.parseInt(linha.nextToken(",")));
        this.setVotos_legenda(Integer.parseInt(linha.nextToken(",")));
        this.setNome_partido(linha.nextToken(","));
        this.setSigla_partido(linha.nextToken(",\r"));
    }

    public void imprimePartido() {
        System.out.println(this.getNumero_partido() + " " + this.getVotos_legenda() + " " + this.getNome_partido() + " " + this.getSigla_partido());
    }
}
