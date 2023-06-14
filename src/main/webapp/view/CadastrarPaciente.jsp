<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aplicacao.Plano"%>
<%
    ArrayList<Plano> planos = (ArrayList<Plano>)session.getAttribute("planos");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastrar Paciente</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form action="./ControladorAdministrador" method="POST" class="formulario">
                <h3>Cadastre um novo Paciente</h3>
                <br><br>
                <label for="nome">Nome:</label>
                <input type="text" placeholder="Entre com o nome do Paciente" name="nome" required>
                <br><br>
                <label for="CPF">CPF:</label>
                <input type="text" placeholder="Entre com o CPF do Paciente" name="CPF" required>
                <br><br>
                <label for="senha">Senha:</label>
                <input type="password" placeholder="Entre com a senha do Paciente" name="senha" required> 
                <br><br>
                <label for="autorizado">Autoriazado ?</label>
                <select name="autorizado">
                    <option value="S">Sim</option>
                    <option value="N">Não</option>
                </select>
                <br><br>
                <label for="plano">Plano de Saúde:</label>
                <select id="plano" name="plano">
                    <%
                    for(int i=0;i<planos.size();i++){
                        out.println("<option value=" + planos.get(i).getId() + ">" + planos.get(i).getDescricao() + "</option>");
                    }
                    %>
                </select>
                <br><br>
                <input type="submit" name="action" value="Cadastrar">
                <input type="hidden" name="type" value="Paciente">
            </form>
            <br>
            <a href="./ControladorAdministrador?arg=Visualizar&type=Paciente"><button type="button"><b>Voltar</b></button></a>
        </div>
    </body>
</html>
