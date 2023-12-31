package model;

import conexao.ConexaoBancoDeDados;
import aplicacao.Especialidade;
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
 * A classe EspecialidadeDAO representa um Data Access Object para a entidade Especialidade,
 * fornecendo métodos para acessar e manipular dados no banco de dados relacionados
 * a especialidades médicas.
 */
public class EspecialidadeDAO {
   
    private Connection conn;
    
    /**
    * Construtor para testes
    */
    public EspecialidadeDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão.
    */
    public EspecialidadeDAO() {
        
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
    * Cria uma nova especialidade no banco de dados.
    *
    * @param novaEspecialidade a especialidade a ser criada.
    */    
    public void createEspecialidade(Especialidade novaEspecialidade){
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO especialidade "
                    + "(descricao) VALUES ( '" + novaEspecialidade.getDescricao() + "')");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }

    /**
    * Retorna uma lista contendo todas as especialidades do banco de dados.
    *
    * @return uma lista de especialidades.
    */    
    public List<Especialidade> getEspecialidades(){
    
        List<Especialidade> especialidades = new ArrayList<>();
       
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM especialidade");
            
            while(resultSet.next()){
                Especialidade espec = new Especialidade();
                espec.setId(resultSet.getInt("id"));
                espec.setDescricao(resultSet.getString("descricao"));
                especialidades.add(espec);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return especialidades;
    }

    /**
    * Retorna uma especialidade com base no seu ID.
    *
    * @param idEspecialidade o ID da especialidade.
    * @return a especialidade com o ID especificado.
    */    
    public Especialidade getEspecialidade(int idEspecialidade){
        
        Especialidade espec = new Especialidade();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM especialidade "
                    + "WHERE especialidade.id = '" + idEspecialidade + "'");
            
            if (resultSet.next()) {
                espec.setId(resultSet.getInt("id"));
                espec.setDescricao(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return espec;
    }
 
    /**
    * Atualiza os dados de uma especialidade existente no banco de dados.
    *
    * @param idEspecialidade o ID da especialidade a ser atualizada.
    * @param novaEspecialidade os novos dados da especialidade.
    */
    public void updateEspecialidade(int idEspecialidade, Especialidade novaEspecialidade){
    
       try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE especialidade SET descricao='" + novaEspecialidade.getDescricao() + "' "
                    + Constantes.WHEREESPECIALIDADEID + idEspecialidade + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }

    /**
    * Exclui uma especialidade com base no ID da especialidade fornecido.
    *
    * @param idEspecialidade O ID da especialidade a ser excluída.
    */
    public void deleteEspecialidade(int idEspecialidade){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM especialidade WHERE especialidade.id=" + idEspecialidade + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }

    /**
    * Retorna uma lista contendo as listas de IDs das chaves estrangeiras dos exames, consultas e médicos associados a uma especialidade específica que podem ser excluídos.
    * @param idEspecialidade o ID da especialidade
    * @return uma lista contendo as listas de IDs das chaves estrangeiras dos exames, consultas e médicos a serem excluídos
    */    
    public List<List<Integer>> getIdDeleteEspecialidade(int idEspecialidade){
        
        List<List<Integer>> idCompilado = new ArrayList<>();
        
        List<Integer> idMedicos = new ArrayList<>();
        List<Integer> idConsultas = new ArrayList<>();
        List<Integer> idExames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            Constantes.FROMESPECIALIDADE +
            "INNER JOIN consulta ON consulta.idmedico=medico.id " +
            "INNER JOIN exames ON exames.idconsulta=consulta.id " +
            Constantes.WHEREESPECIALIDADEID + idEspecialidade + "");
            
            while(resultSet.next()) {
                idExames.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            Constantes.FROMESPECIALIDADE +
            "INNER JOIN consulta ON consulta.idmedico=medico.id " +
            "WHERE especialidade.id=" + idEspecialidade + "");
            
            while(resultSet.next()) {
                idConsultas.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT medico.id " +
            "FROM especialidade INNER JOIN medico ON especialidade.id=medico.idespecialidade " +
            "WHERE especialidade.id=" + idEspecialidade + "");
            
            while(resultSet.next()) {
                idMedicos.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        
        idCompilado.add(idExames);
        idCompilado.add(idConsultas);
        idCompilado.add(idMedicos);
        
        return idCompilado;
    }

    public Connection getConnection(){
        return this.conn;
    }
}
