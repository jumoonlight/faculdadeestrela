package com.ifsp.anajuliaferreira.controller;

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

import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.model.Matricula;
import com.ifsp.anajuliaferreira.model.OfertaDisciplina;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.MatriculaRepository;
import com.ifsp.anajuliaferreira.repository.OfertaDisciplinaRepository;

@Controller
public class MatriculaController {
    @Autowired private MatriculaRepository matriculaRepository;
    @Autowired private OfertaDisciplinaRepository ofertaDisciplinaRepository;
    @Autowired private AlunoRepository alunoRepository;

    
    private boolean ehAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private Aluno alunoLogado(Authentication auth) {
        return (Aluno) alunoRepository.findByEmail(auth.getName());
    }

   
    private void exigirDono(Matricula m, Authentication auth) {
        if (ehAdmin(auth)) return;
        Aluno logado = alunoLogado(auth);
        if (m == null || logado == null
                || !m.getAluno().getIdAluno().equals(logado.getIdAluno())) {
            throw new AccessDeniedException("Matrícula de outro aluno");
        }
    }

private boolean jaMatriculado(Aluno aluno, Long idOferta, int ignorarId) {
    return matriculaRepository.findByAluno(aluno.getIdAluno().intValue()).stream()
            .anyMatch(m -> m.getId() != ignorarId
                        && m.getOfertaDisc().getId() == idOferta.intValue());
}

   
    @GetMapping("/formularioMatricula")
    public String matricula(Model model) {
        model.addAttribute("ofertas", ofertaDisciplinaRepository.findAll());
        model.addAttribute("alunos", alunoRepository.findAll());
        return "matriculaFormulario";
    }

    @PostMapping("/cadastrarMatricula")
    public String cadastrarMatricula(@RequestParam(required = false) Long id_aluno,
                                     @RequestParam Long id_oferta_disc,
                                     Authentication auth, RedirectAttributes redirect) {
        Aluno aluno;
        if (ehAdmin(auth)) {
            if (id_aluno == null) {
                redirect.addFlashAttribute("erro", "Selecione o aluno que vai se matricular.");
                return "redirect:/formularioMatricula";
            }
            aluno = alunoRepository.findByID(id_aluno);
        } else {
            aluno = alunoLogado(auth);
        }

      
        OfertaDisciplina oferta = ofertaDisciplinaRepository.findByID(id_oferta_disc);

          if (jaMatriculado(aluno, id_oferta_disc, 0)) {
        String quem = ehAdmin(auth) ? "O aluno " + aluno.getNome() : "Você";
        redirect.addFlashAttribute("erro", quem + " já possui matrícula na oferta de \""
                + oferta.getDisciplina().getNome() + "\".");
        return "redirect:/formularioMatricula";
    }
        matriculaRepository.save(new Matricula(aluno, oferta));
        return "redirect:/listarMatriculas";
    }

    @GetMapping("/listarMatriculas")
    public String list(@RequestParam(value = "idAluno", required = false) Integer idAluno,
                       @RequestParam(value = "idOferta", required = false) Integer idOferta,
                       Authentication auth, Model model) {
        boolean admin = ehAdmin(auth);
        List<Matricula> matriculas;

        if (admin) {
            if (idAluno != null && idAluno > 0) {
                matriculas = matriculaRepository.findByAluno(idAluno);
            } else if (idOferta != null && idOferta > 0) {
                matriculas = matriculaRepository.findByOferta(idOferta);
            } else {
                matriculas = matriculaRepository.findAll();
            }
            model.addAttribute("alunos", alunoRepository.findAll());
            model.addAttribute("ofertas", ofertaDisciplinaRepository.findAll());
        } else {
            matriculas = matriculaRepository.findByAluno(alunoLogado(auth).getIdAluno().intValue());
        }

        model.addAttribute("matriculas", matriculas);
        model.addAttribute("ehAdmin", admin);
        model.addAttribute("idAlunoFiltro", idAluno);
        model.addAttribute("idOfertaFiltro", idOferta);
        return "verMatricula";
    }

    @GetMapping("/matricula/{id}")
    public String oferta(@PathVariable long id, Model model, Authentication auth) {
        Matricula matricula = matriculaRepository.findByID(id);
        exigirDono(matricula, auth);
        model.addAttribute("matriculas", matricula);
        return "detalhesMatricula";
    }

    @GetMapping("/matricula/{id}/editar")
    public String editarMatriculaString(@PathVariable long id, Model model, Authentication auth) {
        Matricula matricula = matriculaRepository.findByID(id);
        exigirDono(matricula, auth);
        model.addAttribute("matriculas", matricula);
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("ofertas", ofertaDisciplinaRepository.findAll());
        return "editarMatricula";
    }

    @PostMapping("/atualizarMatricula")
public String atualizarMatricula(@RequestParam long id,
                                 @RequestParam(required = false) Long id_aluno,
                                 @RequestParam Long id_oferta_disc,
                                 Authentication auth, RedirectAttributes redirect) {
    Matricula matricula = matriculaRepository.findByID(id);
    exigirDono(matricula, auth);

    OfertaDisciplina oferta = ofertaDisciplinaRepository.findByID(id_oferta_disc);
    Aluno aluno = (ehAdmin(auth) && id_aluno != null)
            ? alunoRepository.findByID(id_aluno)
            : matricula.getAluno();

    if (jaMatriculado(aluno, id_oferta_disc, (int) id)) {
        String quem = ehAdmin(auth) ? "O aluno " + aluno.getNome() : "Você";
        redirect.addFlashAttribute("erro", quem + " já possui matrícula na oferta de \""
                + oferta.getDisciplina().getNome() + "\".");
        return "redirect:/matricula/" + id + "/editar";
    }
    matricula.setOfertaDisc(oferta);
    matricula.setAluno(aluno);
    matriculaRepository.update(matricula);
    return "redirect:/listarMatriculas";
}

    @GetMapping("/matricula/{id}/deletar")
    public String excluirMatricula(@PathVariable long id, Authentication auth) {
        exigirDono(matriculaRepository.findByID(id), auth);
        matriculaRepository.deleteById(id);
        return "redirect:/listarMatriculas";
    }
}
