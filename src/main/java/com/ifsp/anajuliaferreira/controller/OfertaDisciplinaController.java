package com.ifsp.anajuliaferreira.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ifsp.anajuliaferreira.model.Curso;
import com.ifsp.anajuliaferreira.model.Disciplina;
import com.ifsp.anajuliaferreira.model.OfertaDisciplina;
import com.ifsp.anajuliaferreira.model.Professor;
import com.ifsp.anajuliaferreira.repository.CursoRepository;
import com.ifsp.anajuliaferreira.repository.DisciplinaRepository;
import com.ifsp.anajuliaferreira.repository.OfertaDisciplinaRepository;
import com.ifsp.anajuliaferreira.repository.ProfessorRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import com.ifsp.anajuliaferreira.model.Pessoa;

@Controller
public class OfertaDisciplinaController {
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private OfertaDisciplinaRepository ofertaDisciplinaRepository;

    private boolean ehProfessor(Authentication auth) {
    return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_PROFESSOR"));
}

private Pessoa professorLogado(Authentication auth) {
    return professorRepository.findByEmail(auth.getName());
}

    @GetMapping("/formularioOfertaDisciplina")
    public String ofertaDisciplina(Model model){
         model.addAttribute("professores", professorRepository.findAll());
         model.addAttribute("cursos", cursoRepository.findAll());
         model.addAttribute("disciplinas", disciplinaRepository.findAll());
         return "ofertadisciplinaFormulario";
    }
    @PostMapping("/cadastrarOfertaDisciplina")
    public String cadastrarOfertaDisciplina(@RequestParam Long id_prof, @RequestParam Long id_curso, @RequestParam Long id_disc,  @RequestParam Integer ano, @RequestParam Integer semestre, Model model) {
         Professor professor = professorRepository.findByID(id_prof);
         Curso curso = cursoRepository.findByID(id_curso);
         Disciplina disciplina = disciplinaRepository.findByID(id_disc);
         ofertaDisciplinaRepository.save(new OfertaDisciplina(professor,curso,disciplina, ano, semestre));
         return "redirect:/listarOfertasDisciplina";
    }
    @GetMapping("/listarOfertasDisciplina")
public String list(@RequestParam(value = "idDisciplina", required = false) Integer idDisciplina,
                   @RequestParam(value = "idProfessor", required = false) Integer idProfessor,
                   Authentication auth, Model model) {
    boolean professor = ehProfessor(auth);
    List<OfertaDisciplina> ofertas;

    if (professor) {
        ofertas = ofertaDisciplinaRepository.findByProfessor(
                professorLogado(auth).getIdPessoa().intValue());
    } else if (idDisciplina != null && idDisciplina > 0) {
        ofertas = ofertaDisciplinaRepository.findByDisciplina(idDisciplina);
    } else if (idProfessor != null && idProfessor > 0) {
        ofertas = ofertaDisciplinaRepository.findByProfessor(idProfessor);
    } else {
        ofertas = ofertaDisciplinaRepository.findAll();
    }

    model.addAttribute("ofertas", ofertas);
    model.addAttribute("ehProfessor", professor);
    model.addAttribute("disciplinas", disciplinaRepository.findAll());
    model.addAttribute("professores", professorRepository.findAll());
    model.addAttribute("idDisciplinaFiltro", idDisciplina);
    model.addAttribute("idProfessorFiltro", idProfessor);
    return "verOfertaDisciplina";
}

@GetMapping("/oferta/{id}")
public String oferta(@PathVariable long id, Model model, Authentication auth) {
    OfertaDisciplina ofertaDisciplina = ofertaDisciplinaRepository.findByID(id);
    if (ehProfessor(auth)) {
        Pessoa logado = professorLogado(auth);
        if (logado == null
                || !ofertaDisciplina.getProfessor().getIdPessoa().equals(logado.getIdPessoa())) {
            throw new AccessDeniedException("Oferta de outro professor");
        }
    }
    model.addAttribute("ofertas", ofertaDisciplina);
    return "detalhesOfertaDisciplina";
}
     @GetMapping("/oferta/{id}/editar")
    public String editarOferta(@PathVariable long id, Model model){
        OfertaDisciplina oferta = ofertaDisciplinaRepository.findByID(id);
        List<Professor> professor = professorRepository.findAll();
        List<Curso> curso = cursoRepository.findAll();
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        model.addAttribute("oferta", oferta);
        model.addAttribute("professores", professor);
        model.addAttribute("cursos", curso);
        model.addAttribute("disciplinas", disciplinas);
        return "editarOfertaDisciplina";
    }
    @PostMapping("/atualizarOfertaDisciplina")
    public String atualizarOferta(@RequestParam long id,@RequestParam Long id_prof, @RequestParam Long id_curso, @RequestParam Long id_disc, @RequestParam Integer ano, @RequestParam Integer semestre){
        OfertaDisciplina oferta = ofertaDisciplinaRepository.findByID(id);
        Professor professor = professorRepository.findByID(id_prof);
        Curso curso = cursoRepository.findByID(id_curso);
        Disciplina disciplina = disciplinaRepository.findByID(id_disc);
        oferta.setProfessor(professor);
        oferta.setCurso(curso);
        oferta.setDisciplina(disciplina);
        oferta.setAno(ano);
        oferta.setSemestre(semestre);
        ofertaDisciplinaRepository.update(oferta);
        return "redirect:/listarOfertasDisciplina";
    }
    @GetMapping("/oferta/{id}/deletar")
    public String excluirOferta(@PathVariable long id, RedirectAttributes redirectAttributes){
        try{
            ofertaDisciplinaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir a oferta de disciplina pois ela está relacionada a uma matrícula. Por favor, exclua a matrícula antes de excluir a oferta de disciplina.");
        }
         return "redirect:/listarOfertasDisciplina";
    }
}

