<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Medico"%>
<%@page import="aplicacao.Especialidade"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Medico> lista_medicos = (ArrayList<Medico>)session.getAttribute("medicos");
    ArrayList<Especialidade> lista_especialidades = (ArrayList<Especialidade>)session.getAttribute("especialidades_medicos");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Médicos da Clínica</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Visualizar.css"/>
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
    </head>
    <body>
        <%@include file="BarraInterna.jsp" %>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        
        <br><br><br><br>
        
        <h2 style="text-align:center">Veja aqui todos os médicos da Clínica.</h2>
        <br>
        <form action="./ControladorAdministrador" method="GET" style="text-align: center;">
            <button class="button button2" type="submit">Cadastrar Médicos</button>
            <input type="hidden" name="arg" value="Cadastrar">
            <input type="hidden" name="type" value="Medico">
        </form>
        <div class="div">
            <table>
                <tr>
                    <th>Médico</th>
                    <th>Nome</th>
                    <th>CRM</th>
                    <th>Estado CRM</th>
                    <th>CPF</th>
                    <th>Senha</th>
                    <th>Autorizado</th>
                    <th>Especialidade</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                for(int i=0;i<lista_medicos.size();i++){
                    out.println("<tr>");
                    out.println("<td>Médico " + (i+1) + "</td>");
                    out.println("<td>" + lista_medicos.get(i).getNome() + "</td>");
                    out.println("<td>" + lista_medicos.get(i).getCrm() + "</td>");
                    out.println("<td>" + lista_medicos.get(i).getEstadocrm() + "</td>");
                    out.println("<td>" + lista_medicos.get(i).getCpf() + "</td>");  
                    out.println("<td>" + lista_medicos.get(i).getSenha() + "</td>");
                    if(lista_medicos.get(i).getAutorizado() == 'S'){
                        out.println("<td style=\"color: green;\">Sim</td>");
                    }
                    else{
                        out.println("<td style=\"color: red;\">Não</td>");
                    }
                    out.println("<td>" + lista_especialidades.get(i).getDescricao() + "</td>");
                    
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=VerConsultas&type=Medico&id=" + lista_medicos.get(i).getId()+ "\">"
                            + "<button type=\"button\" style=\"border-radius: 4px;\">Ver Consultas</button></a>"
                            + "</td>");
                    
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Editar&type=Medico&id=" + lista_medicos.get(i).getId()+ "\">"
                            + "<button type=\"button\" style=\"border-radius: 4px;\">Editar</button></a>"
                            + "</td>");
                        
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Excluir&type=Medico&id=" + lista_medicos.get(i).getId()+ "\">"
                            + "<button type=\"button\" style=\"border-radius: 4px;\">Excluir</button></a>"
                            + "</td>");
                    out.println("</tr>");
                }
                %>
            </table>
            <br><br>    
            <div style="text-align:center">
            <a href="./ControladorAdministrador?type=Dashboard"><button type="button"><b>Voltar</b></button></a>
            </div>
        </div>
    </body>
</html>
