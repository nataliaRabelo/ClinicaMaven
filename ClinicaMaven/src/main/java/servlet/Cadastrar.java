package servlet;

import aplicacao.Cliente;
import aplicacao.Plano;
import conexao.ConexaoBancoDeDados;
import model.ClienteDAO;
import model.PlanoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet responsável pelo cadastro de novos clientes.
 */
@WebServlet(name = "Cadastrar", urlPatterns = {"/Cadastrar"})
public class Cadastrar extends HttpServlet {
    
    ClienteDAO novocltDAO;
    
    PlanoDAO planoDAO;
    

     /**
     * Processa as requisições GET para o servlet.
     * Verifica se o parâmetro "arg" é igual a "Cadastrar" e encaminha para a página de cadastro,
     * preenchendo os planos disponíveis.
     * 
     * @param request O objeto HttpServletRequest que contém a requisição do cliente.
     * @param response O objeto HttpServletResponse que será enviado como resposta.
     * @throws ServletException exceção lançada ao ocorrer um erro durante o processamento da servlet.
     * @throws IOException exceção lançada ao ocorrer um erro de I/O durante o processamento da servlet.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        planoDAO.createConnection();
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

     /**
     * Processa as requisições POST para o servlet.
     * Realiza o cadastro de um novo cliente com base nos dados fornecidos.
     * 
     * @param request O objeto HttpServletRequest que contém a requisição do cliente.
     * @param response O objeto HttpServletResponse que será enviado como resposta.
     * @throws ServletException exceção lançada ao ocorrer um erro durante o processamento da servlet.
     * @throws IOException exceção lançada ao ocorrer um erro de I/O durante o processamento da servlet.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        novocltDAO.createConnection();
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
}
