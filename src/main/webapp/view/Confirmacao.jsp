<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aviso</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        <div style="border: 15px solid #008CBA;border-radius: 12px; padding: 30px;
            margin: 150px; text-align: center;">
            <%      
            String tipo = request.getParameter("type");
            
            if(tipo.equals("SemConsulta")){
                out.println("<h3>Você não tem consultas!</h3>"); 
                if(request.getParameter("role").equals("Med")){
                    out.println("<a href=\"./ControladorMedico?arg=Dashboard\"><button type=\"button\"><b>Voltar</b></button></a>");
                }else{
                    out.println("<a href=\"./ControladorCliente?arg=Dashboard\"><button type=\"button\"><b>Voltar</b></button></a>");
                }
            }

            if(tipo.equals("Marcado")){
                out.println("<h3>Consulta marcada!</h3>"); 
                out.println("<a href=\"./ControladorCliente?arg=Dashboard\"><button type=\"button\"><b>Voltar</b></button></a>");
            }

            if(tipo.equals("Excluido")){
                out.println("<h3>Consulta excluída!</h3>");
                out.println("<a href=\"./ControladorCliente?arg=Visualizar\"><button type=\"button\"><b>Voltar</b></button></a>");    
            }

            if(tipo.equals("Cadastrado")){
                out.println("<h3>Cadastrado com sucesso na Clínica WeB!</h3>");
                out.println("<a href=\"./index.jsp\"><button type=\"button\"><b>Voltar</b></button></a>");
            }

            if(tipo.equals("Editado")){
                out.println("<h3>Consulta remarcada com sucesso!</h3>");
                out.println("<a href=\"./ControladorCliente?arg=Visualizar\"><button type=\"button\"><b>Voltar</b></button></a>");
            }

            if(tipo.equals("Exame")){
                out.println("<h3>Exame solicitado com sucesso!</h3>");
                out.println("<a href=\"./ControladorMedico?arg=Visualizar\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(tipo.equals("Concluido")){
                out.println("<h3>Consulta Concluída!</h3>");
                out.println("<a href=\"./ControladorMedico?arg=Visualizar\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(tipo.equals("Lotado")){
                out.println("<h3>Não é possível marcar consultas para este Médico neste dia!</h3>");
                out.println("<a href=\"./ControladorCliente?arg=Visualizar\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(tipo.equals("JaCadastrado")){
                out.println("<h3>Não foi possível cadastrar! Este CPF já está registrado na Clínica WeB!</h3>");
                out.println("<a href=\"./Cadastrar?arg=Cadastrar\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(tipo.equals("SemPlano")){
                out.println("<h3>Desculpe, mas não é possível se cadastrar na Clínica WeB no momento!</h3>");
                out.println("<a href=\"./index.jsp\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(tipo.equals("SemEspMed")){
                out.println("<h3>Desculpe, mas não é possível marcar uma consulta no momento!</h3>");
                out.println("<a href=\"./ControladorCliente?arg=Dashboard\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            %>
        </div>    
    </body>
</html>
