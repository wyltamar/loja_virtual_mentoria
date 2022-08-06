package mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.model.FormaPagamento;
import mentoria.lojavirtual.repository.FormaPagamentoRepository;

@RestController
public class FormaPagamentoContoller {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarFormaPagamento")
	public ResponseEntity<FormaPagamento> salvarFormaPagamento(@RequestBody @Valid FormaPagamento formaPagamento ){
		
		
		formaPagamento = formaPagamentoRepository.saveAndFlush(formaPagamento);
		
		return new ResponseEntity<FormaPagamento>(formaPagamento, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/listarFormasDePagamento")
	public ResponseEntity<List<FormaPagamento>>listarFormasDePagamento(){
		
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.listarFormasDePagamento();
		
		return new ResponseEntity<List<FormaPagamento>>(formasPagamento, HttpStatus.OK);
		
	}
	

}
