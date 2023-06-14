<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Administrador"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Administrador> lista_adms = (ArrayList<Administrador>)session.getAttribute("adms");
    Administrador administrador_sessao = (Administrador)session.getAttribute("administrador_sessao");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administradores da Clínica</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Visualizar.css"/>
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
    </head>
    <body>
        <%@include file="BarraInterna.jsp" %>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        
        <br><br><br><br>
        
        <h2 style="text-align:center">Veja aqui todos os administradores da Clínica.</h2>
        <br>
        <form action="./ControladorAdministrador" method="GET" style="text-align: center;">
            <button class="button button2" type="submit">Cadastrar Administrador</button>
            <input type="hidden" name="arg" value="Cadastrar">
            <input type="hidden" name="type" value="Administrador">
        </form>
        <div class="div">
            <table>
                <tr>
                    <th>Administrador</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Senha</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                for(int i=0;i<lista_adms.size();i++){
                    out.println("<tr>");
                    out.println("<td>Administrador " + (i+1) + "</td>");
                    out.println("<td>" + lista_adms.get(i).getNome() + "</td>");
                    out.println("<td>" + lista_adms.get(i).getCpf() + "</td>");  
                    out.println("<td>" + lista_adms.get(i).getSenha() + "</td>");
                    out.println("<td>"
                            + "<a href=\"./ControladorAdministrador?arg=Editar&type=Administrador&id=" + lista_adms.get(i).getId()+ "\">"
                            + "<button type=\"button\" style=\"border-radius: 4px;\">Editar</button></a>"
                            + "<input type=\"hidden\" name=\"id_adm\" value= "+ lista_adms.get(i).getId() +">"
                            + "</td>");
                    
                    if(administrador_sessao.getId() == lista_adms.get(i).getId()){
                        out.println("<td></td>");
                    }else{
                        out.println("<td>"
                                + "<a href=\"./ControladorAdministrador?arg=Excluir&type=Administrador&id=" + lista_adms.get(i).getId()+ "\">"
                                + "<button type=\"button\" style=\"border-radius: 4px;\">Excluir</button></a>"
                                + "<input type=\"hidden\" name=\"id_adm\" value= "+ lista_adms.get(i).getId() +">"
                                + "</td>");
                        out.println("</tr>");
                    }
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
