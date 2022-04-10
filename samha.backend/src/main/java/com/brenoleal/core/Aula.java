package com.brenoleal.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "aula")
public class Aula implements Serializable, Comparable<Object> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private int numero;
    
    @Column(nullable = false)
    private int dia;
    
    @Column(nullable = false)
    private int turno;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alocacao_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Alocacao alocacao;
     
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oferta_id", nullable = false)
    private Oferta oferta;
    
    public Aula() {
    }

    public Aula(int id, int numero, int dia, int turno, Alocacao alocacao, Oferta oferta) {
        this.id = id;
        this.numero = numero;
        this.dia = dia;
        this.turno = turno;
        this.alocacao = alocacao;
        this.oferta = oferta;
    }

    public Aula(int numero, int dia, int turno, Alocacao alocacao, Oferta oferta) {
        this.numero = numero;
        this.dia = dia;
        this.turno = turno;
        this.alocacao = alocacao;
        this.oferta = oferta;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Alocacao getAlocacao() {
        return alocacao;
    }

    public void setAlocacao(Alocacao alocacao) {
        this.alocacao = alocacao;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }
    
    @Override
    public String toString() { 
        String retorno = getAlocacao().getDisciplina().getSigla() + " - " + getAlocacao().getProfessor1().obterNomeAbreviado();
        if(getAlocacao().getDisciplina().getTipo().toUpperCase().equals("ESPECIAL")){
            retorno = retorno + "/" + getAlocacao().getProfessor2().obterNomeAbreviado();
        }
        return retorno;
    }

    @Override
    public int compareTo(Object o) {
       
        Aula other = (Aula) o;
        if(this.getNumero() > other.getNumero())
            return 1;
        return -1;
    }
}
