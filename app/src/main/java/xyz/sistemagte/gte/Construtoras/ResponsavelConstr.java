package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Aluno on 20/04/2018.
 */

public class ResponsavelConstr {
    String nome,sobrenome,email,cpf,rg,dataNasc;
    int id,idEmpresa;

    public ResponsavelConstr(String nome, String sobrenome, String email, String cpf, String rg, String dataNasc, int id, int idEmpresa) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNasc = dataNasc;
        this.id = id;
        this.idEmpresa = idEmpresa;
    }

    public String getNomeResp() {
        return nome;
    }

    public String getSobrenomeResp() {
        return sobrenome;
    }

    public String getEmailResp() {
        return email;
    }

    public String getCpfResp() {
        return cpf;
    }

    public String getRgResp() {
        return rg;
    }

    public String getDataNascResp() {
        return dataNasc;
    }

    public int getIdResp() {
        return id;
    }

    public int getIdEmpresaResp() {
        return idEmpresa;
    }
}
