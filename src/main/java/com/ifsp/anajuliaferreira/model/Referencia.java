package com.ifsp.anajuliaferreira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "referencias")
public class Referencia {

    public Referencia() {
    }
    public Referencia(Disciplina disciplina, Livro livro) {
        this.disciplina = disciplina;
        this.livro = livro;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_referencia")
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @ManyToOne
    @JoinColumn(name = "id_disc")
    private Disciplina disciplina;
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private Livro livro;
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
}