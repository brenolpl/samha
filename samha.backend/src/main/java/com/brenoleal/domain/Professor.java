package com.brenoleal.domain;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "professor_id")
public class Professor extends Servidor implements Comparable<Object>{

    @Column(nullable = false)
    private double cargaHoraria;

    @Column(nullable = false, unique = false)
    private boolean ativo;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "coordenadoria_id", nullable = false)
    private Coordenadoria coordenadoria;


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

//    public Object[] toArray() {
//        return new Object[] { this, getMatricula(), getCoordenadoria().getNome()};
//    }

    public Object[] toArrayCargaHoraria() {
        return new Object[] { this, getCargaHoraria()};
    }

    @Override
    public int compareTo(Object o) {

        Professor other = (Professor) o;
        return this.getNome().compareTo(other.getNome());
    }
}
