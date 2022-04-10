package com.brenoleal.core;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "coordenador_pedagogico")
@PrimaryKeyJoinColumn(name = "coordenador_pedagogico_id")
public class CoordenadorPedagogico extends Usuario{
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servidor_id", nullable = false)
    @Cascade(CascadeType.ALL)
    private Servidor servidor;

    public CoordenadorPedagogico() {
    }

    public CoordenadorPedagogico(Servidor servidor, int id, String login, String senha) {
        super(id, login, senha);
        this.servidor = servidor;
    }

    public CoordenadorPedagogico(Servidor servidor, String login, String senha) {
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
        return new Object[] { this, getServidor().getMatricula(), "COORDENADOR PEDAGÓGICO" };
    }  
}
