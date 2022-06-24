package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class ImagemProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String imagemOriginal;
	
	private String imagemMiniatura;
	
	private Long empresa;
	
	private Long produto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImagemOriginal() {
		return imagemOriginal;
	}

	public void setImagemOriginal(String imagemOriginal) {
		this.imagemOriginal = imagemOriginal;
	}

	public String getImagemMiniatura() {
		return imagemMiniatura;
	}

	public void setImagemMiniatura(String imagemMiniatura) {
		this.imagemMiniatura = imagemMiniatura;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	public Long getProduto() {
		return produto;
	}

	public void setProduto(Long produto) {
		this.produto = produto;
	}
	
	

}
