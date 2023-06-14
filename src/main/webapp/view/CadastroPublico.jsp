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
        <title>Cadastre-se</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <link rel="stylesheet" type="text/css" href="./styles/Barra.css"/>

        <nav>
            <ul class="estilobarra">  
                <li class="estilolista"><a href="./index.jsp">Home</a></li>
                <li class="estilolista"><a href="./index.jsp#Espec">Especialidades</a></li>
                <li class="estilolista"><a href="./index.jsp#Conv">Convênios</a></li>  
                <li class="estilologin"><a href="./view/Login.jsp">Log In</a></li>
                <li class="estilologin"><a href="./Cadastrar?arg=cadastro">Cadastre-se</a></li>
            </ul>
        </nav>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        
        <br><br>
        
        <div class="quadro" style="width: 700px;">
            <form action="./Cadastrar" method="POST" class="formulario">
                <h3>Cadastre-se agora mesmo na Clínica WeB</h3>
                <br>
                <label for="nome">Nome:</label>
                <input type="text" placeholder="Entre com o seu nome" name="nome" required>
                <br><br>
                <label for="CPF">CPF:</label>
                <input type="text" placeholder="Entre com o seu CPF" name="CPF" required>
                <br><br>
                <label for="senha">Senha:</label>
                <input type="password" placeholder="Entre com a sua senha" name="senha" required> 
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
                <input type="submit" value="Enviar">
            </form>
            <br>
            <a href="./index.jsp"><button type="button"><b>Voltar</b></button></a>    
        </div>
    </body>
</html>
