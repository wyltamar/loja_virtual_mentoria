package mentoria.lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.net.SyslogOutputStream;
import junit.framework.TestCase;
import mentoria.lojavirtual.controller.AcessoController;
import mentoria.lojavirtual.model.Acesso;
import mentoria.lojavirtual.repository.AcessoRepository;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests extends TestCase {

	
	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext wac;
	
	/*Teste do end-point de salvar*/
	@Test
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
								.perform(MockMvcRequestBuilders.post("/salvarAcesso")
								.content(objectMapper.writeValueAsString(acesso))
								.accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API:"+retornoApi.andReturn().getResponse().getContentAsString());
		/*Converter retorno da API para um objeto de acesso*/
		
		Acesso objetoRetorno = objectMapper.
								readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
		
	}
	
	@Test
	public void testRestApiDeleteAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
								.perform(MockMvcRequestBuilders.post("/deleteAcesso")
								.content(objectMapper.writeValueAsString(acesso))
								.accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API: "+retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno: "+retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso excluido com suceso",retornoApi.andReturn().getResponse().getContentAsString() );
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
	}
	
	@Test
	public void testRestApiDeletePorIDAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
								.perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/"+acesso.getId())
								.content(objectMapper.writeValueAsString(acesso))
								.accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println("Retorno da API: "+retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno: "+retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals("Acesso removido",retornoApi.andReturn().getResponse().getContentAsString() );
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
	}
	
	@Test
	public void testRestApiObterAcessoID() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_OBTER_ACESSO_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
								.perform(MockMvcRequestBuilders.get("/obterAcesso/"+acesso.getId())
								.content(objectMapper.writeValueAsString(acesso))
								.accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON));
		
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		Acesso acessoRetorno = objectMapper
								.readValue(retornoApi.andReturn()
								.getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(),acessoRetorno.getDescricao());
		
	}
	
	@Test
	public void testRestApiObterAcessoDesc() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TEST_OBTER_LIST");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc
								.perform(MockMvcRequestBuilders.get("/buscarPorDesc/OBTER_LIST")
								.content(objectMapper.writeValueAsString(acesso))
								.accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON));
		
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		
		List <Acesso> retornoApiList = objectMapper.
										readValue(retornoApi.andReturn().
										getResponse().getContentAsString(), 
										new TypeReference<List<Acesso>>() {
										});
		
		assertEquals(1,retornoApiList.size());
	
		assertEquals(acesso.getDescricao(),retornoApiList.get(0).getDescricao() );
		
		
		acessoRepository.deleteById(acesso.getId());
		
	}
	
	@Test
	public void testCadastraAcesso() {
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		/*Validando se o id realmente está nulo, pois ele deve ser nulo antes de gravar no banco de dados*/
		
		assertEquals(true, acesso.getId() == null);
		
		/*Salvou no banco de dados e atribuiu o corpo da requisição na variavel de referência acesso*/
		
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		/*Validando se o id foi salvo no banco de dados*/
		
		assertEquals(true, acesso.getId() > 0);
		
		/*Validar dados salvos da forma correta*/
		
		assertEquals("ROLE_ADMIN", acesso.getDescricao());
		
		/*Teste de carregamento de dados vindos do banco*/
		
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
		
		assertEquals(acesso.getId(), acesso2.getId());
		
		/*Teste de exclusão de dados*/
		
		acessoRepository.deleteById(acesso2.getId());
		
		acessoRepository.flush(); /*Roda esse SQL de delete no banco de dados*/
		
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		
		assertEquals(null, acesso3);
		
		/*Teste de query*/
		
		acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ALUNO");
		
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());
		
		assertEquals(1, acessos.size());
		
		acessoRepository.deleteById(acesso.getId());
		
		
		
		
		
	}

}
