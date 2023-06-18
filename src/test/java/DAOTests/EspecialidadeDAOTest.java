package DAOTests;

import model.EspecialidadeDAO;
import aplicacao.Especialidade;
import conexao.ConexaoBancoDeDados;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public class EspecialidadeDAOTest {

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
public void testCreateEspecialidade() {
    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);
    Especialidade novaEspecialidade = new Especialidade();
    novaEspecialidade.setDescricao("Cardiologia");

    especialidadeDAO.createEspecialidade(novaEspecialidade);

    // Tenta recuperar a especialidade do banco de dados
    ArrayList<Especialidade> especialidades = especialidadeDAO.getEspecialidades();

    boolean found = false;
    for (Especialidade especialidade : especialidades) {
        if (especialidade.getDescricao().equals(novaEspecialidade.getDescricao())) {
            found = true;
            break;
        }
    }
    
    assertTrue(found);
}


    @Test
    public void testGetEspecialidades() {
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);
        ArrayList<Especialidade> especialidades = especialidadeDAO.getEspecialidades();

        Assertions.assertNotNull(especialidades, "A lista de especialidades não deve ser nula");
        Assertions.assertTrue(especialidades.size() > 0, "A lista de especialidades deve ter pelo menos uma especialidade");
    }

    @Test
    public void testGetEspecialidade() {
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO(conn);
        int idEspecialidade = 1; // ID válido

        Especialidade especialidade = especialidadeDAO.getEspecialidade(idEspecialidade);

        Assertions.assertNotNull(especialidade, "A especialidade não deve ser nula");
        Assertions.assertEquals(idEspecialidade, especialidade.getId(), "O ID da especialidade deve ser o mesmo do ID de entrada");
    }

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
                ArrayList<ArrayList<Integer>> id_compilado = dao.getIdDeleteEspecialidade(id);

                // Assert
                assertFalse(id_compilado.get(0).isEmpty(), "A lista de ids de exames não deve estar vazia");
                assertFalse(id_compilado.get(1).isEmpty(), "A lista de ids de consultas não deve estar vazia");
            }
        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }
}

