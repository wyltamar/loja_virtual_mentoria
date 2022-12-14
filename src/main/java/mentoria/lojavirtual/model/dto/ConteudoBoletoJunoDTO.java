package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConteudoBoletoJunoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String reference;
	private String dueDate;
	private String link;
	private String checkoutUrl;
	private String installmentLink;
	private String payNumber;
	private String amount;
	private String status = "ACTIVE";
	
	private BilletDetailsDTO billetDetails = new BilletDetailsDTO();
	
	private List<PaymentsDTO> payments = new ArrayList<PaymentsDTO>();
	
	private PixDTO pix = new PixDTO();
	
	private List<LinksDTO> _links = new ArrayList<LinksDTO>();

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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCheckoutUrl() {
		return checkoutUrl;
	}

	public void setCheckoutUrl(String checkoutUrl) {
		this.checkoutUrl = checkoutUrl;
	}

	public String getInstallmentLink() {
		return installmentLink;
	}

	public void setInstallmentLink(String installmentLink) {
		this.installmentLink = installmentLink;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
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

	public BilletDetailsDTO getBilletDetails() {
		return billetDetails;
	}

	public void setBilletDetails(BilletDetailsDTO billetDetails) {
		this.billetDetails = billetDetails;
	}

	public List<PaymentsDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentsDTO> payments) {
		this.payments = payments;
	}

	public PixDTO getPix() {
		return pix;
	}

	public void setPix(PixDTO pix) {
		this.pix = pix;
	}

	public List<LinksDTO> get_links() {
		return _links;
	}

	public void set_links(List<LinksDTO> _links) {
		this._links = _links;
	}
	
	
	
	

}
