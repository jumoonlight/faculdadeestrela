package com.ifsp.anajuliaferreira.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class TelaInicialController{
    @GetMapping("/telainicial")
    public String telaInicial(){
        return "telaInicial";
    }
     @GetMapping("/telainicialAluno")
    public String telaInicialAluno(){
        return "telaInicialAluno";
    }
    @GetMapping ("/telainicialProfessor")
    public String telaInicialProfessor(){
        return "telaInicialProfessor";
    }
    @GetMapping("/telainicialBibliotecario")
    public String telaInicialBibliotecario(){
        return "telaInicialBibliotecario";
    }
}