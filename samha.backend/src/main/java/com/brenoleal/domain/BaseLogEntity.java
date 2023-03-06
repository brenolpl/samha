package com.brenoleal.domain;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Para a implementação desta classe, foi realizada uma junção de Hibernate Envers com o Spring Entity Listeners.
 * O Hibernate Envers usa a tag @Audited para gerar uma tabela com os mesmo campos que a entidade mapeada e insere automáticamente
 * os registros nesta tabela com um nível de versionamento.
 * Já o Entity Listener cria estes quatro campos abaixo na entidade mapeada tornando possível saber a data das alterações
 * e o autor destas. Juntando as duas soluções em uma mesma classe além de termos os campos abaixos na entidade mapeada
 * também temos eles na tabela de auditoria criada (sufixo _aud), tornando possível fazer um gerenciamento avançado das alterações.
 *
 * O Ponto negativo é que os quatro campos abaixo também são replicados para as entidades mapeadas sem o sufixo _aud.
 */

@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public class BaseLogEntity implements Serializable {

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private Long createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Long modifiedDate;

    @Column(name = "created_by", updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    public LocalDateTime getCreatedDate() {

        if(createdDate != null) return LocalDateTime.ofInstant(Instant.ofEpochMilli(createdDate), ZoneOffset.UTC);
        else return null;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        if(modifiedDate != null) return LocalDateTime.ofInstant(Instant.ofEpochMilli(modifiedDate), ZoneOffset.UTC);
        else return null;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
