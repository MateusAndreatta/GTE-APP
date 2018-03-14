package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 14/03/2018.
 */

public class FuncConst {
    String nome, sobrenome;

    public FuncConst(String nome, String sobrenome){
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public String getNome(){
        return this.nome;
    }

    public String getSobrenome(){
        return this.sobrenome;
    }

}
