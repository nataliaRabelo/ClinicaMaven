package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import conexao.ConexaoBancoDeDados;
import aplicacao.Plano;

public class PlanoDAO {
   
    private Connection conn;

    /**
    * Construtor para testes
    */
    public PlanoDAO(Connection conn) {
        this.conn = conn; 
    }

    /**
    * Construtor padrão que cria uma nova conexão com o banco.
    */
    public PlanoDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    public void createPlano(Plano novo_plano){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO tipoplano "
                    + "(descricao) VALUES ( '" + novo_plano.getDescricao() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
  
    public ArrayList<Plano> getPlanos(){
    
        ArrayList<Plano> planos = new ArrayList<>();
       
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoplano");
            
            while(resultSet.next()){
                Plano plano = new Plano();
                plano.setId(resultSet.getInt("id"));
                plano.setDescricao(resultSet.getString("descricao"));
                planos.add(plano);
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return planos;
    }
    
    public Plano getPlano(int id_plano){
    
        Plano plano = new Plano();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoplano "
                    + "WHERE tipoplano.id=" + id_plano + "");
            
            if (resultSet.next()) {
                plano.setId(resultSet.getInt("id"));
                plano.setDescricao(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return plano;
    }
     
    public void updatePlano(int id_plano, Plano novo_plano){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE tipoplano SET descricao='" + novo_plano.getDescricao() + "' WHERE tipoplano.id=" + id_plano + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void deletePlano(int id_plano){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM tipoplano WHERE tipoplano.id=" + id_plano + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<ArrayList<Integer>> getIdDeletePlano(int id_plano){
        
        ArrayList<ArrayList<Integer>> id_compilado = new ArrayList<>();
        
        ArrayList<Integer> id_pacientes = new ArrayList<>();
        ArrayList<Integer> id_consultas = new ArrayList<>();
        ArrayList<Integer> id_exames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM tipoplano INNER JOIN paciente ON tipoplano.id=paciente.idtipoplano " +
            "INNER JOIN consulta ON consulta.idpaciente=paciente.id " +
            "INNER JOIN exames ON exames.idconsulta=consulta.id " +
            "WHERE tipoplano.id=" + id_plano + "");
            
            while(resultSet.next()) {
                id_exames.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            "FROM tipoplano INNER JOIN paciente ON tipoplano.id=paciente.idtipoplano " +
            "INNER JOIN consulta ON consulta.idpaciente=paciente.id " +
            "WHERE tipoplano.id=" + id_plano + "");
            
            while(resultSet.next()) {
                id_consultas.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT paciente.id " +
            "FROM tipoplano INNER JOIN paciente ON tipoplano.id=paciente.idtipoplano " +
            "WHERE tipoplano.id=" + id_plano + "");
            
            while(resultSet.next()) {
                id_pacientes.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        
        id_compilado.add(id_exames);
        id_compilado.add(id_consultas);
        id_compilado.add(id_pacientes);
        
        return id_compilado;
    }
}    
