package mentoria.lojavirtual.controller;

import java.util.ArrayList;
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
import mentoria.lojavirtual.model.NotaFiscalCompra;
import mentoria.lojavirtual.model.dto.RelatorioProdAlertaEstoqueDTO;
import mentoria.lojavirtual.model.dto.RelatorioProdCompradoNotaFiscalDTO;
import mentoria.lojavirtual.repository.NotaFiscalCompraRepository;
import mentoria.lojavirtual.service.NotaFiscalCompraService;

@Controller
@RestController
public class NotaFiscalCompraController {
	
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@Autowired
	private NotaFiscalCompraService notaFiscalCompraService; 
	
	@ResponseBody
	@PostMapping(value = "**/relatorioProdCompradoNotaFiscal")
	public ResponseEntity<List<RelatorioProdCompradoNotaFiscalDTO>> relatorioProdCompradoNotaFiscal(
		@Valid @RequestBody RelatorioProdCompradoNotaFiscalDTO relatorioProdCompradoNotaFiscalDTO){
		
		List<RelatorioProdCompradoNotaFiscalDTO> relatorio = new ArrayList<RelatorioProdCompradoNotaFiscalDTO>();
		
		relatorio = notaFiscalCompraService.gerarRelatorioProdCompraNota(relatorioProdCompradoNotaFiscalDTO); 
		
		return new ResponseEntity<List<RelatorioProdCompradoNotaFiscalDTO>>(relatorio, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/relatorioAlertaEstoque")
	public ResponseEntity<List<RelatorioProdAlertaEstoqueDTO>> gerarRelatorioAlertaEstoque(
		@RequestBody RelatorioProdAlertaEstoqueDTO relatorio){
		List<RelatorioProdAlertaEstoqueDTO> retorno = new ArrayList<RelatorioProdAlertaEstoqueDTO>();
		
		retorno = notaFiscalCompraService.gerarRelatorioAlertaEstoque(relatorio);
		
		return new ResponseEntity<List<RelatorioProdAlertaEstoqueDTO>>(retorno, HttpStatus.OK);
	}
	
	@ResponseBody 
	@PostMapping(value = "**/salvarNotaFiscalCompra") 
	public ResponseEntity<NotaFiscalCompra> salvarNotaFiscalCompra(@RequestBody @Valid NotaFiscalCompra notaFiscalCompra) throws ExceptionMentoriaJava { /*Recebe o JSON e converte para Objeto Java*/
		
		if(notaFiscalCompra.getId() == null ) {
			
			if(notaFiscalCompra.getDescricaoObs() != null) {
				
				boolean existeNotaFiscal = notaFiscalCompraRepository.existeNotaComDescricao(notaFiscalCompra.getDescricaoObs().toUpperCase().trim());
				
				if(existeNotaFiscal) {
					throw new ExceptionMentoriaJava("Já existe Nota Fiscal de compra com essa descrição");
				}
			}
		
		}
		
		if(notaFiscalCompra.getPessoaFornecedor() == null || notaFiscalCompra.getPessoaFornecedor().getId() <= 0) {
			throw new ExceptionMentoriaJava("O Fornecedor(PessoaJuridica) da nota fiscal deve ser informada ");
		}
		
		if(notaFiscalCompra.getEmpresa() == null || notaFiscalCompra.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A empresa deve ser informada ");
		}
		
		if(notaFiscalCompra.getContaPagar() == null || notaFiscalCompra.getContaPagar().getId() <= 0) {
			throw new ExceptionMentoriaJava("A conta a pagar da nota fiscal é um campo obrigatório");
		}
		
		NotaFiscalCompra notaFiscalCompraSalva = notaFiscalCompraRepository.save(notaFiscalCompra);
		
		return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraSalva, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteNotaFiscalCompraPorId/{id}") 
	public ResponseEntity<?> deleteNotaFiscalCompraPorId(@PathVariable ("id") Long id) {
		
		notaFiscalCompraRepository.deleteItemNotaFiscal(id); //Deleta os filhos
		notaFiscalCompraRepository.deleteById(id); //Deleta o pai
		
		return new ResponseEntity<>("Nota Fiscal de compra removida com sucesso", HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/buscarNotaFiscalCompraPorId/{id}")
	public ResponseEntity<NotaFiscalCompra> buscarNotaFiscalCompraPorId(@PathVariable("id") Long id) throws ExceptionMentoriaJava { 
		
		NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElse(null);
		
		if(notaFiscalCompra == null) {
			throw new ExceptionMentoriaJava("Nota fiscal de compra com código: "+id+" não existe em nossa base de dados");
		}
		
		return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompra, HttpStatus.OK);
	}

	
	@ResponseBody 
	@GetMapping(value = "**/buscarNotaFiscalCompraPorDesc/{desc}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarNotaFiscalCompraPorDesc(@PathVariable("desc") String desc) { 
		
		List <NotaFiscalCompra> notasFiscaisCompras = notaFiscalCompraRepository.buscarNotaPorDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<NotaFiscalCompra>>(notasFiscaisCompras, HttpStatus.OK);
	}
	
	
	
	

}
