package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.anajuliaferreira.model.OfertaDisciplina;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class OfertaDisciplinaRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save (OfertaDisciplina ofertaDisc){
        String sql = "INSERT INTO oferta_disciplina(id_professor, id_curso, id_disc ) VALUES (:id_professor , :id_curso, :id_disc)";
        Query query = em.createNativeQuery (sql);
        query.setParameter("id_professor", ofertaDisc.getProfessor().getId());
        query.setParameter("id_curso", ofertaDisc.getCurso().getId());
        query.setParameter("id_disc", ofertaDisc.getDisciplina().getId());
        query.executeUpdate();
    }
     @Transactional
    public List<OfertaDisciplina> findAll() {
        String sql = "SELECT * FROM oferta_disciplina";
        Query q = em.createNativeQuery(sql, OfertaDisciplina.class);
        List<OfertaDisciplina> ofertaDisciplinas = q.getResultList();
        return ofertaDisciplinas;
    }
     @Transactional
    public OfertaDisciplina findByID(long id){
        String sql = "select * from oferta_disciplina WHERE id_oferta_disc = :id_oferta_disc";
        Query query = em.createNativeQuery(sql, OfertaDisciplina.class);
        query.setParameter("id_oferta_disc",id);
        OfertaDisciplina ofertaDisciplina= (OfertaDisciplina) query.getSingleResult();
        return ofertaDisciplina;
    }
    @Transactional
     public void update(OfertaDisciplina ofertaDisciplina){
        String sql = "UPDATE oferta_disciplina SET id_professor = :id_professor, id_curso = :id_curso, id_disc = :id_disc  WHERE id_oferta_disc = :id_oferta_disc";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id_oferta_disc", ofertaDisciplina.getId());
        query.setParameter("id_professor", ofertaDisciplina.getProfessor().getId());
        query.setParameter("id_curso", ofertaDisciplina.getCurso().getId());
        query.setParameter("id_disc", ofertaDisciplina.getDisciplina().getId());
        query.executeUpdate();
    }
    @Transactional
    public void deleteById(Long id){
        OfertaDisciplina ofertaDisciplina = em.find(OfertaDisciplina.class,id);
        if (ofertaDisciplina != null){
            em.remove(ofertaDisciplina);
        }
    }

}
