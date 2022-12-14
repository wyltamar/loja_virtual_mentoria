package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoletoGeradoApiJunoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EmbeddedDTO _embedded = new EmbeddedDTO();
	
	private List<LinksDTO> _links = new ArrayList<LinksDTO>();
	
	public EmbeddedDTO get_embedded() {
		return _embedded;
	}
	
	public void set_embedded(EmbeddedDTO _embedded) {
		this._embedded = _embedded;
	}
	
	public List<LinksDTO> get_links() {
		return _links;
	}
	
	public void set_links(List<LinksDTO> _links) {
		this._links = _links;
	}

}
