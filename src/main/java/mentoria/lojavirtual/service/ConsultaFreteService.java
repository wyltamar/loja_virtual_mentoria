package mentoria.lojavirtual.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import mentoria.lojavirtual.model.dto.ConsultaFreteDTO;
import mentoria.lojavirtual.model.dto.EmpresaTransporteDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class ConsultaFreteService {
	
	public List<EmpresaTransporteDTO> calcularFrete(ConsultaFreteDTO consultaFreteDTO) throws IOException{
		
		ObjectMapper objeto = new ObjectMapper();
		
		String json = objeto.writeValueAsString(consultaFreteDTO);
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody requestBody = RequestBody.create(mediaType, json);
		Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"/api/v2/me/shipment/calculate")
				  .method("POST", requestBody)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer " +ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				
		Response response = client.newCall(request).execute();
		
		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
		
		Iterator<JsonNode> iterator = jsonNode.iterator();
		
		List<EmpresaTransporteDTO> empresaTransporteDTOList = new ArrayList<EmpresaTransporteDTO>();
		
		
		while(iterator.hasNext()) {
			
			JsonNode node = iterator.next();
			
			EmpresaTransporteDTO empresaTransporteDTO = new EmpresaTransporteDTO();
			
			if(node.get("id") != null) {
				empresaTransporteDTO.setId(node.get("id").asText()); 
			}
			
			if(node.get("name") != null) {
				empresaTransporteDTO.setNome(node.get("name").asText());
			}
			
			if(node.get("price") != null) {
				empresaTransporteDTO.setValor(node.get("price").asText());
			}
			
			if(node.get("company").get("name") != null) {
				empresaTransporteDTO.setEmpresa(node.get("company").get("name").asText());
			}
			
			if(node.get("company").get("picture") != null) {
				empresaTransporteDTO.setPicture(node.get("company").get("picture").asText());
			}
			
			if(empresaTransporteDTO.dadosOK()) {
				empresaTransporteDTOList.add(empresaTransporteDTO);
			}
			
		}
		
		return empresaTransporteDTOList;
		
		
	
	}
}
