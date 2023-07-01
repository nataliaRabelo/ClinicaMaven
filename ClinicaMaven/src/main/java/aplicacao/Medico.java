package aplicacao;

/**
A classe Medico representa um médico do sistema de aplicação.
Cada médico possui um identificador, nome, CPF, especialidade e CRM.
*/
public class Medico {
    
    private Integer id;
    private String nome;
    private Integer crm;
    private String estadocrm;
    private String cpf;
    private String senha;
    private char autorizado;
    private Integer idespecialidade;
/**

Obtém o identificador do médico.
@return O identificador do médico.
*/
    public Integer getId() {
        return id;
    }
/**

Define o identificador do médico.
@param id O identificador a ser definido.
*/
    public void setId(Integer id) {
        this.id = id;
    }
/**

Obtém o nome do médico.
@return O nome do médico.
*/
    public String getNome() {
        return nome;
    }
/**

Define o nome do médico.
@param nome O nome a ser definido.
*/
    public void setNome(String nome) {
        this.nome = nome;
    }
/**

Obtém o CRM do médico.
@return O CRM do médico.
*/
    public Integer getCrm() {
        return crm;
    }
/**

Define o CRM do médico.
@param crm O CRM a ser definido.
*/
    public void setCrm(Integer crm) {
        this.crm = crm;
    }

/**
Obtém o estado do CRM do médico.
@return O estado do CRM do médico.
*/
    public String getEstadocrm() {
        return estadocrm;
    }
    
/**
Define o estado do CRM do médico.
@param estadocrm O estado do CRM a ser definido.
*/
    public void setEstadocrm(String estadocrm) {
        this.estadocrm = estadocrm;
    }
/**

Obtém o CPF do médico.
@return O CPF do médico.
*/
    public String getCpf() {
        return cpf;
    }
/**

Define o CPF do médico.
@param cpf O CPF a ser definido.
*/
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
/**

Obtém a senha do médico.
@return A senha do médico.
*/
    public String getSenha() {
        return senha;
    }
/**

Define a senha do médico.
@param senha A senha a ser definida.
*/
    public void setSenha(String senha) {
        this.senha = senha;
    }

/**
Obtém a autorização do médico.
@return A autorização do médico.
*/
    public char getAutorizado() {
        return autorizado;
    }
    
/**
Obtém a autorização do médico.
@param autorizado A autorização do médico.
*/
    public void setAutorizado(char autorizado) {
        this.autorizado = autorizado;
    }

    /**

Obtém a especialidade do médico.
@return A especialidade do médico.
*/
    public Integer getIdespecialidade() {
        return idespecialidade;
    }
/**
Define a especialidade do médico.
@param idespecialidade A especialidade a ser definida.
*/
    public void setIdespecialidade(Integer idespecialidade) {
        this.idespecialidade = idespecialidade;
    }
}
