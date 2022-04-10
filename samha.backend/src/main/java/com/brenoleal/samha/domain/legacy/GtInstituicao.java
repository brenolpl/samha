package com.brenoleal.samha.domain.legacy;

import com.brenoleal.samha.core.Aula;
import com.brenoleal.samha.core.Oferta;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GtInstituicao {
    
    private GtPrincipal gtPrincipal;
    private List[] vetorAulas;
    private boolean validacaoGeral = false;
    
    public GtInstituicao(GtPrincipal gt){
        this.gtPrincipal = gt;  
    }
    
    public List identificarConflitoRestricaoInstituicao(Aula aula, int numeroProfessor){
        
        int idProfessor = aula.getAlocacao().getProfessor1().getId();
        int dia = aula.getDia();
        List mensagens = new ArrayList<>();
        
        if(numeroProfessor != 1 && aula.getAlocacao().getProfessor2() != null)
            idProfessor = aula.getAlocacao().getProfessor2().getId();
        
        preencherVetorAulas(aula, idProfessor);
        
        //VERIFICAR SE O DIA DA AULA VINDA DO BANCO FOI ALTERADO NA MATRIZ
        
        if(aula.getId() != 0){
            Aula aulaAux = identificarAulaMatriz(aula);
            if(aulaAux != null)
                dia = aulaAux.getDia();
        }
         
        String tempoMaximo = identificarConflitoTempoMaximo(dia, idProfessor, aula);
        if(tempoMaximo != null) mensagens.add(tempoMaximo);

        String intervalo = identificarConflitoIntervaloMinimo(dia, idProfessor, aula);
        if(intervalo != null) mensagens.add(intervalo);
        
        return mensagens;
    }
    
    public void preencherVetorAulas(Aula aula, int idProfessor){

        vetorAulas = new List[Constantes.LINHA];
        
        for(int linha = 0; linha < Constantes.LINHA; linha++){
            
            vetorAulas[linha] = gtPrincipal.getGtAula().filtrarAulasProfessorDiaLista(linha, idProfessor);
            
            if(!validacaoGeral){
                identificarAlteracaoAula(linha, aula);
                identificarNovaAula(linha, aula, idProfessor);
            }    
            Collections.sort(vetorAulas[linha]);
        }
    }
    
    public void identificarAlteracaoAula(int linha, Aula aula){
        
        List listaAulasBanco = vetorAulas[linha];
        Aula aulaBanco, aulaMatriz;
        
        for(int indice = 0; indice < listaAulasBanco.size(); indice++){
            
            aulaBanco = (Aula) listaAulasBanco.get(indice);
            
            if((aulaBanco.getOferta().getTurma().getId() == aula.getOferta().getTurma().getId())){ // VERIFICA SE SÃO DA MESMA TURMA

                aulaMatriz = identificarAulaMatriz(aulaBanco);
                
                if(aulaMatriz != null)
                    vetorAulas[linha].set(indice, aulaMatriz);
                else
                    vetorAulas[linha].remove(indice); 
            }
        }
    }
    
    public Aula identificarAulaMatriz(Aula aulaBanco){
        
        // VERIFICAR SE A AULA VINDA DO BANCO AINDA EXISTE NA MATRIZ NO DIA ESPECIFICADO
        
        Aula aulaMatriz;
        
        for(int linha = 0; linha < Constantes.LINHA; linha++){
            for(int coluna = 0; coluna < Constantes.COLUNA; coluna++){
                aulaMatriz = gtPrincipal.getGtAula().getAulaMatriz(linha, coluna);
                if(aulaMatriz != null){
                    if(aulaMatriz.getId() == aulaBanco.getId()){
                        if(aulaBanco.getDia() == aulaMatriz.getDia())
                            return aulaMatriz;
                        else
                            return null;
                    }
                }    
            }
        }
        return null;
    }
    
    public void identificarNovaAula(int linha, Aula aula, int idProfessor){
        
        // INSERIR AULAS QUE AINDA NÃO FORAM SALVAS NO BANCO NA LISTA DE AULAS OU AULAS QUE FORAM MUDADAS DE DIA

        Aula aulaMatriz;

        for(int coluna = 0; coluna < Constantes.COLUNA; coluna++){
            
            aulaMatriz = gtPrincipal.getGtAula().getAulaMatriz(linha, coluna);
            
            if(aulaMatriz != null){
                
                if(aulaMatriz.getAlocacao().getProfessor1().getId() == idProfessor 
                    || (aulaMatriz.getAlocacao().getProfessor2() != null && aulaMatriz.getAlocacao().getProfessor2().getId() == idProfessor)){
                
                    if(aulaMatriz.getId() == 0)
                        vetorAulas[linha].add(aulaMatriz);
                    else if(aula.getDia() == linha && !existeNoDia(linha, aulaMatriz))
                        vetorAulas[linha].add(aulaMatriz);
                }   
            }
        }   
    }
    
    public boolean existeNoDia(int dia, Aula aula){
        
        Aula aulaVetor;
        if(aula.getId() != 0){
            for(int indice = 0; indice < vetorAulas[dia].size(); indice++){
                aulaVetor = (Aula) vetorAulas[dia].get(indice);
                if(aulaVetor.getId() == aula.getId())
                    return true;
            }
        }   
        return false;
    }
    
    public String identificarConflitoTempoMaximo(int dia, int idProfessor, Aula aula){
        
        Aula primeiraAula, ultimaAula;
        int tempo;
        
        Oferta oferta = gtPrincipal.getGtOferta().getOfertaSelecionada();
        List aulas = vetorAulas[dia];

        if(aulas.size() > 1){

            primeiraAula = (Aula) aulas.get(0);
            ultimaAula = (Aula) aulas.get(aulas.size() - 1);
            
            if(aula.getId() != ultimaAula.getId()){
                primeiraAula = aula;
            }

            tempo = obterQuantidadeHoras(primeiraAula, ultimaAula, Constantes.TEMPO_MAXIMO);
            
            if(tempo > oferta.getTempoMaximoTrabalho()){
                String nomeProfessor = gtPrincipal.getGtConflito().obterNomeProfessor(aula, idProfessor);
                return montarMensagemTempoMaximo(ultimaAula, primeiraAula, tempo, nomeProfessor);
            }
        }
        
        return null;
    }
    
    public String montarMensagemTempoMaximo(Aula ultima, Aula primeira, int tempo, String nomeProfessor){
        
        String mensagem = "0 " + nomeProfessor + " possui um tempo máximo de trabalho superior ao permitido:\n" 
                + primeira.getOferta().getTurma().getNome() + ": Aula " + (primeira.getNumero() + 1) + " | " 
                + ultima.getOferta().getTurma().getNome() + ": Aula " + (ultima.getNumero() + 1) + " - " +  tempo + " horas.";
        
        return mensagem;
    }    
    
    public String identificarConflitoIntervaloMinimo(int dia, int idProfessor, Aula aula){
        
        Oferta oferta = gtPrincipal.getGtOferta().getOfertaSelecionada();
        
        if(dia != 0)
            return alterarDiaIntervaloMinimo(vetorAulas[dia - 1], vetorAulas[dia], oferta, aula, idProfessor);            
        else
            return alterarDiaIntervaloMinimo(vetorAulas[dia], vetorAulas[dia + 1], oferta, aula, idProfessor);
    }
    
    public String alterarDiaIntervaloMinimo(List aulasDiaAnterior, List aulasDiaAtual, Oferta oferta, Aula aula, int idProfessor){
        
        Aula primeiraAula, ultimaAula;
        int tempo;
        
        if(!aulasDiaAnterior.isEmpty() && !aulasDiaAtual.isEmpty()){
                      
            primeiraAula = (Aula) aulasDiaAtual.get(0);
            ultimaAula = (Aula) aulasDiaAnterior.get(aulasDiaAnterior.size() - 1);
            
            if(aula.getDia() != 0){
                primeiraAula = aula;
            }

            tempo = obterQuantidadeHoras(primeiraAula, ultimaAula, Constantes.INTERVALO_MINIMO);

            if(tempo < oferta.getIntervaloMinimo()){
                String nomeProfessor = gtPrincipal.getGtConflito().obterNomeProfessor(aula, idProfessor);
                return montarMensagemIntervaloMinimo(ultimaAula, primeiraAula, tempo, nomeProfessor);
            }    
        }
        return null;
    }
    
    public String montarMensagemIntervaloMinimo(Aula ultima, Aula primeira, int tempo, String nomeProfessor){
    
        String diaAnterior = obterStringDia(ultima.getDia());
        String diaAtual = obterStringDia(primeira.getDia());
        
        String horarioFinal = obterHorarioFinal(ultima);
        String horarioInicial = obterHorarioInicial(primeira);
        
        String mensagem = "1 " + nomeProfessor + " possui um intervalo mínimo de descanso inferior ao permitido: " + tempo + " horas.\n"
                    + ultima.getOferta().getTurma().getNome() + ": " + diaAnterior + " - " + horarioFinal + ".\n"
                    + primeira.getOferta().getTurma().getNome() + ": "+ diaAtual + " - " + horarioInicial + ".";
   
        return mensagem;
    }    
    
    public String obterHorarioInicial(Aula aula){
        
        String horarioInicial = Horarios.horarioInicial(aula.getNumero());
        return horarioInicial;
    }
    
    public String obterHorarioFinal(Aula aula){
        
        String horarioFinal = Horarios.horarioFinal(aula.getNumero());  
        return horarioFinal;
    }
    
    public int obterQuantidadeHoras(Aula primeira, Aula ultima, int flag){
        
        String horarioInicial = obterHorarioInicial(primeira);
        String horarioFinal = obterHorarioFinal(ultima);
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);
        
        LocalTime inicio = LocalTime.parse(horarioInicial, formato);
        LocalTime fim = LocalTime.parse(horarioFinal, formato);

        int qtHoras = calcularDiferencaHoras(inicio, fim, formato);
        
        if(flag == Constantes.INTERVALO_MINIMO)
            return modificarQuantidadeHorasIntervaloMinimo(fim, inicio, qtHoras);
        else    
            return qtHoras;
    }
    
    public int calcularDiferencaHoras(LocalTime inicio, LocalTime fim, DateTimeFormatter formato){
        
        LocalTime diferenca = fim.minusHours(inicio.getHour()).minusMinutes(inicio.getMinute());
        String dif = diferenca.format(formato);

        String[] horas = dif.split(":");
        
        return Integer.parseInt(horas[0]);
    }
     
    public int modificarQuantidadeHorasIntervaloMinimo(LocalTime fim, LocalTime inicio, int qtHoras){
            
        if(fim.getHour() == inicio.getHour()){

            if(fim.getMinute() < inicio.getMinute()){
                return qtHoras;
            }

        }else if(fim.getHour() < inicio.getHour()){
            return qtHoras; 
        }

        return 24 - qtHoras; 
    }
    
    public String obterStringDia(int dia){

        switch(dia){
            
            case 0:
                return Constantes.SEGUNDA;
            case 1:
                return Constantes.TERCA;
            case 2:
                return Constantes.QUARTA;
            case 3:
                return Constantes.QUINTA;    
            default:
                return Constantes.SEXTA; 
        }  
    }

    public void setValidacaoGeral(boolean validacaoGeral) {
        this.validacaoGeral = validacaoGeral;
    }
}
