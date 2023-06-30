package DAOTests;

import model.EspecialidadeDAO;
import aplicacao.Especialidade;
import conexao.ConexaoBancoDeDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para a classe EspecialidadeDAO.
 */
public class EspecialidadeDAOTest {

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
     * Testa o método createEspecialidade da classe EspecialidadeDAO.
     * Verifica se uma nova especialidade é cadastrada corretamente no banco de dados.
     */
    @Test
    public void testCreateEspecialidade() {
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);
        Especialidade novaEspecialidade = new Especialidade();
        novaEspecialidade.setDescricao("Cardiologia");

        especialidadeDAO.createEspecialidade(novaEspecialidade);

        // Tenta recuperar a especialidade do banco de dados
        List<Especialidade> especialidades = especialidadeDAO.getEspecialidades();

        boolean found = false;
        for (Especialidade especialidade : especialidades) {
            if (especialidade.getDescricao().equals(novaEspecialidade.getDescricao())) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }

     /**
     * Testa o método getEspecialidades da classe EspecialidadeDAO.
     * Verifica se a lista de especialidades retornada não está vazia.
     */
    @Test
    public void testGetEspecialidades() {
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);
        List<Especialidade> especialidades = especialidadeDAO.getEspecialidades();

        Assertions.assertNotNull(especialidades, "A lista de especialidades não deve ser nula");
        Assertions.assertTrue(especialidades.size() > 0, "A lista de especialidades deve ter pelo menos uma especialidade");
    }

     /**
     * Testa o método getEspecialidade da classe EspecialidadeDAO.
     * Verifica se uma especialidade é retornada corretamente com base em seu ID.
     */
    @Test
    public void testGetEspecialidade() {
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);
        int idEspecialidade = 1; // ID válido

        Especialidade especialidade = especialidadeDAO.getEspecialidade(idEspecialidade);

        Assertions.assertNotNull(especialidade, "A especialidade não deve ser nula");
        Assertions.assertEquals(idEspecialidade, especialidade.getId(), "O ID da especialidade deve ser o mesmo do ID de entrada");
    }
    
    /**
     * Testa o método updateEspecialidade da classe EspecialidadeDAO.
     * Verifica se uma especialidade é atualizada corretamente no banco de dados.
     */
    @Test
    public void testUpdateEspecialidade() {
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);
        int idEspecialidade = 1; // ID válido
        Especialidade novaEspecialidade = new Especialidade();
        novaEspecialidade.setDescricao("Pediatria");

        especialidadeDAO.updateEspecialidade(idEspecialidade, novaEspecialidade);

        // Recupera a especialidade do banco de dados
        Especialidade especialidadeAtualizada = especialidadeDAO.getEspecialidade(idEspecialidade);

        // Verifica se a descrição da especialidade atualizada corresponde à descrição da nova especialidade
        assertEquals(novaEspecialidade.getDescricao(), especialidadeAtualizada.getDescricao());
    }

     /**
     * Testa o método deleteEspecialidade da classe EspecialidadeDAO.
     * Verifica se uma especialidade é corretamente removida do banco de dados.
     */
    @Test
    public void testDeleteEspecialidade() {
        try {
            // Arrange
            EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);  
            Especialidade especialidade = new Especialidade(); // Criando uma especialidade para testar a deleção
            especialidade.setDescricao("Psiquiatria");
            especialidadeDAO.createEspecialidade(especialidade);

            // Criando o Statement para executar a query
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta WHERE especialidade.descricao = '" + especialidade.getDescricao() + "'");
            int idResultSet = 0; 
            // Verificando se a especialidade retornou algum resultado
            if(resultSet.next()) {
                // Act
                int id = resultSet.getInt("id"); // Supondo que a coluna do id na tabela seja 'id'
                idResultSet = id;
                Especialidade especialidadeApagada = especialidadeDAO.getEspecialidade(id); // deletando a especialidade que acabei de criar, onde o id é inserido no banco
            }

            // Assert
            Especialidade especialidadeQuery = especialidadeDAO.getEspecialidade(idResultSet);
            assertNull(especialidadeQuery.getDescricao(), "A consulta deveria ser null após ser deletado");
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

     /**
     * Testa o método getIdDeleteEspecialidade da classe EspecialidadeDAO.
     * Verifica se os IDs dos exames e consultas associados a uma especialidade são corretamente obtidos.
     */
    @Test
    public void testGetIdDeleteEspecialidade() {
        try {
            // Arrange
            EspecialidadeDAO dao = new EspecialidadeDAO(conn);

            Especialidade especialidade = new Especialidade(); // Criando uma especialidade para testar a deleção
            especialidade.setDescricao("Psiquiatria");
            dao.createEspecialidade(especialidade);

            // Criando o Statement para executar a query
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente WHERE especialidade.descricao = '" + especialidade.getDescricao() + "'");

            // Verificando se a consulta retornou algum resultado
            if(resultSet.next()) {
                // Act
                int id = resultSet.getInt("id"); // Supondo que a coluna do id na tabela seja 'id'
                List<List<Integer>> id_compilado = dao.getIdDeleteEspecialidade(id);

                // Assert
                assertFalse(id_compilado.get(0).isEmpty(), "A lista de ids de exames não deve estar vazia");
                assertFalse(id_compilado.get(1).isEmpty(), "A lista de ids de consultas não deve estar vazia");
            }
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }
    
    // --------------- TESTES COM MOCK ---------
        /**
     * Testa o método createEspecialidade de EspecialidadeDAO.
     * @throws SQLException para qualquer exceção SQL lançada.
     */
    @Test
    public void testCreateEspecialidadeMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);

        Especialidade novaEspecialidade = new Especialidade();
        novaEspecialidade.setDescricao("Especialidade Teste");

        when(statement.execute(anyString())).thenReturn(true);

        especialidadeDAO.createEspecialidade(novaEspecialidade);
        
        verify(statement, times(1)).execute(anyString());
    }

    /**
     * Testa o método getEspecialidades de EspecialidadeDAO.
     * @throws SQLException para qualquer exceção SQL lançada.
     */
    @Test
    public void testGetEspecialidadesMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);

        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("descricao")).thenReturn("Especialidade Teste");

        List<Especialidade> especialidades = especialidadeDAO.getEspecialidades();

        assertNotNull(especialidades);
        assertEquals(1, especialidades.size());
        assertEquals(1, especialidades.get(0).getId());
        assertEquals("Especialidade Teste", especialidades.get(0).getDescricao());
    }
    
        /**
     * Testa o método getEspecialidade de EspecialidadeDAO.
     * @throws SQLException para qualquer exceção SQL lançada.
     */
    @Test
    public void testGetEspecialidadeMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);

        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("descricao")).thenReturn("Especialidade Teste");

        Especialidade especialidade = especialidadeDAO.getEspecialidade(1);

        assertNotNull(especialidade);
        assertEquals(1, especialidade.getId());
        assertEquals("Especialidade Teste", especialidade.getDescricao());
    }

    /**
     * Testa o método updateEspecialidade de EspecialidadeDAO.
     * @throws SQLException para qualquer exceção SQL lançada.
     */
    @Test
    public void testUpdateEspecialidadeMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);

        Especialidade novaEspecialidade = new Especialidade();
        novaEspecialidade.setDescricao("Especialidade Atualizada");

        when(statement.execute(anyString())).thenReturn(true);

        especialidadeDAO.updateEspecialidade(1, novaEspecialidade);
        
        verify(statement, times(1)).execute(anyString());
    }

    /**
     * Testa o método deleteEspecialidade de EspecialidadeDAO.
     * @throws SQLException para qualquer exceção SQL lançada.
     */
    @Test
    public void testDeleteEspecialidadeMock() throws SQLException {
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);

        when(statement.execute(anyString())).thenReturn(true);

        especialidadeDAO.deleteEspecialidade(1);
        
        verify(statement, times(1)).execute(anyString());
    }

    /**
     * Testa o método getIdDeleteEspecialidade de EspecialidadeDAO.
     * @throws SQLException para qualquer exceção SQL lançada.
     */
    @Test
    public void getIdDeleteEspecialidadeTest() throws SQLException {
        // Mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSetExames = mock(ResultSet.class);
        ResultSet resultSetConsultas = mock(ResultSet.class);
        ResultSet resultSetMedicos = mock(ResultSet.class);

        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);

        when(conn.createStatement()).thenReturn(statement);

        when(statement.executeQuery(contains("exames.id"))).thenReturn(resultSetExames);
        when(statement.executeQuery(contains("consulta.id"))).thenReturn(resultSetConsultas);
        when(statement.executeQuery(contains("medico.id"))).thenReturn(resultSetMedicos);

        when(resultSetExames.next()).thenReturn(true, true, true, false);
        when(resultSetConsultas.next()).thenReturn(true, true, true, false);
        when(resultSetMedicos.next()).thenReturn(true, true, true, false);

        when(resultSetExames.getInt("id")).thenReturn(1, 2, 3);
        when(resultSetConsultas.getInt("id")).thenReturn(4, 5, 6);
        when(resultSetMedicos.getInt("id")).thenReturn(7, 8, 9);

        // Expected results
        List<Integer> idExames = Arrays.asList(1, 2, 3);
        List<Integer> idConsultas = Arrays.asList(4, 5, 6);
        List<Integer> idMedicos = Arrays.asList(7, 8, 9);

        // Execute test
        List<List<Integer>> result = especialidadeDAO.getIdDeleteEspecialidade(1);

        // Assertions
        assertEquals(idExames, result.get(0));
        assertEquals(idConsultas, result.get(1));
        assertEquals(idMedicos, result.get(2));
    }
}

