package model;

import conexao.ConexaoBancoDeDados;
import aplicacao.Administrador;
import utils.Constantes;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            System.out.println("Nao foi possivel conectar");
        }
    } 

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
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return administrador;
    }
    
    public void createAdministrador(Administrador administrador){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO administrador (nome,cpf,senha) VALUES ( '" +
                    administrador.getNome() + "','" + administrador.getCpf() + "','" + administrador.getSenha() + "')");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public ArrayList<Administrador> getAdministradores(){
    
        ArrayList<Administrador> administradores = new ArrayList<>();
        
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
            System.out.println(Constantes.SQLError + e.getMessage());
        }
        return administradores;
    }
    
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
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return adm;
    }
    
    public void updateAdministrador(int idAdministrador, Administrador administrador){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE administrador SET nome='" + administrador.getNome() + "',cpf='" + administrador.getCpf() +
                    "',senha='" + administrador.getSenha() + "'  WHERE administrador.id=" + idAdministrador + "");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public void deleteAdministrador(int idAdministrador){
    
        try(Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM administrador WHERE administrador.id=" + idAdministrador + "");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
}
