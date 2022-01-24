import java.util.StringTokenizer;

public class Partido {

    private int numero_partido;
    private int votos_legenda;
    private String nome_partido;
    private String sigla_partido;
    private int votos_totais;
    private int votos_nominais;
    private int candidatos_eleitos;

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

    public int getVotos_totais() {
        return votos_totais;
    }

    public void setVotos_totais(int votos_totais) {
        this.votos_totais = votos_totais;
    }

    public int getVotos_nominais() {
        return votos_nominais;
    }

    public void setVotos_nominais(int votos_nominais) {
        this.votos_nominais = votos_nominais;
    }

    public int getCandidatos_eleitos() {
        return candidatos_eleitos;
    }

    public void setCandidatos_eleitos(int candidatos_eleitos) {
        this.candidatos_eleitos = candidatos_eleitos;
    }

    public void registraPartido(StringTokenizer linha) {
        this.setNumero_partido(Integer.parseInt(linha.nextToken(",")));
        this.setVotos_legenda(Integer.parseInt(linha.nextToken(",")));
        this.setNome_partido(linha.nextToken(","));
        this.setSigla_partido(linha.nextToken(",\r"));
        this.setVotos_totais(this.votos_legenda);
        this.setVotos_nominais(0);
        this.setCandidatos_eleitos(0);
    }

    @Override
    public String toString() {
        if (this.getCandidatos_eleitos() > 1) {
            return (" - " + this.getSigla_partido() + " - " + this.getNumero_partido() + ", " + this.getVotos_totais() + " votos (" + this.getVotos_nominais() + " nominais e " + this.getVotos_legenda() + " de legenda), " + this.getCandidatos_eleitos() + " candidatos eleitos");
        }
        else {
            return (" - " + this.getSigla_partido() + " - " + this.getNumero_partido() + ", " + this.getVotos_totais() + " votos (" + this.getVotos_nominais() + " nominais e " + this.getVotos_legenda() + " de legenda), " + this.getCandidatos_eleitos() + " candidato eleito");

        }
    }

    public void incrementaVotos(int votos) {
        this.votos_nominais += votos;
        this.votos_totais += votos;
    }

    public void incrementaCandidatosEleitos() {
        this.candidatos_eleitos++;
    }
}
