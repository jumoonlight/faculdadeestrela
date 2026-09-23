package com.ifsp.anajuliaferreira.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.util.Util;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
public class AlunoController {
    @Autowired
    private AlunoRepository alunoRepository;
    @GetMapping("/formularioAluno")
    public String aluno(){
        return "alunoFormulario";
    }
    @PostMapping("/cadastrarAluno")
    public String cadastrarAluno(@RequestParam String nome, 
         @RequestParam String cpf,
         @RequestParam Date dataNascimento, 
         @RequestParam String email, 
         @RequestParam String telefone, 
         @RequestParam String endereco,
         @RequestParam String cidade, 
         @RequestParam String cep,
         @RequestParam String uf,
         @RequestParam String pront,
         @RequestParam int ano_ingresso, 
         @RequestParam int ano_saida,
         RedirectAttributes redirectAttributes){
            Aluno aluno = new Aluno();
            aluno.setNome(nome);
            aluno.setCpf(cpf);
            aluno.setDataNasc(dataNascimento);
            aluno.setEmail(email);
            aluno.setTelefone(telefone);
            aluno.setEndereco(endereco);
            aluno.setCidade(cidade);
            aluno.setCep(cep);
            aluno.setUf(uf);
            aluno.setProntuario(pront);
            aluno.setAno_ingresso(ano_ingresso);
            aluno.setAno_saida(ano_saida);
             if (!Util.validarCPF(cpf)) {
                redirectAttributes.addFlashAttribute("erro", "CPF inválido!");
                return "redirect:/formularioAluno";
            } else if (!Util.validarNome(nome)){
                 redirectAttributes.addFlashAttribute("erro", "Nome inválido!");
                 return "redirect:/formularioAluno";
            } else if (!Util.validarTelefone(telefone)){
                 redirectAttributes.addFlashAttribute("erro", "Telefone inválido");
                 return "redirect:/formularioAluno";
            } else if (!Util.validarEmail(email)){
                 redirectAttributes.addFlashAttribute("erro", "Email inválido");
                 return "redirect:/formularioAluno";
            } else if (alunoRepository.findByCpf(cpf) != null) {
                 redirectAttributes.addFlashAttribute("erro", "CPF já cadastrado!");
                 return "redirect:/formularioAluno";
            } else if (alunoRepository.findByEmail(email) != null) {
                 redirectAttributes.addFlashAttribute("erro", "Email já cadastrado!");
                 return "redirect:/formularioAluno";
            } else if (alunoRepository.findByProntuario(pront) != null) {
                 redirectAttributes.addFlashAttribute("erro", "Prontuário já cadastrado!");
                 return "redirect:/formularioAluno";
            }else{
                alunoRepository.insert(aluno);
                return "redirect:/listarAlunos";
            }
    }
    @GetMapping("/listarAlunos")
    public String list(Model model){
        List<Aluno> alunos = alunoRepository.findAll();
        model.addAttribute("alunos", alunos);
        return "verAluno";
    }
    @GetMapping("/aluno/{id}")
    public String aluno(@PathVariable long id, Model model){
        Aluno aluno = alunoRepository.findByID(id);
        model.addAttribute("aluno", aluno);
        return "detalhesAluno";
    }
    @GetMapping("/aluno/{id}/editar")
    public String editarAluno(@PathVariable long id, Model model){
        Aluno aluno = alunoRepository.findByID(id);
        model.addAttribute("aluno", aluno);
        return "editarAluno";
    }
    @PostMapping("/atualizarAluno")
    public String atualizarAluno(@RequestParam long id,@RequestParam String nome, @RequestParam String cpf, @RequestParam Date dataNascimento, @RequestParam String email, @RequestParam String telefone, @RequestParam String endereco, @RequestParam String cidade, @RequestParam String cep, @RequestParam String uf, @RequestParam String pront, @RequestParam int ano_ingresso, @RequestParam int ano_saida, RedirectAttributes redirectAttributes){
        Aluno aluno = alunoRepository.findByID(id);
        aluno.setNome(nome);
        aluno.setCpf(cpf);
        aluno.setDataNasc(dataNascimento);
        aluno.setEmail(email);
        aluno.setTelefone(telefone);
        aluno.setEndereco(endereco);
        aluno.setCidade(cidade);
        aluno.setCep(cep);
        aluno.setUf(uf);
        aluno.setProntuario(pront);
        aluno.setAno_ingresso(ano_ingresso);
        aluno.setAno_saida(ano_saida);
        if (!Util.validarCPF(cpf)) {
                redirectAttributes.addFlashAttribute("erro", "CPF inválido!");
                return "redirect:/formularioAluno";
            } else if (!Util.validarNome(nome)){
                 redirectAttributes.addFlashAttribute("erro", "Nome inválido!");
                 return "redirect:/formularioAluno";
            } else if (!Util.validarTelefone(telefone)){
                 redirectAttributes.addFlashAttribute("erro", "Telefone inválido");
                 return "redirect:/formularioAluno";
            } else if (!Util.validarEmail(email)){
                 redirectAttributes.addFlashAttribute("erro", "Email inválido");
                 return "redirect:/formularioAluno";
            } else if (alunoRepository.findByCpfUpdate(cpf, id) != null) {
                 redirectAttributes.addFlashAttribute("erro", "CPF já cadastrado!");
                 return "redirect:/formularioAluno";
            } else if(alunoRepository.findByEmailUpdate(email, id) != null) {
                 redirectAttributes.addFlashAttribute("erro", "Email já cadastrado!");
                 return "redirect:/formularioAluno";
            } else if(alunoRepository.findByProntuarioUpdate(pront, id) != null) {
                 redirectAttributes.addFlashAttribute("erro", "Prontuário já cadastrado!");
                 return "redirect:/formularioAluno"; 
            } else{
                 alunoRepository.update(aluno);
                 return "redirect:/listarAlunos";
            }
    }
    @GetMapping("/aluno/{id}/deletar")
    public String excluirAluno(@PathVariable long id, RedirectAttributes redirectAttributes){
        try{
            alunoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir o aluno pois ele tem uma matrícula. Por favor, exclua a matrícula antes de excluir o aluno.");
            return "redirect:/listarAlunos";
        }
        return "redirect:/listarAlunos";
    }
    }

