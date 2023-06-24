package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import conexao.ConexaoBancoDeDados;
import aplicacao.Consulta;
import utils.Constantes;


/**
 * A classe ConsultaDAO representa um Data Access Object para a entidade Consulta,
 * fornecendo métodos para acessar e manipular dados no banco de dados relacionados
 * a consultas médicas.
 */
public class ConsultaDAO {
   
    private Connection conn;
    
    /**
    * Construtor para testes
    */
    public ConsultaDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão que cria uma nova conexão com o banco.
    */
    public ConsultaDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, "Nao foi possivel conectar");
        }
    }

    /**
    * Cria uma nova consulta no banco de dados.
    *
    * @param novaConsulta a consulta a ser criada.
    */    
    public void createConsulta(Consulta novaConsulta){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO consulta"
                    + " (data,descricao,realizada,idmedico,idpaciente) VALUES ( '" +
                    novaConsulta.getData() + "','" + novaConsulta.getDescricao() + "','" +
                    novaConsulta.getRealizada() + "','" + novaConsulta.getIdmedico() +
                    "','" + novaConsulta.getIdpaciente() + "')");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    
    /**
    * Retorna uma lista de consultas associadas a um determinado paciente.
    *
    * @param idPaciente o ID do paciente.
    * @return uma lista de consultas do paciente.
    */    
    public List<Consulta> getConsultas(int idPaciente){
        
        List<Consulta> listaConsultas = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta" + 
                " WHERE idpaciente = '" + idPaciente + "'");
            
            while (resultSet.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(resultSet.getInt(Constantes.ID));
                consulta.setData(resultSet.getString(Constantes.DATA));
                consulta.setDescricao(resultSet.getString(Constantes.DESCRICAO));
                consulta.setRealizada(resultSet.getString(Constantes.REALIZADA).charAt(0));
                consulta.setIdmedico(resultSet.getInt(Constantes.IDMEDICO));
                consulta.setIdpaciente(resultSet.getInt(Constantes.IDPACIENTE));
                listaConsultas.add(consulta);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return listaConsultas;
    }
    
    /**
     * Retorna uma consulta com base no seu ID.
     *
     * @param idConsulta o ID da consulta.
     * @return a consulta com o ID especificado.
     */    
    public Consulta getConsulta(int idConsulta){
    
        Consulta consulta = new Consulta();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta "
                    + "WHERE consulta.id = '" + idConsulta + "'");
            
            if (resultSet.next()) {
                consulta.setId(resultSet.getInt(Constantes.ID));
                consulta.setData(resultSet.getString(Constantes.DATA));
                consulta.setDescricao(resultSet.getString(Constantes.DESCRICAO));
                consulta.setRealizada(resultSet.getString(Constantes.REALIZADA).charAt(0));
                consulta.setIdmedico(resultSet.getInt(Constantes.IDMEDICO));
                consulta.setIdpaciente(resultSet.getInt(Constantes.IDPACIENTE));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return consulta;
    }

    /**
    * Retorna uma lista contendo o nome do médico e a descrição da especialidade
    * associados a uma determinada consulta.
    *
    * @param idConsulta o ID da consulta.
    * @param medicoDescricao uma lista para armazenar o nome do médico e a descrição da especialidade.
    * @return a lista contendo o nome do médico e a descrição da especialidade.
    */    
    public List<Object> getMedicoEspecialidade(int idConsulta, List<Object> medicoDescricao){
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT medico.nome, especialidade.descricao " +
                    "FROM medico INNER JOIN consulta ON medico.id = consulta.idmedico INNER JOIN especialidade " +
                    "ON medico.idespecialidade = especialidade.id WHERE consulta.id = '" + idConsulta + "'");
            
            while(resultSet.next()) {
                medicoDescricao.add(resultSet.getString("nome"));
                medicoDescricao.add(resultSet.getString("descricao"));
            }  
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return medicoDescricao;
    }
    
    /**
   * Retorna uma lista contendo as descrições e IDs dos médicos que estão associados
   * a especialidades disponíveis.
   *
   * @return uma lista contendo as descrições e IDs dos médicos.
   */    
    public List<Object> getProcedimentosDisponiveis(){
    
        List<Object> medEspecs = new ArrayList<>();
    
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT esp.descricao, med.nome, med.id "
                    + "FROM especialidade AS esp INNER JOIN medico As med "
                    + "ON esp.id = med.idespecialidade AND med.autorizado='S'");
            
            while(resultSet.next()){
                medEspecs.add(resultSet.getString("descricao"));
                medEspecs.add(resultSet.getString("nome"));
                medEspecs.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return medEspecs;
    }

    /**
    * Atualiza os dados de uma consulta existente no banco de dados.
    *
    * @param idConsulta o ID da consulta a ser atualizada.
    * @param novaConsulta os novos dados da consulta.
    */     
    public void updateConsulta(int idConsulta, Consulta novaConsulta){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE consulta SET data='" + novaConsulta.getData() + "' , descricao='" +
                    novaConsulta.getDescricao() + "' , realizada='" + novaConsulta.getRealizada() +
                    "' , idmedico='" + novaConsulta.getIdmedico() + "' , idpaciente='" + novaConsulta.getIdpaciente() + 
                    "' WHERE consulta.id='" + idConsulta + "'");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    
    /**
    * Exclui uma consulta com base no ID da consulta fornecido.
    *
    * @param idConsulta O ID da consulta a ser excluída.
    */
    public void deleteConsulta(int idConsulta){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM consulta WHERE consulta.id=" + idConsulta + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
}
