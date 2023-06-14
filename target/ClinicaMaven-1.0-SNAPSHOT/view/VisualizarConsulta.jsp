<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Consulta"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Consulta> lista_consultas = (ArrayList<Consulta>)session.getAttribute("lista_consultas");
   
    ArrayList<Object> lista_med_esp = (ArrayList<Object>)session.getAttribute("lista_med_esp");

    ArrayList<ArrayList<String>> lista_exames = (ArrayList<ArrayList<String>>)session.getAttribute("lista_exames");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Suas Consultas</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Visualizar.css"/>
    </head>
    <body>
        <%@include file="BarraInterna.jsp" %>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        
        <br><br><br><br>
        
        <h2 style="text-align:center">Veja aqui as suas Consultas!</h2>
        <div class="div">
            <table>
                <tr>
                    <th>Consulta</th>
                    <th>Data</th>
                    <th>Hora</th>
                    <th>Médico</th>
                    <th>Especialidade</th>
                    <th>Exames solicitados</th>
                    <th></th>
                    <th></th>
                   
                </tr>
                <%
                int x=0, y=1;
                for(int i=0;i<lista_consultas.size();i++){
                    String infos[] = lista_consultas.get(i).getData().split(" ");
                    
                    String dia = infos[0].substring(8,10);
                    String mes = infos[0].substring(5,7);
                    String ano = infos[0].substring(0,4);

                    String data = dia + "/" + mes + "/" + ano;
                    String hora = infos[1];
                    hora = hora.substring(0, hora.length() - 5);
                    out.println("<tr>");
                    out.println("<td>Consulta " + (i+1) + "</td>");
                    out.println("<td>" + data + "</td>");
                    out.println("<td>" + hora + "</td>");  
                    out.println("<td>" + lista_med_esp.get(x) + "</td>");
                    out.println("<td>" + lista_med_esp.get(y) + "</td>");
                    
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
                    
                    if(Character.compare(lista_consultas.get(i).getRealizada(),'S') == 0){
                        
                        out.println("<td style=\"color: green;\">Consulta realizada</td>");
                        out.println("<td></td>");
                    }
                    else{
                        
                        out.println("<td>"
                                + "<a href=\"./ControladorCliente?arg=Editar&id=" + lista_consultas.get(i).getId()+ "\">"
                                + "<button type=\"button\" style=\"border-radius: 4px;\">Editar</button></a>"
                                + "<input type=\"hidden\" name=\"id_consulta\" value= "+ lista_consultas.get(i).getId() +">"
                                + "</td>");
                        
                        out.println("<td>"
                                + "<a href=\"./ControladorCliente?arg=Excluir&id=" + lista_consultas.get(i).getId()+ "\">"
                                + "<button type=\"button\" style=\"border-radius: 4px;\">Excluir</button></a>"
                                + "<input type=\"hidden\" name=\"id_consulta\" value= "+ lista_consultas.get(i).getId() +">"
                                + "</td>");
                    }
                    out.println("</tr>");
                    
                    x+=2;
                    y+=2;
                }
                %>
            </table>
            <br><br>    
            <div style="text-align:center">
            <a href="./ControladorCliente?arg=Dashboard"><button type="button"><b>Voltar</b></button></a>
            </div>
        </div>
    </body>
</html>
