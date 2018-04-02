package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 24/03/2018.
 */

public class Usuario {
    private int id,tipoUser,idEmpresa;
    private String nome,sobrenome,email;

    public Usuario(int id, int tipoUser,int idEmpresa, String nome, String sobrenome, String email) {
        this.id = id;
        this.tipoUser = tipoUser;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idEmpresa = idEmpresa;
        this.email = email;
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

    public int getUserIdEmpresa(){
        return idEmpresa;
    }

    public String getUserEmail(){
        return email;
    }
}
