package xyz.sistemagte.gte.Auxiliares;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Aluno on 04/05/2018.
 */

public class Sessao {

    public static final String PREFS_NAME = "PREF_GTE";

    private static Context contexto;

    private static SharedPreferences preferences;

    private SharedPreferences getPreferences() {
        return preferences;
    }

    public Sessao(Context contexto) {
        this.contexto = contexto;
        preferences = contexto.getSharedPreferences(PREFS_NAME, 0);
    }

    private SharedPreferences.Editor getEditor(){
        return preferences.edit();
    }

    public void setString(String name, String value){
        SharedPreferences.Editor editor = getEditor();
        editor.putString(name,value);
        editor.commit();
    }
    public void setBoolean(String name, boolean value){
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(name,value);
        editor.commit();
    }
    public String getString(String name){
        String retorno = getPreferences().getString(name, null);
        return retorno;
    }
    public boolean getBoolean(String name){
        boolean retorno = getPreferences().getBoolean(name, false);
        return retorno;
    }
}




