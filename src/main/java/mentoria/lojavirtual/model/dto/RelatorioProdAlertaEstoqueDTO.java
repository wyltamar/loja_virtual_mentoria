package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class RelatorioProdAlertaEstoqueDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeProduto = "";
	
	@NotEmpty(message = "Por favor informe a data inicial")
	private String dataInicial;
	
	@NotEmpty(message = "Por favor informe a data final")
	private String dataFinal;
	
	private String codigoNotaFiscalCompra = "";
	
	private String codigoProduto = "";
	
	private String valorVendaProduto = "";
	
	private String quantidadeComprada = "";
	
	private String codFornecedor = "";
	
	private String nomeFornecedor = "";
	
	private String dataCompra;
	
	private int qtdEstoque;
	
	private int qtdAlertaEstoque;
	
	
	public int getQtdEstoque() {
		return qtdEstoque;
	}
	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}
	public int getQtdAlertaEstoque() {
		return qtdAlertaEstoque;
	}
	public void setQtdAlertaEstoque(int qtdAlertaEstoque) {
		this.qtdAlertaEstoque = qtdAlertaEstoque;
	}
	public String getValorVendaProduto() {
		return valorVendaProduto;
	}
	public void setValorVendaProduto(String valorVendaProduto) {
		this.valorVendaProduto = valorVendaProduto;
	}
	public String getQuantidadeComprada() {
		return quantidadeComprada;
	}
	public void setQuantidadeComprada(String quantidadeComprada) {
		this.quantidadeComprada = quantidadeComprada;
	}
	public String getCodFornecedor() {
		return codFornecedor;
	}
	public void setCodFornecedor(String codFornecedor) {
		this.codFornecedor = codFornecedor;
	}
	public String getNomeFornecedor() {
		return nomeFornecedor;
	}
	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}
	public String getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
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
	public String getCodigoNotaFiscalCompra() {
		return codigoNotaFiscalCompra;
	}
	public void setCodigoNotaFiscalCompra(String codigoNotaFiscalCompra) {
		this.codigoNotaFiscalCompra = codigoNotaFiscalCompra;
	}
	public String getCodigoProduto() {
		return codigoProduto;
	}
	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	
	
}
