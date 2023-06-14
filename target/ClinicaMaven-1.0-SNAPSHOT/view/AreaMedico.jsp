<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Medico"%>
<%
    Medico medico = (Medico)session.getAttribute("medico");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Área do Médico</title>
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page = "./BarraInterna.jsp" />
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>

        <br><br><br>
        
        <h3>Olá, Bem vindo(a) Dr.(a) <%= medico.getNome() %>!</h3>
        
        <br><br>
        
        <div style="text-align: center; display:inline;">
            <form action="./ControladorMedico" method="GET">
                <button class="button button2" type="submit">Visualizar Consultas</button>
                <input type="hidden" name="arg" value="Visualizar">
            </form>   
        </div>
    </body>
</html>
