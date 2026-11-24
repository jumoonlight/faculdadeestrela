package com.ifsp.anajuliaferreira.controller;

import java.util.List;

import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String cadastrarMatricula(@RequestParam Long id_aluno, @RequestParam Long id_oferta_disc) {
         OfertaDisciplina ofertadisciplina = ofertaDisciplinaRepository.findByID(id_oferta_disc);
         Aluno aluno = alunoRepository.findByID(id_aluno);
         matriculaRepository.save(new Matricula(aluno,ofertadisciplina));
         return "redirect:/listarMatriculas";
    }
     @GetMapping("/listarMatriculas")
    public String list(@RequestParam(value = "idAluno", required = false) Integer idAluno,
            @RequestParam(value = "idOferta", required = false) Integer idOferta, Authentication authentication,
            Model model) {

                boolean ehAluno = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ALUNO"));

        List<Matricula> matriculas;

        if (ehAluno) {
             Aluno logado = (Aluno) alunoRepository.findByEmail(authentication.getName());
            matriculas = matriculaRepository.findByAluno( Math.toIntExact(logado.getIdAluno()));
            model.addAttribute("aluno", logado);
    }
       else if (idAluno != null && idAluno > 0) {
            matriculas = matriculaRepository.findByAluno(idAluno);
        } else if (idOferta != null && idOferta > 0) {
            matriculas = matriculaRepository.findByOferta(idOferta);
        } else {
            matriculas = matriculaRepository.findAll();
        }
       
    
        model.addAttribute("matriculas", matriculas);
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("ofertas", ofertaDisciplinaRepository.findAll());
        model.addAttribute("idAlunoFiltro", idAluno);
        model.addAttribute("idOfertaFiltro", idOferta);
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
