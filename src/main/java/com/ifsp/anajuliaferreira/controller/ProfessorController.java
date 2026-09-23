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

import com.ifsp.anajuliaferreira.model.Professor;
import com.ifsp.anajuliaferreira.repository.ProfessorRepository;
import com.ifsp.anajuliaferreira.util.Util;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;
    @GetMapping("/formularioProfessor")
    public String professor(){
        return "professorFormulario";
    }
    @PostMapping("/cadastrarProfessor")
    public String cadastrarProfessor(@RequestParam String nome, 
         @RequestParam String cpf,
         @RequestParam Date dataNasc, 
         @RequestParam String email, 
         @RequestParam String telefone, 
         @RequestParam String endereco,
         @RequestParam String cidade, 
         @RequestParam String cep,
         @RequestParam String uf,
         @RequestParam String siape,
         @RequestParam String area, 
         @RequestParam String formacao,
         RedirectAttributes redirectAttributes){
            Professor professor = new Professor();
            professor.setNome(nome);
            professor.setCpf(cpf);
            professor.setDataNasc(dataNasc);
            professor.setEmail(email);
            professor.setTelefone(telefone);
            professor.setEndereco(endereco);
            professor.setCidade(cidade);
            professor.setCep(cep);
            professor.setUf(uf);
            professor.setSiape(siape);
            professor.setArea(area);
            professor.setFormacao(formacao);
            if (!Util.validarCPF(cpf)) {
                redirectAttributes.addFlashAttribute("erro", "CPF inválido!");
                return "redirect:/formularioProfessor";
            } else if (!Util.validarNome(nome)){
                 redirectAttributes.addFlashAttribute("erro", "Nome inválido!");
                 return "redirect:/formularioProfessor";
            } else if (!Util.validarTelefone(telefone)){
                 redirectAttributes.addFlashAttribute("erro", "Telefone inválido");
                 return "redirect:/formularioProfessor";
            } else if (!Util.validarEmail(email)){
                 redirectAttributes.addFlashAttribute("erro", "Email inválido");
                 return "redirect:/formularioProfessor";
            }  else if (professorRepository.findByCpf(cpf) != null) {
                 redirectAttributes.addFlashAttribute("erro", "CPF já cadastrado!");
                 return "redirect:/formularioProfessor";
            }   else if (professorRepository.findByEmail(email) != null) {
                 redirectAttributes.addFlashAttribute("erro", "Email já cadastrado!");
                 return "redirect:/formularioProfessor";
            }   else if (Util.validarIdade(dataNasc.toString())){
                 redirectAttributes.addFlashAttribute("erro", "Idade inválida!");
                 return "redirect:/formularioProfessor";
            }   else if (professorRepository.findBySiape(siape) != null) {
                 redirectAttributes.addFlashAttribute("erro", "SIAPE já cadastrado!");
                 return "redirect:/formularioProfessor";
            }   else{
                professorRepository.insert(professor);
                return "redirect:/listarProfessores";
            }
    }
     @GetMapping("/listarProfessores")
    public String list(Model model){
        List<Professor> professores = professorRepository.findAll();
        model.addAttribute("professores", professores);
        return "verProfessor";
    }
    @GetMapping("/professor/{id}")
    public String professor(@PathVariable long id, Model model){
        Professor professor = professorRepository.findByID(id);
        model.addAttribute("professor", professor);
        return "detalhesProfessor";
    }
    @GetMapping("/professor/{id}/editar")
    public String editarProfessor(@PathVariable long id, Model model){
        Professor professor = professorRepository.findByID(id);
        model.addAttribute("professor", professor);
        return "editarProfessor";
    }
    @PostMapping("/atualizarProfessor")
    public String atualizarProfessor(@RequestParam long id,@RequestParam String nome, @RequestParam String cpf, @RequestParam Date dataNascimento, @RequestParam String email, @RequestParam String telefone, @RequestParam String endereco, @RequestParam String cidade, @RequestParam String cep, @RequestParam String uf, @RequestParam String siape, @RequestParam String area, @RequestParam String formacao, RedirectAttributes redirectAttributes){
        Professor professor = professorRepository.findByID(id);
        professor.setNome(nome);
        professor.setCpf(cpf);
        professor.setDataNasc(dataNascimento);
        professor.setEmail(email);
        professor.setTelefone(telefone);
        professor.setEndereco(endereco);
        professor.setCidade(cidade);
        professor.setCep(cep);
        professor.setUf(uf);
        professor.setSiape(siape);
        professor.setArea(area);
        professor.setFormacao(formacao);
        if (!Util.validarCPF(cpf)) {
                redirectAttributes.addFlashAttribute("erro", "CPF inválido!");
                return "redirect:/formularioProfessor";
            } else if (!Util.validarNome(nome)){
                 redirectAttributes.addFlashAttribute("erro", "Nome inválido!");
                 return "redirect:/formularioProfessor";
            } else if (!Util.validarTelefone(telefone)){
                 redirectAttributes.addFlashAttribute("erro", "Telefone inválido");
                 return "redirect:/formularioProfessor";
            } else if (!Util.validarEmail(email)){
                 redirectAttributes.addFlashAttribute("erro", "Email inválido");
                 return "redirect:/formularioProfessor";
            } else if (professorRepository.findByCpfUpdate(cpf, id) != null) {
                 redirectAttributes.addFlashAttribute("erro", "CPF já cadastrado!");
                 return "redirect:/formularioProfessor";
            } else if (professorRepository.findByEmailUpdate(email, id) != null) {
                 redirectAttributes.addFlashAttribute("erro", "Email já cadastrado!");
                 return "redirect:/formularioProfessor";
            } else if (Util.validarIdade(dataNascimento.toString())){
                 redirectAttributes.addFlashAttribute("erro", "Idade inválida!");
                 return "redirect:/formularioProfessor";
            } else if (professorRepository.findBySiapeUpdate(siape, id) != null) {
                 redirectAttributes.addFlashAttribute("erro", "SIAPE já cadastrado!");
                 return "redirect:/formularioProfessor";
            } else{
                 professorRepository.update(professor);
                 return "redirect:/listarProfessores";
            }
    }
    @GetMapping("/professor/{id}/deletar")
    public String excluirProfessor(@PathVariable long id, RedirectAttributes redirectAttributes){
        try{
            professorRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir o professor pois ele está relacionado a uma oferta de disciplina. Por favor, exclua a oferta de disciplina antes de excluir o professor.");
            return "redirect:/listarProfessores";
        }
        return "redirect:/listarProfessores";
    }
    
}
