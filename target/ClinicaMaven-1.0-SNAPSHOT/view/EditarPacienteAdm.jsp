<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aplicacao.Plano"%>
<%@page import="aplicacao.Cliente"%>
<%
    Cliente paciente = (Cliente)session.getAttribute("paciente");
    Plano plano = (Plano)session.getAttribute("plano");
    ArrayList<Plano> planos = (ArrayList<Plano>)session.getAttribute("planos");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Paciente</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form action="./ControladorAdministrador" method="POST" class="formulario">
                <h3>Edite o Paciente</h3>
                <br><br>
                <%
                out.println("<h5>Nome do Paciente: " + paciente.getNome() + "</h5>");
                %>
                <label for="nome">Nome:</label>
                <input type="text" name="nome" value="<%=paciente.getNome()%>" required>
                <br><br>
                <%
                out.println("<h5>CPF do Paciente: " + paciente.getCpf() + "</h5>");
                %>
                <label for="CPF">CPF:</label>
                <input type="text" name="CPF" value="<%=paciente.getCpf()%>" required>
                <br><br>
                <%
                out.println("<h5>Senha do Paciente: " + paciente.getSenha() + "</h5>");
                %>
                <label for="senha">Senha:</label>
                <input type="text" name="senha" value="<%=paciente.getSenha()%>" required> 
                <br><br>
                <%
                if(paciente.getAutorizado() == 'S'){
                    out.println("<h5>Autorização: Sim</h5>");
                }else{
                    out.println("<h5>Autorização: Não</h5>");
                }
                %>
                <label for="autorizado">Autorizado ?</label>
                <select name="autorizado">
                    <option value="S">Sim</option>
                    <option value="N">Não</option>
                </select>
                <br><br>
                <%
                out.println("<h5>Plano do Paciente: " + plano.getDescricao() + "</h5>");
                %>
                <label for="plano">Plano de Saúde:</label>
                <select id="plano" name="plano">
                    <%
                    for(int i=0;i<planos.size();i++){
                        out.println("<option value=" + planos.get(i).getId() + ">" + planos.get(i).getDescricao() + "</option>");
                    }
                    %>
                </select>
                <br><br>
                <input type="submit" name="action" value="Editar">
                <input type="hidden" name="id" value="<%=paciente.getId()%>">
                <input type="hidden" name="type" value="Paciente">
            </form>
            <br>
            <a href="./ControladorAdministrador?arg=Visualizar&type=Paciente"><button type="button"><b>Voltar</b></button></a>
        </div>
    </body>
</html>
