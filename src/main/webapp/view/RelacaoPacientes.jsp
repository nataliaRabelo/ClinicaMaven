<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Cliente"%>
<%@page import="aplicacao.Plano"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Cliente> lista_pacientes = (ArrayList<Cliente>)session.getAttribute("pacientes");
    ArrayList<Plano> lista_planos = (ArrayList<Plano>)session.getAttribute("planos_pacientes");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pacientes da Clínica</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Visualizar.css"/>
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
    </head>
    <body>
        <%@include file="BarraInterna.jsp" %>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        
        <br><br><br><br>
        
        <h2 style="text-align:center">Veja aqui todos os pacientes da Clínica.</h2>
        <br>
        <form action="./ControladorAdministrador" method="GET" style="text-align: center;">
            <button class="button button2" type="submit">Cadastrar Pacientes</button>
            <input type="hidden" name="arg" value="Cadastrar">
            <input type="hidden" name="type" value="Paciente">
        </form>
        <div class="div">
            <table>
                <tr>
                    <th>Paciente</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Senha</th>
                    <th>Autorizado</th>
                    <th>Plano</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                for(int i=0;i<lista_pacientes.size();i++){
                    out.println("<tr>");
                    out.println("<td>Paciente " + (i+1) + "</td>");
                    out.println("<td>" + lista_pacientes.get(i).getNome() + "</td>");
                    out.println("<td>" + lista_pacientes.get(i).getCpf() + "</td>");  
                    out.println("<td>" + lista_pacientes.get(i).getSenha() + "</td>");
                    if(lista_pacientes.get(i).getAutorizado() == 'S'){
                        out.println("<td style=\"color: green;\">Sim</td>");
                    }
                    else{
                        out.println("<td style=\"color: red;\">Não</td>");
                    }
                    out.println("<td>" + lista_planos.get(i).getDescricao() + "</td>");
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Editar&type=Paciente&id=" + lista_pacientes.get(i).getId()+ "\">"
                            + "<button type=\"button\" style=\"border-radius: 4px;\">Editar</button></a>"
                            + "</td>");
                        
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Excluir&type=Paciente&type=Paciente&id=" + lista_pacientes.get(i).getId()+ "\">"
                            + "<button type=\"button\" style=\"border-radius: 4px;\">Excluir</button></a>"
                            + "<input type=\"hidden\" name=\"type\" value=Paciente>"
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
