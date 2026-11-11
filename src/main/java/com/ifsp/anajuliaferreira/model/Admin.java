package com.ifsp.anajuliaferreira.model;

import com.ifsp.anajuliaferreira.service.PessoaRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
    public class Admin extends Pessoa{
        public Admin() {
        }
        public Admin(PessoaRole role) {
            this.role = role;
        }
        @Enumerated (EnumType.STRING)
        @Column(name = "role")
        private PessoaRole role  = PessoaRole.ADMIN;
        public PessoaRole getRole() {
            return role;
        }
        public void setRole(PessoaRole role) {
            this.role = role;
        }
    }

