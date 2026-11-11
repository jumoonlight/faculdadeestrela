package com.ifsp.anajuliaferreira.model;
import com.ifsp.anajuliaferreira.service.PessoaRole;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("professor")
@PrimaryKeyJoinColumn(name="id_pessoa")
@Table(name = "professor")
public class Professor  extends Pessoa{
     public Professor() {
    }
    public Professor(String siape, String area, String formacao, PessoaRole role) {
        this.siape = siape;
        this.area = area;
        this.formacao = formacao;
        this.role = role;
    }
    @Enumerated (EnumType.STRING)
    @Column(name = "role")
    private PessoaRole role = PessoaRole.PROFESSOR;
    public PessoaRole getRole() {
        return role;
    }
    public void setRole(PessoaRole role) {
        this.role = role;
    }
    @Column(name = "siape")
    private String siape;
    public String getSiape() {
        return siape;
    }
    public void setSiape(String siape) {
        this.siape = siape;
    }

    @Column(name = "area")
    private String area;
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    
    @Column(name = "formacao")
    private String formacao;
    public String getFormacao() {
        return formacao;
    }
    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    
}
