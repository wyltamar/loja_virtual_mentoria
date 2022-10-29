package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class OptionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String insurance_value;
	private String receipt;
	private String own_hand;
	private String reverse;
	private String non_commercial;
	private InvoiceDTO invoice;
	public String getInsurance_value() {
		return insurance_value;
	}
	public void setInsurance_value(String insurance_value) {
		this.insurance_value = insurance_value;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getOwn_hand() {
		return own_hand;
	}
	public void setOwn_hand(String own_hand) {
		this.own_hand = own_hand;
	}
	public String getReverse() {
		return reverse;
	}
	public void setReverse(String reverse) {
		this.reverse = reverse;
	}
	public String getNon_commercial() {
		return non_commercial;
	}
	public void setNon_commercial(String non_commercial) {
		this.non_commercial = non_commercial;
	}
	public InvoiceDTO getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceDTO invoice) {
		this.invoice = invoice;
	}
		

}
