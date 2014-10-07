package br.com.cgc.vaadin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe de modelo que representa uma maquina.
 * 
 * <p>A <code>maquina</code> é um objeto persistido no banco de dados, por isso a classificamos como <strong>Entidade</strong>.</p>
 * 
 * <p>Por se tratar de uma Entidade persistente, a <code>Maquina</code> utiliza as anotações <code>JPA</code> para definir o mapeamento <code>ORM</code>.
 * 
 * <p>As funcionalidades desse sistema demonstração são concentradas no cadastro (CRUD) de maquinas.</p>
 * 
 * <p>
 *  Outra característica dessa classe, é o uso de anotações do Bean Validations para validar o estado (dados) da <code>Maquina</code>.
 *  Bean Validations (JSR 303) é uma especificação Java para habilitar a validação de dados via o uso de anotações. O principal provider
 *  dessa API é o <code>Hibernate Validator</code>.
 * </p>
 *
 * @author CGC Automação
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "maquina")
public class Maquina implements AbstractEntity {

    /**
     * Chave primária da entidade <code>Maquina</code>. O valor gerado pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull @Size(min=5, max=200)
    private String nome;
    
    private String descricao;
    
    @NotNull @Min(value=1)
    private Double producaominima;
    
    @NotNull @Min(value=1)
    private Double producaomaxima;
    
    /**
     * Atributo utilizado para controle
     * <code>lock</code> (otimista) da
     * <code>JPA</code> para cada registro (objeto) Maquina.
     */
    @Version
    private Integer version;
    
    public Maquina() {
    	this(null, "", "", 0.0, 0.0, 0);
    }

    public Maquina(Integer id, String nome, String descricao, Double producaominima, Double producaomaxima, Integer version) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.producaominima = producaominima;
        this.producaomaxima = producaomaxima;
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

    public Double getProducaomaxima() {
        return producaomaxima;
    }

    public void setProducaomaxima(Double producaomaxima) {
        this.producaomaxima = producaomaxima;
    }

    public Double getProducaominima() {
        return producaominima;
    }

    public void setProducaominima(Double producaominima) {
        this.producaominima = producaominima;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "[ " + nome + " - " + descricao + " - " + producaominima + " - " + producaomaxima + " ]";
    }
}