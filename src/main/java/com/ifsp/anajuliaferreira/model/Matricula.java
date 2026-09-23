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
@Table(name = "matricula")
public class Matricula {

    public Matricula() {
    }
    public Matricula(Aluno aluno, OfertaDisciplina ofertaDisc) {
        this.aluno = aluno;
        this.ofertaDisc = ofertaDisc;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private int id;    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "id_oferta_disc")
    private OfertaDisciplina ofertaDisc;
    public OfertaDisciplina getOfertaDisc() {
        return ofertaDisc;
    }
    public void setOfertaDisc(OfertaDisciplina ofertaDisc) {
        this.ofertaDisc = ofertaDisc;
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
    
    
 
}
