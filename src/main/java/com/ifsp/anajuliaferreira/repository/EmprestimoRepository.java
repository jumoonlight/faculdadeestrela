package com.ifsp.anajuliaferreira.repository;

import java.sql.Date;
import java.time.LocalDate;
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
    public void save(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimos (data_emprestimo, data_devolucao, id_pessoa, id_livro, status) "
                   + "VALUES (:data_emprestimo, :data_devolucao, :id_pessoa, :id_livro, :status)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("data_emprestimo", emprestimo.getDataEmprestimo());
        query.setParameter("data_devolucao", emprestimo.getDataDevolucao());
        query.setParameter("id_pessoa", emprestimo.getPessoa().getIdPessoa());
        query.setParameter("id_livro", emprestimo.getLivro().getId());
        query.setParameter("status", emprestimo.getStatus());
        query.executeUpdate();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Emprestimo> findAll() {
        Query q = em.createNativeQuery("SELECT * FROM emprestimos", Emprestimo.class);
        return q.getResultList();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Emprestimo> findByPessoa(Long idPessoa) {
        Query q = em.createNativeQuery("SELECT * FROM emprestimos WHERE id_pessoa = :id_pessoa", Emprestimo.class);
        q.setParameter("id_pessoa", idPessoa);
        return q.getResultList();
    }

    @Transactional
    public Emprestimo findByID(long id) {
        Query query = em.createNativeQuery("SELECT * FROM emprestimos WHERE id_emprestimo = :id_emprestimo", Emprestimo.class);
        query.setParameter("id_emprestimo", id);
        return (Emprestimo) query.getSingleResult();
    }

    @Transactional
    public void update(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimos SET data_emprestimo = :data_emprestimo, data_devolucao = :data_devolucao, "
                   + "id_pessoa = :id_pessoa, id_livro = :id_livro, status = :status WHERE id_emprestimo = :id_emprestimo";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_emprestimo", emprestimo.getId());
        query.setParameter("data_emprestimo", emprestimo.getDataEmprestimo());
        query.setParameter("data_devolucao", emprestimo.getDataDevolucao());
        query.setParameter("id_pessoa", emprestimo.getPessoa().getIdPessoa());
        query.setParameter("id_livro", emprestimo.getLivro().getId());
        query.setParameter("status", emprestimo.getStatus());
        query.executeUpdate();
    }

    @Transactional
    public void deleteById(Long id) {
        Query query = em.createNativeQuery("DELETE FROM emprestimos WHERE id_emprestimo = :id_emprestimo");
        query.setParameter("id_emprestimo", id);
        query.executeUpdate();
    }
    @Transactional
    public boolean livroIndisponivel(Long idLivro, LocalDate data_emprestimo, LocalDate data_devolucao, int ignorarId) {
        String sql = "SELECT COUNT(*) FROM emprestimos "
                   + "WHERE id_livro = :id_livro AND id_emprestimo <> :ignorar AND status = 'PENDENTE' "
                   + "AND data_emprestimo <= :fim AND data_devolucao >= :inicio";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_livro", idLivro);
        query.setParameter("ignorar", ignorarId);
        query.setParameter("inicio", data_emprestimo);
        query.setParameter("fim", data_devolucao);
        return ((Number) query.getSingleResult()).longValue() > 0;
    }
}