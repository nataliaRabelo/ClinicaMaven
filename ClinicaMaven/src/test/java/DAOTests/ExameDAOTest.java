package DAOTests;

import conexao.ConexaoBancoDeDados;
import model.ExameDAO;
import aplicacao.Exame;
import aplicacao.Consulta;

import java.util.ArrayList;
import java.util.*;
import java.util.function.Supplier;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;


/**
 * Classe de teste para a classe ExameDAO.
 */
public class ExameDAOTest {

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
    
    @Test
    public void testExameDAO(){
        ExameDAO exame = new ExameDAO();
        assertNotNull(exame);
    }

    /**
     * Testa o método createExame da classe ExameDAO.
     * Verifica se um novo exame é cadastrado corretamente no banco de dados.
     */
    @Test
    public void testCreate_exame() {
        System.out.println("create_exame");
        
        Exame novo_exame = new Exame();
        novo_exame.setId(100);
        novo_exame.setDescricao("Exame de sangue");
        
        List total_exames = new ArrayList<Exame>();
        ExameDAO instance = new ExameDAO(conn);
        
        total_exames = instance.getExames();
        int qtd = total_exames.size();
        
        instance.createExame(novo_exame);
        
        total_exames = instance.getExames();
        assertNotEquals(total_exames.size(),qtd);
    }

    /**
     * Testa o método getExames da classe ExameDAO.
     * Verifica se a lista de exames retornada não está vazia.
     */
    @Test
    public void testGet_exames() {
        System.out.println("get_exames");
        ExameDAO instance = new ExameDAO(conn);
        List expResult = new ArrayList<Exame>();
        expResult = instance.getExames();
        assertNotNull(expResult);   
    }

