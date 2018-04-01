package xyz.sistemagte.gte.metodos;

/**
 * Created by Andreatta on 01/04/2018.
 */

public class Validacoes {

    public boolean ValidarSenhas(String senha1, String senha2){
        if(senha1.equals(senha2))
            return true;
        else
            return false;
    }
}
