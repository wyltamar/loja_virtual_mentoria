package mentoria.lojavirtual.service;

import java.io.Serializable;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import mentoria.lojavirtual.model.AccessTokenJunoAPI;
import mentoria.lojavirtual.repository.AccessTokenJunoRepository;

@Service
public class ServiceJunoBoleto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AccessTokenJunoService accessTokenJunoService;
	
	private AccessTokenJunoRepository accessTokenJunoRepository;
	
	public String geraChaveBoletoPix() throws Exception {
		
		AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();
		Client client = new HostIgnoringCliente("https://api.juno.com.br/").hostIgnoringCliente();
		WebResource webResource = client.resource("https://api.juno.com.br/pix/keys");
		
		ClientResponse clientResponse = webResource
				.accept("Content-Type","application/json")
				.header("X-API-Version", 2)
				.header("X-Resource-Token",ApiTokenIntegracao.TOKEN_PRIVATE_JUNO)
				.header("Authorization", " Bearer " +accessTokenJunoAPI.getAccess_token())
				.header("X-Idempotency-Key", "chave_boleto_pix")
				.post(ClientResponse.class,"{ \"type\": \"RANDOM_KEY\" }" );
		
		return clientResponse.getEntity(String.class);
	}
	
	public AccessTokenJunoAPI obterTokenApiJuno() throws Exception {
		
		AccessTokenJunoAPI accessTokenJunoAPI = accessTokenJunoService.buscaTokenAtivo();
		
		if(accessTokenJunoAPI == null || (accessTokenJunoAPI != null && accessTokenJunoAPI.expirado())) {
			
			String clientID = ""; //passar clientID quando tiver o cadastro  na juno
			String secretID = ""; // "
			
			Client client = new HostIgnoringCliente("https://api.juno.com.br/").hostIgnoringCliente();
			
			WebResource webResource = client.resource("https://api.juno.com.br/authorization-server/oauth/token?grant_type=client_credentials");
			
			String basicChave = clientID + ":" + secretID;
			String token_autenticacao = DatatypeConverter.printBase64Binary(basicChave.getBytes());
			
			ClientResponse clientResponse = webResource
					.accept(MediaType.APPLICATION_FORM_URLENCODED)
					.type(MediaType.APPLICATION_FORM_URLENCODED)
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Authorization", "Basic " + token_autenticacao)
					.post(ClientResponse.class);
			
			if(clientResponse.getStatus() == 200) {
				
				accessTokenJunoRepository.deleteAll();
				accessTokenJunoRepository.flush();
				
				AccessTokenJunoAPI accessTokenJunoAPI2 = clientResponse.getEntity(AccessTokenJunoAPI.class);
				accessTokenJunoAPI2.setToken_acesso(token_autenticacao);
				
				accessTokenJunoAPI2 = accessTokenJunoRepository.saveAndFlush(accessTokenJunoAPI2);
				
				return accessTokenJunoAPI2;
				
			}else {
				return null;
			}
		}else {
			return accessTokenJunoAPI;
		}
	}

}
