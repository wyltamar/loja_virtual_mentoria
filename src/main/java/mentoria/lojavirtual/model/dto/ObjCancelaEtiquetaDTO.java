package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class ObjCancelaEtiquetaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String reason_id;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReason_id() {
		return reason_id;
	}
	public void setReason_id(String reason_id) {
		this.reason_id = reason_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
