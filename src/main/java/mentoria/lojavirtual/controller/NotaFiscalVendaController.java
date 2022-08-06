package mentoria.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.model.NotaFiscalVenda;
import mentoria.lojavirtual.repository.NotaFiscalVendaRepository;

@RestController
public class NotaFiscalVendaController {
	
	@Autowired
	private NotaFiscalVendaRepository notaFiscalVendaRepository;
	
	@ResponseBody
	@GetMapping(value = "**/consultaNotaFiscalVenda/{idVenda}")
	public ResponseEntity<NotaFiscalVenda> consultaNotaFiscalVenda(@PathVariable("idVenda") Long idVenda){
		
		NotaFiscalVenda notaFiscalVenda = notaFiscalVendaRepository.consultaNotaFiscalVenda(idVenda);
		
		return new ResponseEntity<NotaFiscalVenda>(notaFiscalVenda, HttpStatus.OK);
	}

}
