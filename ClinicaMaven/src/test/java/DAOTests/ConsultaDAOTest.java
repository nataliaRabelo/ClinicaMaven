package DAOTests;

import aplicacao.Consulta;
import model.ConsultaDAO;
import conexao.ConexaoBancoDeDados;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

/**
* Classe de teste para a classe ConsultaDAO.
*/
public class ConsultaDAOTest {

    /**
     * Configuração inicial para todos os testes.
     * Estabelece uma conexão com o banco de dados.
     */
    private static Connection conn;

    @BeforeAll
    public static void setUp() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }
    
    /**
     * Testa o método createConsulta da classe ConsultaDAO.
     * Verifica se o número de consultas aumentou em 1 após a inserção.
     */
    @Test
    public void testCreateConsulta() {
        ConsultaDAO consultaDAO = new ConsultaDAO(conn);
        Consulta consulta = new Consulta();
        consulta.setData("2023-07-17");
        consulta.setDescricao("Consulta de rotina");
        consulta.setRealizada('N');
        consulta.setIdmedico(1); // ID válido
        consulta.setIdpaciente(1); // ID válido

        // Obtenha o número atual de consultas para esse paciente
        int numConsultasAntes = consultaDAO.getConsultas(consulta.getIdpaciente()).size();

        consultaDAO.createConsulta(consulta);

        // Obtenha o número atual de consultas para esse paciente novamente, após a inserção
        int numConsultasDepois = consultaDAO.getConsultas(consulta.getIdpaciente()).size();

        // Verifique se o número de consultas aumentou em 1
        assertEquals(numConsultasAntes + 1, numConsultasDepois);
    }
    
    /**
     * Testa o método getConsulta da classe ConsultaDAO.
     * Verifica se a consulta retornada não é nula.
     */
    @Test
    public void testGetConsultas() {
        int id_paciente = 1; // Considerando que nosso paciente 1 sempre nasce com uma consulta quando o banco é alimentado.
        ConsultaDAO instance = new ConsultaDAO(conn);
        List<Consulta> expResult = new ArrayList();
        try{
            expResult = instance.getConsultas(id_paciente);
            assertNotNull(expResult);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }
    
    /**
     * Testa o método getConsultas da classe ConsultaDAO.
     * Verifica se a lista de consultas retornada não é nula.
     */
    @Test
    public void testGetConsulta() {
        int id_consulta = 1; // Considerando que já há uma consulta 1 com a construção do banco.
        ConsultaDAO instance = new ConsultaDAO(conn);
        Consulta expResult = new Consulta();
        try{
            expResult = instance.getConsulta(id_consulta);
            assertNotNull(expResult);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }
    
    /**
     * Testa o método getConsulta da classe ConsultaDAO.
     * Verifica se a consulta retornada não é nula.
     */
    @Test
    public void testGetMedicoEspecialidade() {
        int id_consulta = 1; // Considerando que já há uma consulta 1 com a construção do banco e esta sempre tem um médico associado por chave estrangeira.
        List<Object> medico_descricao = new ArrayList<Object>();
        ConsultaDAO instance = new ConsultaDAO(conn);
        List<Object> expResult = null;
        try{    
            expResult = instance.getMedicoEspecialidade(id_consulta, medico_descricao);
            assertNotNull(expResult);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    /**
    * Testa o método getProcedimentosDisponiveis da classe ConsultaDAO.
    */
    @Test
    public void testGetProcedimentosDisponiveis() {
        ConsultaDAO instance = new ConsultaDAO(conn);
        List<Object> expResult = new ArrayList<Object>();
        try{
        expResult = instance.getProcedimentosDisponiveis(); // Considerando que já há especialidades no banco.
        assertNotNull(expResult);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    /**
    * Testa o método updateConsulta da classe ConsultaDAO.
    */
    @Test
    public void testUpdateConsulta() {
        ConsultaDAO consultaDAO = new ConsultaDAO(conn);
        int idConsulta = 1; // ID válido
        Consulta novaConsulta = new Consulta();
        novaConsulta.setData("2023-07-17 00:00:00");
        novaConsulta.setDescricao("Consulta atualizada");
        novaConsulta.setRealizada('S');
        novaConsulta.setIdmedico(1); // ID válido
        novaConsulta.setIdpaciente(1); // ID válido

        consultaDAO.updateConsulta(idConsulta, novaConsulta);

        // Recupera a consulta atualizada do banco de dados
        Consulta consultaAtualizada = consultaDAO.getConsulta(idConsulta);

        // Agora você pode comparar os campos da consulta recuperada com os dados que você usou para a atualização
        assertEquals(novaConsulta.getData(), consultaAtualizada.getData());
        assertEquals(novaConsulta.getDescricao(), consultaAtualizada.getDescricao());
        assertEquals(novaConsulta.getRealizada(), consultaAtualizada.getRealizada());
        assertEquals(novaConsulta.getIdmedico(), consultaAtualizada.getIdmedico());
        assertEquals(novaConsulta.getIdpaciente(), consultaAtualizada.getIdpaciente());
    }

    /**
   * Testa o método deleteConsulta da classe ConsultaDAO.
   */
    @Test
    public void testDeleteConsulta() {
        try {
            // Arrange
            ConsultaDAO consultaDAO = new ConsultaDAO(conn);  
            Consulta consulta = new Consulta(); // Criando uma consulta para testar a deleção
            consulta.setDescricao("Exame de sangue");
            consulta.setData("2023-07-17 00:41:00");
            consultaDAO.createConsulta(consulta);

            // Criando o Statement para executar a query
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta WHERE consulta.descricao = '" + consulta.getDescricao() + " and consulta.data ='" + consulta.getData() + "'");
            int idResultSet = 0; 
            // Verificando se a consulta retornou algum resultado
            if(resultSet.next()) {
                // Act
                int id = resultSet.getInt("id"); // Supondo que a coluna do id na tabela seja 'id'
                idResultSet = id;
                Consulta consultaApagada = consultaDAO.getConsulta(id); // deletando a consulta que acabei de criar, onde o id é inserido no banco
            }

            // Assert
            Consulta consultaQuery = consultaDAO.getConsulta(idResultSet);
            assertNull(consultaQuery.getDescricao(), "A consulta deveria ser null após ser deletado");
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

    // ---- TESTES COM MOCK -------------

    /**
    * Testa o método createConsulta utilizando Mockito.
    *
    * @throws Exception se ocorrer uma exceção durante o teste
    */
    @Test
    public void testCreateConsultaMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.execute(anyString())).thenReturn(true);
        ConsultaDAO dao = new ConsultaDAO(conn);

        Consulta consulta = new Consulta();
        consulta.setData("2023-06-25");
        consulta.setDescricao("consulta teste");
        consulta.setRealizada('S');
        consulta.setIdmedico(1);
        consulta.setIdpaciente(1);

        // Act
        dao.createConsulta(consulta);

        // Assert
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }

    /**
    * Testa o método getConsultas utilizando Mockito.
    *
    * @throws Exception se ocorrer uma exceção durante o teste
    */
    @Test
    public void testGetConsultasMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("teste");
        ConsultaDAO dao = new ConsultaDAO(conn);

        // Act
        List<Consulta> consultas = dao.getConsultas(1);

        // Assert
        assertNotNull(consultas);
        assertEquals(1, consultas.size());
        assertEquals("teste", consultas.get(0).getDescricao());
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    /**
    * Testa o método getConsulta utilizando Mockito.
    *
    * @throws Exception se ocorrer uma exceção durante o teste
    */
    @Test
    public void testGetConsultaMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("teste");
        ConsultaDAO dao = new ConsultaDAO(conn);


        // Act
        Consulta consulta = dao.getConsulta(1);

        // Assert
        assertNotNull(consulta);
        assertEquals("teste", consulta.getDescricao());
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    /**
    * Testa o método updateConsulta utilizando Mockito.
    *
    * @throws Exception se ocorrer uma exceção durante o teste
    */
    @Test
    public void testUpdateConsultaMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.execute(anyString())).thenReturn(true);
        ConsultaDAO dao = new ConsultaDAO(conn);

        Consulta consulta = new Consulta();
        consulta.setData("2023-06-25");
        consulta.setDescricao("consulta teste atualizada");
        consulta.setRealizada('S');
        consulta.setIdmedico(1);
        consulta.setIdpaciente(1);

        // Act
        dao.updateConsulta(1, consulta);

        // Assert
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }

    /**
    * Testa o método deleteConsulta utilizando Mockito.
    *
    * @throws Exception se ocorrer uma exceção durante o teste
    */
    @Test
    public void testDeleteConsultaMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.execute(anyString())).thenReturn(true);
        ConsultaDAO dao = new ConsultaDAO(conn);

        // Act
        dao.deleteConsulta(1);

        // Assert
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }

    /**
    * Testa o método getMedicoEspecialidade utilizando Mockito.
    *
    * @throws Exception se ocorrer uma exceção durante o teste
    */
    @Test
    public void testGetMedicoEspecialidadeMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString(anyString())).thenReturn("teste");
        ConsultaDAO dao = new ConsultaDAO(conn);
        List<Object> medicoDescricao = new ArrayList<>(); 

        // Act
        medicoDescricao = dao.getMedicoEspecialidade(1, medicoDescricao);

        // Assert
        assertNotNull(medicoDescricao);
        assertEquals(2, medicoDescricao.size());
        assertEquals("teste", medicoDescricao.get(0));
        assertEquals("teste", medicoDescricao.get(1));
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    /**
    * Testa o método getProcedimentosDisponiveis utilizando Mockito.
    *
    * @throws Exception se ocorrer uma exceção durante o teste
    */
    @Test
    public void testGetProcedimentosDisponiveisMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false); // Return true twice to simulate two rows in the result set
        when(resultSet.getString("descricao")).thenReturn("teste");
        when(resultSet.getString("nome")).thenReturn("teste");
        when(resultSet.getInt("id")).thenReturn(1);
        ConsultaDAO dao = new ConsultaDAO(conn);

        // Act
        List<Object> procedimentos = dao.getProcedimentosDisponiveis();

        // Assert
        assertNotNull(procedimentos);
        assertEquals(6, procedimentos.size()); // The list should have 6 elements, two rows of three items each
        assertEquals("teste", procedimentos.get(0));
        assertEquals("teste", procedimentos.get(1));
        assertEquals(1, procedimentos.get(2));
        assertEquals("teste", procedimentos.get(3));
        assertEquals("teste", procedimentos.get(4));
        assertEquals(1, procedimentos.get(5));
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }
}

