package servlet;

import aplicacao.Cliente;
import aplicacao.Medico;
import aplicacao.Administrador;
import model.ClienteDAO;
import model.MedicoDAO;
import model.AdministradorDAO;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet responsável pela autenticação de usuários.
 */
@WebServlet(name= "Autenticar", urlPatterns = {"/Autenticar"})
public class Autenticar extends HttpServlet {
    
     /**
     * Processa as requisições GET para o servlet.
     * Verifica se o parâmetro "arg" é igual a "Logout" e realiza o logout do usuário.
     * 
     * @param request O objeto HttpServletRequest que contém a requisição do cliente.
     * @param response O objeto HttpServletResponse que será enviado como resposta.
     * @throws ServletException Se ocorrer um erro durante o processamento da servlet.
     * @throws IOException Se ocorrer um erro de I/O durante o processamento da servlet.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        HttpSession session = request.getSession();
        
        if(request.getParameter("arg").equals("Logout")){
        
            session.invalidate();
            RequestDispatcher logout = request.getRequestDispatcher("./index.jsp");
            logout.forward(request, response);
        }
    }
    
     /**
     * Processa as requisições POST para o servlet.
     * Realiza a autenticação do usuário com base no tipo (cliente, médico ou administrador),
     * CPF e senha fornecidos.
     * 
     * @param request O objeto HttpServletRequest que contém a requisição do cliente.
     * @param response O objeto HttpServletResponse que será enviado como resposta.
     * @throws ServletException Exxceção lançada por um erro durante o processamento da servlet.
     * @throws IOException Exceção lançada por um erro de I/O durante o processamento da servlet.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("papel");
        String cpf = request.getParameter("CPF");
        String senha = request.getParameter("senha");

        if (tipo.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("./view/Oops.jsp?type=Login");
            rd.forward(request, response);
            return;
        }

        HttpSession session = request.getSession();

        switch (tipo) {
            case "Cliente":
                ClienteDAO cltDAO = new ClienteDAO();
                cltDAO.createConnection();
                Cliente cliente = cltDAO.login(cpf, senha);

                if (cliente.getId() != null && cliente.getAutorizado() == 'S') {
                    session.setAttribute("cliente", cliente);
                    RequestDispatcher clt = request.getRequestDispatcher("./view/AreaCliente.jsp");
                    clt.forward(request, response);
                    return;
                }
                break;

            case "Medico":
                MedicoDAO medDAO = new MedicoDAO();
                Medico medico = medDAO.login(cpf, senha);

                if (medico.getId() != null && medico.getAutorizado() == 'S') {
                    session.setAttribute("medico", medico);
                    RequestDispatcher med = request.getRequestDispatcher("./view/AreaMedico.jsp");
                    med.forward(request, response);
                    return;
                }
                break;

            case "Administrador":
                AdministradorDAO admDAO = new AdministradorDAO();
                Administrador adm = admDAO.login(cpf, senha);

                if (adm.getId() != null) {
                    session.setAttribute("adm", adm);
                    RequestDispatcher admin = request.getRequestDispatcher("./view/AreaAdministrador.jsp");
                    admin.forward(request, response);
                    return;
                }
                break;
                
            default:
                break;
        }

        RequestDispatcher erro = request.getRequestDispatcher("./view/Oops.jsp?type=Login");
        erro.forward(request, response);
    }
}
