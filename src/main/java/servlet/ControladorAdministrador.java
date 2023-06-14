package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import aplicacao.Cliente;
import aplicacao.Medico;
import aplicacao.Administrador;
import aplicacao.Consulta;
import aplicacao.Plano;
import aplicacao.Especialidade;
import aplicacao.Exame;
import model.ClienteDAO;
import model.MedicoDAO;
import model.AdministradorDAO;
import model.PlanoDAO;
import model.EspecialidadeDAO;
import model.ConsultaDAO;
import model.ExameDAO;

@WebServlet(name = "ControladorAdministrador", urlPatterns = {"/ControladorAdministrador"})
public class ControladorAdministrador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Administrador administrador_sessao = (Administrador)session.getAttribute("adm");
        
        ClienteDAO clienteDAO = new ClienteDAO();
        MedicoDAO medicoDAO = new MedicoDAO();
        AdministradorDAO administradorDAO = new AdministradorDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();
        ExameDAO exameDAO = new ExameDAO();
        PlanoDAO planoDAO = new PlanoDAO();
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
        
        switch(request.getParameter("type")){
            case "Paciente": 
                switch(request.getParameter("arg")){
                    case "Visualizar":
                        
                        ArrayList<Cliente> pacientes = new ArrayList<Cliente>();
                        pacientes = clienteDAO.get_pacientes();
                        
                        if(pacientes.size() > 0){
                        
                            ArrayList<Plano> planos_pacientes = new ArrayList<Plano>();

                            for(int i=0;i<pacientes.size();i++){
                                planos_pacientes.add(planoDAO.get_plano(pacientes.get(i).getIdtipoplano()));
                            }

                            session.setAttribute("pacientes", pacientes);
                            session.setAttribute("planos_pacientes", planos_pacientes);
                            RequestDispatcher cadpac = request.getRequestDispatcher("./view/RelacaoPacientes.jsp");
                            cadpac.forward(request, response);
                        }else{
                            RequestDispatcher sempac = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Paciente");
                            sempac.forward(request, response);
                        }
                    break;
                    
                    case "Excluir":
                        ArrayList<ArrayList<Integer>> compilado_ids = new ArrayList<ArrayList<Integer>>();
                        
                        compilado_ids = clienteDAO.get_idDeletePaciente(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compilado_ids.get(0).size();i++){
                            exameDAO.delete_exame(compilado_ids.get(0).get(i));
                        }
                        for(int i=0;i<compilado_ids.get(1).size();i++){
                            consultaDAO.delete_consulta(compilado_ids.get(1).get(i));
                        }
                        
                        clienteDAO.delete_paciente(Integer.parseInt(request.getParameter("id")));
                        
                        RequestDispatcher del_adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Paciente");
                        del_adm.forward(request, response);
                    break;
                    
                    case "Editar":
                        Cliente paciente = new Cliente();
                        Plano plano = new Plano();
                        ArrayList<Plano> planos_disponiveis = new ArrayList<Plano>();
                        
                        paciente = clienteDAO.get_paciente(Integer.parseInt(request.getParameter("id")));
                        plano = planoDAO.get_plano(paciente.getIdtipoplano());
                        planos_disponiveis = planoDAO.get_planos();
                        
                        session.setAttribute("paciente",paciente);
                        session.setAttribute("plano",plano);
                        session.setAttribute("planos", planos_disponiveis);
                        
                        RequestDispatcher edt_pac = request.getRequestDispatcher("/view/EditarPacienteAdm.jsp");
                        edt_pac.forward(request, response);
                    break;
                    
                    case "Cadastrar":
                        ArrayList<Plano> planos = new ArrayList<Plano>();
                        planos = planoDAO.get_planos();
                        
                        session.setAttribute("paciente",null);
                        session.setAttribute("plano",null);
                        session.setAttribute("planos", planos);
                        
                        if(planos.size() > 0){
                            RequestDispatcher cad = request.getRequestDispatcher("/view/CadastrarPaciente.jsp");
                            cad.forward(request, response);
                        }else{
                            RequestDispatcher cad = request.getRequestDispatcher("/view/ConfirmacaoAdm.jsp?arg=SemRequisito&type=Paciente");
                            cad.forward(request, response);
                        }
                    break;
                }
            break;
                
            case "Medico":
                Medico medico = new Medico();
                switch(request.getParameter("arg")){
                    case "Visualizar":
                        ArrayList<Medico> medicos = new ArrayList<Medico>();

                        medicos = medicoDAO.get_medicos();
                        
                        if(medicos.size() > 0){
                        
                            ArrayList<Especialidade> especialidades_medicos = new ArrayList<Especialidade>();

                            for(int i=0;i<medicos.size();i++){
                                especialidades_medicos.add(especialidadeDAO.get_especialidade(medicos.get(i).getIdespecialidade()));
                            }

                            session.setAttribute("medicos",medicos);
                            session.setAttribute("especialidades_medicos",especialidades_medicos);
                            RequestDispatcher cadmed = request.getRequestDispatcher("./view/RelacaoMedicos.jsp");
                            cadmed.forward(request, response);
                        }else{
                            RequestDispatcher semmed = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Medico");
                            semmed.forward(request, response);
                        }
                    break;
                
                    case "Excluir":
                        ArrayList<ArrayList<Integer>> compilado_ids = new ArrayList<ArrayList<Integer>>();
                        
                        compilado_ids = medicoDAO.get_idDeleteMedico(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compilado_ids.get(0).size();i++){
                            exameDAO.delete_exame(compilado_ids.get(0).get(i));
                        }
                        for(int i=0;i<compilado_ids.get(1).size();i++){
                            consultaDAO.delete_consulta(compilado_ids.get(1).get(i));
                        }
                        
                        medicoDAO.delete_medico(Integer.parseInt(request.getParameter("id")));
                        
                        RequestDispatcher del_adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Medico");
                        del_adm.forward(request, response);
                    break;
                
                    case "Editar":
                        
                        Especialidade especialidade  = new Especialidade();
                        ArrayList<Especialidade> especs_disponiveis = new ArrayList<Especialidade>();
                        
                        medico = medicoDAO.get_medico(Integer.parseInt(request.getParameter("id")));
                        especialidade = especialidadeDAO.get_especialidade(medico.getIdespecialidade());
                        especs_disponiveis = especialidadeDAO.get_especialidades();
                        
                        session.setAttribute("medico",medico);
                        session.setAttribute("especialidade",especialidade);
                        session.setAttribute("especialidades", especs_disponiveis);
                        
                        RequestDispatcher edt_med = request.getRequestDispatcher("/view/EditarMedicoAdm.jsp");
                        edt_med.forward(request, response);
                    break;
                    
                    case "Cadastrar":
                        ArrayList<Especialidade> especs = new ArrayList<Especialidade>();

                        especs = especialidadeDAO.get_especialidades();
                        
                        if(especs.size() > 0){
                            
                            session.setAttribute("especialidades", especs);
                            RequestDispatcher cad_med = request.getRequestDispatcher("/view/CadastrarMedico.jsp");
                            cad_med.forward(request, response);
                        }else{
                            RequestDispatcher cad = request.getRequestDispatcher("/view/ConfirmacaoAdm.jsp?arg=SemRequisito&type=Medico");
                            cad.forward(request, response);
                        }
                    break;
                    
                    case "VerConsultas":
                        
                        ArrayList<Consulta> lista_consultas = new ArrayList<Consulta>();                
                        lista_consultas = medicoDAO.get_consultas(Integer.parseInt(request.getParameter("id")));
                    
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

                            for(int i=0;i<lista_consultas.size();i++){

                                String nome_paciente = clienteDAO.get_nomePaciente(lista_consultas.get(i).getIdpaciente());
                                nome_pacientes.add(nome_paciente);
                            }
                            
                            medico = medicoDAO.get_medico(Integer.parseInt(request.getParameter("id")));
                            String nome_medico = medico.getNome();
                            
                            session.setAttribute("lista_nomes", nome_pacientes);
                            session.setAttribute("lista_exames", lista_exames_compilado);
                            session.setAttribute("lista_consultas", lista_consultas);
                            session.setAttribute("nome_medico", nome_medico);
                            RequestDispatcher adm_cons = request.getRequestDispatcher("./view/RelacaoConsultas.jsp");
                            adm_cons.forward(request, response);
                        }
                        else{
                            RequestDispatcher clt = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=SemConsulta");
                            clt.forward(request, response);
                        }
                    break;
                }
            break;
                
            case "Administrador":
                switch(request.getParameter("arg")){
                    
                    case "Visualizar":     
                        ArrayList<Administrador> adms = new ArrayList<Administrador>();

                        adms = administradorDAO.get_administradores();
                        
                        session.setAttribute("adms",adms);
                        session.setAttribute("administrador_sessao",administrador_sessao);
                        RequestDispatcher vi_adm = request.getRequestDispatcher("./view/RelacaoAdministradores.jsp");
                        vi_adm.forward(request, response);
                    break;
                    
                    case "Excluir":
                        administradorDAO.delete_administrador(Integer.parseInt(request.getParameter("id")));
                        RequestDispatcher del_adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Administrador");
                        del_adm.forward(request, response);
                    break;
                
                    case "Editar": 
                        Administrador administrador = new Administrador();
                        
                        administrador = administradorDAO.get_administrador(Integer.parseInt(request.getParameter("id")));
                        
                        session.setAttribute("administrador",administrador);
                        RequestDispatcher edt_adm = request.getRequestDispatcher("/view/EditarAdministradorAdm.jsp");
                        edt_adm.forward(request, response);
                    break;
                    
                    case "Cadastrar":
                        session.setAttribute("administrador",null);
                        RequestDispatcher cr_adm = request.getRequestDispatcher("/view/CadastrarAdministrador.jsp");
                        cr_adm.forward(request, response);
                    break;
                }  
            break;
            
            case "Plano":
                switch(request.getParameter("arg")){
                    case "Visualizar":
                        
                        ArrayList<Plano> planos = new ArrayList<Plano>();

                        planos = planoDAO.get_planos();

                        if(planos.size() > 0){
                            session.setAttribute("planos",planos);
                            RequestDispatcher cadpla = request.getRequestDispatcher("./view/RelacaoPlanos.jsp");
                            cadpla.forward(request, response);
                        }else{
                            RequestDispatcher sempla = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Plano");
                            sempla.forward(request, response);
                        }
                    break;
                    
                    case "Excluir":
                        
                        ArrayList<ArrayList<Integer>> compilado_ids = new ArrayList<ArrayList<Integer>>();
                        
                        compilado_ids = planoDAO.get_idDeletePlano(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compilado_ids.get(0).size();i++){
                            exameDAO.delete_exame(compilado_ids.get(0).get(i));
                        }
                        for(int i=0;i<compilado_ids.get(1).size();i++){
                            consultaDAO.delete_consulta(compilado_ids.get(1).get(i));
                        }
                        for(int i=0;i<compilado_ids.get(2).size();i++){
                            clienteDAO.delete_paciente(compilado_ids.get(2).get(i));
                        }
                        
                        planoDAO.delete_plano(Integer.parseInt(request.getParameter("id")));
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher del_adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Plano");
                        del_adm.forward(request, response);
                    break;

                    case "Editar":
                        Plano plano = new Plano();
                        
                        plano = planoDAO.get_plano(Integer.parseInt(request.getParameter("id")));
                        session.setAttribute("plano",plano);
                        
                        RequestDispatcher edt_pln = request.getRequestDispatcher("/view/CadastrarPlano.jsp");
                        edt_pln.forward(request, response);
                    break;

                    case "Cadastrar":
                        session.setAttribute("plano",null);
                        
                        RequestDispatcher cad_plan = request.getRequestDispatcher("/view/CadastrarPlano.jsp");
                        cad_plan.forward(request, response);
                    break;
                }  
            break;
            
            case "Especialidade":
                switch(request.getParameter("arg")){
                    case "Visualizar":
                        
                        ArrayList<Especialidade> especs = new ArrayList<Especialidade>();

                        especs = especialidadeDAO.get_especialidades();

                        if(especs.size() > 0){
                            session.setAttribute("especs",especs);
                            RequestDispatcher cadesp = request.getRequestDispatcher("./view/RelacaoEspecialidades.jsp");
                            cadesp.forward(request, response);
                        }else{
                            RequestDispatcher semesp = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Especialidade");
                            semesp.forward(request, response);
                        }
                    break;
                    
                    case "Excluir":
                        ArrayList<ArrayList<Integer>> compilado_ids = new ArrayList<ArrayList<Integer>>();
                        
                        compilado_ids = especialidadeDAO.get_idDeleteEspecialidade(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compilado_ids.get(0).size();i++){
                            exameDAO.delete_exame(compilado_ids.get(0).get(i));
                        }
                        for(int i=0;i<compilado_ids.get(1).size();i++){
                            consultaDAO.delete_consulta(compilado_ids.get(1).get(i));
                        }
                        for(int i=0;i<compilado_ids.get(2).size();i++){
                            medicoDAO.delete_medico(compilado_ids.get(2).get(i));
                        }
                        
                        especialidadeDAO.delete_especialidade(Integer.parseInt(request.getParameter("id")));
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher del_adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Especialidade");
                        del_adm.forward(request, response);   
                    break;

                    case "Editar":
                        Especialidade especialidade = new Especialidade();
                        
                        especialidade = especialidadeDAO.get_especialidade(Integer.parseInt(request.getParameter("id")));
                        session.setAttribute("especialidade",especialidade);
                        
                        RequestDispatcher edt_esp = request.getRequestDispatcher("/view/CadastrarEspecialidade.jsp");
                        edt_esp.forward(request, response);

                    break;

                    case "Cadastrar":
                        session.setAttribute("especialidade",null);
                        
                        RequestDispatcher cad_esp = request.getRequestDispatcher("/view/CadastrarEspecialidade.jsp");
                        cad_esp.forward(request, response);
                    break;
                }
            break;
        
            case "Exame":
                switch(request.getParameter("arg")){
                    case "Visualizar":
                        
                        ArrayList<Exame> exames = new ArrayList<Exame>();

                        exames = exameDAO.get_exames();

                        if(exames.size() > 0){
                            session.setAttribute("exames",exames);
                            RequestDispatcher exms = request.getRequestDispatcher("./view/RelacaoExames.jsp");
                            exms.forward(request, response);
                        }else{
                            RequestDispatcher semexm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Exame");
                            semexm.forward(request, response);
                        }
                    break;
                    
                    case "Excluir":
                        ArrayList<Integer> compilado_ids = new ArrayList<Integer>();
                        
                        compilado_ids = exameDAO.get_idDeleteExame(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compilado_ids.size();i++){
                            exameDAO.delete_exame(compilado_ids.get(i));
                        }
                        
                        exameDAO.delete_tipoExame(Integer.parseInt(request.getParameter("id")));
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher del_adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Exame");
                        del_adm.forward(request, response);   
                    break;
                    
                    case "Editar":
                        Exame exame = new Exame();
                        
                        exame = exameDAO.get_exame(Integer.parseInt(request.getParameter("id")));
                        session.setAttribute("exame",exame);
                        
                        RequestDispatcher edt_exm = request.getRequestDispatcher("/view/CadastrarExame.jsp");
                        edt_exm.forward(request, response);

                    break;

                    case "Cadastrar":
                        session.setAttribute("exame",null);
                        
                        RequestDispatcher cad_exm = request.getRequestDispatcher("/view/CadastrarExame.jsp");
                        cad_exm.forward(request, response);
                    break;
                }
            break;
            
            case "Dashboard":
            
                session.setAttribute("adm", administrador_sessao);
                RequestDispatcher admin = request.getRequestDispatcher("./view/AreaAdministrador.jsp");
                admin.forward(request, response);    
            break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        switch(request.getParameter("type")){
            
            case "Paciente":
                ClienteDAO novo_cltDAO = new ClienteDAO();
                Cliente paciente = new Cliente();
                
                paciente.setNome(request.getParameter("nome"));
                paciente.setCpf(request.getParameter("CPF"));
                paciente.setSenha(request.getParameter("senha"));
                paciente.setIdtipoplano(Integer.parseInt(request.getParameter("plano")));
                paciente.setAutorizado(request.getParameter("autorizado").charAt(0));
                
                switch(request.getParameter("action")){
                
                    case "Cadastrar":
                        
                        novo_cltDAO.create_paciente(paciente);
                        
                        RequestDispatcher cad_pac = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Paciente");
                        cad_pac.forward(request, response);
                    break;
                    
                    case "Editar":
                        
                        novo_cltDAO.update_paciente(Integer.parseInt(request.getParameter("id")),paciente);
                        
                        RequestDispatcher clt_edit = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Paciente");
                        clt_edit.forward(request, response);
                    break;
                }  
            break;
            
            case "Medico":
                MedicoDAO novo_medDAO = new MedicoDAO();
                Medico medico = new Medico();
                
                medico.setNome(request.getParameter("nome"));
                medico.setCrm(Integer.parseInt(request.getParameter("crm")));
                medico.setEstadocrm(request.getParameter("estadocrm"));
                medico.setCpf(request.getParameter("CPF"));
                medico.setSenha(request.getParameter("senha"));
                medico.setIdespecialidade(Integer.parseInt(request.getParameter("especialidade")));
                medico.setAutorizado(request.getParameter("autorizado").charAt(0));
                
                switch(request.getParameter("action")){
                
                    case "Cadastrar":
                        
                        novo_medDAO.create_medico(medico);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher med = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Medico");
                        med.forward(request, response);
                    break;
                    
                    case "Editar":
                    
                        novo_medDAO.update_medico(Integer.parseInt(request.getParameter("id")),medico);
                        
                        RequestDispatcher med_edit = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Medico");
                        med_edit.forward(request, response);
                    break;
                }
            break;
                
            case "Administrador":
                AdministradorDAO novo_admDAO = new AdministradorDAO();
                Administrador novo_adm = new Administrador();
                
                switch(request.getParameter("action")){
                
                    case "Cadastrar":
                        novo_adm.setNome(request.getParameter("nome"));
                        novo_adm.setCpf(request.getParameter("CPF"));
                        novo_adm.setSenha(request.getParameter("senha"));

                        novo_admDAO.create_administrador(novo_adm);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Administrador");
                        adm.forward(request, response);           
                    break;
                    
                    case "Editar":
                        novo_adm.setNome(request.getParameter("nome"));
                        novo_adm.setCpf(request.getParameter("CPF"));
                        novo_adm.setSenha(request.getParameter("senha")); 
                        
                        novo_admDAO.update_administrador(Integer.parseInt(request.getParameter("id")),novo_adm);
                        
                        RequestDispatcher edt_adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Administrador");
                        edt_adm.forward(request, response);
                    break;
                }
            break;
            
            case "Plano":
                Plano novo_plano = new Plano();
                PlanoDAO novo_planDAO = new PlanoDAO();
                
                switch(request.getParameter("action")){
                
                    case "Cadastrar":
                        novo_plano.setDescricao(request.getParameter("nomePlano"));
                        
                        novo_planDAO.create_plano(novo_plano);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher pln = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Plano");
                        pln.forward(request, response);           
                    break;
                    
                    case "Editar":
                        novo_plano.setDescricao(request.getParameter("nomePlano"));
                        novo_planDAO.update_plano(Integer.parseInt(request.getParameter("id")),novo_plano);
                        
                        RequestDispatcher novopln = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Plano");
                        novopln.forward(request, response);
                     break;
                }
            break;
            
            case "Especialidade":
                EspecialidadeDAO nova_espDAO = new EspecialidadeDAO();
                Especialidade nova_especialidade = new Especialidade();
                
                switch(request.getParameter("action")){
                
                    case "Cadastrar":
                        
                        nova_especialidade.setDescricao(request.getParameter("nomeEspecialidade"));
                        nova_espDAO.create_especialidade(nova_especialidade);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher esp = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Especialidade");
                        esp.forward(request, response);           
                    break;
                    
                    case "Editar":
                        nova_especialidade.setDescricao(request.getParameter("nomeEspecialidade"));
                        nova_espDAO.update_especialidade(Integer.parseInt(request.getParameter("id")),nova_especialidade);
                        
                        RequestDispatcher novaesp = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Especialidade");
                        novaesp.forward(request, response);
                    break;
                }
            break;
            
            case "Exame":
                ExameDAO novo_exmDAO = new ExameDAO();
                Exame novo_exame = new Exame();
                
                switch(request.getParameter("action")){
                
                    case "Cadastrar":
                        
                        novo_exame.setDescricao(request.getParameter("nomeExame"));
                        novo_exmDAO.create_exame(novo_exame);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher exm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Exame");
                        exm.forward(request, response);           
                    break;
                    
                    case "Editar":
                        novo_exame.setDescricao(request.getParameter("nomeExame"));
                        novo_exmDAO.update_exame(Integer.parseInt(request.getParameter("id")),novo_exame);
                        
                        RequestDispatcher novoexm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Exame");
                        novoexm.forward(request, response);
                    break;
                }
            break;
        }
    }
}
