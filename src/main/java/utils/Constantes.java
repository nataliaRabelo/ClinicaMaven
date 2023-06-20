package utils;

public class Constantes {
    
   private Constantes(){
    throw new IllegalStateException("Utility class");
   }
   
    public static final String ID = "id";
    public static final String CPF = "cpf";
    public static final String NOME = "nome";
    public static final String SENHA = "senha";
    public static final String AUTORIZADO = "autorizado";
    public static final String IDTIPOPLANO = "idtipoplano";
    public static final String TYPE = "type";
    public static final String PACIENTE = "paciente";
    public static final String PLANO = "plano";
    public static final String PLANOS = "planos";
    public static final String MEDICO = "Medico";
    public static final String ESPECIALIDADE = "especialidade";
    public static final String ESPECIALIDADE2 = "Especialidade";
    public static final String ESPECIALIDADES = "especialidades";
    public static final String IDENTIDADE = "identidade";
    public static final String ACTION = "action";
    public static final String ARG = "arg";
    public static final String CRM = "crm";
    public static final String ESTADOCRM = "estadocrm";
    public static final String SQLERROR = "SQL Error: ";
    public static final String CLIENTE = "cliente";
    public static final String LISTA_EXAMES = "lista_exames";
    public static final String ID_CONSULTA = "id_consulta";
    public static final String IDMEDICO = "idmedico";
    public static final String IDPACIENTE = "idpaciente";
    public static final String DESCRICAO = "descricao";
    public static final String DATA = "data";
    public static final String REALIZADA = "realizada";
    public static final String VISUALIZAR = "Visualizar";
    public static final String EXCLUIR = "Excluir";
    public static final String EDITAR = "Editar";
    public static final String CADASTRAR = "Cadastrar";
    public static final String VERCONSULTAS = "VerConsultas";
    public static final String ADMINISTRADOR = "Administrador";
    public static final String EXAME = "Exame";
    public static final String DASHBOARD = "Dashboard";
    public static final String PACIENTE2 = "Paciente";
    public static final String MEDICO2 = "Medico";
    public static final String PLANO2 = "Plano";
    public static final String NOMEPLANO = "nomePlano";
    public static final String WHEREESPECIALIDADEID = "WHERE especialidade.id=";
    public static final String FROMESPECIALIDADE = "FROM especialidade INNER JOIN medico ON especialidade.id=medico.idespecialidade ";
    public static final String WHERETIPOPLANO = "WHERE tipoplano.id=";
    public static final String FROMTIPOPLANO = "FROM tipoplano INNER JOIN paciente ON tipoplano.id=paciente.idtipoplano ";
    
    
    
}
