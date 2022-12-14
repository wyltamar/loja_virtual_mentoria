package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class LinksDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private SelfDTO self = new SelfDTO();
	
	public SelfDTO getSelf() {
		return self;
	}
	
	public void setSelf(SelfDTO self) {
		this.self = self;
	}

}
