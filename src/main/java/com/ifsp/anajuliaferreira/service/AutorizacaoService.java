package com.ifsp.anajuliaferreira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ifsp.anajuliaferreira.model.Pessoa;
import com.ifsp.anajuliaferreira.repository.AdminRepository;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.BibliotecarioRepository;
import com.ifsp.anajuliaferreira.repository.ProfessorRepository;

@Service
public class AutorizacaoService implements UserDetailsService {
    @Autowired private AlunoRepository alunoRepository;
    @Autowired private ProfessorRepository professorRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private BibliotecarioRepository bibliotecarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pessoa p;

        if ((p = alunoRepository.findByEmail(username)) != null)        return montar(p, "ALUNO");
        if ((p = professorRepository.findByEmail(username)) != null)    return montar(p, "PROFESSOR");
        if ((p = adminRepository.findByEmail(username)) != null)        return montar(p, "ADMIN");
        if ((p = bibliotecarioRepository.findByEmail(username)) != null) return montar(p, "BIBLIOTECARIO");

        throw new UsernameNotFoundException("Usuário não encontrado. Tente novamente");
    }

    private UserDetails montar(Pessoa p, String role) {
        return User.builder()
                .username(p.getUsername())
                .password(p.getPassword())
                .roles(role)
                .build();
    }
}