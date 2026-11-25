package com.ifsp.anajuliaferreira.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.anajuliaferreira.model.Emprestimo;
import com.ifsp.anajuliaferreira.model.Livro;
import com.ifsp.anajuliaferreira.model.Pessoa;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.BibliotecarioRepository;
import com.ifsp.anajuliaferreira.repository.EmprestimoRepository;
import com.ifsp.anajuliaferreira.repository.LivroRepository;
import com.ifsp.anajuliaferreira.repository.PessoaRepository;
import com.ifsp.anajuliaferreira.repository.ProfessorRepository;

@Controller
public class EmprestimoController {
    @Autowired private EmprestimoRepository emprestimoRepository;
    @Autowired private LivroRepository livroRepository;
    @Autowired private AlunoRepository alunoRepository;
    @Autowired private ProfessorRepository professorRepository;
    @Autowired private BibliotecarioRepository bibliotecarioRepository;
    @Autowired private PessoaRepository pessoaRepository;

   
    private boolean temRole(Authentication auth, String role) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    
    private boolean ehStaff(Authentication auth) {
        return temRole(auth, "ADMIN") || temRole(auth, "BIBLIOTECARIO");
    }

    private void exigirStaff(Authentication auth) {
        if (!ehStaff(auth)) throw new AccessDeniedException("Apenas admin ou bibliotecário");
    }

    private Pessoa pessoaLogada(Authentication auth) {
        String email = auth.getName();
        if (temRole(auth, "ALUNO"))     return alunoRepository.findByEmail(email);
        if (temRole(auth, "PROFESSOR")) return professorRepository.findByEmail(email);
        return bibliotecarioRepository.findByEmail(email);
    }

    private void exigirDono(Emprestimo e, Authentication auth) {
        if (ehStaff(auth)) return;
        Pessoa logada = pessoaLogada(auth);
        if (e == null || logada == null
                || !e.getPessoa().getIdPessoa().equals(logada.getIdPessoa())) {
            throw new AccessDeniedException("Empréstimo de outra pessoa");
        }
    }

    private void carregarListas(Model model) {
        model.addAttribute("livros", livroRepository.findAll());
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("professores", professorRepository.findAll());
        model.addAttribute("bibliotecarios", bibliotecarioRepository.findAll());
    }

    @GetMapping("/formularioEmprestimo")
    public String formulario(Model model) {
        carregarListas(model);
        return "emprestimoFormulario";
    }

    @PostMapping("/cadastrarEmprestimo")
    public String cadastrarEmprestimo(@RequestParam LocalDate data_emprestimo,
                                      @RequestParam(required = false) Long id_pessoa,
                                      @RequestParam Long id_livro,
                                      RedirectAttributes redirect,
                                      Authentication auth) {
        Pessoa pessoa;
         LocalDate data_devolucao = data_emprestimo.plusDays(7);
         Livro livro = livroRepository.findByID(id_livro);
        if (ehStaff(auth)) {
            if (id_pessoa == null) return "redirect:/formularioEmprestimo";
            pessoa = pessoaRepository.findByID(id_pessoa);
        } else {
            pessoa = pessoaLogada(auth);
        }
    
    if (emprestimoRepository.livroIndisponivel(id_livro,
        data_emprestimo, data_devolucao, 0)) {
        redirect.addFlashAttribute("erro", "O livro \"" + livro.getTitulo()
            + "\" já está emprestado nesse período. Escolha outras datas ou outro livro.");
    return "redirect:/formularioEmprestimo";
    }
    
       
        emprestimoRepository.save(new Emprestimo(data_emprestimo, data_devolucao, pessoa, livro));
        return "redirect:/listarEmprestimos";
    }

