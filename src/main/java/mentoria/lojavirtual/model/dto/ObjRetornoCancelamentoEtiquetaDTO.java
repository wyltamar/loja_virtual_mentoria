package mentoria.lojavirtual.model.dto;

import java.io.Serializable;

public class ObjRetornoCancelamentoEtiquetaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ObjCancelaEtiquetaDTO objCancelaEtiquetaDTO;
	
	private CanceledDTO canceledDTO;

	public ObjCancelaEtiquetaDTO getObjCancelaEtiquetaDTO() {
		return objCancelaEtiquetaDTO;
	}

	public void setObjCancelaEtiquetaDTO(ObjCancelaEtiquetaDTO objCancelaEtiquetaDTO) {
		this.objCancelaEtiquetaDTO = objCancelaEtiquetaDTO;
	}

	public CanceledDTO getCanceledDTO() {
		return canceledDTO;
	}

	public void setCanceledDTO(CanceledDTO canceledDTO) {
		this.canceledDTO = canceledDTO;
	}
	
	
	
	
	
	

}
