package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class RelatorioCompraCanceladaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Informe a data inicial")
	private String dataInicial;
	
	@NotEmpty(message = "Informe a data final")
	private String dataFinal;
	
	private String codigoProduto = "";
	private String nomeProduto = "";
	private String valorVendaProduto = "";
	private String emailCliente = "";
	private String foneCliente = "";
	private String codigoCliente = "";
	private String nomeCliente = "";
	private String quantidadeEstoque = "";
	private String codigoVenda = "";
	private String status = "";
	
	public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getCodigoProduto() {
		return codigoProduto;
	}
	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getValorVendaProduto() {
		return valorVendaProduto;
	}
	public void setValorVendaProduto(String valorVendaProduto) {
		this.valorVendaProduto = valorVendaProduto;
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
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(String quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public String getCodigoVenda() {
		return codigoVenda;
	}
	public void setCodigoVenda(String codigoVenda) {
		this.codigoVenda = codigoVenda;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
