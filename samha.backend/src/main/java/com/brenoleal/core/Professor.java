package com.brenoleal.core;

import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "professor")
public class Professor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int professor_id;

    @Column(nullable = false)
    private double cargaHoraria;
    
    @Column(nullable = false)
    private boolean ativo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "coordenadoria_id", nullable = false)
    private Coordenadoria coordenadoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Usuario usuario;
    
//    public String obterNomeAbreviado(){
//
//        int espaco = this.getNome().indexOf(" ");
//
//        if(espaco > 0){
//
//            String nomeAbreviado = this.getNome().substring(0, espaco) + " ";
//
//            for(int indice = espaco; indice < this.getNome().length() - 1; indice++){
//                char caractere = this.getNome().charAt(indice);
//                if(caractere == ' '){
//                    char letra = this.getNome().charAt(indice + 1);
//                    if(letra != 'd')
//                        nomeAbreviado = nomeAbreviado + letra;
//                }
//            }
//
//            return nomeAbreviado;
//        }
//        return getNome();
//    }
    
//    public String obterPrimeiroNome(){
//
//        int espaco = this.getNome().indexOf(" ");
//
//        if(espaco > 0)
//            return this.getNome().substring(0, espaco);
//
//        return getNome();
//    }

    public double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Coordenadoria getCoordenadoria() {
        return coordenadoria;
    }

    public void setCoordenadoria(Coordenadoria coordenadoria) {
        this.coordenadoria = coordenadoria;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public Object[] toArray() {
       // return new Object[] { this, getMatricula(), getCoordenadoria().getNome()};
        return null;
    }
    
    public Object[] toArrayCargaHoraria() {
        return new Object[] { this, getCargaHoraria()};
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(int professor_id) {
        this.professor_id = professor_id;
    }
}
