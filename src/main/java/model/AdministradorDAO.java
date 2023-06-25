package model;

import conexao.ConexaoBancoDeDados;
import aplicacao.Administrador;
import utils.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
A classe AdministradorDAO é responsável por realizar operações de acesso a dados
relacionadas aos administradores no banco de dados.
*/

public class AdministradorDAO {
    
    private Connection conn;
    
    /**
    * Construtor para testes
    */
    public AdministradorDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão que cria uma nova conexão com o banco.
    */
    public AdministradorDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, "Nao foi possivel conectar");
        }
    } 
    /**
     * Realiza o login de um administrador no sistema.
     * @param cpf    O CPF do administrador.
     * @param senha  A senha do administrador.
     * @return O objeto Administrador correspondente ao login realizado.
     */
    public Administrador login(String cpf, String senha) {
        
        Administrador administrador = new Administrador();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador" + 
                " WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");
            
            if (resultSet.next()) {
                administrador.setId(resultSet.getInt(Constantes.ID));
                administrador.setNome(resultSet.getString(Constantes.NOME));
                administrador.setCpf(resultSet.getString(Constantes.CPF));
                administrador.setSenha(resultSet.getString(Constantes.SENHA));
            } 
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return administrador;
    }

    /**
     * Cria um novo administrador no banco de dados.
     * @param administrador O objeto Administrador a ser criado.
     */    
    public void createAdministrador(Administrador administrador){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO administrador (nome,cpf,senha) VALUES ( '" +
                    administrador.getNome() + "','" + administrador.getCpf() + "','" + administrador.getSenha() + "')");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    /**
     * Retorna uma lista com todos os administradores cadastrados no banco de dados.
     * @return Uma lista de objetos Administrador.
     */    
    public List<Administrador> getAdministradores(){
    
        List<Administrador> administradores = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador");
            
            while (resultSet.next()) {
                Administrador adm = new Administrador();
                adm.setId(resultSet.getInt(Constantes.ID));
                adm.setNome(resultSet.getString(Constantes.NOME));
                adm.setCpf(resultSet.getString(Constantes.CPF));
                adm.setSenha(resultSet.getString(Constantes.SENHA));
                administradores.add(adm);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return administradores;
    }
    
    /**
     * Retorna um objeto Administrador com base no ID do administrador fornecido.
     * @param idAdministrador O ID do administrador a ser recuperado.
     * @return Um objeto Administrador com as informações do administrador correspondente ao ID, ou um objeto vazio se nenhum administrador for encontrado.
     */
    public Administrador getAdministrador(int idAdministrador){
    
        Administrador adm = new Administrador();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador "
                    + "WHERE administrador.id = '" + idAdministrador + "'");
            
            if (resultSet.next()) {
                adm.setId(resultSet.getInt(Constantes.ID));
                adm.setNome(resultSet.getString(Constantes.NOME));
                adm.setCpf(resultSet.getString(Constantes.CPF));
                adm.setSenha(resultSet.getString(Constantes.SENHA));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return adm;
    }
    /**
    Atualiza os dados de um administrador no banco de dados.
    @param idAdministrador O identificador do administrador a ser atualizado.
    @param administrador O objeto Administrador com os novos dados a serem atualizados.
    */    
    public void updateAdministrador(int idAdministrador, Administrador administrador){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE administrador SET nome='" + administrador.getNome() + "',cpf='" + administrador.getCpf() +
                    "',senha='" + administrador.getSenha() + "'  WHERE administrador.id=" + idAdministrador + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    /**
    * Exclui um administrador com base no ID do administrador fornecido.
    * @param idAdministrador O ID do administrador a ser excluído.
    */
    public void deleteAdministrador(int idAdministrador){
    
        try(Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM administrador WHERE administrador.id=" + idAdministrador + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
}
