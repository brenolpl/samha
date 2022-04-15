package com.brenoleal.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, unique = true)
    private String login;
    
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String matricula;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Papel> papeis;

    public Usuario(){    
    }

    public Usuario(int id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public Usuario(int id, String nome, String matricula, String email) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
    }

    public Usuario(String nome, String matricula, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(Collection<Papel> papeis) {
        this.papeis = papeis;
    }
}
