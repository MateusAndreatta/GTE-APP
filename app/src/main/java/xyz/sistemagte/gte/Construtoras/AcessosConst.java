package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 06/04/2018.
 */

public class AcessosConst {
    String nome,sobrenome, data, tipo;

    public AcessosConst(String nome, String sobrenome,String tipo, String data) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.data = data;
        this.tipo = tipo;
    }

    public String getNomeAcessos() {
        return nome;
    }

    public String getSobrenomeAcessos() {
        return sobrenome;
    }

    public String getDataAcessos() {
        return data;
    }

    public String getTipoAcessos() {
        return tipo;
    }
}
