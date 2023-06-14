<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Especialidade"%>
<%
    Especialidade especialidade = null;
    try{
        especialidade = (Especialidade)session.getAttribute("especialidade");
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
        if(especialidade != null){
            out.println("<title>Editar Especialidade</title>");
        }else{
            out.println("<title>Cadastrar Especialidade</title>");
        }
        
        %>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro">
            <form action="ControladorAdministrador" method="POST" class="formulario">                
                <%
                String retorno = "Cadastrar";  
                
                if(especialidade != null){
                    out.println("<h3>Edição da Especialidade</h3>");
                    out.println("<br>");
                    out.println("<h5>Nome da Especialidade: " + especialidade.getDescricao() + "</h5>");    
                    retorno = "Editar";  
                    out.println("<input type=\"hidden\" name=\"id\" value=" + especialidade.getId() + ">");
                }
                else{
                    out.println("<h3>Cadastre uma nova Especialidade</h3>");
                    out.println("<br>");
                }
                %>
                <label for="nomeEspecialidade">Especialidade:</label>
                <input type="text" placeholder="Entre com o nome da Especialidade" name="nomeEspecialidade" required>
                <br><br>
                <input type="submit" name="action" value="<%=retorno%>">
                <input type="hidden" name="type" value="Especialidade">
            </form> 
            <a href="./ControladorAdministrador?arg=Visualizar&type=Especialidade"><button type="button"><b>Voltar</b></button></a>    
        </div>
    </body>
</html>
