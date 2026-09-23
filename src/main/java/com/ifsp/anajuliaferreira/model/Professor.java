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
@DiscriminatorValue("professor")
@PrimaryKeyJoinColumn(name="id_pessoa")
@Table(name = "professor")
public class Professor  extends Pessoa{
     public Professor() {
    }
    public Professor(String siape, String area, String formacao) {
        this.siape = siape;
        this.area = area;
        this.formacao = formacao;
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
