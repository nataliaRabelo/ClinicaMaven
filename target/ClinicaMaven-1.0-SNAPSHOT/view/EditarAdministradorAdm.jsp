<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Administrador"%>
<%
    Administrador administrador = (Administrador)session.getAttribute("administrador");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Administrador</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form method="POST" class="formulario">
                <h3>Edite o Administrador</h3>
                <br>
                <%
                out.println("<h5>Nome do Administrador: " + administrador.getNome() + "</h5>");
                %>
                <label for="nome">Nome:</label>
                <input type="text" name="nome" value="<%=administrador.getNome()%>" required>
                <br><br>
                <%
                out.println("<h5>CPF do Administrador: " + administrador.getCpf() + "</h5>");
                %>
                <label for="CPF">CPF:</label>
                <input type="text" name="CPF" value="<%=administrador.getCpf()%>" required>
                <br><br>
                <%
                out.println("<h5>Senha do Administrador: " + administrador.getSenha() + "</h5>");
                %>      
                <label for="senha">Senha:</label>
                <input type="text" name="senha" value="<%=administrador.getSenha()%>" required>
                <br><br>
                <input type="hidden" name="id" value="<%=administrador.getId()%>" required>
                <input type="submit" name="action" value="Editar">
                <input type="hidden" name="type" value="Administrador">   
            </form> 
            <a href="./ControladorAdministrador?arg=Visualizar&type=Administrador"><button type="button"><b>Voltar</b></button></a>    
        </div>
    </body>
</html>
