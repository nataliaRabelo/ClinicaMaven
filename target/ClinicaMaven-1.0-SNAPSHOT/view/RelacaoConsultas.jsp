<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Medico"%>
<%@page import="aplicacao.Consulta"%>
<%@page import="java.util.ArrayList"%>
<%
    String nome_medico = (String)session.getAttribute("nome_medico");
    ArrayList<Consulta> lista = (ArrayList<Consulta>)session.getAttribute("lista_consultas");
    ArrayList<ArrayList<String>> lista_exames = (ArrayList<ArrayList<String>>)session.getAttribute("lista_exames");
    ArrayList<String> lista_nomes = (ArrayList<String>)session.getAttribute("lista_nomes");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Visualizar Consultas</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Visualizar.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        <%@include file="BarraInterna.jsp" %>
        
        <br><br><br><br>
        
        <h2 style="text-align:center">Veja aqui as Consultas do Dr(a) <%=nome_medico%>.</h2>        
        <div class="div">
            <table>
                <tr>
                    <th>Consulta</th>
                    <th>Paciente</th>
                    <th>Data</th>
                    <th>Hora</th>
                    <th>Descrição</th>
                    <th>Realizada</th>
                    <th>Exames</th>
                </tr>
                <%
                for(int i=0;i<lista.size();i++){
                    String infos[] = lista.get(i).getData().split(" ");
                    String dia = infos[0].substring(8,10);
                    String mes = infos[0].substring(5,7);
                    String ano = infos[0].substring(0,4);

                    String data = dia + "/" + mes + "/" + ano;
                    String hora = infos[1];
                    hora = hora.substring(0, hora.length() - 5);
                    out.println("<tr>");
                    out.println("<td>Consulta " + (i+1) + "</td>");
                    out.println("<td>" + lista_nomes.get(i) + "</td>");
                    out.println("<td>" + data + "</td>");
                    out.println("<td>" + hora + "</td>");  
                    out.println("<td>" + lista.get(i).getDescricao() + "</td>");
                    
                    if(lista.get(i).getRealizada() == 'S'){
                        out.println("<td style=\"color: green;\">Sim</td>");
                    }
                    else{
                        out.println("<td style=\"color: red;\">Não</td>");
                    }
                       
                    if(lista_exames.get(i) == null){
                        out.println("<td>Não há exames</td>");
                    }
                    else{
                        out.println("<td><select>");
                        
                        ArrayList<String> temp = new ArrayList<String>();
                        temp = lista_exames.get(i);
                        
                        for(int e=0;e<temp.size();e++){
                            out.println("<option>" + temp.get(e) + "</option>");
                        }
                        out.println("</select></td>");
                    }
                }
                %>
            </table>
            <br><br>    
            <div style="text-align:center">
            <a href="./ControladorAdministrador?arg=Visualizar&type=Medico"><button type="button"><b>Voltar</b></button></a>
            </div>
        </div>
    </body>
</html>
