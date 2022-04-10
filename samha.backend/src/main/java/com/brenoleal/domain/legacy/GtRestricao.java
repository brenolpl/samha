package com.brenoleal.domain.legacy;


import com.brenoleal.core.Professor;
import com.brenoleal.core.RestricaoProfessor;

import java.sql.SQLException;
import java.util.List;

public class GtRestricao {

    private GtPrincipal gtPrincipal;

    public GtRestricao(GtPrincipal gt) {
        gtPrincipal = gt;
    }

    public RestricaoProfessor cadastrar(String nome, String turno, int dia, String descricao, String prioridade,
                                        boolean aula1, boolean aula2, boolean aula3, boolean aula4, boolean aula5, boolean aula6, Professor professor) {

        try {

            gtPrincipal.identificarPermissaoPadrao();
            validarCampos(nome, descricao);
            RestricaoProfessor restricao = new RestricaoProfessor();
            restricao.setAula1(aula1);
            restricao.setAula2(aula2);
            restricao.setAula3(aula3);
            restricao.setAula4(aula4);
            restricao.setAula5(aula5);
            restricao.setAula6(aula6);

            restricao.setDescricao(descricao);
            restricao.setDia(dia);
            restricao.setNome(nome);
            restricao.setPrioridade(prioridade.toUpperCase());
            restricao.setTurno(turno.toUpperCase());
            restricao.setProfessor(professor);
            gtPrincipal.getGdPrincipal().getGdRestricao().cadastrar(restricao);
            return restricao;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<RestricaoProfessor> filtrarPorProfessor(int id) {
        return gtPrincipal.getGdPrincipal().getGdRestricao().filtrarPorProfessor(id);
    }

    public String excluir(RestricaoProfessor restricao) {
        try {
            gtPrincipal.identificarPermissaoPadrao();
            gtPrincipal.getGdPrincipal().getGdRestricao().excluir(restricao);
            return Constantes.EXCLUIDO;
        } catch (SAMHAException | ClassNotFoundException | SQLException ex) {
            return ex.getMessage();
        }
    }
    
    public String excluirTodasRestricoes(){
        try {
            gtPrincipal.identificarPermissaoPadrao();
            gtPrincipal.getGdPrincipal().getGdRestricao().excluirTodasRestricoes();
            return Constantes.EXCLUIDO;
        } catch (SAMHAException ex) {
            return ex.getMessage();
        }
    }

    public void validarCampos(String nome, String descricao) throws Exception {
        if (nome.equals("")) {
            throw new SAMHAException(1);
        }
        if (descricao.equals("")) {
            throw new SAMHAException(6);
        }
    }
}
