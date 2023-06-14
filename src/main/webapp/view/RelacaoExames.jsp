<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Exame"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Exame> lista_exames = (ArrayList<Exame>)session.getAttribute("exames");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exames da Clínica</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Visualizar.css"/>
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
    </head>
    <body>
        <%@include file="BarraInterna.jsp"%>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        
        <br><br><br><br>
        
        <h2 style="text-align:center">Veja aqui todos os tipos de exame da Clínica.</h2>
        <br>
        <form action="./ControladorAdministrador" method="GET" style="text-align: center;">
            <button class="button button2" type="submit">Cadastrar Exame</button>
            <input type="hidden" name="arg" value="Cadastrar">
            <input type="hidden" name="type" value="Exame">
        </form>
        <div class="div">
            <table>
                <tr>
                    <th>Tipo de Exame</th>
                    <th>Descrição</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                for(int i=0;i<lista_exames.size();i++){
                    out.println("<tr>");
                    out.println("<td>Exame " + (i+1) + "</td>");
                    out.println("<td>" + lista_exames.get(i).getDescricao() + "</td>");
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Editar&type=Exame&id=" + lista_exames.get(i).getId()+ "\">"
                            + "<button type=\"button\" style=\"border-radius: 4px;\">Editar</button></a>"
                            + "</td>");
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Excluir&type=Exame&id=" + lista_exames.get(i).getId()+ "\">"
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
