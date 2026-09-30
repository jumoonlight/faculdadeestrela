package com.ifsp.anajuliaferreira.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ifsp.anajuliaferreira.model.Livro;
import com.ifsp.anajuliaferreira.repository.LivroRepository;
import com.ifsp.anajuliaferreira.service.LivroService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LivroController {
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private LivroService livroService;
    @GetMapping("/formularioLivro")
    public String disciplina(){
        return "livroFormulario";
    }
    @PostMapping("/cadastrarLivro")
    public String cadastrarDisciplina(@RequestParam String titulo, 
         @RequestParam String autor,
         @RequestParam int anoPublicacao,
         @RequestParam String editora,
         @RequestParam String isbn,
         @RequestParam int numPaginas,
        @RequestParam ("capa") MultipartFile capa) {
            try {
            String caminho_arquivo = livroService.salvarCapa(capa);
            Livro livro = new Livro(titulo, autor, anoPublicacao, editora, isbn, numPaginas,  caminho_arquivo);
            livroRepository.save(livro);
        } catch (IOException e) {
            e.printStackTrace();
        }
         return "redirect:/listarLivros";
    }
    @GetMapping("/listarLivros")
    public String list(Model model){
        List<Livro> livros = livroRepository.findAll();
        model.addAttribute("livros", livros);
        return "verLivro";
    }
    @GetMapping("/livro/{id}")
    public String livro(@PathVariable long id, Model model){
        Livro livro = livroRepository.findByID(id);
        model.addAttribute("livro", livro);
        return "detalhesLivro";
    }
    @GetMapping("/livro/{id}/editar")
    public String editarLivro(@PathVariable long id, Model model){
        Livro livro = livroRepository.findByID(id);
        model.addAttribute("livro", livro);
        return "editarLivro";
    }
    @PostMapping("/atualizarLivro")
    public String atualizarLivro(@RequestParam long id,@RequestParam String titulo, @RequestParam String autor, @RequestParam int anoPublicacao, @RequestParam String editora, @RequestParam String isbn, @RequestParam int numPaginas,  @RequestParam ("capa") MultipartFile capa){
        Livro livro = livroRepository.findByID(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setEditora(editora);
        livro.setIsbn(isbn);
        livro.setNumPaginas(numPaginas);
        try {
            String caminho_arquivo = livroService.salvarCapa(capa);
            livro.setCapa(caminho_arquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        livroRepository.update(livro);
        return "redirect:/listarLivros";
    }
    @GetMapping("/livro/{id}/deletar")
    public String excluirLivro(@PathVariable long id, RedirectAttributes redirectAttributes){
         try{
            livroRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir o livro pois ele está relacionado a um empréstimo ou referenciado em uma disciplina. Por favor, exclua o empréstimo antes de excluir o livro.");
            return "redirect:/listarLivros";
        }
        return "redirect:/listarLivros";
    }
}