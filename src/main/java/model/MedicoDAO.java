package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import conexao.ConexaoBancoDeDados;
import aplicacao.Medico;
import aplicacao.Consulta;

public class MedicoDAO {
    
    private Connection conn;

    /**
    * Construtor para testes
    */
    public MedicoDAO(Connection conn){
        this.conn = conn;
    }
    
    /**
    * Construtor padrão que cria uma nova conexão com o banco.
    */
    public MedicoDAO() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    public Medico login(String cpf, String senha){
        
        Medico medico = new Medico();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medico" + 
                " WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'");
            
            if (resultSet.next()) {
                medico.setId(resultSet.getInt("id"));
                medico.setNome(resultSet.getString("nome"));
                medico.setCrm(resultSet.getInt("crm"));
                medico.setEstadocrm(resultSet.getString("estadocrm"));
                medico.setCpf(resultSet.getString("cpf"));
                medico.setSenha(resultSet.getString("senha"));
                medico.setAutorizado(resultSet.getString("autorizado").charAt(0));
                medico.setIdespecialidade(resultSet.getInt("idespecialidade"));
            } 
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return medico;
    }
   
    public void createExame(int id_tipoexame, int id_consulta){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO exames (idtipoexame,idconsulta) VALUES ('" +
                    id_tipoexame + "','" + id_consulta + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void createMedico(Medico novo_medico){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO medico "
                    + "(nome,crm,estadocrm,cpf,senha,autorizado,idespecialidade) "
                    + "VALUES ( '" + novo_medico.getNome() + "','" + novo_medico.getCrm()  + "','" + novo_medico.getEstadocrm()  + "','" + 
                    novo_medico.getCpf()  + "','" + novo_medico.getSenha()  + "','" + novo_medico.getAutorizado()  + "','" + novo_medico.getIdespecialidade() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Consulta> getConsultas(int id_medico){
    
        ArrayList<Consulta> consultasMedico = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta" + 
                " WHERE idmedico = '" + id_medico + "'");
            
            while (resultSet.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(resultSet.getInt("id"));
                consulta.setData(resultSet.getString("data"));
                consulta.setDescricao(resultSet.getString("descricao"));
                consulta.setRealizada(resultSet.getString("realizada").charAt(0));
                consulta.setIdmedico(resultSet.getInt("idmedico"));
                consulta.setIdpaciente(resultSet.getInt("idpaciente"));
                consultasMedico.add(consulta);
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return consultasMedico;
    }
    
    public Medico getMedico(int id_medico){
        
        Medico medico = new Medico();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medico "
                    + "WHERE medico.id = '" + id_medico + "'");
            
            if (resultSet.next()) {
                medico.setId(resultSet.getInt("id"));
                medico.setNome(resultSet.getString("nome"));
                medico.setCrm(resultSet.getInt("crm"));
                medico.setEstadocrm(resultSet.getString("estadocrm"));
                medico.setCpf(resultSet.getString("cpf"));
                medico.setSenha(resultSet.getString("senha"));
                medico.setAutorizado(resultSet.getString("autorizado").charAt(0));
                medico.setIdespecialidade(resultSet.getInt("idespecialidade"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return medico;
    }
    
    public ArrayList<Object> getExames(int id_consulta, int id_medico){
        
        ArrayList<Object> exames_disponiveis = new ArrayList<>();
    
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoexame");
            
            while(resultSet.next()){
                exames_disponiveis.add(resultSet.getInt("id"));
                exames_disponiveis.add(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return exames_disponiveis;
    }
    
    public ArrayList<Medico> getMedicos(){
    
        ArrayList<Medico> medicos = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medico");
            
            while (resultSet.next()) {
                Medico medico = new Medico();
                medico.setId(resultSet.getInt("id"));
                medico.setNome(resultSet.getString("nome"));
                medico.setCrm(resultSet.getInt("crm"));
                medico.setEstadocrm(resultSet.getString("estadocrm"));
                medico.setCpf(resultSet.getString("cpf"));
                medico.setSenha(resultSet.getString("senha"));
                medico.setAutorizado(resultSet.getString("autorizado").charAt(0));
                medico.setIdespecialidade(resultSet.getInt("idespecialidade"));
                medicos.add(medico);
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return medicos;
    }
    
    public void updateMedico(int id_medico, Medico medico){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE medico SET nome='" + medico.getNome() + "', crm='" +
                    medico.getCrm() + "', estadocrm='" + medico.getEstadocrm() + "', cpf='" + medico.getCpf() +
                    "', senha='" + medico.getSenha() + "', autorizado='" + medico.getAutorizado() +
                    "', idespecialidade='" + medico.getIdespecialidade() + "' WHERE medico.id='" + id_medico + "'");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void deleteMedico(int id_medico){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM medico WHERE medico.id=" + id_medico + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<ArrayList<Integer>> getIdDeleteMedico(int id_medico){
    
        ArrayList<ArrayList<Integer>> id_compilado = new ArrayList<>();
        
        ArrayList<Integer> id_consultas = new ArrayList<>();
        ArrayList<Integer> id_exames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM medico INNER JOIN consulta ON medico.id=consulta.idmedico " +
            "INNER JOIN exames ON consulta.id=exames.idconsulta " +
            "WHERE medico.id=" + id_medico + "");
            
            while(resultSet.next()) {
                id_exames.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            "FROM medico INNER JOIN consulta ON medico.id=consulta.idmedico " +
            "WHERE medico.id=" + id_medico + "");
            
            while(resultSet.next()) {
                id_consultas.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        
        id_compilado.add(id_exames);
        id_compilado.add(id_consultas);
        
        return id_compilado;
    }
    
    public ArrayList<Integer> medicoAvailable(int id_medico, String data){
        
        ArrayList<Integer>colisoes = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
//            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as total " +
            ResultSet resultSet = statement.executeQuery("SELECT consulta.id " +        
            "FROM consulta WHERE data LIKE '%" + data + "%' AND consulta.idmedico=" + id_medico + "");
            
            while (resultSet.next()) {
                colisoes.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return colisoes;
    }
}
