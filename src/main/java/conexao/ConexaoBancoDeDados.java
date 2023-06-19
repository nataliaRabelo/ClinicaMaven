package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(name = "Conexao", urlPatterns = {"/Conexao"})
public class ConexaoBancoDeDados extends HttpServlet {
    
    private static Connection conexao = null;
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
