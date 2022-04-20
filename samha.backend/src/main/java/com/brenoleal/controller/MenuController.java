package com.brenoleal.controller;

import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.domain.ListarMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/menu")
public class MenuController {

    private final UseCaseFacade facade;

    @PostMapping("list")
    public List<Integer> list(HttpServletRequest request){
        return this.facade.execute(new ListarMenu(request));
    }
}