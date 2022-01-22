import java.util.StringTokenizer;

public class Candidato {

    private int numero;
    private int votos_nominais;
    private String situacao;
    private String nome;
    private String nome_urna;
    private String sexo;
    private String data_nasc;
    private String destino_voto;
    private int numero_partido;
    private Partido partido;

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Candidato() {

    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getVotos_nominais() {
        return votos_nominais;
    }

    public void setVotos_nominais(int votos_nominais) {
        this.votos_nominais = votos_nominais;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_urna() {
        return nome_urna;
    }

    public void setNome_urna(String nome_urna) {
        this.nome_urna = nome_urna;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public String getDestino_voto() {
        return destino_voto;
    }

    public void setDestino_voto(String destino_voto) {
        this.destino_voto = destino_voto;
    }

    public int getNumero_partido() {
        return numero_partido;
    }

    public void setNumero_partido(int numero_partido) {
        this.numero_partido = numero_partido;
    }

    public void registraCandidato(StringTokenizer linha) {
        this.setNumero(Integer.parseInt(linha.nextToken(",")));
        this.setVotos_nominais(Integer.parseInt(linha.nextToken(",")));
        this.setSituacao(linha.nextToken(","));
        this.setNome(linha.nextToken(","));
        this.setNome_urna(linha.nextToken(","));
        this.setSexo(linha.nextToken(","));
        this.setData_nasc(linha.nextToken(","));
        this.setDestino_voto(linha.nextToken(","));
        this.setNumero_partido(Integer.parseInt(linha.nextToken(",\r")));
    }

    public void imprimeCandidato() {
        System.out.println(this.getNumero() + " " + this.getVotos_nominais() + " " + this.getSituacao() + " " + this.getNome() + " " + this.getNome_urna() + " " + this.getSexo() + " " + this.getData_nasc() + " " + this.getDestino_voto() + " " + this.getNumero_partido());
    }

}
