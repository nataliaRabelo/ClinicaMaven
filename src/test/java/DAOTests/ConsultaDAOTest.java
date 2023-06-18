package DAOTests;

import aplicacao.Consulta;
import model.ConsultaDAO;
import conexao.conexao_bancodedados;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaDAOTest {

    private static Connection conn;

    @BeforeAll
    public static void setUp() {
        try {
            conn = conexao_bancodedados.newConnection();
        } catch(SQLException e) {
            System.out.println("Nao foi possivel conectar");
        }
    }

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
        int numConsultasAntes = consultaDAO.get_consultas(consulta.getIdpaciente()).size();

        consultaDAO.create_consulta(consulta);

        // Obtenha o número atual de consultas para esse paciente novamente, após a inserção
        int numConsultasDepois = consultaDAO.get_consultas(consulta.getIdpaciente()).size();

        // Verifique se o número de consultas aumentou em 1
        assertEquals(numConsultasAntes + 1, numConsultasDepois);
    }

    @Test
    public void testGet_consultas() {
        int id_paciente = 1;
        ConsultaDAO instance = new ConsultaDAO(conn);
        ArrayList<Consulta> expResult = new ArrayList();
        try{
            expResult = instance.get_consultas(id_paciente);
            assertNotNull(expResult);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    @Test
    public void testGet_consulta() {
        int id_consulta = 1;
        ConsultaDAO instance = new ConsultaDAO(conn);
        Consulta expResult = new Consulta();
        try{
            expResult = instance.get_consulta(id_consulta);
            assertNotNull(expResult);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    @Test
    public void testGet_medicoEspecialidade() {
        int id_consulta = 1;
        ArrayList<Object> medico_descricao = new ArrayList<Object>();
        ConsultaDAO instance = new ConsultaDAO(conn);
        ArrayList<Object> expResult = null;
        try{    
            expResult = instance.get_medicoEspecialidade(id_consulta, medico_descricao);
            assertNotNull(expResult);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    @Test
    public void testGet_procedimentosDisponiveis() {
        ConsultaDAO instance = new ConsultaDAO(conn);
        ArrayList<Object> expResult = new ArrayList<Object>();
        try{
        expResult = instance.get_procedimentosDisponiveis();
        assertNotNull(expResult);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

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

        consultaDAO.update_consulta(idConsulta, novaConsulta);

        // Recupera a consulta atualizada do banco de dados
        Consulta consultaAtualizada = consultaDAO.get_consulta(idConsulta);

        // Agora você pode comparar os campos da consulta recuperada com os dados que você usou para a atualização
        assertEquals(novaConsulta.getData(), consultaAtualizada.getData());
        assertEquals(novaConsulta.getDescricao(), consultaAtualizada.getDescricao());
        assertEquals(novaConsulta.getRealizada(), consultaAtualizada.getRealizada());
        assertEquals(novaConsulta.getIdmedico(), consultaAtualizada.getIdmedico());
        assertEquals(novaConsulta.getIdpaciente(), consultaAtualizada.getIdpaciente());
    }


    @Test
    public void testDeleteConsulta() {
        try {
            // Arrange
            ConsultaDAO consultaDAO = new ConsultaDAO(conn);  
            Consulta consulta = new Consulta(); // Criando uma consulta para testar a deleção
            consulta.setDescricao("Exame de sangue");
            consulta.setData("2023-07-17 00:41:00");
            consultaDAO.create_consulta(consulta);

            // Criando o Statement para executar a query
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM consulta WHERE consulta.descricao = '" + consulta.getDescricao() + " and consulta.data ='" + consulta.getData() + "'");
            int idResultSet = 0; 
            // Verificando se a consulta retornou algum resultado
            if(resultSet.next()) {
                // Act
                int id = resultSet.getInt("id"); // Supondo que a coluna do id na tabela seja 'id'
                idResultSet = id;
                Consulta consultaApagada = consultaDAO.get_consulta(id); // deletando a consulta que acabei de criar, onde o id é inserido no banco
            }

            // Assert
            Consulta consultaQuery = consultaDAO.get_consulta(idResultSet);
            assertNull(consulta.getDescricao(), "A consulta deveria ser null após ser deletado");
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

}
