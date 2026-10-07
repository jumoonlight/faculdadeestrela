package com.ifsp.anajuliaferreira.controller;

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
import com.ifsp.anajuliaferreira.model.Matricula;
import com.ifsp.anajuliaferreira.model.OfertaDisciplina;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.MatriculaRepository;
import com.ifsp.anajuliaferreira.repository.OfertaDisciplinaRepository;


@Controller
public class MatriculaController {
    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private OfertaDisciplinaRepository ofertaDisciplinaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @GetMapping("/formularioMatricula")
    public String matricula(Model model){
         model.addAttribute("ofertas", ofertaDisciplinaRepository.findAll());
         model.addAttribute("alunos", alunoRepository.findAll());
         return "matriculaFormulario";
    }
    @PostMapping("/cadastrarMatricula")
    public String cadastrarMatricula(@RequestParam Long id_aluno, @RequestParam Long id_oferta_disc, RedirectAttributes redirectAttributes) {
         OfertaDisciplina ofertadisciplina = ofertaDisciplinaRepository.findByID(id_oferta_disc);
         Aluno aluno = alunoRepository.findByID(id_aluno);
         if(matriculaRepository.existe(id_aluno, id_oferta_disc).equals("true")){
            redirectAttributes.addFlashAttribute("erro", "Aluno já matriculado nessa disciplina!");
            return "redirect:/formularioMatricula";
         }
         matriculaRepository.save(new Matricula(aluno,ofertadisciplina));
         return "redirect:/listarMatriculas";
    }
     @GetMapping("/listarMatriculas")
    public String list(Model model){
        List<Matricula> matricula = matriculaRepository.findAll();
        model.addAttribute("matriculas", matricula);
        return "verMatricula";
    }
    @GetMapping("/matricula/{id}")
    public String oferta(@PathVariable long id, Model model){
        Matricula matricula = matriculaRepository.findByID(id);
        model.addAttribute("matriculas", matricula);
        return "detalhesMatricula";
    }
     @GetMapping("/matricula/{id}/editar")
    public String editarMatriculaString(@PathVariable long id, Model model){
        Matricula matricula = matriculaRepository.findByID(id);
        List<Aluno> alunos = alunoRepository.findAll();
        List<OfertaDisciplina> ofertas = ofertaDisciplinaRepository.findAll();
        model.addAttribute("matriculas", matricula);
        model.addAttribute("alunos", alunos);
        model.addAttribute("ofertas", ofertas);
        return "editarMatricula";
    }
    @PostMapping("/atualizarMatricula")
    public String atualizarMatricula(@RequestParam long id,@RequestParam Long id_aluno,@RequestParam Long id_oferta_disc){
        Matricula matricula = matriculaRepository.findByID(id);
        OfertaDisciplina oferta = ofertaDisciplinaRepository.findByID(id_oferta_disc);
        Aluno aluno = alunoRepository.findByID(id_aluno);
        matricula.setAluno(aluno);
        matricula.setOfertaDisc(oferta);
        matriculaRepository.update(matricula);
        return "redirect:/listarMatriculas";
    }
    @GetMapping("/matricula/{id}/deletar")
    public String excluirMatricula(@PathVariable long id){
        matriculaRepository.deleteById(id);
        return "redirect:/listarMatriculas";
    }
}
