package com.brenoleal.domain.log;

import com.brenoleal.domain.BaseLogEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "eixo_aud")
public class EixoAud extends BaseLogEntity {
    @EmbeddedId
    private AuditCompositeKey pk;

    @Column(name = "nome", updatable = false)
    private String nome;

    @Column(name = "revtype", updatable = false)
    private Integer revtype;

    public AuditCompositeKey getPk() {
        return pk;
    }

    public void setPk(AuditCompositeKey pk) {
        this.pk = pk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getRevtype() {
        return revtype;
    }

    public void setRevtype(Integer revtype) {
        this.revtype = revtype;
    }
}
