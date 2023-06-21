package com.samha.domain.dto;

import com.samha.domain.Aula;

import java.io.Serializable;
import java.util.List;

public class OfertaDto implements Serializable {
    private List<Aula> aulas;

    private Integer ofertaId;

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public Integer getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(Integer ofertaId) {
        this.ofertaId = ofertaId;
    }
}
