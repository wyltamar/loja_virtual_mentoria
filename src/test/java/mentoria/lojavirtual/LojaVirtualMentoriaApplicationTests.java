package mentoria.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mentoria.lojavirtual.controller.AcessoController;
import mentoria.lojavirtual.model.Acesso;
import mentoria.lojavirtual.repository.AcessoRepository;
import mentoria.lojavirtual.service.AcessoService;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests {

	
	@Autowired
	private AcessoController acessoController;
	
	@Test
	public void testCadastraAcesso() {
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		acessoController.salvarAcesso(acesso);
	}

}
