package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import com.ifsp.anajuliaferreira.model.Livro;

@Repository
public class LivroRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save (Livro livro){
        String sql = "INSERT INTO livros (titulo, autor, anoPublicacao, editora, isbn, numPaginas) VALUES (:titulo, :autor, :anoPublicacao, :editora, :isbn, :numPaginas)";
        Query query = em.createNativeQuery (sql);
        query.setParameter("titulo", livro.getTitulo());
        query.setParameter("autor", livro.getAutor());
        query.setParameter("anoPublicacao", livro.getAnoPublicacao());
        query.setParameter("editora", livro.getEditora());
        query.setParameter("isbn", livro.getIsbn());
        query.setParameter("numPaginas", livro.getNumPaginas());
        query.executeUpdate();
    }
     @Transactional
    public List<Livro> findAll() {
        String sql = "SELECT * FROM livros";
        Query q = em.createNativeQuery(sql,Livro.class);
        List<Livro> livros = q.getResultList();
        return livros;
    }
     @Transactional
    public Livro findByID(long id){
        String sql = "select * from livros WHERE id_livro = :id_livro";
        Query query = em.createNativeQuery(sql, Livro.class);
        query.setParameter("id_livro",id);
        Livro livro = (Livro) query.getSingleResult();
        return livro;
    }
    @Transactional
     public void update(Livro livro){
        String sql = "UPDATE livros SET titulo = :titulo, autor = :autor, anoPublicacao = :anoPublicacao, editora = :editora, isbn = :isbn, numPaginas = :numPaginas WHERE id_livro = :id_livro";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id_livro", livro.getId());
        query.setParameter("titulo", livro.getTitulo());
        query.setParameter("autor", livro.getAutor());
        query.setParameter("anoPublicacao", livro.getAnoPublicacao());
        query.setParameter("editora", livro.getEditora());
        query.setParameter("isbn", livro.getIsbn());
        query.setParameter("numPaginas", livro.getNumPaginas());
        query.executeUpdate();
    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM livros WHERE id_livro = :id_livro";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_livro", id);
        query.executeUpdate();
    }

}
