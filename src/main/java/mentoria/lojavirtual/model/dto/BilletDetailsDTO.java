package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class BilletDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String bankAccount;
	
	private String ourNuber;
	
	private String barcodeNumber;
	
	private String portfolio;
	
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getOurNuber() {
		return ourNuber;
	}
	public void setOurNuber(String ourNuber) {
		this.ourNuber = ourNuber;
	}
	public String getBarcodeNumber() {
		return barcodeNumber;
	}
	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	public String getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}
	
	

}
