package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import conexao.ConexaoBancoDeDados;
import aplicacao.Administrador;

public class AdministradorDAO {
    
    private Connection conn;

    public AdministradorDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    } 

    public Administrador login(String cpf, String senha) {
        
        Administrador administrador = new Administrador();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador" + 
                " WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");
            
            if (resultSet.next()) {
                administrador.setId(resultSet.getInt("id"));
                administrador.setNome(resultSet.getString("nome"));
                administrador.setCpf(resultSet.getString("cpf"));
                administrador.setSenha(resultSet.getString("senha"));
            } 
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return administrador;
    }
    
    public void createAdministrador(Administrador administrador){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO administrador (nome,cpf,senha) VALUES ( '" +
                    administrador.getNome() + "','" + administrador.getCpf() + "','" + administrador.getSenha() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Administrador> getAdministradores(){
    
        ArrayList<Administrador> administradores = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador");
            
            while (resultSet.next()) {
                Administrador adm = new Administrador();
                adm.setId(resultSet.getInt("id"));
                adm.setNome(resultSet.getString("nome"));
                adm.setCpf(resultSet.getString("cpf"));
                adm.setSenha(resultSet.getString("senha"));
                administradores.add(adm);
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return administradores;
    }
    
    public Administrador getAdministrador(int idAdministrador){
    
        Administrador adm = new Administrador();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador "
                    + "WHERE administrador.id = '" + idAdministrador + "'");
            
            if (resultSet.next()) {
                adm.setId(resultSet.getInt("id"));
                adm.setNome(resultSet.getString("nome"));
                adm.setCpf(resultSet.getString("cpf"));
                adm.setSenha(resultSet.getString("senha"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return adm;
    }
    
    public void updateAdministrador(int idAdministrador, Administrador administrador){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE administrador SET nome='" + administrador.getNome() + "',cpf='" + administrador.getCpf() +
                    "',senha='" + administrador.getSenha() + "'  WHERE administrador.id=" + idAdministrador + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void deleteAdministrador(int idAdministrador){
    
        try(Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM administrador WHERE administrador.id=" + idAdministrador + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
