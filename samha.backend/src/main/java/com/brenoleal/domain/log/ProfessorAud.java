package com.brenoleal.domain.log;

import com.brenoleal.domain.BaseLogEntity;
import com.brenoleal.domain.Coordenadoria;

import javax.persistence.*;

@Entity
@Table(name = "professor_aud")
public class ProfessorAud extends BaseLogEntity {
    @EmbeddedId
    private AuditCompositeKey pk;

    @Column(name = "carga_horaria", updatable = false)
    private Double cargaHoraria;

    @Column(name = "ativo", updatable = false)
    private Boolean ativo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordenadoria_id", updatable = false)
    private Coordenadoria coordenadoria;

    public AuditCompositeKey getPk() {
        return pk;
    }

    public void setPk(AuditCompositeKey pk) {
        this.pk = pk;
    }

    public Double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Coordenadoria getCoordenadoria() {
        return coordenadoria;
    }

    public void setCoordenadoria(Coordenadoria coordenadoria) {
        this.coordenadoria = coordenadoria;
    }
}
