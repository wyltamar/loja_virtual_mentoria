package mentoria.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mentoria.lojavirtual.model.Acesso;
import mentoria.lojavirtual.repository.AcessoRepository;


@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso save(Acesso acesso) {
		
		/*Aqui podemos fazer qualque tipo de validação*/
		
		
		return acessoRepository.save(acesso);
	}
	
}


