package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Aluno on 10/05/2018.
 */

public class MotoristaConstr {
    String nome, sobrenome;
    Integer id_motorista;

    public MotoristaConstr(String nome, String sobrenome, Integer id_motorista) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.id_motorista = id_motorista;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Integer getId_motorista() {
        return id_motorista;
    }
}




