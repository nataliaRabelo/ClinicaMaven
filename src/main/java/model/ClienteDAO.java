package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import conexao.conexao_bancodedados;
import aplicacao.Cliente;

@WebServlet(name = "ClienteDAO", urlPatterns = {"/ClienteDAO"})
public class ClienteDAO extends HttpServlet {
    
    private Connection conn;

    public ClienteDAO() {
        try {
            conn = conexao_bancodedados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    } 
    
    public Cliente login(String cpf, String senha){
        
        Cliente paciente = new Cliente();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente" + 
                " WHERE cpf = '" + String.valueOf(cpf) + "' AND senha = '" + String.valueOf(senha) + "'");
            
            if (resultSet.next()) {
                paciente.setId(resultSet.getInt("id"));
                paciente.setNome(resultSet.getString("nome"));
                paciente.setCpf(resultSet.getString("cpf"));
                paciente.setSenha(resultSet.getString("senha"));
                paciente.setAutorizado(resultSet.getString("autorizado").charAt(0));
                paciente.setIdtipoplano(resultSet.getInt("idtipoplano"));
            } 
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return paciente;
    }
    
    public boolean jaCadastrado(String cpf_paciente){
    
        boolean resp = false;
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente "
                    + "WHERE paciente.cpf=" + cpf_paciente + "");
            
            if (resultSet.next() == true) {
                resp = true;
            }
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return resp;
    }
    
    public void create_paciente(Cliente novo_paciente){
 
        try {
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO paciente"
                    + " (nome, cpf, senha, autorizado, idtipoplano) VALUES ( '" +
                    novo_paciente.getNome() + "','" + novo_paciente.getCpf() + "','" +
                    novo_paciente.getSenha() + "','" + novo_paciente.getAutorizado() +
                    "','" + novo_paciente.getIdtipoplano() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
        
    public String get_nomePaciente(int id_paciente){
        
        String nome = null;
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nome FROM paciente " +
                "WHERE paciente.id='" + id_paciente + "'");
            
            if (resultSet.next()) {
                nome = resultSet.getString("nome");
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return nome;
    }
    
    public ArrayList<Cliente> get_pacientes(){
    
        ArrayList<Cliente> pacientes = new ArrayList<Cliente>();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente");
            
            while (resultSet.next()) {
                Cliente paciente = new Cliente();
                paciente.setId(resultSet.getInt("id"));
                paciente.setNome(resultSet.getString("nome"));
                paciente.setCpf(resultSet.getString("cpf"));
                paciente.setSenha(resultSet.getString("senha"));
                paciente.setAutorizado(resultSet.getString("autorizado").charAt(0));
                paciente.setIdtipoplano(resultSet.getInt("idtipoplano"));
                pacientes.add(paciente);
            }
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return pacientes;
    }
    
    public Cliente get_paciente(int id_paciente){
    
        Cliente paciente = new Cliente();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente "
                    + "WHERE paciente.id = '" + id_paciente + "'");
            
            if (resultSet.next()) {
                paciente.setId(resultSet.getInt("id"));
                paciente.setNome(resultSet.getString("nome"));
                paciente.setCpf(resultSet.getString("cpf"));
                paciente.setSenha(resultSet.getString("senha"));
                paciente.setAutorizado(resultSet.getString("autorizado").charAt(0));
                paciente.setIdtipoplano(resultSet.getInt("idtipoplano"));
            }
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return paciente;
    }
    
    public void update_paciente(int id_paciente, Cliente paciente){
        
        try {
            Statement statement = conn.createStatement();
            statement.execute("UPDATE paciente SET nome='" + paciente.getNome() + "' , cpf='" +
                    paciente.getCpf() + "' , senha='" + paciente.getSenha() +
                    "' , autorizado='" + paciente.getAutorizado() +
                    "' , idtipoplano='" + paciente.getIdtipoplano() + "' WHERE paciente.id='" + id_paciente + "'");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void delete_paciente(int id_paciente){
        
        try{
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM paciente WHERE paciente.id=" + id_paciente + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<ArrayList<Integer>> get_idDeletePaciente(int id_paciente){
    
        ArrayList<ArrayList<Integer>> id_compilado = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> id_consultas = new ArrayList<Integer>();
        ArrayList<Integer> id_exames = new ArrayList<Integer>();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM paciente INNER JOIN consulta ON paciente.id=consulta.idpaciente " +
            "INNER JOIN exames ON consulta.id=exames.idconsulta " +
            "WHERE paciente.id=" + id_paciente + "");
            
            while(resultSet.next()) {
                id_exames.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            "FROM paciente INNER JOIN consulta ON paciente.id=consulta.idpaciente " +
            "WHERE paciente.id=" + id_paciente + "");
            
            while(resultSet.next()) {
                id_consultas.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        
        id_compilado.add(id_exames);
        id_compilado.add(id_consultas);
        
        return id_compilado;
    }
}
