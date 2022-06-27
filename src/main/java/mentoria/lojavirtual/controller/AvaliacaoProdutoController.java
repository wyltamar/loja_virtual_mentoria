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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.model.AvaliacaoProduto;
import mentoria.lojavirtual.repository.AvaliacaoProdutoRepository;

@RestController
public class AvaliacaoProdutoController {
	
	@Autowired
	private AvaliacaoProdutoRepository avaliacaoProdutoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarAvalicaoProduto")
	public ResponseEntity<AvaliacaoProduto> salvarAvalicaoProduto(@RequestBody @Valid AvaliacaoProduto avaliacaoProduto)throws ExceptionMentoriaJava{
		
		if(avaliacaoProduto.getPessoa() == null || (avaliacaoProduto.getPessoa() != null && avaliacaoProduto.
													getPessoa().getId() <= 0)) {
			throw new ExceptionMentoriaJava("A avaliação deve ter uma pessao/cliente associado");
		}
		
		if(avaliacaoProduto.getProduto() == null || (avaliacaoProduto.getProduto() == null && avaliacaoProduto.getProduto().getId() <=0)) {
			throw new ExceptionMentoriaJava("O produto deve ser informado.");
		}
		
		if(avaliacaoProduto.getEmpresa() == null || (avaliacaoProduto.getEmpresa() == null && avaliacaoProduto.getEmpresa().getId() <=0)) {
			throw new ExceptionMentoriaJava("A empresa dona do registro deve ser informada.");
		}
		
		avaliacaoProduto = avaliacaoProdutoRepository.saveAndFlush(avaliacaoProduto);
		
		return new ResponseEntity<AvaliacaoProduto>(avaliacaoProduto, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteAvaliacaoPessoa/{idAvaliacao}")
	public ResponseEntity<?> deleteAvaliacaoPessoa(@PathVariable("idAvaliacao") Long idAvaliacao){
		
		avaliacaoProdutoRepository.deleteById(idAvaliacao);
		
		return new ResponseEntity<String>("Avaliação removida com sucesso.",HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarAvaliacaoPorProduto/{idProduto}")
	public ResponseEntity<List<AvaliacaoProduto>> buscarAvaliacaoPorProduto(@PathVariable("idProduto") Long idProduto)throws ExceptionMentoriaJava{
		
		List<AvaliacaoProduto> avaliacoesProduto = avaliacaoProdutoRepository.buscarAvaliacaoPorProduto(idProduto);
		
		if(avaliacoesProduto.isEmpty()) {
			throw new ExceptionMentoriaJava("Não existe avaliação para o produto como id = "+idProduto);
		}
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacoesProduto, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarAvaliacaoProdutoPorPessoa/{idPessoa}")
	public ResponseEntity<List<AvaliacaoProduto>> buscarAvaliacaoProdutoPorPessoa(@PathVariable("idPessoa") Long idPessoa) throws ExceptionMentoriaJava{
		
		List<AvaliacaoProduto> avaliacoesPessoas = avaliacaoProdutoRepository.buscarAvaliacaoProdutoPorPessoa(idPessoa);
		
		if(avaliacoesPessoas.isEmpty()) {
			throw new ExceptionMentoriaJava("Não existe avaliação para o produto como id = "+idPessoa);
		}
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacoesPessoas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarAvaliacaoPorProdutoEPessoa/{idProduto}/{idPessoa}")
	public ResponseEntity<List<AvaliacaoProduto>> buscarAvaliacaoPorProdutoEPessoa(@PathVariable("idProduto") Long idProduto,
																				@PathVariable("idPessoa") Long idPessoa)throws ExceptionMentoriaJava{
		
		List<AvaliacaoProduto> avaliacoesProdutoPessoa = avaliacaoProdutoRepository.buscarAvaliacaoPorProdutoEPessoa(idProduto, idPessoa);
		
		if(avaliacoesProdutoPessoa.isEmpty()) {
			throw new ExceptionMentoriaJava("Não existe avaliação para a pessoa ou produto informados.");
		}
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacoesProdutoPessoa, HttpStatus.OK);
	}

}
