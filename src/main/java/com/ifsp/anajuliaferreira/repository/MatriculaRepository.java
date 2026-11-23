package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.anajuliaferreira.model.Matricula;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class MatriculaRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save (Matricula matricula){
        String sql = "INSERT INTO matricula (id_aluno, id_oferta_disc) VALUES (:id_aluno , :id_oferta_disc)";
        Query query = em.createNativeQuery (sql);
        query.setParameter("id_aluno", matricula.getAluno().getIdPessoa());
        query.setParameter("id_oferta_disc", matricula.getOfertaDisc().getId());
        query.executeUpdate();
    }
     @Transactional
    public List<Matricula> findAll() {
        String sql = "SELECT * FROM matricula";
        Query q = em.createNativeQuery(sql, Matricula.class);
        List<Matricula> matriculas = q.getResultList();
        return matriculas;
    }
     @Transactional
    public Matricula findByID(long id){
        String sql = "select * from matricula WHERE id_matricula = :id_matricula";
        Query query = em.createNativeQuery(sql, Matricula.class);
        query.setParameter("id_matricula",id);
        Matricula matricula= (Matricula) query.getSingleResult();
        return matricula;
    }
    @Transactional
     public void update(Matricula matricula){
        String sql = "UPDATE matricula SET id_aluno = :id_aluno, id_oferta_disc = :id_oferta_disc  WHERE id_matricula = :id_matricula";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id_matricula", matricula.getId());
        query.setParameter("id_aluno", matricula.getAluno().getIdPessoa());
        query.setParameter("id_oferta_disc", matricula.getOfertaDisc().getId());
        query.executeUpdate();
    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM matricula WHERE id_matricula = :id_matricula";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_matricula", id);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<Matricula> findByAluno(int idAluno) {
        String sql = "SELECT * FROM matricula WHERE id_aluno = :idAluno ORDER BY id_matricula";
        Query query = em.createNativeQuery(sql, Matricula.class);
        query.setParameter("idAluno", idAluno);
        return query.getResultList();
}

  @SuppressWarnings("unchecked")
    public List<Matricula> findByOferta(int idOferta) {
        String sql = "SELECT * FROM matricula WHERE id_oferta_disc = :idOferta ORDER BY id_matricula";
        Query query = em.createNativeQuery(sql, Matricula.class);
        query.setParameter("idOferta", idOferta);
        return query.getResultList();
    }
}
