package br.com.cgc.vaadin.model;

import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "turno")
public class Turno implements AbstractEntity {

    /**
     * Chave primária da entidade <code>Turno</code>. O valor gerado pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull @Size(min=5, max=200)
    private String nome;
    
    private String descricao;
    
    @NotNull 
    private Timestamp entrada;
    
    @NotNull 
    private Timestamp saida;
    
    /**
     * Atributo utilizado para controle
     * <code>lock</code> (otimista) da
     * <code>JPA</code> para cada registro (objeto) Maquina.
     */
    @Version
    private Integer version;
    
    public Turno() {
    	this(null, "", "", null, null, 0);
    }

    public Turno(Integer id, String nome, String descricao, Timestamp entrada, Timestamp saida, Integer version) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.entrada = entrada;
        this.saida = saida;
        this.version = version;
    }

    @Override
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getEntrada() {
        return entrada;
    }

    public void setEntrada(Timestamp entrada) {
        this.entrada = entrada;
    }

    public Timestamp getSaida() {
        return saida;
    }

    public void setSaida(Timestamp saida) {
        this.saida = saida;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "[ " + nome + " - " + descricao + " - " + entrada + " - " + saida + " ]";
    }
}