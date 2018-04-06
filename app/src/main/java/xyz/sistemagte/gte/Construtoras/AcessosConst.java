package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Aluno on 06/04/2018.
 */

public class AcessosConst {
    String nome, data, tipo;

    public AcessosConst(String nome, String data, String tipo) {
        this.nome = nome;
        this.data = data;
        this.tipo = tipo;
    }

    public String getNomeAcessos() {
        return nome;
    }

    public String getDataAcessos() {
        return data;
    }

    public String getTipoAcessos() {
        return tipo;
    }
}
