package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OptionEnvioEtiquetaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String insurance_value;
    private boolean receipt;
    private boolean own_hand;
    private boolean reverse;
    private boolean non_commercial;
    
    private InvoiceEnvioEtiquetaDTO invoice = new InvoiceEnvioEtiquetaDTO();
    
    private String plataform;
	
	private List<TagEnvioEtiquetaDTO> tags = new ArrayList<TagEnvioEtiquetaDTO>();

    
    
	public String getInsurance_value() {
		return insurance_value;
	}
	public void setInsurance_value(String insurance_value) {
		this.insurance_value = insurance_value;
	}
	public boolean isReceipt() {
		return receipt;
	}
	public void setReceipt(boolean receipt) {
		this.receipt = receipt;
	}
	public boolean isOwn_hand() {
		return own_hand;
	}
	public void setOwn_hand(boolean own_hand) {
		this.own_hand = own_hand;
	}
	public boolean isReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	public boolean isNon_commercial() {
		return non_commercial;
	}
	public void setNon_commercial(boolean non_commercial) {
		this.non_commercial = non_commercial;
	}
	public InvoiceEnvioEtiquetaDTO getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceEnvioEtiquetaDTO invoice) {
		this.invoice = invoice;
	}
	
	public String getPlataform() {
		return plataform;
	}

	public void setPlataform(String plataform) {
		this.plataform = plataform;
	}

	public List<TagEnvioEtiquetaDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagEnvioEtiquetaDTO> tags) {
		this.tags = tags;
	}
    
    
      

}
