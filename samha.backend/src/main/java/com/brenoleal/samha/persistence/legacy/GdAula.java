package com.brenoleal.samha.persistence.legacy;

import java.util.List;

import com.brenoleal.samha.core.Aula;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

public class GdAula extends GdGenerico{
    
    private GdPrincipal gdPrincipal;

    public GdAula(GdPrincipal gdPrincipal) {
        this.gdPrincipal = gdPrincipal;
    }
    
    public void salvarAulasOferta(Aula[][] matriz, List listaAulasRemovidas){
        
        try {
            sessao = criarSessao();
            sessao.beginTransaction();
            
            Aula aulaRemovida;
            for(int i = 0; i < listaAulasRemovidas.size(); i++){
                aulaRemovida = (Aula) listaAulasRemovidas.get(i);
                sessao.delete(aulaRemovida);
            }
            
            listaAulasRemovidas.clear();
            
            int linhas = matriz.length;
            int colunas = matriz[0].length;
            
            for(int linha = 0; linha < linhas; linha++){
                for(int coluna = 0; coluna < colunas; coluna++){
                    if(matriz[linha][coluna] != null)
                        sessao.merge(matriz[linha][coluna]);  
                } 
            }
            
            sessao.getTransaction().commit();
            sessao.close();

        } catch (HibernateException e) {
            sessao.getTransaction().rollback();
            sessao.close();
            throw e;
        }     
    }
    
    public List filtrarAulasAnoSemestre(int ano, int semestre) {
        Criteria crit = criarSessao().createCriteria(Aula.class);
        sessao.beginTransaction();
        crit.createAlias("oferta", "o");
        crit.add( Restrictions.eq("o.ano", ano));
        crit.add( Restrictions.eq("o.semestre", semestre));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarAulasOferta(int idOferta) {
        Criteria crit = criarSessao().createCriteria(Aula.class);
        sessao.beginTransaction();
        crit.createAlias("oferta", "o");
        crit.add( Restrictions.eq("o.id", idOferta));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }  
}