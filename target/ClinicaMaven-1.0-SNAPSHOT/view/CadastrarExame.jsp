<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Exame"%>
<%
    Exame exame = null;
    try{
        exame = (Exame)session.getAttribute("exame");
    }
    catch(NullPointerException e){
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%
        if(exame != null){
            out.println("<title>Editar Exame</title>");
        }else{
            out.println("<title>Cadastrar Exame</title>");
        }
        
        %>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 600px;">
            <form action="ControladorAdministrador" method="POST" class="formulario">
                <%
                String retorno = "Cadastrar";  
                
                if(exame != null){
                    out.println("<h3>Edição de Exame</h3>");
                    out.println("<br>");
                    out.println("<h5>Nome do Exame: " + exame.getDescricao() + "</h5>");    
                    retorno = "Editar";  
                    out.println("<input type=\"hidden\" name=\"id\" value=" + exame.getId() + ">");
                }
                else{
                    out.println("<h3>Cadastre um novo Exame</h3>");
                    out.println("<br>");
                }
                %>
                <label for="descricao">Descrição:</label>
                <input type="text" placeholder="Entre com a descrição do Exame" name="nomeExame" required>
                <br><br>
                <input type="submit" name="action" value="<%=retorno%>">
                <input type="hidden" name="type" value="Exame">
            </form>
            <a href="./ControladorAdministrador?arg=Visualizar&type=Exame"><button type="button"><b>Voltar</b></button></a>    
        </div>
    </body>
</html>
