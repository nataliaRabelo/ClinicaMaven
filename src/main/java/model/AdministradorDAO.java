package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import conexao.conexao_bancodedados;
import aplicacao.Administrador;

@WebServlet(name = "AdministradorDAO", urlPatterns = {"/AdministradorDAO"})
public class AdministradorDAO extends HttpServlet {
    
    private Connection conn;

    public AdministradorDAO() {
        try {
            conn = conexao_bancodedados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    } 

    public Administrador login(String cpf, String senha) {
        
        Administrador administrador = new Administrador();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador" + 
                " WHERE cpf = '" + String.valueOf(cpf) + "' AND senha = '" + String.valueOf(senha) + "'");
            
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
    
    public void create_administrador(Administrador administrador){
        
        try {
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO administrador (nome,cpf,senha) VALUES ( '" +
                    administrador.getNome() + "','" + administrador.getCpf() + "','" + administrador.getSenha() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Administrador> get_administradores(){
    
        ArrayList<Administrador> administradores = new ArrayList<Administrador>();
        
        try {
            Statement statement = conn.createStatement();
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
    
    public Administrador get_administrador(int id_administrador){
    
        Administrador adm = new Administrador();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM administrador "
                    + "WHERE administrador.id = '" + id_administrador + "'");
            
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
    
    public void update_administrador(int id_administrador, Administrador administrador){
    
        try {
            Statement statement = conn.createStatement();
            statement.execute("UPDATE administrador SET nome='" + administrador.getNome() + "',cpf='" + administrador.getCpf() +
                    "',senha='" + administrador.getSenha() + "'  WHERE administrador.id=" + id_administrador + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void delete_administrador(int id_administradore){
    
        try{
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM administrador WHERE administrador.id=" + id_administradore + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
