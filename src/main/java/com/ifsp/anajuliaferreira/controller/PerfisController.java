package com.ifsp.anajuliaferreira.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.anajuliaferreira.model.Admin;
import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.model.Professor;
import com.ifsp.anajuliaferreira.repository.AdminRepository;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.ProfessorRepository;

@Controller
public class PerfisController {
    @Autowired 
    AdminRepository adminRepository;
    @Autowired 
    AlunoRepository alunoRepository;
    @Autowired 
    ProfessorRepository professorRepository;
    @GetMapping("/perfilAdmin")
    public String perfilAdmin(Principal principal, Model model) {
        Admin admin = adminRepository.findByEmail(principal.getName());
        model.addAttribute("admin", admin);
        return "perfilAdmin";
    }
    @GetMapping("/perfilAluno")
    public String perfilAluno(Principal principal, Model model) {
        Aluno aluno = alunoRepository.findByEmail(principal.getName());
        model.addAttribute("aluno", aluno);
        return "perfilAluno";
    }
    @GetMapping ("/perfilProfessor")
    public String perfilProfessor(Principal principal, Model model) {
        Professor professor = professorRepository.findByEmail(principal.getName());
        model.addAttribute("professor", professor);
        return "perfilProfessor";
    }
    @PostMapping("/deletarContaAluno")
    public String deletarConta(Principal principal, RedirectAttributes redirectAttributes) {
        Aluno aluno = alunoRepository.findByEmail(principal.getName());
        alunoRepository.deleteById(aluno.getId());
        redirectAttributes.addFlashAttribute("mensagem", "Conta excluída com sucesso!");
        return "redirect:/logout";
    }
    
    @PostMapping("/deletarContaProfessor")
    public String deletarContaProfessor(Principal principal, RedirectAttributes redirectAttributes) {
        Professor professor = professorRepository.findByEmail(principal.getName());
        professorRepository.deleteById(professor.getId());
        redirectAttributes.addFlashAttribute("mensagem", "Conta excluída com sucesso!");
        return "redirect:/logout";
    }
}
