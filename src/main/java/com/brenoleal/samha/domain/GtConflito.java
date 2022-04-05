package com.brenoleal.samha.domain;

import com.brenoleal.samha.core.Aula;
import com.brenoleal.samha.core.Professor;
import com.brenoleal.samha.core.RestricaoProfessor;

import java.util.ArrayList;
import java.util.List;

public class GtConflito {
    
    private GtPrincipal gtPrincipal;
    private List<RestricaoProfessor> listaRestricoes;
    
    public GtConflito(GtPrincipal gt) {
        this.gtPrincipal = gt;
    }
      
    public List validarAula(Aula aula){
 
        Professor professor = aula.getAlocacao().getProfessor1();

        List mensagens = identificarConflitos(aula, professor.getId(), 1);

        if(aula.getAlocacao().getDisciplina().getTipo().equals(Constantes.ESPECIAL)){
            Professor professor2 = aula.getAlocacao().getProfessor2();
            List msg = identificarConflitos(aula, professor2.getId(), 2);
            mensagens.addAll(msg);
        }
        
        return mensagens;

    }
    
    public List identificarConflitos(Aula aula, int idProfessor, int numeroProfessor){
        
        List mensagens = new ArrayList<>();
        
        String mensagemConflitoTurma = identificarConflitoTurma(aula, idProfessor);
        if(mensagemConflitoTurma != null) mensagens.add(mensagemConflitoTurma);
        
        List mensagemConflitoInstituicao = gtPrincipal.getGtInstituicao().identificarConflitoRestricaoInstituicao(aula, numeroProfessor);
        mensagens.addAll(mensagemConflitoInstituicao);
        
        String mensagemConflitoRestricaoProfessor = identificarConflitoRestricaoProfessor(aula, idProfessor);
        if(mensagemConflitoRestricaoProfessor != null) mensagens.add(mensagemConflitoRestricaoProfessor);
        
        return mensagens;
    }
    
    public String identificarConflitoTurma(Aula aula, int idProfessor){
        
        List listaAulas = gtPrincipal.getGtAula().filtrarAulasProfessorNumeroDiaLista(idProfessor, aula.getNumero(), aula.getDia());
        listaAulas = removerAulaListaBanco(listaAulas, aula);
        
        if(listaAulas.isEmpty())
            return null;
        else{
            String nomeProfessor = obterNomeProfessor(aula, idProfessor);
            return montarMensagemConflitoTurma(listaAulas, nomeProfessor);
        }    
    }
 
    public List removerAulaListaBanco(List listaAulasBanco, Aula aula){
        
        Aula aulaLista;
        
        for(int i = 0; i < listaAulasBanco.size(); i++){
            
            aulaLista = (Aula) listaAulasBanco.get(i);
            
            if(aulaLista.getId() == aula.getId()){
                listaAulasBanco.remove(i);    
            }else if(aula.getOferta().getTurma().getId() == aulaLista.getOferta().getTurma().getId()){
                if(aula.getNumero() == aulaLista.getNumero()){
                    listaAulasBanco.remove(i);
                }
            }    
        }
        
        return listaAulasBanco;
    }
    
    public String montarMensagemConflitoTurma(List aulas, String nomeProfessor){
        
        Aula aulaLista;
        String novaMensagem = nomeProfessor + " está em outra(s) turma(s) neste horário: ";

        for(int i = 0; i < aulas.size(); i++){
            aulaLista = (Aula) aulas.get(i);
            novaMensagem = novaMensagem + aulaLista.getOferta().getTurma().getNome() + " - " + aulaLista.getAlocacao().getDisciplina().getNome() + ". ";           
        }     
        return novaMensagem;
    }
    
    public void preencherListaRestricoesProfessor(){
        
        if(listaRestricoes != null)
            listaRestricoes.clear();
        
        listaRestricoes = gtPrincipal.getGdPrincipal().getGdRestricao().consultar(RestricaoProfessor.class);
    }
    
    public List filtrarRestricoesProfessorDiaTurno(int idProfessor, int dia, String turno){
        
        List restricoes = new ArrayList<>();
        
        for(RestricaoProfessor restricao : listaRestricoes){
            if(restricao.getProfessor().getId() == idProfessor && restricao.getDia() == dia && restricao.getTurno().equals(turno)){
                restricoes.add(restricao);
            }
        }
        return restricoes;
    }
        
    public String identificarConflitoRestricaoProfessor(Aula aula, int idProfessor){
        
        String turno = obterStringTurno(aula.getTurno());

        List lista = filtrarRestricoesProfessorDiaTurno(idProfessor, aula.getDia(), turno);
        
        if(lista.isEmpty())
            return null;
        else
            return montarMensagemConflitoRestricaoProfessor(lista, aula);
    }
    
    public String montarMensagemConflitoRestricaoProfessor(List lista, Aula aula){
        
        boolean resposta; 
        RestricaoProfessor restricao;
                
        for(int i = 0; i < lista.size(); i++){

            restricao = (RestricaoProfessor) lista.get(i);
            resposta = identificarNumeroAulaConflitante(restricao, aula.getNumero());

            if(resposta) 
                return restricao.getProfessor().obterNomeAbreviado() + " possui uma restrição neste horário: " + restricao.getNome().toUpperCase();    
        }
        return null;
    }
    
    public boolean identificarNumeroAulaConflitante(RestricaoProfessor restricao, int numero){
            
        String t = restricao.getTurno();
        int turno = gtPrincipal.getGtAula().obterNumeroTurno(t);
        
        switch(numero - turno){

            case 0: return restricao.isAula1();
            case 1: return restricao.isAula2();
            case 2: return restricao.isAula3();
            case 3: return restricao.isAula4();
            case 4: return restricao.isAula5();
            case 5: return restricao.isAula6();
        }
        return false;
    }
    
    public String obterStringTurno(int turno){
        
        switch(turno){ 
            case 0: return Constantes.MATUTINO;
            case 6: return Constantes.VESPERTINO;
            default: return Constantes.NOTURNO;    
        }
    }
    
    public String obterNomeProfessor(Aula aula, int idProfessor){
        
        if(aula.getAlocacao().getProfessor1().getId() == idProfessor)
            return aula.getAlocacao().getProfessor1().obterNomeAbreviado();
        else
            return aula.getAlocacao().getProfessor2().obterNomeAbreviado();
    }
    
    public boolean validarQuantidadeAulasDisciplina(Aula aula){
        
        if(aula != null){
            
            int cont  = 0;
            int qtAulas = aula.getAlocacao().getDisciplina().getQtAulas();
            int idDisciplina = aula.getAlocacao().getDisciplina().getId();

            Aula aulaAux;

            for(int linha = 0; linha < Constantes.LINHA; linha++){

                for(int coluna = 0; coluna < Constantes.COLUNA; coluna++){

                    aulaAux = gtPrincipal.getGtAula().getAulaMatriz(linha, coluna);

                    if(aulaAux != null){
                        if(aulaAux.getAlocacao().getDisciplina().getId() == idDisciplina){
                            cont++;
                        }
                    }
                }
            }
            return cont == qtAulas;
        }
        return true;
    }
}
