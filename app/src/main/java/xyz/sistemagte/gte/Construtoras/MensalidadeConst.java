package xyz.sistemagte.gte.Construtoras;


public class MensalidadeConst {
    String NomeResp, NomeCrianca, Status;
    Double Valor;

    public String getNomeResp() {
        return NomeResp;
    }

    public String getNomeCrianca() {
        return NomeCrianca;
    }

    public String getStatus() {
        return Status;
    }

    public Double getValor() {
        return Valor;
    }

    public MensalidadeConst(String nomeResp, String nomeCrianca, String status, Double valor) {
        NomeResp = nomeResp;
        NomeCrianca = nomeCrianca;
        Status = status;
        Valor = valor;
    }
}
