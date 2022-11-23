package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class CanceledDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String canceled;

	public String getCanceled() {
		return canceled;
	}

	public void setCanceled(String canceled) {
		this.canceled = canceled;
	}
	
	
}
