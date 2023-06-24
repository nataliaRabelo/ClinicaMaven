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
    public void testGet_consultas() {
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
    public void testGet_consulta() {
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
    public void testGet_medicoEspecialidade() {
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
    public void testGet_procedimentosDisponiveis() {
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

}

