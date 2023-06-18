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
        Administrador administradorSessao = (Administrador)session.getAttribute("adm");
        
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
                        
                        ArrayList<Cliente> pacientes = new ArrayList<>();
                        pacientes = clienteDAO.getPacientes();
                        
                        if(pacientes.size() > 0){
                        
                            ArrayList<Plano> planosPacientes = new ArrayList<>();

                            for(int i=0;i<pacientes.size();i++){
                                planosPacientes.add(planoDAO.getPlano(pacientes.get(i).getIdtipoplano()));
                            }

                            session.setAttribute("pacientes", pacientes);
                            session.setAttribute("planos_pacientes", planosPacientes);
                            RequestDispatcher cadpac = request.getRequestDispatcher("./view/RelacaoPacientes.jsp");
                            cadpac.forward(request, response);
                        }else{
                            RequestDispatcher sempac = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Paciente");
                            sempac.forward(request, response);
                        }
                    break;
                    
                    case "Excluir":
                        ArrayList<ArrayList<Integer>> compiladoIds = new ArrayList<>();
                        
                        compiladoIds = clienteDAO.getIdDeletePaciente(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compiladoIds.get(0).size();i++){
                            exameDAO.deleteExame(compiladoIds.get(0).get(i));
                        }
                        for(int i=0;i<compiladoIds.get(1).size();i++){
                            consultaDAO.deleteConsulta(compiladoIds.get(1).get(i));
                        }
                        
                        clienteDAO.deletePaciente(Integer.parseInt(request.getParameter("id")));
                        
                        RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Paciente");
                        delAdm.forward(request, response);
                    break;


                    
                    case "Editar":
                        Cliente paciente = new Cliente();
                        Plano plano = new Plano();
                        ArrayList<Plano> planosDisponiveis = new ArrayList<>();
                        
                        paciente = clienteDAO.getPaciente(Integer.parseInt(request.getParameter("id")));
                        plano = planoDAO.getPlano(paciente.getIdtipoplano());
                        planosDisponiveis = planoDAO.getPlanos();
                        
                        session.setAttribute("paciente",paciente);
                        session.setAttribute("plano",plano);
                        session.setAttribute("planos", planosDisponiveis);
                        
                        RequestDispatcher edtPac = request.getRequestDispatcher("/view/EditarPacienteAdm.jsp");
                        edtPac.forward(request, response);
                    break;


                    
                    case "Cadastrar":
                        ArrayList<Plano> planos = new ArrayList<>();
                        planos = planoDAO.getPlanos();
                        
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
                        ArrayList<Medico> medicos = new ArrayList<>();

                        medicos = medicoDAO.getMedicos();
                        
                        if(medicos.size() > 0){
                        
                            ArrayList<Especialidade> especialidadesMedicos = new ArrayList<>();

                            for(int i=0;i<medicos.size();i++){
                                especialidadesMedicos.add(especialidadeDAO.getEspecialidade(medicos.get(i).getIdespecialidade()));
                            }

                            session.setAttribute("medicos",medicos);
                            session.setAttribute("especialidades_medicos",especialidadesMedicos);
                            RequestDispatcher cadmed = request.getRequestDispatcher("./view/RelacaoMedicos.jsp");
                            cadmed.forward(request, response);
                        }else{
                            RequestDispatcher semmed = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Medico");
                            semmed.forward(request, response);
                        }
                    break;
                
                    case "Excluir":
                        ArrayList<ArrayList<Integer>> compiladoIds = new ArrayList<>();
                        
                        compiladoIds = medicoDAO.getIdDeleteMedico(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compiladoIds.get(0).size();i++){
                            exameDAO.deleteExame(compiladoIds.get(0).get(i));
                        }
                        for(int i=0;i<compiladoIds.get(1).size();i++){
                            consultaDAO.deleteConsulta(compiladoIds.get(1).get(i));
                        }
                        
                        medicoDAO.deleteMedico(Integer.parseInt(request.getParameter("id")));
                        
                        RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Medico");
                        delAdm.forward(request, response);
                    break;


                
                    case "Editar":
                        
                        Especialidade especialidade  = new Especialidade();
                        ArrayList<Especialidade> especsDisponiveis = new ArrayList<>();
                        
                        medico = medicoDAO.getMedico(Integer.parseInt(request.getParameter("id")));
                        especialidade = especialidadeDAO.getEspecialidade(medico.getIdespecialidade());
                        especsDisponiveis = especialidadeDAO.getEspecialidades();
                        
                        session.setAttribute("medico",medico);
                        session.setAttribute("especialidade",especialidade);
                        session.setAttribute("especialidades", especsDisponiveis);
                        
                        RequestDispatcher edtMed = request.getRequestDispatcher("/view/EditarMedicoAdm.jsp");
                        edtMed.forward(request, response);
                    break;


                    
                    case "Cadastrar":
                        ArrayList<Especialidade> especs = new ArrayList<>();

                        especs = especialidadeDAO.getEspecialidades();
                        
                        if(especs.size() > 0){
                            
                            session.setAttribute("especialidades", especs);
                            RequestDispatcher cadMed = request.getRequestDispatcher("/view/CadastrarMedico.jsp");
                            cadMed.forward(request, response);
                        }else{
                            RequestDispatcher cad = request.getRequestDispatcher("/view/ConfirmacaoAdm.jsp?arg=SemRequisito&type=Medico");
                            cad.forward(request, response);
                        }
                    break;
                    
                    case "VerConsultas":
                        
                        ArrayList<Consulta> listaConsultas = new ArrayList<>();                
                        listaConsultas = medicoDAO.getConsultas(Integer.parseInt(request.getParameter("id")));
                    
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

                            for(int i=0;i<listaConsultas.size();i++){

                                String nomePaciente = clienteDAO.getNomePaciente(listaConsultas.get(i).getIdpaciente());
                                nomePacientes.add(nomePaciente);
                            }
                            
                            medico = medicoDAO.getMedico(Integer.parseInt(request.getParameter("id")));
                            String nomeMedico = medico.getNome();
                            
                            session.setAttribute("lista_nomes", nomePacientes);
                            session.setAttribute("lista_exames", listaExamesCompilado);
                            session.setAttribute("lista_consultas", listaConsultas);
                            session.setAttribute("nome_medico", nomeMedico);
                            RequestDispatcher admCons = request.getRequestDispatcher("./view/RelacaoConsultas.jsp");
                            admCons.forward(request, response);
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
                        ArrayList<Administrador> adms = new ArrayList<>();

                        adms = administradorDAO.getAdministradores();
                        
                        session.setAttribute("adms",adms);
                        session.setAttribute("administrador_sessao",administradorSessao);
                        RequestDispatcher viAdm = request.getRequestDispatcher("./view/RelacaoAdministradores.jsp");
                        viAdm.forward(request, response);
                    break;

                    
                    case "Excluir":
                        administradorDAO.deleteAdministrador(Integer.parseInt(request.getParameter("id")));
                        RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Administrador");
                        delAdm.forward(request, response);
                    break;

                
                    case "Editar": 
                        Administrador administrador = new Administrador();
                        
                        administrador = administradorDAO.getAdministrador(Integer.parseInt(request.getParameter("id")));
                        
                        session.setAttribute("administrador",administrador);
                        RequestDispatcher edtAdm = request.getRequestDispatcher("/view/EditarAdministradorAdm.jsp");
                        edtAdm.forward(request, response);
                    break;

                    
                    case "Cadastrar":
                        session.setAttribute("administrador",null);
                        RequestDispatcher crAdm = request.getRequestDispatcher("/view/CadastrarAdministrador.jsp");
                        crAdm.forward(request, response);
                    break;


                }  
            break;
            
            case "Plano":
                switch(request.getParameter("arg")){
                    case "Visualizar":
                        
                        ArrayList<Plano> planos = new ArrayList<>();

                        planos = planoDAO.getPlanos();

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
                        
                        ArrayList<ArrayList<Integer>> compiladoIds = new ArrayList<>();
                        
                        compiladoIds = planoDAO.getIdDeletePlano(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compiladoIds.get(0).size();i++){
                            exameDAO.deleteExame(compiladoIds.get(0).get(i));
                        }
                        for(int i=0;i<compiladoIds.get(1).size();i++){
                            consultaDAO.deleteConsulta(compiladoIds.get(1).get(i));
                        }
                        for(int i=0;i<compiladoIds.get(2).size();i++){
                            clienteDAO.deletePaciente(compiladoIds.get(2).get(i));
                        }
                        
                        planoDAO.deletePlano(Integer.parseInt(request.getParameter("id")));
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Plano");
                        delAdm.forward(request, response);
                    break;




                    case "Editar":
                        Plano plano = new Plano();
                        
                        plano = planoDAO.getPlano(Integer.parseInt(request.getParameter("id")));
                        session.setAttribute("plano",plano);
                        
                        RequestDispatcher edtPln = request.getRequestDispatcher("/view/CadastrarPlano.jsp");
                        edtPln.forward(request, response);
                    break;


                    case "Cadastrar":
                        session.setAttribute("plano",null);
                        
                        RequestDispatcher cadPlan = request.getRequestDispatcher("/view/CadastrarPlano.jsp");
                        cadPlan.forward(request, response);
                    break;

                }  
            break;
            
            case "Especialidade":
                switch(request.getParameter("arg")){
                    case "Visualizar":
                        
                        ArrayList<Especialidade> especs = new ArrayList<>();

                        especs = especialidadeDAO.getEspecialidades();

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
                        ArrayList<ArrayList<Integer>> compiladoIds = new ArrayList<>();
                        
                        compiladoIds = especialidadeDAO.getIdDeleteEspecialidade(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compiladoIds.get(0).size();i++){
                            exameDAO.deleteExame(compiladoIds.get(0).get(i));
                        }
                        for(int i=0;i<compiladoIds.get(1).size();i++){
                            consultaDAO.deleteConsulta(compiladoIds.get(1).get(i));
                        }
                        for(int i=0;i<compiladoIds.get(2).size();i++){
                            medicoDAO.deleteMedico(compiladoIds.get(2).get(i));
                        }
                        
                        especialidadeDAO.deleteEspecialidade(Integer.parseInt(request.getParameter("id")));
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Especialidade");
                        delAdm.forward(request, response);   
                    break;



                    case "Editar":
                        Especialidade especialidade = new Especialidade();
                        
                        especialidade = especialidadeDAO.getEspecialidade(Integer.parseInt(request.getParameter("id")));
                        session.setAttribute("especialidade",especialidade);
                        
                        RequestDispatcher edtEsp = request.getRequestDispatcher("/view/CadastrarEspecialidade.jsp");
                        edtEsp.forward(request, response);

                    break;


                    case "Cadastrar":
                        session.setAttribute("especialidade",null);
                        
                        RequestDispatcher cadEsp = request.getRequestDispatcher("/view/CadastrarEspecialidade.jsp");
                        cadEsp.forward(request, response);
                    break;

                }
            break;
        
            case "Exame":
                switch(request.getParameter("arg")){
                    case "Visualizar":
                        
                        ArrayList<Exame> exames = new ArrayList<>();

                        exames = exameDAO.getExames();

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
                        ArrayList<Integer> compiladoIds = new ArrayList<>();
                        
                        compiladoIds = exameDAO.getIdDeleteExame(Integer.parseInt(request.getParameter("id")));
                        
                        for(int i=0;i<compiladoIds.size();i++){
                            exameDAO.deleteExame(compiladoIds.get(i));
                        }
                        
                        exameDAO.deleteTipoExame(Integer.parseInt(request.getParameter("id")));
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Exame");
                        delAdm.forward(request, response);   
                    break;


                    
                    case "Editar":
                        Exame exame = new Exame();
                        
                        exame = exameDAO.getExame(Integer.parseInt(request.getParameter("id")));
                        session.setAttribute("exame",exame);
                        
                        RequestDispatcher edtExm = request.getRequestDispatcher("/view/CadastrarExame.jsp");
                        edtExm.forward(request, response);

                    break;


                    case "Cadastrar":
                        session.setAttribute("exame",null);
                        
                        RequestDispatcher cadExm = request.getRequestDispatcher("/view/CadastrarExame.jsp");
                        cadExm.forward(request, response);
                    break;

                }
            break;
            
            case "Dashboard":
            
                session.setAttribute("adm", administradorSessao);
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
                ClienteDAO novoCltDAO = new ClienteDAO();
                Cliente paciente = new Cliente();
                
                paciente.setNome(request.getParameter("nome"));
                paciente.setCpf(request.getParameter("CPF"));
                paciente.setSenha(request.getParameter("senha"));
                paciente.setIdtipoplano(Integer.parseInt(request.getParameter("plano")));
                paciente.setAutorizado(request.getParameter("autorizado").charAt(0));
                
                if(request.getParameter("action").equals("Cadastrar")){
                        novoCltDAO.createPaciente(paciente);
                        
                        RequestDispatcher cadPac = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Paciente");
                        cadPac.forward(request, response);
                }
                else if(request.getParameter("action").equals("Editar")){  
                        novoCltDAO.updatePaciente(Integer.parseInt(request.getParameter("id")),paciente);
                        
                        RequestDispatcher cltEdit = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Paciente");
                        cltEdit.forward(request, response);
                }  
            break;

            
            case "Medico":
                MedicoDAO novoMedDAO = new MedicoDAO();
                Medico medico = new Medico();
                
                medico.setNome(request.getParameter("nome"));
                medico.setCrm(Integer.parseInt(request.getParameter("crm")));
                medico.setEstadocrm(request.getParameter("estadocrm"));
                medico.setCpf(request.getParameter("CPF"));
                medico.setSenha(request.getParameter("senha"));
                medico.setIdespecialidade(Integer.parseInt(request.getParameter("especialidade")));
                medico.setAutorizado(request.getParameter("autorizado").charAt(0));
                
                if(request.getParameter("action").equals("Cadastrar")){
                        novoMedDAO.createMedico(medico);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher med = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Medico");
                        med.forward(request, response);
                }
                else if(request.getParameter("action").equals("Editar")){
                        novoMedDAO.updateMedico(Integer.parseInt(request.getParameter("id")),medico);
                        
                        RequestDispatcher med_Edit = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Medico");
                        med_Edit.forward(request, response);
                }
            break;

                
            case "Administrador":
                AdministradorDAO novo_AdmDAO = new AdministradorDAO();
                Administrador novo_adm = new Administrador();
                
                if(request.getParameter("action").equals("Cadastrar")){
                        novo_adm.setNome(request.getParameter("nome"));
                        novo_adm.setCpf(request.getParameter("CPF"));
                        novo_adm.setSenha(request.getParameter("senha"));

                        novo_AdmDAO.createAdministrador(novo_adm);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Administrador");
                        adm.forward(request, response);           
                }
                else if(request.getParameter("action").equals("Editar")){
                        novo_adm.setNome(request.getParameter("nome"));
                        novo_adm.setCpf(request.getParameter("CPF"));
                        novo_adm.setSenha(request.getParameter("senha")); 
                        
                        novo_AdmDAO.updateAdministrador(Integer.parseInt(request.getParameter("id")),novo_adm);
                        
                        RequestDispatcher edt_adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Administrador");
                        edt_adm.forward(request, response);
                }
            break;

            
            case "Plano":
                Plano novo_plano = new Plano();
                PlanoDAO novo_planDAO = new PlanoDAO();
                
                if(request.getParameter("action").equals("Cadastrar")){
                        novo_plano.setDescricao(request.getParameter("nomePlano"));
                        
                        novo_planDAO.createPlano(novo_plano);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher pln = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Plano");
                        pln.forward(request, response);           
                }
                else if(request.getParameter("action").equals("Editar")){
                        novo_plano.setDescricao(request.getParameter("nomePlano"));
                        novo_planDAO.updatePlano(Integer.parseInt(request.getParameter("id")),novo_plano);
                        
                        RequestDispatcher novopln = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Plano");
                        novopln.forward(request, response);
                }
            break;
            
            case "Especialidade":
                EspecialidadeDAO nova_espDAO = new EspecialidadeDAO();
                Especialidade nova_especialidade = new Especialidade();
                
                if(request.getParameter("action").equals("Cadastrar")){
                        nova_especialidade.setDescricao(request.getParameter("nomeEspecialidade"));
                        nova_espDAO.createEspecialidade(nova_especialidade);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher esp = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Especialidade");
                        esp.forward(request, response);           
                }
                else if(request.getParameter("action").equals("Editar")){
                        nova_especialidade.setDescricao(request.getParameter("nomeEspecialidade"));
                        nova_espDAO.updateEspecialidade(Integer.parseInt(request.getParameter("id")),nova_especialidade);
                        
                        RequestDispatcher novaesp = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Especialidade");
                        novaesp.forward(request, response);
                }
            break;
            
            case "Exame":
                ExameDAO novo_exmDAO = new ExameDAO();
                Exame novo_exame = new Exame();
                
                if(request.getParameter("action").equals("Cadastrar")){
                        novo_exame.setDescricao(request.getParameter("nomeExame"));
                        novo_exmDAO.createExame(novo_exame);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher exm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Exame");
                        exm.forward(request, response);           
                }
                else if (request.getParameter("action").equals("Editar")){   
                        novo_exame.setDescricao(request.getParameter("nomeExame"));
                        novo_exmDAO.updateExame(Integer.parseInt(request.getParameter("id")),novo_exame);
                        
                        RequestDispatcher novoexm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Exame");
                        novoexm.forward(request, response);
                }
            break;
        }
    }
}
