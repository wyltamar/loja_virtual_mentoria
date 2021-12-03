package mentoria.lojavirtual.enums;

public enum StatusContaReceber {
	
	COBRANCA ("Pagar"),
	VENCIDA ("Vencida"),
	ABERTA("Aberta"),
	QUITADA ("Quit ada");
	
	private String descricao;
	
	private StatusContaReceber(String descricao) {
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
