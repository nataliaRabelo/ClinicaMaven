package aplicacao;


/**
A classe Administrador representa um administrador do sistema.
Um administrador possui um identificador, nome, CPF e senha.
*/
public class Administrador {
    
    private Integer id;
    private String nome;
    private String cpf;
    private String senha;
    
    /**
    Obtém o identificador do administrador.
    @return O identificador do administrador.
    */
    public Integer getId() {
        return id;
    }
    
    /**
    Define o identificador do administrador.
    @param id O identificador a ser definido.
    */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
    Obtém o nome do administrador.
    @return O nome do administrador.
    */
    public String getNome() {
        return nome;
    }
    
    /**
    Define o nome do administrador.
    @param nome O nome a ser definido.
    */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
    Obtém o CPF do administrador.
    @return O CPF do administrador.
    */
    public String getCpf() {
        return cpf;
    }
    
    /**
    Define o CPF do administrador.
    @param cpf O CPF a ser definido.
    */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    /**
    Obtém a senha do administrador.
    @return A senha do administrador.
    */
    public String getSenha() {
        return senha;
    }
    
    /**
    Define a senha do administrador.
    @param senha A senha a ser definida.
    */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
