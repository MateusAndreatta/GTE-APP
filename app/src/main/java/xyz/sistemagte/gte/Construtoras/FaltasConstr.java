package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Aluno on 13/06/2018.
 */

public class FaltasConstr {

    String nome,placa,sobrenome;

    public FaltasConstr(String nome, String placa,String sobre) {
        this.nome = nome;
        this.placa = placa;
        this.sobrenome = sobre;
    }

    public String getNomeFalta() {
        return nome;
    }

    public String getPlacaFalta() {
        return placa;
    }

    public String getSobrenomePlaca(){
        return sobrenome;
    }
}
