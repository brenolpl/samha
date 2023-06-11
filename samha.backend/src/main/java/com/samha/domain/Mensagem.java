package com.samha.domain;

import java.util.ArrayList;
import java.util.List;

public class Mensagem {
    private List<String> restricoes = new ArrayList<>();
    private String cor;
    private String titulo;
    //1 vermelho, 2 amarelo, 3 azul.
    private int tipo;


    public List<String> getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(List<String> restricoes) {
        this.restricoes = restricoes;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
