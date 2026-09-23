package com.ifsp.anajuliaferreira.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.ifsp.anajuliaferreira.model.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class AlunoRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public boolean insert (Aluno aluno) {
        try {
            String comando;
            Query query;
            comando = "INSERT INTO pessoa(nome, cpf, data_nasc, email, telefone, endereco, cidade, cep, uf) VALUES (:nome, :cpf, :data_nasc, :email, :telefone, :endereco, :cidade, :cep, :uf)";
            query = em.createNativeQuery(comando);
            query.setParameter("nome", aluno.getNome());
            query.setParameter("cpf", aluno.getCpf());
            query.setParameter("data_nasc", aluno.getDataNasc());
            query.setParameter("email", aluno.getEmail());
            query.setParameter("telefone", aluno.getTelefone());
            query.setParameter("endereco", aluno.getEndereco());
            query.setParameter("cidade", aluno.getCidade());
            query.setParameter("cep", aluno.getCep());
            query.setParameter("uf", aluno.getUf());
            query.executeUpdate();

            Number idGerado = (Number) em
                    .createNativeQuery("SELECT LAST_INSERT_ID()")
                    .getSingleResult();

            comando = "INSERT INTO aluno(id_pessoa, prontuario, ano_ingresso, ano_saida) VALUES (:id_pessoa, :prontuario, :ano_ingresso, :ano_saida)";
            query = em.createNativeQuery(comando);
            query.setParameter("id_pessoa", idGerado.intValue());
            query.setParameter("prontuario", aluno.getProntuario());
            query.setParameter("ano_ingresso", aluno.getAno_ingresso());
            query.setParameter("ano_saida", aluno.getAno_saida());
            query.executeUpdate();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
     public List<Aluno> findAll() {
        String comando = "SELECT * FROM aluno INNER JOIN pessoa ON aluno.id_pessoa = pessoa.id_pessoa";
        Query query = em.createNativeQuery(comando, Aluno.class);
        List<Aluno> alunos = query.getResultList();
        return alunos;
    }

     @Transactional
    public Aluno findByID(Long id_pessoa){
        String sql = "select * from aluno INNER JOIN pessoa ON aluno.id_pessoa = pessoa.id_pessoa WHERE aluno.id_pessoa = :id_pessoa";
        Query query = em.createNativeQuery(sql, Aluno.class);
        query.setParameter("id_pessoa", id_pessoa);
        Aluno aluno = (Aluno) query.getSingleResult();
        return aluno;
    }
    @Transactional
     public void update(Aluno aluno){
        try{
            String sql;
            sql = "UPDATE pessoa SET id_pessoa = :id_pessoa, nome = :nome, cpf = :cpf, data_nasc = :data_nasc, email = :email, telefone = :telefone, endereco = :endereco, cidade = :cidade, cep = :cep, uf = :uf  WHERE id_pessoa = :id_pessoa";
            Query query =em.createNativeQuery(sql);
            query.setParameter("id_pessoa", aluno.getId());
            query.setParameter("nome", aluno.getNome());
            query.setParameter("cpf", aluno.getCpf());
            query.setParameter("data_nasc", aluno.getDataNasc());
            query.setParameter("email", aluno.getEmail());
            query.setParameter("telefone", aluno.getTelefone());
            query.setParameter("endereco", aluno.getEndereco());
            query.setParameter("cidade", aluno.getCidade());
            query.setParameter("cep", aluno.getCep());
            query.setParameter("uf", aluno.getUf());
            sql= "UPDATE aluno SET id_pessoa = :id_pessoa, prontuario = :prontuario, ano_ingresso = :ano_ingresso, ano_saida = :ano_saida  WHERE id_pessoa = :id_pessoa";
            query =em.createNativeQuery(sql);
            query.setParameter("id_pessoa", aluno.getId());
            query.setParameter("prontuario", aluno.getProntuario());
            query.setParameter("ano_ingresso", aluno.getAno_ingresso());
            query.setParameter("ano_saida", aluno.getAno_saida());
            query.executeUpdate();

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    @Transactional
    public void deleteById(Long id){
        String sql = "DELETE FROM aluno WHERE id_pessoa = :id_pessoa";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id_pessoa", id);
        query.executeUpdate();
    }
    @Transactional
    public Aluno findByCpf(String cpf) {
        try {
            String sql = "SELECT * from aluno INNER JOIN pessoa ON aluno.id_pessoa = pessoa.id_pessoa WHERE pessoa.cpf = :cpf";
            Query query = em.createNativeQuery(sql, Aluno.class);
            query.setParameter("cpf", cpf);
            @SuppressWarnings("unchecked")
            List<Aluno> alunos = query.getResultList();
            return alunos.isEmpty() ? null : alunos.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional
    public Aluno findByCpfUpdate(String cpf, Long id_pessoa) {
        try {
            String sql = "SELECT * from aluno INNER JOIN pessoa ON aluno.id_pessoa = pessoa.id_pessoa WHERE pessoa.cpf = :cpf AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Aluno.class);
            query.setParameter("cpf", cpf);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Aluno> alunos = query.getResultList();
            return alunos.isEmpty() ? null : alunos.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    @Transactional
    public Aluno findByEmail(String email) {
        try {
            String sql = "SELECT * from aluno INNER JOIN pessoa ON aluno.id_pessoa = pessoa.id_pessoa WHERE pessoa.email = :email";
            Query query = em.createNativeQuery(sql, Aluno.class);
            query.setParameter("email", email);
            @SuppressWarnings("unchecked")
            List<Aluno> alunos = query.getResultList();
            return alunos.isEmpty() ? null : alunos.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public Aluno findByEmailUpdate(String email, Long id_pessoa) {
        try {
            String sql = "SELECT * from aluno INNER JOIN pessoa ON aluno.id_pessoa = pessoa.id_pessoa WHERE pessoa.email = :email AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Aluno.class);
            query.setParameter("email", email);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Aluno> alunos = query.getResultList();
            return alunos.isEmpty() ? null : alunos.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public Aluno findByProntuario(String prontuario) {
        try {
            String sql = "SELECT * from aluno INNER JOIN pessoa ON aluno.id_pessoa = pessoa.id_pessoa WHERE aluno.prontuario = :prontuario";
            Query query = em.createNativeQuery(sql, Aluno.class);
            query.setParameter("prontuario", prontuario);
            @SuppressWarnings("unchecked")
            List<Aluno> alunos = query.getResultList();
            return alunos.isEmpty() ? null : alunos.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    public Aluno findByProntuarioUpdate(String prontuario, Long id_pessoa) {
        try {
            String sql = "SELECT * from aluno INNER JOIN pessoa ON aluno.id_pessoa = pessoa.id_pessoa WHERE aluno.prontuario = :prontuario AND pessoa.id_pessoa != :id_pessoa";
            Query query = em.createNativeQuery(sql, Aluno.class);
            query.setParameter("prontuario", prontuario);
            query.setParameter("id_pessoa", id_pessoa);
            @SuppressWarnings("unchecked")
            List<Aluno> alunos = query.getResultList();
            return alunos.isEmpty() ? null : alunos.get(0);
        } catch (Exception e) {
            return null;
        }
    }

}
