package com.samha.domain.dto;

import com.samha.domain.Aula;
import com.samha.domain.Oferta;

import java.io.Serializable;
import java.util.List;

public class RestricaoRequest implements Serializable {
    private List<Aula> aulas;
    private Oferta oferta;

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }
}
