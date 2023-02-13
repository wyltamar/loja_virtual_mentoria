package mentoria.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DadosNotificacaoApiJunoPagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String eventId;
	private String eventType;
	private String timestamp;
	
	private List<DataNotificacaoApiJunoPag> data = new ArrayList<DataNotificacaoApiJunoPag>();
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public List<DataNotificacaoApiJunoPag> getData() {
		return data;
	}
	public void setData(List<DataNotificacaoApiJunoPag> data) {
		this.data = data;
	}
	
	

	
}
