<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Consulta"%>
<%@page import="java.util.ArrayList"%>
<%
    Consulta consulta = (Consulta)session.getAttribute("consulta_edit");
    ArrayList<Object> lista = (ArrayList<Object>)session.getAttribute("consulta_edit_infos");
    ArrayList<Object> lista_disponiveis = (ArrayList<Object>)session.getAttribute("lista_disponiveis");
    
    String infos[] = consulta.getData().split(" ");
    String data = infos[0];
    
    String dia = infos[0].substring(8,10);
    String mes = infos[0].substring(5,7);
    String ano = infos[0].substring(0,4);
    
    String data_br = dia + "/" + mes + "/" + ano;
    
    String hora = infos[1];
    hora = hora.substring(0, hora.length() - 5);
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Consulta</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form action="./ControladorCliente" method="POST" class="formulario">
                <h3>Edite a Consulta</h3>
                <br><br>
                <h5>Data da Consulta: <%= data_br %></h5>
                <label for="data">Data:</label>
                <input type="date" name="data" value="<%= data %>" required>
                <br><br><br>
                <h5>Horário da Consulta: <%= hora %></h5>
                <label for="hora">Horário:</label>
                <input type="time" name="hora" value="<%= hora %>" required>
                <br><br><br>
                <h5>Especialidade e Médico da Consulta: <%= lista.get(1) + " - " + lista.get(0) %></h5>
                <label for="med">Nova Especialidade e Médico:</label>
                <select name="id_med">
                    <%
                    int iesp = 0, ikey = 2,imed = 1;
                    
                    for(int i=0;i<lista_disponiveis.size()/3;i++){
                        out.println("<option value=" + lista_disponiveis.get(ikey) + ">" + lista_disponiveis.get(iesp)+ " - " + lista_disponiveis.get(imed) + "</option>");
                        iesp+=3;
                        ikey+=3;
                        imed+=3;
                    }
                    %>
                </select>                
                <br><br>
                <label for="id_cliente"></label>
                <input type="hidden" name="id_cliente" value="<%= consulta.getIdpaciente() %>">
                <input type="hidden" name="id_consulta" value="<%= consulta.getId() %>">
                <input style="width: 160px;" type="submit" name="acao" value="Remarcar Consulta">
            </form> 
            <br>
            <div>
                <a href="./ControladorCliente?arg=Visualizar"><button type="button"><b>Voltar</b></button></a>
            </div>
        </div>       
    </body>
</html>
