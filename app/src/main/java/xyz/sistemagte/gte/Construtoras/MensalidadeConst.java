package xyz.sistemagte.gte.Construtoras;


public class MensalidadeConst {
    String NomeResp, NomeCrianca, Status, SobreResp, SobreCrianca;

    public String getNomeResp() {
        return NomeResp;
    }

    public String getNomeCrianca() {
        return NomeCrianca;
    }

    public String getStatus() {
        return Status;
    }

    public String getSobreResp() {
        return SobreResp;
    }

    public String getSobreCrianca() {
        return SobreCrianca;
    }

    public int getId_resp() {
        return id_resp;
    }

    public int getId_crianca() {
        return id_crianca;
    }

    public Double getValor() {
        return Valor;
    }

    int  id_resp;

    public MensalidadeConst(String nomeResp, String nomeCrianca, String status, String sobreResp, String sobreCrianca, int id_resp, int id_crianca, Double valor) {
        NomeResp = nomeResp;
        NomeCrianca = nomeCrianca;
        Status = status;
        SobreResp = sobreResp;
        SobreCrianca = sobreCrianca;
        this.id_resp = id_resp;
        this.id_crianca = id_crianca;
        Valor = valor;
    }

    int id_crianca;
    Double Valor;


}
