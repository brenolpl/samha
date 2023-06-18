package com.samha.domain;

import com.samha.domain.log.ProfessorAud;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Audited
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "professor_id")
public class Professor extends Servidor implements Comparable<Object>{
    @Column(nullable = false)
    private Double cargaHoraria;

    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "coordenadoria_id", nullable = false)
    private Coordenadoria coordenadoria;

    public String obterNomeAbreviado(){

        int espaco = this.getNome().indexOf(" ");

        if(espaco > 0){

            String nomeAbreviado = this.getNome().substring(0, espaco) + " ";

            for(int indice = espaco; indice < this.getNome().length() - 1; indice++){
                char caractere = this.getNome().charAt(indice);
                if(caractere == ' '){
                    char letra = this.getNome().charAt(indice + 1);
                    if(letra != 'd')
                        nomeAbreviado = nomeAbreviado + letra;
                }
            }

            return nomeAbreviado;
        }
        return getNome();
    }

    public Double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Double cargaHoraria) {
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

    @Override
    public Class getLogEntity() {
        return ProfessorAud.class;
    }
}
