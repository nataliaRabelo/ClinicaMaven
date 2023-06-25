package DAOTests;

import aplicacao.Plano;
import model.PlanoDAO;
import conexao.ConexaoBancoDeDados;

import java.util.List;
import java.util.ArrayList;
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
}
