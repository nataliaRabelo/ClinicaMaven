package DAOTests;

import static org.junit.jupiter.api.Assertions.*;
import model.ExameDAO;
import aplicacao.Exame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.sql.Connection;

import conexao.conexao_bancodedados;

import java.util.ArrayList;

public class ExameDAOTest {

    private static Connection conn;

    @BeforeAll
    public static void setUp() {
        try {
            conn = conexao_bancodedados.newConnection();
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
        
        total_exames = instance.get_exames();
        int qtd = total_exames.size();
        
        instance.create_exame(novo_exame);
        
        total_exames = instance.get_exames();
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
        expResult = instance.get_exames();
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
            Exame result = instance.get_exame(id_exame);
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
            lista_exames = instance.get_examesDaConsulta(id_consulta, lista_exames);
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
            exp_exame = instance.get_exame(id_exame);
            instance.update_exame(id_exame, novo_exame);
            
            novo_exame = instance.get_exame(id_exame);
            
            assertNotEquals(exp_exame.getDescricao(),novo_exame.getDescricao());
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
        int id_exame = 1;
        ExameDAO instance = new ExameDAO(conn);
        try{
            instance.delete_exame(id_exame);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    /**
     * Test of delete_tipoExame method, of class ExameDAO.
     */
    @Test
    public void testDelete_tipoExame() {
        System.out.println("delete_tipoExame");
        int id_exame = 1;
        ExameDAO instance = new ExameDAO(conn);
        try{
            instance.delete_tipoExame(id_exame);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }

    /**
     * Test of get_idDeleteExame method, of class ExameDAO.
     */
    @Test
    public void testGet_idDeleteExame() {
        System.out.println("get_idDeleteExame");
        int id_exame = 1;
        ExameDAO instance = new ExameDAO(conn);
        try{
            ArrayList<Integer> result = instance.get_idDeleteExame(id_exame);
            assertNotNull(result);
        } catch (NullPointerException e) {
            fail("Não há objeto com a id no banco de dados");
        }
    }
}

