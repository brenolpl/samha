package com.brenoleal.samha.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eixo")
public class Eixo implements Serializable, Comparable<Object> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}