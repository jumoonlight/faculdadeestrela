package com.ifsp.anajuliaferreira.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "disciplinas")
public class Disciplina {
    public Disciplina() {
    }
    public Disciplina(String nome, String desc, int numeroSemestres, int cargaHoraria) {
        this.nome = nome;
        this.desc = desc;
        this.numeroSemestres= numeroSemestres;
        this.cargaHoraria = cargaHoraria;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disc")
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

    @Column(name = "semestres")
    private int numeroSemestres;
    public int getNumeroSemestres() {
        return numeroSemestres;
    }
    public void setNumeroSemestres(int numeroSemestres) {
        this.numeroSemestres = numeroSemestres;
    }
    @Column(name = "carga_horaria")
    private int cargaHoraria;
    public int getCargaHoraria() {
        return cargaHoraria;
    }
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    
   
}
