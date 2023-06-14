<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Log In</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="../styles/Formulario.css"/>
        <link href="../bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="Barra.jsp" %>
        <script src="../bootstrap/bootstrap.bundle.min.js"></script>
        
        <br><br><br>
        
        <div class="quadro">
            <form action="../Autenticar" method="POST" class="formulario">
                <h3>Log In</h3>
                
                <br>
                
                <label for="papel"><b>Selecione o tipo de acesso:</b></label>
                
                <select id="papel" name="papel">
                    <option value="Cliente">Cliente</option>
                    <option value="Administrador">Administrador</option>
                    <option value="Medico">Médico</option>
                </select>
                <br><br>
                <label for="CPF"><b>CPF:</b></label>
                <input type="text" placeholder="Digite seu CPF" name="CPF" required>
                <br><br>
                <label for="senha"><b>Senha:</b></label>
                <input type="password" placeholder="Digite sua senha" name="senha" required> 
                <br><br>
                <input type="submit" value="Enviar">
            </form>    
            <br>
            <a href="../index.jsp"><button type="button"><b>Voltar</b></button></a>
        </div>
    </body>
</html>
