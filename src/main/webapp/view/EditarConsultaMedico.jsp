<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  
    String id_consulta = (String)session.getAttribute("id_consulta");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Concluir Consulta</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form action="./ControladorMedico" method="POST" class="formulario">
                <h4>Faça uma descrição da Consulta para marca-lá como concluída.</h4>
                <h6>Após marcada como concluída, você poderá solicitar exames para a mesma.</h6>
                <br><br>
                <label>Descrição:</label>
                <input type="text" name="descricao" required>
                <br><br>
                <input type="hidden" name="realizada" value="S">
                <input type="hidden" name="id_consulta" value="<%= id_consulta %>">
                <input type="submit" name="acao" value="Enviar">
            </form> 
            <div>
            <a href="./ControladorMedico?arg=Visualizar"><button type="button"><b>Voltar</b></button></a>
            </div>
        </div>       
    </body>
</html>
