package servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import aplicacao.Cliente;
import aplicacao.Medico;
import aplicacao.Administrador;
import model.ClienteDAO;
import model.MedicoDAO;
import model.AdministradorDAO;

@WebServlet(name= "Autenticar", urlPatterns = {"/Autenticar"})
public class Autenticar extends HttpServlet {
    
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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{    
            
            String tipo = request.getParameter("papel");
            String cpf = request.getParameter("CPF");
            String senha = request.getParameter("senha");

            if(tipo.isEmpty() || cpf.isEmpty() || senha.isEmpty()){
                RequestDispatcher rd = request.getRequestDispatcher("./view/Oops.jsp?type=Login");
                rd.forward(request, response);            
            }
            else{
                
                switch(tipo){
                    case "Cliente":

                        ClienteDAO cltDAO = new ClienteDAO();
                        Cliente cliente = cltDAO.login(cpf,senha);
                        
                        if(cliente.getId() != null){
                            
                            if(cliente.getAutorizado() == 'S'){
                                
                                HttpSession session = request.getSession();
                                session.setAttribute("cliente", cliente);
                                RequestDispatcher clt = request.getRequestDispatcher("./view/AreaCliente.jsp");
                                clt.forward(request, response); 
                            }
                            else{
                                
                                RequestDispatcher clt = request.getRequestDispatcher("./view/Oops.jsp?type=Autorizacao");
                                clt.forward(request, response);
                            }
                        }    
                        
                        break;
                        
                    case "Medico":
                        
                        MedicoDAO medDAO = new MedicoDAO();
                        Medico medico = medDAO.login(cpf,senha);
                        
                        if(medico.getId() != null){
                            
                            if(medico.getAutorizado() == 'S'){
                            
                                HttpSession session = request.getSession();
                                session.setAttribute("medico", medico);
                                RequestDispatcher med = request.getRequestDispatcher("./view/AreaMedico.jsp");
                                med.forward(request, response);    
                            }
                            else{
                                
                                RequestDispatcher clt = request.getRequestDispatcher("./view/Oops.jsp?type=Autorizacao");
                                clt.forward(request, response);
                            }
                        }
                        break;
                            
                    case "Administrador":
                        
                        AdministradorDAO admDAO = new AdministradorDAO();
                        Administrador adm = admDAO.login(cpf,senha);
                        
                        if(adm.getId() != null){
                            
                            HttpSession session = request.getSession();
                            session.setAttribute("adm", adm);
                            RequestDispatcher admin = request.getRequestDispatcher("./view/AreaAdministrador.jsp");
                            admin.forward(request, response);                      
                        }
                        break;
                        
                    default:
                }
                
                RequestDispatcher erro = request.getRequestDispatcher("./view/Oops.jsp?type=Login");
                erro.forward(request, response);
            }
            
        } catch(IOException | NumberFormatException | ServletException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
