package mentoria.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import junit.framework.TestCase;
import mentoria.lojavirtual.controller.PessoaController;
import mentoria.lojavirtual.model.PessoaJuridica;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase{
	
	
	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void testCadPessoaFisica() throws ExceptionMentoriaJava {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Pedro Lucas Almeida de Oliveira");
		pessoaJuridica.setEmail("plao@gmail.com");
		pessoaJuridica.setInscEstadual("99645517");
		pessoaJuridica.setNomeFantasia("Loja da Tecnologia");
		pessoaJuridica.setRazaoSocial("ImportTECLTDA");
		pessoaJuridica.setTelefone("969334422");
		
		pessoaController.salvarPj(pessoaJuridica);
		
		
		
	}

}
