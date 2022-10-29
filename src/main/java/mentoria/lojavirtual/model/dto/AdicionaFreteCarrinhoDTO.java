package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.List;

public class AdicionaFreteCarrinhoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String service;
	private String agency;
	private From2DTO from;
	private To2DTO to;
	private List<Product2DTO> products;
	private List<VolumeDTO> volumes;
	private OptionDTO options;
	private String plataform;
	private List<TagDTO> tags;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public From2DTO getFrom() {
		return from;
	}
	public void setFrom(From2DTO from) {
		this.from = from;
	}
	public To2DTO getTo() {
		return to;
	}
	public void setTo(To2DTO to) {
		this.to = to;
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
	public OptionDTO getOptions() {
		return options;
	}
	public void setOptions(OptionDTO options) {
		this.options = options;
	}
	public String getPlataform() {
		return plataform;
	}
	public void setPlataform(String plataform) {
		this.plataform = plataform;
	}
	public List<TagDTO> getTags() {
		return tags;
	}
	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}
	@Override
	public String toString() {
		return "AdicionaFreteCarrinhoDTO [service=" + service + ", agency=" + agency + ", from=" + from + ", to=" + to
				+ ", products=" + products + ", volumes=" + volumes + ", options=" + options + ", plataform="
				+ plataform + ", tags=" + tags + "]";
	}
	
	
	
	


}
