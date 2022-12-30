package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class ObjetoPostCarneJuno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*Descrição da cobrança*/
	private String description;
	
	/*Nome do cliente*/
	private String payerName;
	
	/*Telefone do cliente*/
	private String payerPhone;
	
	/*Valor da compra ou parcela*/
	private String totalAmount;
	
	/*Quantidade de parcelas*/
	private String installments;
	
	/*Referência para o produto da loja ou código do produto*/
	private String reference;
	
	private String Email;
	
	private String payerCpfCnpj;
	
	private Long idVenda;
	
	public void setEmail(String email) {
		Email = email;
	}

	public void setIdVenda(Long idVenda) {
		this.idVenda = idVenda;
	}
	
	public Long getIdVenda() {
		return idVenda;
	}
	
	public String getPayerCpfCnpj() {
		return payerCpfCnpj;
	}
	
	public void setPayerCpfCnpj(String cpfCnpj) {
		this.payerCpfCnpj = cpfCnpj;
	}
	
	public void Email(String payerEmail) {
		this.Email = payerEmail;
	}

	public String getEmail() {
		return Email;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerPhone() {
		return payerPhone;
	}

	public void setPayerPhone(String payerPhone) {
		this.payerPhone = payerPhone;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInstallments() {
		return installments;
	}

	public void setInstallments(String installments) {
		this.installments = installments;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	

}
