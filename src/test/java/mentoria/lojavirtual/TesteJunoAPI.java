package mentoria.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import junit.framework.TestCase;
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

}
