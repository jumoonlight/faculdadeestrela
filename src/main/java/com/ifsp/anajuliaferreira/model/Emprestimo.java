package com.ifsp.anajuliaferreira.model;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {
    public Emprestimo() {
    }
    public Emprestimo(Date data_emprestimo, Date data_devolucao,  Aluno aluno, Livro livro) {
        this.data_emprestimo = data_emprestimo;
        this.data_devolucao = data_devolucao;
        this.aluno = aluno;
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
    @Column(name = "data_emprestimo")
    private Date data_emprestimo;
    public Date getDataEmprestimo(){
        return data_emprestimo;
    }
    public void setDataEmprestimo(Date dataEmprestimo) {
        this.data_emprestimo = dataEmprestimo;
    }
    @Column(name = "data_devolucao")
    private Date data_devolucao;
    public Date getDataDevolucao(){
        return data_devolucao;
    }
    public void setDataDevolucao(Date dataDevolucao) {
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
}
