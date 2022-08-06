package mentoria.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mentoria.lojavirtual.model.CupDesc;
import mentoria.lojavirtual.repository.CupDescRepository;

@RestController
public class CupDescController {
	
	@Autowired
	private CupDescRepository cupDescRepository;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "**/carregarCuponsDesconto")
	public ResponseEntity<List<CupDesc>> carregarCuponsDesconto(){
		
		List<CupDesc> coponsDescontos = cupDescRepository.carregarCuponsDesconto();
		
		return new ResponseEntity<List<CupDesc>>(coponsDescontos, HttpStatus.OK);
	}
}
