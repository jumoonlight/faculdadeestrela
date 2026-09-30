package com.ifsp.anajuliaferreira.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ifsp.anajuliaferreira.model.Referencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ReferenciaRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Referencia ref) {
        String sql = "INSERT INTO referencias (id_disc, id_livro) VALUES (:id_disc, :id_livro)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_disc", ref.getDisciplina().getId());
        query.setParameter("id_livro", ref.getLivro().getId());
        query.executeUpdate();
    }

    @Transactional
    public List<Referencia> findAll() {
        String sql = "SELECT * FROM referencias";
        Query q = em.createNativeQuery(sql, Referencia.class);
        return q.getResultList();
    }

    @Transactional
    public Referencia findByID(long id) {
        String sql = "SELECT * FROM referencias WHERE id_referencia = :id";
        Query query = em.createNativeQuery(sql, Referencia.class);
        query.setParameter("id", id);
        return (Referencia) query.getSingleResult();
    }

    @Transactional
    public void update(Referencia ref) {
        String sql = "UPDATE referencias SET id_disc = :id_disc, id_livro = :id_livro WHERE id_referencia = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", ref.getId());
        query.setParameter("id_disc", ref.getDisciplina().getId());
        query.setParameter("id_livro", ref.getLivro().getId());
        query.executeUpdate();
    }

    @Transactional
    public void deleteById(Long id) {
        String sql = "DELETE FROM referencias WHERE id_referencia = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();

        
    }

    @Transactional
public List<Referencia> findByDisciplina(long id_disc) {
    String sql = "SELECT * FROM referencias WHERE id_disc = :id_disc";
    Query q = em.createNativeQuery(sql, Referencia.class);
    q.setParameter("id_disc", id_disc);
    return q.getResultList();
}

@Transactional
public Referencia findByDisciplinaAndLivro(long id_disc, long id_livro) {
    String sql = "SELECT * FROM referencias WHERE id_disc = :id_disc AND id_livro = :id_livro";
    Query q = em.createNativeQuery(sql, Referencia.class);
    q.setParameter("id_disc", id_disc);
    q.setParameter("id_livro", id_livro);
    @SuppressWarnings("unchecked")
    List<Referencia> lista = q.getResultList();
    return lista.isEmpty() ? null : lista.get(0);
}
}