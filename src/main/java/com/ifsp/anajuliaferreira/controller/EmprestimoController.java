package com.ifsp.anajuliaferreira.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.model.Emprestimo;
import com.ifsp.anajuliaferreira.model.Livro;
import com.ifsp.anajuliaferreira.model.Matricula;
import com.ifsp.anajuliaferreira.model.OfertaDisciplina;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.EmprestimoRepository;
import com.ifsp.anajuliaferreira.repository.LivroRepository;
import com.ifsp.anajuliaferreira.repository.MatriculaRepository;
import com.ifsp.anajuliaferreira.repository.OfertaDisciplinaRepository;


@Controller
public class EmprestimoController {
    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @GetMapping("/formularioEmprestimo")
    public String emprestimo(Model model){
         model.addAttribute("livros", livroRepository.findAll());
         model.addAttribute("alunos", alunoRepository.findAll());
         return "emprestimoFormulario";
    }
    @PostMapping("/cadastrarEmprestimo")
    public String cadastrarEmprestimo(@RequestParam LocalDateTime data_emprestimo, @RequestParam LocalDateTime data_devolucao,  @RequestParam Long id_aluno, @RequestParam Long id_oferta_disc) {
         Aluno aluno = alunoRepository.findByID(id_aluno); 
         Livro livro = livroRepository.findByID(id_oferta_disc);
         emprestimoRepository.save(new Emprestimo(data_emprestimo, data_devolucao,  aluno, livro));
         return "redirect:/listarEmprestimos";
    }
     @GetMapping("/listarEmprestimos")
    public String list(Model model){
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        model.addAttribute("emprestimos", emprestimos);
        return "verEmprestimo";
    }
    @GetMapping("/emprestimo/{id}")
    public String emprestimo(@PathVariable long id, Model model){
        Emprestimo emprestimo = emprestimoRepository.findByID(id);
        model.addAttribute("emprestimo", emprestimo);
        return "detalhesEmprestimo";
    }
     @GetMapping("/emprestimo/{id}/editar")
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
