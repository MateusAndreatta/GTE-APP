package xyz.sistemagte.gte;

/**
 * Created by Andreatta on 06/03/2018.
 */

public class Hero {
    String nome, sobrenome;

    public Hero(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

}