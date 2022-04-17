package com.brenoleal.core;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Papel> papeis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(Set<Papel> papeis) {
        this.papeis = papeis;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
