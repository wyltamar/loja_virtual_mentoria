package mentoria.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.model.PessoaJuridica;
import mentoria.lojavirtual.repository.PessoaRepository;
import mentoria.lojavirtual.service.PessoaUserService;
import mentoria.lojavirtual.util.ValidaCnpj;

@Controller
@RestController
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{
		
		if(pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Não podemos cadastrar Pessoa Júridica como NULL");
		}
		
		if(pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null ) {
			throw new ExceptionMentoriaJava("Já existe Pessoa Jurídica cadastrada com o cnpj: "+pessoaJuridica.getCnpj());
		}
		
		if(pessoaJuridica.getId() == null && pessoaRepository.existeInscEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoriaJava("Já existe Pessoa Jurídica cadastrada com a inscrição estadual: "+pessoaJuridica.getInscEstadual());
		}
		
		if(!ValidaCnpj.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("CNPJ: "+pessoaJuridica.getCnpj()+" é inválido.");
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}

}
