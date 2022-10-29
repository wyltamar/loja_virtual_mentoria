package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.List;

public class RespostaAdicionaFreteCarrinhoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String protocol;
	private String service_id;
	private String price;
	private String delivery_min;
	private String delivery_max;
	private String status;
	private String insurance_value;
	private String format;
	private List<Product2DTO> products;
	private List<VolumeDTO> volumes;
	private List<TagDTO> tags;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDelivery_min() {
		return delivery_min;
	}
	public void setDelivery_min(String delivery_min) {
		this.delivery_min = delivery_min;
	}
	public String getDelivery_max() {
		return delivery_max;
	}
	public void setDelivery_max(String delivery_max) {
		this.delivery_max = delivery_max;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInsurance_value() {
		return insurance_value;
	}
	public void setInsurance_value(String insurance_value) {
		this.insurance_value = insurance_value;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public List<Product2DTO> getProducts() {
		return products;
	}
	public void setProducts(List<Product2DTO> products) {
		this.products = products;
	}
	public List<VolumeDTO> getVolumes() {
		return volumes;
	}
	public void setVolumes(List<VolumeDTO> volumes) {
		this.volumes = volumes;
	}
	public List<TagDTO> getTags() {
		return tags;
	}
	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}
	@Override
	public String toString() {
		return "RespostaAdicionaFreteCarrinhoDTO [id=" + id + ", protocol=" + protocol + ", service_id=" + service_id
				+ ", price=" + price + ", delivery_min=" + delivery_min + ", delivery_max=" + delivery_max + ", status="
				+ status + ", insurance_value=" + insurance_value + ", format=" + format + ", products=" + products
				+ ", volumes=" + volumes + ", tags=" + tags + "]";
	}
	
	

}
