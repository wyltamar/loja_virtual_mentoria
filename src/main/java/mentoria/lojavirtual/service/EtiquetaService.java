package mentoria.lojavirtual.service;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import mentoria.lojavirtual.model.dto.ObjRetornoCancelamentoEtiquetaDTO;
import okhttp3.OkHttpClient;

@Service
public class EtiquetaService {
	
	
	
	public ObjRetornoCancelamentoEtiquetaDTO cancelarEtiqueta(String idEtiqueta, String description, String reason_id ) throws IOException, ExceptionMentoriaJava {
		
		okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().build();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\n    \"order\": {\n        \"id\": \""+idEtiqueta+"\",\n        \"reason_id\": \""+reason_id+",\n        \"description\": \""+description+"\"\n    }\n}");
		okhttp3.Request request = new okhttp3.Request.Builder()
		  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/cancel")
		  .method("POST", body)
		  .addHeader("Accept", "application/json")
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
		  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
		  .build();
		okhttp3.Response response = client.newCall(request).execute();
		
		String retornoJson = response.body().string();
		
		if(retornoJson.contains("error")) {
			throw new ExceptionMentoriaJava(retornoJson);
		}
		
		JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
		
		Iterator<JsonNode> iterador = jsonNode.iterator();
		
		ObjRetornoCancelamentoEtiquetaDTO dto = new ObjRetornoCancelamentoEtiquetaDTO();
		
		if(iterador.hasNext()) {
			
			JsonNode node = iterador.next();
			
			if(node.get("id") != null) {
				dto.getObjCancelaEtiquetaDTO().setId(node.get("id").asText());
			}
			
			if(node.get("canceled")!= null) {
				dto.getCanceledDTO().setCanceled(node.get("canceled").asText());
			}
			
		}
		
		return dto ;
		
		
		
		
	}

}
