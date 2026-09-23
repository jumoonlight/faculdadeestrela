package com.ifsp.anajuliaferreira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ifsp.anajuliaferreira.model.Disciplina;
import com.ifsp.anajuliaferreira.repository.DisciplinaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DisciplinaController {
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @GetMapping("/formularioDisciplina")
    public String disciplina(){
        return "disciplinaFormulario";
    }
    @PostMapping("/cadastrarDisciplina")
    public String cadastrarDisciplina(@RequestParam String nome_disc, 
         @RequestParam String desc_disc,
         @RequestParam int num_sem_disc,
         @RequestParam int carga_horaria) {
         disciplinaRepository.save(new Disciplina(nome_disc, desc_disc, num_sem_disc, carga_horaria));
         return "redirect:/listarDisciplinas";
    }
    @GetMapping("/listarDisciplinas")
    public String list(Model model){
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        model.addAttribute("disciplinas", disciplinas);
        return "verDisciplina";
    }
    @GetMapping("/disciplina/{id}")
    public String disciplina(@PathVariable long id, Model model){
        Disciplina disciplina = disciplinaRepository.findByID(id);
        model.addAttribute("disciplina", disciplina);
        return "detalhesDisciplina";
    }
    @GetMapping("/disciplina/{id}/editar")
    public String editarDisciplina(@PathVariable long id, Model model){
        Disciplina disciplina = disciplinaRepository.findByID(id);
        model.addAttribute("disciplina", disciplina);
        return "editarDisciplina";
    }
    @PostMapping("/atualizarDisciplina")
    public String atualizarDisciplina(@RequestParam long id,@RequestParam String nome, @RequestParam String desc, @RequestParam int num_sem_disc, @RequestParam int carga_horaria){
        Disciplina disciplina = disciplinaRepository.findByID(id);
        disciplina.setNome(nome);
        disciplina.setDesc(desc);
        disciplina.setNumeroSemestres(num_sem_disc);
        disciplina.setCargaHoraria(carga_horaria);
        disciplinaRepository.update(disciplina);
        return "redirect:/listarDisciplinas";
    }
    @GetMapping("/disciplina/{id}/deletar")
    public String excluirDisciplina(@PathVariable long id, RedirectAttributes redirectAttributes){
         try{
            disciplinaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir a disciplina pois ela está relacionada a uma oferta de disciplina. Por favor, exclua a oferta de disciplina antes de excluir a disciplina.");
            return "redirect:/listarDisciplinas";
        }
        return "redirect:/listarDisciplinas";
    }
}