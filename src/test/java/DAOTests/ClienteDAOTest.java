package DAOTests;

import aplicacao.Cliente;
import model.ClienteDAO;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteDAOTest {
    @Test
    public void testLogin() throws Exception {
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
    public void testJaCadastrado() throws Exception {
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
    public void testGetNomePaciente() throws Exception {
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
        String result = dao.get_nomePaciente(1);

        // Assert
        assertEquals("Teste", result);
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

        @Test
    public void testGetPacientes() throws Exception {
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
        List<Cliente> result = dao.get_pacientes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Teste", result.get(0).getNome());
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    @Test
    public void testGetPaciente() throws Exception {
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
        Cliente result = dao.get_paciente(1);

        // Assert
        assertNotNull(result);
        assertEquals("Teste", result.getNome());
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).executeQuery(anyString());
    }

    @Test
    public void testUpdatePaciente() throws Exception {
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
        dao.update_paciente(1, paciente);

        // Assert
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }
    
    @Test
    public void testGetIdDeletePaciente() throws Exception {
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
        ArrayList<ArrayList<Integer>> result = dao.get_idDeletePaciente(1);

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
    public void testDeletePaciente() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        when(conn.createStatement()).thenReturn(statement);
        ClienteDAO dao = new ClienteDAO(conn);

        // Act
        dao.delete_paciente(1);

        // Assert
        verify(conn, times(1)).createStatement();
        verify(statement, times(1)).execute(anyString());
    }
}
