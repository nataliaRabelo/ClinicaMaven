<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Administrador"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastrar Administrador</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form method="POST" class="formulario">
                <h3>Cadastre um novo Administrador</h3>
                <br>
                <label for="nome">Nome:</label>
                <input type="text" placeholder="Entre com o nome do Administrador" name="nome" required>
                <br><br>
                <label for="CPF">CPF:</label>
                <input type="text" placeholder="Entre com o CPF do Administrador" name="CPF" required>
                <br><br
                <label for="senha">Senha:</label>
                <input type="password" placeholder="Entre com a senha do Administrador" name="senha" required> 
                <br><br>
                <input type="submit" name="action" value="Cadastrar">
                <input type="hidden" name="type" value="Administrador">   
            </form> 
            <a href="./ControladorAdministrador?arg=Visualizar&type=Administrador"><button type="button"><b>Voltar</b></button></a>    
        </div>
    </body>
</html>
