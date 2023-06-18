package com.samha.domain;

import com.samha.domain.log.EixoAud;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Audited
@Table(name = "eixo")
public class Eixo extends BaseLogEntity implements Comparable<Object> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String nome;

    public Eixo() {
    }

    public Eixo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Eixo(String nome) {
        this.nome = nome;
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

    @Override
    public String toString() {
        return nome;
    }
    
    public Object[] toArray() {
        return new Object[] { this };
    }   

    @Override
    public int compareTo(Object o) {
        Eixo other = (Eixo) o;
        return this.getNome().compareTo(other.getNome());
    }

    @Override
    public Class getLogEntity() {
        return EixoAud.class;
    }
}
