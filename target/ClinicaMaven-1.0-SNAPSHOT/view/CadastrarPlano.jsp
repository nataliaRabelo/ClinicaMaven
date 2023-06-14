<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Plano"%>
<%
    Plano plano = null;
    try{
        plano = (Plano)session.getAttribute("plano");
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
        if(plano != null){
            out.println("<title>Editar Plano</title>");
        }else{
            out.println("<title>Cadastrar Plano</title>");
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
                
                if(plano != null){
                    out.println("<h3>Edição de Plano</h3>");
                    out.println("<br>");
                    out.println("<h5>Nome do Plano: " + plano.getDescricao() + "</h5>");    
                    retorno = "Editar";  
                    out.println("<input type=\"hidden\" name=\"id\" value=" + plano.getId() + ">");
                }
                else{
                    out.println("<h3>Cadastre um novo Plano</h3>");
                    out.println("<br>");
                }
                %>
                <label for="descricao">Descrição:</label>
                <input type="text" placeholder="Entre com a descrição do Plano" name="nomePlano" required>
                <br><br>
                <input type="submit" name="action" value="<%=retorno%>">
                <input type="hidden" name="type" value="Plano">
            </form>
            <a href="./ControladorAdministrador?arg=Visualizar&type=Plano"><button type="button"><b>Voltar</b></button></a>    
        </div>
    </body>
</html>
