package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import conexao.conexao_bancodedados;
import aplicacao.Consulta;

@WebServlet(name = "ConsultaDAO", urlPatterns = {"/ConsultaDAO"})
public class ConsultaDAO extends HttpServlet {
   
    private Connection conn;

    public ConsultaDAO() {
        try {
            conn = conexao_bancodedados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    public void create_consulta(Consulta nova_consulta){
        
        try {
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO consulta"
                    + " (data,descricao,realizada,idmedico,idpaciente) VALUES ( '" +
                    nova_consulta.getData() + "','" + nova_consulta.getDescricao() + "','" +
                    nova_consulta.getRealizada() + "','" + nova_consulta.getIdmedico() +
                    "','" + nova_consulta.getIdpaciente() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Consulta> get_consultas(int id_paciente){
        
        ArrayList<Consulta> lista_consultas = new ArrayList<>();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta" + 
                " WHERE idpaciente = '" + id_paciente + "'");
            
            while (resultSet.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(resultSet.getInt("id"));
                consulta.setData(resultSet.getString("data"));
                consulta.setDescricao(resultSet.getString("descricao"));
                consulta.setRealizada(resultSet.getString("realizada").charAt(0));
                consulta.setIdmedico(resultSet.getInt("idmedico"));
                consulta.setIdpaciente(resultSet.getInt("idpaciente"));
                lista_consultas.add(consulta);
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return lista_consultas;
    }
    
    public Consulta get_consulta(int id_consulta){
    
        Consulta consulta = new Consulta();
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta "
                    + "WHERE consulta.id = '" + id_consulta + "'");
            
            if (resultSet.next()) {
                consulta.setId(resultSet.getInt("id"));
                consulta.setData(resultSet.getString("data"));
                consulta.setDescricao(resultSet.getString("descricao"));
                consulta.setRealizada(resultSet.getString("realizada").charAt(0));
                consulta.setIdmedico(resultSet.getInt("idmedico"));
                consulta.setIdpaciente(resultSet.getInt("idpaciente"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return consulta;
    }
    
    public ArrayList<Object> get_medicoEspecialidade(int id_consulta, ArrayList<Object> medico_descricao){
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT medico.nome, especialidade.descricao " +
                    "FROM medico INNER JOIN consulta ON medico.id = consulta.idmedico INNER JOIN especialidade " +
                    "ON medico.idespecialidade = especialidade.id WHERE consulta.id = '" + id_consulta + "'");
            
            while(resultSet.next()) {
                medico_descricao.add(resultSet.getString("nome"));
                medico_descricao.add(resultSet.getString("descricao"));
            }  
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return medico_descricao;
    }
    
    public ArrayList<Object> get_procedimentosDisponiveis(){
    
        ArrayList<Object> med_especs = new ArrayList<Object>();
    
        try{
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT esp.descricao, med.nome, med.id "
                    + "FROM especialidade AS esp INNER JOIN medico As med "
                    + "ON esp.id = med.idespecialidade AND med.autorizado='S'");
            
            while(resultSet.next()){
                med_especs.add(resultSet.getString("descricao"));
                med_especs.add(resultSet.getString("nome"));
                med_especs.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return med_especs;
    }
     
    public void update_consulta(int id_consulta, Consulta nova_consulta){
        
        try {
            Statement statement = conn.createStatement();
            statement.execute("UPDATE consulta SET data='" + nova_consulta.getData() + "' , descricao='" +
                    nova_consulta.getDescricao() + "' , realizada='" + nova_consulta.getRealizada() +
                    "' , idmedico='" + nova_consulta.getIdmedico() + "' , idpaciente='" + nova_consulta.getIdpaciente() + 
                    "' WHERE consulta.id='" + id_consulta + "'");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void delete_consulta(int id_consulta){
        
        try{
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM consulta WHERE consulta.id=" + id_consulta + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
