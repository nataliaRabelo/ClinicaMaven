package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import conexao.ConexaoBancoDeDados;
import aplicacao.Consulta;
import utils.Constantes;


public class ConsultaDAO {
   
    private Connection conn;
    
    /**
    * Construtor para testes
    */
    public ConsultaDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão que cria uma nova conexão com o banco.
    */
    public ConsultaDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    public void createConsulta(Consulta novaConsulta){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO consulta"
                    + " (data,descricao,realizada,idmedico,idpaciente) VALUES ( '" +
                    novaConsulta.getData() + "','" + novaConsulta.getDescricao() + "','" +
                    novaConsulta.getRealizada() + "','" + novaConsulta.getIdmedico() +
                    "','" + novaConsulta.getIdpaciente() + "')");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public ArrayList<Consulta> getConsultas(int idPaciente){
        
        ArrayList<Consulta> listaConsultas = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta" + 
                " WHERE idpaciente = '" + idPaciente + "'");
            
            while (resultSet.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(resultSet.getInt(Constantes.ID));
                consulta.setData(resultSet.getString(Constantes.DATA));
                consulta.setDescricao(resultSet.getString(Constantes.DESCRICAO));
                consulta.setRealizada(resultSet.getString(Constantes.REALIZADA).charAt(0));
                consulta.setIdmedico(resultSet.getInt(Constantes.IDMEDICO));
                consulta.setIdpaciente(resultSet.getInt(Constantes.IDPACIENTE));
                listaConsultas.add(consulta);
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return listaConsultas;
    }
    
    public Consulta getConsulta(int idConsulta){
    
        Consulta consulta = new Consulta();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta "
                    + "WHERE consulta.id = '" + idConsulta + "'");
            
            if (resultSet.next()) {
                consulta.setId(resultSet.getInt(Constantes.ID));
                consulta.setData(resultSet.getString(Constantes.DATA));
                consulta.setDescricao(resultSet.getString(Constantes.DESCRICAO));
                consulta.setRealizada(resultSet.getString(Constantes.REALIZADA).charAt(0));
                consulta.setIdmedico(resultSet.getInt(Constantes.IDMEDICO));
                consulta.setIdpaciente(resultSet.getInt(Constantes.IDPACIENTE));
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return consulta;
    }
    
    public ArrayList<Object> getMedicoEspecialidade(int idConsulta, ArrayList<Object> medicoDescricao){
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT medico.nome, especialidade.descricao " +
                    "FROM medico INNER JOIN consulta ON medico.id = consulta.idmedico INNER JOIN especialidade " +
                    "ON medico.idespecialidade = especialidade.id WHERE consulta.id = '" + idConsulta + "'");
            
            while(resultSet.next()) {
                medicoDescricao.add(resultSet.getString("nome"));
                medicoDescricao.add(resultSet.getString("descricao"));
            }  
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return medicoDescricao;
    }
    
    public ArrayList<Object> getProcedimentosDisponiveis(){
    
        ArrayList<Object> medEspecs = new ArrayList<>();
    
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT esp.descricao, med.nome, med.id "
                    + "FROM especialidade AS esp INNER JOIN medico As med "
                    + "ON esp.id = med.idespecialidade AND med.autorizado='S'");
            
            while(resultSet.next()){
                medEspecs.add(resultSet.getString("descricao"));
                medEspecs.add(resultSet.getString("nome"));
                medEspecs.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
        return medEspecs;
    }
     
    public void updateConsulta(int idConsulta, Consulta novaConsulta){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE consulta SET data='" + novaConsulta.getData() + "' , descricao='" +
                    novaConsulta.getDescricao() + "' , realizada='" + novaConsulta.getRealizada() +
                    "' , idmedico='" + novaConsulta.getIdmedico() + "' , idpaciente='" + novaConsulta.getIdpaciente() + 
                    "' WHERE consulta.id='" + idConsulta + "'");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
    
    public void deleteConsulta(int idConsulta){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM consulta WHERE consulta.id=" + idConsulta + "");
            
        } catch(SQLException e) {
            System.out.println(Constantes.SQLERROR + e.getMessage());
        }
    }
}
