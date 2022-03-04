package mentoria.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", initialValue = 1,allocationSize = 1)
public class Produto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;
	
	@Column(nullable = false)
	private String tipoUnidade;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;
	
	@Column(columnDefinition = "text", length = 2000, nullable = false)
	private String descricao;
	
	/*FALTA ITEM NOTA PRODUTO - ASSOCIAR */
	
	@Column(nullable = false)
	private Double peso;
	
	@Column(nullable = false)
	private Double largura;
	
	@Column(nullable = false)
	private Double altura;
	
	@Column(nullable = false)
	private Double profundidade;
	
	@Column(nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;
	
	@Column(nullable = false)
	private Integer qtdEstoque = 0;
	
	private Integer qtdAlertaEstoque = 0;
	
	private String linkYoutuble;
	
	private Boolean alertaQtdEstoque = Boolean.FALSE;
	
	private Integer qtdClick = 0;
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = 
	@ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private Pessoa empresa;
	
	public Pessoa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Pessoa empresa) {
		this.empresa = empresa;
	}

	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
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

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public Integer getQtdAlertaEstoque() {
		return qtdAlertaEstoque;
	}

	public void setQtdAlertaEstoque(Integer qtdAlertaEstoque) {
		this.qtdAlertaEstoque = qtdAlertaEstoque;
	}

	public String getLinkYoutuble() {
		return linkYoutuble;
	}

	public void setLinkYoutuble(String linkYoutuble) {
		this.linkYoutuble = linkYoutuble;
	}

	public Boolean getAlertaQtdEstoque() {
		return alertaQtdEstoque;
	}

	public void setAlertaQtdEstoque(Boolean alertaQtdEstoque) {
		this.alertaQtdEstoque = alertaQtdEstoque;
	}

	public Integer getQtdClick() {
		return qtdClick;
	}

	public void setQtdClick(Integer qtdClick) {
		this.qtdClick = qtdClick;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
	


}
