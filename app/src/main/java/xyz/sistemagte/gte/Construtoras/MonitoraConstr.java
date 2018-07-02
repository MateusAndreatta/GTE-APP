package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 02/07/2018.
 */

public class MonitoraConstr {

    String nome, sobrenome;
    Integer idMonitora;

    public MonitoraConstr(String nome, String sobrenome, Integer id) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idMonitora = id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Integer getIdMonitora() {
        return idMonitora;
    }
}
