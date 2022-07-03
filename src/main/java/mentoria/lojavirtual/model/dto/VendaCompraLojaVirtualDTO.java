package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mentoria.lojavirtual.model.Endereco;
import mentoria.lojavirtual.model.Pessoa;

public class VendaCompraLojaVirtualDTO implements Serializable {
	

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private BigDecimal valorTotal;
	
	private Pessoa pessoa;
	
	private Endereco entrega;
	
	private Endereco cobranca;
	
	private BigDecimal valorDesconto;
	
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	private BigDecimal valorFrete;
	
	private List<ItemVendaDTO> itemVendas  = new ArrayList<ItemVendaDTO>();
	
	
	public List<ItemVendaDTO> getItemVendas() {
		return itemVendas;
	}

	public void setItemVendas(List<ItemVendaDTO> itemVendas) {
		this.itemVendas = itemVendas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}
	
	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}
	
		
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEntrega() {
		return entrega;
	}

	public void setEntrega(Endereco entrega) {
		this.entrega = entrega;
	}

	public Endereco getCobranca() {
		return cobranca;
	}

	public void setCobranca(Endereco cobranca) {
		this.cobranca = cobranca;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}
