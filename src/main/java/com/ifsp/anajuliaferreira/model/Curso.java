package com.ifsp.anajuliaferreira.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cursos")
public class Curso {
    public Curso() {
    }
    public Curso(String nome, String desc, String turno, int numeroSemestres) {
        this.nome = nome;
        this.desc = desc;
        this.turno = turno;
        this.numeroSemestres = numeroSemestres;
    } 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "nome")
    private String nome;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "descricao")
    private String desc;
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Column(name = "turno")
    private String turno;
    public String getTurno() {
        return turno;
    }
    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Column(name = "semestres")
    private int numeroSemestres;
    public int getNumeroSemestres() {
        return numeroSemestres;
    }
    public void setNumeroSemestres(int numeroSemestres) {
        this.numeroSemestres = numeroSemestres;
    }

}

