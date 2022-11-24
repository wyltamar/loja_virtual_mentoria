package mentoria.lojavirtual;

import java.io.IOException;
import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TesteAPIMelhorEnvio {
	
	public static void main(String[] args) throws IOException {
		
			/*Listar agências*/	
		
		/*	OkHttpClient client = new OkHttpClient().newBuilder().build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/agencies")
				  .method("GET", null)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				Response response = client.newCall(request).execute();
				
				System.out.println(response.body().string()); */
		
			/*Testando o rastreio de envios*/
			OkHttpClient client = new OkHttpClient().newBuilder().build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\n    \"orders\": [\n        \"78417fae-eda6-4808-8700-93a24d919c10\"\n    ]\n}");
				Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/tracking")
				  .method("POST", body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer " +ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gamail.com")
				  .build();
				Response response = client.newCall(request).execute();
				
				System.out.println(response.body().string());
				
				
				
				
	}

}
