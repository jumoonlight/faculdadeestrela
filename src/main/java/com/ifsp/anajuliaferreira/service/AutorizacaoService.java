package com.ifsp.anajuliaferreira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ifsp.anajuliaferreira.model.Admin;
import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.model.Bibliotecario;
import com.ifsp.anajuliaferreira.model.Professor;
import com.ifsp.anajuliaferreira.repository.AdminRepository;
import com.ifsp.anajuliaferreira.repository.AlunoRepository;
import com.ifsp.anajuliaferreira.repository.BibliotecarioRepository;
import com.ifsp.anajuliaferreira.repository.ProfessorRepository;

@Service 
public class AutorizacaoService implements UserDetailsService{
    @Autowired 
    private AlunoRepository alunoRepository;

    @Autowired 
    private ProfessorRepository professorService;

    @Autowired 
    private AdminRepository adminService;

    @Autowired 
    private BibliotecarioRepository bibliotecarioService;

    @Override 
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
            UserDetails aluno = alunoRepository.findByUsername(username);
            if (aluno != null) {
                return User.builder()
                    .username(aluno.getUsername())
                    .password(aluno.getPassword())
                    .roles("ALUNO")
                    .build();
            }
            UserDetails professor = professorService.findByEmail(username);
            if (professor != null) {
                return User.builder()
                    .username(professor.getUsername())
                    .password(professor.getPassword())
                    .roles("PROFESSOR")
                    .build();
            }
            UserDetails admin = adminService.findByEmail(username);
            if (admin != null) {
                return User.builder()
                    .username(admin.getUsername())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
            }
            UserDetails bibliotecario = bibliotecarioService.findByEmail(username);
            if (bibliotecario != null) {
                return User.builder()
                    .username(bibliotecario.getUsername())
                    .password(bibliotecario.getPassword())
                    .roles("BIBLIOTECARIO")
                    .build();
            }
            throw new UsernameNotFoundException("Usuário não encontrado. Tente novamente");
}
}