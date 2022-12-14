package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmbeddedDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<ConteudoBoletoJunoDTO> charges = new ArrayList<ConteudoBoletoJunoDTO>();
	
	public List<ConteudoBoletoJunoDTO> getCharges() {
		return charges;
	}
	
	public void setCharges(List<ConteudoBoletoJunoDTO> charges) {
		this.charges = charges;
	}

}
