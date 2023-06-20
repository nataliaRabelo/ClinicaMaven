package aplicacao;

/**
A classe Plano representa um plano de saúde do sistema de aplicação.
Cada plano possui um identificador e uma descrição.
*/
public class Plano {
    
    private Integer id;
    private String descricao;
    
/**
Obtém o identificador do plano.
@return O identificador do plano.
*/
    public Integer getId() {
        return id;
    }
    
/**
Define o identificador do plano.
@param id O identificador a ser definido.
*/
    public void setId(Integer id) {
        this.id = id;
    }
    
/**
Obtém a descrição do plano.
@return A descrição do plano.
*/
    public String getDescricao() {
        return descricao;
    }
    
/**
Define a descrição do plano.
@param descricao A descrição a ser definida.
*/
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
