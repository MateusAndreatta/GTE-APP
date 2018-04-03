package xyz.sistemagte.gte.Construtoras;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public Usuario(JSONObject jsonObject) {
        JSONObject jsonObject1 = new JSONObject(jsonObject);
        JSONArray jsonArray = new JSONArray();
        jsonArray.getJSONObject(0);
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
