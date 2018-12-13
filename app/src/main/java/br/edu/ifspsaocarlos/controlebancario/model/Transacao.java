package br.edu.ifspsaocarlos.controlebancario.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Leo Lira on 09/12/2018.
 */

public class Transacao  implements Serializable {

    private long id;
    private String tipo;
    private String valor;
    private String conta;
    private String periodo;
    private int repeticao;
    private int repetido;
    private String data;



    public long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getData() {
        return data;
    }

    public String getValor() {
        return valor;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }


    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(int repeticao) {
        this.repeticao = repeticao;
    }

    public int getRepetido() {
        return repetido;
    }

    public void setRepetido(int repetido) {
        this.repetido = repetido;
    }
}
