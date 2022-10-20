package mentoria.lojavirtual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import mentoria.lojavirtual.model.dto.EmpresaTransporteDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TesteAPIMelhorEnvio {
	
	public static void main(String[] args) throws IOException {
		
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\n    \"from\": {\n        \"postal_code\": \"96020360\"\n    },\n    \"to\": {\n        \"postal_code\": \"01018020\"\n    },\n    \"products\": [\n        {\n            \"id\": \"x\",\n            \"width\": 11,\n            \"height\": 17,\n            \"length\": 11,\n            \"weight\": 0.3,\n            \"insurance_value\": 10.1,\n            \"quantity\": 1\n        },\n        {\n            \"id\": \"y\",\n            \"width\": 16,\n            \"height\": 25,\n            \"length\": 11,\n            \"weight\": 0.3,\n            \"insurance_value\": 55.05,\n            \"quantity\": 2\n        },\n        {\n            \"id\": \"z\",\n            \"width\": 22,\n            \"height\": 30,\n            \"length\": 11,\n            \"weight\": 1,\n            \"insurance_value\": 30,\n            \"quantity\": 1\n        }\n    ]\n}");
				Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"/api/v2/me/shipment/calculate")
				  .method("POST", body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer " +ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				Response response = client.newCall(request).execute();
				//System.out.println(response.body().string());
				
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
				
				System.out.println(empresaTransporteDTOList);
				
	}

}
