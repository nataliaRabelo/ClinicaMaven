package conexao;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


/**
A classe ConexaoBancoDeDados representa uma conexão com o banco de dados.
É responsável por estabelecer a conexão com o banco de dados MySQL.
*/

@WebServlet(name = "Conexao", urlPatterns = {"/Conexao"})
public class ConexaoBancoDeDados extends HttpServlet {
    
    private static Connection conexao = null;

    /**
    * Cria e retorna uma conexão com o banco de dados.
    * @return A conexão com o banco de dados.
    * @throws SQLException exceção lançada ao estabelecer a conexão.
    */
    public static Connection newConnection() throws SQLException {
        if (conexao == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection("jdbc:mysql://localhost:3307/clinica", "root", "");
            } catch(ClassNotFoundException e) {
                Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, "Nao encontrado");
            }
        }
        return conexao;
    }
}
