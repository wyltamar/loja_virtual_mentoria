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
import mentoria.lojavirtual.model.MarcaProduto;
import mentoria.lojavirtual.repository.MarcaRepository;

@Controller
@RestController
public class MarcaProdutoController {

	
	@Autowired
	private MarcaRepository marcaRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarMarca") 
	public ResponseEntity<MarcaProduto> salvarMarca(@RequestBody @Valid MarcaProduto marcaProduto) throws ExceptionMentoriaJava {		
		if(marcaProduto.getId() == null) {
			
			List <MarcaProduto> marcas = marcaRepository.buscarMarcaDesc(marcaProduto.getNomeDesc().toUpperCase());
			
			if(!marcas.isEmpty()) {
				
				throw new ExceptionMentoriaJava("Já existe Marca com a descrição: "+marcaProduto.getNomeDesc());
			}
		}
		
		MarcaProduto marcaSalva = marcaRepository.save(marcaProduto);
		
		return new ResponseEntity<MarcaProduto>(marcaSalva, HttpStatus.OK);
	}
	
	@ResponseBody 
	@PostMapping(value = "**/deleteMarca") 
	public ResponseEntity<?> deleteMarca(@RequestBody MarcaProduto marcaProduto) {
		
		marcaRepository.delete(marcaProduto);
		
		return new ResponseEntity<>("Maarca excluida com suceso", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteMarcaPorId/{id}") 
	public ResponseEntity<?> deleteMarcaPorId(@PathVariable ("id") Long id) {
		
		marcaRepository.deleteById(id);
		
		return new ResponseEntity<>("Marca removida", HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/obterMarca/{id}")
	public ResponseEntity<MarcaProduto> obterMarca(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		MarcaProduto marcaProduto = marcaRepository.findById(id).orElse(null);
		
		if(marcaProduto == null) {
			throw new ExceptionMentoriaJava("Marca com código: "+id+" não existe em nossa base de dados");
		}
		
		return new ResponseEntity<MarcaProduto>(marcaProduto, HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/buscarMarcaPorDesc/{desc}")
	public ResponseEntity<List<MarcaProduto>> buscarMarcaPorDesc(@PathVariable("desc") String desc) { 
		
		List <MarcaProduto> marcas = marcaRepository.buscarMarcaDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<MarcaProduto>>(marcas, HttpStatus.OK);
	}
	
	
}
