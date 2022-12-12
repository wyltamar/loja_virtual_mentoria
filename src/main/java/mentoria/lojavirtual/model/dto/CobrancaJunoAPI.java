package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class CobrancaJunoAPI implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Charge charge = new Charge();
	
	private Billing belling = new Billing();

	public Charge getCharge() {
		return charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
	}

	public Billing getBelling() {
		return belling;
	}

	public void setBelling(Billing belling) {
		this.belling = belling;
	}
	
	
	
	

}
