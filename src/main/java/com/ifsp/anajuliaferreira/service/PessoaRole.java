package com.ifsp.anajuliaferreira.service;

public enum PessoaRole {
    ADMIN("admin"),
    PROFESSOR("professor"),
    ALUNO("aluno"),
    BIBLIOTECARIO("bibliotecario");
    private String role;
    
    PessoaRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
}
