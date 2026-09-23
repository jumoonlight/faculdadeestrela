package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.ifsp.anajuliaferreira.model.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ProfessorRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public boolean insert (Professor professor) {
        try {
            String comando;
            Query query;
            comando = "INSERT INTO pessoa(nome, cpf, data_nasc, email, telefone, endereco, cidade, cep, uf) VALUES (:nome, :cpf, :data_nasc, :email, :telefone, :endereco, :cidade, :cep, :uf)";
            query = em.createNativeQuery(comando);
            query.setParameter("nome", professor.getNome());
            query.setParameter("cpf", professor.getCpf());
            query.setParameter("data_nasc", professor.getDataNasc());
            query.setParameter("email", professor.getEmail());
            query.setParameter("telefone", professor.getTelefone());
            query.setParameter("endereco", professor.getEndereco());
            query.setParameter("cidade", professor.getCidade());
            query.setParameter("cep", professor.getCep());
            query.setParameter("uf", professor.getUf());
            query.executeUpdate();

            Number idGerado = (Number) em
                    .createNativeQuery("SELECT LAST_INSERT_ID()")
                    .getSingleResult();

            comando = "INSERT INTO professor(id_pessoa, siape, area, formacao) VALUES (:id_pessoa, :siape, :area, :formacao)";
            query = em.createNativeQuery(comando);
            query.setParameter("id_pessoa", idGerado.intValue());
            query.setParameter("siape", professor.getSiape());
            query.setParameter("area", professor.getArea());
            query.setParameter("formacao", professor.getFormacao());
            query.executeUpdate();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
     @Transactional
    public List<Professor> findAll() {
        String comando = "SELECT * FROM professor INNER JOIN pessoa ON professor.id_pessoa = pessoa.id_pessoa";
        Query query = em.createNativeQuery(comando, Professor.class);
        List<Professor> professores = query.getResultList();
        return professores;
    }
     @Transactional
    public Professor findByID(Long id){
        String sql = "select * from professor INNER JOIN pessoa ON professor.id_pessoa = pessoa.id_pessoa WHERE professor.id_pessoa = :id_pessoa";
        Query query = em.createNativeQuery(sql, Professor.class);
        query.setParameter("id_pessoa", id);
        Professor professor = (Professor) query.getSingleResult();
        return professor;
    }
    @Transactional
     public void update(Professor professor){
         try{
            String sql;
            sql = "UPDATE pessoa SET id_pessoa = :id_pessoa, nome = :nome, cpf = :cpf, data_nasc = :data_nasc, email = :email, telefone = :telefone, endereco = :endereco, cidade = :cidade, cep = :cep, uf = :uf  WHERE id_pessoa = :id_pessoa";
            Query query =em.createNativeQuery(sql);
            query.setParameter("id_pessoa", professor.getId());
            query.setParameter("nome", professor.getNome());
            query.setParameter("cpf", professor.getCpf());
            query.setParameter("data_nasc", professor.getDataNasc());
            query.setParameter("email", professor.getEmail());
            query.setParameter("telefone", professor.getTelefone());
            query.setParameter("endereco", professor.getEndereco());
            query.setParameter("cidade", professor.getCidade());
            query.setParameter("cep", professor.getCep());
            query.setParameter("uf", professor.getUf());
            sql= "UPDATE professor SET id_pessoa = :id_pessoa, siape = :siape, area = :area, formacao = :formacao  WHERE id_pessoa = :id_pessoa";
            query =em.createNativeQuery(sql);
            query.setParameter("id_pessoa", professor.getId());
            query.setParameter("siape", professor.getSiape());
            query.setParameter("area", professor.getArea());
            query.setParameter("formacao", professor.getFormacao());
            query.executeUpdate();

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM professor WHERE id_pessoa = :id_pessoa";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_pessoa", id);
        query.executeUpdate();
    }
    @Transactional
    public Professor findByCpf(String cpf) {
        try {
            String sql = "SELECT * from professor INNER JOIN pessoa ON professor.id_pessoa = pessoa.id_pessoa WHERE pessoa.cpf = :cpf";
            Query query = em.createNativeQuery(sql, Professor.class);
            query.setParameter("cpf", cpf);
            @SuppressWarnings("unchecked")
            List<Professor> professores = query.getResultList();
            return professores.isEmpty() ? null : professores.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional
    public Professor findByCpfUpdate(String cpf, Long id_pessoa) {
        try {
            String sql = "SELECT * from professor INNER JOIN pessoa ON professor.id_pessoa = pessoa.id_pessoa WHERE pessoa.cpf = :cpf AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Professor.class);
            query.setParameter("cpf", cpf);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Professor> professores = query.getResultList();
            return professores.isEmpty() ? null : professores.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional
    public Professor findByEmail(String email) {
        try {
            String sql = "SELECT * from professor INNER JOIN pessoa ON professor.id_pessoa = pessoa.id_pessoa WHERE pessoa.email = :email";
            Query query = em.createNativeQuery(sql, Professor.class);
            query.setParameter("email", email);
            @SuppressWarnings("unchecked")
            List<Professor> professores = query.getResultList();
            return professores.isEmpty() ? null : professores.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional
    public Professor findByEmailUpdate(String email, Long id_pessoa) {
        try {
            String sql = "SELECT * from professor INNER JOIN pessoa ON professor.id_pessoa = pessoa.id_pessoa WHERE pessoa.email = :email AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Professor.class);
            query.setParameter("email", email);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Professor> professores = query.getResultList();
            return professores.isEmpty() ? null : professores.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public Professor findBySiape(String siape) {
        try {
            String sql = "SELECT * from professor INNER JOIN pessoa ON professor.id_pessoa = pessoa.id_pessoa WHERE professor.siape = :siape";
            Query query = em.createNativeQuery(sql, Professor.class);
            query.setParameter("siape", siape);
            @SuppressWarnings("unchecked")
            List<Professor> professores = query.getResultList();
            return professores.isEmpty() ? null : professores.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public Professor findBySiapeUpdate(String siape, Long id_pessoa) {
        try {
            String sql = "SELECT * from professor INNER JOIN pessoa ON professor.id_pessoa = pessoa.id_pessoa WHERE professor.siape = :siape AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Professor.class);
            query.setParameter("siape", siape);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Professor> professores = query.getResultList();
            return professores.isEmpty() ? null : professores.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}

