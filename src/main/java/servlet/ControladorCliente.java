package servlet;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import aplicacao.Cliente;
import aplicacao.Consulta;
import model.ExameDAO;
import model.ConsultaDAO;
import model.MedicoDAO;
import java.util.List;


@WebServlet(name = "ControladorCliente", urlPatterns = {"/ControladorCliente"})
public class ControladorCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Cliente cliente = (Cliente)session.getAttribute("cliente");
        
        ConsultaDAO consultaDAO = new ConsultaDAO();
        
        switch(request.getParameter("arg")){
        
            case "Dashboard":
                
                session.setAttribute("cliente", cliente);
                RequestDispatcher dashboard = request.getRequestDispatcher("./view/AreaCliente.jsp");
                dashboard.forward(request, response); 
            
            break;
            
            case "Editar":
               
                Consulta consultaOriginal = new Consulta();
                
                consultaOriginal = consultaDAO.getConsulta(Integer.parseInt(request.getParameter("id")));
               
                List<Object>listaMedicoEspec = new ArrayList<>();
                List<Object>listaDisponiveis = new ArrayList<>();
                
                consultaDAO.getMedicoEspecialidade(consultaOriginal.getId(),listaMedicoEspec);
                    
                listaDisponiveis = consultaDAO.getProcedimentosDisponiveis();
   
                session.setAttribute("lista_disponiveis", listaDisponiveis);
                session.setAttribute("consulta_edit", consultaOriginal);
                session.setAttribute("consulta_edit_infos", listaMedicoEspec);
                RequestDispatcher editar = request.getRequestDispatcher("./view/EditarConsulta.jsp");
                editar.forward(request, response);
                
            break;


            
            case "Visualizar":
                
                List<Consulta> listaConsultas = new ArrayList<>();
                ExameDAO exameDAO = new ExameDAO();
                
                listaConsultas = consultaDAO.getConsultas(cliente.getId());
                
                if(listaConsultas.size() > 0){
                    
                    ArrayList<Object>listaMedEsp = new ArrayList<>();
                    List<List<String>>listaExamesCompilado = new ArrayList<>();
                    
                    for(int i=0;i<listaConsultas.size();i++){
                        consultaDAO.getMedicoEspecialidade(listaConsultas.get(i).getId(),listaMedEsp);
                        
                        List<String>listaExames = new ArrayList<>();
                        listaExames = exameDAO.getExamesDaConsulta(listaConsultas.get(i).getId(),listaExames);
                        
                        if(listaExames.isEmpty()){
                            listaExamesCompilado.add(null);
                        }
                        else{
                            listaExamesCompilado.add(listaExames);
                        }
                    }
                    
                    session.setAttribute("lista_exames", listaExamesCompilado);
                    session.setAttribute("lista_consultas", listaConsultas);
                    session.setAttribute("lista_med_esp", listaMedEsp);
                    RequestDispatcher clt = request.getRequestDispatcher("./view/VisualizarConsulta.jsp");
                    clt.forward(request, response);
                }
                else{
                    RequestDispatcher clt = request.getRequestDispatcher("./view/Confirmacao.jsp?type=SemConsulta&role=Pac");
                    clt.forward(request, response);
                }
            break;

        
            case "Marcar":

                List<Object> listaProcedimentos = new ArrayList<>();
                
                listaProcedimentos = consultaDAO.getProcedimentosDisponiveis();
   
                if(!listaProcedimentos.isEmpty()){
                    session.setAttribute("lista", listaProcedimentos);
                    RequestDispatcher marcar = request.getRequestDispatcher("./view/MarcarConsulta.jsp");
                    marcar.forward(request, response);
                }else{
                    RequestDispatcher marcar = request.getRequestDispatcher("./view/Confirmacao.jsp?type=SemEspMed");
                    marcar.forward(request, response);
                }
            break;

            
            case "Excluir":
                
                consultaDAO.deleteConsulta(Integer.parseInt(request.getParameter("id")));
                RequestDispatcher excluir = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Excluido");
                excluir.forward(request, response);
            
            break;    
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Cliente cliente = (Cliente)session.getAttribute("cliente");
        
        MedicoDAO medicoDAO = new MedicoDAO();
        Consulta consulta = new Consulta();
        ConsultaDAO consultaDAO = new ConsultaDAO();
        
        consulta.setData(request.getParameter("data") + " " + request.getParameter("hora"));
        consulta.setDescricao("Sem descricao");
        consulta.setRealizada('N');
        consulta.setIdmedico(Integer.valueOf(request.getParameter("id_med")));
        consulta.setIdpaciente(cliente.getId());
        
        String[] datahora = consulta.getData().split(" ");
        
        List<Integer> colisoes = new ArrayList<>();
        colisoes = medicoDAO.medicoAvailable(consulta.getIdmedico(),datahora[0]);
        
        if(request.getParameter("acao").equals("Enviar")){

                if(colisoes.size() < 2){
                    consultaDAO.createConsulta(consulta);
                    RequestDispatcher voltar = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Marcado");
                    voltar.forward(request, response);
                }else{
                    RequestDispatcher lotado = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Lotado");
                    lotado.forward(request, response);
                }
        }
        else if(request.getParameter("acao").equals("Remarcar Consulta")){   
                int id = Integer.parseInt(request.getParameter("id_consulta"));
                
                if(colisoes.size() < 2 || colisoes.contains(id)){
                    consulta.setId(id);
                    consultaDAO.updateConsulta(consulta.getId(),consulta);
                    RequestDispatcher voltar = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Editado");
                    voltar.forward(request, response);
                }else{
                    RequestDispatcher lotado = request.getRequestDispatcher("./view/Confirmacao.jsp?type=Lotado");
                    lotado.forward(request, response);
                }
        }
    }
}
