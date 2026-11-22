package com.ifsp.anajuliaferreira.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ifsp.anajuliaferreira.model.Bibliotecario;
import com.ifsp.anajuliaferreira.repository.BibliotecarioRepository;
import com.ifsp.anajuliaferreira.util.Util;

@Controller
public class BibliotecarioController {
    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/formularioBibliotecario")
    public String bibliotecario(){
        return "bibliotecarioFormulario";
    }
    @PostMapping("/cadastrarBibliotecario")
    public String cadastrarBibliotecario(@RequestParam String nome, 
         @RequestParam String cpf,
         @RequestParam Date dataNasc, 
         @RequestParam String email, 
         @RequestParam String password,
         @RequestParam String telefone, 
         @RequestParam String endereco,
         @RequestParam String cidade, 
         @RequestParam String cep,
         @RequestParam String uf,
         RedirectAttributes redirectAttributes){
            Bibliotecario bibliotecario = new Bibliotecario();
            bibliotecario.setNome(nome);
            bibliotecario.setCpf(cpf);
            bibliotecario.setDataNasc(dataNasc);
            bibliotecario.setEmail(email);
            bibliotecario.setPassword(passwordEncoder.encode(password));
            bibliotecario.setTelefone(telefone);
            bibliotecario.setEndereco(endereco);
            bibliotecario.setCidade(cidade);
            bibliotecario.setCep(cep);
            bibliotecario.setUf(uf);
             if (!Util.validarCPF(cpf)) {
                redirectAttributes.addFlashAttribute("erro", "CPF inválido!");
                return "redirect:/formularioBibliotecario";
            } else if (!Util.validarNome(nome)){
                 redirectAttributes.addFlashAttribute("erro", "Nome inválido!");
                 return "redirect:/formularioBibliotecario";
            } else if (!Util.validarTelefone(telefone)){
                 redirectAttributes.addFlashAttribute("erro", "Telefone inválido");
                 return "redirect:/formularioBibliotecario";
            } else if (!Util.validarEmail(email)){
                 redirectAttributes.addFlashAttribute("erro", "Email inválido");
                 return "redirect:/formularioBibliotecario";
            } else if (!Util.validarSenha(password)){
                 redirectAttributes.addFlashAttribute("erro", "Senha com pelo menos 8 caracteres!");
                 return "redirect:/formularioBibliotecario";
            }else if (bibliotecarioRepository.findByCpf(cpf) != null) {
                 redirectAttributes.addFlashAttribute("erro", "CPF já cadastrado!");
                 return "redirect:/formularioBibliotecario";
            } else if (Util.validarIdade(dataNasc.toString())){
                 redirectAttributes.addFlashAttribute("erro", "Idade inválida!");
                 return "redirect:/formularioBibliotecario";
            }   else if (bibliotecarioRepository.findByEmail(email) != null) {
                 redirectAttributes.addFlashAttribute("erro", "Email já cadastrado!");
                 return "redirect:/formularioBibliotecario";
            } else{
                bibliotecarioRepository.insert(bibliotecario);
                return "redirect:/listarBibliotecarios";
            }
    }
    @GetMapping("/listarBibliotecarios")
    public String list(Model model){
        List<Bibliotecario> bibliotecarios = bibliotecarioRepository.findAll();
        model.addAttribute("bibliotecarios", bibliotecarios);
        return "verBibliotecario";
    }
    @GetMapping("/bibliotecario/{id}")
    public String bibliotecario(@PathVariable long id, Model model){
        Bibliotecario bibliotecario = bibliotecarioRepository.findByID(id);
        model.addAttribute("bibliotecario", bibliotecario);
        return "detalhesBibliotecario";
    }
    @GetMapping("/bibliotecario/{id}/editar")
    public String editarBibliotecario(@PathVariable long id, Model model){
        Bibliotecario bibliotecario = bibliotecarioRepository.findByID(id);
        model.addAttribute("bibliotecario", bibliotecario);
        return "editarBibliotecario";
    }
    @PostMapping("/atualizarBibliotecario")
    public String atualizarBibliotecario(@RequestParam long id,@RequestParam String nome, @RequestParam String cpf, @RequestParam Date dataNascimento, @RequestParam String email, @RequestParam String password, @RequestParam String telefone, @RequestParam String endereco, @RequestParam String cidade, @RequestParam String cep, @RequestParam String uf, RedirectAttributes redirectAttributes){
        Bibliotecario bibliotecario = bibliotecarioRepository.findByID(id);
        bibliotecario.setNome(nome);
        bibliotecario.setCpf(cpf);
        bibliotecario.setDataNasc(dataNascimento);
        bibliotecario.setEmail(email);
        bibliotecario.setPassword(passwordEncoder.encode(password));
        bibliotecario.setTelefone(telefone);
        bibliotecario.setEndereco(endereco);
        bibliotecario.setCidade(cidade);
        bibliotecario.setCep(cep);
        bibliotecario.setUf(uf);
        if (!Util.validarCPF(cpf)) {
                redirectAttributes.addFlashAttribute("erro", "CPF inválido!");
                return "redirect:/formularioBibliotecario";
            } else if (!Util.validarNome(nome)){
                 redirectAttributes.addFlashAttribute("erro", "Nome inválido!");
                 return "redirect:/formularioBibliotecario";
            } else if (!Util.validarTelefone(telefone)){
                 redirectAttributes.addFlashAttribute("erro", "Telefone inválido");
                 return "redirect:/formularioBibliotecario";
            }
        if (!Util.validarCPF(cpf)) {
                redirectAttributes.addFlashAttribute("erro", "CPF inválido!");
                return "redirect:/formularioBibliotecario";
            } else if (!Util.validarNome(nome)){
                 redirectAttributes.addFlashAttribute("erro", "Nome inválido!");
                 return "redirect:/formularioBibliotecario";
            } else if (!Util.validarTelefone(telefone)){
                 redirectAttributes.addFlashAttribute("erro", "Telefone inválido");
                 return "redirect:/formularioBibliotecario";
            } else if (!Util.validarEmail(email)){
                 redirectAttributes.addFlashAttribute("erro", "Email inválido");
                 return "redirect:/formularioBibliotecario";
            } else if (!Util.validarSenha(password)){
                 redirectAttributes.addFlashAttribute("erro", "Senha com pelo menos 8 caracteres!");
                 return "redirect:/formularioBibliotecario";
            } else if (bibliotecarioRepository.findByCpfUpdate(cpf, id) != null) {
                 redirectAttributes.addFlashAttribute("erro", "CPF já cadastrado!");
                 return "redirect:/formularioBibliotecario";
            } else if(bibliotecarioRepository.findByEmailUpdate(email, id) != null) {
                 redirectAttributes.addFlashAttribute("erro", "Email já cadastrado!");
                 return "redirect:/formularioBibliotecario";
            } else if (Util.validarIdade(dataNascimento.toString())){
                 redirectAttributes.addFlashAttribute("erro", "Idade inválida!");
                 return "redirect:/formularioBibliotecario"; 
            }else{
                 bibliotecarioRepository.update(bibliotecario);
                 return "redirect:/listarBibliotecarios";
            }
    }
    @GetMapping("/bibliotecario/{id}/deletar")
    public String excluirBibliotecario(@PathVariable long id, RedirectAttributes redirectAttributes){
        try{
            bibliotecarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir o bibliotecário pois ele tem uma matrícula. Por favor, exclua a matrícula antes de excluir o bibliotecário.");
            return "redirect:/listarBibliotecarios";
        }
        return "redirect:/listarBibliotecarios";
    }
}
