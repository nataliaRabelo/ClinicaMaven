package aplicacao;

/**
A classe Especialidade representa uma especialidade do sistema de aplicação.
Cada especialidade possui um identificador e uma descrição.
*/
public class Exame {
    
    private Integer id;
    private String descricao;
    
/**
Obtém o identificador da especialidade.
@return O identificador da especialidade.
*/
    public Integer getId() {
        return id;
    }
    
/**
Define o identificador da especialidade.
@param id O identificador a ser definido.
*/
    public void setId(Integer id) {
        this.id = id;
    }
    
/**
Obtém a descrição da especialidade.
@return A descrição da especialidade.
*/
    public String getDescricao() {
        return descricao;
    }
    
/**
Define a descrição da especialidade.
@param descricao A descrição a ser definida.
*/
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
