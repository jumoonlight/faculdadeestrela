package com.ifsp.anajuliaferreira.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain correnteFiltroSeguranca(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/css/**", "/error").permitAll()
                        .requestMatchers("/formularioAluno",
                        "/cadastrarAluno",
                        "/listarAlunos",
                        "/aluno/**",
                        "/atualizarAluno",
                        "/formularioProfessor",
                        "/cadastrarProfessor",
                        "/listarProfessores",
                        "/professor/**",
                        "/atualizarProfessor",
                        "/formularioCurso",
                        "/cadastrarCurso",
                        "/listarCursos",
                        "/curso/**",
                        "/atualizarCurso",
                        "/formularioDisciplina",
                        "/cadastrarDisciplina",
                        "/listarDisciplinas",
                        "/disciplina/**",
                        "/atualizarDisciplina",
                        "/formularioOfertaDisciplina",
                        "/cadastrarOfertaDisciplina",
                        "/listarOfertasDisciplina",
                        "/oferta/**",
                        "/atualizarOfertaDisciplina",
                        "/formularioBibliotecario",
                        "/cadastrarBibliotecario",
                        "/listarBibliotecarios",
                        "/bibliotecario/**",
                        "/atualizarBibliotecario",
                        "/telainicial"
                        ).hasRole("ADMIN")
                          .requestMatchers("/formularioMatricula",
                            "/cadastrarMatricula",
                            "/listarMatriculas",
                            "/matricula/**",
                            "/atualizarMatricula").hasAnyRole("ADMIN","ALUNO")
                          .requestMatchers("/telainicialAluno").hasRole("ALUNO")
                          .requestMatchers("/telainicialProfessor").hasRole("PROFESSOR")
                          .requestMatchers("/formularioReferencia",
                            "/cadastrarReferencia",
                            "/listarReferencias",
                            "/referencia/**",
                            "/atualizarReferencia") .hasAnyRole("ADMIN","PROFESSOR")
                          .requestMatchers("/telainicialBibliotecario").hasRole("BIBLIOTECARIO")
                          .requestMatchers("/formularioLivro",
                            "/cadastrarLivro",
                            "/listarLivros",
                            "/livro/**",
                            "/listarProfessores/*",
                            "/professor/*",
                            "/atualizarLivro").hasAnyRole("ADMIN","BIBLIOTECARIO")
                          .requestMatchers("/listarAlunos",
                            "aluno/*").hasAnyRole("ADMIN","BIBLIOTECARIO","PROFESSOR")
                          .requestMatchers("/listarCursos",
                            "curso/*",
                            "/listarMatriculas",
                            "matricula/*",
                            "/listarOfertasDisciplina",
                            "/oferta/*").hasAnyRole("ADMIN","ALUNO","PROFESSOR")
                          .requestMatchers("/formularioEmprestimo",
                            "/cadastrarEmprestimo",
                            "/listarEmprestimos",
                            "/emprestimo/**",
                            "/atualizarEmprestimo",
                            "/listarLivros",
                            "/livro/*",
                            "/listarReferencias",
                            "/referencia/*",
                            "/listarDisciplinas",
                            "/disciplina/*"
                          ).hasAllRoles("ADMIN","ALUNO","PROFESSOR","BIBLIOTECARIO")
                          .anyRequest().authenticated() 
                )
                .formLogin(form -> form.loginPage("/login")
                        .loginPage("/login")
                        .defaultSuccessUrl("/menu", true)
                        .failureUrl("/login?erro")
                        .permitAll()) 
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?sair")
                        .invalidateHttpSession(true) 
                        .deleteCookies("JSESSIONID"))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation(fixation -> fixation.migrateSession()) 
                        .invalidSessionUrl("/login?expirou")
                        .maximumSessions(1)) 
                .build();
                return http.build();
            }
            @Bean
            public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
            }
        }