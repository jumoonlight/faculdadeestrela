package com.ifsp.anajuliaferreira.model;

import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.security.core.parameters.P;

import com.ifsp.anajuliaferreira.service.PessoaRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "bibliotecario")
    public class Bibliotecario extends Pessoa{
        public Bibliotecario() {
        }
        public Bibliotecario(PessoaRole role) {
            this.role = role;
        }
        @Enumerated (EnumType.STRING)
        @Column(name = "role")
        private PessoaRole role = PessoaRole.BIBLIOTECARIO;
        public PessoaRole getRole() {
            return role;
        }
        public void setRole(PessoaRole role) {
            this.role = role;
        }
    }

