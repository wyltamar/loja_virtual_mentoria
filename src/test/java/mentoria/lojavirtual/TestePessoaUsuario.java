package mentoria.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import junit.framework.TestCase;
import mentoria.lojavirtual.controller.PessoaController;
import mentoria.lojavirtual.enums.TipoEndereco;
import mentoria.lojavirtual.model.Endereco;
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
		pessoaJuridica.setEmail("wyltamardouglasdesousaoliveir@gmail.com");
		pessoaJuridica.setInscEstadual("99645517");
		pessoaJuridica.setNomeFantasia("Loja da Tecnologia2");
		pessoaJuridica.setRazaoSocial("ImportTECLTDA");
		pessoaJuridica.setTelefone("969334422");
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Beira Rio");
		endereco1.setCep("58830-000");
		endereco1.setCidade("Jericó");
		endereco1.setComplemento("casa");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setLogradouro("Rua Delmiro Pereira da Silva");
		endereco1.setNumero("12");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco1.setUf("PB");
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Centro");
		endereco2.setCep("58830-000");
		endereco2.setCidade("Jericó");
		endereco2.setComplemento("apartamento");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setLogradouro("Rua Erundina de Oliveira");
		endereco2.setNumero("129");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco2.setUf("PB");
		
		pessoaJuridica.getEnderecos().add(endereco1);
		pessoaJuridica.getEnderecos().add(endereco2);
		
		pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();
		
		assertEquals(true, pessoaJuridica.getId() > 0);
		
		for(Endereco endereco : pessoaJuridica.getEnderecos()) {
			
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaJuridica.getEnderecos().size());
		
		assertEquals(endereco2.getNumero(), "129");
		
		
	}

}
