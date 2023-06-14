<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Cliente"%>
<%
        Cliente cliente = (Cliente)session.getAttribute("cliente");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Área do Cliente</title>
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page = "./BarraInterna.jsp" />
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        
        <br><br><br>
        
        <h3>Olá, Bem vindo(a) <%= cliente.getNome() %>!</h3>
        <br><br>
        
        <div style="text-align: center; display:inline;">
            
            <div style="display:inline">
            <form action="./ControladorCliente" method="GET">
                <button class="button button2" type="submit">Visualizar Consultas</button>
                <input type="hidden" name="arg" value="Visualizar">
            </form>

            <form action="./ControladorCliente" method="GET">
                <button class="button button2" type="submit">Marcar uma Consulta</button>
                <input type="hidden" name="arg" value="Marcar">
            </form>
            </div>
        </div>
    </body>
</html>
