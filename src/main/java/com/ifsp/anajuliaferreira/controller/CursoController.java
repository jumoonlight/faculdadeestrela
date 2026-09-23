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
import com.ifsp.anajuliaferreira.repository.CursoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CursoController {
     @Autowired
    private CursoRepository cursoRepository;
    @GetMapping("/formularioCurso")
    public String curso(){
        return "cursoFormulario";
    }
    @PostMapping("/cadastrarCurso")
    public String cadastrarCurso(@RequestParam String nome_curs, 
         @RequestParam String desc_curs,
         @RequestParam String turno, 
         @RequestParam int num_sem_curso){
            cursoRepository.save(new Curso(nome_curs, desc_curs, turno, num_sem_curso));
            return "redirect:/listarCursos";
    }
    @GetMapping("/listarCursos")
    public String list(Model model){
        List<Curso> cursos = cursoRepository.findAll();
        model.addAttribute("cursos", cursos);
        return "verCurso";
    }
    @GetMapping("/curso/{id}")
    public String curso(@PathVariable long id, Model model){
        Curso curso = cursoRepository.findByID(id);
        model.addAttribute("curso", curso);
        return "detalhesCursos";
    }
    @GetMapping("/curso/{id}/editar")
    public String editarCurso(@PathVariable long id, Model model){
        Curso curso = cursoRepository.findByID(id);
        model.addAttribute("curso", curso);
        return "editarCurso";
    }
    @PostMapping("/atualizarCurso")
    public String atualizarCurso(@RequestParam long id,@RequestParam String nome, @RequestParam String desc, @RequestParam String turno, @RequestParam int num_sem_curso){
        Curso curso = cursoRepository.findByID(id);
        curso.setNome(nome);
        curso.setDesc(desc);
        curso.setTurno(turno);
        curso.setNumeroSemestres(num_sem_curso);
        cursoRepository.update(curso);
        return "redirect:/listarCursos";
    }
    @GetMapping("/curso/{id}/deletar")
    public String excluirCurso(@PathVariable long id, RedirectAttributes redirectAttributes){
        try{
            cursoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir o curso pois ele está relacionado a uma oferta de disciplina. Por favor, exclua a oferta de disciplina antes de excluir o curso.");
            return "redirect:/listarCursos";
        }
        return "redirect:/listarCursos";
    }
}
