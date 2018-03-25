package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 24/03/2018.
 */

public class Usuario {
    private int id,tipoUser;
    private String nome,sobrenome;

    public Usuario(int id, int tipoUser, String nome, String sobrenome) {
        this.id = id;
        this.tipoUser = tipoUser;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public int getUserId() {
        return id;
    }

    public int getUserTipoUser() {
        return tipoUser;
    }

    public String getUserNome() {
        return nome;
    }

    public String getUserSobrenome() {
        return sobrenome;
    }
}
