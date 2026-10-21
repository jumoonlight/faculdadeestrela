package com.ifsp.anajuliaferreira.model;

public class Bibliotecario extends Pessoa{
    public Bibliotecario() {
    }
    public Bibliotecario(String nome, String cpf, java.sql.Date dataNasc, String email, String telefone, String endereco, String cidade,
        String cep, String uf,  String login, String password) {
        super(nome, cpf, dataNasc, email, telefone, endereco, cidade, cep, uf, login, password);
        this.setRole(com.ifsp.anajuliaferreira.service.PessoaRole.BIBLIOTECARIO);
    }
    
    
}
