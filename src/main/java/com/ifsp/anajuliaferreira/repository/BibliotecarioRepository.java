package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.anajuliaferreira.model.Admin;
import com.ifsp.anajuliaferreira.model.Bibliotecario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class BibliotecarioRepository {
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public boolean insert(Bibliotecario bibliotecario) {
        try {
            em.persist(bibliotecario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
     public List<Bibliotecario> findAll() {
        String comando = "SELECT * FROM bibliotecario INNER JOIN pessoa ON bibliotecario.id_pessoa = pessoa.id_pessoa";
        Query query = em.createNativeQuery(comando, Bibliotecario.class);
        List<Bibliotecario> bibliotecarios = query.getResultList();
        return bibliotecarios;
    }

     @Transactional
    public Bibliotecario findByID(Long id_pessoa){
        String sql = "select * from bibliotecario INNER JOIN pessoa ON bibliotecario.id_pessoa = pessoa.id_pessoa WHERE bibliotecario.id_pessoa = :id_pessoa";
        Query query = em.createNativeQuery(sql, Bibliotecario.class);
        query.setParameter("id_pessoa", id_pessoa);
        Bibliotecario bibliotecario = (Bibliotecario) query.getSingleResult();
        return bibliotecario;
    }
    @Transactional
     public void update(Bibliotecario bibliotecario){
        try{
            String sql;
            sql = "UPDATE pessoa SET id_pessoa = :id_pessoa, nome = :nome, cpf = :cpf, data_nasc = :data_nasc, email = :email, telefone = :telefone, endereco = :endereco, cidade = :cidade, cep = :cep, uf = :uf,  password = :password WHERE id_pessoa = :id_pessoa";
            Query query =em.createNativeQuery(sql);
            query.setParameter("id_pessoa", bibliotecario.getId());
            query.setParameter("nome", bibliotecario.getNome());
            query.setParameter("cpf", bibliotecario.getCpf());
            query.setParameter("data_nasc", bibliotecario.getDataNasc());
            query.setParameter("email", bibliotecario.getEmail());
            query.setParameter("telefone", bibliotecario.getTelefone());
            query.setParameter("endereco", bibliotecario.getEndereco());
            query.setParameter("cidade", bibliotecario.getCidade());
            query.setParameter("cep", bibliotecario.getCep());
            query.setParameter("uf", bibliotecario.getUf());
            query.setParameter("password", bibliotecario.getPassword());
            query.executeUpdate();

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM bibliotecario WHERE id_pessoa = :id_pessoa";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_pessoa", id);
        query.executeUpdate();
    }
    @Transactional
    public Bibliotecario findByCpf(String cpf) {
        try {
            String sql = "SELECT * from bibliotecario INNER JOIN pessoa ON bibliotecario.id_pessoa = pessoa.id_pessoa WHERE pessoa.cpf = :cpf";
            Query query = em.createNativeQuery(sql, Bibliotecario.class);
            query.setParameter("cpf", cpf);
            @SuppressWarnings("unchecked")
            List<Bibliotecario> bibliotecarios = query.getResultList();
            return bibliotecarios.isEmpty() ? null : bibliotecarios.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional
    public Bibliotecario findByCpfUpdate(String cpf, Long id_pessoa) {
        try {
            String sql = "SELECT * from bibliotecario INNER JOIN pessoa ON bibliotecario.id_pessoa = pessoa.id_pessoa WHERE pessoa.cpf = :cpf AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Bibliotecario.class);
            query.setParameter("cpf", cpf);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Bibliotecario> bibliotecarios = query.getResultList();
            return bibliotecarios.isEmpty() ? null : bibliotecarios.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional
    public Bibliotecario findByEmail(String email) {
        try {
            String sql = "SELECT * from bibliotecario INNER JOIN pessoa ON bibliotecario.id_pessoa = pessoa.id_pessoa WHERE pessoa.email = :email";
            Query query = em.createNativeQuery(sql, Bibliotecario.class);
            query.setParameter("email", email);
            @SuppressWarnings("unchecked")
            List<Bibliotecario> bibliotecarios = query.getResultList();
            return bibliotecarios.isEmpty() ? null : bibliotecarios.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public Bibliotecario findByEmailUpdate(String email, Long id_pessoa) {
        try {
            String sql = "SELECT * from bibliotecario INNER JOIN pessoa ON bibliotecario.id_pessoa = pessoa.id_pessoa WHERE pessoa.email = :email AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Bibliotecario.class);
            query.setParameter("email", email);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Bibliotecario> bibliotecarios = query.getResultList();
            return bibliotecarios.isEmpty() ? null : bibliotecarios.get(0);
        } catch (Exception e) {
            return null;
        }
    }

}


