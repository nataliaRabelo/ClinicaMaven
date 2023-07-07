package model;

import conexao.ConexaoBancoDeDados;
import aplicacao.Cliente;
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
Classe responsável por realizar operações relacionadas aos objetos Cliente no banco de dados.
*/
public class ClienteDAO {
    
    private Connection conn;
    
    /**
    * Construtor para testes
    */
    public ClienteDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão.
    */
    public ClienteDAO() {
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
    Realiza o login de um cliente no sistema.
    @param cpf O CPF do cliente.
    @param senha A senha do cliente.
    @return O objeto Cliente com os dados do cliente logado.
    */    
    public Cliente login(String cpf, String senha){
        
        Cliente paciente = new Cliente();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente" + 
                " WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");
            
            if (resultSet.next()) {
                paciente.setId(resultSet.getInt(Constantes.ID));
                paciente.setNome(resultSet.getString(Constantes.NOME));
                paciente.setCpf(resultSet.getString(Constantes.CPF));
                paciente.setSenha(resultSet.getString(Constantes.SENHA));
                paciente.setAutorizado(resultSet.getString(Constantes.AUTORIZADO).charAt(0));
                paciente.setIdtipoplano(resultSet.getInt(Constantes.IDTIPOPLANO));
            } 
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return paciente;
    }
 
    /**
    Verifica se um determinado CPF de paciente já está cadastrado no banco de dados.
    @param cpfPaciente O CPF do paciente a ser verificado.
    @return true se o CPF já estiver cadastrado, false caso contrário.
    */    
    public boolean jaCadastrado(String cpfPaciente){
    
        boolean resp = false;
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente "
                    + "WHERE paciente.cpf=" + cpfPaciente + "");
            
