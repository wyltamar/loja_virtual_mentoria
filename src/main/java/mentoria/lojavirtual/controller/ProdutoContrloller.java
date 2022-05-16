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
import mentoria.lojavirtual.model.Produto;
import mentoria.lojavirtual.repository.ProdutoRepository;

@Controller
@RestController
public class ProdutoContrloller {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@ResponseBody /*Para ser possível dar um retorno da API*/
	@PostMapping(value = "**/salvarProduto") /*Mapeando a url para receber JSON*/
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava { /*Recebe o JSON e converte para Objeto Java*/
		
		if(produto.getEmpresa().getId() == null || produto.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A empresa deve ser informada ");
		}
		
		if(produto.getId() == null ||produtoRepository.existeProduto(produto.getNome())) {
			
			List <Produto> produtos = produtoRepository.buscarProdutoNome(produto.getNome().toUpperCase(), 
									  produto.getEmpresa().getId());
			
			if(!produtos.isEmpty()) {
				
				throw new ExceptionMentoriaJava("Já existe produto com esse nome: "+produto.getNome());
			}
		}
		
		if(produto.getCategoriaProduto().getId() == null || produto.getCategoriaProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("A Categoria do produto é um campo obrigatório ");
		}
		
		if(produto.getMarcaProduto().getId() == null || produto.getMarcaProduto().getId() <= 0) {
			throw new ExceptionMentoriaJava("A Marca do produto é um campo obrigatório ");
		}
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*Para ser possível dar um retorno da API*/
	@PostMapping(value = "**/deleteProduto") /*Mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteProduto(@RequestBody Produto produto) { /*Recebe o JSON e converte para Objeto Java*/
		
		produtoRepository.delete(produto);
		
		return new ResponseEntity<>("Produto excluido com suceso", HttpStatus.OK);
	}
	
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteProdutoPorId/{id}") 
	public ResponseEntity<?> deleteProdutoPorId(@PathVariable ("id") Long id) {
		
		produtoRepository.deleteById(id);
		
		return new ResponseEntity<>("Produto removido", HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/obterProduto/{id}")
	public ResponseEntity<Produto> obterProduto(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		Produto produto = produtoRepository.findById(id).orElse(null);
		
		if(produto == null) {
			throw new ExceptionMentoriaJava("Produto com código: "+id+" não existe em nossa base de dados");
		}
		
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/buscarProdNome/{desc}")
	public ResponseEntity<List<Produto>> buscarProdutoPorDesc(@PathVariable("desc") String desc) { 
		
		List <Produto> produtos = produtoRepository.buscarProdutoNome(desc.toUpperCase());
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	
}
