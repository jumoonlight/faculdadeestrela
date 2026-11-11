package com.ifsp.anajuliaferreira.controller;


import org.hibernate.type.descriptor.jdbc.LongNVarcharJdbcType;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.model.UF;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.CursoRepository;
import com.ifsp.anajuliaferreira.util.Util;
import static com.ifsp.anajuliaferreira.util.Util.*;

import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {


    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/listar")
    public String listar(@RequestParam(value = "idCurso", required = false) Integer idCurso, Model model) {
        List<Aluno> alunos;
        if (idCurso != null && idCurso > 0) {
            alunos = alunoRepository.findByCurso(idCurso);
        } else {
            alunos = alunoRepository.findAll();
        }
        model.addAttribute("alunos", alunos);
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("idCursoFiltro", idCurso);
        return "verALuno";
    }

    @GetMapping("/cadastrar")
    public String novo(Model model, RedirectAttributes redirect) {
         if (cursoRepository.findAll().isEmpty() || cursoRepository.findAll() == null ) {
              redirect.addFlashAttribute("erro", "Não há nenhum curso cadastrado para vincular um aluno");
              return  "redirect:/aluno/listar";
        }else{
              model.addAttribute("aluno", new Aluno());
            model.addAttribute("cursos", cursoRepository.findAll());
            model.addAttribute("ufs", UF.values());
            return "alunoFormulario";
        }
      
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam(value = "idAluno", required = false) Long idAluno,
            @RequestParam("nome") String nome,
            @RequestParam("idade") int idade,
            @RequestParam("email") String email,
            @RequestParam("telefone") String telefone,
            @RequestParam("endereco") String endereco,
            @RequestParam("cidade") String cidade,
            @RequestParam("uf") String uf,
            @RequestParam("cpf") String cpf,
            @RequestParam("prontuario") String prontuario,
            @RequestParam("nomeMae") String nomeMae,
            @RequestParam("nomePai") String nomePai,
            @RequestParam("contatoResponsavel") String contatoResponsavel,
            @RequestParam("anoIngresso") int anoIngresso,
            @RequestParam(value = "anoSaida", required = false) Integer anoSaida,
            @RequestParam("idCurso") Integer idCurso,
            RedirectAttributes redirect) {

        

        if (!Util.validarCPF(cpf)) {
            redirect.addFlashAttribute("erro", CPF_INVALIDO);
            return "redirect:/aluno/cadastrar";
        }

        if (!Util.validarEmail(email)) {
            redirect.addFlashAttribute("erro", EMAIL_INVALIDO);
            return "redirect:/aluno/cadastrar";
        }

        if (!Util.validarTelefone(telefone)) {
            redirect.addFlashAttribute("erro", TELEFONE_INVALIDO);
            return "redirect:/aluno/cadastrar";
        }

        Long idIgnorar = (idAluno != null && idAluno > 0) ? idAluno : null;
        if (alunoRepository.existeEmail(email, idIgnorar)) {
            redirect.addFlashAttribute("erro", EMAIL_DUPLICADO);
            return "redirect:/aluno/cadastrar";
        }

        if (alunoRepository.existeCpf(cpf, idIgnorar)) {
            redirect.addFlashAttribute("erro", CPF_DUPLICADO);
            return "redirect:/aluno/cadastrar";
        }

        if (alunoRepository.existeProntuario(prontuario, idIgnorar)) {
            redirect.addFlashAttribute("erro", PRONTUARIO_DUPLICADO);
            return "redirect:/aluno/cadastrar";
        }

        
        Aluno aluno = new Aluno();
        if (idAluno != null && idAluno > 0) {
            aluno.setIdAluno(idAluno);
        }
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setTelefone(telefone);
        aluno.setEndereco(endereco);
        aluno.setCidade(cidade);
        aluno.setUf(uf);
        aluno.setCpf(cpf);
        aluno.setProntuario(prontuario);
        aluno.setNomeMae(nomeMae);
        aluno.setNomePai(nomePai);
        aluno.setContatoResponsavel(contatoResponsavel);
        aluno.setAnoIngresso(anoIngresso);
        aluno.setAnoSaida(anoSaida);
        aluno.setIdCurso(idCurso);

        boolean ok;
        if (idAluno == null || idAluno == 0) {
            ok = alunoRepository.insert(aluno);
        } else {
            ok = alunoRepository.update(aluno);
        }

        if (!ok) {
            redirect.addFlashAttribute("erro", ERRO_SALVAR);
            return "redirect:/aluno/cadastrar";
        }

        redirect.addFlashAttribute("mensagem", SUCESSO_SALVO);
        return "redirect:/aluno/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Aluno aluno = alunoRepository.findByID(id);
        model.addAttribute("aluno", aluno);
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("ufs", UF.values());
        return "editarAluno";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable int id, RedirectAttributes redirect) {
        try {
            alunoRepository.delete(id);
            redirect.addFlashAttribute("mensagem", SUCESSO_EXCLUIDO);
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", "Erro ao excluir aluno: " + e.getMessage());
        }
        return "redirect:/aluno/listar";
    }
}
