package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

public class RelatorioCompraCanceladaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codigoProduto;
	
	@NotEmpty(message = "O nome do produto deve ser informado")
	private String nomeProduto;
	
	private String emailCliente;
	
	private String foneCliente;
	
	private BigDecimal valorVendaProduto;
	
	private Long codigoCliente;
	
	private String nomeCliente;
	
	private int quantidadeEstoque;
	
	private Long codigoVenda;
	
	@NotEmpty(message = "O status da compra deve ser informado")
	private String status;

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getFoneCliente() {
		return foneCliente;
	}

	public void setFoneCliente(String foneCliente) {
		this.foneCliente = foneCliente;
	}

	public BigDecimal getValorVendaProduto() {
		return valorVendaProduto;
	}

	public void setValorVendaProduto(BigDecimal valorVendaProduto) {
		this.valorVendaProduto = valorVendaProduto;
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public int getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(int quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Long getCodigoVenda() {
		return codigoVenda;
	}

	public void setCodigoVenda(Long codigoVenda) {
		this.codigoVenda = codigoVenda;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
