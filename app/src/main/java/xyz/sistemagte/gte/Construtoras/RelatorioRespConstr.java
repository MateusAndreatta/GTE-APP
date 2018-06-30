package xyz.sistemagte.gte.Construtoras;

/**
 * Created by Liver on 30/06/2018.
 */

public class RelatorioRespConstr {
    String Nome_crianca, hora_entrada, hora_escola, hora_saida, hora_casa;

    public RelatorioRespConstr(String nome_crianca, String hora_entrada, String hora_escola, String hora_saida, String hora_casa) {
        Nome_crianca = nome_crianca;
        this.hora_entrada = hora_entrada;
        this.hora_escola = hora_escola;
        this.hora_saida = hora_saida;
        this.hora_casa = hora_casa;
    }

    public String getNome_crianca() {
        return Nome_crianca;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public String getHora_escola() {
        return hora_escola;
    }

    public String getHora_saida() {
        return hora_saida;
    }

    public String getHora_casa() {
        return hora_casa;
    }
}
