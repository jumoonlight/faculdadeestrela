package com.ifsp.anajuliaferreira.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.ifsp.anajuliaferreira.model.Aluno;
import com.ifsp.anajuliaferreira.model.Pessoa;
import com.ifsp.anajuliaferreira.model.Professor;

import java.util.List;

@Repository
public class AlunoRepository {

    @PersistenceContext
    private EntityManager em;

    
    public boolean existeEmail(String email, Long idIgnorar) {
        String sql = "SELECT COUNT(*) FROM pessoa WHERE email = :email";
        if (idIgnorar != null && idIgnorar > 0) {
            sql += " AND id_pessoa != :id";
        }
        Query query = em.createNativeQuery(sql);
        query.setParameter("email", email);
        if (idIgnorar != null && idIgnorar > 0) {
            query.setParameter("id", idIgnorar);
        }
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    public boolean existeCpf(String cpf, Long idIgnorar) {
        String sql = "SELECT COUNT(*) FROM pessoa WHERE cpf = :cpf";
        if (idIgnorar != null && idIgnorar > 0) {
            sql += " AND id_pessoa != :id";
        }
        Query query = em.createNativeQuery(sql);
        query.setParameter("cpf", cpf);
        if (idIgnorar != null && idIgnorar > 0) {
            query.setParameter("id", idIgnorar);
        }
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    public boolean existeProntuario(String prontuario, Long idIgnorar) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE prontuario = :prontuario";
        if (idIgnorar != null && idIgnorar > 0) {
            sql += " AND id_pessoa != :id";
        }
        Query query = em.createNativeQuery(sql);
        query.setParameter("prontuario", prontuario);
        if (idIgnorar != null && idIgnorar > 0) {
            query.setParameter("id", idIgnorar);
        }
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    
    @Transactional
    public boolean insert(Aluno aluno) {
        try {
            
            String sqlPessoa = "INSERT INTO pessoa(nome, cpf, data_nasc, email, telefone, endereco, cidade, cep, uf) VALUES (:nome, :cpf, :data_nasc, :email, :telefone, :endereco, :cidade, :cep, :uf)";
            Query query = em.createNativeQuery(sqlPessoa);
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

            Number idGerado = (Number) em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();

            
            String sqlAluno = "INSERT INTO aluno (id_pessoa, prontuario, nome_mae, nome_pai, contato_responsavel, ano_ingresso, ano_saida, id_curso) VALUES (:id, :prontuario, :nomeMae, :nomePai, :contato, :anoIngresso, :anoSaida, :idCurso)";
            Query qAluno = em.createNativeQuery(sqlAluno);
            qAluno.setParameter("id", idGerado.intValue());
            qAluno.setParameter("prontuario", aluno.getProntuario());
            qAluno.setParameter("nomeMae", aluno.getNomeMae());
            qAluno.setParameter("nomePai", aluno.getNomePai());
            qAluno.setParameter("contato", aluno.getContatoResponsavel());
            qAluno.setParameter("anoIngresso", aluno.getAnoIngresso());
            qAluno.setParameter("anoSaida", aluno.getAnoSaida());
            qAluno.setParameter("idCurso", aluno.getIdCurso());
            qAluno.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean update(Aluno aluno) {
        try {
            String sqlPessoa = "UPDATE pessoa SET id_pessoa = :id_pessoa, nome = :nome, cpf = :cpf, data_nasc = :data_nasc, email = :email, telefone = :telefone, endereco = :endereco, cidade = :cidade, cep = :cep, uf = :uf  WHERE id_pessoa = :id_pessoa";
            Query query =em.createNativeQuery(sqlPessoa);
            query.setParameter("id_pessoa", aluno.getIdPessoa());
            query.setParameter("nome", aluno.getNome());
            query.setParameter("cpf", aluno.getCpf());
            query.setParameter("data_nasc", aluno.getDataNasc());
            query.setParameter("email", aluno.getEmail());
            query.setParameter("telefone", aluno.getTelefone());
            query.setParameter("endereco", aluno.getEndereco());
            query.setParameter("cidade", aluno.getCidade());
            query.setParameter("cep", aluno.getCep());
            query.setParameter("uf", aluno.getUf());

            String sqlAluno = "UPDATE aluno SET prontuario = :prontuario, nome_mae = :nomeMae, nome_pai = :nomePai, contato_responsavel = :contato, ano_ingresso = :anoIngresso, ano_saida = :anoSaida, id_curso = :idCurso WHERE id_pessoa = :id";
            Query qAluno = em.createNativeQuery(sqlAluno);
            qAluno.setParameter("prontuario", aluno.getProntuario());
            qAluno.setParameter("nomeMae", aluno.getNomeMae());
            qAluno.setParameter("nomePai", aluno.getNomePai());
            qAluno.setParameter("contato", aluno.getContatoResponsavel());
            qAluno.setParameter("anoIngresso", aluno.getAnoIngresso());
            qAluno.setParameter("anoSaida", aluno.getAnoSaida());
            qAluno.setParameter("idCurso", aluno.getIdCurso());
            qAluno.setParameter("id", aluno.getIdPessoa());
            qAluno.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public void delete(long id) {
        Aluno aluno = em.find(Aluno.class, id);
        if (aluno != null) {
            em.remove(aluno);
        }
    }

    public Aluno findByID(Long id) {
         Aluno aluno = em.find(Aluno.class, id);
         return aluno;
    }

    @Transactional
    public Pessoa findByCpf(String cpf) {
        try {
            String sql = "SELECT * from pessoa  WHERE cpf = :cpf";
            Query query = em.createNativeQuery(sql, Pessoa.class);
            query.setParameter("cpf", cpf);
            @SuppressWarnings("unchecked")
            List<Pessoa> pessoas= query.getResultList();
            return pessoas.isEmpty() ? null : pessoas.get(0);
        } catch (Exception e) {
            return null;
        }
    }

 @Transactional
    public Pessoa findByEmail(String email) {
        try {
            String sql = "SELECT * from pessoa  WHERE email = :email";
            Query query = em.createNativeQuery(sql, Pessoa.class);
            query.setParameter("email", email);
            @SuppressWarnings("unchecked")
            List<Pessoa> pessoas= query.getResultList();
            return pessoas.isEmpty() ? null : pessoas.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public Aluno findByProntuario(String prontuario) {
        try {
            String sql = "SELECT * from aluno WHERE prontuario = :prontuario";
            Query query = em.createNativeQuery(sql, Pessoa.class);
            query.setParameter("prontuario", prontuario);
            @SuppressWarnings("unchecked")
            List<Aluno> alunos= query.getResultList();
            return alunos.isEmpty() ? null : alunos.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Aluno> findAll() {
        return em.createQuery("SELECT a FROM Aluno a ORDER BY a.prontuario", Aluno.class).getResultList();
    }

    public List<Aluno> findByCurso(int idCurso) {
        return em.createQuery("SELECT a FROM Aluno a WHERE a.curso.idCurso = :idCurso ORDER BY a.prontuario", Aluno.class)
                 .setParameter("idCurso", idCurso)
                 .getResultList();
    }


}

//comando = "INSERT INTO pessoa(nome, cpf, data_nasc, email, telefone, endereco, cidade, cep, uf) VALUES (:nome, :cpf, :data_nasc, :email, :telefone, :endereco, :cidade, :cep, :uf)";
            //query = em.createNativeQuery(comando);
            //query.setParameter("nome", aluno.getNome());
            //query.setParameter("cpf", aluno.getCpf());
            //query.setParameter("data_nasc", aluno.getDataNasc());
            //query.setParameter("email", aluno.getEmail());
            //query.setParameter("telefone", aluno.getTelefone());
            //query.setParameter("endereco", aluno.getEndereco());
            //query.setParameter("cidade", aluno.getCidade());
            //query.setParameter("cep", aluno.getCep());
            //query.setParameter("uf", aluno.getUf());
            //query.executeUpdate();"

             //sql = "UPDATE pessoa SET id_pessoa = :id_pessoa, nome = :nome, cpf = :cpf, data_nasc = :data_nasc, email = :email, telefone = :telefone, endereco = :endereco, cidade = :cidade, cep = :cep, uf = :uf  WHERE id_pessoa = :id_pessoa";
            //Query query =em.createNativeQuery(sql);
            //query.setParameter("id_pessoa", aluno.getId());
            //query.setParameter("nome", aluno.getNome());
            //query.setParameter("cpf", aluno.getCpf());
            //query.setParameter("data_nasc", aluno.getDataNasc());
            //query.setParameter("email", aluno.getEmail());
            //query.setParameter("telefone", aluno.getTelefone());
            //query.setParameter("endereco", aluno.getEndereco());
            //query.setParameter("cidade", aluno.getCidade());
            //query.setParameter("cep", aluno.getCep());
            //query.setParameter("uf", aluno.getUf());
          