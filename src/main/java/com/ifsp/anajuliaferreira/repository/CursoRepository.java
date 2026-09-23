package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.anajuliaferreira.model.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class CursoRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save (Curso curso){
        String sql = "INSERT INTO cursos (nome, descricao, turno, semestres) VALUES ( :nome, :descricao, :turno, :semestres)";
        Query query = em.createNativeQuery (sql);
        query.setParameter("nome", curso.getNome());
        query.setParameter("descricao", curso.getDesc());
        query.setParameter("turno", curso.getTurno());
        query.setParameter("semestres", curso.getNumeroSemestres());
        query.executeUpdate();
    }
     @Transactional
    public List<Curso> findAll() {
        String sql = "SELECT * FROM cursos";
        Query q = em.createNativeQuery(sql, Curso.class);
        List<Curso> cursos = q.getResultList();
        return cursos;
    }
     @Transactional
    public Curso findByID(long id){
        String sql = "select * from cursos WHERE id_curso = :id_curso";
        Query query = em.createNativeQuery(sql, Curso.class);
        query.setParameter("id_curso",id);
        Curso curso = (Curso) query.getSingleResult();
        return curso;
    }
    @Transactional
     public void update(Curso curso){
        String sql = "UPDATE cursos SET nome = :nome, descricao = :descricao, turno = :turno, semestres = :semestres WHERE id_curso = :id_curso";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id_curso", curso.getId());
        query.setParameter("nome", curso.getNome());
        query.setParameter("descricao", curso.getDesc());
        query.setParameter("turno", curso.getTurno());
        query.setParameter("semestres", curso.getNumeroSemestres());
        query.executeUpdate();
    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM cursos WHERE id_curso = :id_curso";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_curso", id);
        query.executeUpdate();
    }

}
