package br.com.cgc.vaadin.model;

import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "ocorrencia")
public class Ocorrencia implements AbstractEntity {

    /**
     * Chave primária da entidade
     * <code>Ocorrencia</code>. O valor gerado pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Size(min = 5, max = 200)
    private String nome;
    private String descricao;
    @NotNull
    private Timestamp dataHora;
    @ManyToOne
    @JoinColumn(name = "idTipoOcorrencia", referencedColumnName = "id")
    private TipoOcorrencia tipoOcorrencia;
    /**
     * Atributo utilizado para controle
     * <code>lock</code> (otimista) da
     * <code>JPA</code> para cada registro (objeto) Maquina.
     */
    @Version
    private Integer version;

    public Ocorrencia() {
        this(null, "", "", null, 0);
    }

    public Ocorrencia(Integer id, String nome, String descricao, Timestamp dataHora, Integer version) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = dataHora;
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

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getVersion() {
        return version;
    }

    public TipoOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    @Override
    public String toString() {
        return "[ " + nome + " - " + descricao + " - " + dataHora + " ]";
    }
}