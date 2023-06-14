<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="./styles/Home.css"/>
    <link rel="stylesheet" type="text/css" href="./styles/Barra.css"/>
    <link href="./bootstrap/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    
    <script src="./bootstrap/bootstrap.bundle.min.js"></script>
    
    <nav>
        <ul class="estilobarra">  
            <li class="estilolista"><a href="#Home">Home</a></li>
            <li class="estilolista"><a href="#Espec">Especialidades</a></li>
            <li class="estilolista"><a href="#Conv">Convênios</a></li>  
            <li class="estilologin"><a href="./view/Login.jsp">Log In</a></li>
            <li class="estilologin"><a href="./Cadastrar?arg=Cadastrar">Cadastre-se</a></li>
        </ul>
    </nav>
          
    <br><br>
    
    <div class="parte1" id="Home">
        <div class="caixa">
            <div class="margem">
        
                <h1 class="tituloprincipal">Clínica WeB</h1>

                <p class="subtitulo">Aqui para te salvar!</p>
                
                <p class="paragrafo">
                    Olá, navegante! Seja bem-vindo(a) à Clínica WeB!<br>
                    Você tem tossido muito ultimamente ? Fadiga incistente durante
                    o dia ? Você ouve vozes toda vez que vai dormir mesmo morando
                    sozinho ? Não se preocupe! A Clínica WeB conta com o melhor
                    time de médicos do mercado para te ajudar. Marque já a sua
                    consulta!            
                </p>          
            </div>
        </div>
    </div>
    
    <div class="parte2" id="Espec">
        <div class="caixa">
            <div class="margem">
        
                <h1 class="titulo">Especialidades</h1>
        
                <p class="paragrafo">Na Clínica WeB, além de um exelente atendim
                    ento você conta com as seguintes especialidades:</p>
                    
                    <dl>
                        <dt>Cardiologia</dt>
                            <dd>Especialidade médica que se ocupa do diagnóstico
                                e tratamento das doenças que acometem o coração
                                bem como os outros componentes do sistema
                                circulatório.</dd>
                        <dt>Neurologia</dt>
                            <dd>Especialidade médica que trata dos distúrbios 
                                estruturais do sistema nervoso. Especificamente,
                                ela lida com o diagnóstico e tratamento de todas
                                as categorias de doenças que envolvem os sistemas
                                nervoso central, periférico e autônomo, incluindo
                                os seus revestimentos, vasos sanguíneos, e todos
                                os tecidos efetores, 
                                como os músculos.</dd>
                        <dt>Gastrologia</dt>
                            <dd>A Gastrologia é a especialidade médica que
                                se ocupa do estudo, diagnóstico e tratamento
                                clínico das doenças do aparelho digestivo.</dd>
                        <dt>Pneumologia</dt>
                            <dd>É a especialidade médica a qual é responsável
                                pelo tratamento das patologias das vias aéreas
                                inferiores.</dd>
                    </dl>  
            </div>
        </div>
    </div>
    
    <div class="parte3" id="Conv">
        <div class="caixa">
            <div class="margem">
        
                <h1 class="titulo">Convênios</h1>
        
                <p class="paragrafo">Confira a lista de convênios aceitos pela Clínica WeB:</p>
                
                <div class="caixaconvenio">UNIMED</div>
                <div class="caixaconvenio">AMIL</div>
                <br>
                <br>
                <div class="caixaconvenio">SulAmérica</div>
                <div class="caixaconvenio">Particular</div>
            </div>
        </div>
    </div>
</body>
</html>