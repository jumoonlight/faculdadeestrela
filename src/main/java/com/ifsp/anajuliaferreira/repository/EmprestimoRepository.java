package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.anajuliaferreira.model.Emprestimo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class EmprestimoRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save (Emprestimo emprestimo){
        String sql = "INSERT INTO emprestimos (data_emprestimo, data_devolucao, id_aluno, id_livro) VALUES (:data_emprestimo, :data_devolucao, :id_aluno, :id_livro)";
        Query query = em.createNativeQuery (sql);
        query.setParameter("data_emprestimo", emprestimo.getDataEmprestimo());
        query.setParameter("data_devolucao", emprestimo.getDataDevolucao());
        query.setParameter("id_aluno", emprestimo.getAluno().getId());
        query.setParameter("id_livro", emprestimo.getLivro().getId());
        query.executeUpdate();
    }
     @Transactional
    public List<Emprestimo> findAll() {
        String sql = "SELECT * FROM emprestimos";
        Query q = em.createNativeQuery(sql, Emprestimo.class);
        List<Emprestimo> emprestimos = q.getResultList();
        return emprestimos;
    }
     @Transactional
    public Emprestimo findByID(long id){
        String sql = "select * from emprestimos WHERE id_emprestimo = :id_emprestimo";
        Query query = em.createNativeQuery(sql, Emprestimo.class);
        query.setParameter("id_emprestimo",id);
        Emprestimo emprestimo = (Emprestimo) query.getSingleResult();
        return emprestimo;
    }
    @Transactional
     public void update(Emprestimo emprestimo){
        String sql = "UPDATE emprestimos SET data_emprestimo = :data_emprestimo, data_devolucao = :data_devolucao, id_aluno = :id_aluno, id_livro = :id_livro  WHERE id_emprestimo = :id_emprestimo";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id_emprestimo", emprestimo.getId());
        query.setParameter("data_emprestimo", emprestimo.getDataEmprestimo());
        query.setParameter("data_devolucao", emprestimo.getDataDevolucao());
        query.setParameter("id_aluno", emprestimo.getAluno().getId());
        query.setParameter("id_livro", emprestimo.getLivro().getId());
        query.executeUpdate();
    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM emprestimos WHERE id_emprestimo = :id_emprestimo";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_emprestimo", id);
        query.executeUpdate();
    }

}
