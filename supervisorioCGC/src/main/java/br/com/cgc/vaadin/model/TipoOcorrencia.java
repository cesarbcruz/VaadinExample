package br.com.cgc.vaadin.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "tipoocorrencia")
public class TipoOcorrencia implements AbstractEntity {

    /**
     * Chave primária da entidade
     * <code>TipoOcorrencia</code>. O valor gerado pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Size(min = 5, max = 200)
    private String nome;
    private String descricao;
    /**
     * Atributo utilizado para controle
     * <code>lock</code> (otimista) da
     * <code>JPA</code> para cada registro (objeto) Maquina.
     */
    @Version
    private Integer version;
    
    @Transient
    private String caption;

    public String getCaption() {
        caption = id + "-" + nome;
        return caption;
    }

    public TipoOcorrencia() {
        this(null, "", "", 0);
    }

    public TipoOcorrencia(Integer id, String nome, String descricao, Integer version) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
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

    public Integer getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}