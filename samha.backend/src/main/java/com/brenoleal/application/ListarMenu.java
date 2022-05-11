package com.brenoleal.application;

import com.brenoleal.commons.UseCase;
import com.brenoleal.domain.Menu;
import com.brenoleal.domain.Menu_;
import com.brenoleal.domain.Papel;
import com.brenoleal.domain.Papel_;
import com.brenoleal.persistence.generics.IGenericRepository;
import com.brenoleal.util.JWTUtil;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class ListarMenu extends UseCase<List<Menu>> {

    private final HttpServletRequest request;

    public ListarMenu(HttpServletRequest request) {
        this.request = request;
    }

    @Inject
    private IGenericRepository genericRepository;

    @Override
    protected List<Menu> execute() throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String access_token = authorizationHeader.substring("Bearer ".length());
        List<String> nomePapeis = JWTUtil.getPapeisFromToken(access_token);
        List<Papel> papeis = this.genericRepository.find(Papel.class, q -> q.where(
                ((CriteriaBuilder.In) q.in(q.get(Papel_.nome))).value(nomePapeis)
        ));
        List<Menu> menus = new ArrayList<>();
        for(var papel : papeis){
            menus.addAll(
                    this.genericRepository.find(Menu.class, q -> {
                        CriteriaBuilder builder = q.getCriteriaBuilder();
                        CriteriaQuery query = q.getCriteriaQuery();
                        Root root = q.getRoot();

                        query.where(
                                builder.equal(root.join(Menu_.papeis).get(Papel_.id), papel.getId())
                        );

                        return q;
                    }
                 )
            );
        }
        return menus;
    }
}
