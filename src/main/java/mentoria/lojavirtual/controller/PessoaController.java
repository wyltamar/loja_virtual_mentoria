package mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.enums.TipoPessoa;
import mentoria.lojavirtual.model.Endereco;
import mentoria.lojavirtual.model.PessoaFisica;
import mentoria.lojavirtual.model.PessoaJuridica;
import mentoria.lojavirtual.model.dto.CepDTO;
import mentoria.lojavirtual.model.dto.ConsultaCnpjDTO;
import mentoria.lojavirtual.repository.EnderecoRepository;
import mentoria.lojavirtual.repository.PessoaFisicaRepository;
import mentoria.lojavirtual.repository.PessoaRepository;
import mentoria.lojavirtual.service.AcessoEndPointService;
import mentoria.lojavirtual.service.PessoaUserService;
import mentoria.lojavirtual.util.ValidaCPF;
import mentoria.lojavirtual.util.ValidaCnpj;

@Controller
@RestController
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private AcessoEndPointService acessoEndPointService;
	
	@ResponseBody
	@GetMapping(value = "**/consultaPfNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome){
		
		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorNomePF(nome.trim().toUpperCase());
		
		acessoEndPointService.qtdAcessoEndPoint(("CONSULTA_PF_NOME").trim().toUpperCase());
		
		return new ResponseEntity<List<PessoaFisica>>(fisicas,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPfCpf/{cpf}")
	public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf){
		
		return new ResponseEntity<List<PessoaFisica>>(pessoaFisicaRepository.existeCpfCadastradoList(cpf),HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPjNome/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaPjNome(@PathVariable("nome") String nome){
		
		return new ResponseEntity<List<PessoaJuridica>>(pessoaRepository.pesquisaPorNomePJ(nome.trim().toUpperCase()),HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCnpjPj/{cnpj}")
	public ResponseEntity<List<PessoaJuridica>> consultaCnpjPj(@PathVariable("cnpj") String cnpj){
		
		return new ResponseEntity<List<PessoaJuridica>>(pessoaRepository.existeCnpjCadastradoList(cnpj),HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep){
		return new ResponseEntity<CepDTO>(pessoaUserService.consultaCep(cep), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCnpjReceitaWS/{cnpj}")
	public ResponseEntity<ConsultaCnpjDTO> consultarCnpj(@PathVariable("cnpj") String cnpj){
		return new ResponseEntity<ConsultaCnpjDTO>(pessoaUserService.conslutaCnpjReceitaWS(cnpj),HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{
		
		/*if(pessoaJuridica.getNome() == null || pessoaJuridica.getNome().trim().isEmpty()) {
			throw new ExceptionMentoriaJava("Informe o campo nome");
		}*/
		
		if(pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Não podemos cadastrar Pessoa Júridica como NULL");
		}
		
		if(pessoaJuridica.getTipoPessoa() == null) {
			throw new ExceptionMentoriaJava("Informe o tipo: Júridica ou Jurídica e Fornecedor");
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
		
		if(pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			
			for(int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				
				CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
				
				pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setLogradouro(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
			}
		}else {
			
			for(int p = 0; p < pessoaJuridica.getEnderecos().size(); p++ ) {
				
				Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
				
				if(!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
					
					CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
				
					pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setLogradouro(cepDTO.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
				}
			}
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava{
		
		if(pessoaFisica == null) {
			throw new ExceptionMentoriaJava("Não podemos cadastrar Pessoa Física como NULL");
		}
		
		if(pessoaFisica.getTipoPessoa() == null) {
			pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
		}
		
		if(pessoaFisica.getId() == null && pessoaFisicaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null ) {
			throw new ExceptionMentoriaJava("Já existe Pessoa Física cadastrada com o cpf: "+pessoaFisica.getCpf());
		}
		

		if(!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("CPF: "+pessoaFisica.getCpf()+" é inválido.");
		}
		
		if(pessoaFisica.getId() == null || pessoaFisica.getId() < 0) {
			
			for(int pos = 0; pos < pessoaFisica.getEnderecos().size(); pos++) {
				
				CepDTO cepDTO2 = pessoaUserService.consultaCep(pessoaFisica.getEnderecos().get(pos).getCep());
				
				pessoaFisica.getEnderecos().get(pos).setBairro(cepDTO2.getBairro());
				pessoaFisica.getEnderecos().get(pos).setCidade(cepDTO2.getLocalidade());
				pessoaFisica.getEnderecos().get(pos).setComplemento(cepDTO2.getComplemento());
				pessoaFisica.getEnderecos().get(pos).setLogradouro(cepDTO2.getLogradouro());
				pessoaFisica.getEnderecos().get(pos).setUf(cepDTO2.getUf());
			}
		}else {
			
			for(int pos = 0; pos < pessoaFisica.getEnderecos().size(); pos++) {
				
				Endereco enderecoTemp2 = enderecoRepository.findById(pessoaFisica.getEnderecos().get(pos).getId()).get();
				
				if(!enderecoTemp2.getCep().equals(pessoaFisica.getEnderecos().get(pos).getCep())) {
					
					CepDTO cepDTO2 = pessoaUserService.consultaCep(pessoaFisica.getEnderecos().get(pos).getCep());
					
					pessoaFisica.getEnderecos().get(pos).setBairro(cepDTO2.getBairro());
					pessoaFisica.getEnderecos().get(pos).setCidade(cepDTO2.getLocalidade());
					pessoaFisica.getEnderecos().get(pos).setComplemento(cepDTO2.getComplemento());
					pessoaFisica.getEnderecos().get(pos).setLogradouro(cepDTO2.getLogradouro());
					pessoaFisica.getEnderecos().get(pos).setUf(cepDTO2.getUf());
				}
			}
		}
		
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
	}


}
