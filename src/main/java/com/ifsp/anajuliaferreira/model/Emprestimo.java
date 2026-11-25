package com.ifsp.anajuliaferreira.model;
import java.sql.Date;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.Hibernate;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {
    public Emprestimo() {
    }
   public Emprestimo(LocalDate data_emprestimo, LocalDate data_devolucao, Pessoa pessoa, Livro livro) {
    this.data_emprestimo = data_emprestimo;
    this.data_devolucao = data_devolucao;
    this.pessoa = pessoa;
    this.livro = livro;
}

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emprestimo")
    private int id;
     public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }   

@ManyToOne
@JoinColumn(name = "id_pessoa")
private Pessoa pessoa;
public Pessoa getPessoa() { return pessoa; }
public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }


public String getTipoPessoa() {
    Object p = Hibernate.unproxy(pessoa);
    if (p instanceof Aluno) return "aluno";
    if (p instanceof Professor) return "professor";
    if (p instanceof Bibliotecario) return "bibliotecario";
    return "";
}
    
   
    @Column(name = "data_emprestimo")
    private LocalDate data_emprestimo;
    public LocalDate getDataEmprestimo(){
        return data_emprestimo;
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.data_emprestimo = dataEmprestimo;
    }
    @Column(name = "data_devolucao")
    private LocalDate data_devolucao;
    public LocalDate getDataDevolucao(){
        return data_devolucao;
    }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.data_devolucao = dataDevolucao;
    }
    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;
    public Aluno getAluno() {
        return aluno;
    }
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    @ManyToOne 
    @JoinColumn(name = "id_livro")
    private Livro livro;
    public Livro getLivro(){
        return livro;
    }
    public void setLivro (Livro livro){
        this.livro = livro;
    }

    @Column(name = "status", columnDefinition = "VARCHAR(20) NOT NULL DEFAULT 'PENDENTE'")
    private String status = "PENDENTE";

    public String getStatus() {
    return (status == null || status.isBlank()) ? "PENDENTE" : status;
    }
    public void setStatus(String status) { this.status = status; }

    public boolean isPendente() { return "PENDENTE".equals(getStatus()); }

    public boolean isAtrasado() {
    return isPendente() && data_devolucao != null
            && LocalDate.now().isAfter(data_devolucao);
    }

    public boolean isPodeRenovar() {
    if (!isPendente() || data_emprestimo == null || data_devolucao == null) return false;
    LocalDate hoje = LocalDate.now();
    return !hoje.isBefore(data_emprestimo)
        && !hoje.isAfter(data_devolucao);
    }
}