    /**
     * Testa o método getExame da classe ExameDAO.
     * Verifica se um exame é retornado corretamente com base em seu ID.
     */
    @Test
    public void testGet_exame() {
        System.out.println("get_exame");
        int id_exame = 1;
        ExameDAO instance = new ExameDAO(conn);
        try{
            Exame result = instance.getExame(id_exame);
            assertNotNull(result);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    /**
     * Testa o método getExamesDaConsulta da classe ExameDAO.
     * Verifica se a lista de exames de uma consulta é retornada corretamente.
     */
    @Test
    public void testGet_examesDaConsulta() {
        System.out.println("get_examesDaConsulta");
        int id_consulta = 1;
        List lista_exames = new ArrayList<String>();
        ExameDAO instance = new ExameDAO(conn);
        try{
            lista_exames = instance.getExamesDaConsulta(id_consulta, lista_exames);
            assertNotNull(lista_exames);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    /**
     * Testa o método updateExame da classe ExameDAO.
     * Verifica se um exame é atualizado corretamente no banco de dados.
     */
    @Test
    public void testUpdate_exame() {
        System.out.println("update_exame");
        int id_exame = 1;
        Exame novo_exame = new Exame();
        Exame exp_exame = new Exame();
        novo_exame.setDescricao("Nova descricao");
        ExameDAO instance = new ExameDAO(conn);
        
        try{
            exp_exame = instance.getExame(id_exame);
            instance.updateExame(id_exame, novo_exame);
            
            novo_exame = instance.getExame(id_exame);
            
            assertEquals(exp_exame.getDescricao(),novo_exame.getDescricao());
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    /**
     * Testa o método deleteExame da classe ExameDAO.
     * Verifica se um exame é corretamente removido do banco de dados.
     */
    @Test
    public void testDelete_exame() {
        System.out.println("delete_exame");
        try{
            
            ExameDAO instance = new ExameDAO(conn);
            
            int idconsulta = 4; //considerando que ha uma consulta com id 4 no banco
            int idtipoexame = 3; //considerando que ha um tipo de exame com id 3 no banco
            
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("INSERT INTO exames (id, idtipoexame, idconsulta) VALUES (NULL, '" + idtipoexame + "', '" + idconsulta + "'");
            
            //exame inserido, agora iremos exclui lo

            resultSet = statement.executeQuery("SELECT * FROM exames");
            
            int idResultSet = 0;       
            
            if(resultSet.next()) {
                int id = resultSet.getInt("id"); //buscar pelo exame que foi adicionado
                idResultSet = id;
                instance.deleteExame(id); //deletar o exame
            }
            
            assertNull(instance.getExame(idResultSet));
            
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

    /**
     * Testa o método deleteTipoExame da classe ExameDAO.
     * Verifica se um tipo de exame é corretamente removido do banco de dados.
     */
    @Test
    public void testDelete_tipoExame() {
        System.out.println("delete_tipoExame");
        
        try{
        
            ExameDAO instance = new ExameDAO(conn);
            Exame novo_exame = new Exame();
            novo_exame.setDescricao("Novo Teste de Exame");

            instance.createExame(novo_exame);

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tipoexame WHERE tipoexame.descricao = '" + novo_exame.getDescricao() + "'");
            int idResultSet = 0; 
            
            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                idResultSet = id;
                instance.deleteTipoExame(id); 
            }
            
            Exame exp_exame = instance.getExame(idResultSet);
            assertNull(exp_exame.getDescricao(), "O exame deve ser null após ser deletado");
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

    /**
     * Testa o método getIdDeleteExame da classe ExameDAO.
     * Verifica se os IDs dos exames e consultas associados a um exame são corretamente obtidos.
     */
    @Test
    public void testGet_idDeleteExame() {
        System.out.println("get_idDeleteExame");
        int id_exame = 1; //considerando que se deseja buscar o id do exame 1
        ExameDAO instance = new ExameDAO(conn);
        try{
            List id_delete = instance.getIdDeleteExame(id_exame);
            assertNotNull(id_delete);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    // --------------- TESTES COM MOCK --------------
    
     /**
     * Testa o método createExame da classe ExameDAO.
     * Verifica se o método executa corretamente a instrução SQL esperada.
     */
    @Test
    public void testCreateExameMock() throws SQLException {
        // Mock objects
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        Exame exame = new Exame();
        exame.setDescricao("Exame de sangue");

        // Define the mock behavior
        when(conn.createStatement()).thenReturn(statement);

        // Call the method to test
        ExameDAO exameDao = new ExameDAO(conn);
        exameDao.createExame(exame);

        // Verify the results
        verify(statement, times(1)).execute("INSERT INTO tipoexame (descricao) VALUES ( 'Exame de sangue')");
    }

     /**
     * Testa o método getExames da classe ExameDAO.
     * Verifica se o método retorna a lista de exames correta a partir dos dados do resultSet.
     */
    @Test
    public void testGetExamesMock() throws SQLException {
        // Mock objects
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Define the mock behavior
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT * FROM tipoexame")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("descricao")).thenReturn("Exame de sangue");

        // Call the method to test
        ExameDAO exameDao = new ExameDAO(conn);
        List<Exame> exames = exameDao.getExames();

        // Verify the results
        assertEquals(1, exames.size());
        assertEquals(1, exames.get(0).getId());
        assertEquals("Exame de sangue", exames.get(0).getDescricao());
    }
    
     /**
     * Testa o método getExame da classe ExameDAO.
     * Verifica se o método retorna o exame correto a partir dos dados do resultSet.
     */
    @Test
    public void testGetExameMock() throws SQLException {
        // Mock objects
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Define the mock behavior
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT * FROM tipoexame WHERE tipoexame.id=1")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("descricao")).thenReturn("Exame de sangue");

        // Call the method to test
        ExameDAO exameDao = new ExameDAO(conn);
        Exame exame = exameDao.getExame(1);

        // Verify the results
        assertEquals(1, exame.getId());
        assertEquals("Exame de sangue", exame.getDescricao());
    }

     /**
     * Testa o método updateExame da classe ExameDAO.
     * Verifica se o método executa corretamente a instrução SQL esperada.
     */
    @Test
    public void testUpdateExameMock() throws SQLException {
        // Mock objects
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        Exame exame = new Exame();
        exame.setDescricao("Exame de sangue atualizado");

        // Define the mock behavior
        when(conn.createStatement()).thenReturn(statement);

        // Call the method to test
        ExameDAO exameDao = new ExameDAO(conn);
        exameDao.updateExame(1, exame);

        // Verify the results
        verify(statement, times(1)).execute("UPDATE tipoexame SET descricao='Exame de sangue atualizado' WHERE tipoexame.id=1");
    }
    
     /**
     * Testa o método deleteExame da classe ExameDAO.
     * Verifica se o método executa corretamente a instrução SQL esperada.
     */
    @Test
    public void testDeleteExameMock() throws SQLException {
        // Mock objects
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);

        // Define the mock behavior
        when(conn.createStatement()).thenReturn(statement);

        // Call the method to test
        ExameDAO exameDao = new ExameDAO(conn);
        exameDao.deleteExame(1);

        // Verify the results
        verify(statement, times(1)).execute("DELETE FROM exames WHERE exames.id=1");
    }

       /**
     * Testa o método getIdDeleteExame da classe ExameDAO.
     * Verifica se o método retorna a lista de IDs de exame correta a partir dos dados do resultSet.
     */
    @Test
    public void testGetIdDeleteExameMock() throws SQLException {
        // Mock objects
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        // Define the mock behavior
        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT exames.id FROM exames WHERE exames.idtipoexame=1")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);

        // Call the method to test
        ExameDAO exameDao = new ExameDAO(conn);
        List<Integer> idExames = exameDao.getIdDeleteExame(1);

        // Verify the results
        assertEquals(1, idExames.size());
        assertEquals(1, idExames.get(0));
    }
    
    @Test
    public void testCreateExameSQLException() throws SQLException {
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ExameDAO exameDAO = new ExameDAO(conn);
        Exame exame = mock(Exame.class);

        // Configurando o comportamento dos mocks
        when(exame.getDescricao()).thenReturn("teste");
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        exameDAO.createExame(exame);

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testGetExamesSQLException() throws SQLException {
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ExameDAO exameDAO = new ExameDAO(conn);
        
        // Configurando o comportamento dos mocks
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        exameDAO.getExames();

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testGetExameSQLException() throws SQLException {
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ExameDAO exameDAO = new ExameDAO(conn);
        Exame exame = mock(Exame.class);

        // Configurando o comportamento dos mocks
        when(exame.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        exameDAO.getExame(exame.getId());

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testGetExamesDaConsultaSQLException() throws SQLException {
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ExameDAO exameDAO = new ExameDAO(conn);
        Exame exame = mock(Exame.class);

        // Configurando o comportamento dos mocks
        when(exame.getDescricao()).thenReturn("teste");
        when(exame.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);
        
        List<String> lista = new ArrayList<>();
        lista.add(exame.getDescricao());

        // Chamando o método a ser testado
        exameDAO.getExamesDaConsulta(exame.getId(),lista);

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testUpdateExameSQLException() throws SQLException {
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ExameDAO exameDAO = new ExameDAO(conn);
        Exame exame = mock(Exame.class);

        // Configurando o comportamento dos mocks
        when(exame.getDescricao()).thenReturn("teste");
        when(exame.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        exameDAO.updateExame(exame.getId(),exame);

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testDeleteExameSQLException() throws SQLException {
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ExameDAO exameDAO = new ExameDAO(conn);
        Exame exame = mock(Exame.class);

        // Configurando o comportamento dos mocks
        when(exame.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        exameDAO.deleteExame(exame.getId());

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testDeleteTipoExameSQLException() throws SQLException {
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ExameDAO exameDAO = new ExameDAO(conn);
        Exame exame = mock(Exame.class);

        // Configurando o comportamento dos mocks
        when(exame.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        exameDAO.deleteTipoExame(exame.getId());

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testGetIdDeleteExameSQLException() throws SQLException {
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ExameDAO exameDAO = new ExameDAO(conn);
        Exame exame = mock(Exame.class);

        // Configurando o comportamento dos mocks
        when(exame.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        exameDAO.getIdDeleteExame(exame.getId());

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
}

