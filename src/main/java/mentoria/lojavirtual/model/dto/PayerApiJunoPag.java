package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class PayerApiJunoPag implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String document;
	private AddressApiJuno address;
	private String id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public AddressApiJuno getAddress() {
		return address;
	}
	public void setAddress(AddressApiJuno address) {
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
