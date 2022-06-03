package mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.ExceptionMentoriaJava;
import mentoria.lojavirtual.model.NotaItemProduto;
import mentoria.lojavirtual.repository.NotaItemProdutoRepository;

@RestController
public class NotaItemProdutoController {
	
	@Autowired
	private  NotaItemProdutoRepository notaItemProdutoRepository;
	
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

}
