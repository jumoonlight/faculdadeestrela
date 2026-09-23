package com.ifsp.anajuliaferreira.model;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("aluno")
@PrimaryKeyJoinColumn(name="id_pessoa")
@Table(name = "aluno")
public class Aluno extends Pessoa{

    public Aluno() {
    }
    public Aluno(String prontuario, int ano_ingresso, int ano_saida) {
        this.prontuario = prontuario;
        this.ano_ingresso = ano_ingresso;
        this.ano_saida = ano_saida;
    }

    @Column(name = "prontuario")
    private String prontuario;
    public String getProntuario() {
        return prontuario;
    }
    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    @Column(name = "ano_ingresso")
    private int ano_ingresso;
    public int getAno_ingresso() {
        return ano_ingresso;
    }
    public void setAno_ingresso(int ano_ingresso) {
        this.ano_ingresso = ano_ingresso;
    }
    @Column(name = "ano_saida")
    private int ano_saida;
    public int getAno_saida() {
        return ano_saida;
    }
    public void setAno_saida(int ano_saida) {
        this.ano_saida = ano_saida;
    }
    
    
    
}
