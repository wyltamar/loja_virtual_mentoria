package mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.model.ContaPagar;
import mentoria.lojavirtual.repository.ContaPagarRepository;

@Controller
@RestController
public class ContaPagarContrloller {

	@Autowired
	private ContaPagarRepository contaPagarRepository;
	
	@ResponseBody 
	@PostMapping(value = "**/salvarContaPagar") 
	public ResponseEntity<ContaPagar> salvarContaPagar(@RequestBody @Valid ContaPagar contaPagar) throws ExceptionMentoriaJava { /*Recebe o JSON e converte para Objeto Java*/
		
		if(contaPagar.getEmpresa().getId() == null || contaPagar.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A empresa deve ser informada ");
		}
		
		if(contaPagar.getPessoa().getId() == null || contaPagar.getPessoa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A Pessoa responsávle deve ser informada ");
		}
		
		if(contaPagar.getPessoa_fornecedor().getId() == null || contaPagar.getPessoa_fornecedor().getId() <= 0) {
			throw new ExceptionMentoriaJava("O fornecedor responsávle deve ser informada ");
		}
		
		if(contaPagar.getId() == null || contaPagar.getId() <= 0) {
			
			List<ContaPagar> contasPagar = contaPagarRepository.buscaContaDesc(contaPagar.getDescricao().toUpperCase().trim());
		
			if(!contasPagar.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe conta pagar com essa descrição");
			}
		}
		
		ContaPagar contaPagarSalva = contaPagarRepository.save(contaPagar);
		
		return new ResponseEntity<ContaPagar>(contaPagarSalva, HttpStatus.OK);
	}
	
	@ResponseBody 
	@PostMapping(value = "**/deleteContaPagar")
	public ResponseEntity<?> deleteContaPagar(@RequestBody ContaPagar contaPagar) { 
		
		contaPagarRepository.delete(contaPagar);
		
		return new ResponseEntity<>("Conta a pagar excluida com suceso", HttpStatus.OK);
	}
	
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteContaPagarPorId/{id}") 
	public ResponseEntity<?> deleteContaPagarPorId(@PathVariable ("id") Long id) {
		
		contaPagarRepository.deleteById(id);
		
		return new ResponseEntity<>("Conta a pagar removida com sucesso", HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/obterContaPagar/{id}")
	public ResponseEntity<ContaPagar> obterContaPagar(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		ContaPagar contaPagar = contaPagarRepository.findById(id).orElse(null);
		
		if(contaPagar == null) {
			throw new ExceptionMentoriaJava("Conta a pagar com código: "+id+" não existe em nossa base de dados");
		}
		
		return new ResponseEntity<ContaPagar>(contaPagar, HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/buscarContaPagarDesc/{desc}")
	public ResponseEntity<List<ContaPagar>> buscarContaPagarDesc(@PathVariable("desc") String desc) { 
		
		List <ContaPagar> contasPagar = contaPagarRepository.buscaContaDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<ContaPagar>>(contasPagar, HttpStatus.OK);
	}
	
	
}
