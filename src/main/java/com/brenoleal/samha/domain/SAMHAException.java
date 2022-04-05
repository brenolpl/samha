package com.brenoleal.samha.domain;

public class SAMHAException extends Exception {

    int codErro;
    String [] msgsErro;

    public SAMHAException(int cod) {
        
        super("Erro genérico.");
        msgsErro = new String[500];
        this.codErro = cod;
        
        msgsErro[0] = "Usuário inválido";

        msgsErro[1] = "Campo NOME inválido";
        msgsErro[2] = "Campo MATRICULA inválido";
        msgsErro[3] = "Campo LOGIN inválido";
        msgsErro[4] = "Campo SENHA inválido";
        msgsErro[5] = "Campo CARGA HORÁRIA inválido"; 
        msgsErro[6] = "Campo DESCRIÇÃO inválido";
        msgsErro[7] = "Professor não selecionado";
        msgsErro[8] = "Eixo não selecionado";
        
        msgsErro[9] = "Campo NÍVEL inválido";
        msgsErro[10] = "Campo PERÍODOS inválido";
        msgsErro[11] = "Coordenadoria não selecionada";
        
        //Disciplina
        msgsErro[12] = "Curso não selecionado";
        msgsErro[13] = "Matriz Curricular não selecionada";
        msgsErro[14] = "Campo Quantidade de Aulas inválido";
        msgsErro[15] = "Campo TURNO inválido";
        msgsErro[16] = "Campo TIPO inválido";
        msgsErro[25] = "Campo SIGLA inválido";
        
        //Alocacao
        msgsErro[17] = "Disciplina não selecionada";
        msgsErro[18] = "Professor não selecionado";
        msgsErro[19] = "Quantidade de Professores inválida, selecione 2 professores";
        msgsErro[20] = "Quantidade de Professores inválida, selecione apenas 1 professor";
        msgsErro[21] = "2º Professor não selecionado";
        msgsErro[22] = "Professor possui alocações associadas";
        
        msgsErro[50] = "Usuário não possui permissão para este tipo de operação.";
    }
    
    @Override
    public String getMessage() {        
        return msgsErro[codErro];
    }  
}
