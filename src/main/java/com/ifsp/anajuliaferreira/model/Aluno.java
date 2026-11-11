package com.ifsp.anajuliaferreira.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Aluno extends Pessoa {

    @Column(name = "prontuario", unique = true, nullable = false)
    private String prontuario;

    @Column(name = "nome_mae")
    private String nomeMae;

    @Column(name = "nome_pai")
    private String nomePai;

    @Column(name = "contato_responsavel")
    private String contatoResponsavel;

    @Column(name = "ano_ingresso")
    private int anoIngresso;

   @Column(name = "ano_saida") 
   private Integer anoSaida;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    
    public String getProntuario() { return prontuario; }
    public void setProntuario(String prontuario) { this.prontuario = prontuario; }

    public String getNomeMae() { return nomeMae; }
    public void setNomeMae(String nomeMae) { this.nomeMae = nomeMae; }

    public String getNomePai() { return nomePai; }
    public void setNomePai(String nomePai) { this.nomePai = nomePai; }

    public String getContatoResponsavel() { return contatoResponsavel; }
    public void setContatoResponsavel(String contatoResponsavel) { this.contatoResponsavel = contatoResponsavel; }

    public int getAnoIngresso() { return anoIngresso; }
    public void setAnoIngresso(int anoIngresso) { this.anoIngresso = anoIngresso; }

    public Integer getAnoSaida() { return anoSaida; }
    public void setAnoSaida(Integer anoSaida) { this.anoSaida = anoSaida; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public List<Matricula> getMatriculas() { return matriculas; }
    public void setMatriculas(List<Matricula> matriculas) { this.matriculas = matriculas; }

    
    public Long getIdAluno() { return getIdPessoa(); }
    public void setIdAluno(Long idAluno) { setIdPessoa(idAluno); }

    public Integer getIdCurso() {
        return curso != null ? curso.getIdCurso() : 0;
    }
    public void setIdCurso(Integer idCurso) {
        if (this.curso == null) {
            this.curso = new Curso();
        }
        this.curso.setIdCurso(idCurso);
    }

    public void addMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setAluno(this);
    }
    public void removeMatricula(Matricula matricula) {
        matriculas.remove(matricula);
        matricula.setAluno(null);
    }
}
