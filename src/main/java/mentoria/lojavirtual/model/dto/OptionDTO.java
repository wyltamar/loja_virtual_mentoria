package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OptionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal insurance_value;
	private boolean receipt;
	private boolean own_hand;
	private boolean reverse;
	private boolean non_commercial;
	private InvoiceDTO invoice;
	
	public BigDecimal getInsurance_value() {
		return insurance_value;
	}
	public void setInsurance_value(BigDecimal insurance_value) {
		this.insurance_value = insurance_value;
	}
	public boolean getReceipt() {
		return receipt;
	}
	public void setReceipt(boolean receipt) {
		this.receipt = receipt;
	}
	public boolean getOwn_hand() {
		return own_hand;
	}
	public void setOwn_hand(boolean own_hand) {
		this.own_hand = own_hand;
	}
	public boolean getReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	public boolean getNon_commercial() {
		return non_commercial;
	}
	public void setNon_commercial(boolean non_commercial) {
		this.non_commercial = non_commercial;
	}
	public InvoiceDTO getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceDTO invoice) {
		this.invoice = invoice;
	}
		

}
