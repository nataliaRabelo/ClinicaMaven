package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoBancoDeDados;
import aplicacao.Cliente;
import utils.Constantes;

public class ClienteDAO {
    
    private Connection conn;
    
    /**
    * Construtor para testes
    */
    public ClienteDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão que cria uma nova conexão com o banco.
    */
    public ClienteDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    } 
    
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
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return paciente;
    }
    
    public boolean jaCadastrado(String cpfPaciente){
    
        boolean resp = false;
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente "
                    + "WHERE paciente.cpf=" + cpfPaciente + "");
            
            if (resultSet.next()) {
                resp = true;
            }
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return resp;
    }
    
    public void createPaciente(Cliente novoPaciente){
 
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO paciente"
                    + " (nome, cpf, senha, autorizado, idtipoplano) VALUES ( '" +
                    novoPaciente.getNome() + "','" + novoPaciente.getCpf() + "','" +
                    novoPaciente.getSenha() + "','" + novoPaciente.getAutorizado() +
                    "','" + novoPaciente.getIdtipoplano() + "')");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
        
    public String getNomePaciente(int idPaciente){
        
        String nome = null;
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT nome FROM paciente " +
                "WHERE paciente.id='" + idPaciente + "'");
            
            if (resultSet.next()) {
                nome = resultSet.getString(Constantes.NOME);
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return nome;
    }
    
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
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return pacientes;
    }
    
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
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return paciente;
    }
    
    public void updatePaciente(int idPaciente, Cliente paciente){
        
        try(Statement statement = conn.createStatement()){
            statement.execute("UPDATE paciente SET nome='" + paciente.getNome() + "' , cpf='" +
                    paciente.getCpf() + "' , senha='" + paciente.getSenha() +
                    "' , autorizado='" + paciente.getAutorizado() +
                    "' , idtipoplano='" + paciente.getIdtipoplano() + "' WHERE paciente.id='" + idPaciente + "'");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public void deletePaciente(int idPaciente){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM paciente WHERE paciente.id=" + idPaciente + "");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
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
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        
        idCompilado.add(idExames);
        idCompilado.add(idConsultas);
        
        return idCompilado;
    }
}
