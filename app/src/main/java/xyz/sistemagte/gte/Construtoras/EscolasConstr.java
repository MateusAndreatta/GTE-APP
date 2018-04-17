package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Andreatta on 13/04/2018.
 */

public class EscolasConstr {
    String nome,cep, rua,numero,complemento,estado,cidade;
    int idEscola,idEnderecoEscola;

    public EscolasConstr(String nome, String cep, String rua, String numero, String complemento, String estado, String cidade, int idEscola, int idEnderecoEscola) {
        this.nome = nome;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.estado = estado;
        this.cidade = cidade;
        this.idEscola = idEscola;
        this.idEnderecoEscola = idEnderecoEscola;
    }

    public String getNomeEscola() {
        return nome;
    }

    public String getCepEscola() {
        return cep;
    }

    public String getRuaEscola() {
        return rua;
    }

    public String getNumeroEscola() {
        return numero;
    }

    public String getComplementoEscola() {
        return complemento;
    }

    public String getEstadoEscola() {
        return estado;
    }

    public String getCidadeEscola() {
        return cidade;
    }

    public int getIdEscola() {
        return idEscola;
    }

    public int getIdEnderecoEscolaEscola() {
        return idEnderecoEscola;
    }
}
