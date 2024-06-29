package com.development.login_crud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investimentos")
public class InvestimentosController {


    @GetMapping
    public void getInvestimentos() {
        System.out.println("busquei os investimentos");
    }
}
