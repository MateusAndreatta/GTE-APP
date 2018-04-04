package xyz.sistemagte.gte.Auxiliares;

import android.content.Context;
import android.widget.Toast;

import xyz.sistemagte.gte.R;

/**
 * Created by Andreatta on 01/04/2018.
 */

public class Validacoes {

    public boolean ValidarSenhas(Context ctx,String senha1, String senha2){
        if(senha1.equals(senha2)){
            if(senha1.length() < 8){
                Toast.makeText(ctx, ctx.getResources().getString(R.string.senhasMenor), Toast.LENGTH_SHORT).show();
                return false;
            }else{
                if(senha1.length() > 12 ){
                    Toast.makeText(ctx, ctx.getResources().getString(R.string.senhasMaior), Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    return true;
                }

            }

        }else{
            Toast.makeText(ctx, ctx.getResources().getString(R.string.senhasDiferentes), Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}