package com.brenoleal.domain.log;

import com.brenoleal.domain.BaseLogEntity;
import com.brenoleal.domain.Papel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "menu_aud")
public class MenuAud extends BaseLogEntity {
    @EmbeddedId
    private AuditCompositeKey pk;

    @Column(name = "nome", updatable = false)
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "papel_id", updatable = false)
    private Set<Papel> papeis;

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

    public Set<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(Set<Papel> papeis) {
        this.papeis = papeis;
    }
}
