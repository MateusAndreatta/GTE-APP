package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 08/04/2018.
 */

public class VansConstr {
    String modelo,marca,placa,motorista;
    int ano,capacidade,id;

    public VansConstr(String modelo, String marca, String placa, int ano, int capacidade, String motorista, int id) {
        this.modelo = modelo;
        this.marca = marca;
        this.placa = placa;
        this.ano = ano;
        this.capacidade = capacidade;
        this.motorista = motorista;
        this.id = id;
    }

    public String getModeloVans() {
        return modelo;
    }

    public String getMarcaVans() {
        return marca;
    }

    public String getPlacaVans() {
        return placa;
    }

    public String getMotoristaVans(){
        return motorista;
    }

    public int getAnoVans() {
        return ano;
    }

    public int getCapacidadeVans() {
        return capacidade;
    }

    public int getIdVans(){ return id;}

}
