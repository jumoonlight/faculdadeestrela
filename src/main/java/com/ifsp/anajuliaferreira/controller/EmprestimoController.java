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
import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.model.Emprestimo;
import com.ifsp.anajuliaferreira.model.Livro;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.EmprestimoRepository;
import com.ifsp.anajuliaferreira.repository.LivroRepository;



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
    public String cadastrarEmprestimo(@RequestParam Date data_emprestimo, @RequestParam Date data_devolucao,  @RequestParam Long id_aluno, @RequestParam Long id_oferta_disc) {
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
        Emprestimo emprestimo = emprestimoRepository.findByID(id);
        List<Aluno> alunos = alunoRepository.findAll();
        List<Livro> livros = livroRepository.findAll();
        model.addAttribute("emprestimos", emprestimo);
        model.addAttribute("livros", livros);
        model.addAttribute("alunos", alunos);
        return "editarEmprestimo";
    }
    @PostMapping("/atualizarEmprestimo")
    public String atualizarEmprestimo(@RequestParam long id, @RequestParam Date data_emprestimo, @RequestParam Date data_devolucao, @RequestParam Long id_livro, @RequestParam Long id_aluno){
        Emprestimo emprestimo = emprestimoRepository.findByID(id);
        Aluno aluno = alunoRepository.findByID(id_aluno);
        Livro livro = livroRepository.findByID(id_livro);
        emprestimo.setDataEmprestimo(data_emprestimo);
        emprestimo.setDataDevolucao(data_devolucao);
        emprestimo.setAluno(aluno);
        emprestimo.setLivro(livro);
        emprestimoRepository.update(emprestimo);
        return "redirect:/listarEmprestimos";
    }
    @GetMapping("/emprestimo/{id}/deletar")
    public String excluirEmprestimo(@PathVariable long id){
        emprestimoRepository.deleteById(id);
        return "redirect:/listarEmprestimos";
    }
}
