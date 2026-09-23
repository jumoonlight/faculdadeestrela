package com.ifsp.anajuliaferreira.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "oferta_disciplina")
public class OfertaDisciplina {
    public OfertaDisciplina() {
    }
    public OfertaDisciplina(Professor professor, Curso curso, Disciplina disciplina) {
        this.professor = professor;
        this.curso = curso;
        this.disciplina = disciplina;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta_disc")
    private int id;    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;
    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @ManyToOne
    @JoinColumn(name = "id_disc")
    private Disciplina disciplina;
    public Disciplina getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

}
