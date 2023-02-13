package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class DataNotificacaoApiJunoPag implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String entityId;
	private String entityType;
	private AttributesApiJunoPagamento attributes = new AttributesApiJunoPagamento();
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public AttributesApiJunoPagamento getAttributes() {
		return attributes;
	}
	public void setAttributes(AttributesApiJunoPagamento attributes) {
		this.attributes = attributes;
	}
	
	
	
	
	

}
