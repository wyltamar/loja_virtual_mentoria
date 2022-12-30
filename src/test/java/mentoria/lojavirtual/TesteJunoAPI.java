package mentoria.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import junit.framework.TestCase;
import mentoria.lojavirtual.model.dto.ObjetoPostCarneJuno;
import mentoria.lojavirtual.service.ServiceJunoBoleto;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TesteJunoAPI extends TestCase {
	
	@Autowired
	private ServiceJunoBoleto serviceJunoBoleto;
	
	@Test
	public void testeBuscaToken() throws Exception {
		
		serviceJunoBoleto.obterTokenApiJuno();
		
	}
	
	@Test
	public void testeToken()throws Exception{
		
		ObjetoPostCarneJuno carneJuno = new ObjetoPostCarneJuno();
		carneJuno.setDescription("Teste de geração de boleto");
		carneJuno.setEmail("wyltamarjavadev@gmail.com");
		carneJuno.setIdVenda(9L);
		carneJuno.setInstallments("1");
		carneJuno.setPayerCpfCnpj("05783603490");
		carneJuno.setPayerName("Wyltamar Douglas");
		carneJuno.setPayerPhone("83999645517");
		carneJuno.setReference("Venda número 9 por exemplo");
		carneJuno.setTotalAmount("300,00");
		
		String valor = serviceJunoBoleto.gerarCarneApi(carneJuno);
		System.out.println(valor);
	}

}
