package mentoria.lojavirtual.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import mentoria.lojavirtual.model.dto.AdicionaFreteCarrinhoDTO;
import mentoria.lojavirtual.model.dto.Product2DTO;
import mentoria.lojavirtual.model.dto.RespostaAdicionaFreteCarrinhoDTO;
import mentoria.lojavirtual.model.dto.TagDTO;
import mentoria.lojavirtual.model.dto.VolumeDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class AdicionaFreteCarrinhoService {
	
	public List<RespostaAdicionaFreteCarrinhoDTO> adicionarFreteCarrinho(
				AdicionaFreteCarrinhoDTO adicionaFreteCarrinhoDTO) throws IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		
		String jsonRequisicao = mapper.writeValueAsString(adicionaFreteCarrinhoDTO);
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody requestBody = RequestBody.create(mediaType, jsonRequisicao);
		Request request = new Request.Builder()
				.url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX +"api/v2/me/cart")
				.method("POST", requestBody)
				.addHeader("Accept", "application/json")
				 .addHeader("Content-Type", "application/json")
				 .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				 .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				 .build();
		
		Response response = client.newCall(request).execute();
		
		JsonNode node = new ObjectMapper().readTree(response.body().string());
		
		Iterator<JsonNode> iterator = node.iterator();
		
		List<RespostaAdicionaFreteCarrinhoDTO> adicionaFreteCarrinhoList = new ArrayList<RespostaAdicionaFreteCarrinhoDTO>();
		
		while(iterator.hasNext()) {
			
			
			JsonNode jsonNode = iterator.next();
			
			RespostaAdicionaFreteCarrinhoDTO  freteCarrinhoDTO = new RespostaAdicionaFreteCarrinhoDTO();
			
			if(jsonNode.get("id") != null) {
				freteCarrinhoDTO.setId(jsonNode.get("id").asText());
			}
			
			if(jsonNode.get("protocol") != null) {
				freteCarrinhoDTO.setProtocol(jsonNode.get("protocol").asText());
			}
			
			if(jsonNode.get("service_id") != null) {
				freteCarrinhoDTO.setService_id(jsonNode.get("service_id").asText());
			}
			
			if(jsonNode.get("price") != null) {
				freteCarrinhoDTO.setPrice(jsonNode.get("price").asText());
			}
			
			if(jsonNode.get("delivery_min") != null) {
				freteCarrinhoDTO.setDelivery_min(jsonNode.get("delivery_min").asText());
			}
			
			if(jsonNode.get("delivery_max") != null) {
				freteCarrinhoDTO.setDelivery_max(jsonNode.get("delivery_max").asText());
			}
			
			if(jsonNode.get("status") != null) {
				freteCarrinhoDTO.setStatus(jsonNode.get("status").asText());
			}
			
			if(jsonNode.get("insurance_value") != null) {
				freteCarrinhoDTO.setInsurance_value(jsonNode.get("insurance_value").asText());
			}
			
			if(jsonNode.get("format") != null) {
				freteCarrinhoDTO.setFormat(jsonNode.get("format").asText());
			}
			
			if(!jsonNode.findValuesAsText("products").isEmpty()) {
								
				List<String> jsonList = jsonNode.findValuesAsText("products");
				
				ObjectMapper mapper2 = new ObjectMapper();
					
				for (String stringValue : jsonList) {
					Product2DTO product = mapper2.readValue(stringValue, Product2DTO.class);
					freteCarrinhoDTO.getProducts().add(product);
				}
			
			}
			
			if(!jsonNode.findValuesAsText("volumes").isEmpty()) {
				
				List<String> jsonList = jsonNode.findValuesAsText("volumes");
				
				ObjectMapper objectMapper = new ObjectMapper();
				
				for (String itemList : jsonList) {
					VolumeDTO volume = objectMapper.readValue(itemList, VolumeDTO.class);
					freteCarrinhoDTO.getVolumes().add(volume);
					
				}
			}
			
			if(!jsonNode.findValuesAsText("tags").isEmpty()) {
				
				List<String> tagsList = jsonNode.findValuesAsText("tags");
				
				ObjectMapper objectMapper = new ObjectMapper();
				
				for (String itemTagsList : tagsList) {
					
					TagDTO tag = objectMapper.readValue(itemTagsList, TagDTO.class);
					freteCarrinhoDTO.getTags().add(tag);
				}
				
			}
			
			adicionaFreteCarrinhoList.add(freteCarrinhoDTO);
				
		}
		
		
		return adicionaFreteCarrinhoList;
	}

}
