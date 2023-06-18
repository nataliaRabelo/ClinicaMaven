package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import conexao.ConexaoBancoDeDados;
import aplicacao.Especialidade;

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
    
    public void createEspecialidade(Especialidade nova_especialidade){
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO especialidade "
                    + "(descricao) VALUES ( '" + nova_especialidade.getDescricao() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Especialidade> getEspecialidades(){
    
        ArrayList<Especialidade> especialidades = new ArrayList<>();
       
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM especialidade");
            
            while(resultSet.next()){
                Especialidade espec = new Especialidade();
                espec.setId(resultSet.getInt("id"));
                espec.setDescricao(resultSet.getString("descricao"));
                especialidades.add(espec);
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return especialidades;
    }
    
    public Especialidade getEspecialidade(int id_especialidade){
        
        Especialidade espec = new Especialidade();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM especialidade "
                    + "WHERE especialidade.id = '" + id_especialidade + "'");
            
            if (resultSet.next()) {
                espec.setId(resultSet.getInt("id"));
                espec.setDescricao(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return espec;
    }
    
    public void updateEspecialidade(int id_especialidade, Especialidade nova_especialidade){
    
       try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE especialidade SET descricao='" + nova_especialidade.getDescricao() + "' "
                    + "WHERE especialidade.id=" + id_especialidade + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void deleteEspecialidade(int id_especialidade){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM especialidade WHERE especialidade.id=" + id_especialidade + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<ArrayList<Integer>> getIdDeleteEspecialidade(int id_especialidade){
        
        ArrayList<ArrayList<Integer>> id_compilado = new ArrayList<>();
        
        ArrayList<Integer> id_medicos = new ArrayList<>();
        ArrayList<Integer> id_consultas = new ArrayList<>();
        ArrayList<Integer> id_exames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM especialidade INNER JOIN medico ON especialidade.id=medico.idespecialidade " +
            "INNER JOIN consulta ON consulta.idmedico=medico.id " +
            "INNER JOIN exames ON exames.idconsulta=consulta.id " +
            "WHERE especialidade.id=" + id_especialidade + "");
            
            while(resultSet.next()) {
                id_exames.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            "FROM especialidade INNER JOIN medico ON especialidade.id=medico.idespecialidade " +
            "INNER JOIN consulta ON consulta.idmedico=medico.id " +
            "WHERE especialidade.id=" + id_especialidade + "");
            
            while(resultSet.next()) {
                id_consultas.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT medico.id " +
            "FROM especialidade INNER JOIN medico ON especialidade.id=medico.idespecialidade " +
            "WHERE especialidade.id=" + id_especialidade + "");
            
            while(resultSet.next()) {
                id_medicos.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        
        id_compilado.add(id_exames);
        id_compilado.add(id_consultas);
        id_compilado.add(id_medicos);
        
        return id_compilado;
    }
}
