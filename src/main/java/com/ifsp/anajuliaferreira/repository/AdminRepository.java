package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.ifsp.anajuliaferreira.model.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class AdminRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public boolean insert (Admin admin) {
        try {
            String comando;
            Query query;
            comando = "INSERT INTO pessoa(nome, cpf, data_nasc, email, telefone, endereco, cidade, cep, uf,  password) VALUES (:nome, :cpf, :data_nasc, :email, :telefone, :endereco, :cidade, :cep, :uf, :password)";
            query = em.createNativeQuery(comando);
            query.setParameter("nome", admin.getNome());
            query.setParameter("cpf", admin.getCpf());
            query.setParameter("data_nasc", admin.getDataNasc());
            query.setParameter("email", admin.getEmail());
            query.setParameter("telefone", admin.getTelefone());
            query.setParameter("endereco", admin.getEndereco());
            query.setParameter("cidade", admin.getCidade());
            query.setParameter("cep", admin.getCep());
            query.setParameter("uf", admin.getUf());
            query.setParameter("password", admin.getPassword());
            query.executeUpdate();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
     public List<Admin> findAll() {
        String comando = "SELECT * FROM admin INNER JOIN pessoa ON admin.id_pessoa = pessoa.id_pessoa";
        Query query = em.createNativeQuery(comando, Admin.class);
        List<Admin> admins = query.getResultList();
        return admins;
    }

     @Transactional
    public Admin findByID(Long id_pessoa){
        String sql = "select * from admin INNER JOIN pessoa ON admin.id_pessoa = pessoa.id_pessoa WHERE admin.id_pessoa = :id_pessoa";
        Query query = em.createNativeQuery(sql, Admin.class);
        query.setParameter("id_pessoa", id_pessoa);
        Admin admin = (Admin) query.getSingleResult();
        return admin;
    }
    @Transactional
     public void update(Admin admin){
        try{
            String sql;
            sql = "UPDATE pessoa SET id_pessoa = :id_pessoa, nome = :nome, cpf = :cpf, data_nasc = :data_nasc, email = :email, telefone = :telefone, endereco = :endereco, cidade = :cidade, cep = :cep, uf = :uf,  password = :password WHERE id_pessoa = :id_pessoa";
            Query query =em.createNativeQuery(sql);
            query.setParameter("id_pessoa", admin.getId());
            query.setParameter("nome", admin.getNome());
            query.setParameter("cpf", admin.getCpf());
            query.setParameter("data_nasc", admin.getDataNasc());
            query.setParameter("email", admin.getEmail());
            query.setParameter("telefone", admin.getTelefone());
            query.setParameter("endereco", admin.getEndereco());
            query.setParameter("cidade", admin.getCidade());
            query.setParameter("cep", admin.getCep());
            query.setParameter("uf", admin.getUf());
            query.setParameter("password", admin.getPassword());
            query.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM admin WHERE id_pessoa = :id_pessoa";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_pessoa", id);
        query.executeUpdate();
    }
    @Transactional
    public Admin findByCpf(String cpf) {
        try {
            String sql = "SELECT * from admin INNER JOIN pessoa ON admin.id_pessoa = pessoa.id_pessoa WHERE pessoa.cpf = :cpf";
            Query query = em.createNativeQuery(sql, Admin.class);
            query.setParameter("cpf", cpf);
            @SuppressWarnings("unchecked")
            List<Admin> admins = query.getResultList();
            return admins.isEmpty() ? null : admins.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional
    public Admin findByCpfUpdate(String cpf, Long id_pessoa) {
        try {
            String sql = "SELECT * from admin INNER JOIN pessoa ON admin.id_pessoa = pessoa.id_pessoa WHERE pessoa.cpf = :cpf AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Admin.class);
            query.setParameter("cpf", cpf);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Admin> admins = query.getResultList();
            return admins.isEmpty() ? null : admins.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public Admin findByEmail(String email) {
        try {
            String sql = "SELECT * from admin INNER JOIN pessoa ON admin.id_pessoa = pessoa.id_pessoa WHERE pessoa.email = :email";
            Query query = em.createNativeQuery(sql, Admin.class);
            query.setParameter("email", email);
            @SuppressWarnings("unchecked")
            List<Admin> admins = query.getResultList();
            return admins.isEmpty() ? null : admins.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional 
    public UserDetails findByUsername(String username) {
        return findByEmail(username);
    }
    @Transactional 
    public Admin findByEmailUpdate(String email, Long id_pessoa) {
        try {
            String sql = "SELECT * from admin INNER JOIN pessoa ON admin.id_pessoa = pessoa.id_pessoa WHERE pessoa.email = :email AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Admin.class);
            query.setParameter("email", email);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Admin> admins = query.getResultList();
            return admins.isEmpty() ? null : admins.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    }



