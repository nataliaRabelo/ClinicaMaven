<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%
    Cliente cliente = (Cliente)session.getAttribute("cliente");
    
    ArrayList<Object> lista = (ArrayList<Object>)session.getAttribute("lista");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Nova Consulta</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form action="./ControladorCliente" method="POST" class="formulario">
                <h3>Marque uma nova Consulta!</h3>
                <br><br>
                <label for="data">Data:</label>
                <input type="date" name="data" required>
                <br><br>
                <label for="hora">Horário:</label>
                <input type="time" name="hora" required>
                <br><br>
                <label for="med">Especialidade e Médico:</label>
                <select name="id_med">
                <%
                    int iesp = 0, ikey = 2,imed = 1;
                    for(int i=0;i<lista.size()/3;i++){
                        out.println("<option value=" + lista.get(ikey) + ">" + lista.get(iesp)+ " - " + lista.get(imed) + "</option>");
                        iesp+=3;
                        ikey+=3;
                        imed+=3;
                    }
                %>
                </select>
                <br><br>
                <input type="submit" name="acao" value="Enviar">
                <label for="id_cliente"></label>
                <input type="hidden" name="id_cliente" value="<%= cliente.getId() %>">
            </form> 
            <br>
            <a href="./ControladorCliente?arg=Dashboard"><button type="button"><b>Voltar</b></button></a>
        </div>       
    </body>
</html>
