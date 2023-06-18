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
                
                ArrayList<Consulta> listaConsultas = new ArrayList<>();                
                listaConsultas = medicoDAO.getConsultas(medico.getId());
                
                if(listaConsultas.size() > 0){
                    
                    ArrayList<ArrayList<String>>listaExamesCompilado = new ArrayList<>();
                    
                    for(int i=0;i<listaConsultas.size();i++){
                        
                        ArrayList<String>listaExames = new ArrayList<>();
                        listaExames = exameDAO.getExamesDaConsulta(listaConsultas.get(i).getId(),listaExames);
                        
                        if(listaExames.isEmpty()){
                            listaExamesCompilado.add(null);
                        }
                        else{
                            listaExamesCompilado.add(listaExames);
                        }
                    }
                    
                    ArrayList<String> nomePacientes = new ArrayList<>();                    
                    ClienteDAO clienteDAO = new ClienteDAO();
                    
                    for(int i=0;i<listaConsultas.size();i++){
                        String nomePaciente = clienteDAO.getNomePaciente(listaConsultas.get(i).getIdpaciente());
                        nomePacientes.add(nomePaciente);
                    }
                    
                    session.setAttribute("lista_nomes", nomePacientes);
                    session.setAttribute("lista_exames", listaExamesCompilado);
                    session.setAttribute("lista_consultas", listaConsultas);
                    RequestDispatcher visualiz = request.getRequestDispatcher("./view/VisualizarConsultaMedico.jsp");
                    visualiz.forward(request, response);
                }
                else{
                    RequestDispatcher visualiz = request.getRequestDispatcher("./view/Confirmacao.jsp?type=SemConsulta&role=Med");
                    visualiz.forward(request, response);
                }
                
            break;

            
            case "SolicitarExame":
                
                String idConsultaEx = request.getParameter("id");
                ArrayList<Object> lista = new ArrayList<>();
                
                lista = medicoDAO.getExames();
                
                session.setAttribute("lista_exames", lista);
                session.setAttribute("id_consulta", idConsultaEx);
                RequestDispatcher medex = request.getRequestDispatcher("./view/MarcarExame.jsp");
                medex.forward(request, response);                
            
            break;

            
            case "ConcluirConsulta":
                
                String idConsulta = request.getParameter("id");
                session.setAttribute("id_consulta", idConsulta);
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
        
        if(request.getParameter("acao").equals("Enviar")){
                Consulta consulta = new Consulta();
                ConsultaDAO consultaDAO = new ConsultaDAO();
                
                consulta = consultaDAO.getConsulta(Integer.parseInt(request.getParameter("id_consulta")));
          
                consulta.setDescricao(request.getParameter("descricao"));
                consulta.setRealizada(request.getParameter("realizada").charAt(0));
                
                consultaDAO.updateConsulta(consulta.getId(), consulta);
                
                RequestDispatcher med = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Concluido");
                med.forward(request, response);
        }
        else if(request.getParameter("acao").equals("Marcar Exame")){   
                MedicoDAO medicoDAO = new MedicoDAO();
                
                medicoDAO.createExame(Integer.parseInt(request.getParameter("id_exame")),Integer.parseInt(request.getParameter("id_consulta")));
                
                RequestDispatcher marcar = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Exame");
                marcar.forward(request, response);
        }
    }
}
