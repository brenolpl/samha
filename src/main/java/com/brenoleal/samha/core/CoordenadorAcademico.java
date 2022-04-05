package com.brenoleal.samha.core;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "coordenador_academico")
@PrimaryKeyJoinColumn(name = "coordenador_academico_id")
public class CoordenadorAcademico extends Usuario{
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servidor_id", nullable = false)
    @Cascade(CascadeType.ALL)
    private Servidor servidor;

    public CoordenadorAcademico() {
    }

    public CoordenadorAcademico(Servidor servidor, int id, String login, String senha) {
        super(id, login, senha);
        this.servidor = servidor;
    }

    public CoordenadorAcademico(Servidor servidor, String login, String senha) {
        super(login, senha);
        this.servidor = servidor;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }
    
    @Override
    public String toString() {
        return servidor.getNome();
    }

    public Object[] toArray() {
        return new Object[] { this, getServidor().getMatricula(), "COORDENADOR ACADÊMICO" };
    }
}
