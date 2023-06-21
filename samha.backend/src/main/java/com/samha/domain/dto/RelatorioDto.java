package com.samha.domain.dto;

import java.io.Serializable;

public class RelatorioDto implements Serializable {
    private Integer ano;
    private Integer semestre;
    private Integer eixoId;
    private Integer professorId;
    private Integer coordenadoriaId;

    private Boolean enviarEmail;

    private String senha;

    private Integer turmaId;

    private Integer cursoId;

    private String nomeRelatorio;

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getEixoId() {
        return eixoId;
    }

    public void setEixoId(Integer eixoId) {
        this.eixoId = eixoId;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public Integer getCoordenadoriaId() {
        return coordenadoriaId;
    }

    public void setCoordenadoriaId(Integer coordenadoriaId) {
        this.coordenadoriaId = coordenadoriaId;
    }

    public String getNomeRelatorio() {
        return nomeRelatorio;
    }

    public void setNomeRelatorio(String nomeRelatorio) {
        this.nomeRelatorio = nomeRelatorio;
    }

    public Integer getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Integer turmaId) {
        this.turmaId = turmaId;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public Boolean getEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(Boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
