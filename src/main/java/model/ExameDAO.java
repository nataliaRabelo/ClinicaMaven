package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import conexao.conexao_bancodedados;
import aplicacao.Exame;

@WebServlet(name = "ExameDAO", urlPatterns = {"/ExameDAO"})
public class ExameDAO extends HttpServlet {
   
    private Connection conn;

    public ExameDAO() {
        try {
            conn = conexao_bancodedados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    public void create_exame(Exame novo_exame){
        
        try {
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO tipoexame "
                    + "(descricao) VALUES ( '" + novo_exame.getDescricao() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Exame> get_exames(){
    
        ArrayList<Exame> exames = new ArrayList<Exame>();
       
        try{
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoexame");
            
            while(resultSet.next()){
                Exame exame = new Exame();
                exame.setId(resultSet.getInt("id"));
                exame.setDescricao(resultSet.getString("descricao"));
                exames.add(exame);
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return exames;
    }
    
    public Exame get_exame(int id_exame){
    
        Exame exame = new Exame();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoexame "
                    + "WHERE tipoexame.id=" + id_exame + "");
            
            if (resultSet.next()) {
                exame.setId(resultSet.getInt("id"));
                exame.setDescricao(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return exame;
    }
    
    public ArrayList<String> get_examesDaConsulta(int id_consulta, ArrayList<String> lista_exames){
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT tipoexame.descricao " +
                "FROM consulta INNER JOIN exames ON consulta.id = exames.idconsulta " +
                "INNER JOIN tipoexame ON exames.idtipoexame = tipoexame.id " +
                "WHERE consulta.id='" + id_consulta + "'");
            
            while (resultSet.next()) {
                lista_exames.add(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return lista_exames;
    }
    
    public void update_exame(int id_exame, Exame novo_exame){
    
        try {
            Statement statement = conn.createStatement();
            statement.execute("UPDATE tipoexame SET descricao='" + novo_exame.getDescricao() + "' WHERE tipoexame.id=" + id_exame + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void delete_exame(int id_exame){
        
        try{
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM exames WHERE exames.id=" + id_exame + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void delete_tipoExame(int id_exame){
        
        try{
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM tipoexame WHERE tipoexame.id=" + id_exame + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Integer> get_idDeleteExame(int id_exame){
        
        ArrayList<Integer> id_exames = new ArrayList<Integer>();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM exames WHERE exames.idtipoexame=" + id_exame + "");
            
            while(resultSet.next()) {
                id_exames.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return id_exames;
    }
}
