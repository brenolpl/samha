package com.samha.application.oferta;

import com.samha.commons.UseCase;
import com.samha.domain.Oferta;
import com.samha.persistence.IOfertaRepository;

import javax.inject.Inject;

public class MudarVisibilidadeOferta extends UseCase<Oferta> {

    private Integer ofertaId;

    @Inject
    public MudarVisibilidadeOferta(Integer ofertaId) {
        this.ofertaId = ofertaId;
    }

    @Inject
    private IOfertaRepository ofertaRepository;

    @Override
    protected Oferta execute() throws Exception {
        Oferta oferta = ofertaRepository.findById(ofertaId).get();
        oferta.setPublica(!oferta.getPublica());
        return ofertaRepository.save(oferta);
    }
}
