package DAOTests;

import aplicacao.Cliente;
import model.ClienteDAO;
import conexao.ConexaoBancoDeDados;

import java.util.List;
import java.util.ArrayList;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import conexao.ConexaoBancoDeDados;

import org.junit.jupiter.api.BeforeAll;

/**
* @author Natália
*/
public class ClienteDAOTest {
    
    private static Connection conn;

    @BeforeAll
    public static void setUp() {
        try {
            conn = ConexaoBancoDeDados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }

    @Test
    public void testCreatePaciente() {
        // Arrange
        ClienteDAO dao = new ClienteDAO(conn);
        Cliente novoPaciente = new Cliente();
        novoPaciente.setNome("João Teste");
        novoPaciente.setCpf("12345678901");
        novoPaciente.setSenha("111");
        novoPaciente.setAutorizado('S');
        novoPaciente.setIdtipoplano(1);

        // Act
        dao.createPaciente(novoPaciente);

        // Assert
        boolean isCadastrado = dao.jaCadastrado("12345678901");
        assertTrue(isCadastrado, "O paciente deveria estar cadastrado após a chamada a create_paciente");
    }
    
    @Test
    public void testLogin() {
        // Arrange
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        Cliente paciente = dao.login("937.397.160-37", "111");

        // Assert
        assertNotNull(paciente);
        assertEquals("Maria", paciente.getNome());
    }
    
    @Test
    public void testGetPaciente() {
        // Arrange
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        Cliente paciente = dao.getPaciente(1); // Necessário que exista um paciente com ID 1 no banco de dados de teste

        // Assert
        assertNotNull(paciente);
        assertEquals("Maria", paciente.getNome());
    }
    
    @Test
    public void testUpdatePaciente() {
        // Arrange
        ClienteDAO dao = new ClienteDAO(conn);
        Cliente paciente = dao.getPaciente(1); // Certifique-se de que existe um paciente com ID 1
        paciente.setNome("Maria2");

        // Act
        dao.updatePaciente(1, paciente);

        // Assert
        Cliente pacienteAtualizado = dao.getPaciente(1);
        assertEquals("Maria2", pacienteAtualizado.getNome());
        
        //Retornando o nome como estava antes para não causar inconsistências no banco
        paciente.setNome("Maria");
        dao.updatePaciente(1, paciente);
    }
    
    @Test
    public void testGetNomePaciente() {
        // Arrange
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        String nome = dao.getNomePaciente(1); // Certifique-se de que existe um paciente com ID 1

        // Assert
        assertEquals("Maria", nome);
    }

    @Test
    public void testGetPacientes() {
        // Arrange
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        ArrayList<Cliente> pacientes = dao.getPacientes();

        // Assert
        assertFalse(pacientes.isEmpty(), "A lista de pacientes não deve estar vazia");
    }

    @Test
    public void testDeletePaciente() {
        try {
            // Arrange
            ClienteDAO dao = new ClienteDAO(conn);  
            Cliente cliente = new Cliente(); // Criando um paciente para testar a deleção
            cliente.setNome("natalia");
            cliente.setCpf("123");
            dao.createPaciente(cliente);

            // Criando o Statement para executar a query
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente WHERE paciente.cpf = '" + cliente.getCpf() + "'");
            int idResultSet = 0; 
            // Verificando se a consulta retornou algum resultado
            if(resultSet.next()) {
                // Act
                int id = resultSet.getInt("id"); // Supondo que a coluna do id na tabela seja 'id'
                idResultSet = id;
                dao.deletePaciente(id); // deletando o usuário que acabei de criar, onde o id é inserido no banco
            }

            // Assert
            Cliente paciente = dao.getPaciente(idResultSet);
            assertNull(paciente.getNome(), "O paciente deveria ser null após ser deletado");
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

@Test
public void testGetIdDeletePaciente() {
    try {
        // Arrange
        ClienteDAO dao = new ClienteDAO(conn);
        
        Cliente cliente = new Cliente(); // Criando um paciente para testar a deleção
        cliente.setNome("natalia");
        cliente.setCpf("123");
        dao.createPaciente(cliente);

        // Criando o Statement para executar a query
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente WHERE paciente.cpf = '" + cliente.getCpf() + "'");

        // Verificando se a consulta retornou algum resultado
        if(resultSet.next()) {
            // Act
            int id = resultSet.getInt("id"); // Supondo que a coluna do id na tabela seja 'id'
            ArrayList<ArrayList<Integer>> id_compilado = dao.getIdDeletePaciente(id);

            // Assert
            assertFalse(id_compilado.get(0).isEmpty(), "A lista de ids de exames não deve estar vazia");
            assertFalse(id_compilado.get(1).isEmpty(), "A lista de ids de consultas não deve estar vazia");
        }
    } catch (SQLException e) {
        System.out.println("Erro SQL: " + e.getMessage());
    }
}

    // ------------------------ TESTES COM MOCK --------------------------

    @Test
    public void testLoginMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nome")).thenReturn("Teste");
        when(resultSet.getString("cpf")).thenReturn("12345678901");
        when(resultSet.getString("senha")).thenReturn("senha");
        when(resultSet.getString("autorizado")).thenReturn("S");
        when(resultSet.getInt("idtipoplano")).thenReturn(1);
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        Cliente result = dao.login("12345678901", "senha");

        // Assert
        assertNotNull(result);
        assertEquals("Teste", result.getNome());
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
        verify(resultSet, times(1)).getInt("id");
        verify(resultSet, times(1)).getString("nome");
        verify(resultSet, times(1)).getString("cpf");
        verify(resultSet, times(1)).getString("senha");
        verify(resultSet, times(1)).getString("autorizado");
        verify(resultSet, times(1)).getInt("idtipoplano");
    }

    @Test
    public void testJaCadastradoMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        boolean result = dao.jaCadastrado("12345678901");

        // Assert
        assertTrue(result);
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    @Test
    public void testGetNomePacienteMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("nome")).thenReturn("Teste");
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        String result = dao.getNomePaciente(1);

        // Assert
        assertEquals("Teste", result);
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    @Test
    public void testGetPacientesMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nome")).thenReturn("Teste");
        when(resultSet.getString("cpf")).thenReturn("12345678901");
        when(resultSet.getString("senha")).thenReturn("senha");
        when(resultSet.getString("autorizado")).thenReturn("S");
        when(resultSet.getInt("idtipoplano")).thenReturn(1);
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        List<Cliente> result = dao.getPacientes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Teste", result.get(0).getNome());
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    @Test
    public void testGetPacienteMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nome")).thenReturn("Teste");
        when(resultSet.getString("cpf")).thenReturn("12345678901");
        when(resultSet.getString("senha")).thenReturn("senha");
        when(resultSet.getString("autorizado")).thenReturn("S");
        when(resultSet.getInt("idtipoplano")).thenReturn(1);
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        Cliente result = dao.getPaciente(1);

        // Assert
        assertNotNull(result);
        assertEquals("Teste", result.getNome());
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    @Test
    public void testUpdatePacienteMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        ClienteDAO dao = new ClienteDAO(conn);
        Cliente paciente = new Cliente();
        paciente.setId(1);
        paciente.setNome("Teste");
        paciente.setCpf("12345678901");
        paciente.setSenha("senha");
        paciente.setAutorizado('S');
        paciente.setIdtipoplano(1);

        // Act
        dao.updatePaciente(1, paciente);

        // Assert
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }
    
    @Test
    public void testGetIdDeletePacienteMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet, resultSet2);
        when(resultSet.next()).thenReturn(true).thenReturn(false); 
        when(resultSet2.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet2.getInt("id")).thenReturn(1);
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        ArrayList<ArrayList<Integer>> result = dao.getIdDeletePaciente(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); 
        assertEquals(1, result.get(0).size()); 
        assertEquals(1, result.get(1).size());
        assertEquals(1, (int) result.get(0).get(0));
        assertEquals(1, (int) result.get(1).get(0));
        verify(conn, times(1)).createStatement();
        verify(statement, times(2)).executeQuery(anyString());
    }

    @Test
    public void testDeletePacienteMock() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        dao.deletePaciente(1);

        // Assert
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }
}
