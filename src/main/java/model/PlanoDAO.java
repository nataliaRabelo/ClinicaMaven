package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoBancoDeDados;
import aplicacao.Plano;
import utils.Constantes;

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
    
    public void createPlano(Plano novoPlano){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO tipoplano "
                    + "(descricao) VALUES ( '" + novoPlano.getDescricao() + "')");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR  + e.getMessage());
        }
    }
  
    public List<Plano> getPlanos(){
    
        List<Plano> planos = new ArrayList<>();
       
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoplano");
            
            while(resultSet.next()){
                Plano plano = new Plano();
                plano.setId(resultSet.getInt("id"));
                plano.setDescricao(resultSet.getString("descricao"));
                planos.add(plano);
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return planos;
    }
    
    public Plano getPlano(int idPlano){
    
        Plano plano = new Plano();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoplano "
                    + "WHERE tipoplano.id=" + idPlano + "");
            
            if (resultSet.next()) {
                plano.setId(resultSet.getInt("id"));
                plano.setDescricao(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return plano;
    }
     
    public void updatePlano(int idPlano, Plano novoPlano){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE tipoplano SET descricao='" + novoPlano.getDescricao() + "' WHERE tipoplano.id=" + idPlano + "");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public void deletePlano(int idPlano){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM tipoplano WHERE tipoplano.id=" + idPlano + "");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public List<List<Integer>> getIdDeletePlano(int idPlano){
        
        List<List<Integer>> idCompilado = new ArrayList<>();
        
        List<Integer> idPacientes = new ArrayList<>();
        List<Integer> idConsultas = new ArrayList<>();
        List<Integer> idExames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM tipoplano INNER JOIN paciente ON tipoplano.id=paciente.idtipoplano " +
            "INNER JOIN consulta ON consulta.idpaciente=paciente.id " +
            "INNER JOIN exames ON exames.idconsulta=consulta.id " +
            "WHERE tipoplano.id=" + idPlano + "");
            
            while(resultSet.next()) {
                idExames.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            "FROM tipoplano INNER JOIN paciente ON tipoplano.id=paciente.idtipoplano " +
            "INNER JOIN consulta ON consulta.idpaciente=paciente.id " +
            "WHERE tipoplano.id=" + idPlano + "");
            
            while(resultSet.next()) {
                idConsultas.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT paciente.id " +
            "FROM tipoplano INNER JOIN paciente ON tipoplano.id=paciente.idtipoplano " +
            "WHERE tipoplano.id=" + idPlano + "");
            
            while(resultSet.next()) {
                idPacientes.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        
        idCompilado.add(idExames);
        idCompilado.add(idConsultas);
        idCompilado.add(idPacientes);
        
        return idCompilado;
    }
}    
