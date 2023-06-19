package servlet;import utils.Constantes;

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
import utils.Constantes;
import java.util.List;

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

        switch(request.getParameter(Constantes.TYPE)){
            case "Paciente": 
                switch(request.getParameter(Constantes.ARG)){
                    case Constantes.VISUALIZAR:
                        visualizarPacientes(request, response, session, clienteDAO, planoDAO);
                        break;

                    case "Excluir":
                        excluirPaciente(request, response, clienteDAO, consultaDAO, exameDAO);
                        break;

                    case Constantes.EDITAR:
                        editarPaciente(request, response, session, clienteDAO, planoDAO);
                        break;

                    case Constantes.CADASTRAR:
                        cadastrarPaciente(request, response, session, planoDAO);
                        break;
                }
                break;

            case "Medico":
                switch(request.getParameter("arg")){
                    case Constantes.VISUALIZAR:
                        visualizarMedicos(request, response, session, medicoDAO, especialidadeDAO);
                        break;

                    case Constantes.EXCLUIR:
                        excluirMedico(request, response, medicoDAO, consultaDAO, exameDAO);
                        break;

                    case Constantes.EDITAR:
                        editarMedico(request, response, session, medicoDAO, especialidadeDAO);
                        break;

                    case Constantes.CADASTRAR:
                        cadastrarMedico(request, response, session, especialidadeDAO);
                        break;

                    case Constantes.VERCONSULTAS:
                        verConsultas(request, response, session, medicoDAO, clienteDAO, exameDAO);
                        break;
                }
                break;

            case Constantes.ADMINISTRADOR:
                switch(request.getParameter(Constantes.ARG)){
                    case Constantes.VISUALIZAR:     
                        visualizarAdministradores(request, response, session, administradorDAO, administradorSessao);
                        break;

                    case Constantes.EXCLUIR:
                        excluirAdministrador(request, response, administradorDAO);
                        break;

                    case Constantes.EDITAR: 
                        editarAdministrador(request, response, session, administradorDAO);
                        break;

                    case Constantes.CADASTRAR:
                        cadastrarAdministrador(request, response, session);
                        break;
                }  
                break;

            case Constantes.PLANO:
                switch(request.getParameter(Constantes.ARG)){
                    case Constantes.VISUALIZAR:
                        visualizarPlanos(request, response, session, planoDAO);
                        break;

                    case Constantes.EXCLUIR:
                        excluirPlano(request, response, planoDAO, consultaDAO, exameDAO, clienteDAO, session);
                        break;

                    case Constantes.EDITAR:
                        editarPlano(request, response, session, planoDAO);
                        break;

                    case Constantes.CADASTRAR:
                        cadastrarPlano(request, response, session);
                        break;
                }  
                break;

            case Constantes.ESPECIALIDADE:
                switch(request.getParameter(Constantes.ARG)){
                    case Constantes.VISUALIZAR:
                        visualizarEspecialidades(request, response, session, especialidadeDAO);
                        break;

                    case Constantes.EXCLUIR:
                        excluirEspecialidade(request, response, especialidadeDAO, consultaDAO, exameDAO, medicoDAO, session);
                        break;

                    case Constantes.EDITAR:
                        editarEspecialidade(request, response, session, especialidadeDAO);
                        break;

                    case Constantes.CADASTRAR:
                        cadastrarEspecialidade(request, response, session);
                        break;
                }
                break;

            case Constantes.EXAME:
                switch(request.getParameter(Constantes.ARG)){
                    case Constantes.VISUALIZAR:
                        visualizarExames(request, response, session, exameDAO);
                        break;

                    case Constantes.EXCLUIR:
                        excluirExame(request, response, exameDAO, session);
                        break;

                    case Constantes.EDITAR:
                        editarExame(request, response, session, exameDAO);
                        break;

                    case Constantes.CADASTRAR:
                        cadastrarExame(request, response, session);
                        break;
                }
                break;

            case Constantes.DASHBOARD:
                mostrarDashboard(request, response, session, administradorSessao);
                break;
            }
        }
        
         // Método para visualizar consultas de um médico
        private void verConsultas(HttpServletRequest request, HttpServletResponse response, HttpSession session, MedicoDAO medicoDAO, ClienteDAO clienteDAO, ExameDAO exameDAO)
                throws ServletException, IOException {
            List<Consulta> listaConsultas = medicoDAO.getConsultas(Integer.parseInt(request.getParameter("id")));

            if(listaConsultas.size() > 0) {
                List<List<String>> listaExamesCompilado = new ArrayList<>();

                for(int i=0; i<listaConsultas.size(); i++) {
                    List<String> listaExames = new ArrayList<>();
                    listaExames = exameDAO.getExamesDaConsulta(listaConsultas.get(i).getId(), listaExames);

                    if(listaExames.isEmpty()) {
                        listaExamesCompilado.add(null);
                    } else {
                        listaExamesCompilado.add(listaExames);
                    }
                }

                List<String> nomePacientes = new ArrayList<>();

                for(int i=0; i<listaConsultas.size(); i++) {
                    String nomePaciente = clienteDAO.getNomePaciente(listaConsultas.get(i).getIdpaciente());
                    nomePacientes.add(nomePaciente);
                }

                Medico medico = medicoDAO.getMedico(Integer.parseInt(request.getParameter(Constantes.ID)));
                String nomeMedico = medico.getNome();

                session.setAttribute("lista_nomes", nomePacientes);
                session.setAttribute("lista_exames", listaExamesCompilado);
                session.setAttribute("lista_consultas", listaConsultas);
                session.setAttribute("nome_medico", nomeMedico);
                RequestDispatcher admCons = request.getRequestDispatcher("./view/RelacaoConsultas.jsp");
                admCons.forward(request, response);
            } else {
                RequestDispatcher clt = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=SemConsulta");
                clt.forward(request, response);
            }
        }
        
            // Método para editar especialidade
        private void editarEspecialidade(HttpServletRequest request, HttpServletResponse response, HttpSession session, EspecialidadeDAO especialidadeDAO)
                throws ServletException, IOException {
            Especialidade especialidade = especialidadeDAO.getEspecialidade(Integer.parseInt(request.getParameter(Constantes.ID)));
            session.setAttribute(Constantes.ESPECIALIDADE, especialidade);
            RequestDispatcher edtEsp = request.getRequestDispatcher("/view/CadastrarEspecialidade.jsp");
            edtEsp.forward(request, response);
        }
        
        private void cadastrarEspecialidade(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
            session.setAttribute("especialidade", null);
            RequestDispatcher cadEsp = request.getRequestDispatcher("/view/CadastrarEspecialidade.jsp");
            cadEsp.forward(request, response);
        }

        private void visualizarExames(HttpServletRequest request, HttpServletResponse response, HttpSession session, ExameDAO exameDAO)
            throws ServletException, IOException {
            List<Exame> exames = exameDAO.getExames();

            if (exames.size() > 0) {
                session.setAttribute("exames", exames);
                RequestDispatcher exms = request.getRequestDispatcher("./view/RelacaoExames.jsp");
                exms.forward(request, response);
            } else {
                RequestDispatcher semexm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Exame");
                semexm.forward(request, response);
            }
        }
        
        private void excluirExame(HttpServletRequest request, HttpServletResponse response, ExameDAO exameDAO, HttpSession session)
            throws ServletException, IOException {
            List<Integer> compiladoIds = exameDAO.getIdDeleteExame(Integer.parseInt(request.getParameter(Constantes.ID)));
                        
            for(int i=0; i<compiladoIds.size(); i++){
            exameDAO.deleteExame(compiladoIds.get(i));
            }
                        
            exameDAO.deleteTipoExame(Integer.parseInt(request.getParameter(Constantes.ID)));
                        
            session.setAttribute(Constantes.IDENTIDADE, 2);
            RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Exame");
            delAdm.forward(request, response);
        }

        private void editarExame(HttpServletRequest request, HttpServletResponse response, HttpSession session, ExameDAO exameDAO)
            throws ServletException, IOException {
            Exame exame = exameDAO.getExame(Integer.parseInt(request.getParameter(Constantes.ID)));
            session.setAttribute("exame", exame);
            RequestDispatcher edtExm = request.getRequestDispatcher("/view/CadastrarExame.jsp");
            edtExm.forward(request, response);
        }
        
        private void cadastrarExame(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
            session.setAttribute("exame", null);
            RequestDispatcher cadExm = request.getRequestDispatcher("/view/CadastrarExame.jsp");
            cadExm.forward(request, response);
        }
        
        private void editarAdministrador(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdministradorDAO administradorDAO)
            throws ServletException, IOException {
            Administrador administrador = administradorDAO.getAdministrador(Integer.parseInt(request.getParameter(Constantes.ID)));
            session.setAttribute("administrador", administrador);
            RequestDispatcher edtAdm = request.getRequestDispatcher("/view/EditarAdministradorAdm.jsp");
            edtAdm.forward(request, response);
        }

        private void cadastrarAdministrador(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
            session.setAttribute("administrador", null);
            RequestDispatcher crAdm = request.getRequestDispatcher("/view/CadastrarAdministrador.jsp");
            crAdm.forward(request, response);
        }

        private void visualizarPlanos(HttpServletRequest request, HttpServletResponse response, HttpSession session, PlanoDAO planoDAO)
            throws ServletException, IOException {
            List<Plano> planos = planoDAO.getPlanos();

            if (planos.size() > 0) {
                session.setAttribute("planos", planos);
                RequestDispatcher cadpla = request.getRequestDispatcher("./view/RelacaoPlanos.jsp");
                cadpla.forward(request, response);
            } else {
                RequestDispatcher sempla = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Plano");
                sempla.forward(request, response);
            }
        }

            // Método para excluir plano
        private void excluirPlano(HttpServletRequest request, HttpServletResponse response, PlanoDAO planoDAO, ConsultaDAO consultaDAO, ExameDAO exameDAO, ClienteDAO clienteDAO, HttpSession session)
            throws ServletException, IOException {
            List<List<Integer>> compiladoIds = planoDAO.getIdDeletePlano(Integer.parseInt(request.getParameter(Constantes.ID)));

            for(int i=0; i<compiladoIds.get(0).size(); i++){
                exameDAO.deleteExame(compiladoIds.get(0).get(i));
            }
            for(int i=0; i<compiladoIds.get(1).size(); i++){
                consultaDAO.deleteConsulta(compiladoIds.get(1).get(i));
            }
            for(int i=0; i<compiladoIds.get(2).size(); i++){
                clienteDAO.deletePaciente(compiladoIds.get(2).get(i));
            }

            planoDAO.deletePlano(Integer.parseInt(request.getParameter(Constantes.ID)));

            session.setAttribute(Constantes.IDENTIDADE, 2);
            RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Plano");
            delAdm.forward(request, response);
        }
        
            // Método para editar plano
        private void editarPlano(HttpServletRequest request, HttpServletResponse response, HttpSession session, PlanoDAO planoDAO)
                throws ServletException, IOException {
            Plano plano = planoDAO.getPlano(Integer.parseInt(request.getParameter(Constantes.ID)));
            session.setAttribute("plano", plano);
            RequestDispatcher edtPln = request.getRequestDispatcher("/view/CadastrarPlano.jsp");
            edtPln.forward(request, response);
        }
        
        private void cadastrarPlano(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
            session.setAttribute("plano", null);
            RequestDispatcher cadPlan = request.getRequestDispatcher("/view/CadastrarPlano.jsp");
            cadPlan.forward(request, response);
        }
        
        private void visualizarEspecialidades(HttpServletRequest request, HttpServletResponse response, HttpSession session, EspecialidadeDAO especialidadeDAO)
            throws ServletException, IOException {
            List<Especialidade> especs = especialidadeDAO.getEspecialidades();

            if (especs.size() > 0) {
                session.setAttribute("especs", especs);
                RequestDispatcher cadesp = request.getRequestDispatcher("./view/RelacaoEspecialidades.jsp");
                cadesp.forward(request, response);
            } else {
                RequestDispatcher semesp = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Especialidade");
                semesp.forward(request, response);
            }
        }
        
        private void excluirEspecialidade(HttpServletRequest request, HttpServletResponse response, EspecialidadeDAO especialidadeDAO, ConsultaDAO consultaDAO, ExameDAO exameDAO, MedicoDAO medicoDAO, HttpSession session)
                throws ServletException, IOException {
            List<List<Integer>> compiladoIds = especialidadeDAO.getIdDeleteEspecialidade(Integer.parseInt(request.getParameter(Constantes.ID)));

            for(int i=0; i<compiladoIds.get(0).size(); i++){
                exameDAO.deleteExame(compiladoIds.get(0).get(i));
            }
            for(int i=0; i<compiladoIds.get(1).size(); i++){
                consultaDAO.deleteConsulta(compiladoIds.get(1).get(i));
            }
            for(int i=0; i<compiladoIds.get(2).size(); i++){
                medicoDAO.deleteMedico(compiladoIds.get(2).get(i));
            }

            especialidadeDAO.deleteEspecialidade(Integer.parseInt(request.getParameter("id")));

            session.setAttribute(Constantes.IDENTIDADE, 2);
            RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Especialidade");
            delAdm.forward(request, response);
        }

        private void visualizarAdministradores(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdministradorDAO administradorDAO, Administrador administradorSessao)
            throws ServletException, IOException {
            List<Administrador> adms = administradorDAO.getAdministradores();
        
            session.setAttribute("adms", adms);
            session.setAttribute("administrador_sessao", administradorSessao);
            RequestDispatcher viAdm = request.getRequestDispatcher("./view/RelacaoAdministradores.jsp");
            viAdm.forward(request, response);
        }

        // Método para excluir administrador
        private void excluirAdministrador(HttpServletRequest request, HttpServletResponse response, AdministradorDAO administradorDAO)
                throws ServletException, IOException {
            administradorDAO.deleteAdministrador(Integer.parseInt(request.getParameter(Constantes.ID)));
            RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Administrador");
            delAdm.forward(request, response);
        }

        private void excluirMedico(HttpServletRequest request, HttpServletResponse response, MedicoDAO medicoDAO, ConsultaDAO consultaDAO, ExameDAO exameDAO)
            throws ServletException, IOException {
        List<List<Integer>> compiladoIds = medicoDAO.getIdDeleteMedico(Integer.parseInt(request.getParameter("id")));
        
        for(int i=0; i<compiladoIds.get(0).size(); i++) {
            exameDAO.deleteExame(compiladoIds.get(0).get(i));
        }
        for(int i=0; i<compiladoIds.get(1).size(); i++) {
            consultaDAO.deleteConsulta(compiladoIds.get(1).get(i));
        }
        
        medicoDAO.deleteMedico(Integer.parseInt(request.getParameter("id")));
        
        RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Medico");
        delAdm.forward(request, response);
        }
        
            // Método para editar médico
    private void editarMedico(HttpServletRequest request, HttpServletResponse response, HttpSession session, MedicoDAO medicoDAO, EspecialidadeDAO especialidadeDAO)
            throws ServletException, IOException {
        Medico medico = medicoDAO.getMedico(Integer.parseInt(request.getParameter("id")));
        Especialidade especialidade = especialidadeDAO.getEspecialidade(medico.getIdespecialidade());
        List<Especialidade> especsDisponiveis = especialidadeDAO.getEspecialidades();
        
        session.setAttribute(Constantes.MEDICO, medico);
        session.setAttribute(Constantes.ESPECIALIDADE, especialidade);
        session.setAttribute(Constantes.ESPECIALIDADE, especsDisponiveis);
        
        RequestDispatcher edtMed = request.getRequestDispatcher("/view/EditarMedicoAdm.jsp");
        edtMed.forward(request, response);
    }

    
    private void visualizarMedicos(HttpServletRequest request, HttpServletResponse response, HttpSession session, MedicoDAO medicoDAO, EspecialidadeDAO especialidadeDAO)
            throws ServletException, IOException {
        List<Medico> medicos = medicoDAO.getMedicos();
        
        if(medicos.size() > 0) {
            List<Especialidade> especialidadesMedicos = new ArrayList<>();

            for(int i=0; i<medicos.size(); i++) {
                especialidadesMedicos.add(especialidadeDAO.getEspecialidade(medicos.get(i).getIdespecialidade()));
            }

            session.setAttribute("medicos", medicos);
            session.setAttribute("especialidades_medicos", especialidadesMedicos);
            RequestDispatcher cadmed = request.getRequestDispatcher("./view/RelacaoMedicos.jsp");
            cadmed.forward(request, response);
        } else {
            RequestDispatcher semmed = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Medico");
            semmed.forward(request, response);
        }
    }
    
        private void cadastrarMedico(HttpServletRequest request, HttpServletResponse response, HttpSession session, EspecialidadeDAO especialidadeDAO)
            throws ServletException, IOException {
        List<Especialidade> especs = especialidadeDAO.getEspecialidades();
        
        if(!especs.isEmpty()) {
            session.setAttribute("especialidades", especs);
            RequestDispatcher cadMed = request.getRequestDispatcher("/view/CadastrarMedico.jsp");
            cadMed.forward(request, response);
        } else {
            RequestDispatcher cad = request.getRequestDispatcher("/view/ConfirmacaoAdm.jsp?arg=SemRequisito&type=Medico");
            cad.forward(request, response);
        }
    }
   
    private void visualizarPacientes(HttpServletRequest request, HttpServletResponse response, HttpSession session, ClienteDAO clienteDAO, PlanoDAO planoDAO)
            throws ServletException, IOException {
        List<Cliente> pacientes = clienteDAO.getPacientes();

        if(pacientes.size() > 0) {
            List<Plano> planosPacientes = new ArrayList<>();

            for(int i=0; i<pacientes.size(); i++) {
                planosPacientes.add(planoDAO.getPlano(pacientes.get(i).getIdtipoplano()));
            }

            session.setAttribute("pacientes", pacientes);
            session.setAttribute("planos_pacientes", planosPacientes);
            RequestDispatcher cadpac = request.getRequestDispatcher("./view/RelacaoPacientes.jsp");
            cadpac.forward(request, response);
        } else {
            RequestDispatcher sempac = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Vazio&type=Paciente");
            sempac.forward(request, response);
        }
    }

    private void excluirPaciente(HttpServletRequest request, HttpServletResponse response, ClienteDAO clienteDAO, ConsultaDAO consultaDAO, ExameDAO exameDAO)
            throws ServletException, IOException {
        List<List<Integer>> compiladoIds = clienteDAO.getIdDeletePaciente(Integer.parseInt(request.getParameter(Constantes.ID)));

        for(int i=0; i<compiladoIds.get(0).size(); i++) {
            exameDAO.deleteExame(compiladoIds.get(0).get(i));
        }
        for(int i=0; i<compiladoIds.get(1).size(); i++) {
            consultaDAO.deleteConsulta(compiladoIds.get(1).get(i));
        }

        clienteDAO.deletePaciente(Integer.parseInt(request.getParameter("id")));

        RequestDispatcher delAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Excluido&type=Paciente");
        delAdm.forward(request, response);
    }

    private void editarPaciente(HttpServletRequest request, HttpServletResponse response, HttpSession session, ClienteDAO clienteDAO, PlanoDAO planoDAO)
            throws ServletException, IOException {
        Cliente paciente = clienteDAO.getPaciente(Integer.parseInt(request.getParameter(Constantes.ID)));
        Plano plano = planoDAO.getPlano(paciente.getIdtipoplano());
        List<Plano> planosDisponiveis = planoDAO.getPlanos();

        session.setAttribute(Constantes.PACIENTE, paciente);
        session.setAttribute(Constantes.PLANO, plano);
        session.setAttribute(Constantes.PLANOS, planosDisponiveis);

        RequestDispatcher edtPac = request.getRequestDispatcher("/view/EditarPacienteAdm.jsp");
        edtPac.forward(request, response);
    }

    private void cadastrarPaciente(HttpServletRequest request, HttpServletResponse response, HttpSession session, PlanoDAO planoDAO)
            throws ServletException, IOException {
        List<Plano> planos = planoDAO.getPlanos();

        session.setAttribute(Constantes.PACIENTE, null);
        session.setAttribute(Constantes.PLANO, null);
        session.setAttribute(Constantes.PLANOS, planos);

        if(planos.size() > 0) {
            RequestDispatcher cad = request.getRequestDispatcher("/view/CadastrarPaciente.jsp");
            cad.forward(request, response);
        } else {
            RequestDispatcher cad = request.getRequestDispatcher("/view/ConfirmacaoAdm.jsp?arg=SemRequisito&type=Paciente");
            cad.forward(request, response);
        }
    }

    // Métodos para os outros casos...

    private void mostrarDashboard(HttpServletRequest request, HttpServletResponse response, HttpSession session, Administrador administradorSessao)
            throws ServletException, IOException {
        session.setAttribute("adm", administradorSessao);
        RequestDispatcher admin = request.getRequestDispatcher("./view/AreaAdministrador.jsp");
        admin.forward(request, response);    
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        switch(request.getParameter("type")){
            
            case Constantes.PACIENTE2:
                ClienteDAO novoCltDAO = new ClienteDAO();
                Cliente paciente = new Cliente();
                
                paciente.setNome(request.getParameter(Constantes.NOME));
                paciente.setCpf(request.getParameter(Constantes.CPF));
                paciente.setSenha(request.getParameter(Constantes.SENHA));
                paciente.setIdtipoplano(Integer.parseInt(request.getParameter(Constantes.PLANO)));
                paciente.setAutorizado(request.getParameter(Constantes.AUTORIZADO).charAt(0));
                
                if(request.getParameter(Constantes.ACTION).equals(Constantes.CADASTRAR)){
                        novoCltDAO.createPaciente(paciente);
                        
                        RequestDispatcher cadPac = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Paciente");
                        cadPac.forward(request, response);
                }
                else if(request.getParameter(Constantes.ACTION).equals(Constantes.EDITAR)){  
                        novoCltDAO.updatePaciente(Integer.parseInt(request.getParameter(Constantes.ID)),paciente);
                        
                        RequestDispatcher cltEdit = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Paciente");
                        cltEdit.forward(request, response);
                }  
            break;

            
            case Constantes.MEDICO2:
                MedicoDAO novoMedDAO = new MedicoDAO();
                Medico medico = new Medico();
                
                medico.setNome(request.getParameter(Constantes.NOME));
                medico.setCrm(Integer.parseInt(request.getParameter(Constantes.CRM)));
                medico.setEstadocrm(request.getParameter(Constantes.ESTADOCRM));
                medico.setCpf(request.getParameter(Constantes.CPF));
                medico.setSenha(request.getParameter(Constantes.SENHA));
                medico.setIdespecialidade(Integer.parseInt(request.getParameter(Constantes.ESPECIALIDADE)));
                medico.setAutorizado(request.getParameter(Constantes.AUTORIZADO).charAt(0));
                
                if(request.getParameter(Constantes.ACTION).equals(Constantes.CADASTRAR)){
                        novoMedDAO.createMedico(medico);
                        
                        session.setAttribute(Constantes.IDENTIDADE, 2);
                        RequestDispatcher med = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Medico");
                        med.forward(request, response);
                }
                else if(request.getParameter(Constantes.ACTION).equals(Constantes.EDITAR)){
                        novoMedDAO.updateMedico(Integer.parseInt(request.getParameter(Constantes.ID)),medico);
                        
                        RequestDispatcher medEdit = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Medico");
                        medEdit.forward(request, response);
                }
            break;

                
            case Constantes.ADMINISTRADOR:
                AdministradorDAO novoAdmDAO = new AdministradorDAO();
                Administrador novoAdm = new Administrador();
                
                if(request.getParameter(Constantes.ACTION).equals(Constantes.CADASTRAR)){
                        novoAdm.setNome(request.getParameter(Constantes.NOME));
                        novoAdm.setCpf(request.getParameter(Constantes.CPF));
                        novoAdm.setSenha(request.getParameter(Constantes.SENHA));

                        novoAdmDAO.createAdministrador(novoAdm);
                        
                        session.setAttribute(Constantes.IDENTIDADE, 2);
                        RequestDispatcher adm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Administrador");
                        adm.forward(request, response);           
                }
                else if(request.getParameter(Constantes.ACTION).equals(Constantes.EDITAR)){
                        novoAdm.setNome(request.getParameter(Constantes.NOME));
                        novoAdm.setCpf(request.getParameter(Constantes.CPF));
                        novoAdm.setSenha(request.getParameter(Constantes.SENHA)); 
                        
                        novoAdmDAO.updateAdministrador(Integer.parseInt(request.getParameter(Constantes.ID)),novoAdm);
                        
                        RequestDispatcher edtAdm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Administrador");
                        edtAdm.forward(request, response);
                }
            break;



            
            case Constantes.PLANO2:
                Plano novoPlano = new Plano();
                PlanoDAO novoPlanoDAO = new PlanoDAO();
                
                if(request.getParameter(Constantes.ACTION).equals(Constantes.CADASTRAR)){
                        novoPlano.setDescricao(request.getParameter(Constantes.NOMEPLANO));
                        
                        novoPlanoDAO.createPlano(novoPlano);
                        
                        session.setAttribute(Constantes.IDENTIDADE, 2);
                        RequestDispatcher pln = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Plano");
                        pln.forward(request, response);           
                }
                else if(request.getParameter(Constantes.ACTION).equals(Constantes.EDITAR)){
                        novoPlano.setDescricao(request.getParameter(Constantes.NOMEPLANO));
                        novoPlanoDAO.updatePlano(Integer.parseInt(request.getParameter(Constantes.ID)),novoPlano);
                        
                        RequestDispatcher novopln = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Plano");
                        novopln.forward(request, response);
                }
            break;


            
            case Constantes.ESPECIALIDADE:
                EspecialidadeDAO novaEspDAO = new EspecialidadeDAO();
                Especialidade novaEspecialidade = new Especialidade();
                
                if(request.getParameter(Constantes.ACTION).equals(Constantes.CADASTRAR)){
                        novaEspecialidade.setDescricao(request.getParameter("nomeEspecialidade"));
                        novaEspDAO.createEspecialidade(novaEspecialidade);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher esp = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Especialidade");
                        esp.forward(request, response);           
                }
                else if(request.getParameter(Constantes.ACTION).equals("Editar")){
                        novaEspecialidade.setDescricao(request.getParameter("nomeEspecialidade"));
                        novaEspDAO.updateEspecialidade(Integer.parseInt(request.getParameter("id")),novaEspecialidade);
                        
                        RequestDispatcher novaesp = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Especialidade");
                        novaesp.forward(request, response);
                }
            break;


            
            case Constantes.EXAME:
                ExameDAO novoExmDAO = new ExameDAO();
                Exame novoExame = new Exame();
                
                if(request.getParameter(Constantes.ACTION).equals(Constantes.CADASTRAR)){
                        novoExame.setDescricao(request.getParameter("nomeExame"));
                        novoExmDAO.createExame(novoExame);
                        
                        session.setAttribute("identidade", 2);
                        RequestDispatcher exm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Cadastrado&type=Exame");
                        exm.forward(request, response);           
                }
                else if (request.getParameter("action").equals("Editar")){   
                        novoExame.setDescricao(request.getParameter("nomeExame"));
                        novoExmDAO.updateExame(Integer.parseInt(request.getParameter("id")),novoExame);
                        
                        RequestDispatcher novoexm = request.getRequestDispatcher("./view/ConfirmacaoAdm.jsp?arg=Editar&type=Exame");
                        novoexm.forward(request, response);
                }
            break;


        }
    }
}
