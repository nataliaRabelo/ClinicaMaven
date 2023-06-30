package aplicacao;

/**
A classe Cliente representa um cliente do sistema.
Um cliente possui um identificador, nome, CPF, senha, autorização e identificador de tipo de plano.
*/
public class Cliente {
    
    private Integer id;
    private String nome;
    private String cpf;
    private String senha;
    private char autorizado;
    private Integer idtipoplano;
    
 /**
Obtém o identificador do cliente.
@return O identificador do cliente.
*/  
    public Integer getId() {
        return id;
    }

/**

Define o identificador do cliente.
@param id O identificador a ser definido.
*/
    public void setId(Integer id) {
        this.id = id;
    }

/**
Obtém o nome do cliente.
@return O nome do cliente.
*/
    public String getNome() {
        return nome;
    }
    
/**
Define o nome do cliente.
@param nome O nome a ser definido.
*/
    public void setNome(String nome) {
        this.nome = nome;
    }
    
/**
Obtém o CPF do cliente.
@return O CPF do cliente.
*/
    public String getCpf() {
        return cpf;
    }
    
/**
Define o CPF do cliente.
@param cpf O CPF a ser definido.
*/
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
/**
Obtém a senha do cliente.
@return A senha do cliente.
*/
    public String getSenha() {
        return senha;
    }
    
/**
Define a senha do cliente.
@param senha A senha a ser definida.
*/
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
/**
Obtém a autorização do cliente.
@return A autorização do cliente.
*/
    public char getAutorizado() {
        return autorizado;
    }
    
/**
Define a autorização do cliente.
@param autorizado A autorização a ser definida.
*/
    public void setAutorizado(char autorizado) {
        this.autorizado = autorizado;
    }
    
/**
Obtém o identificador do tipo de plano do cliente.
@return O identificador do tipo de plano do cliente.
*/
    public Integer getIdtipoplano() {
        return idtipoplano;
    }
    
/**
Define o identificador do tipo de plano do cliente.
@param idtipoplano O identificador do tipo de plano a ser definido.
*/
    public void setIdtipoplano(Integer idtipoplano) {
        this.idtipoplano = idtipoplano;
    } 
}
