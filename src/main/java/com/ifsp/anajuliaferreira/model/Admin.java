package com.ifsp.anajuliaferreira.model;

import com.ifsp.anajuliaferreira.service.PessoaRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
    public class Admin{
        public Admin() {
        }
        public Admin(String login, String password) {
            this.login = login;
            this.password = password;
            this.role = PessoaRole.ADMIN;
        }
        @Enumerated (EnumType.STRING)
        @Column(name = "role")
        private PessoaRole role;
        public PessoaRole getRole() {
            return role;
        }
        public void setRole(PessoaRole role) {
            this.role = role;
        }
        @Column (name = "login")
        private String login;
        public String getLogin() {
            return login;
        }
        public void setLogin(String login) {
            this.login = login;
        }

        @Column (name = "password")
        private String password;
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

