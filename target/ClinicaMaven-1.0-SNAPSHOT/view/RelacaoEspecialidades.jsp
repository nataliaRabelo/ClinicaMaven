<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Especialidade"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Especialidade> lista_especialidades = (ArrayList<Especialidade>)session.getAttribute("especs");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Especialidades da Clínica</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Visualizar.css"/>
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
    </head>
    <body>
        <%@include file="BarraInterna.jsp" %>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        
        <br><br><br><br>
        
        <h2 style="text-align:center">Veja aqui todos as especialidades da Clínica.</h2>
        <br>
        <form action="./ControladorAdministrador" method="GET" style="text-align: center;">
            <button class="button button2" type="submit">Cadastrar Especialidade</button>
            <input type="hidden" name="arg" value="Cadastrar">
            <input type="hidden" name="type" value="Especialidade">
        </form>
        
        <div class="div">
            <table>
                <tr>
                    <th>Especialidade</th>
                    <th>Descrição</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                for(int i=0;i<lista_especialidades.size();i++){
                    out.println("<tr>");
                    out.println("<td>Especialidade " + (i+1) + "</td>");
                    out.println("<td>" + lista_especialidades.get(i).getDescricao() + "</td>");
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Editar&type=Especialidade&id=" + lista_especialidades.get(i).getId()+ "\">"
                            + "<button type=\"button\" style=\"border-radius: 4px;\">Editar</button></a>"
                            + "</td>");
                        
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Excluir&type=Especialidade&id=" + lista_especialidades.get(i).getId()+ "\">"
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
