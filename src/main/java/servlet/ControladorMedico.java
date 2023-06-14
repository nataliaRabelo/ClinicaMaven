package servlet;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ClienteDAO;
import model.ConsultaDAO;
import model.MedicoDAO;
import model.ExameDAO;
import aplicacao.Medico;
import aplicacao.Consulta;

@WebServlet(name = "ControladorMedico", urlPatterns = {"/ControladorMedico"})
public class ControladorMedico extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Medico medico = (Medico)session.getAttribute("medico");
        
        MedicoDAO medicoDAO = new MedicoDAO();
        
        switch(request.getParameter("arg")){
        
            case "Visualizar":
                
                ExameDAO exameDAO = new ExameDAO();
                
                ArrayList<Consulta> lista_consultas = new ArrayList<Consulta>();                
                lista_consultas = medicoDAO.get_consultas(medico.getId());
                
                if(lista_consultas.size() > 0){
                    
                    ArrayList<ArrayList<String>>lista_exames_compilado = new ArrayList<ArrayList<String>>();
                    
                    for(int i=0;i<lista_consultas.size();i++){
                        
                        ArrayList<String>lista_exames = new ArrayList<String>();
                        lista_exames = exameDAO.get_examesDaConsulta(lista_consultas.get(i).getId(),lista_exames);
                        
                        if(lista_exames.isEmpty()){
                            lista_exames_compilado.add(null);
                        }
                        else{
                            lista_exames_compilado.add(lista_exames);
                        }
                    }
                    
                    ArrayList<String> nome_pacientes = new ArrayList<String>();                    
                    ClienteDAO clienteDAO = new ClienteDAO();
                    
                    for(int i=0;i<lista_consultas.size();i++){
                        String nome_paciente = clienteDAO.get_nomePaciente(lista_consultas.get(i).getIdpaciente());
                        nome_pacientes.add(nome_paciente);
                    }
                    
                    session.setAttribute("lista_nomes", nome_pacientes);
                    session.setAttribute("lista_exames", lista_exames_compilado);
                    session.setAttribute("lista_consultas", lista_consultas);
                    RequestDispatcher visualiz = request.getRequestDispatcher("./view/VisualizarConsultaMedico.jsp");
                    visualiz.forward(request, response);
                }
                else{
                    RequestDispatcher visualiz = request.getRequestDispatcher("./view/Confirmacao.jsp?type=SemConsulta&role=Med");
                    visualiz.forward(request, response);
                }
                
            break;
            
            case "SolicitarExame":
                
                String id_consulta_ex = request.getParameter("id");
                ArrayList<Object> lista = new ArrayList<Object>();
                
                lista = medicoDAO.get_exames(Integer.parseInt(id_consulta_ex),medico.getId());
                
                session.setAttribute("lista_exames", lista);
                session.setAttribute("id_consulta", id_consulta_ex);
                RequestDispatcher medex = request.getRequestDispatcher("./view/MarcarExame.jsp");
                medex.forward(request, response);                
            
            break;
            
            case "ConcluirConsulta":
                
                String id_consulta = request.getParameter("id");
                session.setAttribute("id_consulta", id_consulta);
                RequestDispatcher med = request.getRequestDispatcher("./view/EditarConsultaMedico.jsp");
                med.forward(request, response);
                
            break;
        
            case "Dashboard":
                
                session.setAttribute("medico", medico);
                RequestDispatcher meddsh = request.getRequestDispatcher("./view/AreaMedico.jsp");
                meddsh.forward(request, response);
                
            break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        switch(request.getParameter("acao")){
            
            case "Enviar":
                
                Consulta consulta = new Consulta();
                ConsultaDAO consultaDAO = new ConsultaDAO();
                
                consulta = consultaDAO.get_consulta(Integer.parseInt(request.getParameter("id_consulta")));
          
                consulta.setDescricao(request.getParameter("descricao"));
                consulta.setRealizada(request.getParameter("realizada").charAt(0));
                
                consultaDAO.update_consulta(consulta.getId(), consulta);
                
                RequestDispatcher med = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Concluido");
                med.forward(request, response);
                
            break;
            
            case "Marcar Exame":
                
                MedicoDAO medicoDAO = new MedicoDAO();
                
                medicoDAO.create_exame(Integer.parseInt(request.getParameter("id_exame")),Integer.parseInt(request.getParameter("id_consulta")));
                
                RequestDispatcher marcar = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Exame");
                marcar.forward(request, response);
                
            break;
        }
    }
}
