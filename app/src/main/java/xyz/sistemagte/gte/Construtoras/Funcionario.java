package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 12/03/2018.
 */

public class Funcionario {
    String Nome,Sobrenome,Sexo,Tipo;
    Double Salario;

    public Funcionario(String nome, String sobrenome, String sexo, String tipo) {
        this.Nome = nome;
        this.Sobrenome = sobrenome;
        this.Sexo = sexo;
        this.Tipo = tipo;
    }
}
