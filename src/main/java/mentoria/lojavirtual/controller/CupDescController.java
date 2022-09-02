package mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.model.CupDesc;
import mentoria.lojavirtual.repository.CupDescRepository;

@RestController
public class CupDescController {
	
	@Autowired
	private CupDescRepository cupDescRepository;
	
	@ResponseBody
	@PostMapping("**/salvarCupomDesconto")
	public ResponseEntity<CupDesc> salvarCupomDesconto(@RequestBody @Valid CupDesc cupomDesconto) throws ExceptionMentoriaJava{
		
		if(cupomDesconto.getEmpresa() == null ||cupomDesconto.getEmpresa().getId() <= 0 ) {
			
			throw new ExceptionMentoriaJava("Informe a empresa responsável");
		}
		
		List<CupDesc> cupons = cupDescRepository.buscarCupnDescontoPorCodDescricaoPorEmpresa(cupomDesconto.getCodDesc().toUpperCase().trim(), 
																				   				cupomDesconto.getEmpresa().getId());
		if(!cupons.isEmpty()) {
			throw new ExceptionMentoriaJava("Já existe cupom cadastrado com a descrição "+cupomDesconto.getCodDesc());
		}
		
		CupDesc cupDesc = cupDescRepository.save(cupomDesconto);
		
		return new ResponseEntity<CupDesc>(cupDesc, HttpStatus.CREATED);
	}
	
	@ResponseBody
	@PutMapping(value = "**/atualizarCupom")
	public ResponseEntity<CupDesc> atualizarCupom(@RequestBody CupDesc cupDesc) throws ExceptionMentoriaJava{
		
		if(cupDesc.getId() == null || cupDesc == null) {
			throw new ExceptionMentoriaJava("O id é obrigatório para atualização do cupom");
		}
		
		CupDesc cupomAtualizado = cupDescRepository.saveAndFlush(cupDesc);
		
		return new ResponseEntity<CupDesc>(cupomAtualizado, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "**/carregarCuponsDesconto")
	public ResponseEntity<List<CupDesc>> carregarCuponsDesconto(){
		
		List<CupDesc> coponsDescontos = cupDescRepository.findAll();
		
		return new ResponseEntity<List<CupDesc>>(coponsDescontos, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("**/buscarCupomPorId/{idCupom}")
	public ResponseEntity<CupDesc> buscarCupomPorId(@PathVariable("idCupom") Long idCupom) throws ExceptionMentoriaJava{
		
		CupDesc cupDesc = cupDescRepository.findById(idCupom).orElse(null);
		
		if(cupDesc == null) {
			throw new ExceptionMentoriaJava("Não existe cupom de desconto com o id "+idCupom+" cadastrado");
		}
		
		return new ResponseEntity<CupDesc>(cupDesc, HttpStatus.FOUND);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarCuponDescontoPorEmpresa/{idEmpresa}")
	public ResponseEntity<List<CupDesc>> buscarCuponDescontoPorEmpresa(@PathVariable("idEmpresa") Long idEmpresa){
		
		List<CupDesc> cuponsDesconto = cupDescRepository.buscarCuponDescontoPorEmpresa(idEmpresa);
		
		return new ResponseEntity<List<CupDesc>>(cuponsDesconto, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteCupomId/{idCupom}")
	public ResponseEntity<?> deleteCupomId(@PathVariable("idCupom") Long idCupom) throws ExceptionMentoriaJava{
		
		List<CupDesc> cuponsList = cupDescRepository.findAll();
		
		for (CupDesc cupDesc : cuponsList) {
			if(cupDesc.getId() == idCupom) {
				cupDescRepository.deleteById(idCupom);
				break;
			}else {
				throw new ExceptionMentoriaJava("Não existe cumpom cadastrado com o id "+idCupom);
			}
		}
		
		return new ResponseEntity<String>("Cupom com id "+idCupom+" excluído com sucesso!", HttpStatus.OK);
		
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteCupom")
	public ResponseEntity<String> deleteCupom(@RequestBody CupDesc cupDesc){
		
		cupDescRepository.delete(cupDesc);
		
		return new ResponseEntity<String>("Cupom excluído com sucesso", HttpStatus.OK);
	}
	
	
}
