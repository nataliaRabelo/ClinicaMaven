<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Administrador"%>
<%
    Administrador adm = (Administrador)session.getAttribute("adm");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Área do Administrador</title>
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <style>
.quadro { 
    padding: 50px;
    margin: 20px;
    text-align: center; 
    
    background-color: white;
    border: 15px solid blue;
    padding: 50px;
    margin-top: 50px;
    width: 800px;
    margin: auto;
    margin-top: 50px;
    margin-bottom: 50px;
    
    .intern{
        width: 25%;
        height: 100px;
        display: inline-block;
        background: red;
        border: 15px solid red;
        padding: 100px;
        
    }
    
}
</style>
    </head>
    <body>
        <jsp:include page = "./BarraInterna.jsp" />
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>

        <br><br><br>
        
        <h3>Olá, Bem vindo(a), Administrador <%= adm.getNome() %>!</h3>
        
        <br><br>
        
        <div style="text-align: center;">
            <div>
            <!--<div class="intern" style="border: 15px solid red; display: inline-block;">-->
                <!--<div class="intern" style="border: 15px solid green; width: 30%;">-->
                    <form action="./ControladorAdministrador" method="GET">
                        <button class="button button2" type="submit">Ver Pacientes</button>
                        <input type="hidden" name="arg" value="Visualizar">
                        <input type="hidden" name="type" value="Paciente">
                    </form>
                    <br>
                    <form action="./ControladorAdministrador" method="GET">
                        <button class="button button2" type="submit">Ver Médicos</button>
                        <input type="hidden" name="arg" value="Visualizar">
                        <input type="hidden" name="type" value="Medico">
                    </form>
                    <br>
                    <form action="./ControladorAdministrador" method="GET">
                        <button class="button button2" type="submit">Ver Administradores</button>
                        <input type="hidden" name="arg" value="Visualizar">
                        <input type="hidden" name="type" value="Administrador">
                    </form>
                    <br>
                    <form action="./ControladorAdministrador" method="GET">
                        <button class="button button2" type="submit">Ver Planos</button>
                        <input type="hidden" name="arg" value="Visualizar">
                        <input type="hidden" name="type" value="Plano">
                    </form>
                    <br>
                    <form action="./ControladorAdministrador" method="GET">
                        <button class="button button2" type="submit">Ver Especialidades</button>
                        <input type="hidden" name="arg" value="Visualizar">
                        <input type="hidden" name="type" value="Especialidade">
                    </form>
                    <br>
                    <form action="./ControladorAdministrador" method="GET">
                        <button class="button button2" type="submit">Ver Exames</button>    
                        <input type="hidden" name="arg" value="Visualizar">
                        <input type="hidden" name="type" value="Exame">
                    </form>
                    <br><br>
            </div>
        </div>
    </body>
</html>