    @GetMapping("/listarEmprestimos")
    public String list(@RequestParam(value = "tipo", required = false) String tipo,
                       @RequestParam(value = "id_pessoa", required = false) Long id_pessoa,
                       Authentication auth, Model model) {
        List<Emprestimo> emprestimos;
        boolean staff = ehStaff(auth);

        if (staff) {
            if (id_pessoa != null) {
                emprestimos = emprestimoRepository.findByPessoa(id_pessoa);
            } else {
                emprestimos = emprestimoRepository.findAll();
                if (tipo != null && !tipo.isBlank()) {
                    emprestimos = emprestimos.stream()
                            .filter(e -> tipo.equals(e.getTipoPessoa()))
                            .toList();
                }
            }
            carregarListas(model);
        } else {
            emprestimos = emprestimoRepository.findByPessoa(pessoaLogada(auth).getIdPessoa());
        }

        model.addAttribute("emprestimos", emprestimos);
        model.addAttribute("ehStaff", staff);
        model.addAttribute("tipoFiltro", tipo);
        model.addAttribute("idPessoaFiltro", id_pessoa);
        return "verEmprestimo";
    }

  
    @GetMapping("/emprestimo/{id}")
    public String detalhes(@PathVariable long id, Model model, Authentication auth) {
        Emprestimo emprestimo = emprestimoRepository.findByID(id);
        exigirDono(emprestimo, auth);
        model.addAttribute("emprestimo", emprestimo);
        return "detalhesEmprestimo";
    }

   
    @GetMapping("/emprestimo/{id}/editar")
    public String editar(@PathVariable long id, Model model, Authentication auth) {
        exigirStaff(auth);
        model.addAttribute("emprestimos", emprestimoRepository.findByID(id));
        carregarListas(model);
        return "editarEmprestimo";
    }

    @PostMapping("/atualizarEmprestimo")
    public String atualizarEmprestimo(@RequestParam long id,
                                      @RequestParam LocalDate data_emprestimo,
                                      @RequestParam Long id_livro,
                                      @RequestParam(required = false) Long id_pessoa,
                                      RedirectAttributes redirect,
                                      Authentication auth) {
        exigirStaff(auth);
        Emprestimo e = emprestimoRepository.findByID(id);
        LocalDate data_devolucao = data_emprestimo.plusDays(7);

        if (e.isPendente() && emprestimoRepository
                .livroIndisponivel(id_livro,
        data_emprestimo, data_devolucao, (int) id)) {
    Livro livro = livroRepository.findByID(id_livro);
    redirect.addFlashAttribute("erro", "O livro \"" + livro.getTitulo()
            + "\" já está emprestado nesse período. Escolha outras datas ou outro livro.");
    return "redirect:/emprestimo/" + id + "/editar";
        }
        e.setDataEmprestimo(data_emprestimo);
        e.setDataDevolucao(data_devolucao);
        e.setLivro(livroRepository.findByID(id_livro));
        if (id_pessoa != null) {
            e.setPessoa(pessoaRepository.findByID(id_pessoa));
        }
        emprestimoRepository.update(e);
        return "redirect:/listarEmprestimos";
    }

    @GetMapping("/emprestimo/{id}/deletar")
    public String excluir(@PathVariable long id, Authentication auth) {
        exigirStaff(auth);
        emprestimoRepository.deleteById(id);
        return "redirect:/listarEmprestimos";
    }

   @GetMapping("/emprestimo/{id}/devolver")
public String devolver(@PathVariable long id, Authentication auth, RedirectAttributes redirect) {
    Emprestimo e = emprestimoRepository.findByID(id);
    exigirDono(e, auth);

    if (!e.isPendente()) {
        redirect.addFlashAttribute("erro", "Este empréstimo já foi devolvido.");
        return "redirect:/listarEmprestimos";
    }
    e.setStatus("DEVOLVIDO");
    emprestimoRepository.update(e);
    return "redirect:/listarEmprestimos?devolvido";
}
    

  
    @GetMapping("/emprestimo/{id}/renovar")
    public String renovar(@PathVariable long id, Authentication auth) {
        Emprestimo e = emprestimoRepository.findByID(id);
        exigirDono(e, auth);

        if (!e.isPodeRenovar()) {
            return "redirect:/listarEmprestimos?renovacaoNegada";
        }
        LocalDate fimAtual = e.getDataDevolucao();
        LocalDate inicioExtra = fimAtual.plusDays(1);
        LocalDate novoFim = fimAtual.plusDays(7);

    
        if (emprestimoRepository.livroIndisponivel(
                Long.valueOf(e.getLivro().getId()), inicioExtra, novoFim, e.getId())) {
            return "redirect:/listarEmprestimos?livroReservado";
        }
        e.setDataDevolucao(novoFim);
        emprestimoRepository.update(e);
        return "redirect:/listarEmprestimos?renovado";
    }
}