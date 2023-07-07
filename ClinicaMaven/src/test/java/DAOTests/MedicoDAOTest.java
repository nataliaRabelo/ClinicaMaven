package DAOTests;

import model.MedicoDAO;
import model.ConsultaDAO;
import model.ExameDAO;
import aplicacao.Medico;
import aplicacao.Exame;
import aplicacao.Consulta;
import conexao.ConexaoBancoDeDados;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Mockito.*;

public class MedicoDAOTest {

    private static Connection conn;

     /**
     * Configuração inicial dos testes.
     * Abre uma conexão com o banco de dados.
     */
    @BeforeAll
    public static void setUp() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }

     /**
     * Testa o login com credenciais válidas.
     */
    @Test
    public void testLoginWithValidCredentials() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        String cpf = "693.339.230-98"; //Assumindo que esse cpf existe no banco depois do script de inserts foi executado.
        String senha = "111";

        // Ação
        Medico medico = medicoDAO.login(cpf, senha);

        // Verificação
        assertNotNull(medico);
        assertEquals(cpf, medico.getCpf());
        assertEquals(senha, medico.getSenha());
    }

    /**
     * Testa o login com credenciais inválidas.
     */
    @Test
    public void testLoginWithInvalidCredentials() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        String cpf = "invalid";
        String senha = "invalid";

        // Ação
        Medico medico = medicoDAO.login(cpf, senha);

        // Verificação
        assertNull(medico.getId());
        assertNull(medico.getNome());
        assertNull(medico.getCrm());
        assertNull(medico.getEstadocrm());
        assertNull(medico.getCpf());
        assertNull(medico.getSenha());
    }

    /**
    * Testa a criação de um exame associado a uma consulta.
    */
    @Test
    public void testCreateExame() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        int idTipoExame = 1;
        int idConsulta = 1;

        // Ação
        medicoDAO.createExame(idTipoExame, idConsulta);

        // Verificação
        // Verifique se o exame foi criado corretamente no banco de dados
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM exames WHERE idtipoexame = " + idTipoExame + " AND idconsulta = " + idConsulta);
            if (resultSet.next()) {
                int exameId = resultSet.getInt("id");
                int exameTipoExameId = resultSet.getInt("idtipoexame");
                int exameConsultaId = resultSet.getInt("idconsulta");

                // Verifique se os campos do exame correspondem aos valores esperados
                assertNotNull(exameId);
                assertEquals(idTipoExame, exameTipoExameId);
                assertEquals(idConsulta, exameConsultaId);
            } else {
                fail("O exame não foi encontrado no banco de dados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    * Testa a criação de um médico.
    */
    @Test
    public void testCreateMedico() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        Medico novoMedico = new Medico();
        novoMedico.setNome("John Doe");
        novoMedico.setCrm(123456);
        novoMedico.setEstadocrm("SP");
        novoMedico.setCpf("123456789");
        novoMedico.setSenha("senha123");
        novoMedico.setAutorizado('S');
        novoMedico.setIdespecialidade(1);

        // Ação
        medicoDAO.createMedico(novoMedico);

        // Verificação
        // Verifique se o médico foi criado corretamente no banco de dados
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medico WHERE nome = 'John Doe'");
            if (resultSet.next()) {
                Medico medicoCriado = new Medico();
                medicoCriado.setId(resultSet.getInt("id"));
                medicoCriado.setNome(resultSet.getString("nome"));
                medicoCriado.setCrm(resultSet.getInt("crm"));
                medicoCriado.setEstadocrm(resultSet.getString("estadocrm"));
                medicoCriado.setCpf(resultSet.getString("cpf"));
                medicoCriado.setSenha(resultSet.getString("senha"));
                medicoCriado.setAutorizado(resultSet.getString("autorizado").charAt(0));
                medicoCriado.setIdespecialidade(resultSet.getInt("idespecialidade"));

                // Verifique se o objeto Medico não é nulo
                assertNotNull(medicoCriado);

                // Verifique se os campos batem
                assertEquals("John Doe", medicoCriado.getNome());
                assertEquals(123456, medicoCriado.getCrm());
                assertEquals("SP", medicoCriado.getEstadocrm());
                assertEquals("123456789", medicoCriado.getCpf());
                assertEquals("senha123", medicoCriado.getSenha());
                assertEquals('S', medicoCriado.getAutorizado());
                assertEquals(1, medicoCriado.getIdespecialidade());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * Testa a obtenção de consultas associadas a um médico.
    */
    @Test
    public void testGetConsultas() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        int idMedico = 1;

        // Ação
        List<Consulta> consultas = medicoDAO.getConsultas(idMedico);

        // Verificação
        assertNotNull(consultas);
    }
    
    /**
    * Testa a obtenção de um médico a partir de um id
    */
    @Test
    public void testGetMedico() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        int idMedicoExistente = 1;

        // Ação
        Medico medicoExistente = medicoDAO.getMedico(idMedicoExistente);

        // Verificação
        assertEquals(1, medicoExistente.getId());
        assertEquals("Marcos", medicoExistente.getNome());
        assertEquals(1234, medicoExistente.getCrm());
        assertEquals("RJ", medicoExistente.getEstadocrm());
        assertEquals("381.585.150-53", medicoExistente.getCpf());
        assertEquals("111", medicoExistente.getSenha());
        assertEquals('S', medicoExistente.getAutorizado());
        assertEquals(2, medicoExistente.getIdespecialidade());
    }

     /**
     * Testa a obtenção de exames disponíveis.
     */
    @Test
    public void testGetExames() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);

        // Ação
        List<Object> examesDisponiveis = medicoDAO.getExames();

        // Verificação
        assertNotNull(examesDisponiveis);
    }

     /**
     * Testa a obtenção de todos os médicos cadastrados.
     */
    @Test
    public void testGetMedicos() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);

        // Ação
        List<Medico> medicos = medicoDAO.getMedicos();

        // Verificação
        assertNotNull(medicos);
        assertEquals("Marcos", medicos.get(0).getNome());
    }

    /**
     * Testa a atualização das informações de um médico.
     */
    @Test
    public void testUpdateMedico() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        int idMedico = 1;
        Medico medico = medicoDAO.getMedico(idMedico);
        // Defina os atributos do médico a ser atualizado

        // Ação
        medicoDAO.updateMedico(idMedico, medico);

        // Verificação
        // Verifique se a atualização foi realizada corretamente, recuperando o médico atualizado do banco de dados
        Medico medicoAtualizado = medicoDAO.getMedico(idMedico);
        // Verifique se os atributos do médico atualizado estão corretos comparando-os com os novos valores definidos
        assertEquals(medico.getNome(), medicoAtualizado.getNome());
        assertEquals(medico.getCrm(), medicoAtualizado.getCrm());
        assertEquals(medico.getEstadocrm(), medicoAtualizado.getEstadocrm());
    }

    /**
     * Testa a exclusão de um médico.
     */
    @Test
    public void testDeleteMedico() {
        try {
            // Arrange
            MedicoDAO medicoDAO = new MedicoDAO(conn);  
            // Cria um médico para ser excluído
            Medico novoMedico = new Medico();
            novoMedico.setNome("Joana");
            novoMedico.setCrm(333);
            medicoDAO.createMedico(novoMedico);

            // Criando o Statement para executar a query
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medico WHERE medico.crm = '" + novoMedico.getCrm() + "'");
            int idResultSet = 0; 
            // Verificando se a consulta retornou algum resultado
            if(resultSet.next()) {
                // Act
                int id = resultSet.getInt("id"); // Supondo que a coluna do id na tabela seja 'id'
                idResultSet = id;
                medicoDAO.deleteMedico(id); // deletando o usuário que acabei de criar, onde o id é inserido no banco
            }
            // Assert
            Medico medico = medicoDAO.getMedico(idResultSet);
            assertNull(medico.getNome(), "O paciente deveria ser null após ser deletado");
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

     /**
     * Testa a obtenção de IDs de exames e consultas associados a um médico.
     */
    @Test
    public void testGetIdDeleteMedico() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        int idMedico = 1;

        // Ação
        List<List<Integer>> idCompilado = medicoDAO.getIdDeleteMedico(idMedico);

        // Verificação
        assertNotNull(idCompilado);
    }

     /**
     * Testa a verificação de disponibilidade de um médico em uma determinada data.
     */
    @Test
    public void testMedicoAvailable() {
        // Cenário
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        int idMedico = 1;
        String data = "2023-06-25 00:00:00";

        // Ação
        List<Integer> colisoes = medicoDAO.medicoAvailable(idMedico, data);

        // Verificação
        assertNotNull(colisoes);
    }
    
    // ---------------------- testes com mock ---------------

    /*@Test
    public void loginTestMock() throws Exception {
        Connection conn = mock(Connection.class);
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);
        
        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        
        MedicoDAO dao = new MedicoDAO(conn);
        
        String cpf = "12345678910";
        String senha = "123456";
        
        when(rs.getString("cpf")).thenReturn(cpf);
        when(rs.getString("senha")).thenReturn(senha);
        
        Medico medico = dao.login(cpf, senha);
        
        assertEquals(cpf, medico.getCpf());
        assertEquals(senha, medico.getSenha());
    }*/
    
    @Test
    public void testUpdateMedicoMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        Medico medico = new Medico();
        medicoDAO.updateMedico(1, medico);
        
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }
    
    @Test
    public void testDeleteMedicoMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        medicoDAO.deleteMedico(1);
        
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }
    
    @Test
    public void testGetIdDeleteMedicoMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        List<List<Integer>> ids = medicoDAO.getIdDeleteMedico(1);
        
        verify(conn, times(1)).createStatement();
        verify(statement, times(2)).executeQuery(anyString());
        assertTrue(ids.size() > 0);
    }
    
    @Test
    public void testMedicoAvailableMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        
        MedicoDAO medicoDAO = new MedicoDAO(conn);
        List<Integer> ids = medicoDAO.medicoAvailable(1, "2023-06-25");
        
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
        assertTrue(ids.size() > 0);
    }

    @Test
    public void testGetExamesMock() throws SQLException {
        // Cenário
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        
        MedicoDAO medicoDAO = new MedicoDAO(conn);

        // Ação
        List<Object> exames = medicoDAO.getExames();

        // Verificação
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
        assertTrue(exames.size() > 0);
    }
}