            if (resultSet.next()) {
                resp = true;
            }
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return resp;
    }

    /**
    Cria um novo paciente no banco de dados.
    @param novoPaciente O objeto Cliente com os dados do novo paciente.
    */    
    public void createPaciente(Cliente novoPaciente){
 
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO paciente"
                    + " (nome, cpf, senha, autorizado, idtipoplano) VALUES ( '" +
                    novoPaciente.getNome() + "','" + novoPaciente.getCpf() + "','" +
                    novoPaciente.getSenha() + "','" + novoPaciente.getAutorizado() +
                    "','" + novoPaciente.getIdtipoplano() + "')");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public String getIdPlano(int idPaciente){
        
        String nome = null;
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT idtipoplano FROM paciente " +
                "WHERE paciente.id='" + idPaciente + "'");
            
            if (resultSet.next()) {
                nome = resultSet.getString(Constantes.IDTIPOPLANO);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return nome;
    }
    
    /**
    * Retorna o nome de um paciente com base no ID do paciente fornecido.
    *
    * @param idPaciente O ID do paciente.
    * @return O nome do paciente correspondente ao ID, ou null se nenhum paciente for encontrado.
    */
    public String getNomePaciente(int idPaciente){
        
        String nome = null;
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT nome FROM paciente " +
                "WHERE paciente.id='" + idPaciente + "'");
            
            if (resultSet.next()) {
                nome = resultSet.getString(Constantes.NOME);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return nome;
    }
    
    public String getAutorizado(int idPaciente){
        
        String autorizado = null;
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT autorizado FROM paciente " +
                "WHERE paciente.id='" + idPaciente + "'");
            
            if (resultSet.next()) {
                autorizado = resultSet.getString(Constantes.AUTORIZADO);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return autorizado;
    }
    
    public String getCpf(int idPaciente){
        
        String cpf = null;
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT cpf FROM paciente " +
                "WHERE paciente.id='" + idPaciente + "'");
            
            if (resultSet.next()) {
                cpf = resultSet.getString(Constantes.CPF);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return cpf;
    }
    
    public String getSenha(int idPaciente){
        
        String senha = null;
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT senha FROM paciente " +
                "WHERE paciente.id='" + idPaciente + "'");
            
            if (resultSet.next()) {
                senha = resultSet.getString(Constantes.SENHA);
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return senha;
    }

    /**
    * Retorna uma lista de objetos Cliente contendo todos os pacientes registrados.
    *
    * @return Uma lista de objetos Cliente representando os pacientes registrados.
    */
    public List<Cliente> getPacientes(){
    
        List<Cliente> pacientes = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente");
            
            while (resultSet.next()) {
                Cliente paciente = new Cliente();
                paciente.setId(resultSet.getInt(Constantes.ID));
                paciente.setNome(resultSet.getString(Constantes.NOME));
                paciente.setCpf(resultSet.getString(Constantes.CPF));
                paciente.setSenha(resultSet.getString(Constantes.SENHA));
                paciente.setAutorizado(resultSet.getString(Constantes.AUTORIZADO).charAt(0));
                paciente.setIdtipoplano(resultSet.getInt(Constantes.IDTIPOPLANO));
                pacientes.add(paciente);
            }
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return pacientes;
    }
    
    /**
     * Retorna um objeto Cliente contendo as informações de um paciente com base no ID do paciente fornecido.
     * @param idPaciente O ID do paciente a ser recuperado.
     * @return Um objeto Cliente com as informações do paciente correspondente ao ID, ou um objeto vazio se nenhum paciente for encontrado.
     */
    public Cliente getPaciente(int idPaciente){
    
        Cliente paciente = new Cliente();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente "
                    + "WHERE paciente.id = '" + idPaciente + "'");
            
            if (resultSet.next()) {
                paciente.setId(resultSet.getInt(Constantes.ID));
                paciente.setNome(resultSet.getString(Constantes.NOME));
                paciente.setCpf(resultSet.getString(Constantes.CPF));
                paciente.setSenha(resultSet.getString(Constantes.SENHA));
                paciente.setAutorizado(resultSet.getString(Constantes.AUTORIZADO).charAt(0));
                paciente.setIdtipoplano(resultSet.getInt(Constantes.IDTIPOPLANO));
            }
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        return paciente;
    }
    /**
    * Atualiza as informações de um paciente com base no ID do paciente fornecido.
    *
    * @param idPaciente O ID do paciente a ser atualizado.
    * @param paciente O objeto Cliente contendo as novas informações do paciente.
    */
    public void updatePaciente(int idPaciente, Cliente paciente){
        
        try(Statement statement = conn.createStatement()){
            statement.execute("UPDATE paciente SET nome='" + paciente.getNome() + "' , cpf='" +
                    paciente.getCpf() + "' , senha='" + paciente.getSenha() +
                    "' , autorizado='" + paciente.getAutorizado() +
                    "' , idtipoplano='" + paciente.getIdtipoplano() + "' WHERE paciente.id='" + idPaciente + "'");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    /**
    * Exclui um paciente com base no ID do paciente fornecido.
    *
    * @param idPaciente O ID do paciente a ser excluído.
    */
    public void deletePaciente(int idPaciente){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM paciente WHERE paciente.id=" + idPaciente + "");
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
    }
    /**
    * Retorna uma lista contendo as listas de IDs das chaves estrangeiras dos exames e consultas associados a um paciente específico.
    * @param idPaciente o ID do paciente
    * @return uma lista contendo as listas de IDs das chaves estrangeiras dos exames e consultas a serem excluídas
    */    
    public List<List<Integer>> getIdDeletePaciente(int idPaciente){
    
        List<List<Integer>> idCompilado = new ArrayList<>();
        List<Integer> idConsultas = new ArrayList<>();
        List<Integer> idExames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM paciente INNER JOIN consulta ON paciente.id=consulta.idpaciente " +
            "INNER JOIN exames ON consulta.id=exames.idconsulta " +
            "WHERE paciente.id=" + idPaciente + "");
            
            while(resultSet.next()) {
                idExames.add(resultSet.getInt(Constantes.ID));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            "FROM paciente INNER JOIN consulta ON paciente.id=consulta.idpaciente " +
            "WHERE paciente.id=" + idPaciente + "");
            
            while(resultSet.next()) {
                idConsultas.add(resultSet.getInt(Constantes.ID));
            }
            
        } catch(SQLException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, Constantes.SQLERROR + e.getMessage());
        }
        
        idCompilado.add(idExames);
        idCompilado.add(idConsultas);
        
        return idCompilado;
    }
}
