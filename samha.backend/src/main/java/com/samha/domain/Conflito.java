package com.samha.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conflito implements Serializable {

    private List<Mensagem> mensagens;
    private Professor professor;

    public Conflito() {
        this.mensagens = new ArrayList<>();
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }
}
