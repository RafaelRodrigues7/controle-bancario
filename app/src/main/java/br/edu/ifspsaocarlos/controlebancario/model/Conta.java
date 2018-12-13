package br.edu.ifspsaocarlos.controlebancario.model;

import java.io.Serializable;

/**
 * Created by Leo Lira on 08/12/2018.
 */

public class Conta implements Serializable {

    private long id;
    private String descricao;
    private String valor;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


}
