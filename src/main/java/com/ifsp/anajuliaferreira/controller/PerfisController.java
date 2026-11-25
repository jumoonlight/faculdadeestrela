package com.ifsp.anajuliaferreira.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.anajuliaferreira.model.Admin;
import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.model.Professor;
import com.ifsp.anajuliaferreira.model.Bibliotecario;
import com.ifsp.anajuliaferreira.repository.AdminRepository;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.ProfessorRepository;
import com.ifsp.anajuliaferreira.repository.BibliotecarioRepository;


@Controller
public class PerfisController {
    @Autowired 
    AdminRepository adminRepository;
    @Autowired 
    AlunoRepository alunoRepository;
    @Autowired 
    ProfessorRepository professorRepository;
    @Autowired 
    BibliotecarioRepository bibliotecarioRepository;
    @GetMapping("/perfilAdmin")
    public String perfilAdmin(Principal principal, Model model) {
        Admin admin = adminRepository.findByEmail(principal.getName());
        model.addAttribute("admin", admin);
        return "perfilAdmin";
    }
    @GetMapping("/perfilAluno")
    public String perfilAluno(Principal principal, Model model) {
        Aluno aluno = (Aluno) alunoRepository.findByEmail(principal.getName());
        model.addAttribute("aluno", aluno);
        return "perfilAluno";
    }
    @GetMapping ("/perfilProfessor")
    public String perfilProfessor(Principal principal, Model model) {
        Professor professor = professorRepository.findByEmail(principal.getName());
        model.addAttribute("professor", professor);
        return "perfilProfessor";
    }
    @GetMapping ("/perfilBibliotecario")
    public String perfilBibliotecario(Principal principal, Model model) {
        Bibliotecario bibliotecario = bibliotecarioRepository.findByEmail(principal.getName());
        model.addAttribute("bibliotecario", bibliotecario);
        return "perfilBibliotecario";
    }
    @PostMapping("/deletarContaAluno")
    public String deletarConta(Principal principal, RedirectAttributes redirectAttributes) {
        Aluno aluno = (Aluno) alunoRepository.findByEmail(principal.getName());
        try{
            alunoRepository.deleteById(aluno.getIdPessoa());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir o aluno pois ele está relacionado a uma ou mais matrículas");
            return "redirect:/perfilAluno";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Conta excluída com sucesso!");
        return "redirect:/logout";
    }
    
    @PostMapping("/deletarContaProfessor")
    public String deletarContaProfessor(Principal principal, RedirectAttributes redirectAttributes) {
        Professor professor = professorRepository.findByEmail(principal.getName());
        try{
            professorRepository.deleteById(professor.getIdPessoa());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir o professor pois ele está relacionado a uma oferta de disciplina. Por favor, exclua a oferta de disciplina antes de excluir o professor.");
            return "redirect:/perfilProfessor";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Conta excluída com sucesso!");
        return "redirect:/logout";
    }
    
    @PostMapping("/deletarContaBibliotecario")
    public String deletarContaBibliotecario(Principal principal, RedirectAttributes redirectAttributes) {
        Bibliotecario bibliotecario = bibliotecarioRepository.findByEmail(principal.getName());

         try{
         bibliotecarioRepository.deleteById(bibliotecario.getIdPessoa());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir o bibliotecário pois ele tem um empréstimo. Por favor, exclua o empréstimo antes de excluir o bibliotecário.");
             return "redirect:/perfilBibliotecario";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Conta excluída com sucesso!");
        return "redirect:/logout";
    }
}
