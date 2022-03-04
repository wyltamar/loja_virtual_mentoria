package mentoria.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import junit.framework.TestCase;
import mentoria.lojavirtual.model.PessoaFisica;
import mentoria.lojavirtual.model.PessoaJuridica;
import mentoria.lojavirtual.repository.PessoaRepository;
import mentoria.lojavirtual.service.PessoaUserService;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase{
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Test
	public void testCadPessoaFisica() {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("333933000.111/01");
		pessoaJuridica.setNome("Wyltamar");
		pessoaJuridica.setEmail("wyltamar.doug@gmail.com");
		pessoaJuridica.setInscEstadual("999933333");
		pessoaJuridica.setNomeFantasia("Loja dos imprtados");
		pessoaJuridica.setRazaoSocial("ImportLTDA");
		pessoaJuridica.setTelefone("99334422");
		
		pessoaRepository.save(pessoaJuridica);
		
		/*
		PessoaFisica pessoaFisica = new PessoaFisica();
		
		pessoaFisica.setCpf("987.234.112-02");
		pessoaFisica.setEmail("wyl@gamail.com");
		pessoaFisica.setNome("Wyltamar");
		pessoaFisica.setTelefone("999321111");
		pessoaFisica.setEmpresa(pessoaJuridica); */
		
	}

}
