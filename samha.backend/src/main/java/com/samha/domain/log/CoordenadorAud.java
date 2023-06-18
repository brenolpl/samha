package com.samha.domain.log;

import com.samha.domain.BaseLogEntity;
import com.samha.domain.Usuario;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "coordenador_aud")
public class CoordenadorAud extends BaseLogEntity {
    @Override
    public Class getLogEntity() {
        return this.getClass();
    }

    @EmbeddedId
    private CoordenadorAuditPk pk;

    @OneToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    @Embeddable
    @Data
    class CoordenadorAuditPk implements Serializable {
        private Integer rev;
        private Integer servidor_id;
    }

    public CoordenadorAuditPk getPk() {
        return pk;
    }

    public void setPk(CoordenadorAuditPk pk) {
        this.pk = pk;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
