package model;

import conexao.ConexaoBancoDeDados;
import aplicacao.Exame;
import utils.Constantes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A classe ExameDAO representa um Data Access Object para a entidade Exame,
 * fornecendo métodos para acessar e manipular dados no banco de dados relacionados
 * a exames médicos.
 */
public class ExameDAO {
   
    private Connection conn;
    
    /**
    * Construtor para testes
    */
    public ExameDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão.
    */
    public ExameDAO() {
    }
    
    /**
    * Método que cria uma nova conexão com o banco.
    */
    public void createConnection() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, "Nao foi possivel conectar");
        }
    }
 
    /**
   * Cria um novo exame no banco de dados.
   *
   * @param novoExame o exame a ser criado.
   */
    public void createExame(Exame novoExame){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO tipoexame "
                    + "(descricao) VALUES ( '" + novoExame.getDescricao() + "')");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    
    /**
   * Retorna uma lista contendo todos os exames do banco de dados.
   *
   * @return uma lista de exames.
   */    
    public List<Exame> getExames(){
    
        List<Exame> exames = new ArrayList<>();
       
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoexame");
            
            while(resultSet.next()){
                Exame exame = new Exame();
                exame.setId(resultSet.getInt(Constantes.ID));
                exame.setDescricao(resultSet.getString(Constantes.DESCRICAO));
                exames.add(exame);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return exames;
    }
    /**
    * Retorna um exame com base no seu ID.
    *
    * @param idExame o ID do exame.
    * @return o exame com o ID especificado.
    */   
    public Exame getExame(int idExame){
    
        Exame exame = new Exame();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoexame "
                    + "WHERE tipoexame.id=" + idExame + "");
            
            if (resultSet.next()) {
                exame.setId(resultSet.getInt("id"));
                exame.setDescricao(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return exame;
    }
    /**
    * Retorna uma lista contendo as descrições dos exames realizados em uma determinada consulta.
    *
    * @param idConsulta o ID da consulta.
    * @param listaExames a lista de descrições de exames existente.
    * @return uma lista atualizada contendo as descrições dos exames realizados na consulta.
     */    
    public List<String> getExamesDaConsulta(int idConsulta, List<String> listaExames){
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT tipoexame.descricao " +
                "FROM consulta INNER JOIN exames ON consulta.id = exames.idconsulta " +
                "INNER JOIN tipoexame ON exames.idtipoexame = tipoexame.id " +
                "WHERE consulta.id='" + idConsulta + "'");
            
            while (resultSet.next()) {
                listaExames.add(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return listaExames;
    }
    /**
    * Atualiza os dados de um exame existente no banco de dados.
    *
    * @param idExame o ID do exame a ser atualizado.
    * @param novoExame os novos dados do exame.
    */    
    public void updateExame(int idExame, Exame novoExame){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE tipoexame SET descricao='" + novoExame.getDescricao() + "' WHERE tipoexame.id=" + idExame + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    
    /**
    * Exclui um exame com base no ID do exame fornecido.
    *
    * @param idExame O ID do exame a ser excluído.
    */
    public void deleteExame(int idExame){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM exames WHERE exames.id=" + idExame + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    
    /**
    * Exclui um tipo de exame com base no ID do exame fornecido.
    *
    * @param idExame O ID do tipo de exame a ser excluído.
    */
    public void deleteTipoExame(int idExame){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM tipoexame WHERE tipoexame.id=" + idExame + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    /**
     * Retorna uma lista contendo os IDs das chaves estrangeiras associados a um tipo de exame específico.
     *
     * @param idExame o ID do tipo de exame
     * @return uma lista contendo os IDs dos exames a serem excluídos
     */    
    public List<Integer> getIdDeleteExame(int idExame){
        
        List<Integer> idExames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM exames WHERE exames.idtipoexame=" + idExame + "");
            
            while(resultSet.next()) {
                idExames.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return idExames;
    }
}
