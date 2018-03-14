package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 12/03/2018.
 */

public class Funcionario {
    //String Nome,Sobrenome,Sexo,Tipo;
    //Double Salario;

    String Nome,Sobrenome;


    public Funcionario(String nome, String sobrenome) {
        this.Nome = nome;
        this.Sobrenome = sobrenome;
    }

    public String getNome() {
        return Nome;
    }

    public String getSobrenome() {
        return Sobrenome;
    }

   //public String getSexo() {
   //    return Sexo;
   //}

   //public String getTipo() {
   //    return Tipo;
   //}

   //public Double getSalario() {
   //    return Salario;
   //}
}
