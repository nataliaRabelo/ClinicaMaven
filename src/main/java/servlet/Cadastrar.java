package servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
            
            ArrayList<Plano> planos = new ArrayList<>();
            
            planos = planoDAO.get_planos();
            
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
            
            Cliente novo_paciente = new Cliente();
            
            novo_paciente.setNome(request.getParameter("nome"));
            novo_paciente.setCpf(request.getParameter("CPF"));
            novo_paciente.setSenha(request.getParameter("senha"));
            novo_paciente.setIdtipoplano(Integer.parseInt(request.getParameter("plano")));
            novo_paciente.setAutorizado('S');
            
            if(novocltDAO.jaCadastrado(novo_paciente.getCpf())){
                RequestDispatcher clt = request.getRequestDispatcher("./view/Confirmacao.jsp?type=JaCadastrado");
                clt.forward(request, response);
            }else{ 
                
                novocltDAO.create_paciente(novo_paciente);
                
                RequestDispatcher clt = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Cadastrado");
                clt.forward(request, response);
            }
        } catch(IOException | NumberFormatException | ServletException e) {
            System.out.println("Error: " + e.getMessage());
        }   
    }

    public void setPlanoDAO(PlanoDAO planoDAO) {
        this.planoDAO = planoDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.novocltDAO = clienteDAO;
    }
}
