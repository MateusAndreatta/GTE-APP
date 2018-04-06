package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Aluno on 06/04/2018.
 */

public class CriancaConst {
    String nome, sobrenome, responsavel;

    public CriancaConst(String nome, String sobrenome, String responsavel) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.responsavel = responsavel;
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
}
