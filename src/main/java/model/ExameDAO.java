package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import conexao.ConexaoBancoDeDados;
import aplicacao.Exame;

/**
 *
 * @author Adriano
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
    * Construtor padrão que cria uma nova conexão com o banco.
    */
    public ExameDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    public void createExame(Exame novo_exame){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO tipoexame "
                    + "(descricao) VALUES ( '" + novo_exame.getDescricao() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Exame> getExames(){
    
        ArrayList<Exame> exames = new ArrayList<>();
       
        try (Statement statement = conn.createStatement()){
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
    
    public Exame getExame(int id_exame){
    
        Exame exame = new Exame();
        
        try (Statement statement = conn.createStatement()){
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
    
    public ArrayList<String> getExamesDaConsulta(int id_consulta, ArrayList<String> lista_exames){
        
        try (Statement statement = conn.createStatement()){
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
    
    public void updateExame(int id_exame, Exame novo_exame){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE tipoexame SET descricao='" + novo_exame.getDescricao() + "' WHERE tipoexame.id=" + id_exame + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void deleteExame(int id_exame){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM exames WHERE exames.id=" + id_exame + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void deleteTipoExame(int id_exame){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM tipoexame WHERE tipoexame.id=" + id_exame + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Integer> getIdDeleteExame(int id_exame){
        
        ArrayList<Integer> id_exames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
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
