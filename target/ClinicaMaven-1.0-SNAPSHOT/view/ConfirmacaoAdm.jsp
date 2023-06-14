<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aviso</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/BotaoAreaInterna.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script>
        
        <div style="border: 15px solid #008CBA;border-radius: 12px; padding: 30px;
            margin: 150px; text-align: center;">
            
            <%  
            String arg = request.getParameter("arg");
            String tipo = request.getParameter("type");
            
            if(arg.equals("Editar")){
                out.println("<h3>" + tipo + " editado(a) com sucesso!</h3>"); 
                out.println("<a href=\"./ControladorAdministrador?arg=Visualizar&type=" + tipo + "\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(arg.equals("Cadastrado")){
                out.println("<h3>" + tipo + " cadastrado(a) com sucesso!</h3>"); 
                out.println("<a href=\"./ControladorAdministrador?arg=Visualizar&type=" + tipo + "\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(arg.equals("Excluido")){
                out.println("<h3>" + tipo + " excluido(a) com sucesso!</h3>");
                out.println("<a href=\"./ControladorAdministrador?arg=Visualizar&type=" + tipo + "\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(arg.equals("SemConsulta")){
                out.println("<h3>Não há consultas para esse Médico!</h3>");
                out.println("<a href=\"./ControladorAdministrador?arg=Visualizar&type=Medico\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(arg.equals("Vazio")){
                out.println("<h3>Não há nenhum(a) " + tipo + " na Clínica!</h3><br>");
                out.println("<form action=\"./ControladorAdministrador\" method=\"GET\" style=\"text-align: center;\">" +
            "<button class=\"button button2\" type=\"submit\">Cadastrar um " + tipo + "</button>" +
            "<input type=\"hidden\" name=\"arg\" value=\"Cadastrar\">" +
            "<input type=\"hidden\" name=\"type\" value=\"" + tipo + "\"></form><br>");
                out.println("<a href=\"./ControladorAdministrador?type=Dashboard\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            
            if(arg.equals("SemRequisito")){
                out.println("<h3>Erro. Não é possível criar um " + tipo + " na Clínica WeB no momento!</h3>");
                out.println("<a href=\"./ControladorAdministrador?&type=Dashboard\"><button type=\"button\"><b>Voltar</b></button></a>");
            }
            %>
        </div>    
    </body>
</html>
