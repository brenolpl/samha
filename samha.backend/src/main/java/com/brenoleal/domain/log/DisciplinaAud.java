package com.brenoleal.domain.log;

import com.brenoleal.domain.BaseLogEntity;
import com.brenoleal.domain.MatrizCurricular;

import javax.persistence.*;

@Entity
@Table(name = "disciplina_aud")
public class DisciplinaAud extends BaseLogEntity {

    @EmbeddedId
    private AuditCompositeKey pk;

    @Column(name = "nome", updatable = false)
    private String nome;

    @Column(name = "sigla", updatable = false)
    private String sigla;

    @Column(name = "tipo", updatable = false)
    private String tipo;

    @Column(name = "carga_horaria", updatable = false)
    private int cargaHoraria;

    @Column(name = "qt_aulas", updatable = false)
    private int qtAulas;

    @Column(name = "periodo", updatable = false)
    private int periodo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matriz_id", updatable = false)
    private MatrizCurricular matriz;

    public AuditCompositeKey getPk() {
        return pk;
    }

    public void setPk(AuditCompositeKey pk) {
        this.pk = pk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getQtAulas() {
        return qtAulas;
    }

    public void setQtAulas(int qtAulas) {
        this.qtAulas = qtAulas;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public MatrizCurricular getMatriz() {
        return matriz;
    }

    public void setMatriz(MatrizCurricular matriz) {
        this.matriz = matriz;
    }
}
