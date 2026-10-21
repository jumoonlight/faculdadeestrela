package com.ifsp.anajuliaferreira.model;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ifsp.anajuliaferreira.service.PessoaRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements UserDetails {
    public Pessoa() {
    }
    public Pessoa(String nome, String cpf, Date dataNasc, String email, String telefone, String endereco, String cidade,
        String cep, String uf,  String login, String password) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cep = cep;
        this.uf = uf;
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Long id;    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "nome")
    private String nome;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "cpf")
    private String cpf;
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Column(name = "data_nasc")
    private Date dataNasc;
    public Date getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Column(name = "email")
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "telefone")
    private String telefone;
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Column(name = "endereco")
    private String endereco;
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Column(name = "cidade")
    private String cidade;
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Column(name = "cep")
    private String cep;
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    @Column(name = "uf")
    private String uf;
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    @Column(name = "login")
    private String login;
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    @Column(name = "password")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == PessoaRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else if(this.role == PessoaRole.PROFESSOR){
            return List.of(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
        }else if(this.role == PessoaRole.ALUNO){
            return List.of(new SimpleGrantedAuthority("ROLE_ALUNO"));
        }else if(this.role == PessoaRole.BIBLIOTECARIO){
            return List.of(new SimpleGrantedAuthority("ROLE_BIBLIOTECARIO"));
        }else{
            return Collections.emptyList();
        }
    }
    @Override 
    public String getUsername() {
        return login;
    }

}