package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Aluno on 06/04/2018.
 */

public class CriancaConst {
    String nome, sobrenome, responsavel,cpf,idCrianca;

    public CriancaConst(String nome, String sobrenome, String responsavel, String cpf, String idC) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.responsavel = responsavel;
        this.cpf = cpf;
        this.idCrianca = idC;
    }

    public CriancaConst(String nome, String sobrenome, String cpf,String idC) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.idCrianca = idC;
    }

    public String getNomeCrianca() {
        return nome;
    }

    public String getSobrenomeCrianca() {
        return sobrenome;
    }

    public String getResponsavelCrianca() {
        return responsavel;
    }

    public String getCpfCrianca(){
        return cpf;
    }

    public String getIdCrianca() {
        return idCrianca;
    }
}
