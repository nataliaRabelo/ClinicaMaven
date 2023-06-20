package aplicacao;

/**
A classe Consulta representa uma consulta médica do sistema de aplicação.
Cada consulta possui um identificador, data, descrição, indicação de realização, identificador do médico e identificador do paciente.
*/
public class Consulta {

    private Integer id;
    private String data;
    private String descricao;
    private char realizada;
    private Integer idmedico;
    private Integer idpaciente;
    
/**
Obtém o identificador da consulta.
@return O identificador da consulta.
*/
    public Integer getId() {
        return id;
    }
    
/**
Define o identificador da consulta.
@param id O identificador a ser definido.
*/
    public void setId(Integer id) {
        this.id = id;
    }
    
/**
Obtém a data da consulta.
@return A data da consulta.
*/
    public String getData() {
        return data;
    }
    
/**
Define a data da consulta.
@param data A data a ser definida.
*/
    public void setData(String data) {
        this.data = data;
    }
    
/**
Obtém a descrição da consulta.
@return A descrição da consulta.
*/
    public String getDescricao() {
        return descricao;
    }
    
/**
Define a descrição da consulta.
@param descricao A descrição a ser definida.
*/
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
/**
Verifica se a consulta foi realizada.
@return true se a consulta foi realizada, false caso contrário.
*/
    public char getRealizada() {
        return realizada;
    }
    
/**
Define se a consulta foi realizada.
@param realizada O indicador de realização a ser definido.
*/
    public void setRealizada(char realizada) {
        this.realizada = realizada;
    }
    
/**
Obtém o identificador do médico responsável pela consulta.
@return O identificador do médico.
*/
    public Integer getIdmedico() {
        return idmedico;
    }
    
/**
Define o identificador do médico responsável pela consulta.
@param idmedico O identificador do médico a ser definido.
*/
    public void setIdmedico(Integer idmedico) {
        this.idmedico = idmedico;
    }
    
/**
Obtém o identificador do paciente que realizou a consulta.
@return O identificador do paciente.
*/
    public Integer getIdpaciente() {
        return idpaciente;
    }
    
/**
Define o identificador do paciente que realizou a consulta.
@param idpaciente O identificador do paciente a ser definido.
*/
    public void setIdpaciente(Integer idpaciente) {
        this.idpaciente = idpaciente;
    }
}
