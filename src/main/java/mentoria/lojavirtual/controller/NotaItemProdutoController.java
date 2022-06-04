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
import mentoria.lojavirtual.model.NotaFiscalCompra;
import mentoria.lojavirtual.model.NotaItemProduto;
import mentoria.lojavirtual.model.Produto;
import mentoria.lojavirtual.repository.NotaFiscalCompraRepository;
import mentoria.lojavirtual.repository.NotaItemProdutoRepository;
import mentoria.lojavirtual.repository.ProdutoRepository;

@RestController
public class NotaItemProdutoController {
	
	@Autowired
	private  NotaItemProdutoRepository notaItemProdutoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarNotaItemProduto")
	public ResponseEntity<NotaItemProduto> salvarNotaItemProduto(@RequestBody @Valid NotaItemProduto notaItemProduto )throws ExceptionMentoriaJava{
		
		if(notaItemProduto.getId() == null) {
			
			if(notaItemProduto.getProduto().getId() == null ||notaItemProduto.getProduto().getId() <= 0) {
				throw new ExceptionMentoriaJava("O produto deve ser informado.");
			}
			
			if(notaItemProduto.getNotaFiscalCompra().getId() == null ||notaItemProduto.getNotaFiscalCompra().getId() <= 0) {
				throw new ExceptionMentoriaJava("A nota fiscal deve ser informada");
			}
			
			if(notaItemProduto.getEmpresa().getId() == null ||notaItemProduto.getEmpresa().getId() <= 0) {
				throw new ExceptionMentoriaJava("A empresa deve ser informada");
			}
			
			List<NotaItemProduto> notasItemProdutoExistentes = notaItemProdutoRepository.buscaNotaItemPorProdutoNota(notaItemProduto.getProduto().getId(), 
																							notaItemProduto.getNotaFiscalCompra().getId()); 
			
			if(!notasItemProdutoExistentes.isEmpty()) {
				
				throw new ExceptionMentoriaJava("Já existe este produto cadastrado para esta nota fiscal.");
			}
		}
		
		if(notaItemProduto.getQuantidade() <= 0) {
			throw new ExceptionMentoriaJava("A quantidade do produto deve ser informada.");
		}
		
		
		NotaItemProduto notaItemProdutoSalva = notaItemProdutoRepository.save(notaItemProduto); 
		
		return new ResponseEntity<NotaItemProduto>(notaItemProdutoSalva, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteNotaItemProdutoPorId/{id}") 
	public ResponseEntity<?> deleteNotaItemProdutoPorId(@PathVariable ("id") Long id) {
		
		notaItemProdutoRepository.deleteById(id);
		
		return new ResponseEntity<>("Nota item produto removido com sucesso!", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarNotaItemPorProduto/{idProduto}")
	public ResponseEntity <List<NotaItemProduto>> buscarNotaItemPorProduto(@PathVariable ("idProduto") Long idProduto) throws ExceptionMentoriaJava{
		
		Produto produto = produtoRepository.findById(idProduto).orElse(null);
		
		if(produto == null ) {
			throw new ExceptionMentoriaJava("Informe um id de Produto válido!");
		}
		
		List<NotaItemProduto> notasItemExistentes = notaItemProdutoRepository.buscaNotaItemPorProduto(idProduto);
		
		if(notasItemExistentes.isEmpty()) throw new ExceptionMentoriaJava("Não existe nota item produto associado a este produto!");
		
		return new ResponseEntity <List<NotaItemProduto>>(notasItemExistentes, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarNotaItemPorNota/{id}")
	public ResponseEntity<List<NotaItemProduto>> buscarNotaItemPorNota(@PathVariable("id") Long id)throws ExceptionMentoriaJava{
		
		NotaFiscalCompra notaFiscal = notaFiscalCompraRepository.findById(id).orElse(null);
		
		if(notaFiscal == null ) {
			
			throw new ExceptionMentoriaJava("Nota Fiscal inexistente!");	
		}
		else {
			
			List<NotaItemProduto> notasFiscais = notaItemProdutoRepository.buscaNotaItemPorNotaFiscal(id);
			
			if(notasFiscais.isEmpty()) {
				throw new ExceptionMentoriaJava("Nota Item não está associada a esta nota fiscal de compra");
			}else {
			
			return new ResponseEntity <List<NotaItemProduto>> (notasFiscais, HttpStatus.OK);
			}
		}
	
	}

}
