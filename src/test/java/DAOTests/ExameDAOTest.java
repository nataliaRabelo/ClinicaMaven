package DAOTests;

import static org.junit.jupiter.api.Assertions.*;
import model.ExameDAO;
import aplicacao.Exame;
import aplicacao.Consulta;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.sql.Connection;

import conexao.ConexaoBancoDeDados;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

public class ExameDAOTest {

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
     * Test of create_exame method, of class ExameDAO.
     */
    @Test
    public void testCreate_exame() {
        System.out.println("create_exame");
        
        Exame novo_exame = new Exame();
        novo_exame.setId(100);
        novo_exame.setDescricao("Exame de sangue");
        
        ArrayList<Exame> total_exames = new ArrayList<Exame>();
        ExameDAO instance = new ExameDAO(conn);
        
        total_exames = instance.getExames();
        int qtd = total_exames.size();
        
        instance.createExame(novo_exame);
        
        total_exames = instance.getExames();
        assertNotEquals(total_exames.size(),qtd);
    }

    /**
     * Test of get_exames method, of class ExameDAO.
     */
    @Test
    public void testGet_exames() {
        System.out.println("get_exames");
        ExameDAO instance = new ExameDAO(conn);
        ArrayList<Exame> expResult = new ArrayList<Exame>();
        expResult = instance.getExames();
        assertNotNull(expResult);
        
    }

    /**
     * Test of get_exame method, of class ExameDAO.
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
     * Test of get_examesDaConsulta method, of class ExameDAO.
     */
    @Test
    public void testGet_examesDaConsulta() {
        System.out.println("get_examesDaConsulta");
        int id_consulta = 1;
        ArrayList<String> lista_exames = new ArrayList<String>();
        ExameDAO instance = new ExameDAO(conn);
        try{
            lista_exames = instance.getExamesDaConsulta(id_consulta, lista_exames);
            assertNotNull(lista_exames);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    /**
     * Test of update_exame method, of class ExameDAO.
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
     * Test of delete_exame method, of class ExameDAO.
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
     * Test of delete_tipoExame method, of class ExameDAO.
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
     * Test of get_idDeleteExame method, of class ExameDAO.
     */
    @Test
    public void testGet_idDeleteExame() {
        System.out.println("get_idDeleteExame");
        int id_exame = 1; //considerando que se deseja buscar o id do exame 1
        ExameDAO instance = new ExameDAO(conn);
        try{
            ArrayList id_delete = instance.getIdDeleteExame(id_exame);
            assertNotNull(id_delete);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }
}

