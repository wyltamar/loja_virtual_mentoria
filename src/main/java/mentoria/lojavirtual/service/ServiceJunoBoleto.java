package mentoria.lojavirtual.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mentoria.lojavirtual.enums.ApiTokenIntegracao;
import mentoria.lojavirtual.model.AccessTokenJunoAPI;
import mentoria.lojavirtual.model.BoletoJuno;
import mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import mentoria.lojavirtual.model.dto.BoletoGeradoApiJunoDTO;
import mentoria.lojavirtual.model.dto.CobrancaJunoAPI;
import mentoria.lojavirtual.model.dto.ConteudoBoletoJunoDTO;
import mentoria.lojavirtual.model.dto.ObjetoPostCarneJuno;
import mentoria.lojavirtual.repository.AccessTokenJunoRepository;
import mentoria.lojavirtual.repository.BoletoJunoRepository;
import mentoria.lojavirtual.repository.Vd_Cp_Loja_VirtRepository;

@Service
public class ServiceJunoBoleto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AccessTokenJunoService accessTokenJunoService;
	
	@Autowired
	private AccessTokenJunoRepository accessTokenJunoRepository;
	
	@Autowired
	private Vd_Cp_Loja_VirtRepository vd_Cp_Loja_VirtRepository;
	
	@Autowired
	private BoletoJunoRepository boletoJunoRepository;
	
	public String cancelarBoletoPix(String code) throws Exception {
		
		AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();
		
			
			Client client = new HostIgnoringCliente("https://api.juno.com.br/").hostIgnoringCliente();
			WebResource webResource = client.resource("https://api.juno.com.br/charges/"+code+"/cancelation");
			
			ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.header("X-Api-Version",2)
					.header("X-Resource-Token",ApiTokenIntegracao.TOKEN_PRIVATE_JUNO)
					.header("Authorization", " Bearer " +accessTokenJunoAPI.getAccess_token())
					.post(ClientResponse.class);
			
			if(clientResponse.getStatus() == 204) {
				return "Boleto de código "+ code + "cancelado com sucesso!";
			}		
				
			return clientResponse.getEntity(String.class);
			
	}
	
	public String gerarCarneApi(ObjetoPostCarneJuno objetoPostCarneJuno) throws Exception {
		
		VendaCompraLojaVirtual vendaCompraLojaVirtual = vd_Cp_Loja_VirtRepository.findById(objetoPostCarneJuno.getIdVenda()).get();
		
		CobrancaJunoAPI cobrancaJunoAPI = new CobrancaJunoAPI();
		cobrancaJunoAPI.getCharge().setPixKey(ApiTokenIntegracao.CHAVE_BOLETO_PIX);
		cobrancaJunoAPI.getCharge().setDescription(objetoPostCarneJuno.getDescription());
		cobrancaJunoAPI.getCharge().setAmount(Float.valueOf(objetoPostCarneJuno.getTotalAmount()));
		cobrancaJunoAPI.getCharge().setInstallments(Integer.parseInt(objetoPostCarneJuno.getInstallments()));
		
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.add(Calendar.DAY_OF_MONTH, 7);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cobrancaJunoAPI.getCharge().setDueDate(dateFormat.format(dataVencimento.getTime()));
		cobrancaJunoAPI.getCharge().setFine(BigDecimal.valueOf(1.00));
		cobrancaJunoAPI.getCharge().setInterest(BigDecimal.valueOf(1.00));
		cobrancaJunoAPI.getCharge().setMaxOverdueDays(10);
		cobrancaJunoAPI.getCharge().getPaymentTypes().add("BOLETO_PIX");
		
		cobrancaJunoAPI.getBelling().setName(objetoPostCarneJuno.getPayerName());
		cobrancaJunoAPI.getBelling().setEmail(objetoPostCarneJuno.getEmail());
		cobrancaJunoAPI.getBelling().setDocument(objetoPostCarneJuno.getPayerCpfCnpj());
		cobrancaJunoAPI.getBelling().setPhone(objetoPostCarneJuno.getPayerPhone());
		
		AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();
		
		if(accessTokenJunoAPI != null) {
			
			Client client = new HostIgnoringCliente("https://api.juno.com.br/").hostIgnoringCliente();
			WebResource webResource = client.resource("https://api.juno.com.br/charges");
			
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(cobrancaJunoAPI);
			
			ClientResponse clientResponse = webResource
					.accept("application/json;charset=UTF-8")
					.header("Content-Type","application/json;charset=UTF-8")
					.header("X-API-Version", 2)
					.header("X-Resource-Token",ApiTokenIntegracao.TOKEN_PRIVATE_JUNO)
					.header("Authorization", " Bearer " +accessTokenJunoAPI.getAccess_token())
					.post(ClientResponse.class,json);
			
			String stringRetorno = clientResponse.getEntity(String.class);
			
			if(clientResponse.getStatus() == 200) { //Retornou com sucesso
				
				clientResponse.close();
				//Converte relacionamentos um para muitos dentro desse json
				objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
				
				BoletoGeradoApiJunoDTO jsonRetornoObj = objectMapper.readValue(stringRetorno, 
						new TypeReference<BoletoGeradoApiJunoDTO>() {});
				
				int recorrencia = 1;
				
				List<BoletoJuno> boletosJuno = new ArrayList<BoletoJuno>();
				
				for(ConteudoBoletoJunoDTO c : jsonRetornoObj.get_embedded().getCharges() ) {
					
					BoletoJuno boletoJuno = new BoletoJuno();
					
					boletoJuno.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
					boletoJuno.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
					boletoJuno.setCode(c.getCode());
					boletoJuno.setLink(c.getLink());
					boletoJuno.setDataVencimento(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(c.getDueDate())));
					boletoJuno.setCheckoutUrl(c.getCheckoutUrl());
					boletoJuno.setValor(new BigDecimal(c.getAmount()));
					boletoJuno.setIdChrBoleto(c.getId());
					boletoJuno.setInstallmentLink(c.getInstallmentLink());
					boletoJuno.setIdPix(c.getPix().getId());
					boletoJuno.setPayloadInBase64(c.getPix().getImageInBase64());
					boletoJuno.setImageInBase64(c.getPix().getImageInBase64());
					boletoJuno.setRecorrencia(recorrencia);
					
					boletosJuno.add(boletoJuno);
					recorrencia++;
					
				}
				
				boletoJunoRepository.saveAllAndFlush(boletosJuno);
				
				return boletosJuno.get(0).getLink();
				
			}else {
				return stringRetorno;
			}
			
		}else {
			return "Não existe chave de acesso para API";
		}
		
	}
	
	public String geraChaveBoletoPix() throws Exception {
		
		AccessTokenJunoAPI accessTokenJunoAPI = this.obterTokenApiJuno();
		Client client = new HostIgnoringCliente("https://api.juno.com.br/").hostIgnoringCliente();
		WebResource webResource = client.resource("https://api.juno.com.br/pix/keys");
		
		ClientResponse clientResponse = webResource
				.accept("application/json","charset=UTF-8")
				.header("Content-Type","application/json;charset=UTF-8")
				.header("X-API-Version", 2)
				.header("X-Resource-Token",ApiTokenIntegracao.TOKEN_PRIVATE_JUNO)
				.header("Authorization", " Bearer " +accessTokenJunoAPI.getAccess_token())
				.post(ClientResponse.class,"{ \"type\": \"RANDOM_KEY\" }" );
		
		//.header("X-Idempotency-Key", "chave_boleto_pix")
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
