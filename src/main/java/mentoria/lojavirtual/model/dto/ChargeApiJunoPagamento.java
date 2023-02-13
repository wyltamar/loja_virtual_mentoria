package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class ChargeApiJunoPagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String amount;
	private String status;
	private String dueDate;
	private PayerApiJunoPag payer;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public PayerApiJunoPag getPayer() {
		return payer;
	}
	public void setPayer(PayerApiJunoPag payer) {
		this.payer = payer;
	}
	
	
	
	
	

}
