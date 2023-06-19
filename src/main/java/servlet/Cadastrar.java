package servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;
import java.util.logging.Level;
import conexao.ConexaoBancoDeDados;
import java.util.ArrayList;
import java.util.List;
import aplicacao.Cliente;
import aplicacao.Plano;
import model.ClienteDAO;
import model.PlanoDAO;

@WebServlet(name = "Cadastrar", urlPatterns = {"/Cadastrar"})
public class Cadastrar extends HttpServlet {
    
    ClienteDAO novocltDAO = new ClienteDAO();
    PlanoDAO planoDAO = new PlanoDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameter("arg").equals("Cadastrar")){
            
            List<Plano> planos;
            
            planos = planoDAO.getPlanos();
            
            if(planos.size() > 0){
                HttpSession session = request.getSession();
                session.setAttribute("planos", planos);
                RequestDispatcher cad = request.getRequestDispatcher("/view/CadastroPublico.jsp");
                cad.forward(request, response);
            }else{
                RequestDispatcher cad = request.getRequestDispatcher("/view/Confirmacao.jsp?type=SemPlano");
                cad.forward(request, response);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
            
            Cliente novoPaciente = new Cliente();
            
            novoPaciente.setNome(request.getParameter("nome"));
            novoPaciente.setCpf(request.getParameter("CPF"));
            novoPaciente.setSenha(request.getParameter("senha"));
            novoPaciente.setIdtipoplano(Integer.parseInt(request.getParameter("plano")));
            novoPaciente.setAutorizado('S');
            
            if(novocltDAO.jaCadastrado(novoPaciente.getCpf())){
                RequestDispatcher clt = request.getRequestDispatcher("./view/Confirmacao.jsp?type=JaCadastrado");
                clt.forward(request, response);
            }else{ 
                
                novocltDAO.createPaciente(novoPaciente);
                
                RequestDispatcher clt = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Cadastrado");
                clt.forward(request, response);
            }
        } catch(IOException | NumberFormatException | ServletException e) {
            Logger logger = Logger.getLogger(ConexaoBancoDeDados.class.getName());
            logger.log(Level.INFO, "Error: " + e.getMessage());
        }   
    }

    public void setPlanoDAO(PlanoDAO planoDAO) {
        this.planoDAO = planoDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.novocltDAO = clienteDAO;
    }
}
