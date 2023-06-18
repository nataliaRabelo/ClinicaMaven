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
   
    public void createExame(int idTipoExame, int idConsulta){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO exames (idtipoexame,idconsulta) VALUES ('" +
                    idTipoExame + "','" + idConsulta + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void createMedico(Medico novoMedico){
    
        try (Statement statement = conn.createStatement()){
            statement.execute("INSERT INTO medico "
                    + "(nome,crm,estadocrm,cpf,senha,autorizado,idespecialidade) "
                    + "VALUES ( '" + novoMedico.getNome() + "','" + novoMedico.getCrm()  + "','" + novoMedico.getEstadocrm()  + "','" + 
                    novoMedico.getCpf()  + "','" + novoMedico.getSenha()  + "','" + novoMedico.getAutorizado()  + "','" + novoMedico.getIdespecialidade() + "')");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<Consulta> getConsultas(int idMedico){
    
        ArrayList<Consulta> consultasMedico = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta" + 
                " WHERE idmedico = '" + idMedico + "'");
            
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
    
    public Medico getMedico(int idMedico){
        
        Medico medico = new Medico();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medico "
                    + "WHERE medico.id = '" + idMedico + "'");
            
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
    
    public ArrayList<Object> getExames(){
        
        ArrayList<Object> examesDisponiveis = new ArrayList<>();
    
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoexame");
            
            while(resultSet.next()){
                examesDisponiveis.add(resultSet.getInt("id"));
                examesDisponiveis.add(resultSet.getString("descricao"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return examesDisponiveis;
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
    
    public void updateMedico(int idMedico, Medico medico){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("UPDATE medico SET nome='" + medico.getNome() + "', crm='" +
                    medico.getCrm() + "', estadocrm='" + medico.getEstadocrm() + "', cpf='" + medico.getCpf() +
                    "', senha='" + medico.getSenha() + "', autorizado='" + medico.getAutorizado() +
                    "', idespecialidade='" + medico.getIdespecialidade() + "' WHERE medico.id='" + idMedico + "'");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public void deleteMedico(int idMedico){
        
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM medico WHERE medico.id=" + idMedico + "");
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    public ArrayList<ArrayList<Integer>> getIdDeleteMedico(int idMedico){
    
        ArrayList<ArrayList<Integer>> idCompilado = new ArrayList<>();
        
        ArrayList<Integer> idConsultas = new ArrayList<>();
        ArrayList<Integer> idExames = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT exames.id " +
            "FROM medico INNER JOIN consulta ON medico.id=consulta.idmedico " +
            "INNER JOIN exames ON consulta.id=exames.idconsulta " +
            "WHERE medico.id=" + idMedico + "");
            
            while(resultSet.next()) {
                idExames.add(resultSet.getInt("id"));
            }
            
            resultSet = statement.executeQuery("SELECT consulta.id " +
            "FROM medico INNER JOIN consulta ON medico.id=consulta.idmedico " +
            "WHERE medico.id=" + idMedico + "");
            
            while(resultSet.next()) {
                idConsultas.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        
        idCompilado.add(idExames);
        idCompilado.add(idConsultas);
        
        return idCompilado;
    }
    
    public ArrayList<Integer> medicoAvailable(int idMedico, String data){
        
        ArrayList<Integer>colisoes = new ArrayList<>();
        
        try (Statement statement = conn.createStatement()){
//            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as total " +
            ResultSet resultSet = statement.executeQuery("SELECT consulta.id " +        
            "FROM consulta WHERE data LIKE '%" + data + "%' AND consulta.idmedico=" + idMedico + "");
            
            while (resultSet.next()) {
                colisoes.add(resultSet.getInt("id"));
            }
            
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return colisoes;
    }
}
