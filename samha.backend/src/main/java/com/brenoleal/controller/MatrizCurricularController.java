package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.controller.common.BaseController;
import com.brenoleal.domain.MatrizCurricular;
import com.brenoleal.domain.log.MatrizCurricularAud;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/matrizCurricular")
public class MatrizCurricularController extends BaseController<MatrizCurricular, MatrizCurricularAud, Integer> {
    public MatrizCurricularController(UseCaseFacade facade) {
        super(MatrizCurricular.class, MatrizCurricularAud.class, facade);
    }
}
