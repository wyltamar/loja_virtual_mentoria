package mentoria.lojavirtual.enums;

public enum StatusContaPagar {
	
	COBRANCA ("Pagar"),
	VENCIDA ("Vencida"),
	ABERTA("Aberta"),
	QUITADA ("Quit ada"),
	NEGOCIADA("Renegociada");
	
	private String descricao;
	
	private StatusContaPagar(String descricao) {
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
