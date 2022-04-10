package com.brenoleal.domain.legacy;

import com.brenoleal.core.*;

import java.util.ArrayList;
import java.util.List;

public class GtRelatorio {

    private GtPrincipal gtPrincipal;
    
    public GtRelatorio(GtPrincipal gt){
        gtPrincipal = gt;
    }
    
    public List listarAulasProfessor(int ano, int semestre, int idProfessor){
        if(gtPrincipal.getGtAula().getListaAulasAnoSemestre() == null)
            atualizarAulasAnoSemestre(ano, semestre);
        
        return gtPrincipal.getGtAula().filtrarAulasProfessorLista(idProfessor);
    }
    
    public List listarAulasTurmaPorTurno(int ano, int semestre, int idTurma, int turno){
        if(gtPrincipal.getGtAula().getListaAulasAnoSemestre() == null)
            atualizarAulasAnoSemestre(ano, semestre);
        
        return gtPrincipal.getGtAula().filtrarAulasTurmaTurnoLista(idTurma, turno);
    }
    
    public void atualizarAulasAnoSemestre(int ano, int semestre){
        gtPrincipal.getGtAula().preencherListaAulasAnoSemestre(ano, semestre);
    }
    
    public List preencherListaAulasVazias(List[] aulas){
    
        Aula aula;
        Aula[] vetorAulas;
        
        List lista = new ArrayList<>();
        
        for(int dia = 0; dia < Constantes.LINHA; dia++){
            
            vetorAulas = preencherVetorAulas(aulas[dia]);
            
            for(int numero = 0; numero < 16; numero++){
                
                aula = vetorAulas[numero];
                
                if(aula == null){
                    aula = gerarAulaVazia(dia, numero);
                }
                lista.add(aula);
            }
        }
        return lista;
    }
    
    public Aula[] preencherVetorAulas(List<Aula> lista){
    
        Aula[] vetorAulas = new Aula[16];
        
        for(Aula aula : lista){
            vetorAulas[aula.getNumero()] = aula;
        }
        return vetorAulas;
    }    
    
    public Aula gerarAulaVazia(int dia, int numero){
        
        Aula aula = new Aula();
        
        aula.setDia(dia);
        aula.setNumero(numero);
        aula.setAlocacao(gerarAlocacaoVazia());
        aula.setOferta(gerarOfertaVazia());
        return aula;
    }
    
    public Oferta gerarOfertaVazia(){
        
        Oferta oferta = new Oferta();
        oferta.setTurma(gerarTurmaVazia());
        return oferta;
    }
    
    public Turma gerarTurmaVazia(){
        
        Turma turma = new Turma();
        turma.setNome("");
        
        return turma;
    }
    
    public Alocacao gerarAlocacaoVazia(){
        
        Alocacao alocacao = new Alocacao();
        alocacao.setProfessor1(gerarProfessorVazio());
        alocacao.setProfessor2(null);
        alocacao.setDisciplina(gerarDisciplinaVazia());
        
        return alocacao;
    }
    
    public Disciplina gerarDisciplinaVazia(){
        
        Disciplina disciplina = new Disciplina();
        disciplina.setNome("");
        disciplina.setSigla("");
        
        return disciplina;
    }
    
    public Professor gerarProfessorVazio(){
        Professor professor = new Professor();
        professor.setNome("");
        return professor;
    }
}
