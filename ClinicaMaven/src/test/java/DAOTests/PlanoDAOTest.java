package DAOTests;

import aplicacao.Plano;
import model.PlanoDAO;
import conexao.ConexaoBancoDeDados;
import utils.Constantes;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para a classe PlanoDAO.
 */
public class PlanoDAOTest {

    private static Connection conn;

   /**
   * Configuração inicial para os testes, estabelecendo a conexão com o banco de dados.
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
    public void testPlanoDAO(){
        PlanoDAO plano = new PlanoDAO();
        assertNotNull(plano);
    }

    /**
   * Testa o método createPlano da classe PlanoDAO.
   * Verifica se a inserção de um novo plano aumenta o número de planos na lista.
   */    
    @Test
    public void testCreatePlano() {
        PlanoDAO dao = new PlanoDAO(conn);
        Plano novo_plano = new Plano();
        novo_plano.setDescricao("Plano Teste");

        // Obtenha o número atual de consultas para esse paciente
        int numPlanosAntes = dao.getPlanos().size();

        dao.createPlano(novo_plano);

        // Obtenha o número atual de consultas para esse paciente novamente, após a inserção
        int numPlanosDepois = dao.getPlanos().size();

        // Verifique se o número de consultas aumentou em 1
        assertEquals(numPlanosAntes + 1, numPlanosDepois);
    }

    /**
    * Testa o método getPlano da classe PlanoDAO.
    * Verifica se é possível obter um plano com base em um ID válido.
    */    
    @Test
    public void testGetPlano() {
        PlanoDAO dao = new PlanoDAO(conn);
        Plano plano = dao.getPlano(1); // assume que há um plano com id 1 no BD
        Assertions.assertNotNull(plano, "Plano não deveria ser null");
    }

   /**
   * Testa o método updatePlano da classe PlanoDAO.
   * Verifica se é possível atualizar os dados de um plano existente.
   */    
    @Test
    public void testUpdatePlano() {
        PlanoDAO dao = new PlanoDAO(conn);
        Plano novo_plano = new Plano();
        novo_plano.setDescricao("ABC");

        dao.updatePlano(1, novo_plano); // assume que há um plano com id 1 no BD

        Plano retrieved = dao.getPlano(1);
        assertEquals(novo_plano.getDescricao(), retrieved.getDescricao());
    }


    /**
     * Testa o método deletePlano da classe PlanoDAO.
     * Verifica se é possível deletar um plano existente.
     */    
    @Test
    public void testDeletePlano() {
        try {
            // Arrange
            PlanoDAO dao = new PlanoDAO(conn); 
            Plano plano = new Plano(); // Criando um paciente para testar a deleção
            plano.setDescricao("Bradesco");
            dao.createPlano(plano);

            // Criando o Statement para executar a query
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paciente WHERE plano.descricao = '" + plano.getDescricao() + "'");
            int idResultSet = 0; 
            // Verificando se a consulta retornou algum resultado
            if(resultSet.next()) {
                // Act
                int id = resultSet.getInt("id"); // Supondo que a coluna do id na tabela seja 'id'
                idResultSet = id;
                dao.deletePlano(id); // deletando o usuário que acabei de criar, onde o id é inserido no banco
            }

            // Assert
            Plano plano2 = dao.getPlano(idResultSet);
            assertNull(plano2.getDescricao(), "O plano deveria ser null após ser deletado");
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

    /**
    * Testa o método getIdDeletePlano da classe PlanoDAO.
    * Verifica se os IDs dos exames e consultas associados a um plano são corretamente obtidos.
    */
    @Test
    public void testGetIdDeletePlano() {
        PlanoDAO dao = new PlanoDAO(conn); 
        int id = 1; // assumindo que o plano 1 tem já exames e consultas associadas.

        List<List<Integer>> id_compilado = dao.getIdDeletePlano(id);

        // Assert
        assertFalse(id_compilado.get(0).isEmpty(), "A lista de ids de exames não deve estar vazia");
        assertFalse(id_compilado.get(1).isEmpty(), "A lista de ids de consultas não deve estar vazia");
    }

    // ---------- TESTES COM MOCK

        /**
     * Teste do método createPlano utilizando mock.
     */
    @Test
    public void testCreatePlanoMock() throws SQLException {
        // Configuração do mock
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);

        when(conn.createStatement()).thenReturn(statement);

        // Dado um novo plano
        Plano novoPlano = new Plano();
        novoPlano.setDescricao("Plano de teste");

        // Quando o método createPlano é chamado
        planoDAO.createPlano(novoPlano);

        // Verifica se o SQL de inserção foi executado corretamente
        verify(statement).execute("INSERT INTO tipoplano (descricao) VALUES ( 'Plano de teste')");
    }

    /**
     * Teste do método getPlanos utilizando mock.
     */
    @Test
    public void testGetPlanosMock() throws SQLException {
        // Configuração do mock
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);

        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT * FROM tipoplano")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("descricao")).thenReturn("Plano 1", "Plano 2");

        // Quando o método getPlanos é chamado
        List<Plano> planos = planoDAO.getPlanos();

        // Verifica se o resultado contém os planos esperados
        assertEquals(2, planos.size());
        assertEquals(1, planos.get(0).getId());
        assertEquals("Plano 1", planos.get(0).getDescricao());
        assertEquals(2, planos.get(1).getId());
        assertEquals("Plano 2", planos.get(1).getDescricao());
    }

    /**
     * Teste do método getPlano utilizando mock.
     */
    @Test
    public void testGetPlanoMock() throws SQLException {
        // Configuração do mock
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);

