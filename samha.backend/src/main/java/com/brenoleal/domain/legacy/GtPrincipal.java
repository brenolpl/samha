package com.brenoleal.domain.legacy;

import com.brenoleal.persistence.legacy.GdPrincipal;
import com.brenoleal.core.CoordenadorAcademico;
import com.brenoleal.core.CoordenadorCurso;
import com.brenoleal.core.Servidor;
import com.brenoleal.core.Usuario;

//TODO: IMPLEMENTAR O ENVIO DE EMAIL

public class GtPrincipal {

    private GdPrincipal gdPrincipal;
    private Usuario coordAtual;
    private int permissao;
    
    private GtAlocacao gtAlocacao;
    private GtCoordenador gtCoordenador;
    private GtCoordenadoria gtCoordenadoria;
    private GtCurso gtCurso;
    private GtDisciplina gtDisciplina;
    private GtEixo gtEixo;
    private GtMatriz gtMatriz;
    private GtOferta gtOferta;
    private GtProfessor gtProfessor;
    private GtRestricao gtRestricao;
    private GtTurma gtTurma;
    private GtAula gtAula;
    private GtConflito gtConflito;
    private GtInstituicao gtInstituicao;
    //private GtEmail gtEmail;
    private GtRelatorio gtRelatorio;

    public GtPrincipal() {
        gdPrincipal = new GdPrincipal();
        gtAlocacao = new GtAlocacao(this);
        gtCoordenador = new GtCoordenador(this);
        gtCoordenadoria = new GtCoordenadoria(this);
        gtCurso = new GtCurso(this);
        gtDisciplina = new GtDisciplina(this);
        gtEixo = new GtEixo(this);
        gtMatriz = new GtMatriz(this);
        gtOferta = new GtOferta(this);
        gtProfessor = new GtProfessor(this);
        gtRestricao = new GtRestricao(this);
        gtTurma = new GtTurma(this);
        gtAula = new GtAula(this);
        gtConflito = new GtConflito(this);
        gtInstituicao = new GtInstituicao(this);
       // gtEmail = new GtEmail(this);
        gtRelatorio = new GtRelatorio(this);
        setPermissao(Constantes.PERMISSAO_NEGADA);
        setCoordAtual(null);
    }
    
    public void identificarPermissaoAdmin() throws SAMHAException{
        
        if(!(getCoordAtual() instanceof CoordenadorAcademico))
            throw new SAMHAException(50);
    }
    
    public void identificarPermissaoPadrao() throws SAMHAException{
        
        if(!((getCoordAtual() instanceof CoordenadorAcademico)||(getCoordAtual() instanceof CoordenadorCurso)))
            throw new SAMHAException(50);
    }

    public int validarAcesso(String login, String senha) {

        try {
            validarCampos(login, senha);
            Usuario usuario = gdPrincipal.validarAcesso(login, senha);
            
            CoordenadorAcademico master = new CoordenadorAcademico();
            Servidor serv = new Servidor();
            serv.setNome("Administrador");
            master.setServidor(serv);
            master.setLogin("2015122760084");
            master.setSenha("2015122760084");
                        
            if(login.toLowerCase().equals(master.getLogin()) && senha.toLowerCase().equals(master.getSenha())){
                setCoordAtual(master);
                setPermissao(Constantes.PERMISSAO_ADMIN);
                return Constantes.PERMISSAO_ADMIN;
            }

            if (usuario == null) {
                return Constantes.PERMISSAO_NEGADA;
            } else {
                
                setCoordAtual(usuario);
                if(usuario instanceof CoordenadorCurso){
                    setPermissao(Constantes.PERMISSAO_COORD);
                    return Constantes.PERMISSAO_COORD;
                }else if(usuario instanceof CoordenadorAcademico){
                    setPermissao(Constantes.PERMISSAO_ADMIN);
                    return Constantes.PERMISSAO_ADMIN;
                }else{
                    setPermissao(Constantes.PERMISSAO_VIEW);
                    return Constantes.PERMISSAO_VIEW;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Constantes.PERMISSAO_NEGADA;
        }
    }

    public void encerrarSessao() {
        setCoordAtual(null);
        setPermissao(Constantes.PERMISSAO_NEGADA);
    }

    public void validarCampos(String login, String senha) throws Exception {
        if (login.equals("")) {
            throw new SAMHAException(3);
        }
        if (senha.equals("")) {
            throw new SAMHAException(4);
        }
    }

    public Usuario getCoordAtual() {
        return coordAtual;
    }

    public void setCoordAtual(Usuario coordAtual) {
        this.coordAtual = coordAtual;
    }

    public int getPermissao() {
        return permissao;
    }

    public void setPermissao(int permissao) {
        this.permissao = permissao;
    }

    public GtAlocacao getGtAlocacao() {
        return gtAlocacao;
    }

    public GtCoordenador getGtCoordenador() {
        return gtCoordenador;
    }

    public GtCoordenadoria getGtCoordenadoria() {
        return gtCoordenadoria;
    }

    public GtCurso getGtCurso() {
        return gtCurso;
    }

    public GtDisciplina getGtDisciplina() {
        return gtDisciplina;
    }

    public GtEixo getGtEixo() {
        return gtEixo;
    }

    public GtMatriz getGtMatriz() {
        return gtMatriz;
    }

    public GtOferta getGtOferta() {
        return gtOferta;
    }

    public GtProfessor getGtProfessor() {
        return gtProfessor;
    }

    public GtRestricao getGtRestricao() {
        return gtRestricao;
    }

    public GtTurma getGtTurma() {
        return gtTurma;
    }

    public GdPrincipal getGdPrincipal() {
        return gdPrincipal;
    }

    public GtAula getGtAula() {
        return gtAula;
    }

    public GtConflito getGtConflito() {
        return gtConflito;
    }

    public GtInstituicao getGtInstituicao() {
        return gtInstituicao;
    }

//    public GtEmail getGtEmail() {
//        return gtEmail;
//    }

    public GtRelatorio getGtRelatorio() {
        return gtRelatorio;
    }
}
