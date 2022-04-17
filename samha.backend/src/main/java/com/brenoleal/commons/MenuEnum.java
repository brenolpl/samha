package com.brenoleal.commons;

public enum MenuEnum {
    PROFESSORES(1, "Professores"),
    COORDENADORES(2, "Coordenadores"),
    ALOCACAO(3, "Alocacao");

    private Integer id;
    private String nome;

    MenuEnum(Integer id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
