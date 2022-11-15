package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EnvioEtiquetaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String service;
	private String agency;
	
	private FromEnvioEtiquetaDTO from = new FromEnvioEtiquetaDTO();
	
	private ToEnvioEtiquetaDTO to = new ToEnvioEtiquetaDTO();
	
	private List<ProductEnvioEtiquetaDTO> products = new ArrayList<ProductEnvioEtiquetaDTO>();
	
	private List<VolumeEnvioEtiquetaDTO> volumes = new ArrayList<VolumeEnvioEtiquetaDTO>();
	
	private OptionEnvioEtiquetaDTO options = new OptionEnvioEtiquetaDTO();
	
	
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

	public FromEnvioEtiquetaDTO getFrom() {
		return from;
	}

	public void setFrom(FromEnvioEtiquetaDTO from) {
		this.from = from;
	}

	public ToEnvioEtiquetaDTO getTo() {
		return to;
	}

	public void setTo(ToEnvioEtiquetaDTO to) {
		this.to = to;
	}

	public List<ProductEnvioEtiquetaDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEnvioEtiquetaDTO> products) {
		this.products = products;
	}

	public List<VolumeEnvioEtiquetaDTO> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<VolumeEnvioEtiquetaDTO> volumes) {
		this.volumes = volumes;
	}

	public OptionEnvioEtiquetaDTO getOptions() {
		return options;
	}

	public void setOptions(OptionEnvioEtiquetaDTO options) {
		this.options = options;
	}

	
	
	
	
	

}
