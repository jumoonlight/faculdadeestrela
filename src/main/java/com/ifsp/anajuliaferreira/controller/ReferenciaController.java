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
import org.springframework.dao.DataIntegrityViolationException;
import com.ifsp.anajuliaferreira.model.Referencia;
import com.ifsp.anajuliaferreira.repository.DisciplinaRepository;
import com.ifsp.anajuliaferreira.repository.LivroRepository;
import com.ifsp.anajuliaferreira.repository.ReferenciaRepository;

@Controller
public class ReferenciaController {

    @Autowired
    private ReferenciaRepository referenciaRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("/formularioReferencia")
    public String formulario(Model model) {
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        model.addAttribute("livros", livroRepository.findAll());
        return "referenciaFormulario";
    }

    @PostMapping("/cadastrarReferencia")
    public String cadastrar(@RequestParam Long id_disc, @RequestParam Long id_livro) {
        referenciaRepository.save(new Referencia(
                disciplinaRepository.findByID(id_disc),
                livroRepository.findByID(id_livro)));
        return "redirect:/listarReferencias";
    }

    @GetMapping("/listarReferencias")
    public String list(Model model) {
        model.addAttribute("referencias", referenciaRepository.findAll());
        return "verReferencia";
    }

    @GetMapping("/referencia/{id}")
    public String detalhes(@PathVariable long id, Model model) {
        model.addAttribute("referencia", referenciaRepository.findByID(id));
        return "detalhesReferencia";
    }

    @GetMapping("/referencia/{id}/editar")
    public String editar(@PathVariable long id, Model model) {
        model.addAttribute("referencia", referenciaRepository.findByID(id));
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        model.addAttribute("livros", livroRepository.findAll());
        return "editarReferencia";
    }

    @PostMapping("/atualizarReferencia")
    public String atualizar(@RequestParam long id,
                            @RequestParam Long id_disc,
                            @RequestParam Long id_livro) {
        Referencia r = referenciaRepository.findByID(id);
        r.setDisciplina(disciplinaRepository.findByID(id_disc));
        r.setLivro(livroRepository.findByID(id_livro));
        referenciaRepository.update(r);
        return "redirect:/listarReferencias";
    }

    @GetMapping("/referencia/{id}/deletar")
    public String deletar(@PathVariable long id, RedirectAttributes ra) {
        try {
            referenciaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            ra.addFlashAttribute("erro", "Não é possível excluir a referência.");
        }
        return "redirect:/listarReferencias";
    }
}