package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.anajuliaferreira.model.Disciplina;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class DisciplinaRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save (Disciplina disciplina){
        String sql = "INSERT INTO disciplinas (nome, descricao, semestres, carga_horaria) VALUES (:nome, :descricao, :semestres, :carga_horaria)";
        Query query = em.createNativeQuery (sql);
        query.setParameter("nome", disciplina.getNome());
        query.setParameter("descricao", disciplina.getDesc());
        query.setParameter("semestres", disciplina.getNumeroSemestres());
        query.setParameter("carga_horaria", disciplina.getCargaHoraria());
        query.executeUpdate();
    }
     @Transactional
    public List<Disciplina> findAll() {
        String sql = "SELECT * FROM disciplinas";
        Query q = em.createNativeQuery(sql, Disciplina.class);
        List<Disciplina> disciplinas = q.getResultList();
        return disciplinas;
    }
     @Transactional
    public Disciplina findByID(long id){
        String sql = "select * from disciplinas WHERE id_disc = :id_disc";
        Query query = em.createNativeQuery(sql, Disciplina.class);
        query.setParameter("id_disc",id);
        Disciplina disciplina = (Disciplina) query.getSingleResult();
        return disciplina;
    }
    @Transactional
     public void update(Disciplina disciplina){
        String sql = "UPDATE disciplinas SET nome = :nome, descricao = :descricao, semestres = :semestres, carga_horaria = :carga_horaria WHERE id_disc = :id_disc";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id_disc", disciplina.getId());
        query.setParameter("nome", disciplina.getNome());
        query.setParameter("descricao", disciplina.getDesc());
        query.setParameter("semestres", disciplina.getNumeroSemestres());
        query.setParameter("carga_horaria", disciplina.getCargaHoraria());
        query.executeUpdate();
    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM disciplinas WHERE id_disc = :id_disc";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_disc", id);
        query.executeUpdate();
    }

}
