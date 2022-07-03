package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

import mentoria.lojavirtual.model.Produto;

public class ItemVendaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Double quantidade;
	
	private Produto produto;

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	

}
