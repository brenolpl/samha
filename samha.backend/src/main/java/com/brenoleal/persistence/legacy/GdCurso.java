package com.brenoleal.persistence.legacy;

import java.util.List;

import com.brenoleal.core.Curso;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class GdCurso extends GdGenerico{
    
    private GdPrincipal gdPrincipal;
    
    public GdCurso(GdPrincipal gdPrincipal){
        this.gdPrincipal = gdPrincipal;
    }
    
    public List buscar(String coluna, String texto) {
        Criteria crit = criarSessao().createCriteria(Curso.class);
        sessao.beginTransaction();
        crit.add( Restrictions.like(coluna, texto, MatchMode.ANYWHERE) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List buscarPorNivel(String coluna, String texto) {
        Criteria crit = criarSessao().createCriteria(Curso.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq(coluna, texto) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorEixo(int idEixo) {
        Criteria crit = criarSessao().createCriteria(Curso.class);
        sessao.beginTransaction();
        crit.setFetchMode("coordenadoria", FetchMode.JOIN);
        crit.createAlias("coordenadoria", "c");
        crit.createAlias("c.eixo", "e");
        crit.add( Restrictions.eq("e.id", idEixo) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public Curso filtrarCursoUnico(String colunaFiltro, int id) {
        Criteria crit = criarSessao().createCriteria(Curso.class);
        sessao.beginTransaction();
        colunaFiltro = colunaFiltro.toLowerCase();
        crit.add( Restrictions.eq(colunaFiltro, id));
        crit.setMaxResults(1);
        Curso curso = (Curso) crit.uniqueResult();
        sessao.getTransaction().commit();
        sessao.close();
        return curso;
    }
    
    public Curso cursoCoordenadoriaEager(int id) {
        Criteria crit = criarSessao().createCriteria(Curso.class);
        sessao.beginTransaction();
        crit.setFetchMode("coordenadoria", FetchMode.JOIN);
        crit.add( Restrictions.eq("id", id));
        crit.setMaxResults(1);
        Curso curso = (Curso) crit.uniqueResult();
        sessao.getTransaction().commit();
        sessao.close();
        return curso;
    }
}
