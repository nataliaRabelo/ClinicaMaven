<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aplicacao.Especialidade"%>
<%@page import="aplicacao.Medico"%>
<%
    Medico medico = (Medico)session.getAttribute("medico");
    Especialidade especialidade = (Especialidade)session.getAttribute("especialidade");
    ArrayList<Especialidade> especialidades = (ArrayList<Especialidade>)session.getAttribute("especialidades");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Médico</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form action="ControladorAdministrador" method="POST" class="formulario">
                <h3>Edite o Médico</h3>
                <br><br>
                <%
                out.println("<h5>Nome do Médico: " + medico.getNome() + "</h5>");
                %>
                <label for="nome">Nome:</label>
                <input type="text" name="nome" value="<%=medico.getNome()%>" required>
                <br><br>
                <%
                out.println("<h5>CRM do Médico: " + medico.getCrm() + "</h5>");
                %>
                <label for="crm">CRM:</label>
                <input type="text" name="crm" value="<%=medico.getCrm()%>" required>
                <br><br>
                <%
                out.println("<h5>Estado do CRM: " + medico.getEstadocrm() + "</h5>");
                %>
                <label for="estado">Estado do CRM:</label>
                <select name="estadocrm">
                    <option value="AC">Acre</option>
                    <option value="AL">Alagoas</option>
                    <option value="AP">Amapá</option>
                    <option value="AM">Amazonas</option>
                    <option value="BA">Bahia</option>
                    <option value="CE">Ceará</option>
                    <option value="DF">Distrito Federal</option>
                    <option value="ES">Espírito Santo</option>
                    <option value="GO">Goiás</option>
                    <option value="MA">Maranhão</option>
                    <option value="MT">Mato Grosso</option>
                    <option value="MS">Mato Grosso do Sul</option>
                    <option value="MG">Minas Gerais</option>
                    <option value="PA">Pará</option>
                    <option value="PB">Paraíba</option>
                    <option value="PR">Paraná</option>
                    <option value="PE">Pernambuco</option>
                    <option value="PI">Piauí</option>
                    <option value="RJ">Rio de Janeiro</option>
                    <option value="RN">Rio Grande do Norte</option>
                    <option value="RS">Rio Grande do Sul</option>
                    <option value="RO">Rondônia</option>
                    <option value="RR">Roraima</option>
                    <option value="SC">Santa Catarina</option>
                    <option value="SP">São Paulo</option>
                    <option value="SE">Sergipe</option>
                    <option value="TO">Tocantins</option>
                    <option value="EX">Estrangeiro</option>
                </select>
                <br><br>
                <%
                out.println("<h5>CPF do Médico: " + medico.getCpf() + "</h5>");
                %>
                <label for="CPF">CPF:</label>
                <input type="text" name="CPF" value="<%=medico.getCpf()%>" required>
                <br><br>
                <%
                out.println("<h5>Senha do Médico: " + medico.getSenha() + "</h5>");
                %>
                <label for="senha">Senha:</label>
                <input type="text" name="senha" value="<%=medico.getSenha()%>" required> 
                <br><br>
                <%
                if(medico.getAutorizado() == 'S'){
                    out.println("<h5>Autorização: Sim</h5>");
                }else{
                    out.println("<h5>Autorização: Não</h5>");
                }
                %>
                <label for="autorizado">Autoriazado ?</label>
                <select name="autorizado">
                    <option value="S">Sim</option>
                    <option value="N">Não</option>
                </select>
                <br><br>
                <%
                out.println("<h5>Especialidade do Médico: " + especialidade.getDescricao()+ "</h5>");
                %>
                <label for="espec">Especialidade:</label>
                <select name="especialidade">
                    <%
                    for(int i=0;i<especialidades.size();i++){
                        out.println("<option value=" + especialidades.get(i).getId() + ">" + especialidades.get(i).getDescricao() + "</option>");
                    }
                    %>
                </select><br><br>
                <input type="submit" name="action" value="Editar">
                <input type="hidden" name="id" value="<%=medico.getId()%>">
                <input type="hidden" name="type" value="Medico">
            </form>
            <a href="./ControladorAdministrador?arg=Visualizar&type=Medico"><button type="button"><b>Voltar</b></button></a>    
        </div>
    </body>
</html>
