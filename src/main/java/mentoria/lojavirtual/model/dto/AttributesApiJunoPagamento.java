package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class AttributesApiJunoPagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String createdOn;
	private String date;
	private String releaseDate;
	private String amount;
	private String fee;
	private String status;
	private String type;
	private ChargeApiJunoPagamento charge;
	private PixApiJuno pix;
	private String digitalAccountId;
	
	public String getDigitalAccountId() {
		return digitalAccountId;
	}
	
	public void setDigitalAccountId(String digitalAccountId) {
		this.digitalAccountId = digitalAccountId;
	}
	
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFree() {
		return fee;
	}
	public void setFree(String fee) {
		this.fee = fee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ChargeApiJunoPagamento getCharge() {
		return charge;
	}
	public void setCharge(ChargeApiJunoPagamento charge) {
		this.charge = charge;
	}
	public PixApiJuno getPix() {
		return pix;
	}
	public void setPix(PixApiJuno pix) {
		this.pix = pix;
	}
	
	
	
	

}
