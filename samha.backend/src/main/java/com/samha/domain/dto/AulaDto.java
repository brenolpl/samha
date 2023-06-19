package com.samha.domain.dto;

public class AulaDto {
    private Integer id;
    private int dia;
    private int numero;

    private String nomeTurma;
    private String nomeDisciplina;

    public AulaDto(Integer id, int dia, int numero, String nomeTurma, String nomeDisciplina) {
        this.id = id;
        this.dia = dia;
        this.numero = numero;
        this.nomeTurma = nomeTurma;
        this.nomeDisciplina = nomeDisciplina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }
}
