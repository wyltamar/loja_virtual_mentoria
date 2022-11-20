package mentoria.lojavirtual.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import mentoria.lojavirtual.model.dto.AgencyTransportadoraDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class ListarAgenciaTransportadoraService {
	
	/**
	 * Este método retora uma Lista de agências de transportadoras
	 * credenciadas pela API da empresa Melhor Envio
	 * @return List<AgencyTransportadoraDTO> AgencyTransportadoraDTO
	 * @author Wyltamar
	 */
	public List<AgencyTransportadoraDTO> listarAgenciasTransportadoraME() throws IOException,ExceptionMentoriaJava{
		
		/*Listar agências*/	
		OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder()
			  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/agencies")
			  .method("GET", null)
			  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
			  .build();
			Response response = client.newCall(request).execute();
			
			String respostaJson = response.body().string();
			
			if(respostaJson.contains("error")) {
				throw new ExceptionMentoriaJava(respostaJson);
			}
			
			JsonNode jsonNode = new ObjectMapper().readTree(respostaJson);
			
			Iterator<JsonNode> iterator = jsonNode.iterator();
			
			List<AgencyTransportadoraDTO> agenciesList = new ArrayList<AgencyTransportadoraDTO>();
			
			while(iterator.hasNext()){
				
				JsonNode node = iterator.next();
				
				AgencyTransportadoraDTO agencyDTO = new AgencyTransportadoraDTO();
				
				if(node.get("id") != null) {
					agencyDTO.setId(node.get("id").asText());
				}
				
				if(node.get("name") != null) {
					agencyDTO.setName(node.get("name").asText());
				}
				
				if(node.get("code") != null) {
					agencyDTO.setCode(node.get("code").asText());
				}
				
				if(node.get("company_name") != null) {
					agencyDTO.setCompany_name(node.get("company_name").asText());
				}
				
				if(node.get("company_id") != null) {
					agencyDTO.setCompany_id(node.get("company_id").asText());
				}
				
				if(node.get("email") != null) {
					agencyDTO.setEmail(node.get("email").asText());
				}
				
				
				agenciesList.add(agencyDTO);
				
			}
			
			if(agenciesList.isEmpty()) {
				throw new ExceptionMentoriaJava("Tivemos um problema na listagem das Agências de Transportes"
						+ "Informe o problema ao administrador do sistema");
			}
			
		return agenciesList;
	}

}
