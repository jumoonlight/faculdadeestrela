package com.ifsp.anajuliaferreira.repository;

import org.springframework.stereotype.Repository;
import com.ifsp.anajuliaferreira.model.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class PessoaRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Pessoa findByID(Long id) {
        return em.find(Pessoa.class, id);
    }
}