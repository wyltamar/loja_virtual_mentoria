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
		
		/*Teste de insersão de fretes no carrinho*/
		
	/*	OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\n    \"service\": 3,\n    \"agency\": 49,\n    \"from\": {\n        \"name\": \"Nome do remetente\",\n        \"phone\": \"53984470102\",\n        \"email\": \"contato@melhorenvio.com.br\",\n        \"document\": \"16571478358\",\n        \"company_document\": \"89794131000100\",\n        \"state_register\": \"123456\",\n        \"address\": \"Endereço do remetente\",\n        \"complement\": \"Complemento\",\n        \"number\": \"1\",\n        \"district\": \"Bairro\",\n        \"city\": \"São Paulo\",\n        \"country_id\": \"BR\",\n        \"postal_code\": \"01002001\",\n        \"note\": \"observação\"\n    },\n    \"to\": {\n        \"name\": \"Nome do destinatário\",\n        \"phone\": \"53984470102\",\n        \"email\": \"contato@melhorenvio.com.br\",\n        \"document\": \"25404918047\",\n        \"company_document\": \"07595604000177\",\n        \"state_register\": \"123456\",\n        \"address\": \"Endereço do destinatário\",\n        \"complement\": \"Complemento\",\n        \"number\": \"2\",\n        \"district\": \"Bairro\",\n        \"city\": \"Porto Alegre\",\n        \"state_abbr\": \"RS\",\n        \"country_id\": \"BR\",\n        \"postal_code\": \"90570020\",\n        \"note\": \"observação\"\n    },\n    \"products\": [\n        {\n            \"name\": \"Papel adesivo para etiquetas 1\",\n            \"quantity\": 3,\n            \"unitary_value\": 100.00\n        },\n        {\n            \"name\": \"Papel adesivo para etiquetas 2\",\n            \"quantity\": 1,\n            \"unitary_value\": 700.00\n        }\n    ],\n    \"volumes\": [\n        {\n            \"height\": 15,\n            \"width\": 20,\n            \"length\": 10,\n            \"weight\": 3.5\n        }\n    ],\n    \"options\": {\n        \"insurance_value\": 1000.00,\n        \"receipt\": false,\n        \"own_hand\": false,\n        \"reverse\": false,\n        \"non_commercial\": false,\n        \"invoice\": {\n            \"key\": \"31190307586261000184550010000092481404848162\"\n        },\n        \"platform\": \"Nome da Plataforma\",\n        \"tags\": [\n            {\n                \"tag\": \"Identificação do pedido na plataforma, exemplo: 1000007\",\n                \"url\": \"Link direto para o pedido na plataforma, se possível, caso contrário pode ser passado o valor null\"\n            }\n        ]\n    }\n}");
				Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX +"api/v2/me/cart")
				  .method("POST", body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				Response response = client.newCall(request).execute();
				
				System.out.println(response.body().string()); */
		
		/*teste compra de frete ou Chekout de ordens*/
		/*
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\n    \"orders\": [\n        \"78417fae-eda6-4808-8700-93a24d919c10\"\n    ]\n}");
				Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX +"api/v2/me/shipment/checkout")
				  .method("POST", body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				Response response = client.newCall(request).execute();
				System.out.println(response.body().string()); */
		
		/*Teste de pre-vizualização de etiquetas*/
		/*
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\n    \"orders\": [\n        \"78417fae-eda6-4808-8700-93a24d919c10\"\n    ]\n}");
				Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/preview")
				  .method("POST", body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				Response response = client.newCall(request).execute();
				System.out.println(response.body().string()); */
		
		
		/*Teste de geração de etiquetas*/
		/*
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\n    \"orders\":[\n        \"78417fae-eda6-4808-8700-93a24d919c10\"\n    ]\n}");
				Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/generate")
				  .method("POST", body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				Response response = client.newCall(request).execute();
				System.out.println(response.body().string()); */
		
		/*Teste de impressão da etiqueta*/
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "{\n    \"mode\": \"private\",\n    \"orders\": [\n        \"78417fae-eda6-4808-8700-93a24d919c10\"\n    ]\n}");
				Request request = new Request.Builder()
				  .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+"api/v2/me/shipment/print")
				  .method("POST", body)
				  .addHeader("Accept", "application/json")
				  .addHeader("Content-Type", "application/json")
				  .addHeader("Authorization", "Bearer "+ ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
				  .addHeader("User-Agent", "wyltamarjavadev@gmail.com")
				  .build();
				Response response = client.newCall(request).execute();
				System.out.println(response.body().string());
				
	}

}
