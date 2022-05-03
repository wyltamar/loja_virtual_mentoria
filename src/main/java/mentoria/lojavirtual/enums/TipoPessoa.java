package mentoria.lojavirtual.enums;

public enum TipoPessoa {
	
	FISICA("Física"),
	JURIDICA("Jurídica"),
	JURIDICAFORNECEDOR("Jurídica e Fornecedor");
	
	private String descricao;
	
	private TipoPessoa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		
		return this.descricao;
	}

}
