package aplicacao;
public class Cliente {
    
    private Integer id;
    private String nome;
    private String cpf;
    private String senha;
    private char autorizado;
    private Integer idtipoplano;
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public char getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(char autorizado) {
        this.autorizado = autorizado;
    }

    public Integer getIdtipoplano() {
        return idtipoplano;
    }

    public void setIdtipoplano(Integer idtipoplano) {
        this.idtipoplano = idtipoplano;
    } 
}
