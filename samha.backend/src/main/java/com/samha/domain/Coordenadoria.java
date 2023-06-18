package com.samha.domain;

import com.samha.domain.log.CoordenadoriaAud;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Audited
@Table(name = "coordenadoria")
public class Coordenadoria extends BaseLogEntity implements Comparable<Object> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, unique = true)
    private String nome;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eixo_id", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Eixo eixo;
    
    public Coordenadoria() {
    }

    public Coordenadoria(int id, String nome, Eixo eixo) {
        this.id = id;
        this.nome = nome;
        this.eixo = eixo;
    }

    public Coordenadoria(String nome, Eixo eixo) {
        this.nome = nome;
        this.eixo = eixo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Eixo getEixo() {
        return eixo;
    }

    public void setEixo(Eixo eixo) {
        this.eixo = eixo;
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
    public Object[] toArray() {
        return new Object[] { this };
    }

    @Override
    public int compareTo(Object o) {
        Coordenadoria other = (Coordenadoria) o;
        return this.getNome().compareTo(other.getNome());
    }

    @Override
    public Class getLogEntity() {
        return CoordenadoriaAud.class;
    }
}
