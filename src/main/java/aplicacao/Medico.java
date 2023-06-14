package aplicacao;
public class Medico {
    
    private Integer id;
    private String nome;
    private Integer crm;
    private String estadocrm;
    private String cpf;
    private String senha;
    private char autorizado;
    private Integer idespecialidade;

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

    public Integer getCrm() {
        return crm;
    }

    public void setCrm(Integer crm) {
        this.crm = crm;
    }

    public String getEstadocrm() {
        return estadocrm;
    }

    public void setEstadocrm(String estadocrm) {
        this.estadocrm = estadocrm;
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

    public Integer getIdespecialidade() {
        return idespecialidade;
    }

    public void setIdespecialidade(Integer idespecialidade) {
        this.idespecialidade = idespecialidade;
    }
}
