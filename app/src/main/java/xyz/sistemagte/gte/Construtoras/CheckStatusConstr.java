package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Aluno on 14/06/2018.
 */

public class CheckStatusConstr {
    private int id;
    private String nome,sobrenome;
    private String horaEntrada,horaEscola,horaSaida,horaCasa;
    private String btnText;

    public CheckStatusConstr(int id, String nome, String sobrenome, String horaEntrada, String horaEscola, String horaSaida, String horaCasa) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.horaEntrada = horaEntrada;
        this.horaEscola = horaEscola;
        this.horaSaida = horaSaida;
        this.horaCasa = horaCasa;
    }

    public CheckStatusConstr(String btn){
        this.btnText = btn;
    }

    public int getIdCriancaCheck() {
        return id;
    }

    public String getNomeCheck() {
        return nome;
    }

    public String getSobrenomeCheck() {
        return sobrenome;
    }

    public String getHoraEntradaCheck() {
        return horaEntrada;
    }

    public String getHoraEscolaCheck() {
        return horaEscola;
    }

    public String getHoraSaidaCheck() {
        return horaSaida;
    }

    public String getHoraCasaCheck() {
        return horaCasa;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void setHoraEscola(String horaEscola) {
        this.horaEscola = horaEscola;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public void setHoraCasa(String horaCasa) {
        this.horaCasa = horaCasa;
    }
}
