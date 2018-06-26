package xyz.sistemagte.gte.Auxiliares;

import android.app.Application;

import com.flurry.android.FlurryAgent;

/**
 * Created by Andreatta on 04/04/2018.
 */

public class GlobalUser extends Application {

    public static int GlobalUserID,GlobalUserTipoUser,GlobalUserIdEmpresa;
    public static String GlobalUserNome,GlobalUserSobrenome,GlobalUserEmail;

    public int getGlobalUserID() {
        return GlobalUserID;
    }

    public int getGlobalUserTipoUser() {
        return GlobalUserTipoUser;
    }

    public int getGlobalUserIdEmpresa() {
        return GlobalUserIdEmpresa;
    }

    public String getGlobalUserNome() {
        return GlobalUserNome;
    }

    public String getGlobalUserSobrenome() {
        return GlobalUserSobrenome;
    }

    public String getGlobalUserEmail() {
        return GlobalUserEmail;
    }

    public void setGlobalUserID(int globalUserID) {
        GlobalUserID = globalUserID;
    }

    public void setGlobalUserTipoUser(int globalUserTipoUser) {
        GlobalUserTipoUser = globalUserTipoUser;
    }

    public void setGlobalUserIdEmpresa(int globalUserIdEmpresa) {
        GlobalUserIdEmpresa = globalUserIdEmpresa;
    }

    public void setGlobalUserNome(String globalUserNome) {
        GlobalUserNome = globalUserNome;
    }

    public void setGlobalUserSobrenome(String globalUserSobrenome) {
        GlobalUserSobrenome = globalUserSobrenome;
    }

    public void setGlobalUserEmail(String globalUserEmail) {
        GlobalUserEmail = globalUserEmail;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "GC9KRTVDYX3D9DQ7BB2X");
    }

}
