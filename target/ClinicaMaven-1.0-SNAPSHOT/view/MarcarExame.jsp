<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%    
    ArrayList<Object> lista = (ArrayList<Object>)session.getAttribute("lista_exames");
    String id_consulta = (String)session.getAttribute("id_consulta");
    
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Exame</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro">
            <form action="./ControladorMedico" method="POST" class="formulario">
                <h3>Solicitar Exame para o paciente</h3>
                <br><br>
                <label for="tipo">Tipo de Exame:</label>
                <select name="id_exame">
                    <%
                    int iex = 1, ikey = 0;
                    
                    for(int i=0;i<lista.size()/2;i++){
                        out.println("<option value=" + lista.get(ikey) + ">" + lista.get(iex) + "</option>");
                        iex+=2;
                        ikey+=2;
                    }
                    %>
                </select> <br><br>
                <input type="hidden" name="id_consulta" value="<%= id_consulta %>">
                <input type="hidden" name="acao" value="Marcar Exame">
                <input type="submit" value="Enviar">
            </form>
            <div>
            <a href="./ControladorMedico?arg=Visualizar"><button type="button"><b>Voltar</b></button></a>
            </div>
        </div>
    </body>
</html>
