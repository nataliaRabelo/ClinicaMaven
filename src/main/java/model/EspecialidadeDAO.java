package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import conexao.conexao_bancodedados;
import aplicacao.Especialidade;

@WebServlet(name = "EspecialidadeDAO", urlPatterns = {"/EspecialidadeDAO"})
public class EspecialidadeDAO extends HttpServlet {
   
    private Connection conn;

    public EspecialidadeDAO() {
        try {
            conn = conexao_bancodedados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    public void create_especialidade(Especialidade nova_especialidade){
        try {
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO especialidade "
                    + "(descricao) VALUES ( '" + nova_especialidade.getDescricao() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Especialidade> get_especialidades(){
    
        ArrayList<Especialidade> especialidades = new ArrayList<Especialidade>();
       
        try{
            Statement statement = conn.createStatement();
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
    
    public Especialidade get_especialidade(int id_especialidade){
        
        Especialidade espec = new Especialidade();
        
        try {
            Statement statement = conn.createStatement();
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
    
    public void update_especialidade(int id_especialidade, Especialidade nova_especialidade){
    
       try {
            Statement statement = conn.createStatement();
            statement.execute("UPDATE especialidade SET descricao='" + nova_especialidade.getDescricao() + "' "
                    + "WHERE especialidade.id=" + id_especialidade + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void delete_especialidade(int id_especialidade){
    
        try{
            Statement statement = conn.createStatement();
            statement.execute("DELETE FROM especialidade WHERE especialidade.id=" + id_especialidade + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<ArrayList<Integer>> get_idDeleteEspecialidade(int id_especialidade){
        
        ArrayList<ArrayList<Integer>> id_compilado = new ArrayList<ArrayList<Integer>>();
        
        ArrayList<Integer> id_medicos = new ArrayList<Integer>();
        ArrayList<Integer> id_consultas = new ArrayList<Integer>();
        ArrayList<Integer> id_exames = new ArrayList<Integer>();
        
        try {
            Statement statement = conn.createStatement();
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
