package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoBancoDeDados;
import aplicacao.Especialidade;
import utils.Constantes;

public class EspecialidadeDAO {
   
    private Connection conn;
    
    /**
    * Construtor para testes
    */
    public EspecialidadeDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão que cria uma nova conexão com o banco.
    */
    public EspecialidadeDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    public void createEspecialidade(Especialidade novaEspecialidade){
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO especialidade "
                    + "(descricao) VALUES ( '" + novaEspecialidade.getDescricao() + "')");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public List<Especialidade> getEspecialidades(){
    
        List<Especialidade> especialidades = new ArrayList<>();
       
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM especialidade");
            
            while(resultSet.next()){
                Especialidade espec = new Especialidade();
                espec.setId(resultSet.getInt("id"));
                espec.setDescricao(resultSet.getString("descricao"));
                especialidades.add(espec);
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return especialidades;
    }
    
    public Especialidade getEspecialidade(int idEspecialidade){
        
        Especialidade espec = new Especialidade();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM especialidade "
                    + "WHERE especialidade.id = '" + idEspecialidade + "'");
            
            if (resultSet.next()) {
                espec.setId(resultSet.getInt("id"));
                espec.setDescricao(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return espec;
    }
    
    public void updateEspecialidade(int idEspecialidade, Especialidade novaEspecialidade){
    
       try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE especialidade SET descricao='" + novaEspecialidade.getDescricao() + "' "
                    + "WHERE especialidade.id=" + idEspecialidade + "");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public void deleteEspecialidade(int idEspecialidade){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM especialidade WHERE especialidade.id=" + idEspecialidade + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public List<List<Integer>> getIdDeleteEspecialidade(int idEspecialidade){
        
        List<List<Integer>> idCompilado = new ArrayList<>();
        
        List<Integer> idMedicos = new ArrayList<>();
        List<Integer> idConsultas = new ArrayList<>();
        List<Integer> idExames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM especialidade INNER JOIN medico ON especialidade.id=medico.idespecialidade " +
            "INNER JOIN consulta ON consulta.idmedico=medico.id " +
            "INNER JOIN exames ON exames.idconsulta=consulta.id " +
            "WHERE especialidade.id=" + idEspecialidade + "");
            
            while(resultSet.next()) {
                idExames.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            "FROM especialidade INNER JOIN medico ON especialidade.id=medico.idespecialidade " +
            "INNER JOIN consulta ON consulta.idmedico=medico.id " +
            "WHERE especialidade.id=" + idEspecialidade + "");
            
            while(resultSet.next()) {
                idConsultas.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT medico.id " +
            "FROM especialidade INNER JOIN medico ON especialidade.id=medico.idespecialidade " +
            "WHERE especialidade.id=" + idEspecialidade + "");
            
            while(resultSet.next()) {
                idMedicos.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        
        idCompilado.add(idExames);
        idCompilado.add(idConsultas);
        idCompilado.add(idMedicos);
        
        return idCompilado;
    }
}
