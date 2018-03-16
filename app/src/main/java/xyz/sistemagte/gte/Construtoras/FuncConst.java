package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 14/03/2018.
 */

public class FuncConst {
    String nome, sobrenome, tipo;

    public FuncConst(String nome, String sobrenome,String tipo){
        this.nome = nome + " " + sobrenome;
        this.tipo = tipo;
    }

    public String getNome(){
        return this.nome;
    }

    public String getTipo(){
        return this.tipo;
    }

}
