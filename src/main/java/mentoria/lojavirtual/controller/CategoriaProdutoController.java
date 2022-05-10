package mentoria.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.model.CategoriaProduto;
import mentoria.lojavirtual.model.dto.CategoriaProdutoDTO;
import mentoria.lojavirtual.repository.CategoriaProdutoRepository;


@RestController
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	
	@ResponseBody
	@PostMapping(value = "**/salvarCategoriaProduto")
	public ResponseEntity<CategoriaProdutoDTO> salvarCategoriaProduto(@RequestBody CategoriaProduto categoriaProduto)throws ExceptionMentoriaJava{
		
		if(categoriaProduto.getEmpresa() == null || categoriaProduto.getEmpresa().getId() == null) {
			throw new ExceptionMentoriaJava("A empresa deve ser informada.");
		}
		
		if(categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc())) {
			throw new ExceptionMentoriaJava("Já existe categoria cadastrada com mesmo nome.");
		}
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();
		categoriaProdutoDTO.setId(categoriaSalva.getId());
		categoriaProdutoDTO.setNomeDesc(categoriaSalva.getNomeDesc());
		categoriaProdutoDTO.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
		
		return new ResponseEntity<CategoriaProdutoDTO>(categoriaProdutoDTO, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteDescricao") 
	public ResponseEntity<?> deleteDescricao(@RequestBody CategoriaProduto categoria) {
		
		categoriaProdutoRepository.deleteById(categoria.getId());
		
		return new ResponseEntity<>("Categoria excluida com suceso", HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/buscarCatProdPorDesc/{desc}")
	public ResponseEntity<List<CategoriaProduto>> buscarCatProdPorDesc(@PathVariable("desc") String desc) { 
		
		List <CategoriaProduto> categoriasProdutos = categoriaProdutoRepository.buscarCategoriaProdutoDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<CategoriaProduto>>(categoriasProdutos, HttpStatus.OK);
	}
	
	

}
