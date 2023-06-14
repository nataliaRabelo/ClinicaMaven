<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aplicacao.Especialidade"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Especialidade> especialidades = (ArrayList<Especialidade>)session.getAttribute("especialidades");  
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastrar Médico</title>
        <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./styles/Formulario.css"/>
    </head>
    <body>
        <script src="./bootstrap/bootstrap.bundle.min.js"></script> 
        <jsp:include page = "./BarraInterna.jsp" />
        
        <br><br><br>
        
        <div class="quadro" style="width: 700px;">
            <form action="ControladorAdministrador" method="POST" class="formulario">
                <h3>Cadastre um novo Médico</h3>
                <br><br>
                <label for="nome">Nome:</label>
                <input type="text" placeholder="Entre com o nome do Médico" name="nome" required>
                <br><br>
                <label for="crm">CRM:</label>
                <input type="text" placeholder="Entre com o CRM do Médico" name="crm" required>
                <br><br>
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
                <label for="CPF">CPF:</label>
                <input type="text" placeholder="Entre com o CPF do Médico" name="CPF" required>
                <br><br>
                <label for="senha">Senha:</label>
                <input type="password" placeholder="Entre com a senha do Médico" name="senha" required> 
                <br><br>
                <label for="autorizado">Autoriazado ?</label>
                <select name="autorizado">
                    <option value="S">Sim</option>
                    <option value="N">Não</option>
                </select>
                <br><br>
                <label for="espec">Especialidade:</label>
                <select name="especialidade">
                    <%
                    for(int i=0;i<especialidades.size();i++){
                        out.println("<option value=" + especialidades.get(i).getId() + ">" + especialidades.get(i).getDescricao() + "</option>");
                    }
                    %>
                </select><br><br>
                <input type="submit" name="action" value="Cadastrar">
                <input type="hidden" name="type" value="Medico">
            </form>
            <a href="./ControladorAdministrador?arg=Visualizar&type=Medico"><button type="button"><b>Voltar</b></button></a>    
        </div>
    </body>
</html>
