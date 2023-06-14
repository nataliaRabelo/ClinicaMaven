<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String nome = request.getParameter("type");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Oooooops!</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        <div style="border: 15px solid red;border-radius: 12px; padding: 30px;
            margin: 150px; text-align: center;">
        
            <h1>Ooooooops! Isso é um erro!</h1>
            <%
                if(nome.equals("Login")){
                    out.println("<h3>Login ou Senha incorretos!</h3>");
                }
                if(nome.equals("Autorizacao")){
                    out.println("<h3>Você não está autorizado a acessar a Clínica WeB no momento!</h3>");
                }
            %>
            <a href="./view/Login.jsp"><button type="button"><b>Voltar</b></button></a>
        </div>
    </body>
</html>
