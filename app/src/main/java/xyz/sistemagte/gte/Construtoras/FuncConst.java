package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 14/03/2018.
 */

public class FuncConst {
    String nome, sobrenome, tipo,id;
    public FuncConst(String nome, String sobrenome,String tipo,String id){
        this.nome = nome + " " + sobrenome;
        this.tipo = tipo;
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getTipo(){
        return this.tipo;
    }

    public String getId() {
        return id;
    }
}