        when(conn.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT * FROM tipoplano " + Constantes.WHERETIPOPLANO + "1")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("descricao")).thenReturn("Plano de teste");

        // Quando o método getPlano é chamado
        Plano plano = planoDAO.getPlano(1);

        // Verifica se o resultado é o plano esperado
        assertEquals(1, plano.getId());
        assertEquals("Plano de teste", plano.getDescricao());
    }

    /**
     * Teste do método updatePlano utilizando mock.
     */
    @Test
    public void testUpdatePlanoMock() throws SQLException {
        // Configuração do mock
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);

        when(conn.createStatement()).thenReturn(statement);

        // Dado um ID de plano e um novo plano
        int idPlano = 1;
        Plano novoPlano = new Plano();
        novoPlano.setDescricao("Plano atualizado");

        // Quando o método updatePlano é chamado
        planoDAO.updatePlano(idPlano, novoPlano);

        // Verifica se o SQL de atualização foi executado corretamente
        verify(statement).execute("UPDATE tipoplano SET descricao='Plano atualizado' WHERE tipoplano.id=1");
    }

    /**
     * Teste do método deletePlano utilizando mock.
     */
    @Test
    public void testDeletePlanoMock() throws SQLException {
        // Configuração do mock
        Connection conn = Mockito.mock(Connection.class);
        Statement statement = Mockito.mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);

        when(conn.createStatement()).thenReturn(statement);

        // Dado um ID de plano
        int idPlano = 1;

        // Quando o método deletePlano é chamado
        planoDAO.deletePlano(idPlano);

        // Verifica se o SQL de exclusão foi executado corretamente
        verify(statement).execute("DELETE FROM tipoplano WHERE tipoplano.id=1");
    }

    /**
     * Teste do método getIdDeletePlano utilizando mock.
     */
    @Test
    public void testGetIdDeletePlanoMock() throws SQLException {
        // Criar mocks dos objetos necessários
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet1 = mock(ResultSet.class);
        ResultSet resultSet2 = mock(ResultSet.class);
        ResultSet resultSet3 = mock(ResultSet.class);

        // Configurar o comportamento do mock Connection
        when(conn.createStatement()).thenReturn(statement);

        // Configurar o comportamento do mock Statement
        when(statement.executeQuery(Mockito.anyString()))
                .thenReturn(resultSet1)
                .thenReturn(resultSet2)
                .thenReturn(resultSet3);

        // Configurar o comportamento do mock ResultSet
        when(resultSet1.next()).thenReturn(true, false);
        when(resultSet1.getInt("id")).thenReturn(1);
        when(resultSet2.next()).thenReturn(true, false);
        when(resultSet2.getInt("id")).thenReturn(2);
        when(resultSet3.next()).thenReturn(true, false);
        when(resultSet3.getInt("id")).thenReturn(3);

        // Criar instância do objeto em teste
        PlanoDAO planoDAO = new PlanoDAO(conn);

        // Chamar o método a ser testado
        List<List<Integer>> resultado = planoDAO.getIdDeletePlano(1);

        // Verificar se o método retornou o resultado esperado
        List<List<Integer>> resultadoEsperado = Arrays.asList(
                Arrays.asList(1),
                Arrays.asList(2),
                Arrays.asList(3)
        );
        assertEquals(resultadoEsperado, resultado);

        // Verificar se os métodos do mock foram chamados corretamente
        verify(conn, times(1)).createStatement();
        verify(statement, times(3)).executeQuery(Mockito.anyString());
        verify(resultSet1, times(2)).next();
        verify(resultSet1, times(1)).getInt("id");
        verify(resultSet2, times(2)).next();
        verify(resultSet2, times(1)).getInt("id");
        verify(resultSet3, times(2)).next();
        verify(resultSet3, times(1)).getInt("id");
    }
    
    @Test
    public void testCreatePlanoSQLException() throws SQLException{
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);
        Plano plano = mock(Plano.class);

        // Configurando o comportamento dos mocks
        when(plano.getDescricao()).thenReturn("teste");
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        planoDAO.createPlano(plano);

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testGetPlanosSQLException() throws SQLException{
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);
        
        // Configurando o comportamento dos mocks
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        planoDAO.getPlanos();

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testGetPlanoSQLException() throws SQLException{
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);
        Plano plano = mock(Plano.class);

        // Configurando o comportamento dos mocks
        when(plano.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        planoDAO.getPlano(plano.getId());

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testUpdatePlanoSQLException() throws SQLException{
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);
        Plano plano = mock(Plano.class);

        // Configurando o comportamento dos mocks
        when(plano.getDescricao()).thenReturn("teste");
        when(plano.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        planoDAO.updatePlano(plano.getId(),plano);

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testDeletePlanoSQLException() throws SQLException{
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);
        Plano plano = mock(Plano.class);

        // Configurando o comportamento dos mocks
        when(plano.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        planoDAO.deletePlano(plano.getId());

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
    
    @Test
    public void testGetIdDeletePlanoSQLException() throws SQLException{
        // Criando e configurando os mocks
        Connection conn = mock(Connection.class);
        Statement statement = mock(Statement.class);
        PlanoDAO planoDAO = new PlanoDAO(conn);
        Plano plano = mock(Plano.class);

        // Configurando o comportamento dos mocks
        when(plano.getId()).thenReturn(1);
        when(conn.createStatement()).thenThrow(SQLException.class);

        // Chamando o método a ser testado
        planoDAO.getIdDeletePlano(plano.getId());

        // Verificando se a SQLException foi lançada
        verify(conn).createStatement();
    }
}
